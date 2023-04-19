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

public class EducaterUI extends JFrame {
    private JPanel wrapper;
    private JLabel lbl_wlcm_tchr;
    private JTabbedPane tabbedPane1;
    private JTable tbl_content_list;
    private DefaultTableModel mdl_content_list;
    private DefaultTableModel row_content_list;
    private JScrollBar scrollBar1;
    private JButton btn_tchr_exit;
    private JTable tbl_tchr_crs_list;
    private JTextField fld_content;
    private JTextArea txt_content_explain;
    private JTextField fld_link;
    private JComboBox cmb_course_name;
    private JButton btn_add_content;
    private JTable tbl_que_list;
    private DefaultTableModel mdl_que_list;
    private DefaultTableModel row_quw_list;
    private DefaultTableModel mdl_tchr_crs_list;
    private DefaultTableModel row_tchr_crs_list;
    private Educater educater;
    public EducaterUI(Educater educater){
        this.educater = educater;
        setContentPane(wrapper);
        setSize(1100,500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        int x = (int) Helper.findScreenPoint("x",getSize());
        int y = (int) Helper.findScreenPoint("y",getSize());
        setLocation(x,y);
        lbl_wlcm_tchr.setText(educater.getName());
        //Ders Tablosu yapımı
        mdl_tchr_crs_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column==0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };
        mdl_tchr_crs_list = new DefaultTableModel();
        mdl_tchr_crs_list.setColumnIdentifiers(new Object[]{"id","Ders Adı"});
        tbl_tchr_crs_list.setModel(mdl_tchr_crs_list);
        row_tchr_crs_list =(DefaultTableModel) tbl_tchr_crs_list.getModel();
        tbl_tchr_crs_list.getTableHeader().setReorderingAllowed(false);
        tbl_tchr_crs_list.getColumnModel().getColumn(0).setMaxWidth(60);
        getCourselistbyuser();
        //Content tablosu yapımı
        mdl_content_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column==0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };
        mdl_content_list = new DefaultTableModel();
        mdl_content_list.setColumnIdentifiers(new Object[]{"id","İçerik Adı","Açıklama","Link","Ders Adı","Öğretmen Adı"});
        tbl_content_list.setModel(mdl_content_list);
        row_content_list =(DefaultTableModel) tbl_content_list.getModel();
        tbl_content_list.getTableHeader().setReorderingAllowed(false);
        tbl_content_list.getColumnModel().getColumn(0).setMaxWidth(60);
        getDBcontentlists();

        //Contente sağtıkla menu ekleme
        JPopupMenu contentmenu = new JPopupMenu();
        JMenuItem updateitem = new JMenuItem("Güncelle");
        JMenuItem removeitem = new JMenuItem("Sil");
        JMenuItem addQ = new JMenuItem("Soru ekle");
        contentmenu.add(updateitem);
        contentmenu.add(removeitem);
        contentmenu.add(addQ);
        tbl_content_list.setComponentPopupMenu(contentmenu);
        tbl_content_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = tbl_content_list.rowAtPoint(point);
                tbl_content_list.setRowSelectionInterval(selected_row,selected_row);
                super.mousePressed(e);
            }
        });
        //Silme işlemi
        removeitem.addActionListener(e -> {
           if(Helper.confirm()) {
               Content.RemoveContent((Integer) tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(),0));
               getDBcontentlists();
           }
        });
        //update işlemi
        updateitem.addActionListener(e -> {
            int update_id = (int) tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(),0);
            String update_title = (String) tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(),1);
            String update_explain = (String) tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(),2);
            String update_link = (String) tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(),3);
            UpdateContentUI update = new UpdateContentUI(update_id,update_title,update_link,update_explain);
            update.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    getDBcontentlists();
                    super.windowClosed(e);
                }
            });
        });
        //Add Question
        addQ.addActionListener(e -> {
            AddquestionUI addqui = new AddquestionUI(educater.getId(), (Integer) tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(),0));
            addqui.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    getQuestions();
                    super.windowClosed(e);
                }
            });
        });
        btn_add_content.addActionListener(e -> {
            if (Helper.isEmpty(fld_content) || Helper.isEmpty(fld_link) || Helper.isEmpty(txt_content_explain) || cmb_course_name.getSelectedItem()==""){
                Helper.getFillFull("fill");
            }else{
                Helper.getFillFull(("done"));
                Content.AddContent(fld_content.getText(),txt_content_explain.getText(),fld_link.getText(),Course.getFetckCourse((String) cmb_course_name.getSelectedItem()).getId(),educater.getId());
                getDBcontentlists();
                fld_content.setText("");
                fld_link.setText("");
                txt_content_explain.setText("");
                cmb_course_name.setSelectedIndex(0);
            }
        });

        // Questions Alanı
        mdl_que_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                    if (row==0){
                        return false;
                    }
                return super.isCellEditable(row, column);
            }
        };
        mdl_que_list = new DefaultTableModel();
        mdl_que_list.setColumnIdentifiers(new Object[]{"Id","Question","Ders Adı","Öğretmen Adı"});
        tbl_que_list.setModel(mdl_que_list);
        row_quw_list = (DefaultTableModel) tbl_que_list.getModel();
        getQuestions();

        JPopupMenu queMenu = new JPopupMenu();
        JMenuItem updateq = new JMenuItem("Güncelle");
        JMenuItem removeq = new JMenuItem("Sil");
        queMenu.add(updateq);
        queMenu.add(removeq);
        tbl_que_list.setComponentPopupMenu(queMenu);
        tbl_que_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = tbl_que_list.rowAtPoint(point);
                tbl_que_list.setRowSelectionInterval(selected_row,selected_row);
                super.mousePressed(e);
            }
        });

        updateq.addActionListener(e -> {
            if (educater.getId()==User.getFetchbyNAME((String) tbl_que_list.getValueAt(tbl_que_list.getSelectedRow(),3)).getId()){
                    AddquestionUI updateque = new AddquestionUI((int)tbl_que_list.getValueAt(tbl_que_list.getSelectedRow(),0),(String) tbl_que_list.getValueAt(tbl_que_list.getSelectedRow(),1));
                    updateque.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            getQuestions();
                            super.windowClosed(e);
                        }
                    });
            }else{
                Helper.getFillFull("notyou");
            }
        });
        removeq.addActionListener(e -> {
            if (educater.getId()==User.getFetchbyNAME((String) tbl_que_list.getValueAt(tbl_que_list.getSelectedRow(),3)).getId()){
                Question.removeQue((int)tbl_que_list.getValueAt(tbl_que_list.getSelectedRow(),0));
                Helper.getFillFull("done");
                getQuestions();
            }else{
                Helper.getFillFull("notyou");
            }
        });

        btn_tchr_exit.addActionListener(e -> {
            dispose();
            LoginUI loginUI = new LoginUI();
        });
    }
    public void getDBcontentlists(){
        DefaultTableModel clr_content_list = (DefaultTableModel) tbl_content_list.getModel();
        clr_content_list.setRowCount(0);
        try {
            Statement st = DBConnector.getCon().createStatement();
            ResultSet rs = st.executeQuery(Config.ALL_CONTENTS);
            while (rs.next()){
                //Sadece educaterin kendi dersleri gözüküyor
                if (educater.getId()==rs.getInt(6)){
                    row_content_list.addRow(new Object[]{
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            Course.getFetckCourse(rs.getInt(5)).getName(),
                            User.getFetchbyid(rs.getString(6)).getName()
                    });
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void getCourselistbyuser(){
        DefaultTableModel clr_tchr_course_list = (DefaultTableModel) tbl_tchr_crs_list.getModel();
        clr_tchr_course_list.setRowCount(0);
        cmb_course_name.removeAllItems();
        cmb_course_name.addItem("");

        try {
            PreparedStatement pr = DBConnector.getCon().prepareStatement(Config.CONTROL_COURSE_BY_USER);
            pr.setInt(1,educater.getId());
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                row_tchr_crs_list.addRow(new Object[]{
                        rs.getInt(1),
                        rs.getString(4)
                });
                cmb_course_name.addItem(rs.getString(4));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void getQuestions(){
        DefaultTableModel clr_que_list = (DefaultTableModel) tbl_que_list.getModel();
        clr_que_list.setRowCount(0);
        try {
            Statement st = DBConnector.getCon().createStatement();
            ResultSet rs = st.executeQuery(Config.SELECT_ALL_QUESTION);
            while (rs.next()){
                row_quw_list.addRow(new Object[]{
                        rs.getInt(1),
                        rs.getString(2),
                        Content.getFetchContent(rs.getInt(4)).getTitle() ,//İçerik
                        User.getFetchbyid(rs.getString(3)).getName() //öğretmen
                });
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
