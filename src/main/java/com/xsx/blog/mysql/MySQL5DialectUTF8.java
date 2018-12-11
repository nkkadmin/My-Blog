package com.xsx.blog.mysql;

import org.hibernate.dialect.MySQLInnoDBDialect;


/**
 * 设置数据库编码
 */
public class MySQL5DialectUTF8 extends MySQLInnoDBDialect {

    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}
