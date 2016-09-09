/**
 * Copyright (c) 2010-2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.radiothermostat.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.openhab.binding.radiothermostat.RadioThermostatBindingConfig;
import org.openhab.binding.radiothermostat.RadioThermostatBindingProvider;
import org.openhab.binding.radiothermostat.internal.hardware.RadioThermostatHardware;
import org.openhab.binding.radiothermostat.internal.hardware.RadioThermostatStatus;
import org.openhab.core.binding.AbstractActiveBinding;
import org.openhab.core.library.types.DateTimeType;
import org.openhab.core.library.types.DecimalType;
import org.openhab.core.library.types.StringType;
import org.openhab.core.types.Command;
import org.openhab.core.types.State;
import org.openhab.core.types.Type;
import org.openhab.core.types.UnDefType;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* @author Eric J Thill
* @since 1.9.0
*/
public class RadioThermostatBinding extends AbstractActiveBinding<RadioThermostatBindingProvider>implements ManagedService {

    static final Logger logger = LoggerFactory.getLogger(RadioThermostatBinding.class);

    private long refreshInterval;

    @Override
    protected long getRefreshInterval() {
        return refreshInterval;
    }

    @Override
    protected String getName() {
        return "radiothermostat";
    }

    @Override
    public void execute() {
    	pollUpdates();
    }
    
    private synchronized void pollUpdates() {
    	// key=host, value=items (organized this way for batch reads per host/device)
    	Map<String, List<RadioThermostatBindingConfig>> hostItems = getHostItems();
    	
    	if(logger.isDebugEnabled()) {
    		logger.debug("Polling updates for " + hostItems);
    	}
    	
    	for(Map.Entry<String, List<RadioThermostatBindingConfig>> entry : hostItems.entrySet()) {
    		String host = entry.getKey();
    		List<RadioThermostatBindingConfig> items = entry.getValue();
    		// RadioThermostat object is very lightweight. No need to keep instances around between calls.
    		RadioThermostatHardware tstat = new RadioThermostatHardware(host);
    		try {
	    		RadioThermostatStatus status = tstat.poll_status();
	    		if(logger.isDebugEnabled()) {
	        		logger.debug(host + " Status is " + status);
	        	}
	    		for(RadioThermostatBindingConfig item : items) {
	    			updateItem(item, status);
	    		}
    		} catch(Throwable t) {
    			logger.error("Could not read from Radio Thermostat @" + host, t);
    		}
    	}
    	
    	if(logger.isDebugEnabled()) {
    		logger.debug("Poll complete");
    	}
    }
    
    private void updateItem(RadioThermostatBindingConfig item, RadioThermostatStatus status) {
    	State value;
    	switch(item.property) {
		case "a_cool":
    		value = status.a_cool != null ? new DecimalType(status.a_cool) : UnDefType.NULL;
    		break;
    	case "a_heat":
    		value = status.a_heat != null ? new DecimalType(status.a_heat) : UnDefType.NULL;
    		break;
    	case "a_mode":
    		value = status.a_mode != null ? new DecimalType(status.a_mode) : UnDefType.NULL;
    		break;
    	case "fmode":
    		value = status.fmode != null ? new DecimalType(status.fmode) : UnDefType.NULL;
    		break;
    	case "fstate":
    		value = status.fstate != null ? new DecimalType(status.fstate) : UnDefType.NULL;
    		break;
    	case "hold":
    		value = status.hold != null ? new DecimalType(status.hold) : UnDefType.NULL;
    		break;
    	case "it_cool":
    		value = status.it_cool != null ? new DecimalType(status.it_cool) : UnDefType.NULL;
    		break;
    	case "it_heat":
    		value = status.it_heat != null ? new DecimalType(status.it_heat) : UnDefType.NULL;
    		break;
    	case "override":
    		value = status.override != null ? new DecimalType(status.override) : UnDefType.NULL;
    		break;
    	case "t_cool":
    		value = status.t_cool != null ? new DecimalType(status.t_cool) : UnDefType.NULL;
    		break;
    	case "t_heat":
    		value = status.t_heat != null ? new DecimalType(status.t_heat) : UnDefType.NULL;
    		break;
    	case "temp":
    		value = status.temp != null ? new DecimalType(status.temp) : UnDefType.NULL;
    		break;
    	case "time":
    		value = status.time != null ? new StringType(status.time.toString()) : UnDefType.NULL;
    		break;
    	case "tmode":
    		value = status.tmode != null ? new DecimalType(status.tmode) : UnDefType.NULL;
    		break;
    	case "tstate":
    		value = status.tstate != null ? new DecimalType(status.tstate) : UnDefType.NULL;
    		break;
    	case "_last_connection_time":
    		value = new DateTimeType();
    		break;
		case "humidity":
			value = status.humidity != null ? new DecimalType(status.humidity) : UnDefType.NULL;
			break;
		case "t_humidity":
			value = status.t_humidity != null ? new DecimalType(status.t_humidity) : UnDefType.NULL;
			break;
		case "humidifier_mode":
			value = status.humidifier_mode != null ? new DecimalType(status.humidifier_mode) : UnDefType.NULL;
			break;
    	case "_refresh":
    		// do nothing. command signal only.
    		return;
    	default:
			logger.error(item + " has invalid property");
			return;
    	}
    	eventPublisher.postUpdate(item.itemName, value);
	}
    
    private Map<String, List<RadioThermostatBindingConfig>> getHostItems() {
        Map<String, List<RadioThermostatBindingConfig>> hostItemsMap = new HashMap<>();

        for (RadioThermostatBindingProvider provider : this.providers) {
            Collection<String> itemNames = provider.getItemNames();
            for (String itemName : itemNames) {
                RadioThermostatBindingConfig config = provider.getItemConfig(itemName);
                List<RadioThermostatBindingConfig> l = hostItemsMap.get(config.host);
                if(l == null) {
                	l = new ArrayList<>();
                	hostItemsMap.put(config.host, l);
                }
                l.add(config);
            }
        }

        return hostItemsMap;
    }

    @Override
    public void internalReceiveCommand(String itemName, Command command) {
    	if(logger.isDebugEnabled()) {
    		logger.debug(itemName + " received command " + command);
    	}
    	for (RadioThermostatBindingProvider provider : this.providers) {
    		RadioThermostatBindingConfig cfg = provider.getItemConfig(itemName);
    		if(cfg != null) {
    			try {
    				post(cfg, command);
				} catch (IOException e) {
					logger.error("Could not update '" + itemName + "'", e);
				}
    		}
    	}
    }
    
    private void post(RadioThermostatBindingConfig item, Type value) throws IOException {
    	if(logger.isDebugEnabled()) {
    		logger.debug(item + " post " + value);
    	}
    	
    	if("_refresh".equals(item.property)) {
    		// internal entry to force a status refresh
    		pollUpdates();
    		return;
    	}
		
    	if(!(value instanceof DecimalType)) {
    		logger.error("Unsupported type '" + value.getClass().getSimpleName() + "'");
    		return;
    	}
    	DecimalType decimal = (DecimalType)value;
    	RadioThermostatHardware tstat = new RadioThermostatHardware(item.host);
    	switch(item.property) {
    	case "a_cool":
    		tstat.post_a_cool(decimal.doubleValue());
    		break;
    	case "a_heat":
    		tstat.post_a_heat(decimal.doubleValue());
    		break;
    	case "a_mode":
    		tstat.post_a_mode(decimal.intValue());
    		break;
    	case "fmode":
    		tstat.post_fmode(decimal.intValue());
    		break;
    	case "it_cool":
    		tstat.post_it_cool(decimal.doubleValue());
    		break;
    	case "it_heat":
    		tstat.post_it_heat(decimal.doubleValue());
    		break;
    	case "t_cool":
    		tstat.post_t_cool(decimal.doubleValue());
    		break;
    	case "t_heat":
    		tstat.post_t_heat(decimal.doubleValue());
    		break;
    	case "tmode":
    		tstat.post_tmode(decimal.intValue());
    		break;
		case "t_humidity":
			tstat.post_t_humidity(decimal.doubleValue());
			break;
		case "humidifier_mode":
			tstat.post_humidifier_mode(decimal.intValue());
			break;
    	case "fstate":
    	case "hold":
    	case "override":
    	case "temp":
    	case "time":
    	case "tstate":
    	case "_last_connection_time":
    		logger.error(item + " is read-only");
    		return;
		default:
			logger.error(item + " has invalid property");
			return;
    	}
    	
    	// always poll new updates after a command is issued
    	pollUpdates();
	}


    @Override
    public void updated(@SuppressWarnings("rawtypes") Dictionary config) throws ConfigurationException {
        if (config != null) {
            String refreshIntervalString = (String) config.get("refresh");
            if (StringUtils.isNotBlank(refreshIntervalString)) {
                refreshInterval = Long.parseLong(refreshIntervalString);

                // RefreshInterval is specified in openhap.cfg, therefore enable polling
                setProperlyConfigured(true);
            }
        }
    }
}
