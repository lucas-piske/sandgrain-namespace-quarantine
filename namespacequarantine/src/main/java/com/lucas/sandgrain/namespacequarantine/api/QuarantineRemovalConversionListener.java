package com.lucas.sandgrain.namespacequarantine.api;

import java.util.Set;
import java.util.stream.Collectors;

import com.lucas.sandgrain.namespacequarantine.domain.model.quarantine.InstanceNamespace;
import com.lucas.sandgrain.namespacequarantine.domain.model.quarantine.QuarantineRemovalListener;

/***
 * Maps the listener parameter from the internal domain model to the api exposed types.
 * 
 * @see NamespaceQuarantineServiceImpl#listenForQuarantineRemovals(InstanceNamespaceQuarantineRemovalListener)
 * @author Lucas Piske
 *
 */
public final class QuarantineRemovalConversionListener implements QuarantineRemovalListener {

	private final com.lucas.sandgrain.namespacequarantine.api.InstanceNamespaceQuarantineRemovalListener apiRemovalListener;
	
	public QuarantineRemovalConversionListener(
			com.lucas.sandgrain.namespacequarantine.api.InstanceNamespaceQuarantineRemovalListener apiRemovalListener) {
		this.apiRemovalListener = apiRemovalListener;
	}
	
	@Override
	public void onRemoval(Set<InstanceNamespace> namespaces) {
		Set<Integer> intNamespaces = 
				namespaces.stream()
				.map(n -> n.toInt())
				.collect(Collectors.toSet());
				
		apiRemovalListener.onRemoval(intNamespaces);
	}

}
