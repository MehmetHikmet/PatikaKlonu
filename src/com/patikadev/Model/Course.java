package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Course {
    private int id;
    private int user_id;
    private int patika_id;
    private String name;
    private String lang;

    private Patika patika;
    private User educator;

    public Course(int id, int user_id, int patika_id, String name, String lang) {
        this.id = id;
        this.user_id = user_id;
        this.patika_id = patika_id;
        this.name = name;
        this.lang = lang;
        this.patika=Patika.getFetch(patika_id);
        this.educator=User.getFetchByID(user_id);
    }

    public Course(){
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPatika_id() {
        return patika_id;
    }

    public void setPatika_id(int patika_id) {
        this.patika_id = patika_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Patika getPatika() {
        return patika;
    }

    public void setPatika(Patika patika) {
        this.patika = patika;
    }

    public User getEducator() {
        return educator;
    }

    public void setEducator(User educator) {
        this.educator = educator;
    }
    public static Course getByID(int id){
        Course c = null;
        String query = "SELECT * FROM course WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if(rs.next()){
                c = new Course();
                c.setId(rs.getInt("id"));
                c.setUser_id(rs.getInt("user_id"));
                c.setPatika_id(rs.getInt("patika_id"));
                c.setName(rs.getString("name"));
                c.setLang(rs.getString("lang"));
                c.setPatika(Patika.getFetch(c.getPatika_id()));
                c.setEducator(User.getFetchByID(c.getUser_id()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }
    public static ArrayList<Course> getList(){
        ArrayList<Course> courseList = new ArrayList<>();
        Course obj;

        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT*FROM course");

            while(resultSet.next()){

                obj = new Course(resultSet.getInt("id"), resultSet.getInt("user_id"), resultSet.getInt("patika_id"), resultSet.getString("name"), resultSet.getString("lang"));
                courseList.add(obj);

            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return courseList;
    }

    public static ArrayList<Course> getListByEnrollment(int studentID){
        ArrayList<Course> courseList = new ArrayList<>();

        Course obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM course WHERE id IN (SELECT course_id FROM enrollment WHERE student_id=" + studentID + ")");
            while(rs.next()){
                int id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                int patika_id = rs.getInt("patika_id");
                String name = rs.getString("name");
                String lang = rs.getString("lang");
                obj = new Course(id, user_id, patika_id, name, lang);
                courseList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseList;
    }
    public static ArrayList<Course> getListByUser(int user_id){
        ArrayList<Course> courseList = new ArrayList<>();
        Course obj;

        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT*FROM course WHERE user_id = " + user_id);

            while(resultSet.next()){

                obj = new Course(resultSet.getInt("id"), resultSet.getInt("user_id"), resultSet.getInt("patika_id"), resultSet.getString("name"), resultSet.getString("lang"));
                courseList.add(obj);

            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return courseList;
    }
    public static ArrayList<Course> getListByPatika(int patika_id){
        ArrayList<Course> courseList = new ArrayList<>();
        Course obj;

        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT*FROM course WHERE patika_id = " + patika_id);

            while(resultSet.next()){

                obj = new Course(resultSet.getInt("id"), resultSet.getInt("user_id"), resultSet.getInt("patika_id"), resultSet.getString("name"), resultSet.getString("lang"));
                courseList.add(obj);

            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return courseList;
    }
    public static boolean add(String name, String lang, int user_id, int patika_id){
        String query = "INSERT INTO course VALUES(?,?,?,?,?)";

        try {

            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1,null);
            preparedStatement.setInt(2,user_id);
            preparedStatement.setInt(3,patika_id);
            preparedStatement.setString(4,name);
            preparedStatement.setString(5,lang);

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
    public static boolean update(int id, String name, String lang){
        String query = "UPDATE course SET name = ?, lang=? WHERE id=?";
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lang);
            preparedStatement.setInt(3,id);

            return preparedStatement.executeUpdate() !=-1;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return true;
    }
    public static boolean delete(int id){
        String query = "DELETE FROM course WHERE id=?";
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1,id);
            return preparedStatement.executeUpdate() !=-1;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return true;
    }

    public static Course getFetch(int id){
        Course obj = null;
        String query = "SELECT*FROM course WHERE id=?";

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet =  preparedStatement.executeQuery();
            if(resultSet.next()){
                obj = new Course(resultSet.getInt("id"),resultSet.getInt("user_id"),resultSet.getInt("patika_id"),resultSet.getString("name"),resultSet.getString("lang"));
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return obj;

    }
}
