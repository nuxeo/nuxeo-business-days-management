/*
 * (C) Copyright 2006-2010 Nuxeo SAS (http://nuxeo.com/) and contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     <a href="mailto:nulrich@nuxeo.com">Nicolas Ulrich</a>
 *
 */

package org.nuxeo.business.days.management.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.nuxeo.business.days.management.checker.HolidaysChecker;
import org.nuxeo.business.days.management.service.BusinessDaysService;
import org.nuxeo.runtime.model.ComponentInstance;
import org.nuxeo.runtime.model.DefaultComponent;

/**
 * @author Nicolas Ulrich
 *
 */
public class BusinessDaysServiceImpl extends DefaultComponent implements
BusinessDaysService {

    private final Map<String, Integer> values = new HashMap<String, Integer>();

    private HolidaysChecker check;

    public Date getLimitDate(String label, Date from) {

        if(!values.containsKey(label)){
            return null;
        }

        int duration = values.get(label);

        Calendar fromCalendar = GregorianCalendar.getInstance();
        fromCalendar.setTime(from);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        fromCalendar.set(Calendar.MINUTE, 0);                 // set minute in hour
        fromCalendar.set(Calendar.SECOND, 0);                 // set second in minute
        fromCalendar.set(Calendar.MILLISECOND, 0);

        for (int i = 0; i < duration; i++) {

            fromCalendar.add(Calendar.DAY_OF_YEAR, 1);

            // If this is a non working day, increase the limit
            if (isHolidayDay(fromCalendar)) {
                duration++;
            }

        }

        return fromCalendar.getTime();
    }

    private boolean isHolidayDay(Calendar day) {
        if (day.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                || day.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
                || (check != null && check.isHoliday(day.getTime())))
            return true;

        return false;

    }

    @Override
    public void registerContribution(Object contribution,
            String extensionPoint, ComponentInstance contributor) {

        if (extensionPoint.equals("duration")) {

            DurationDescriptor duration = ((DurationDescriptor) contribution);
            values.put(duration.label, duration.numberOfDays);

        } else if (extensionPoint.equals("holidaysChecker")) {

            HolidaysCheckerDescriptor distributionType = ((HolidaysCheckerDescriptor) contribution);
            try {
                check = (HolidaysChecker) Class.forName(
                        distributionType.clazz).newInstance();
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }

        }

    }

    @Override
    public void unregisterContribution(Object contribution,
            String extensionPoint, ComponentInstance contributor) {

        if (extensionPoint.equals("duration")) {

            DurationDescriptor duration = ((DurationDescriptor) contribution);
            values.remove(duration);

        }

    }

}