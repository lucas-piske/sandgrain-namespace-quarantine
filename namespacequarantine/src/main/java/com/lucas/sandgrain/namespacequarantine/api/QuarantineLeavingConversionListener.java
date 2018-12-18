package com.lucas.sandgrain.namespacequarantine.api;

import java.util.Set;
import java.util.stream.Collectors;

import com.lucas.sandgrain.namespacequarantine.domain.model.quarantine.InstanceNamespace;
import com.lucas.sandgrain.namespacequarantine.domain.model.quarantine.QuarantineLeavingListener;

/***
 * Maps the listener parameter from the internal domain model to the api exposed types.
 * 
 * 
 * @see NamespaceQuarantineServiceImpl#listenForQuarantineLeaving(InstanceNamespaceQuarantineLeavingListener)
 * @author Lucas Piske
 *
 */
public final class QuarantineLeavingConversionListener implements QuarantineLeavingListener {

	private final com.lucas.sandgrain.namespacequarantine.api.InstanceNamespaceQuarantineLeavingListener apiLeavingListener;
	
	public QuarantineLeavingConversionListener(
			com.lucas.sandgrain.namespacequarantine.api.InstanceNamespaceQuarantineLeavingListener apiLeavingListener) {
		this.apiLeavingListener = apiLeavingListener;
	}
	
	@Override
	public void onLeaving(Set<InstanceNamespace> namespaces) {
		Set<Integer> intNamespaces = 
				namespaces.stream()
				.map(n -> n.toInt())
				.collect(Collectors.toSet());
				
		apiLeavingListener.onLeaving(intNamespaces);
	}

}
