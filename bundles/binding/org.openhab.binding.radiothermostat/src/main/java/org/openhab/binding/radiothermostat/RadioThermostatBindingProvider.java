/**
 * Copyright (c) 2010-2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.radiothermostat;

import org.openhab.core.binding.BindingProvider;

/**
* @author Eric J Thill
* @since 1.9.0
*/
public interface RadioThermostatBindingProvider extends BindingProvider {
    RadioThermostatBindingConfig getItemConfig(String itemName);
}
