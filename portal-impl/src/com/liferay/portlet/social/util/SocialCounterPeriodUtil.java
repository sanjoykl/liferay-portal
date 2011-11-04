/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.social.util;

import com.liferay.ibm.icu.util.Calendar;
import com.liferay.ibm.icu.util.GregorianCalendar;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.util.PropsValues;

import java.util.Date;

/**
 * @author Zsolt Berentey
 */
public class SocialCounterPeriodUtil {

	public static int getActivityDay() {
		return getActivityDay(System.currentTimeMillis());
	}

	public static int getActivityDay(Calendar calendar) {
		Date date = calendar.getTime();

		return getActivityDay(date.getTime());
	}

	public static int getActivityDay(long time) {
		return (int)((time / Time.DAY) - (_BASE_TIME / Time.DAY));
	}

	public static Date getDate(int activityDay) {
		return new Date(_BASE_TIME + activityDay * Time.DAY);
	}

	public static int getEndPeriod() {
		if (_isWithinPeriod(_startPeriod, _endPeriod, getActivityDay())) {
			return _endPeriod;
		}

		_endPeriod = getStartPeriod() + getPeriodLength() - 1;

		return _endPeriod;
	}

	public static int getEndPeriod(int offset) {
		if (_isMonthlyPeriod()) {
			Calendar calendar = new GregorianCalendar();

			calendar.set(Calendar.DATE, 1);

			calendar.add(Calendar.MONTH, offset + 1);
			calendar.add(Calendar.DATE, -1);

			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);

			return getActivityDay(calendar);
		}

		return getEndPeriod() - offset * getPeriodLength();
	}

	public static int getEndPeriod(long time) {
		int activityDay = getActivityDay(time);

		int offset = 0;

		while (getStartPeriod(offset) > activityDay) {
			offset--;
		}

		return getEndPeriod(offset);
	}

	public static int getFirstActivityDayOfYear() {
		Calendar calendar = new GregorianCalendar();

		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return getActivityDay(calendar);
	}

	public static int getPeriodLength() {
		if (_isMonthlyPeriod()) {
			if (_isWithinPeriod(_startPeriod, _endPeriod, getActivityDay())) {
				return _periodLength;
			}

			Calendar calendar = new GregorianCalendar();

			calendar.set(Calendar.DATE, 1);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);

			_startPeriod = getActivityDay(calendar);

			_periodLength = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

			_endPeriod = _startPeriod + _periodLength - 1;

			return _periodLength;
		}

		if (_periodLength == 0) {
			_periodLength = GetterUtil.getInteger(
				PropsValues.SOCIAL_ACTIVITY_COUNTER_PERIOD_LENGTH);
		}

		return _periodLength;
	}

	public static int getStartPeriod() {
		if (_isWithinPeriod(_startPeriod, _endPeriod, getActivityDay())) {
			return _startPeriod;
		}

		if (_isMonthlyPeriod()) {
			Calendar calendar = new GregorianCalendar();

			calendar.set(Calendar.DATE, 1);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);

			_startPeriod = getActivityDay(calendar);

			return _startPeriod;
		}

		_startPeriod = getActivityDay() / getPeriodLength() * getPeriodLength();

		return _startPeriod;
	}

	public static int getStartPeriod(int offset) {
		if (_isMonthlyPeriod()) {
			Calendar calendar = new GregorianCalendar();

			calendar.set(Calendar.DATE, 1);

			calendar.add(Calendar.MONTH, offset);

			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);

			return getActivityDay(calendar.getTime().getTime());
		}

		return getStartPeriod() - offset * getPeriodLength();
	}

	public static int getStartPeriod(long time) {
		int activityDay = getActivityDay(time);

		int offset = 0;

		while (getStartPeriod(offset) > activityDay) {
			offset--;
		}

		return getStartPeriod(offset);
	}

	private static boolean _isMonthlyPeriod() {
		if (PropsValues.SOCIAL_ACTIVITY_COUNTER_PERIOD_LENGTH.equals("month")) {
			return true;
		}
		else {
			return false;
		}
	}

	private static boolean _isWithinPeriod(
		int startPeriod, int endPeriod, int activityDay) {

		if ((activityDay >= startPeriod) && (activityDay <= endPeriod)) {
			return true;
		}
		else {
			return false;
		}
	}

	private static final long _BASE_TIME =
		new GregorianCalendar(2011, Calendar.JANUARY, 1).getTimeInMillis();

	private static int _endPeriod;
	private static int _periodLength = -1;
	private static int _startPeriod;

}