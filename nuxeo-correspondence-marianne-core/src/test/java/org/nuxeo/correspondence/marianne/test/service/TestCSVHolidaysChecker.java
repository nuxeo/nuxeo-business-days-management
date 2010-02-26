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

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.nuxeo.correspondence.marianne.checker.CSVHolidaysChecker;
import org.nuxeo.runtime.test.NXRuntimeTestCase;

/**
 * @author Nicolas Ulrich
 *
 */
public class TestCSVHolidaysChecker extends NXRuntimeTestCase {

    @Override
    public void setUp() throws Exception {

        super.setUp();

        deployBundle("org.nuxeo.correspondence.marianne.api");
        deployBundle("org.nuxeo.correspondence.marianne.core");
        deployBundle("org.nuxeo.correspondence.marianne.core.test");

    }

    public void testLabel() throws ParseException {

        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");

        CSVHolidaysChecker check = new CSVHolidaysChecker();
        assertTrue(check.isHoliday(formater.parse("12/01/2010")));
        assertFalse(check.isHoliday(formater.parse("13/01/2010")));
        assertTrue(check.isHoliday(formater.parse("14/01/2010")));

    }
}