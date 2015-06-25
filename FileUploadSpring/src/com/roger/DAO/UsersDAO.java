package com.roger.DAO;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import app.User;

public interface UsersDAO {
   /** 
    * This is the method to be used to initialize
    * database resources ie. connection.
    */
   public void setDataSource(DataSource ds);
   /** 
    * This is the method to be used to write
    * records to Users table
    */
   public void write(Map<Integer, List<String>> data);
   /** 
    * This is the method to be used to list down
    * a record from the Users table corresponding
    * to a passed uid.
    */
   public User getUser(Integer uid);
   /** 
    * This is the method to be used to list down
    * all the records from the User table.
    */
   public List<User> listUsers();
   /** 
    * This is the method to be used to delete
    * a record from the User table corresponding
    * to a passed student id.
    */
   public void delete(Integer uid);
   /** 
    * This is the method to be used to update
    * a record into the User table.
    */
   public void update(Integer uid);
}