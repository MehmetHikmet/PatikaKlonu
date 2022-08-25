package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Educator;
import com.patikadev.Model.Operator;
import com.patikadev.Model.Student;
import com.patikadev.Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame{
    private JPanel wrapper;
    private JPanel wtop;
    private JPanel wbottom;
    private JTextField fld_usr_name;
    private JButton btn_login;
    private JPasswordField fld_usr_pass;

    public LoginGUI(){
        add(wrapper);
        setSize(500,500);
        setLocation(Helper.screenCenter("x", getSize()), Helper.screenCenter("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);

        setVisible(true);
        btn_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Helper.isTextFieldEmpty(fld_usr_name) || Helper.isTextFieldEmpty(fld_usr_pass)){
                    Helper.showMsg("fill");
                }else{
                    User user = User.getFetch(fld_usr_name.getText(),fld_usr_pass.getText());
                    if(user==null){
                        Helper.showMsg("Kullanıcı Bulunamadı !");
                    }else{
                        Helper.showMsg("Hoşgeldiniz : " + user.getName()+ " !");
                        switch (user.getType()){
                            case "operator":
                                OperatorGUI operatorGUI = new OperatorGUI((Operator) user);
                                break;
                            case "educator":
                                EducatorGUI educatorGUI = new EducatorGUI((Educator) user);
                                break;
                            case "student":
                                StudentGUI stundetGUI = new StudentGUI((Student) user);
                                break;
                        }
                        dispose();
                    }
                }
            }
        });
    }
    public static void main(String[] args){
        Helper.setLayout();
        LoginGUI loginGUI = new LoginGUI();
    }
}
