package com.lucas.sandgrain.namespacequarantine.api;

import java.util.Set;

/**
 * Receives notifications when namespaces leave quarantine.
 * 
 * @author Lucas Piske
 *
 */
public interface InstanceNamespaceQuarantineLeavingListener {
	/***
	 * Called when a set of namespaces leave quarantine.
	 * 
	 * @param namespaces Namespaces identifications
	 */
	public void onLeaving(Set<Integer> namespaces);
}
