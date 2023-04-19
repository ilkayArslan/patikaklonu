package com.patikadev.Model;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Question {
    private int id;
    private String question;
    private int user_id;
    private int content_id;

    public Question(int id, String question, int user_id, int content_id) {
        this.id = id;
        this.question = question;
        this.user_id = user_id;
        this.content_id = content_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getContent_id() {
        return content_id;
    }

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }
    public static void addQue(String question,int user_id,int content_id){
        try {
            PreparedStatement pr = DBConnector.getCon().prepareStatement(Config.ADD_QUESTION);
            pr.setString(1,question);
            pr.setInt(2,user_id);
            pr.setInt(3,content_id);
            pr.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void updateQue(String question,int id){
        try {
            PreparedStatement pr = DBConnector.getCon().prepareStatement(Config.UPDATE_QESTION);
            pr.setString(1,question);
            pr.setInt(2,id);
            pr.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void removeQue(int id){
        if (Helper.confirm()){
            try {
                PreparedStatement pr = DBConnector.getCon().prepareStatement(Config.DELETE_QUESTION);
                pr.setInt(1,id);
                pr.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
