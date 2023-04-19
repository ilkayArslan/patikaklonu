package com.patikadev.Model;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Course {
    private int id;
    private int user_id;
    private int patika_id;
    private String name;
    private  String language;

    public Course(int id, int user_id, int patika_id, String name, String language) {
        this.id = id;
        this.user_id = user_id;
        this.patika_id = patika_id;
        this.name = name;
        this.language = language;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPatika_id() {
        return patika_id;
    }

    public void setPatika_id(int patika_id) {
        this.patika_id = patika_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
    public static void RemoveCourse(int user_id,String sql){
        try {
            PreparedStatement pr = DBConnector.getCon().prepareStatement(sql);
            pr.setInt(1,user_id);
            pr.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Course getFetckCourse(int id){
        Course c = null;
        try {
            PreparedStatement pr = DBConnector.getCon().prepareStatement(Config.CONTROL_COURSE);
            pr.setInt(1,id);
           ResultSet rs =  pr.executeQuery();
            while (rs.next()){
                c = new Course(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getString(5));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return c;
    }
    public static Course getFetckCourse(String name){
        Course c = null;
        try {
            PreparedStatement pr = DBConnector.getCon().prepareStatement(Config.CONTROL_COURSE_BY_NAME);
            pr.setString(1,name);
            ResultSet rs =  pr.executeQuery();
            while (rs.next()){
                c = new Course(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getString(5));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return c;
    }
    public static Course getFetckCourse(String name,int edu_id){
        Course c = null;
        try {
            PreparedStatement pr = DBConnector.getCon().prepareStatement(Config.CONTROL_COURSE_BY_NAME_AND_BY_EDU_ID);
            pr.setString(1,name);
            pr.setInt(2,edu_id);
            ResultSet rs =  pr.executeQuery();
            while (rs.next()){
                c = new Course(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getString(5));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return c;
    }

}
