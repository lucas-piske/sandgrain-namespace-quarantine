package com.lucas.sandgrain.namespacequarantine.listeners;

import com.google.common.eventbus.Subscribe;
import com.lucas.sandgrain.namespaceledger.api.ledger.InstanceNamespaceRemovedEvent;
import com.lucas.sandgrain.namespacequarantine.api.NamespaceQuarantineService;

/***
 * Called when instance namespaces identifications are no longer owned by the Instance Namespace Manager.
 * 
 * @author Lucas Piske
 *
 */
public class InstanceNamespaceRemovalListener {

	private final NamespaceQuarantineService quarantineService;
	
	public InstanceNamespaceRemovalListener(
			NamespaceQuarantineService quarantineService) {
		this.quarantineService = quarantineService;
	}

	/***
	 * Removes the namespaces identifications that are no longer owned by the Instance Namespace Manager from quarantine.
	 * 
	 * @param event Describes the namespaces identifications removal from Instance Namespace Manager.
	 */
	@Subscribe
	public void onNamespaceRemoval(InstanceNamespaceRemovedEvent event) {
		quarantineService
		.removeFromQuarantine(event.getRemovedNamespaces());
	}
	
}
