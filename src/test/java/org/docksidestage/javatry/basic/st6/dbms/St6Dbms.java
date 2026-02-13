package org.docksidestage.javatry.basic.st6.dbms;

// #1on1: Sql じゃなくて DatabaseManagementSystem にしてるのGood (2026/02/13)
// DBMSとは？ → 「データベースのソフトウェア (System)」を実現した具体的な製品 (という概念)
// MySQLとは？ → 「データベースのソフトウェア (System)」を実現した具体的な製品の一つ
// SQLとは？ → DBMS上で使われるデータを検索する言語
// 
// MySQL(OSS, Oracle)
// PostgreSQL(OSS, Community)
// Oracle(有料, Oracle)
// DB2(有料, IBM)
// SQLServer(有料, Microsoft)
//
// DatabaseじゃなくてDBMSな理由の話。
// さらに、リレーショナルデータベースだけに絞る場合は、RDBMS
// (DBMSだけだと、厳密には、Redis とか DynamoDB とかRDBじゃないDBも含まれるので)
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
