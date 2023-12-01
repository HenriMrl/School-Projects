package com.tests;

import com.sun.net.httpserver.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;
import org.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class Messagehandler implements HttpHandler {
    private MessageDatabase db = MessageDatabase.getInstance();

    private String contentType = "";
    private String response = "";
    private int code = 200;
    private JSONObject obj = null;

    ArrayList<WarningMessage> messages = new ArrayList<WarningMessage>();

    public void handle(HttpExchange t) throws IOException {
        if (t.getRequestMethod().equalsIgnoreCase("POST")) {
            handleResponsePOST(t);
        } else if (t.getRequestMethod().equalsIgnoreCase("GET")) {
            try {
                handleResponseGET(t);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            handleResponse(t);
        }
        byte[] bytes = response.getBytes("UTF-8");
        t.sendResponseHeaders(code, bytes.length);
        OutputStream outputStream = t.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    private void handlejson(HttpExchange t) throws IOException, SQLException {
        InputStream stream = t.getRequestBody();

        String newUser = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8)).lines()
                .collect(Collectors.joining("\n"));

        stream.close();
        if (newUser == null || newUser.length() == 0) {

            code = 412;
            response = "no user credentials";

        } else {
            try {
                obj = new JSONObject(newUser);
            } catch (JSONException e) {
                System.out.println("json parse error, faulty user json");
            }
            if (!(obj.getString("dangertype").equals("Deer") || obj.getString("dangertype").equals("Moose")
                    || obj.getString("dangertype").equals("Reindeer") || obj.getString("dangertype").equals("Other"))) {
                code = 422;
                response = "dangertype not proper";
            } else if (obj.getString("nickname").length() == 0 || obj.getString("dangertype").length() == 0) {
                code = 413;
                response = "user credentials not proper";
            } else {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd'T'HH:mm:ss.SSSX");
                    ZonedDateTime zonedDateTime = ZonedDateTime.parse(obj.getString("sent"), formatter);
                    db.setMessage(obj);
                    code = 200;
                    response = "warning message added";

                } catch (DateTimeParseException e) {
                    code = 500;
                    response = "parse exception";

                }

            }
        }

    }

    private void handleResponsePOST(HttpExchange t) throws IOException {
        Headers headers = t.getRequestHeaders();
        try {
            System.out.println("enterint try");
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
            } else {
                code = 407;
                response = "content type is not application/json";
            }

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            code = 500;
            e.printStackTrace();
            response = "Internal server error";
        }
    }

    private void handleResponseGET(HttpExchange httpExchange) throws IOException, SQLException {
        JSONArray joo = db.getMessages();
        response = joo.toString();

    }

    private void handleResponse(HttpExchange httpExchange) throws IOException {
        code = 401;
        response = "Not supported";
    }
}
