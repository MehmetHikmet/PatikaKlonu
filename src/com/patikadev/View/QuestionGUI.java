package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Answer;
import com.patikadev.Model.Question;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


    public class QuestionGUI extends JFrame {
        private JPanel wrapper;
        private JTextField fld_addQuestion;
        private JTextField fld_addAnswer1;
        private JTextField fld_addAnswer2;
        private JTextField fld_addAnswer3;
        private JTextField fld_addAnswer4;
        private JRadioButton rdbtn_cevap1;
        private JRadioButton rdbtn_cevap2;
        private JRadioButton rdbtn_cevap3;
        private JRadioButton rdbtn_cevap4;
        private JButton btn_addQuestion;

        private int quizID;
        private EducatorQuizGUI educatorQuizGUI;

        public QuestionGUI(EducatorQuizGUI educatorQuizGUI, int quizID){
            this.quizID = quizID;
            this.educatorQuizGUI = educatorQuizGUI;

            add(wrapper);
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    educatorQuizGUI.loadQuestionModel();
                }
            });
            setSize(800, 600);
            setLocation(Helper.screenCenter("x", getSize()), Helper.screenCenter("y", getSize()));
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setTitle(Config.PROJECT_TITLE);
            setResizable(false);
            setVisible(true);

            rdbtn_cevap1.setSelected(true);

            rdbtn_cevap1.addActionListener(e -> {
                rdbtn_cevap1.setSelected(true);
                rdbtn_cevap2.setSelected(false);
                rdbtn_cevap3.setSelected(false);
                rdbtn_cevap4.setSelected(false);
            });

            rdbtn_cevap2.addActionListener(e -> {
                rdbtn_cevap2.setSelected(true);
                rdbtn_cevap1.setSelected(false);
                rdbtn_cevap3.setSelected(false);
                rdbtn_cevap4.setSelected(false);
            });

            rdbtn_cevap3.addActionListener(e -> {
                rdbtn_cevap3.setSelected(true);
                rdbtn_cevap1.setSelected(false);
                rdbtn_cevap2.setSelected(false);
                rdbtn_cevap4.setSelected(false);
            });

            rdbtn_cevap4.addActionListener(e -> {
                rdbtn_cevap4.setSelected(true);
                rdbtn_cevap1.setSelected(false);
                rdbtn_cevap2.setSelected(false);
                rdbtn_cevap3.setSelected(false);
            });

            btn_addQuestion.addActionListener(e -> {
                if(Helper.isTextFieldEmpty(fld_addQuestion) || Helper.isTextFieldEmpty(fld_addAnswer1) || Helper.isTextFieldEmpty(fld_addAnswer2) ||
                        Helper.isTextFieldEmpty(fld_addAnswer3) || Helper.isTextFieldEmpty(fld_addAnswer4)){
                    Helper.showMsg("fill");
                }else{
                    Answer[] answers = new Answer[4];
                    answers[0] = new Answer(fld_addAnswer1.getText());
                    answers[1] = new Answer(fld_addAnswer2.getText());
                    answers[2] = new Answer(fld_addAnswer3.getText());
                    answers[3] = new Answer(fld_addAnswer4.getText());
                    int correctAnswerIdx = -1;
                    if(rdbtn_cevap1.isSelected()) correctAnswerIdx = 0;
                    if(rdbtn_cevap2.isSelected()) correctAnswerIdx = 1;
                    if(rdbtn_cevap3.isSelected()) correctAnswerIdx = 2;
                    if(rdbtn_cevap4.isSelected()) correctAnswerIdx = 3;
                    if(Question.add(quizID, fld_addQuestion.getText(), correctAnswerIdx, answers)){
                        Helper.showMsg("done");
                        dispose();
                    }else{
                        Helper.showMsg("error");
                    }
                }
            });
        }
    }


