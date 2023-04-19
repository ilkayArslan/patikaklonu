package com.patikadev.Model;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private int id;
    private String name;
    private String uname;
    private String upass;
    private String utype;

    public User(){}


    public User(int id , String name, String uname, String upass, String utype){
        this.id = id;
        this.name=name;
        this.uname=uname;
        this.upass = upass;
        this.utype = utype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpass() {
        return upass;
    }

    public void setUpass(String upass) {
        this.upass = upass;
    }

    public String getUtype() {
        return utype;
    }

    public void setUtype(String utype) {
        this.utype = utype;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static boolean Add(String name,String uname,String upass,String utype){
        try {
            PreparedStatement ps = DBConnector.getCon().prepareStatement(Config.ADD_USER);
            ps.setString(1,name);
            ps.setString(2,uname);
            ps.setString(3,upass);
            ps.setString(4,utype);
            return ps.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public  static User getFetch(String uname){
        User fetchuser ;
        try {
            PreparedStatement pr = DBConnector.getCon().prepareStatement(Config.CONTROL_USERNAME);
            pr.setString(1,uname);
           ResultSet rs = pr.executeQuery();
           if (rs.next()){
               fetchuser = new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
               return fetchuser;
           }else {
               return null;
           }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public  static User getFetch(String uname,String upass){
        User fetchuser;
        try {
            PreparedStatement pr = DBConnector.getCon().prepareStatement(Config.CONTOL_LOGIN_USER);
            pr.setString(1,uname);
            pr.setString(2,upass);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                fetchuser = new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
                return fetchuser;
            }else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public  static User getFetchbyid(String user_id){
        User fetchuser ;
        try {
            PreparedStatement pr = DBConnector.getCon().prepareStatement(Config.CONTROL_USER_ID);
            pr.setInt(1, Integer.parseInt(user_id));
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                fetchuser = new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
                return fetchuser;
            }else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public  static User getFetchbyNAME(String name){
        User fetchuser ;
        try {
            PreparedStatement pr = DBConnector.getCon().prepareStatement(Config.CONTROL_USER_NAME);
            pr.setString(1, name);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                fetchuser = new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
                return fetchuser;
            }else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static void removeUser(String id){
        try {
            PreparedStatement pr = DBConnector.getCon().prepareStatement(Config.REMOVE_USER_FROM_ID);
            pr.setInt(1, Integer.parseInt(id));
            pr.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static boolean uptadeUser(int id,String name,String uname,String upass,String utype){
        try {
            PreparedStatement pr = DBConnector.getCon().prepareStatement(Config.UPDATE_USER_INFORMATION);
            pr.setString(1,name);
            pr.setString(2,uname);
            pr.setString(3,upass);
            pr.setString(4,utype);
            pr.setInt(5,id);
            return pr.executeUpdate() !=-1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static String SearchUser(String name,String uname,String utype){
        String sql = Config.SEARCH_USER;
        sql = sql.replace("{{name}}",name);
        sql = sql.replace("{{username}}",uname);
        if (!utype.isEmpty()){
            sql += " AND usertype = '"+utype+"'";
        }
        return sql;
    }

}
