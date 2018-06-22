package com.hoping.ESEARCH.utils.mysqlUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author YKL on 2018/6/19.
 * @version 1.0
 * @Description:
 * @spark: 梦想开始的地方
 */
public class MysqlUtil {

    private static Connection connection;
    private static Statement statement;

    public static Statement getStatement() throws ClassNotFoundException, SQLException {

        /** mysql连接参数 **/
        String URL = "jdbc:mysql://123.206.67.100:3306/xiyuanfinal?useUnicode=true&amp&characterEncoding=utf-8";
        String USER = "xiyuan";
        String PASSWORD = "xiyuan@XIYUAN.2o18";

        /** 加载驱动程序 **/
        Class.forName("com.mysql.jdbc.Driver");
        /** 获得数据库链接 **/
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
        /** 通过数据库的连接操作数据库，实现增删改查（使用Statement类） **/
        statement = connection.createStatement();

        return statement;
    }

    public static void close() throws SQLException {
        connection.close();
        statement.close();
    }


}
