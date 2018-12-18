package com.lucas.sandgrain.namespacequarantine.component;

import com.google.common.eventbus.EventBus;
import com.lucas.sandgrain.namespacequarantine.api.NamespaceQuarantineServiceImpl;
import com.lucas.sandgrain.namespacequarantine.domain.model.quarantine.Quarantine;

public final class NamespaceQuarantineComponentFactoryImpl implements NamespaceQuarantineComponentFactory {

	@Override
	public NamespaceQuarantineComponent create(
			EventBus globalEventBus,
			long pingTakeoverMinWaitTimeMs) {
		Quarantine quarantine = createQuarantine(pingTakeoverMinWaitTimeMs);
		
		return new NamespaceQuarantineComponentImpl(
				globalEventBus,
				new NamespaceQuarantineServiceImpl(quarantine),
				quarantine);
	}
	
	public Quarantine createQuarantine(
			long pingTakeoverMinWaitTimeMs) {
		return new Quarantine(pingTakeoverMinWaitTimeMs);
	}

}
