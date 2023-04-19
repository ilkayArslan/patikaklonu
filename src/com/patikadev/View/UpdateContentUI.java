package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.Helper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateContentUI extends JFrame{
    private JTextField fld_uptade_content;
    private JTextField fld_update_link;
    private JTextArea area_update_explain;
    private JButton btn_update_content;
    private JPanel wrapper;

    public UpdateContentUI(int id,String content,String link,String explain){
        setContentPane(wrapper);
        setSize(300,400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        int x = (int) Helper.findScreenPoint("x",getSize());
        int y = (int) Helper.findScreenPoint("y",getSize());
        setLocation(x,y);
        fld_uptade_content.setText(content);
        fld_update_link.setText(link);
        area_update_explain.setText(explain);

        btn_update_content.addActionListener(e -> {
            if (Helper.isEmpty(fld_uptade_content)||Helper.isEmpty(fld_update_link)||Helper.isEmpty(area_update_explain)){
                Helper.getFillFull("fill");
            }else{
                Helper.getFillFull("done");
                updatecontent(id,fld_uptade_content.getText(),fld_update_link.getText(),area_update_explain.getText());
                dispose();
            }
        });
    }
    public boolean updatecontent(int id,String content,String link,String explain){
        try {
            PreparedStatement pr = DBConnector.getCon().prepareStatement(Config.UPDATE_CONTENT);
            pr.setString(1,content);
            pr.setString(2,explain);
            pr.setString(3,link);
            pr.setInt(4,id);
            return pr.executeUpdate()!=-1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
