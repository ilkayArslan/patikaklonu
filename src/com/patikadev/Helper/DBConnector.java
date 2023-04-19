package com.patikadev.Helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.patikadev.Helper.Config;

public class DBConnector {
    private  Connection connect = null;
    public Connection Connect(){
        try {
            connect = DriverManager.getConnection(Config.DB_URL,Config.USER_NAME,Config.USER_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connect;
    }

    public static Connection getCon(){
        DBConnector db = new DBConnector();
        return db.Connect();
    }
}
