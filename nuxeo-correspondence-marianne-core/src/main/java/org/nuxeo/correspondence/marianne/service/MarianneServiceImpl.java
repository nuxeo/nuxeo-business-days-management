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

package org.nuxeo.correspondence.marianne.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.nuxeo.correspondence.marianne.checker.MarianneHolidaysChecker;
import org.nuxeo.runtime.model.ComponentInstance;
import org.nuxeo.runtime.model.DefaultComponent;

/**
 * @author Nicolas Ulrich
 *
 */
public class MarianneServiceImpl extends DefaultComponent implements
        MarianneService {

    private final Map<String, Integer> values = new HashMap<String, Integer>();

    private MarianneHolidaysChecker check;

    public Date getLimitDate(String label, Date from) {

        Integer days = values.get(label);

        if (days == null) {
            return null;
        }

        Calendar fromCalendar = GregorianCalendar.getInstance();
        fromCalendar.setTime(from);
        fromCalendar.clear(Calendar.HOUR);
        fromCalendar.clear(Calendar.MINUTE);
        fromCalendar.clear(Calendar.MILLISECOND);

        for (int i = 0; i < days; i++) {

            fromCalendar.roll(Calendar.DATE, true);

            // If this is a non working day, increase the limit
            if (isHolidayDay(fromCalendar)) {
                days++;
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
            String extensionPoint, ComponentInstance contributor)
            throws Exception {

        if (extensionPoint.equals("limitDate")) {

            LimitDateDescriptor distributionType = ((LimitDateDescriptor) contribution);
            values.put(distributionType.label, distributionType.numberOfDays);

        } else if (extensionPoint.equals("holidaysChecker")) {

            HolidaysCheckDescriptor distributionType = ((HolidaysCheckDescriptor) contribution);
            check = (MarianneHolidaysChecker) Class.forName(
                    distributionType.name).newInstance();

        }

    }

    @Override
    public void unregisterContribution(Object contribution,
            String extensionPoint, ComponentInstance contributor) {

        LimitDateDescriptor distributionType = ((LimitDateDescriptor) contribution);
        values.remove(distributionType.label);

    }

}