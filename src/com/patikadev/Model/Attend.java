package com.patikadev.Model;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Attend {
    private int id;
    private int user_id;
    private int course_id;
    private int patika_id;
    private int edu_id;
    private int course_point;

    public Attend(int id, int user_id, int course_id, int patika_id, int edu_id, int course_point) {
        this.id = id;
        this.user_id = user_id;
        this.course_id = course_id;
        this.patika_id = patika_id;
        this.edu_id = edu_id;
        this.course_point = course_point;
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

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getPatika_id() {
        return patika_id;
    }

    public void setPatika_id(int patika_id) {
        this.patika_id = patika_id;
    }

    public int getEdu_id() {
        return edu_id;
    }

    public void setEdu_id(int edu_id) {
        this.edu_id = edu_id;
    }

    public int getCourse_point() {
        return course_point;
    }

    public void setCourse_point(int course_point) {
        this.course_point = course_point;
    }
    public static boolean AddAttend(int user_id,int course_id,int patika_id, int edu_id,int course_point){
        try {
            PreparedStatement pr = DBConnector.getCon().prepareStatement(Config.ADD_ATTEND_COURSE);
            pr.setInt(1,user_id);
            pr.setInt(2,course_id);
            pr.setInt(3,patika_id);
            pr.setInt(4,edu_id);
            pr.setInt(5,course_point);
            pr.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    public static void UpdatePoint(int id,int course_point){
        try {
            PreparedStatement pr = DBConnector.getCon().prepareStatement(Config.UPDATE_COURSE_POINT);
            pr.setInt(1,course_point);
            pr.setInt(2,id);
            pr.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void  RemoveAttendCourse(int id){
        try {

            PreparedStatement pr = DBConnector.getCon().prepareStatement(Config.REMOVE_ATTEND_COURSE);
            pr.setInt(1,id);
            pr.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
