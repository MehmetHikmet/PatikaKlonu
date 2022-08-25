package com.patikadev.Model;


import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String uname;
    private String pass;
    private String type;


    public User() {
    }

    public User(int id, String name, String uname, String pass, String type) {
        this.id = id;
        this.name = name;
        this.uname = uname;
        this.pass = pass;
        this.type = type;
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

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static ArrayList<User> getList() {
        ArrayList<User> userList = new ArrayList<>();
        String query = "Select*from user";
        User obj;

        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultset = statement.executeQuery(query);

            while (resultset.next()){
                obj = new User(resultset.getInt("id"),resultset.getString("name"), resultset.getString("uname"), resultset.getString("pass"), resultset.getString("type") );
                userList.add(obj);
            }


        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return userList;

    }
    public static boolean add(String name, String uname, String pass, String type){
        String query = "INSERT INTO user VALUES(?,?,?,?,?)";
        User findUser = User.getFetch(uname);

        if(findUser != null){
            Helper.showMsg("Lütfen Farklı Bir Kullanıcı Adı Giriniz !");
            return false;
        }

        try {

            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1,null);
            preparedStatement.setString(2,name);
            preparedStatement.setString(3,uname);
            preparedStatement.setString(4,pass);
            preparedStatement.setString(5,type);


            int result = preparedStatement.executeUpdate();
            if(result == -1){
                Helper.showMsg("error");
            }
            return result != -1;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return true;
    }
    public static User getFetch(String uname){
        User obj = null;
        String query = "SELECT*FROM user WHERE uname=?";

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1,uname);
            ResultSet resultSet =  preparedStatement.executeQuery();
            if(resultSet.next()){
                obj = new User();
                obj.setId(resultSet.getInt("id"));
                obj.setName(resultSet.getString("name"));
                obj.setUname(resultSet.getString("uname"));
                obj.setPass(resultSet.getString("pass"));
                obj.setType(resultSet.getString("type"));

            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return obj;

    }
    public static User getFetch(String uname, String pass){
        User obj = null;
        String query = "SELECT*FROM user WHERE uname=? AND pass=?";

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1,uname);
            preparedStatement.setString(2,pass);
            ResultSet resultSet =  preparedStatement.executeQuery();
            if(resultSet.next()){
                switch (resultSet.getString("type")){
                    case "operator":
                        obj = new Operator();
                        obj.setId(resultSet.getInt("id"));
                        obj.setName(resultSet.getString("name"));
                        obj.setUname(resultSet.getString("uname"));
                        obj.setPass(resultSet.getString("pass"));
                        obj.setType(resultSet.getString("type"));
                        break;
                    case "educator":
                        obj = new Educator();
                        obj.setId(resultSet.getInt("id"));
                        obj.setName(resultSet.getString("name"));
                        obj.setUname(resultSet.getString("uname"));
                        obj.setPass(resultSet.getString("pass"));
                        obj.setType(resultSet.getString("type"));
                        break;
                    case "student":
                        obj = new Student();
                        obj.setId(resultSet.getInt("id"));
                        obj.setName(resultSet.getString("name"));
                        obj.setUname(resultSet.getString("uname"));
                        obj.setPass(resultSet.getString("pass"));
                        obj.setType(resultSet.getString("type"));
                        break;
                    default:
                        obj = new User();
                        obj.setId(resultSet.getInt("id"));
                        obj.setName(resultSet.getString("name"));
                        obj.setUname(resultSet.getString("uname"));
                        obj.setPass(resultSet.getString("pass"));
                        obj.setType(resultSet.getString("type"));
                }


            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return obj;

    }
    public static User getFetchByID(int id){
        User obj = null;
        String query = "SELECT*FROM user WHERE id=?";

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet =  preparedStatement.executeQuery();
            if(resultSet.next()){
                obj = new User();
                obj.setId(resultSet.getInt("id"));
                obj.setName(resultSet.getString("name"));
                obj.setUname(resultSet.getString("uname"));
                obj.setPass(resultSet.getString("pass"));
                obj.setType(resultSet.getString("type"));

            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return obj;

    }
    public static boolean delete(int id){
        String query = "DELETE FROM user WHERE id = ?";
        ArrayList<Course> courseList = Course.getListByUser(id);
        for(Course course : courseList){
            Course.delete(course.getId());
        }
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1,id);


            int result = preparedStatement.executeUpdate();

            return result != -1;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return true;
    }

    public static boolean update(int id, String name, String uname, String pass, String type){

        String query="UPDATE user SET name=?,uname=?,pass=?,type=? WHERE id=?";
        User findUser = User.getFetch(uname);

        if(findUser != null && findUser.getId() != id){
            Helper.showMsg("Lütfen Farklı Bir Kullanıcı Adı Giriniz !");
            return false;
        }

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,uname);
            preparedStatement.setString(3,pass);
            preparedStatement.setString(4,type);
            preparedStatement.setInt(5,id);

            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    return true;

    }
    public static ArrayList<User> searchUserList(String query){
        ArrayList<User> userList = new ArrayList<>();
        User obj;

        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultset = statement.executeQuery(query);

            while (resultset.next()){
                obj = new User(resultset.getInt("id"),resultset.getString("name"), resultset.getString("uname"), resultset.getString("pass"), resultset.getString("type") );
                userList.add(obj);
            }


        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return userList;
    }
    public static String searchQuery(String name, String uname, String type){
        String query = "SELECT*FROM user WHERE uname LIKE '%{{uname}}%' and name LIKE '%{{name}}%'";
        query = query.replace("{{uname}}",uname);
        query = query.replace("{{name}}",name);
        if(!type.isEmpty()){
            query+="and type LIKE '{{type}}'";
            query = query.replace("{{type}}",type);
        }

        return query;
    }

}
