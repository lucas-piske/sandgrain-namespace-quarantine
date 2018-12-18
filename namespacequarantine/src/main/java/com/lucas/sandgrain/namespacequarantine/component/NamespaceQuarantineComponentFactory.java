package com.lucas.sandgrain.namespacequarantine.component;

import com.google.common.eventbus.EventBus;

/***
 * Instantiates the component {@link com.lucas.sandgrain.namespacequarantine.component.NamespaceQuarantineComponent} following the provided configuration.
 * 
 * @author Lucas Piske
 *
 */
public interface NamespaceQuarantineComponentFactory {
	
	/**
	 * Instantiates the component
	 * 
	 * @param globalEventBus Used to exchange events between the different modules.
	 * @param pingTakeoverMinWaitTimeMs Defines the amount of time in milliseconds for namespaces to stay in quarantine before being released.
	 * @return The instantiated component following the specified configuration.
	 */
	public NamespaceQuarantineComponent create(
			EventBus globalEventBus,
			long pingTakeoverMinWaitTimeMs);

}
