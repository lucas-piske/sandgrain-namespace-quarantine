package com.lucas.sandgrain.namespacequarantine.component;

import java.io.IOException;

import com.google.common.eventbus.EventBus;
import com.lucas.sandgrain.namespacequarantine.api.NamespaceQuarantineService;
import com.lucas.sandgrain.namespacequarantine.domain.model.quarantine.Quarantine;
import com.lucas.sandgrain.namespacequarantine.listeners.InstanceNamespaceReceivalListener;
import com.lucas.sandgrain.namespacequarantine.listeners.InstanceNamespaceRemovalListener;

public final class NamespaceQuarantineComponentImpl implements NamespaceQuarantineComponent {

	private final EventBus globalEventBus;
	private final NamespaceQuarantineService quarantineService;
	private final Quarantine quarantine;
	
	private InstanceNamespaceReceivalListener namespaceReceivalListener;
	private InstanceNamespaceRemovalListener namespaceRemovalListener;
	
	public NamespaceQuarantineComponentImpl(
			EventBus globalEventBus,
			NamespaceQuarantineService quarantineService,
			Quarantine quarantine) {
		this.globalEventBus = globalEventBus;
		this.quarantineService = quarantineService;
		this.quarantine = quarantine;
	}
	
	@Override
	public NamespaceQuarantineService getQuarantineService() {
		return quarantineService;
	}

	@Override
	public void start() {
		this.namespaceReceivalListener = 
				new InstanceNamespaceReceivalListener(quarantineService);
		this.namespaceRemovalListener = 
				new InstanceNamespaceRemovalListener(quarantineService);
		globalEventBus.register(namespaceReceivalListener);
		globalEventBus.register(namespaceRemovalListener);
		
		quarantineService
		.listenForQuarantineLeaving(
				new QuarantineLeavingPipingListener(globalEventBus));
		quarantineService
		.listenForQuarantineRemovals(
				new QuarantineRemovalPipingListener(globalEventBus));
		
		quarantine.start();
	}

	@Override
	public void stop() throws IOException {
		globalEventBus.unregister(namespaceReceivalListener);
		globalEventBus.unregister(namespaceRemovalListener);
		
		quarantine.close();
	}

}
