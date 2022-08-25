package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Patika;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdatePatikaGUI extends JFrame{
    private JPanel wrapper;
    private JTextField fld_ptk_name;
    private JButton güncelleButton;
    private Patika patika;

   public UpdatePatikaGUI(Patika patika){
       this.patika=patika;
       add(wrapper);
       setSize(300,150);
       setLocation(Helper.screenCenter("x", getSize()), Helper.screenCenter("y", getSize()));
       setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       setTitle(Config.PROJECT_TITLE);
       setVisible(true);

       fld_ptk_name.setText(patika.getName());
       güncelleButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               if(Helper.isTextFieldEmpty(fld_ptk_name)){
                   Helper.showMsg("fill");
               }else{
                   if(Patika.update(patika.getId(), fld_ptk_name.getText())){
                       Helper.showMsg("done");
                   }
                   dispose();
               }
           }
       });
   }
}
