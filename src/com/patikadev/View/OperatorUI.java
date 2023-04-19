package com.patikadev.View;

import com.patikadev.Helper.*;
import com.patikadev.Model.Course;
import com.patikadev.Model.Operator;
import com.patikadev.Model.Patikas;
import com.patikadev.Model.User;


import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OperatorUI extends JFrame {

    private JPanel wrapper;
    private JTabbedPane ope_menu;
    private JPanel list_users;
    private JPanel top_pnl;
    private JLabel lbl_welcm;
    private JButton btn_exit;
    private JTable tbl_user_list;
    private JScrollBar scrollBar1;
    private JLabel lbl_name;
    private JTextField fld_name;
    private JLabel lbl_uname;
    private JTextField fld_uname;
    private JLabel lbl_upassword;
    private JPasswordField fld_password;
    private JLabel lbl_type;
    private JComboBox cmb_utype;
    private JButton btn_add_user;
    private JLabel lbl_id;
    private JTextField fld_user_id;
    private JButton btn_remove;
    private JLabel lbl_sh_name;
    private JTextField fld_sh_name;
    private JLabel lbl_sh_uname;
    private JTextField fld_sh_uname;
    private JLabel lbl_sh_type;
    private JComboBox cmb_sh_type;
    private JButton btn_sh;
    private JPanel pnl_patika_list;
    private JTable tbl_ptk_list;
    private JScrollBar scrollBar2;
    private JLabel lbl_ptk_name;
    private JTextField fld_ptk_name;
    private JButton btn_add_patika;
    private JTable tbl_crs_list;
    private JTextField fld_crs_name;
    private JTextField fld_lang_name;
    private JComboBox cmb_path_name;
    private JComboBox cmb_tchr_name;
    private JButton btn_add_course;
    private DefaultTableModel mdl_usel_list ;

    //ekleme yapmak için row oluşturup tablemodelini.getModel ile alıp rowa veriyoruz Önemli bu
    private DefaultTableModel row_user_list;

    private DefaultTableModel mdl_course_list;
    private DefaultTableModel row_course_list;

    private Operator operator;
    private DefaultTableModel mdl_patika_list;
    private DefaultTableModel row_patika_list;
    private JPopupMenu patikaMenu;

    public OperatorUI(Operator operator){
        this.operator = operator;
        makeTurkish();
        setContentPane(wrapper);
        setSize(700,500);
        setVisible(true);
        int x = (int) Helper.findScreenPoint("x",getSize());
        int y = (int) Helper.findScreenPoint("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        lbl_welcm.setText("Hoşgeldiniz " + operator.getUname());
        // USER TABLOSU OLUŞTURDUĞUMUZ BÖLÜM
        mdl_usel_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column==0)
                    return false;
                return super.isCellEditable(row, column);

            }
        };
        mdl_usel_list.setColumnIdentifiers(new Object[]{"id", "name","uname","upassword","utype"});
        tbl_user_list.setModel(mdl_usel_list);
        row_user_list = (DefaultTableModel) tbl_user_list.getModel();
        tbl_user_list.getTableHeader().setReorderingAllowed(false);


        //Tüm tabloya yerleştirme olayımız
        getDBConnection();

        //Tablodan verileri seçme işlemimiz
        tbl_user_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_user_id =(String) tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),0);
                fld_user_id.setText(selected_user_id);
            }catch (Exception exep){

            }

        });

        //Tablodan veri güncelleme işlemi

        tbl_user_list.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE){
                int id = Integer.parseInt(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),0).toString());
                String name = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),1).toString();
                String uname = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),2).toString();
                String upass = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),3).toString();
                String utype = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),4).toString();
                if (User.getFetch(uname)!=null&&User.getFetch(uname).getId()!=id){
                    Helper.getFillFull("Another");
                } else{
                    User.uptadeUser(id,name,uname,upass,utype);
                    Helper.getFillFull("done");

                }
                getDBConnection();
            }
        });

        // PATİKA TABLOSU OLUŞTURDUĞUMUZ BÖLÜM

        mdl_patika_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column==0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };
        mdl_patika_list.setColumnIdentifiers(new Object[]{"id","patika"});
        tbl_ptk_list.setModel(mdl_patika_list);
        row_patika_list = (DefaultTableModel) tbl_ptk_list.getModel();
        tbl_ptk_list.getTableHeader().setReorderingAllowed(false);
        tbl_ptk_list.getColumnModel().getColumn(0).setMaxWidth(60);
        getDBConnection(Config.SELECT_ALL_PATIKA);



        btn_add_user.addActionListener(e -> {
            if (Helper.isEmpty(fld_name) || Helper.isEmpty(fld_uname) || Helper.isEmpty(fld_password) || cmb_utype.getSelectedIndex() ==0){
               Helper.getFillFull("fill");
            }else {
                if (User.getFetch(fld_uname.getText())!=null){
                    Helper.getFillFull("Another");
                }else{
                    User.Add(fld_name.getText(),fld_uname.getText(),fld_password.getText(), cmb_utype.getSelectedItem().toString());
                    Helper.getFillFull("done");
                    getDBConnection();
                    Helper.clearField(fld_name);
                    Helper.clearField(fld_uname);
                    Helper.clearField(fld_password);
                    cmb_utype.setSelectedIndex(0);
                }

            }

        });

        patikaMenu = new JPopupMenu();
        JMenuItem uptadeMenu = new JMenuItem("Güncelle");
        JMenuItem removeMenu = new JMenuItem("Sil");
        patikaMenu.add(uptadeMenu);
        patikaMenu.add(removeMenu);
        tbl_ptk_list.setComponentPopupMenu(patikaMenu);
        tbl_ptk_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selectedrow = tbl_ptk_list.rowAtPoint(point);
                tbl_ptk_list.setRowSelectionInterval(selectedrow,selectedrow);
            }
        });

        uptadeMenu.addActionListener(e -> {
            String name = tbl_ptk_list.getValueAt(tbl_ptk_list.getSelectedRow(),1).toString();
            int id = (int) tbl_ptk_list.getValueAt(tbl_ptk_list.getSelectedRow(),0);
            UptadeUI uptade = new UptadeUI(name,id);
            uptade.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    getDBConnection(Config.SELECT_ALL_PATIKA);
                    getDBConnectionforCOURSE();
                }
            });

        });

        removeMenu.addActionListener(e -> {
            int id = (int) tbl_ptk_list.getValueAt(tbl_ptk_list.getSelectedRow(),0);
            if (confirm()){
                PreparedStatement pr = null;
                try {
                    Course.RemoveCourse(id,Config.REMOVE_COURSE_BY_PATIKA);
                    pr = DBConnector.getCon().prepareStatement(Config.DELETE_PATIKA);
                    pr.setInt(1,id);
                    pr.executeUpdate();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                getDBConnection(Config.SELECT_ALL_PATIKA);
                getDBConnectionforCOURSE();
            }
        });

        //DERSLER TABLOSUNUN OLUŞTURULDUĞU BÖLÜM
        mdl_course_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column==0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };
        mdl_course_list.setColumnIdentifiers(new Object[]{"id","Ders Adı","Kullandığı Dil","Eğitmen Adı","Patika Adı"});
        tbl_crs_list.setModel(mdl_course_list);
        row_course_list = (DefaultTableModel) tbl_crs_list.getModel();
        tbl_crs_list.getTableHeader().setReorderingAllowed(false);
        tbl_crs_list.getColumnModel().getColumn(0).setMaxWidth(60);
        getDBConnectionforCOURSE();

        //user silme işlemi ayrıca dersleride siliyor eğitimciyi silersek
        btn_remove.addActionListener(e -> {
            if (Helper.isEmpty(fld_user_id)){
                Helper.getFillFull("fill");
            }else{
                if (confirm()){
                    User.removeUser(fld_user_id.getText());
                    Course.RemoveCourse(Integer.parseInt(fld_user_id.getText()),Config.REMOVE_COURSE_BY_USER);
                    Helper.getFillFull("done");
                    getDBConnection();
                    getDBConnectionforCOURSE();
                    fld_user_id.setText(null);
                }
            }
        });
        btn_sh.addActionListener(e -> {
            DefaultTableModel tbl_clr_mod = (DefaultTableModel) tbl_user_list.getModel();
            tbl_clr_mod.setRowCount(0);
            String sh_name = fld_sh_name.getText();
            String sh_uname = fld_sh_uname.getText();
            String sh_utype = cmb_sh_type.getSelectedItem().toString();
            String sql = User.SearchUser(sh_name,sh_uname,sh_utype);
            try {
                Statement st = DBConnector.getCon().createStatement();
                ResultSet data = st.executeQuery(sql);
                while (data.next()){
                    row_user_list.addRow(new Object[]{data.getString(1),
                            data.getString(2),
                            data.getString(3),
                            data.getString(4),
                            data.getString(5)}
                    );
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            fld_sh_name.setText("");
            fld_sh_uname.setText("");
            cmb_sh_type.setSelectedIndex(0);
        });
        btn_exit.addActionListener(e -> {
            dispose();
            LoginUI newlog = new LoginUI();
        });
        btn_add_patika.addActionListener(e -> {
            String get_patika_name = fld_ptk_name.getText();
            if (Helper.isEmpty(fld_ptk_name)){
                Helper.getFillFull("fill");
            }else{
                Helper.getFillFull("done");
                Patikas.AddPatika(get_patika_name);
                fld_ptk_name.setText("");
            }
            getDBConnection(Config.SELECT_ALL_PATIKA);
        });
        //DERSLERE EKLEME YAPMA ALANI
        btn_add_course.addActionListener(e -> {
            try {
                if (Helper.isEmpty(fld_crs_name)&&Helper.isEmpty(fld_lang_name)&&cmb_tchr_name.getSelectedItem()==""&& cmb_path_name.getSelectedItem()==""){
                    Helper.getFillFull("fill");
                }else{
                    Helper.getFillFull("done");
                    PreparedStatement pr = DBConnector.getCon().prepareStatement(Config.ADD_COURSE);
                    pr.setInt(1,User.getFetchbyNAME((String) cmb_tchr_name.getSelectedItem()).getId());
                    pr.setInt(2,Patikas.FetchPatika((String) cmb_path_name.getSelectedItem()).getId());
                    pr.setString(3,fld_crs_name.getText());
                    pr.setString(4,fld_lang_name.getText());
                    pr.executeUpdate();
                    getDBConnectionforCOURSE();
                    fld_crs_name.setText("");
                    fld_lang_name.setText("");
                    cmb_path_name.setSelectedIndex(0);
                    cmb_tchr_name.setSelectedIndex(0);
                }

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        //DERSLERE POPUP MENU EKLEME

        JPopupMenu course_menu = new JPopupMenu();
        JMenuItem update_tname = new JMenuItem("Öğretmen güncelle");
        JMenuItem update_pname = new JMenuItem("Patika güncelle");
        JMenuItem remove_course = new JMenuItem("Sil");
        course_menu.add(update_tname);
        course_menu.add(update_pname);
        course_menu.add(remove_course);
        tbl_crs_list.setComponentPopupMenu(course_menu);
        tbl_crs_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selectedrow = tbl_crs_list.rowAtPoint(point);
                tbl_crs_list.setRowSelectionInterval(selectedrow,selectedrow);
            }
        });
        update_pname.addActionListener(e -> {

        });
    }
    public void getDBConnection(){
        DefaultTableModel tbl_clr_mod = (DefaultTableModel) tbl_user_list.getModel();
        tbl_clr_mod.setRowCount(0);
        cmb_tchr_name.removeAllItems();
        cmb_tchr_name.addItem("");
        try {
            Statement st = DBConnector.getCon().createStatement();
            ResultSet data = st.executeQuery(Config.SELECT_ALLUSER);
            while (data.next()){
                row_user_list.addRow(new Object[]{data.getString(1),
                        data.getString(2),
                        data.getString(3),
                        data.getString(4),
                        data.getString(5)}

                );
                if (data.getString(5).equals("educater")){
                    cmb_tchr_name.addItem(data.getString(2));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void getDBConnection(String sql){
        DefaultTableModel tbl_clr_mod = (DefaultTableModel) tbl_ptk_list.getModel();
        tbl_clr_mod.setRowCount(0);
        cmb_path_name.removeAllItems();
        cmb_path_name.addItem("");
        try {
            Statement st = DBConnector.getCon().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                row_patika_list.addRow(new Object[]{
                        rs.getInt(1),
                        rs.getString(2)
                });
                cmb_path_name.addItem(new Patikas(rs.getInt(1),rs.getString(2)).getName());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void getDBConnectionforCOURSE(){
        DefaultTableModel clr_crs_list = (DefaultTableModel) tbl_crs_list.getModel();
        clr_crs_list.setRowCount(0);
        try {
            Statement st = DBConnector.getCon().createStatement();
            ResultSet rs = st.executeQuery(Config.SELECT_ALL_COURSE);
            while (rs.next()){
                row_course_list.addRow(new Object[]{
                        rs.getInt(1),
                        rs.getString(4),
                        rs.getString(5),
                        User.getFetchbyid(rs.getString(2)).getName(),
                        Patikas.FetchPatika(rs.getInt(3)).getName()
                });

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean confirm(){
        return JOptionPane.showConfirmDialog(null,"Silmek istediğinize emin misiniz ?","Sil",JOptionPane.YES_NO_OPTION) ==0;
    }

    //Confirmleri Türkçeleştirme olayımız
    public  void makeTurkish(){
        UIManager.put("OptionPane.okButtonText","Tamam");
        UIManager.put("OptionPane.yesButtonText","Evet");
        UIManager.put("OptionPane.noButtonText","Hayır");

    }




}
