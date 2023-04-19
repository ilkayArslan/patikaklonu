package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

public class StudentUI extends JFrame{
    private JPanel wrapper;
    private JTabbedPane tabbedPane1;
    private JTable tbl_ptk_list;
    private JTable tbl_usercourse_list;
    private JComboBox cmb_chs_path_name;
    private JComboBox cmb_chsn_course_name;
    private JButton btn_add_attend;
    private JComboBox cmb_chsn_teacher_name;
    private JButton btn_exit;
    private JLabel lbl_welcome_stu;
    private DefaultTableModel mdl_ptk_list;
    private DefaultTableModel row_ptk_list;
    private DefaultTableModel mdl_usercourse_list;
    private DefaultTableModel row_usercourse_list;
    private Student student;
    public StudentUI(Student student){
        this.student=student;
        setContentPane(wrapper);
        setVisible(true);
        setSize(800,500);
        setTitle(Config.PROJECT_TITLE);
        int x = (int) Helper.findScreenPoint("x",getSize());
        int y = (int) Helper.findScreenPoint("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        lbl_welcome_stu.setText(student.getName());
        mdl_ptk_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column==0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };
        //Patika Listesi
        mdl_ptk_list = new DefaultTableModel();
        mdl_ptk_list.setColumnIdentifiers(new Object[]{"Id","Patika Adı"});
        tbl_ptk_list.setModel(mdl_ptk_list);
        row_ptk_list = (DefaultTableModel) tbl_ptk_list.getModel();
        getPatikas(Config.SELECT_ALL_PATIKA);
        //Ders Kayıt İşlemi----
        //Combobox selectedda action ekliyoruz
        cmb_chs_path_name.addActionListener(e -> {
            //HASHSET ILE ÇEKTIK DERSLER TEKRAR ETMEDİ
            getFetchCourseList(Patikas.FetchPatika((String) cmb_chs_path_name.getSelectedItem()).getId()); ;
        });
        cmb_chsn_course_name.addActionListener(e -> {
            getTeacherFetch((String) cmb_chsn_course_name.getSelectedItem());
        });
        //Ders Kayıt İşlemi Kısmı
        btn_add_attend.addActionListener(e -> {
            if (cmb_chsn_course_name.getSelectedItem()==""||cmb_chs_path_name.getSelectedItem() ==""||cmb_chsn_teacher_name.getSelectedItem()==""){
                Helper.getFillFull("fill");
            }else{
                Helper.getFillFull("done");
                int patika_id = Patikas.FetchPatika((String) cmb_chs_path_name.getSelectedItem()).getId();
                int edu_id = Educater.getFetchbyNAME((String) cmb_chsn_teacher_name.getSelectedItem()).getId();
                int course_id= Course.getFetckCourse((String) cmb_chsn_course_name.getSelectedItem(),edu_id).getId();
                Attend.AddAttend(student.getId(),course_id,patika_id,edu_id,0);
                GetAttendCourses(student.getId());
            }
        });

        //Kayıtlı Derslerin Listesi
        mdl_usercourse_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column==0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };
        mdl_usercourse_list = new DefaultTableModel();
        mdl_usercourse_list.setColumnIdentifiers(new Object[]{"Id","Ders Adı","Patika Adı","Öğretmen Adı","Ders Puanı"});
        tbl_usercourse_list.setModel(mdl_usercourse_list);
        row_usercourse_list = (DefaultTableModel) tbl_usercourse_list.getModel();
        tbl_usercourse_list.getTableHeader().setReorderingAllowed(false);
        GetAttendCourses(student.getId());
        //KAYITLARIN İÇERİKLERİNİ GÖRME VE PUANLAMA
        JPopupMenu contentMENU = new JPopupMenu();
        JMenuItem get_point = new JMenuItem("Dersi Puanla");
        JMenuItem get_content = new JMenuItem("İçerikleri Gör");
        JMenuItem remove = new JMenuItem("Kayıt Sil");
        contentMENU.add(get_point);
        contentMENU.add(get_content);
        contentMENU.add(remove);
        tbl_usercourse_list.setComponentPopupMenu(contentMENU);
        tbl_usercourse_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selectedrow = tbl_usercourse_list.rowAtPoint(point);
                tbl_usercourse_list.setRowSelectionInterval(selectedrow,selectedrow);
            }
        });
        //MENU Aksiyonlarını yapma
        get_point.addActionListener(e -> {
            int id = (int) tbl_usercourse_list.getValueAt(tbl_usercourse_list.getSelectedRow(),0);
            GETPOINTUI getpointui = new GETPOINTUI(id);
            getpointui.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    GetAttendCourses(student.getId());
                }
            });
        });
        //remove işlemi
        remove.addActionListener(e -> {
            if (Helper.confirm()){
                Attend.RemoveAttendCourse((Integer) tbl_usercourse_list.getValueAt(tbl_usercourse_list.getSelectedRow(),0));
                GetAttendCourses(student.getId());
            }
        });
        //Content işlemlerini yerine getirme
        get_content.addActionListener(e -> {
            String crs_name = (String) tbl_usercourse_list.getValueAt(tbl_usercourse_list.getSelectedRow(),1);
            String edu_name =(String) tbl_usercourse_list.getValueAt(tbl_usercourse_list.getSelectedRow(),3);
            CONTENTUI contentui = new CONTENTUI(crs_name,edu_name,"content");
        });

        btn_exit.addActionListener(e -> {
                dispose();
                LoginUI newlog = new LoginUI();
        });
    }
    public void getPatikas(String sql){
        DefaultTableModel tbl_clr_mod = (DefaultTableModel) tbl_ptk_list.getModel();
        tbl_clr_mod.setRowCount(0);

        try {
            Statement st = DBConnector.getCon().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                row_ptk_list.addRow(new Object[]{
                        rs.getInt(1),
                        rs.getString(2)
                });
                Patikas path = new Patikas(rs.getInt(1),rs.getString(2));
                cmb_chs_path_name.addItem(path.getName());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void GetAttendCourses(int stu_id){
        DefaultTableModel clr_usercourse_list = (DefaultTableModel) tbl_usercourse_list.getModel();
        clr_usercourse_list.setRowCount(0);
        try {
            PreparedStatement pr = DBConnector.getCon().prepareStatement(Config.SELECT_ATTEND_COURSE);
            pr.setInt(1,stu_id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                row_usercourse_list.addRow(new Object[]{
                        rs.getInt(1),
                        Course.getFetckCourse(rs.getInt(3)).getName(),
                        Patikas.FetchPatika(rs.getInt(4)).getName(),
                        Educater.getFetchbyid(rs.getString(5)).getName(),
                        rs.getInt(6)
                });
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public  void getFetchCourseList(int path_id){
        cmb_chsn_course_name.removeAllItems();
        HashSet<String> arr = new HashSet<>();
        try {
            PreparedStatement pr = DBConnector.getCon().prepareStatement(Config.CONTROL_COURSE_BY_PATH_NAME);
            pr.setInt(1,path_id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                arr.add(rs.getString(4));
            }
            for (String a:arr){
                cmb_chsn_course_name.addItem(a);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void getTeacherFetch(String c_name){
        cmb_chsn_teacher_name.removeAllItems();
        try {
            PreparedStatement pr = DBConnector.getCon().prepareStatement(Config.CONTROL_COURSE_BY_NAME);
            pr.setString(1,c_name);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                cmb_chsn_teacher_name.addItem(User.getFetchbyid(String.valueOf(rs.getInt(2))).getName() );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
