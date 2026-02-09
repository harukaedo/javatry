package org.docksidestage.javatry.basic.st6.dbms;

/**
 * @author harukaedo
 */
public abstract class St6Dbms {
    public String buildPagingQuery(int pageSize, int pageNumber) {
        int offset = pageSize * (pageNumber - 1);
        return doBuildPagingQuery(offset, pageSize);
    }

    protected abstract String doBuildPagingQuery(int offset, int pageSize);
}
