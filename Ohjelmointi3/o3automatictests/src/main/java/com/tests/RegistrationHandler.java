package com.tests;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


public class RegistrationHandler implements HttpHandler {

    private String contentType = "";
    private String response = "";
    private int code = 200;
    private JSONObject obj = null;

    private UserAuthenticator UserAuthenticator;

    public RegistrationHandler(UserAuthenticator a) {
       this.UserAuthenticator = a;
    }


    @Override
    public void handle(HttpExchange t) throws IOException {
         handlerpost(t);
    }

    private void handlejson(HttpExchange t) throws IOException, SQLException {
        InputStream stream = t.getRequestBody();

        String newUser = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));

        stream.close();
        if(newUser == null || newUser.length() == 0){

            code = 412;
            response ="no user credentials";

        }else {
            try {
                obj = new JSONObject(newUser);
            } catch(JSONException e){
                System.out.println("json parse error, faulty user json");
            }

            if(obj.getString("username").length() == 0  || obj.getString("password").length() == 0){
                code = 413;
                response ="no proper user credentials";
            }else{

                System.out.println("registering user " + obj.getString("username") + " " + obj.getString("password"));
                Boolean result = UserAuthenticator.addUser(obj);
                if(result == false){
                    code = 405;
                    response ="user already exist";
                }else{

                    code = 200;
                    response = "User registered";
                }
            }

        }

    }

    private void handlerpost(HttpExchange t) throws IOException {
        Headers headers = t.getRequestHeaders();
        try {
            System.out.println("enterint try");
            if (t.getRequestMethod().equalsIgnoreCase("POST")) {
                System.out.println("POST detected");
                if (headers.containsKey("Content-Type")) {
                    contentType = headers.get("Content-Type").get(0);
                    System.out.println("Content-Type is available");
                } else {
                    System.out.println("No content type");
                    code = 411;
                    response = "No content type in request";
                }
                if (contentType.equalsIgnoreCase("application/json")) {
                    System.out.println("Content-type is application/json");
                    handlejson(t);
                    System.out.println("Registration successfull, writing response");
                }else {
                    code = 407;
                    response = "content type is not application/json";
                }
            }else{
                System.out.println("Other thatn POST detected");
                code = 401;
                response = "only POST is accepted";
            }

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            code = 500;
            e.printStackTrace();
            response = "Internal server error";

        }
        
            byte[] bytes = response.getBytes("UTF-8");
            t.sendResponseHeaders(code, bytes.length);
            OutputStream stream = t.getResponseBody();
            stream.write(response.getBytes());
            stream.close();
    }

}

