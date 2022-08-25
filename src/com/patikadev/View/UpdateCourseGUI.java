package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Helper.Item;
import com.patikadev.Model.Course;
import com.patikadev.Model.Patika;
import com.patikadev.Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateCourseGUI extends JFrame{
    private JPanel wrapper;
    private JTextField fld_course_updatename;
    private JTextField fld_lang_updatename;
    private JComboBox cmb_course_updatepatika;
    private JComboBox cmb_course_updateeducator;
    private JButton güncelleButton;
    private Course course;

    public UpdateCourseGUI(Course course){
        this.course=course;
        add(wrapper);
        setSize(400,200);
        setLocation(Helper.screenCenter("x", getSize()), Helper.screenCenter("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);



        fld_course_updatename.setText(course.getName());
        fld_lang_updatename.setText(course.getLang());


        güncelleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Helper.isTextFieldEmpty(fld_course_updatename)){
                    Helper.showMsg("fill");
                }else{
                    if(Course.update(course.getId(), fld_course_updatename.getText(),fld_lang_updatename.getText())){
                        Helper.showMsg("done");
                    }
                    dispose();
                }
            }
        });
    }
}
