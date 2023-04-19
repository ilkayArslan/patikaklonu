package com.patikadev.Helper;

import com.patikadev.View.LoginUI;

import javax.swing.*;
import java.awt.*;

public class Helper {

    public static double findScreenPoint(String eksen, Dimension size){
        double point = 0;
        switch (eksen){
            case "x":
                point =(Toolkit.getDefaultToolkit().getScreenSize().width - size.getWidth())/2;
                break;
            case  "y":
                point =(Toolkit.getDefaultToolkit().getScreenSize().height-size.height)/2;
                break;
        }
        return point;
    }

    public static void findLayout(){
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels() ){
            if ("Nimbus".equals(info.getName())){
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                         UnsupportedLookAndFeelException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public static boolean isEmpty(JTextField fld){
        return fld.getText().trim().isEmpty();
    }
    public static boolean isEmpty(JTextArea area){
        return area.getText().trim().isEmpty();
    }

    public static void  getFillFull(String dialog){
        switch (dialog){
            case "fill":
                JOptionPane.showMessageDialog(null,"Tüm boşlukları lütfen doldurun","Hata",1);
                break;
            case "done":
                JOptionPane.showMessageDialog(null,"İşlem başarıyla gerçekleşti","Onay",1);
                break;
            case "Another":
                JOptionPane.showMessageDialog(null,"Böyle bir kullanıcı adı mevcuttur","Hata",1);
                break;
            case"login":
                JOptionPane.showMessageDialog(null,"Hoşgeldiniz Sayın : "+LoginUI.getUser().getName(),"Giriş",1);
                break;
            case "wronglogin":
                JOptionPane.showMessageDialog(null,"Hatalı giriş yaptınız","Giriş Hatası",1);
                break;
            case "notyou":
                JOptionPane.showMessageDialog(null,"Bu soruyu siz değiştiremezsiniz","Kullanıcı Hatası",1);
                break;
            case "notequal":
                JOptionPane.showMessageDialog(null,"Şifreleriniz uyuşmuyor","Hatalı Şifre",1);
                break;

        }

    }
    public static void clearField(JTextField field){
        field.setText("");
    }
    public static boolean confirm(){
        return JOptionPane.showConfirmDialog(null,"Silmek istediğinize emin misiniz ?","Sil",JOptionPane.YES_NO_OPTION) ==0;
    }
}
