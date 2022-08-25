package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Patika {
    private int id;
    private String name;

    public Patika(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ArrayList<Patika> getList(){
        ArrayList<Patika> patikaList = new ArrayList<>();
        Patika obj;

        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT*FROM patika");

            while(resultSet.next()){

                obj = new Patika(resultSet.getInt("id"), resultSet.getString("name"));
                patikaList.add(obj);

            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return patikaList;
    }
    public static boolean add(String name){
        String query ="INSERT INTO patika (name) VALUES(?)";
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1,name);
            return preparedStatement.executeUpdate() !=-1;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return true;
    }

    public static boolean update(int id, String name){
        String query = "UPDATE patika SET name = ? WHERE id=?";

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,id);
            return preparedStatement.executeUpdate() !=-1;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return true;
    }
    public static boolean delete(int id){
        String query = "DELETE FROM patika WHERE id=?";
        ArrayList<Course> courseList = Course.getListByPatika(id);
        for(Course course : courseList){
            Course.delete(course.getId());
        }
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1,id);
            return preparedStatement.executeUpdate() !=-1;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return true;
    }
    public static Patika getFetch(int id){
        Patika obj = null;
        String query = "SELECT*FROM patika WHERE id=?";

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet =  preparedStatement.executeQuery();
            if(resultSet.next()){
                obj = new Patika(resultSet.getInt("id"),resultSet.getString("name"));
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return obj;

    }
}
