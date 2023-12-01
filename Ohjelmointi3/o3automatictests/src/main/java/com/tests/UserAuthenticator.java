package com.tests;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import org.json.JSONObject;

import com.sun.net.httpserver.BasicAuthenticator;

public class UserAuthenticator extends BasicAuthenticator  {
    private MessageDatabase db = null;

    public UserAuthenticator() {
        super("warning");
        db = MessageDatabase.getInstance();
    }

    
     public boolean checkCredentials(String username, String password) {
       try {
        return db.checkIfUserExists(username, password);
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
    }

    public boolean addUser(JSONObject user) throws SQLException  {
       return db.setUser(user);
        

    }
}
