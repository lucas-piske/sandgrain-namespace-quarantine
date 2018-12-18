package com.lucas.sandgrain.namespacequarantine.listeners;

import com.google.common.eventbus.Subscribe;
import com.lucas.sandgrain.namespaceledger.api.ledger.InstanceNamespaceInsertedEvent;
import com.lucas.sandgrain.namespacequarantine.api.NamespaceQuarantineService;

/***
 * Called when instance namespaces identifications are received by the Instance Namespace Manager.
 * 
 * @author Lucas Piske
 *
 */
public class InstanceNamespaceReceivalListener {

	private final NamespaceQuarantineService quarantineService;
	
	public InstanceNamespaceReceivalListener(
			NamespaceQuarantineService quarantineService) {
		this.quarantineService = quarantineService;
	}

	/***
	 * Puts the newly received namespaces identifications into quarantine.
	 * 
	 * @param event Describes the namespaces identifications reception by the Instance Namespace Manager.
	 */
	@Subscribe
	public void onNamespaceReceival(InstanceNamespaceInsertedEvent event) {
		quarantineService
		.putOnQuarantine(event.getInsertedNamespaces());
	}
	
}
