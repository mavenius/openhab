/**
 * Copyright (c) 2010-2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.radiothermostat.internal.hardware;

/**
* @author Eric J Thill
* @since 1.9.0
*/
public class RadioThermostatModel {
	private String model;

	public String getModel() {
		return model;
	}

	@Override
	public String toString() {
		return "Model [model=" + model + "]";
	}
}
