package com.lucas.sandgrain.namespacequarantine.api;

import java.util.Set;

/***
 * Receives notifications when namespaces are removed from quarantine.
 * 
 * @author Lucas Piske
 *
 */
public interface InstanceNamespaceQuarantineRemovalListener {
	/***
	 * Called when a set of namespaces are removed from quarantine.
	 * 
	 * @param namespaces Namespaces identifications
	 */
	public void onRemoval(Set<Integer> namespaces);
}
