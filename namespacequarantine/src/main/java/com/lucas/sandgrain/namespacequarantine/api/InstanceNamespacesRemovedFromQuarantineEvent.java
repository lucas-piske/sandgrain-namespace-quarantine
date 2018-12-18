package com.lucas.sandgrain.namespacequarantine.api;

import java.util.Set;

/***
 * Describes the namespaces that were removed from quarantine at some point.
 * 
 * @author Lucas Piske
 *
 */
public class InstanceNamespacesRemovedFromQuarantineEvent {

	private final Set<Integer> namespaces;
	
	public InstanceNamespacesRemovedFromQuarantineEvent(Set<Integer> namespaces) {
		this.namespaces = namespaces;
	}
	
	/***
	 * Retrieves namespaces identifications that were removed from quarantine.
	 * 
	 * @return Namespaces identifications that were removed from quarantine.
	 */
	public Set<Integer> getNamespaces() {
		return namespaces;
	}
	
}
