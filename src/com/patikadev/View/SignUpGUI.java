package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.User;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SignUpGUI extends JFrame {
    private JPanel wrapper;
    private JTextField fld_signUpUname;
    private JButton btn_signUp;
    private JTextField fld_signUpName;
    private JPasswordField fld_signUpPass;

    public SignUpGUI(){
        add(wrapper);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                LoginGUI loginGUI = new LoginGUI();
            }
        });
        setSize(400, 450);
        setLocation(Helper.screenCenter("x", getSize()), Helper.screenCenter("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        btn_signUp.addActionListener(e -> {
            if(Helper.isTextFieldEmpty(fld_signUpName) || Helper.isTextFieldEmpty(fld_signUpUname) || Helper.isTextFieldEmpty(fld_signUpPass)){
                Helper.showMsg("fill");
            }else{
                if(User.add(fld_signUpName.getText(), fld_signUpUname.getText(), fld_signUpPass.getText(), "student")){
                    Helper.showMsg("done");
                    dispose();
                }
            }
        });
    }

}
