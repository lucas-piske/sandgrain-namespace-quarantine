package com.lucas.sandgrain.namespacequarantine.domain.model.quarantine;

import java.util.Set;

public interface QuarantineRemovalListener {
	public void onRemoval(Set<InstanceNamespace> namespaces);
}
