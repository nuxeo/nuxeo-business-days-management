package org.nuxeo.correspondence.marianne.service;

import org.nuxeo.common.xmap.annotation.XNode;
import org.nuxeo.common.xmap.annotation.XObject;

@XObject("limitDate")
public class LimitDateDescriptor {

    @XNode("label")
    protected String label;

    @XNode("numberOfDays")
    protected int numberOfDays;

}
