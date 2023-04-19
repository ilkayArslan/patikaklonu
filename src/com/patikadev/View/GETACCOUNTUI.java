package com.patikadev.View;

import com.patikadev.Helper.Helper;
import com.patikadev.Model.Student;
import com.patikadev.Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GETACCOUNTUI extends JFrame {
    private JPanel wrapper;
    private JTextField fld_user_name;
    private JTextField fls_user_username;
    private JPasswordField fld_password_1;
    private JPasswordField fld_password_2;
    private JButton btn_add_stu;
    public GETACCOUNTUI(){
        setContentPane(wrapper);
        setSize(400,600);
        setVisible(true);
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width-getSize().width)/2,(Toolkit.getDefaultToolkit().getScreenSize().height-getHeight())/2);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        btn_add_stu.addActionListener(e -> {
            if (Helper.isEmpty(fld_user_name)||Helper.isEmpty(fld_user_name)||Helper.isEmpty(fld_password_1)||Helper.isEmpty(fld_password_2)){
                Helper.getFillFull("fill");
            }else{
               String pass = String.valueOf(fld_password_1.getPassword());
               String pass2 = String.valueOf(fld_password_2.getPassword());
               String uname = fls_user_username.getText();
                if (pass.equals(pass2)){
                    if(User.getFetch(uname)!=null){
                        Helper.getFillFull("Another");
                    }else{
                        User.Add(fld_user_name.getText(),fls_user_username.getText(),pass,"student");
                        User user = null;
                        user = User.getFetch(fls_user_username.getText(),pass);
                        Student stu = new Student(user.getId(), user.getName(),user.getUname(),user.getUpass(),user.getUtype());
                        StudentUI studentUI = new StudentUI(stu);
                        dispose();
                    }

                }else{
                    Helper.getFillFull("notequal");
                }
            }
        });
    }
}
