/**
 * Copyright (c) 2010-2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.radiothermostat.internal.hardware;

import java.io.IOException;

import com.google.gson.Gson;

/**
* @author Eric J Thill
* @since 1.9.0
*/
public class RadioThermostatHardware {

	private final Gson gson = new Gson();
	private final String host;
	
	public RadioThermostatHardware(String host) {
		this.host = host;
	}
	
	private String url(String resource) {
		return "http://"+host+"/"+resource;
	}
	
	public String poll_model() throws IOException {
		return gson.fromJson(RadioThermostatUtil.get(url("tstat/model")), RadioThermostatModel.class).getModel();
	}
	
	public RadioThermostatStatus poll_status() throws IOException {
		RadioThermostatStatus status = gson.fromJson(RadioThermostatUtil.get(url("tstat")), RadioThermostatStatus.class);
		if(status.hold != 0) {
			// spec says "1.04 can return any non-zero value if override is enabled." 
			// which would be a pain for mapping. Force [0,1]. 
			status.hold = 1;
		}
		return status;
	}
	
	private void post(RadioThermostatStatus status) throws IOException {
		RadioThermostatUtil.post(url("tstat"), gson.toJson(status));
	}
	
	/**
	 * TMode: 0:OFF, 1:HEAT,2:COOL,3:AUTO
	 */
	public void post_tmode(Integer tmode) throws IOException {
		RadioThermostatStatus status = new RadioThermostatStatus();
		status.tmode = tmode;
		post(status);
	}
	
	/**
	 * FMode: 0:AUTO, 1:AUTO/CIRCULATE, 2:ON
	 */
	public void post_fmode(Integer fmode) throws IOException {
		RadioThermostatStatus status = new RadioThermostatStatus();
		status.fmode = fmode;
		post(status);
	}
	
	/**
	 * Temporary Target Cool
	 */
	public void post_it_cool(Double it_cool) throws IOException {
		RadioThermostatStatus status = new RadioThermostatStatus();
		status.it_cool = it_cool;
		post(status);
	}
	
	/**
	 * Temporary Target Cool And Mode
	 */
	public void post_t_cool(Double t_cool) throws IOException {
		RadioThermostatStatus status = new RadioThermostatStatus();
		status.t_cool = t_cool;
		post(status);
	}
	
	/**
	 * Temporary Target Heat
	 */
	public void post_it_heat(Double it_heat) throws IOException {
		RadioThermostatStatus status = new RadioThermostatStatus();
		status.it_heat = it_heat;
		post(status);
	}
	
	/**
	 * Temporary Target Heat And Mode
	 */
	public void post_t_heat(Double t_heat) throws IOException {
		RadioThermostatStatus status = new RadioThermostatStatus();
		status.t_heat = t_heat;
		post(status);
	}
	
	/**
	 * Absolute Target Cool
	 */
	public void post_a_cool(Double a_cool) throws IOException {
		RadioThermostatStatus status = new RadioThermostatStatus();
		status.a_cool = a_cool;
		post(status);
	}
	
	/**
	 * Absolute Target Heat
	 */
	public void post_a_heat(Double a_heat) throws IOException {
		RadioThermostatStatus status = new RadioThermostatStatus();
		status.a_heat = a_heat;
		post(status);
	}
	
	/**
	 * Absolute Target Mode: 0:Disable,1:Enable
	 */
	public void post_a_mode(Integer a_mode) throws IOException {
		RadioThermostatStatus status = new RadioThermostatStatus();
		status.a_mode = a_mode;
		post(status);
	}

	/**
	 * Target Humidity: Value is % relative humidity from 0 to 100%
	 */
	public void post_t_humidity(Double t_humidity) throws IOException {
		RadioThermostatStatus status = new RadioThermostatStatus();
		status.t_humidity = t_humidity;
		post(status);
	}
	
	/**
	 * Target Humidifier Mode: 0:Off,1:Run only with heat,2:Run any time (runs fan)
	 */
	public void post_humidifier_mode(Integer humidifier_mode) throws IOException {
		RadioThermostatStatus status = new RadioThermostatStatus();
		status.humidifier_mode = humidifier_mode;
		post(status);
	}
}
