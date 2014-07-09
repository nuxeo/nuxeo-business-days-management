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

package org.nuxeo.business.days.management.test.checker;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.nuxeo.business.days.management.checker.CSVHolidaysChecker;
import org.nuxeo.runtime.test.NXRuntimeTestCase;

/**
 * @author Nicolas Ulrich
 *
 */
public class TestCSVHolidaysChecker extends NXRuntimeTestCase {

    @Before
    public void setUp() throws Exception {

        super.setUp();

        deployBundle("org.nuxeo.business.days.management.api");
        deployBundle("org.nuxeo.business.days.management.core");
        deployBundle("org.nuxeo.business.days.management.core.test");

    }

    @Test
    public void testLabel() throws ParseException {

        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");

        CSVHolidaysChecker check = new CSVHolidaysChecker();
        assertTrue(check.isHoliday(formater.parse("12/01/2010")));
        assertFalse(check.isHoliday(formater.parse("13/01/2010")));
        assertTrue(check.isHoliday(formater.parse("14/01/2010")));

    }
}
