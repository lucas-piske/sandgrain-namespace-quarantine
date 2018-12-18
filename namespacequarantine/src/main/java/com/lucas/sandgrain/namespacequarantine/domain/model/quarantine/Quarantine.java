package com.lucas.sandgrain.namespacequarantine.domain.model.quarantine;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class Quarantine implements Closeable {

	private final long pingTakeoverMinWaitTimeMs;
	
	private final Map<InstanceNamespace,Long> namespaces = 
			new HashMap<InstanceNamespace,Long>();
	private final ReentrantLock quarantineManipulationLock = 
			new ReentrantLock();
	private Optional<Thread> quarantineLeavingThread = 
			Optional.empty();
	private Optional<QuarantineLeavingCheckRunnable> quarantineLeavingCheck = 
			Optional.empty();
	private boolean started = false;
	private boolean closed = false;
	private final ReentrantLock runStateLock = new ReentrantLock();
	
	private volatile Optional<QuarantineRemovalListener> removalListener = 
			Optional.empty();
	private volatile Optional<QuarantineLeavingListener> leavingListener = 
			Optional.empty();
	
	public Quarantine(long pingTakeoverMinWaitTimeMs) {
		this.pingTakeoverMinWaitTimeMs = pingTakeoverMinWaitTimeMs;
	}
	
	public boolean put(InstanceNamespace namespace) {
		try {
			quarantineManipulationLock.lock();
			
			return namespaces.put(namespace, System.nanoTime()) != null;
		} finally {
			quarantineManipulationLock.unlock();
		}
	}
	
	public boolean putAll(Set<InstanceNamespace> namespaces) {
		try {
			quarantineManipulationLock.lock();
			
			boolean replacedAtLeastOne = false;
			for(InstanceNamespace namespace : namespaces) {
				replacedAtLeastOne = replacedAtLeastOne || put(namespace);
			}
			
			return replacedAtLeastOne;
		} finally {
			quarantineManipulationLock.unlock();
		}
	}

	public boolean remove(InstanceNamespace namespace) {
		try {
			quarantineManipulationLock.lock();
			
			boolean containedNamespace = this.namespaces.remove(namespace) != null;
			
			callRemovalListener(namespace);
			
			return containedNamespace;
		} finally {
			quarantineManipulationLock.unlock();
		}
	}
	
	public boolean removeAll(Set<InstanceNamespace> namespaces) {
		try {
			quarantineManipulationLock.lock();
			
			boolean setChanged = false;
			for(InstanceNamespace namespace : namespaces) {
				setChanged = setChanged || remove(namespace);
			}
			
			callRemovalListener(namespaces);
			
			return setChanged;
		} finally {
			quarantineManipulationLock.unlock();
		}
	}
	
	private void liberateSafeNamespaces() {
		final long nowNs = System.nanoTime();
		
		final Set<InstanceNamespace> namespacesLeaving = new HashSet<InstanceNamespace>();
		
		try {
			quarantineManipulationLock.lock();
			
			final Iterator<Entry<InstanceNamespace, Long>> quarantinedNamespacesIt = 
					namespaces.entrySet().iterator();
			while(quarantinedNamespacesIt.hasNext()) {
				Map.Entry<InstanceNamespace, Long> namespace = quarantinedNamespacesIt.next();
				if(Math.floor((nowNs - namespace.getValue())/1000000) > pingTakeoverMinWaitTimeMs) {
					quarantinedNamespacesIt.remove();
					namespacesLeaving.add(namespace.getKey());
				}
			}
		} finally {
			quarantineManipulationLock.unlock();
		}
		
		callLeavingListener(namespacesLeaving);
	}
	
	private void callRemovalListener(InstanceNamespace namespace) {
		Set<InstanceNamespace> namespaces = new HashSet<InstanceNamespace>(1);
		namespaces.add(namespace);
		
		callRemovalListener(namespaces);
	}
	
	private void callRemovalListener(Set<InstanceNamespace> namespaces) {
		final Optional<QuarantineRemovalListener> removalListener = 
				this.removalListener;
		
		if(removalListener.isPresent() && namespaces.size() > 0) {
			removalListener.get().onRemoval(namespaces);
		}
	}
	
	private void callLeavingListener(Set<InstanceNamespace> namespaces) {
		final Optional<QuarantineLeavingListener> leavingListener = 
				this.leavingListener;
		
		if(leavingListener.isPresent() && namespaces.size() > 0) {
			leavingListener.get().onLeaving(namespaces);
		}
	}
	
	public void listenForRemovals(QuarantineRemovalListener listener) {
		this.removalListener = Optional.of(listener);
	}
	
	public void listenForLeaving(QuarantineLeavingListener listener) {
		this.leavingListener = Optional.of(listener);
	}

	public void start() {
		try {
			runStateLock.lock();
			
			if(!this.started && !this.closed) {
				this.quarantineLeavingCheck = Optional.of(
						new QuarantineLeavingCheckRunnable());
				this.quarantineLeavingThread = Optional.of(
						new Thread(quarantineLeavingCheck.get()));
				this.quarantineLeavingThread.get().start();
			
				this.started = true;
			}
		} finally {
			runStateLock.unlock();
		}
	}
	
	public void close() throws IOException {
		try {
			runStateLock.lock();
		
			if(this.started && !this.closed) {
				this.quarantineLeavingCheck.get().stopRunning();
	
				this.closed = true;
			}
		} finally {
			runStateLock.unlock();
		}
	}

	private class QuarantineLeavingCheckRunnable implements Runnable {
		
		private volatile boolean stopRequested = false;
		private ReentrantLock runLoopLock = new ReentrantLock();
		
		public void run() {
			try {
				runLoopLock.lock();
				long lastLiberationNs = System.nanoTime();
				while(!stopRequested) {
					long nowNs = System.nanoTime();
					long timeToSleepMs = pingTakeoverMinWaitTimeMs - (long) Math.ceil((nowNs - lastLiberationNs)/1000000);
					if(timeToSleepMs <= 0) {
						liberateSafeNamespaces();
						lastLiberationNs = System.nanoTime();
					} else {
						Thread.sleep(timeToSleepMs);
					}
				}
			} catch (InterruptedException e) {
				
			} finally {
				runLoopLock.unlock();
			}
		}
		
		public void stopRunning() {
			try {
				this.stopRequested = true;
				runLoopLock.lock();
			} finally {
				runLoopLock.unlock();
			}
		}
		
	}
	
}