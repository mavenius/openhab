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
public class RadioThermostatTime {
	private int day;
	private int hour;
	private int minute;

	/**
	 * Day of week: 0:Monday, 1:Tuesday, 2:Wednesday, 3:Thursday, 4:Friday,
	 * 5:Saturday, 6:Sunday
	 * 
	 * @return Day of Week
	 */
	public int getDay() {
		return day;
	}

	/**
	 * Hour of day:  24-hour format
	 * 
	 * @return Hour of day
	 */
	public int getHour() {
		return hour;
	}

	/**
	 * Minute of hour
	 * 
	 * @return Minute of hour
	 */
	public int getMinute() {
		return minute;
	}

	@Override
	public String toString() {
		String dayOfWeek;
		switch (day) {
		case 0:
			dayOfWeek = "Monday";
			break;
		case 1:
			dayOfWeek = "Tuesday";
			break;
		case 2:
			dayOfWeek = "Wednesday";
			break;
		case 3:
			dayOfWeek = "Thursday";
			break;
		case 4:
			dayOfWeek = "Friday";
			break;
		case 5:
			dayOfWeek = "Saturday";
			break;
		case 6:
			dayOfWeek = "Sunday";
			break;
		default:
			dayOfWeek = "Unknown";
			break;
		}
		return dayOfWeek + " " + hour + ":" + minute;
	}

}
