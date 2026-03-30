package org.docksidestage.javatry.basic.st6.os;

import org.docksidestage.javatry.basic.st6.os.base.St6OperationSystem;

public class St6Mac extends St6OperationSystem {

    public St6Mac(String loginId) {
        super(loginId);
    }

    @Override
    protected String getFileSeparator() {
        return "/";
    }

    @Override
    protected String getUserDirectory() {
        return "/Users/" + loginId;
    }
}
