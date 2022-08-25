package com.patikadev.View;

import com.patikadev.Helper.*;
import com.patikadev.Model.Course;
import com.patikadev.Model.Operator;
import com.patikadev.Model.Patika;
import com.patikadev.Model.User;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class OperatorGUI extends JFrame {


    private JPanel wrapper;
    private JTabbedPane tab_operrator;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JPanel pnl_userlist;
    private JScrollPane scrl_userlist;
    private JTable tbl_userlist;
    private JPanel pnl_userform;
    private JTextField fld_username;
    private JTextField fld_useruname;
    private JTextField fld_userpass;
    private JComboBox cmb_usertype;
    private JButton btn_adduser;
    private JTextField fld_userid;
    private JButton btn_user_delete;
    private JTextField fld_search_username;
    private JTextField fld_search_useruname;
    private JComboBox combo_search_usertype;
    private JButton btn_search_user;
    private JPanel pnl_ptk_list;
    private JScrollPane scrl_ptk_list;
    private JTable tbl_ptk_list;
    private JPanel pnl_ptk_add;
    private JTextField fld_ptk_name;
    private JButton btn_ptk_add;
    private JPanel pnl_course_list;
    private JScrollPane scrl_course_list;
    private JTable tbl_course_list;
    private JPanel pnl_course_add;
    private JTextField fld_course_name;
    private JTextField fld_lang_name;
    private JComboBox cmb_course_patika;
    private JComboBox cmb_educator_name;
    private JButton ekleButton;
    private DefaultTableModel mdl_userlist;
    private Object[] row_userlist;
    private User user;
    private DefaultTableModel mdl_patika_list;
    private Object[] row_patika_list;
    private JPopupMenu patikaMenu;
    private JPopupMenu courseMenu;
    private DefaultTableModel mdl_course_list;
    private Object[] row_course_list;


    private final Operator operator;

    private void createUIComponents() {
        tab_operrator = new JTabbedPane();
        tbl_userlist = new JTable();
        scrl_userlist = new JScrollPane();
    }

    public OperatorGUI(Operator operator) {

        this.operator = operator;

        add(wrapper);
        setSize(1000, 500);
        setLocation(Helper.screenCenter("x", getSize()), Helper.screenCenter("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);
//        lbl_welcome.setText("Hoşgeldiniz Sayın " + operator.getName());

        mdl_userlist = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if(column==0){
                    return false;
                }
                return super.isCellEditable(row,column);
            }
        };

        Object[] col_user_list = {"ID", "Ad Soyad", "Kullanıcı Adı", "Şifre", "Üyelik Tipi"};
        mdl_userlist.setColumnIdentifiers(col_user_list);

        row_userlist = new Object[col_user_list.length];
        loadUserModel();

        tbl_userlist.setModel(mdl_userlist);
        tbl_userlist.getTableHeader().setReorderingAllowed(false);

        tbl_userlist.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                try {
                    String selectedUserID =  tbl_userlist.getValueAt(tbl_userlist.getSelectedRow(),0).toString();
                    fld_userid.setText(selectedUserID);
                }catch (Exception exception){

                }

            }
        });

        tbl_userlist.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if(e.getType() == TableModelEvent.UPDATE){
                    int userId= Integer.parseInt(tbl_userlist.getValueAt(tbl_userlist.getSelectedRow(),0).toString());
                    String userName = tbl_userlist.getValueAt(tbl_userlist.getSelectedRow(),1).toString();
                    String userUName = tbl_userlist.getValueAt(tbl_userlist.getSelectedRow(),2).toString();
                    String password = tbl_userlist.getValueAt(tbl_userlist.getSelectedRow(),3).toString();
                    String type = tbl_userlist.getValueAt(tbl_userlist.getSelectedRow(),4).toString();

                    if(User.update(userId,userName,userUName,password,type)){
                        Helper.showMsg("done");
                        loadUserModel();
                        loadEducatorCombo();
                        loadCourseModel();
                    }
                    loadUserModel();
                    loadEducatorCombo();
                    loadCourseModel();
                }
            }
        });

        patikaMenu = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("Güncelle");
        JMenuItem deleteMenu = new JMenuItem("Sil");

        patikaMenu.add(updateMenu);
        patikaMenu.add(deleteMenu);


        updateMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int select_id = Integer.parseInt(tbl_ptk_list.getValueAt(tbl_ptk_list.getSelectedRow(),0).toString());
                UpdatePatikaGUI updatePatikaGUI = new UpdatePatikaGUI(Patika.getFetch(select_id));
                updatePatikaGUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadPatikaModel();
                        loadPatikaCombo();
                        loadCourseModel();
                    }
                });
            }
        });

        deleteMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Helper.confirm("sure")){
                    int select_id = Integer.parseInt(tbl_ptk_list.getValueAt(tbl_ptk_list.getSelectedRow(),0).toString());
                    if(Patika.delete(select_id)){
                        Helper.showMsg("done");
                        loadPatikaModel();
                        loadPatikaCombo();
                        loadCourseModel();
                    }else{
                        Helper.showMsg("error");
                    }
                }
            }
        });

        mdl_patika_list = new DefaultTableModel();
        Object[] col_patika_list = {"ID", "Patika Adı"};
        mdl_patika_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if(column==0){
                    return false;
                }
                return super.isCellEditable(row,column);
            }
        };
        mdl_patika_list.setColumnIdentifiers(col_patika_list);
        row_patika_list = new Object[col_patika_list.length];
        loadPatikaModel();

        tbl_ptk_list.setModel(mdl_patika_list);
        tbl_ptk_list.setComponentPopupMenu(patikaMenu);
        tbl_ptk_list.getTableHeader().setReorderingAllowed(false);
        tbl_ptk_list.getColumnModel().getColumn(0).setMaxWidth(100);

        tbl_ptk_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row= tbl_ptk_list.rowAtPoint(point);
                tbl_ptk_list.setRowSelectionInterval(selected_row,selected_row);
            }
        });
        courseMenu = new JPopupMenu();
        JMenuItem updateCourseMenu = new JMenuItem("Güncelle");
        JMenuItem deleteCourseMenu = new JMenuItem("Sil");

        courseMenu.add(updateCourseMenu);
        courseMenu.add(deleteCourseMenu);

        updateCourseMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int select_id = Integer.parseInt(tbl_course_list.getValueAt(tbl_course_list.getSelectedRow(),0).toString());
                UpdateCourseGUI updateCourseGUI = new UpdateCourseGUI(Course.getFetch(select_id));
                updateCourseGUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadCourseModel();
                        loadPatikaCombo();
                        loadEducatorCombo();
                    }
                });
            }
        });

        deleteCourseMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Helper.confirm("sure")){
                    int select_id = Integer.parseInt(tbl_course_list.getValueAt(tbl_course_list.getSelectedRow(),0).toString());
                    if(Course.delete(select_id)){
                        Helper.showMsg("done");
                        loadCourseModel();
                        loadPatikaCombo();
                        loadEducatorCombo();
                    }else{
                        Helper.showMsg("error");
                    }
                }
            }
        });

        mdl_course_list = new DefaultTableModel();
        Object[] col_courselist = {"ID","Ders Adı","Programlama Dili","Patika","Eğitmen"};
        mdl_course_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if(column==0){
                    return false;
                }
                return super.isCellEditable(row,column);
            }
        };
        mdl_course_list.setColumnIdentifiers(col_courselist);
        row_course_list = new Object[col_courselist.length];

        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.setComponentPopupMenu(courseMenu);
        tbl_course_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_course_list.getTableHeader().setReorderingAllowed(false);

        loadCourseModel();
        loadPatikaCombo();
        loadEducatorCombo();

        tbl_course_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row= tbl_course_list.rowAtPoint(point);
                tbl_course_list.setRowSelectionInterval(selected_row,selected_row);
            }
        });

        btn_adduser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Helper.isTextFieldEmpty(fld_username) || Helper.isTextFieldEmpty(fld_useruname) || Helper.isTextFieldEmpty(fld_userpass)){

                    Helper.showMsg("fill");

                }else{

                    String name = fld_username.getText();
                    String userName = fld_useruname.getText();
                    String password = fld_userpass.getText();
                    String type = cmb_usertype.getSelectedItem().toString();

                if(User.add(name,userName,password,type)){
                    Helper.showMsg("done");
                    loadUserModel();
                    loadEducatorCombo();
                    fld_username.setText(null);
                    fld_userpass.setText(null);
                    fld_useruname.setText(null);
                }
                }


            }
        });
        btn_user_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fld_userid.getText().isEmpty()){
                    Helper.showMsg("fill");
                }else{
                    if(Helper.confirm("sure")){

                    int id = Integer.parseInt(fld_userid.getText());
                    if(User.delete(id)){
                     Helper.showMsg("done");
                    }else {
                        Helper.showMsg("error");
                    }
                    fld_userid.setText(null);
                    loadUserModel();
                    loadEducatorCombo();
                    loadCourseModel();

                }
                }
            }
        });
        btn_search_user.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = fld_search_username.getText();
                String uname = fld_search_useruname.getText();
                String type = combo_search_usertype.getSelectedItem().toString();

                ArrayList<User> searchingUser=User.searchUserList(User.searchQuery(name, uname, type));

                loadUserModel(searchingUser);
            }
        });
        btn_ptk_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Helper.isTextFieldEmpty(fld_ptk_name)){
                    Helper.showMsg("fill");
                }else{
                    if(Patika.add(fld_ptk_name.getText())){
                        Helper.showMsg("done");
                        loadPatikaModel();
                        loadPatikaCombo();
                        fld_ptk_name.setText(null);
                    }else{
                        Helper.showMsg("error");
                    }
                }
            }
        });
        ekleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Item patikaItem = (Item) cmb_course_patika.getSelectedItem();
                Item userItem = (Item) cmb_educator_name.getSelectedItem();
                if(Helper.isTextFieldEmpty(fld_course_name) || Helper.isTextFieldEmpty(fld_lang_name)){

                    Helper.showMsg("fill");

                }else{

                    String name = fld_course_name.getText();
                    String lang = fld_lang_name.getText();
                    int userID = userItem.getKey();
                    int patikaID = patikaItem.getKey();

                    if(Course.add(name,lang,userID,patikaID)){
                        Helper.showMsg("done");
                        loadCourseModel();
                        fld_course_name.setText(null);
                        fld_lang_name.setText(null);
                    }
                }
            }
        });
        btn_logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginGUI loginGUI = new LoginGUI();
            }
        });
    }
    private void loadCourseModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_course_list.getModel();
        clearModel.setRowCount(0);
        Patika patika;
        User user;
        int i;
        for(Course course : Course.getList()){
            i=0;
            row_course_list[i++] = course.getId();
            row_course_list[i++] = course.getName();
            row_course_list[i++] = course.getLang();
            row_course_list[i++] = course.getPatika().getName();
            row_course_list[i++] = course.getEducator().getName();
            mdl_course_list.addRow(row_course_list);
        }
    }

    private void loadPatikaModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_ptk_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for(Patika patika : Patika.getList()){
            i=0;
            row_patika_list[i++] = patika.getId();
            row_patika_list[i++] = patika.getName();
            mdl_patika_list.addRow(row_patika_list);
        }
    }

    public void loadUserModel(){

        DefaultTableModel clearModel = (DefaultTableModel) tbl_userlist.getModel();
        clearModel.setRowCount(0);
        int i;
     for(User obj : User.getList()){
         i=0;
         row_userlist[i++]=obj.getId();
         row_userlist[i++]=obj.getName();
         row_userlist[i++]=obj.getUname();
         row_userlist[i++]=obj.getPass();
         row_userlist[i++]=obj.getType();
         mdl_userlist.addRow(row_userlist);
     }
    }

    public void loadUserModel(ArrayList<User> list){

        DefaultTableModel clearModel = (DefaultTableModel) tbl_userlist.getModel();
        clearModel.setRowCount(0);
        for(User obj : list){

            int i = 0;
            row_userlist[i++]=obj.getId();
            row_userlist[i++]=obj.getName();
            row_userlist[i++]=obj.getUname();
            row_userlist[i++]=obj.getPass();
            row_userlist[i++]=obj.getType();
            mdl_userlist.addRow(row_userlist);
        }
    }
    public void loadPatikaCombo(){
        cmb_course_patika.removeAllItems();
        for(Patika patika : Patika.getList()){
            cmb_course_patika.addItem(new Item(patika.getId(),patika.getName()));
        }
    }
    public void loadEducatorCombo(){
        cmb_educator_name.removeAllItems();
        for(User user : User.getList()){
            if(user.getType().equals("educator")){
                cmb_educator_name.addItem(new Item(user.getId(),user.getName()));
            }

        }
    }
    public static void main(String[] args) {
        Helper.setLayout();
        Operator op = new Operator();
        op.setId(1);
        op.setName("Mehmet Hikmet Demirci");
        op.setPass("45613");
        op.setUname("Mehmet");
        op.setType("1");


        OperatorGUI operatorGUI = new OperatorGUI(op);
    }
}
