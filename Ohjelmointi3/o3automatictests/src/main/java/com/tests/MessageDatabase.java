package com.tests;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.concurrent.Executors;
import java.security.SecureRandom;
import org.apache.commons.codec.digest.Crypt;

import org.json.JSONArray;
import org.json.JSONObject;

public class MessageDatabase {

    private SecureRandom secureRandom = new SecureRandom();

    private Connection connection = null;
    private static MessageDatabase dbInstance = null;

    public static synchronized MessageDatabase getInstance() {
        if (null == dbInstance) {
            dbInstance = new MessageDatabase();
        }
        return dbInstance;
    }

    private MessageDatabase() {

        try {
            open();
        } catch (SQLException e) {

        }

    }

    public void open() throws SQLException {

        String dbName = "database.db";
        File filu = new File(dbName);
        Boolean onkoFilu = filu.exists();
        String URL = "jdbc:sqlite:" + dbName;

        connection = DriverManager.getConnection(URL);

        if (!onkoFilu) {
            initializeDatabase();
        }

    }

    public boolean setUser(JSONObject user) throws SQLException {

        if (checkIfUserExists(user.getString("username"), user.getString("password"))) {
            return false;
        }
        byte bytes[] = new byte[13];
        secureRandom.nextBytes(bytes);

        String saltBytes = new String(Base64.getEncoder().encode(bytes));
        String salt = "$6$" + saltBytes;
        String hashedPassword = Crypt.crypt(user.getString("password"), salt);

        String setUserString = "insert into users " +
                "VALUES('" + user.getString("username") + "','" + hashedPassword + "','" + salt + "','"
                + user.getString("email") + "')";
        Statement createStatement;
        createStatement = connection.createStatement();
        createStatement.executeUpdate(setUserString);
        createStatement.close();

        return true;
    }

    public boolean checkIfUserExists(String givenUserName, String password) throws SQLException {

        Statement queryStatement = null;
        ResultSet rs;

        String checkUser = "select username, password from users where username = '" + givenUserName + "'";
        System.out.println("checking user");

        queryStatement = connection.createStatement();
        rs = queryStatement.executeQuery(checkUser);

        if (rs.next()) {
            System.out.println("user exists");
            String pass = rs.getString("password");
            String hashedpassword = Crypt.crypt(password, pass);
            if (pass.equals(hashedpassword)) {
                return true;
            }
        }
        System.out.println("user not found");
        return false;
    }

    public void setMessage(JSONObject message) throws SQLException {
        String areacode = null;
        String phonenumber = null;

        if (message.has("areacode") && message.has("phonenumber")) {
            areacode = message.getString("areacode");
            phonenumber = message.getString("phonenumber");
        }

        String setMessageString = "insert into messages " +
                "VALUES('"
                + message.getString("sent") + "','"
                + message.getString("nickname") + "','"
                + message.getDouble("longitude") + "','"
                + message.getDouble("latitude") + "','"
                + message.getString("dangertype") + "','"
                + areacode + "','"
                + phonenumber + "')";

        Statement createStatement;
        createStatement = connection.createStatement();
        createStatement.executeUpdate(setMessageString);
        createStatement.close();

    }

    public JSONArray getMessages() throws SQLException {

        Statement queryStatement = null;
        JSONObject obj = new JSONObject();

        String getMessagesString = "select sent, nickname, longitude, latitude, dangertype, areacode, phonenumber from messages ";

        queryStatement = connection.createStatement();
        ResultSet rs = queryStatement.executeQuery(getMessagesString);

        JSONArray jsoniarray = new JSONArray();

        while (rs.next()) {
            obj.put("sent", rs.getString("sent"));
            obj.put("nickname", rs.getString("nickname"));
            obj.put("longitude", rs.getDouble("longitude"));
            obj.put("latitude", rs.getDouble("latitude"));
            obj.put("dangertype", rs.getString("dangertype"));

            String code = rs.getString("areacode");
            String number = rs.getString("phonenumber");

            if (!code.isEmpty() && !code.equals("null")) {
                obj.put("areacode", rs.getString("areacode"));
            }

            if (!number.isEmpty() && !number.equals("null")) {
                obj.put("phonenumber", rs.getString("phonenumber"));
            }
            jsoniarray.put(obj);
        }

        return jsoniarray;

    }

    private boolean initializeDatabase() throws SQLException {
        if (null != connection) {
            String createMessageString = "CREATE Table messages (" +
                    "sent INTEGER," +
                    "nickname TEXT NOT NULL," +
                    "longitude REAL," +
                    "latitude REAL," +    
                    "dangertype text NOT NULL," +
                    "areacode text NOT NULL," +
                    "phonenumber text NOT NULL)";
                   

            Statement createStatement = connection.createStatement();
            createStatement.executeUpdate(createMessageString);
            String createUsersString = "CREATE Table users (" +
                    "username varchar(50) NOT NULL," +
                    "password varchar(50) NOT NULL," +
                    "email varchar(50)," +
                    "salt text NOT NULL," +
                    "primary key(username))";

            createStatement.executeUpdate(createUsersString);
            createStatement.close();
            System.out.println("database creation succesfull");
            return true;
        }
        System.out.println("Database creation failed");
        return false;
    }

}
