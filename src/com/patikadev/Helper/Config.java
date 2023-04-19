package com.patikadev.Helper;

public class Config {
    public static final String  PROJECT_TITLE = "Patika.Dev";
    public static final String DB_URL = "jdbc:postgresql://localhost/users";
    public static final String USER_NAME = "postgres";
    public static final String USER_PASSWORD = "05532733095iA";
    public static final  String SELECT_ALLUSER = "SELECT * FROM users";
    public static final  String ADD_USER = "INSERT INTO users(name,user_name,password,usertype) VALUES(?,?,?,?::user_type)";
    public static final  String CONTROL_USERNAME = "SELECT * FROM users WHERE user_name = ?";
    public static final String CONTROL_USER_ID = "SELECT * FROM users WHERE id = ?";
    public static final String CONTROL_USER_NAME = "SELECT * FROM users WHERE name = ?";

    public static final String REMOVE_USER_FROM_ID = "DELETE FROM users WHERE id = ?";
    public static final String UPDATE_USER_INFORMATION = "UPDATE users SET name = ?,user_name=?,password=?,usertype=?::user_type WHERE id = ?";
    public static final String SEARCH_USER = "SELECT * FROM users WHERE name LIKE '%{{name}}%' AND user_name LIKE '%{{username}}%'";
    public static final String SELECT_ALL_PATIKA = "SELECT * FROM patika";
    public static final String ADD_PATIKA = "INSERT INTO patika(name) VALUES(?)";
    public static final String UPTADE_PATIKA = "UPDATE patika SET name=? WHERE id =?";
    public static final String DELETE_PATIKA = "DELETE FROM patika WHERE id=?";
    public static final String CONTROL_PATIKA = "SELECT * FROM patika WHERE id=?";
    public static final String CONTROL_PATIKA_BY_NAME = "SELECT * FROM patika WHERE name=?";

    public static final String SELECT_ALL_COURSE = "SELECT * FROM course";
    public static final String ADD_COURSE = "INSERT INTO course(user_id,patika_id,name,language) VALUES(?,?,?,?)";
    public static final String REMOVE_COURSE_BY_USER = "DELETE  FROM course WHERE user_id = ?";
    public static final String REMOVE_COURSE_BY_PATIKA="DELETE  FROM course WHERE patika_id = ?";
    public static final  String CONTROL_COURSE = "SELECT * FROM course WHERE id = ?";
    public static final  String CONTROL_COURSE_BY_NAME = "SELECT * FROM course WHERE name = ?";
    public static final String CONTROL_COURSE_BY_NAME_AND_BY_EDU_ID="SELECT * FROM course WHERE name = ? AND  user_id=?";

    public static final String CONTROL_COURSE_BY_USER = "SELECT * FROM course WHERE user_id=?";
    public static final String CONTROL_COURSE_BY_PATH_NAME="SELECT * FROM course WHERE patika_id = ?";
    public static final String CONTOL_LOGIN_USER = "SELECT * FROM users WHERE user_name=? AND password=?";
    public static final String ALL_CONTENTS = "SELECT * FROM content";
    public static final String ADD_NEW_CONTENT = "INSERT INTO content(title,explain,link,course_id,user_id) VALUES(?,?,?,?,?)";
    public static final String REMOVE_CONTENT = "DELETE FROM content WHERE id = ?";
    public static final String UPDATE_CONTENT = "UPDATE content SET title=?,explain=?,link=? WHERE id =?";
    public static final String SELECT_ALL_QUESTION = "SELECT * FROM question";
    public static  final String CONTROL_CONTENT = "SELECT * FROM content WHERE id = ?";
    public static final String CONTROL_CONTENT_BY_COURSE_AND_EDU_ID="SELECT * FROM content WHERE course_id = ? AND user_id=?";
    public static final String  ADD_QUESTION = "INSERT INTO question(question,user_id,content_id) VALUES(?,?,?)";
    public static final String UPDATE_QESTION = "UPDATE question SET question =? WHERE id=?";
    public static final String DELETE_QUESTION="DELETE FROM question WHERE id = ?";
    public static final String SELECT_ATTEND_COURSE = "SELECT * FROM attend WHERE user_id = ?";
    public static final String ADD_ATTEND_COURSE="INSERT INTO attend(user_id,course_id,patika_id,edu_id,course_point) VALUES(?,?,?,?,?)";
    public static final String UPDATE_COURSE_POINT ="UPDATE attend SET course_point=? WHERE id=?";
    public static final String REMOVE_ATTEND_COURSE = "DELETE FROM attend WHERE id =?";

}
