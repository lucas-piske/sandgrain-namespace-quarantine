package com.lucas.sandgrain.namespacequarantine.component;

import java.io.IOException;

import com.lucas.sandgrain.namespacequarantine.api.NamespaceQuarantineService;

/***
 * Provides ways to start and stop the component and also to access the services implemented by this component.
 * 
 * @author Lucas Piske
 *
 */
public interface NamespaceQuarantineComponent {
	public NamespaceQuarantineService getQuarantineService();
	
	/***
	 * Starts this component.
	 * 
	 * This method will return when the component is successfully started.
	 */
	public void start();
	
	/***
	 * Stops this component
	 * 
	 * This method will return when the component is successfully stopped.
	 * 
	 * @throws IOException
	 */
	public void stop() throws IOException;
}
