package com.lucas.sandgrain.namespacequarantine.api;

import java.util.Set;

/***
 * Describes the namespaces that left quarantine at some point.
 * 
 * @author Lucas Piske
 *
 */
public class InstanceNamespacesLeftQuarantineEvent {

	private final Set<Integer> namespaces;
	
	public InstanceNamespacesLeftQuarantineEvent(Set<Integer> namespaces) {
		this.namespaces = namespaces;
	}
	
	/***
	 * Retrieves namespaces identifications that left quarantine.
	 * 
	 * @return Namespaces identifications that left quarantine.
	 */
	public Set<Integer> getNamespaces() {
		return namespaces;
	}
	
}
