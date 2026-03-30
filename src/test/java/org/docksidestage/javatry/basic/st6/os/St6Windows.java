package org.docksidestage.javatry.basic.st6.os;

import org.docksidestage.javatry.basic.st6.os.base.St6OperationSystem;

public class St6Windows extends St6OperationSystem {

    public St6Windows(String loginId) {
        super(loginId);
    }

    @Override
    protected String getFileSeparator() {
        return "\\";
    }

    @Override
    protected String getUserDirectory() {
        return "/Users/" + loginId;
    }
}
