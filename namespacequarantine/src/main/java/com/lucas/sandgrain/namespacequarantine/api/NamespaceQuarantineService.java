package com.lucas.sandgrain.namespacequarantine.api;

import java.util.Set;

/***
 * This interface lets its user operate on the namespace quarantine providing ways to put and remove namespaces.
 * Registering listeners makes it also possible to react when namespaces are removed or leave the quarantine.
 * Namespaces leave quarantine when a predefined amount of time is passed after inserted.
 * 
 * @author Lucas Piske
 *
 */
public interface NamespaceQuarantineService {
	
	/***
	 * Puts a namespace in the quarantine.
	 * 
	 * If the same namespace is still contained the quarantine time will be restarted.
	 * 
	 * @param namespace Namespace identification
	 */
	public void putOnQuarantine(int namespace);
	
	/***
	 * Puts all specified namespaces in the quarantine.
	 * 
	 * If a namespace is still contained the quarantine time will be restarted for that specific namespace.
	 * 
	 * @param namespaces Namespaces identifications
	 */
	public void putOnQuarantine(Set<Integer> namespaces);
	
	/***
	 * Remove the namespace from quarantine
	 * 
	 * If the namespace is not contained nothing is removed.
	 * 
	 * @param namespace Namespace identification
	 */
	public void removeFromQuarantine(int namespace);
	
	/***
	 * Remove all specified namespaces from quarantine.
	 * 
	 * Only contained namespaces that match the specified namespaces are removed.
	 * 
	 * @param namespaces Namespaces identifications
	 */
	public void removeFromQuarantine(Set<Integer> namespaces);
	
	/***
	 * Register a listener to be called when a set of namespaces leave quarantine.
	 * 
	 * @param listener Listener called when a set of namespaces leave quarantine.
	 */
	public void listenForQuarantineLeaving(InstanceNamespaceQuarantineLeavingListener listener);
	
	/***
	 * Register a listener to be called when a set of namespaces are removed from quarantine.
	 * 
	 * @param listener Listener called when a set of namespaces are removed from quarantine.
	 */
	public void listenForQuarantineRemovals(InstanceNamespaceQuarantineRemovalListener listener);
}
