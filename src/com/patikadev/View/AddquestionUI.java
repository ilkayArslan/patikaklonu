package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Question;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddquestionUI extends JFrame {
    private JPanel wrapper;
    private JTextArea area_add_que;
    private JButton btn_add_que;
    private JLabel lbl_header_que;

    public AddquestionUI(int user_id,int content_id){
        setContentPane(wrapper);
        setSize(400,300);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        int x = (int) Helper.findScreenPoint("x",getSize());
        int y = (int) Helper.findScreenPoint("y",getSize());
        setLocation(x,y);
        btn_add_que.addActionListener(e -> {
            if (Helper.isEmpty(area_add_que)){
                Helper.getFillFull("fill");
            }else{
                Helper.getFillFull("done");
                Question.addQue(area_add_que.getText(),user_id,content_id);
                dispose();
            }
        });
    }
    public AddquestionUI(int q_id,String question){
        setContentPane(wrapper);
        setSize(400,300);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        int x = (int) Helper.findScreenPoint("x",getSize());
        int y = (int) Helper.findScreenPoint("y",getSize());
        setLocation(x,y);
        lbl_header_que.setText("Soru Güncelleme Alanı");
        area_add_que.setText(question);
        btn_add_que.setText("Güncelle");
        btn_add_que.addActionListener(e -> {
            if (Helper.isEmpty(area_add_que)){
                Helper.getFillFull("fill");
            }else{
                Helper.getFillFull("done");
                Question.updateQue(area_add_que.getText(),q_id);
                dispose();
            }
        });
    }
}
