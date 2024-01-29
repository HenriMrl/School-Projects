package com.server;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.security.SecureRandom;
import org.apache.commons.codec.digest.Crypt;

import org.json.JSONArray;
import org.json.JSONException;
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

    public JSONArray userQuery(JSONObject obj) throws JSONException, SQLException {

        String userNickName = obj.getString("nickname");
        PreparedStatement stm = connection.prepareStatement("SELECT nickname, latitude, longitude, sent, dangertype, areacode, phonenumber FROM messages WHERE nickname=?");

        stm.setString(1, userNickName);
        ResultSet rs = stm.executeQuery();

        JSONArray jsoniarray = new JSONArray();

        while (rs.next()) {
            JSONObject jsonobject = new JSONObject();
            jsonobject.put("nickname", rs.getString("nickname"));
            jsonobject.put("latitude", rs.getDouble("latitude"));
            jsonobject.put("longitude", rs.getDouble("longitude"));
            jsonobject.put("sent", rs.getString("sent"));
            jsonobject.put("dangertype", rs.getString("dangertype"));

            String code = rs.getString("areacode");
            String number = rs.getString("phonenumber");

            if (!code.isEmpty() && !code.equals("null")) {
                jsonobject.put("areacode", rs.getString("areacode"));
            }

            if (!number.isEmpty() && !number.equals("null")) {
                jsonobject.put("phonenumber", rs.getString("phonenumber"));
            }
            jsoniarray.put(jsonobject);
        }
        return jsoniarray;
    }

    public JSONArray timeQuery(JSONObject obj) throws JSONException, SQLException {

        String timestart = obj.getString("timestart");
        String timeend = obj.getString("timeend");
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM messages WHERE sent >= CAST(? as DATETIME) AND sent <= CAST(? as DATETIME)");

        stm.setString(1, timestart);
        stm.setString(2, timeend);

        ResultSet rs = stm.executeQuery();

        JSONArray jsoniarray = new JSONArray();

        while (rs.next()) {
            JSONObject jsonobject = new JSONObject();
            jsonobject.put("nickname", rs.getString("nickname"));
            jsonobject.put("latitude", rs.getDouble("latitude"));
            jsonobject.put("longitude", rs.getDouble("longitude"));
            jsonobject.put("sent", rs.getString("sent"));
            jsonobject.put("dangertype", rs.getString("dangertype"));

            String code = rs.getString("areacode");
            String number = rs.getString("phonenumber");

            if (!code.isEmpty() && !code.equals("null")) {
                jsonobject.put("areacode", rs.getString("areacode"));
            }

            if (!number.isEmpty() && !number.equals("null")) {
                jsonobject.put("phonenumber", rs.getString("phonenumber"));
            }
            jsoniarray.put(jsonobject);
        }
        return jsoniarray;
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
                + message.getString("nickname") + "','"
                + message.getDouble("latitude") + "','"
                + message.getDouble("longitude") + "','"
                + message.getString("sent") + "','"
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
    

        String getMessagesString = "select nickname, latitude, longitude, sent, dangertype, areacode, phonenumber from messages";

        queryStatement = connection.createStatement();
        ResultSet rs = queryStatement.executeQuery(getMessagesString);

        JSONArray jsoniarray = new JSONArray();

        while (rs.next()) {
            JSONObject obj = new JSONObject();
            obj.put("nickname", rs.getString("nickname"));
            obj.put("latitude", rs.getDouble("latitude"));
            obj.put("longitude", rs.getDouble("longitude"));
            obj.put("sent", rs.getString("sent"));
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
                    "nickname TEXT NOT NULL," +
                    "latitude REAL," +
                    "longitude REAL," +
                    "sent INT NOT NULL," +
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
