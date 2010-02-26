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

package org.nuxeo.correspondence.marianne.test.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.nuxeo.correspondence.marianne.service.MarianneService;
import org.nuxeo.runtime.api.Framework;
import org.nuxeo.runtime.test.NXRuntimeTestCase;

/**
 * @author Nicolas Ulrich
 *
 */
public class TestMarianneService extends NXRuntimeTestCase {

    @Override
    public void setUp() throws Exception {

        super.setUp();

        deployBundle("org.nuxeo.correspondence.marianne.api");
        deployBundle("org.nuxeo.correspondence.marianne.core");
        deployBundle("org.nuxeo.correspondence.marianne.core.test");

    }

    public void testLabel() {

        MarianneService ms = Framework.getLocalService(MarianneService.class);
        assertNotNull(ms);

        Date currentdate = GregorianCalendar.getInstance().getTime();

        assertNotNull(ms.getLimitDate("courriel", currentdate));
        assertNull(ms.getLimitDate("fakeLabel", currentdate));

    }

    /**
     * Test a full week
     */
    public void testLimiteDate() {

        assertEquals(8, getLimit(1));
        assertEquals(8, getLimit(2));
        assertEquals(8, getLimit(3));
        assertEquals(11, getLimit(4));
        assertEquals(13, getLimit(5));
        assertEquals(15, getLimit(6));
        assertEquals(18, getLimit(7));
        assertEquals(19, getLimit(8));

    }

    /**
     * @param dayOfMonth
     * @return
     */
    private int getLimit(int dayOfMonth) {

        MarianneService ms = Framework.getLocalService(MarianneService.class);

        Calendar startCalendar = GregorianCalendar.getInstance();
        startCalendar.set(2010, Calendar.JANUARY, dayOfMonth, 0, 0, 0);

        Date limitDate = ms.getLimitDate("courriel", startCalendar.getTime());

        Calendar limitCalendar = GregorianCalendar.getInstance();
        limitCalendar.setTime(limitDate);

        return limitCalendar.get(Calendar.DAY_OF_MONTH);

    }
}