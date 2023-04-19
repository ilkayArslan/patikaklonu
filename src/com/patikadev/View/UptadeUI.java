package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.Helper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UptadeUI extends JFrame {
    private JPanel wrapper;
    private JLabel lbl_ptk_uptade_name;
    private JTextField fld_uptd_ptk_name;
    private JButton btn_uptade_ptk;
    private JLabel lbl_header;


    public UptadeUI(String name,int id){
        setContentPane(wrapper);
        setSize(250,250);
        setVisible(true);
        int x = (int) Helper.findScreenPoint("x",getSize());
        int y = (int) Helper.findScreenPoint("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        fld_uptd_ptk_name.setText(name);

        btn_uptade_ptk.addActionListener(e -> {
            String new_name = fld_uptd_ptk_name.getText();
           if (uptadepatika(new_name,id)){
               dispose();
           }
        });

    }
    public boolean uptadepatika(String new_name,int id){
        try {
            PreparedStatement pr = DBConnector.getCon().prepareStatement(Config.UPTADE_PATIKA);
            pr.setString(1,new_name);
            pr.setInt(2,id);
           return pr.executeUpdate() !=-1;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


}
