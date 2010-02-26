package org.nuxeo.correspondence.marianne.service;

import org.nuxeo.common.xmap.annotation.XNode;
import org.nuxeo.common.xmap.annotation.XObject;

@XObject("holidaysChecker")
public class HolidaysCheckDescriptor {

    @XNode("name")
    protected String name;

}
