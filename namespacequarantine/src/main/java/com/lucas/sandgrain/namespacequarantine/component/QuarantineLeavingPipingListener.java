package com.lucas.sandgrain.namespacequarantine.component;

import java.util.Set;

import com.google.common.eventbus.EventBus;
import com.lucas.sandgrain.namespacequarantine.api.InstanceNamespacesLeftQuarantineEvent;
import com.lucas.sandgrain.namespacequarantine.api.InstanceNamespaceQuarantineLeavingListener;

/***
 * Listens to namespaces identifications leaving quarantine and publish {@link com.lucas.sandgrain.namespacequarantine.api.InstanceNamespacesLeftQuarantineEvent events} to the global event bus.
 * 
 * @author Lucas Piske
 *
 */
public final class QuarantineLeavingPipingListener implements InstanceNamespaceQuarantineLeavingListener {

	private final EventBus globalEventBus;
	
	public QuarantineLeavingPipingListener(EventBus globalEventBus) {
		this.globalEventBus = globalEventBus;
	}
	
	@Override
	public void onLeaving(Set<Integer> namespaces) {
		globalEventBus.post(
				new InstanceNamespacesLeftQuarantineEvent(namespaces));
	}

}
