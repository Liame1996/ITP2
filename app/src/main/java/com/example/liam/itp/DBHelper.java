package com.example.liam.itp;

import android.util.Log;

import com.example.liam.itp.AppConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * Created by Evan on 13/02/2016.
 */
public class DBHelper {
    private Connection conn;
    private String TAG = DBHelper.class.getSimpleName();

    public DBHelper(){
        conn = null;
        try{
            Class.forName(AppConfig.DRIVER);
            conn = DriverManager.getConnection(AppConfig.connectionString, AppConfig.db_user, AppConfig.db_pass);
            createTable();
        }
        catch(SQLException s){
            Log.e(TAG, s.getMessage());
        }
        catch(ClassNotFoundException c){
            Log.e(TAG, c.getMessage());
        }
    }

    public void createTable(){
        String query = "CREATE TABLE IF NOT EXISTS "+AppConfig.TABLE_NAME + "(id int PRIMARY KEY auto_increment, "+"name varchar(50)not null, "+"type varchar(50)not null, "+"address varchar(100) not null, "+"email varchar(50) not null, "+"phone int not null);";
        try{
            Statement st = conn.createStatement();
            st.executeUpdate(query);
        }
        catch(SQLException s){
            Log.e(TAG, s.getMessage());
        }
    }

    public boolean addDetails(String name, String type, String address, String email, int phone){
        boolean result = false;

        try{
            PreparedStatement st = conn.prepareStatement("INSERT INTO " + AppConfig.TABLE_NAME + " (name, type, address, email, phone) VALUES(?,?,?,?,?)");
            st.setString(1, name);
            st.setString(2, type);
            st.setString(3, address);
            st.setString(4, email);
            st.setInt(5, phone);

            result = st.execute();
            st.close();
        }
        catch(SQLException s){
            Log.e(TAG, s.getMessage());
        }
        return result;
    }

    public HashMap<String, String> getDetails(String name){
        HashMap<String, String> detail = new HashMap<String, String>();
        String query = "SELECT * FROM "+AppConfig.TABLE_NAME+" WHERE name = '"+name+"'";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            if(rs.next()){
                detail.put("name", rs.getString("name"));
                detail.put("type", rs.getString("type"));
                detail.put("address", rs.getString("address"));
                detail.put("email", rs.getString("email"));
                detail.put("phone", ""+ rs.getInt("phone"));
            }
        }
        catch(SQLException s){
            Log.e(TAG, s.getMessage());
        }

        return detail;
    }

    public ResultSet getAllDetails(){
        String query = "SELECT * FROM "+AppConfig.TABLE_NAME;
        ResultSet temp = null;

        try{
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            temp = rs;
        }
        catch(SQLException s){
            Log.e(TAG, s.getMessage());
        }

        return temp;
    }

    public ResultSet getDiceysDetails(){
        String query = "SELECT * FROM club WHERE C_NAME = 'Diceys'";
        ResultSet temp = null;

        try{
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            temp = rs;
        }
        catch(SQLException s){
            Log.e(TAG, s.getMessage());
        }
        return temp;
    }

    public ResultSet getPalaceDetails(){
        String query = "SELECT * FROM club WHERE C_NAME = 'Palace'";
        ResultSet temp = null;

        try{
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            temp = rs;
        }
        catch(SQLException s){
            Log.e(TAG, s.getMessage());
        }
        return temp;
    }

    public ResultSet getEverleighDetails(){
        String query = "SELECT * FROM club WHERE C_NAME = 'Everleigh'";
        ResultSet temp = null;

        try{
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            temp = rs;
        }
        catch(SQLException s){
            Log.e(TAG, s.getMessage());
        }
        return temp;
    }

    public ResultSet getDtwoDetails(){
        String query = "SELECT * FROM club WHERE C_NAME = 'DTwo'";
        ResultSet temp = null;

        try{
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            temp = rs;
        }
        catch(SQLException s){
            Log.e(TAG, s.getMessage());
        }
        return temp;
    }

    public ResultSet getCoppersDetails(){
        String query = "SELECT * FROM club WHERE C_NAME = 'Copper Face Jacks'";
        ResultSet temp = null;

        try{
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            temp = rs;
        }
        catch(SQLException s){
            Log.e(TAG, s.getMessage());
        }
        return temp;
    }

    public ResultSet getSinnottsDetails(){
        String query = "SELECT * FROM bar WHERE B_NAME = 'Sinnotts'";
        ResultSet temp = null;

        try{
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            temp = rs;
        }
        catch(SQLException s){
            Log.e(TAG, s.getMessage());
        }
        return temp;
    }

    public ResultSet getOreillysDetails(){
        String query = "SELECT * FROM bar WHERE B_NAME = 'OReilys'";
        ResultSet temp = null;

        try{
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            temp = rs;
        }
        catch(SQLException s){
            Log.e(TAG, s.getMessage());
        }
        return temp;
    }

    public ResultSet getTrinityDetails(){
        String query = "SELECT * FROM bar WHERE B_NAME = 'Trinity Bar'";
        ResultSet temp = null;

        try{
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            temp = rs;
        }
        catch(SQLException s){
            Log.e(TAG, s.getMessage());
        }
        return temp;
    }

    public ResultSet getLagoonaDetails(){
        String query = "SELECT * FROM bar WHERE B_NAME = 'Lagoona'";
        ResultSet temp = null;

        try{
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            temp = rs;
        }
        catch(SQLException s){
            Log.e(TAG, s.getMessage());
        }
        return temp;
    }

    public ResultSet getTempleDetails(){
        String query = "SELECT * FROM bar WHERE B_NAME = 'The Temple Bar'";
        ResultSet temp = null;

        try{
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            temp = rs;
        }
        catch(SQLException s){
            Log.e(TAG, s.getMessage());
        }
        return temp;
    }

    public ResultSet getCaptainsDetails(){
        String query = "SELECT * FROM restaurant WHERE R_NAME = 'Captain Americas'";
        ResultSet temp = null;

        try{
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            temp = rs;
        }
        catch(SQLException s){
            Log.e(TAG, s.getMessage());
        }
        return temp;
    }

    public ResultSet getTgifDetails(){
        String query = "SELECT * FROM restaurant WHERE R_NAME = 'Captain Americas'";
        ResultSet temp = null;

        try{
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            temp = rs;
        }
        catch(SQLException s){
            Log.e(TAG, s.getMessage());
        }
        return temp;
    }

    public ResultSet getCounterDetails(){
        String query = "SELECT * FROM restaurant WHERE R_NAME = 'Captain Americas'";
        ResultSet temp = null;

        try{
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            temp = rs;
        }
        catch(SQLException s){
            Log.e(TAG, s.getMessage());
        }
        return temp;
    }

    public ResultSet getAussiebbgDetails(){
        String query = "SELECT * FROM restaurant WHERE R_NAME = 'Captain Americas'";
        ResultSet temp = null;

        try{
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            temp = rs;
        }
        catch(SQLException s){
            Log.e(TAG, s.getMessage());
        }
        return temp;
    }

    public ResultSet getItalianDetails(){
        String query = "SELECT * FROM restaurant WHERE R_NAME = 'Captain Americas'";
        ResultSet temp = null;

        try{
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            temp = rs;
        }
        catch(SQLException s){
            Log.e(TAG, s.getMessage());
        }
        return temp;
    }



    public ResultSet getWhiskeyDetails(){
        String query = "SELECT * FROM whiskey";
        ResultSet temp = null;

        try{
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            temp = rs;
        }
        catch(SQLException s){
            Log.e(TAG, s.getMessage());
        }
        return temp;
    }

    public ResultSet getVodkaDetails(){
        String query = "SELECT * FROM vodka";
        ResultSet temp = null;

        try{
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            temp = rs;
        }
        catch(SQLException s){
            Log.e(TAG, s.getMessage());
        }
        return temp;
    }

    public ResultSet getBrandyDetails(){
        String query = "SELECT * FROM brandy";
        ResultSet temp = null;

        try{
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            temp = rs;
        }
        catch(SQLException s){
            Log.e(TAG, s.getMessage());
        }
        return temp;
    }

    public ResultSet getRumDetails(){
        String query = "SELECT * FROM rum";
        ResultSet temp = null;

        try{
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            temp = rs;
        }
        catch(SQLException s){
            Log.e(TAG, s.getMessage());
        }
        return temp;
    }

    public ResultSet getTequilaDetails(){
        String query = "SELECT * FROM tequila";
        ResultSet temp = null;

        try{
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            temp = rs;
        }
        catch(SQLException s){
            Log.e(TAG, s.getMessage());
        }
        return temp;
    }

    public ResultSet getGinDetails(){
        String query = "SELECT * FROM gin";
        ResultSet temp = null;

        try{
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            temp = rs;
        }
        catch(SQLException s){
            Log.e(TAG, s.getMessage());
        }
        return temp;
    }

    public ResultSet getBeerDetails(){
        String query = "SELECT * FROM beer";
        ResultSet temp = null;

        try{
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            temp = rs;
        }
        catch(SQLException s){
            Log.e(TAG, s.getMessage());
        }
        return temp;
    }

    public ResultSet getCiderDetails(){
        String query = "SELECT * FROM cider";
        ResultSet temp = null;

        try{
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            temp = rs;
        }
        catch(SQLException s){
            Log.e(TAG, s.getMessage());
        }
        return temp;
    }
}
