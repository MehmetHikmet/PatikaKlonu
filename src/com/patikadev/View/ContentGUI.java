package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Helper.Item;
import com.patikadev.Model.Content;
import com.patikadev.Model.Course;
import com.patikadev.Model.Educator;
import com.patikadev.Model.Quiz;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ContentGUI extends JFrame {
    private JPanel wrapper;
    private JPanel pnl_addContent;
    private JTextField fld_title;
    private JTextField fld_youtube;
    private JComboBox cmb_quiz;
    private JComboBox cmb_course;
    private JTextArea fld_description;
    private JButton btn_addContent;

    private Educator educator;

    public ContentGUI(EducatorGUI edGUI, Educator educator){
        this.educator = educator;

        add(wrapper);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                edGUI.loadContentModel();
                edGUI.setVisible(true);
            }
        });
        setSize(1000, 500);
        setLocation(Helper.screenCenter("x", getSize()), Helper.screenCenter("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        btn_addContent.addActionListener(e -> {
            if(Helper.isTextFieldEmpty(fld_title) || Helper.isTextFieldEmpty(fld_youtube)){
                Helper.showMsg("fill");
            }else{
                Item courseItem = (Item) cmb_course.getSelectedItem();
                Item quizItem = (Item) cmb_quiz.getSelectedItem();
                if(Content.add(courseItem.getKey(),this.educator.getId(), fld_title.getText(),
                        fld_description.getText(), fld_youtube.getText(), quizItem.getKey())){
                    Helper.showMsg("done");
                    dispose();
                }
            }
        });

        loadComboBoxes();
    }

    private void loadComboBoxes(){
        loadComboCourse();
        loadComboQuiz();
    }

    private void loadComboCourse(){
        for(Course c : Course.getListByUser(this.educator.getId())){
            cmb_course.addItem(new Item(c.getId(), c.getName()));
        }
    }

    private void loadComboQuiz(){
        Item quizItem = (Item) cmb_course.getSelectedItem();
        for(Quiz q : Quiz.getListByCourseID(quizItem.getKey())){
            cmb_quiz.addItem(new Item(q.getId(), q.getName()));
        }
    }
}
