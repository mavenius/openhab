/**
 * Copyright (c) 2010-2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.radiothermostat.internal;

import org.openhab.binding.radiothermostat.RadioThermostatBindingConfig;
import org.openhab.binding.radiothermostat.RadioThermostatBindingProvider;
import org.openhab.core.items.Item;
import org.openhab.model.item.binding.AbstractGenericBindingProvider;
import org.openhab.model.item.binding.BindingConfigParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* @author Eric J Thill
* @since 1.9.0
*/
public class RadioThermostatGenericBindingProvider extends AbstractGenericBindingProvider implements RadioThermostatBindingProvider {

	private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Override
    public String getBindingType() {
        return "radiothermostat";
    }
    
    @Override
    public RadioThermostatBindingConfig getItemConfig(String itemName) {
        return (RadioThermostatBindingConfig) bindingConfigs.get(itemName);
    }

    @Override
    public void processBindingConfiguration(String context, Item item, String bindingConfig)
            throws BindingConfigParseException {
    	logger.info("Loading " + bindingConfig);
        super.processBindingConfiguration(context, item, bindingConfig);
        String[] kvp = bindingConfig.split("\\/");
        if(kvp.length != 2) {
        	throw new BindingConfigParseException("Invalid binding config: " + bindingConfig);
        }
        RadioThermostatBindingConfig cfg = new RadioThermostatBindingConfig(item.getName(), kvp[0], kvp[1]);
        addBindingConfig(item, cfg);
        logger.info("Loaded " + cfg);
    }

    @Override
    public void validateItemType(Item item, String bindingConfig) throws BindingConfigParseException {
        
    }

}
