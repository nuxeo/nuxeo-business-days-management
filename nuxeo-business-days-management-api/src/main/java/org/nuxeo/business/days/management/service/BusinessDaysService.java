/*
 * (C) Copyright 2006-2010 Nuxeo SA (http://nuxeo.com/) and others.
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

import java.util.Date;

/**
 * @author Nicolas Ulrich
 */
public interface BusinessDaysService {

    /**
     * <p>
     * Return the limit date for the given label. The labels are declared using the extension point "limitDate" of the
     * component "org.nuxeo.business.days.management.BusinessDaysService".
     * </p>
     *
     * @param label
     * @param from
     * @return Return the limit date. If the label is unknow, return null.
     */
    Date getLimitDate(String label, Date from);

}
