package com.lucas.sandgrain.namespacequarantine.component;

import java.util.Set;

import com.google.common.eventbus.EventBus;
import com.lucas.sandgrain.namespacequarantine.api.InstanceNamespacesRemovedFromQuarantineEvent;
import com.lucas.sandgrain.namespacequarantine.api.InstanceNamespaceQuarantineRemovalListener;

/***
 * Listens to namespaces identifications being removed from quarantine and publish {@link com.lucas.sandgrain.namespacequarantine.api.InstanceNamespacesRemovedFromQuarantineEvent events} to the global event bus.
 * 
 * @author Lucas Piske
 *
 */
public final class QuarantineRemovalPipingListener implements InstanceNamespaceQuarantineRemovalListener {

	private final EventBus globalEventBus;
	
	public QuarantineRemovalPipingListener(EventBus globalEventBus) {
		this.globalEventBus = globalEventBus;
	}
	
	@Override
	public void onRemoval(Set<Integer> namespaces) {
		globalEventBus.post(
				new InstanceNamespacesRemovedFromQuarantineEvent(namespaces));
	}

}
