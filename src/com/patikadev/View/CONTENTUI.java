package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Course;
import com.patikadev.Model.Educater;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CONTENTUI extends JFrame {
    private JPanel wrapper;
    private JLabel lbl_course_name;
    private JTable tbl_chs_list;

    private DefaultTableModel mdl_chs_list;
    private DefaultTableModel row_chs_list;
    public CONTENTUI(String course_name,String edu_name,String table){
        setContentPane(wrapper);
        setSize(500,500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int x = (int) Helper.findScreenPoint("x",getSize());
        int y = (int) Helper.findScreenPoint("y",getSize());
        setLocation(x,y);
        setTitle(Config.PROJECT_TITLE);
        lbl_course_name.setText(course_name);
        mdl_chs_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (row==0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };
        mdl_chs_list = new DefaultTableModel();
        mdl_chs_list.setColumnIdentifiers(new Object[]{"id","İçerik Adı","Açıklama","Link"});
        tbl_chs_list.setModel(mdl_chs_list);
        row_chs_list = (DefaultTableModel) tbl_chs_list.getModel();
        tbl_chs_list.getTableHeader().setReorderingAllowed(false);
        GetCourseContent(course_name,edu_name);
    }
    public  void GetCourseContent(String course_name,String edu_name){
        int course_id = Course.getFetckCourse(course_name).getId();
        int edu_id = Educater.getFetchbyNAME(edu_name).getId();
        int num=0;
        try {
            PreparedStatement pr = DBConnector.getCon().prepareStatement(Config.CONTROL_CONTENT_BY_COURSE_AND_EDU_ID);
            pr.setInt(1,course_id);
            pr.setInt(2,edu_id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                num++;
                row_chs_list.addRow(new Object[]{
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)
                });
            }
            if (num ==0)
                row_chs_list.addRow(new Object[]{"İçerik Eklenmedi"});
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
