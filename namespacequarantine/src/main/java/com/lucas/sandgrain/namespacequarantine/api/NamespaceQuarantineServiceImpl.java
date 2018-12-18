package com.lucas.sandgrain.namespacequarantine.api;

import java.util.Set;
import java.util.stream.Collectors;

import com.lucas.sandgrain.namespacequarantine.domain.model.quarantine.InstanceNamespace;
import com.lucas.sandgrain.namespacequarantine.domain.model.quarantine.Quarantine;

public final class NamespaceQuarantineServiceImpl implements NamespaceQuarantineService {

	private final Quarantine quarantine;
	
	public NamespaceQuarantineServiceImpl(Quarantine quarantine) {
		this.quarantine = quarantine;
	}
	
	public void putOnQuarantine(int namespace) {
		quarantine.put(new InstanceNamespace(namespace));
	}

	public void putOnQuarantine(Set<Integer> intNamespaces) {
		Set<InstanceNamespace> namespaces = 
				intNamespaces.stream()
				.map(iN -> new InstanceNamespace(iN))
				.collect(Collectors.toSet());
		
		quarantine.putAll(namespaces);
	}

	public void removeFromQuarantine(int namespace) {
		quarantine.remove(new InstanceNamespace(namespace));
	}

	public void removeFromQuarantine(Set<Integer> intNamespaces) {
		Set<InstanceNamespace> namespaces = 
				intNamespaces.stream()
				.map(iN -> new InstanceNamespace(iN))
				.collect(Collectors.toSet());
		
		quarantine.removeAll(namespaces);
	}

	@Override
	public void listenForQuarantineLeaving(
			InstanceNamespaceQuarantineLeavingListener listener) {
		this.quarantine.listenForLeaving(
				new QuarantineLeavingConversionListener(listener));
	}

	@Override
	public void listenForQuarantineRemovals(InstanceNamespaceQuarantineRemovalListener listener) {
		this.quarantine.listenForRemovals(
				new QuarantineRemovalConversionListener(listener));
	}

}
