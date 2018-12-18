package com.lucas.sandgrain.namespacequarantine.domain.model.quarantine;

public class InstanceNamespace {

	private final int namespace;

	public InstanceNamespace(int namespace) {
		this.namespace = namespace;
	}

	public int toInt() {
		return namespace;
	}
	
	@Override
	public int hashCode() {
		return Integer.hashCode(namespace);
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof InstanceNamespace) {
			InstanceNamespace otherNamespace = (InstanceNamespace) o;
			return this.namespace == otherNamespace.namespace;
		}
		return false;
	}

	@Override
	public String toString() {
		return Integer.toString(namespace);
	}
	
}
