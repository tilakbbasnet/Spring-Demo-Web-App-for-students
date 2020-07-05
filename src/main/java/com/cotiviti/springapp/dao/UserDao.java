package com.cotiviti.springapp.dao;

import com.cotiviti.springapp.model.User;
import com.cotiviti.springapp.utility.DBConnection;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//@Repository("uDao")
@Repository
public class UserDao{
    private Connection connection = null;

    public String getPassword(String email){
        String passFromDb="";
        try{
            connection = new DBConnection().getDbConnection();
            PreparedStatement pstmt = connection.prepareStatement("SELECT password from ascol_users where email=?");
            pstmt.setString(1,email);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                passFromDb = rs.getString("password");
            }
        }catch (Exception e){
            System.err.println("Exception during password retrieval "+e.getMessage());
        }
        return passFromDb;
    }

    public boolean registerUser(String name, String dob, String gender, String email, String password){
        boolean isSaved = false;
        connection = new DBConnection().getDbConnection();
        try{
            PreparedStatement pstmt =connection.prepareStatement("INSERT INTO ascol_users (name, dob, gender, email,password) VALUES (?, ?, ?, ?, ?)");
            pstmt.setString(1,name);
            pstmt.setString(2,dob);
            pstmt.setString(3,gender);
            pstmt.setString(4,email);
            pstmt.setString(5,password);
            int i = pstmt.executeUpdate();
            if(i>0){
                isSaved = true;
            }
        }catch (Exception ex){
            System.err.println("Exception during new user registration "+ex.getMessage());
        }finally {
            closeConnection();
        }
        return  isSaved;
    }

    public boolean updateUserInfo(String email, String password){
        boolean isUpdated = false;
        try {
            connection = new DBConnection().getDbConnection();
            PreparedStatement pstmt = connection.prepareStatement("UPDATE ascol_users set password=? where email=?");
            pstmt.setString(1,password);
            pstmt.setString(2,email);
            int i = pstmt.executeUpdate();
            if(i>0){
                isUpdated = true;
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error during user info updation "+e.getMessage());
        }finally {
            closeConnection();
        }
        return isUpdated;
    }

    public List<User> getUserList() {
        List<User> users = new ArrayList<>();
        try {
            connection = new DBConnection().getDbConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ascol_users");
            while(rs.next()) {
                users.add(new User(rs.getInt("uid"), rs.getString("name"),rs.getString("gender"),
                        rs.getString("dob"),rs.getString("email")));
            }
        }catch (Exception exc){
            exc.printStackTrace();
            System.out.println("Exception during user list retrieval "+ exc.getMessage());
        }finally {
            closeConnection();
        }
        return users;
    }

    public boolean deleteUser(int userId){
        boolean isDeleted = false;
        try{
            connection = new DBConnection().getDbConnection();
            PreparedStatement pstmt = connection.prepareStatement("DELETE FROM ascol_users where uid=?");
            pstmt.setInt(1, userId);
            int row = pstmt.executeUpdate();
            if(row > 0){
                isDeleted = true;
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Exception during user deletion "+e.getMessage());
        }finally {
            closeConnection();
        }
        return isDeleted;
    }

    public User getUserById(int userId){
        User usr = null;
        try{
            connection = new DBConnection().getDbConnection();
            PreparedStatement pstmt = connection.prepareStatement("SELECT uid, name, dob, gender, email from ascol_users where uid=?");
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                usr = new User(rs.getInt("uid"),rs.getString("name"),rs.getString("gender"),rs.getString("dob"),rs.getString("email"));
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Exception during user detail retrieval "+e.getMessage());
        }finally {
            closeConnection();
        }
        return usr;
    }

    public boolean updateUserDetails(User user){
        boolean isUpdated = false;
        try{
            connection = new DBConnection().getDbConnection();
            PreparedStatement pstmt = connection.prepareStatement("UPDATE ascol_users set name=?, gender=?, dob=?, email=? where uid=?");
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getGender());
            pstmt.setString(3, user.getDob());
            pstmt.setString(4, user.getEmail());
            pstmt.setInt(5, user.getId());
            int rows = pstmt.executeUpdate();
            if(rows > 0){
                isUpdated = true;
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Exception during user details updation "+e.getMessage());
        }finally {
            closeConnection();
        }
        return false;
    }

    public void closeConnection() {
        try{
            if(this.connection !=  null){
                this.connection.close();
            }
        }catch (Exception e) {
        e.printStackTrace();
        }
    }
}
