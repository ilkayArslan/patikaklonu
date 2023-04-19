package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Attend;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GETPOINTUI extends JFrame{
    private int course_id;
    private JPanel wrapper;
    private JComboBox cmb_point;
    private JButton btn_pointed;
    public GETPOINTUI(int course_id){
        setContentPane(wrapper);
        setSize(250,250);
        setVisible(true);
        setTitle(Config.PROJECT_TITLE);
        int x = (int) Helper.findScreenPoint("x",getSize());
        int y = (int) Helper.findScreenPoint("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cmb_point.addItem("");
        cmb_point.addItem(1);
        cmb_point.addItem(2);
        cmb_point.addItem(3);
        cmb_point.addItem(4);
        cmb_point.addItem(5);

        btn_pointed.addActionListener(e -> {

            if (cmb_point.getSelectedItem()==""){
                Helper.getFillFull("fill");
            }else{
                Helper.getFillFull("done");
                Attend.UpdatePoint(course_id, (Integer) cmb_point.getSelectedItem());
                dispose();
            }
        });
    }
}
