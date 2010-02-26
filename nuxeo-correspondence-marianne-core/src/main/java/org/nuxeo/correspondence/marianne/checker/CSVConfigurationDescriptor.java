package org.nuxeo.correspondence.marianne.checker;

import org.nuxeo.common.xmap.annotation.XNode;
import org.nuxeo.common.xmap.annotation.XObject;

@XObject("csvFile")
public class CSVConfigurationDescriptor {

    @XNode("path")
    protected String csvFilePath;

    @XNode("embended")
    protected boolean embended;

}
