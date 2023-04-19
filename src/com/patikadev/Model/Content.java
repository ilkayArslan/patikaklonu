package com.patikadev.Model;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Content {
    private int id;
    private String title;
    private String explain;
    private String link;
    private int course_id;
    private int user_id;

    public Content(int id, String title, String explain, String link, int course_id, int user_id) {
        this.id = id;
        this.title = title;
        this.explain = explain;
        this.link = link;
        this.course_id = course_id;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public static boolean AddContent(String title,String explain,String link,int course_id,int user_id){
        try {
            PreparedStatement pr = DBConnector.getCon().prepareStatement(Config.ADD_NEW_CONTENT);
            pr.setString(1,title);
            pr.setString(2,explain);
            pr.setString(3,link);
            pr.setInt(4,course_id);
            pr.setInt(5,user_id);
            pr.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }
    public static boolean RemoveContent(int id){
        try {
            PreparedStatement pr = DBConnector.getCon().prepareStatement(Config.REMOVE_CONTENT);
            pr.setInt(1,id);
            pr.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    public static Content getFetchContent(int id){
        Content con = null;
        try {
            PreparedStatement pr = DBConnector.getCon().prepareStatement(Config.CONTROL_CONTENT);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                con = new Content(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return con;
    } ;
}
