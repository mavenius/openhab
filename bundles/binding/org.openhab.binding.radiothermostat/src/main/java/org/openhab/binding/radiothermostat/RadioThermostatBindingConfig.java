/**
 * Copyright (c) 2010-2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.radiothermostat;

import org.openhab.core.binding.BindingConfig;

/**
* @author Eric J Thill
* @since 1.9.0
*/
public class RadioThermostatBindingConfig implements BindingConfig {

	public final String itemName;
	public final String host;
    public final String property;

    public RadioThermostatBindingConfig(String itemName, String host, String property) {
    	this.itemName = itemName;
        this.host = host;
        this.property = property;
    }

    @Override
	public String toString() {
		return "[itemName=" + itemName + ", host=" + host + ", property=" + property + "]";
	}
    
}