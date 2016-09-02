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
public class RadioThermostatStatus {
	// Temperature
	public Double temp;
	
	// Thermostat Mode: 0:OFF, 1:HEAT,2:COOL,3:AUTO
	public Integer tmode;
	
	// Fan Mode: 0:AUTO, 1:AUTO/CIRCULATE, 2:ON
	public Integer fmode;
	
	// Override
	public Integer override;
	
	// Hold
	public Integer hold;
	
	// Temporary Target Cool And Mode
	public Double t_cool;
	
	// Temporary Target Heat and Mode
	public Double t_heat;
	
	// Temporary Target Cool
	public Double it_cool;
	
	// Temporary Target Heat
	public Double it_heat;
	
	// Absolute Target Cool
	public Double a_cool;
	
	// Absolute Target Heat
	public Double a_heat;
	
	// Absolute Target Mode
	public Integer a_mode;
	
	// Thermostat State: 0:OFF, 1:HEAT, 2:COOL
	public Integer tstate;
	
	// Fan State: 0:OFF, 1:ON
	public Integer fstate;
	
	// Time
	public RadioThermostatTime time;

	@Override
	public String toString() {
		return "Status [temp=" + temp + ", tmode=" + tmode + ", fmode=" + fmode + ", override=" + override + ", hold="
				+ hold + ", t_cool=" + t_cool + ", t_heat=" + t_heat + ", it_cool=" + it_cool + ", it_heat=" + it_heat
				+ ", a_cool=" + a_cool + ", a_heat=" + a_heat + ", a_mode=" + a_mode + ", tstate=" + tstate
				+ ", fstate=" + fstate + ", time=" + time + "]";
	}
	
}
