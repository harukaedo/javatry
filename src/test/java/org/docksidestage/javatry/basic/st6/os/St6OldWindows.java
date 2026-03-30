package org.docksidestage.javatry.basic.st6.os;

import org.docksidestage.javatry.basic.st6.os.base.St6OperationSystem;

public class St6OldWindows extends St6OperationSystem {

    public St6OldWindows(String loginId) {
        super(loginId);
    }

    @Override
    protected String getFileSeparator() {
        return "\\";
    }

    @Override
    protected String getUserDirectory() {
        return "/Documents and Settings/" + loginId;
    }
}
