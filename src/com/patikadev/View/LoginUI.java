package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Educater;
import com.patikadev.Model.Operator;
import com.patikadev.Model.Student;
import com.patikadev.Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoginUI extends JFrame {
    private JPanel wrapper;
    private JTextField fld_login_user_name;
    private JPasswordField fld_login_user_pass;
    private JButton btn_new_log;
    private JButton btn_login;
    private static User user;

    public static User getUser() {
        return user;
    }

    public LoginUI(){
        setContentPane(wrapper);
        setVisible(true);
        setSize(300,300);
        setTitle(Config.PROJECT_TITLE);
        int x = (int) Helper.findScreenPoint("x",getSize());
        int y = (int) Helper.findScreenPoint("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        btn_login.addActionListener(e -> {
            if (Helper.isEmpty(fld_login_user_name)&&Helper.isEmpty(fld_login_user_pass)){
                Helper.getFillFull("fill");
            }else{
                this.user = User.getFetch(fld_login_user_name.getText().trim(),fld_login_user_pass.getText());
                if (this.user != null){
                    Helper.getFillFull("login");
                    switch (this.user.getUtype()){
                        case "Operator":
                            Operator op = new Operator(this.user.getId(),this.user.getName(),this.user.getUname(),this.user.getUpass(),this.user.getUtype());
                            OperatorUI operator = new OperatorUI(op);
                            break;
                        case "educater":
                            Educater ed = new Educater(this.user.getId(),this.user.getName(),this.user.getUname(),this.user.getUpass(),this.user.getUtype());
                            EducaterUI edu = new EducaterUI(ed);
                            break;
                        case "student":
                            Student st = new Student(this.user.getId(),this.user.getName(),this.user.getUname(),this.user.getUpass(),this.user.getUtype());
                            StudentUI stu = new StudentUI(st);
                            break;
                    }
                    dispose();
                }else{
                    Helper.getFillFull("wronglogin");
                    fld_login_user_name.setText("");
                    fld_login_user_pass.setText("");

                }

            }
        });
        btn_new_log.addActionListener(e -> {
            GETACCOUNTUI getaccountui= new GETACCOUNTUI();
            dispose();
        });
    }
}
