/**
 * Copyright (c) 2010-2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.radiothermostat.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* @author Eric J Thill
* @since 1.9.0
*/
public final class RadioThermostatActivator implements BundleActivator {

    private static Logger logger = LoggerFactory.getLogger(RadioThermostatActivator.class);

    @Override
    public void start(BundleContext bc) throws Exception {
        logger.debug("Radio Thermostat binding has been started.");
    }

    @Override
    public void stop(BundleContext bc) throws Exception {
        logger.debug("Radio Thermostat binding has been stopped.");
    }

}
