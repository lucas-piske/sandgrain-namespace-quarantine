package com.lucas.sandgrain.namespacequarantine.domain.model.quarantine;

import java.util.Set;

public interface QuarantineLeavingListener {
	public void onLeaving(Set<InstanceNamespace> namespaces);
}
