package com.patikadev.Model;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Patikas {
    private int id;
    private String name;

    public Patikas(int id, String name) {
        this.id = id;
        this.name = name;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static boolean AddPatika(String pname){
        try {
            PreparedStatement pr = DBConnector.getCon().prepareStatement(Config.ADD_PATIKA);
            pr.setString(1,pname);
            return pr.executeUpdate() !=-1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static Patikas FetchPatika(int id){
        Patikas ptk = null;
        try {
            PreparedStatement pr = DBConnector.getCon().prepareStatement(Config.CONTROL_PATIKA);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
               ptk= new Patikas(rs.getInt(1),rs.getString(2));
            }
            return ptk;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Patikas FetchPatika(String name){
        Patikas ptk = null;
        try {
            PreparedStatement pr = DBConnector.getCon().prepareStatement(Config.CONTROL_PATIKA_BY_NAME);
            pr.setString(1,name);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                ptk= new Patikas(rs.getInt(1),rs.getString(2));
            }
            return ptk;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
