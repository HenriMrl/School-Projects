package com.tests;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;

public class PostSecureJSONMessageWithTImeAndDescription {
    
    private static TestClient testClient = null;
    private static TestSettings testSettings = null;


    PostSecureJSONMessageWithTImeAndDescription(){
        testSettings = new TestSettings();
        TestSettings.readSettingsXML("testconfigw5.xml");
        testClient = new TestClient(testSettings.getCertificate(), testSettings.getServerAddress(), testSettings.getNick(), testSettings.getPassword());

    }

    @Test
    @BeforeAll
    @DisplayName("Setting up the test environment")
    public static void initialize() {
        System.out.println("initialized week 5 tests");
    }
    
    @Test
    @AfterAll
    public static void teardown() {
        System.out.println("Testing finished.");
    }

    @Test 
    @Order(1)
    @DisplayName("Testing server connection")
    void testHTTPServerConnection() throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        System.out.println("Testing server connection");
        int result = testClient.testHTTPSConnection();
        assertTrue(result > 1);
    }

    @Test 
    @Order(2)
    @DisplayName("Testing registering an user")
    void testRegisterUser() throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        System.out.println("Testing registering an user");
        int result = testClient.testRegisterUserJSON("jokurandom", "jokurandompsw", "joku@random.com");
        System.out.println(result);
        assertTrue(200 <= result && result <= 299);
    }

    @Test 
    @Order(3)
    @DisplayName("Testing registering same user again - must fail")
    void testRegisterUserAgain() throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        System.out.println("Testing registering same user again - must fail");
        testClient.testRegisterUserJSON("randomi1", "randomi1", "random@random.com");
        int result = testClient.testRegisterUserJSON("randomi1", "randomi1", "random@random.com");
        System.out.println(result);
        assertFalse(200 <= result && result <= 299);
    }

    @Test 
    @Order(4)
    @DisplayName("Testing sending message to server")
    void testSendMessage() throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        System.out.println("Testing sending message to server");
        testClient.testRegisterUserJSON(testSettings.getNick(), testSettings.getPassword(), testSettings.getEmail());
        JSONObject obj = new JSONObject();


        ZonedDateTime now =ZonedDateTime.now(ZoneId.of("UTC"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd'T'HH:mm:ss.SSSX");

        String dateText = now.format(formatter);

        double coord1 = ThreadLocalRandom.current().nextDouble(0,180);
        double coord2 = ThreadLocalRandom.current().nextDouble(0,180);


        obj.put("nickname", "Seppo");
        obj.put("latitude", coord1);
        obj.put("longitude", coord2);
        obj.put("sent", dateText);
        obj.put("dangertype", "Moose");
        int result = testClient.testJSONHTTPSMessage(obj);
        System.out.println(result);
        assertTrue(200 <= result && result <= 299);
        String response = testClient.getlatestJSONHTTPSMessages();
        JSONArray obj2 = new JSONArray(response);
        System.out.println(response);
        System.out.println("object is" + obj2);
        //Jokin joukko jsonobjekteja, joista pitää tunnistaa lähetetty objekti...
        boolean isSame = false;
        JSONObject obj3 = new JSONObject();
        for(int i=0; i<obj2.length(); i++){
            obj3 = obj2.getJSONObject(i);

            System.out.println(obj3);
            if(obj.similar(obj3))
            {isSame = true; break;}
        }

        assertTrue(isSame);

    }

    @Test 
    @Order(5)
    @DisplayName("Testing characters")
    void testCharacters() throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        System.out.println("Testing characters in message");
        testClient.testRegisterUserJSON(testSettings.getNick(), testSettings.getPassword(), testSettings.getEmail());
        JSONObject obj = new JSONObject();

        ZonedDateTime now =ZonedDateTime.now(ZoneId.of("UTC"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd'T'HH:mm:ss.SSSX");

        String dateText = now.format(formatter);

        double coord1 = ThreadLocalRandom.current().nextDouble(0,180);
        double coord2 = ThreadLocalRandom.current().nextDouble(0,180);

        obj.put("nickname", "Matti Meikäläinen");
        obj.put("latitude", coord1);
        obj.put("longitude", coord2);
        obj.put("sent", dateText);
        obj.put("dangertype", "Reindeer");
        int result = testClient.testJSONHTTPSMessage(obj);
        System.out.println(result);
        assertTrue(200 <= result && result <= 299);
        String response = testClient.getlatestJSONHTTPSMessages();
        JSONArray obj2 = new JSONArray(response);
        System.out.println(response);
        System.out.println("object is" + obj2);
        //Jokin joukko jsonobjekteja, joista pitää tunnistaa lähetetty objekti...
        boolean isSame = false;
        JSONObject obj3 = new JSONObject();
        for(int i=0; i<obj2.length(); i++){
            obj3 = obj2.getJSONObject(i);

            System.out.println(obj3);
            if(obj.similar(obj3))
            {isSame = true; break;}
        }

        assertTrue(isSame);
    }

    @Test 
    @Order(6)
    @DisplayName("Testing message with additional contact information")
    void testCoordinateDescription() throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        System.out.println("Testing message with additional contact information");
        testClient.testRegisterUserJSON(testSettings.getNick(), testSettings.getPassword(), testSettings.getEmail());
        JSONObject obj = new JSONObject();

        ZonedDateTime now =ZonedDateTime.now(ZoneId.of("UTC"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd'T'HH:mm:ss.SSSX");

        String dateText = now.format(formatter);

        obj.put("sent", dateText);

        double coord1 = ThreadLocalRandom.current().nextDouble(0,180);
        double coord2 = ThreadLocalRandom.current().nextDouble(0,180);

        obj.put("nickname", "Seppo");
        obj.put("latitude", coord1);
        obj.put("longitude", coord2);
        obj.put("dangertype", "Reindeer");
        obj.put("areacode", "358");
        obj.put("phonenumber", "0406549870");
        int result = testClient.testJSONHTTPSMessage(obj);
        System.out.println(result);
        assertTrue(200 <= result && result <= 299);
        String response = testClient.getlatestJSONHTTPSMessages();
        JSONArray obj2 = new JSONArray(response);
        System.out.println(response);
        System.out.println("object is" + obj2);
        //Jokin joukko jsonobjekteja, joista pitää tunnistaa lähetetty objekti...
        boolean isSame = false;
        JSONObject obj3 = new JSONObject();
        for(int i=0; i<obj2.length(); i++){
            obj3 = obj2.getJSONObject(i);

            //System.out.println(comparison);
            if(obj.similar(obj3))
            {isSame = true; break;}
        }

        assertTrue(isSame);
    }



    @Test 
    @Order(7)
    @DisplayName("Testing faulty message")
    void testFaultyMessage() throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        System.out.println("Testing faulty attributes in message");
        testClient.testRegisterUserJSON(testSettings.getNick(), testSettings.getPassword(), testSettings.getEmail());
        JSONObject obj = new JSONObject();

        ZonedDateTime now =ZonedDateTime.now(ZoneId.of("UTC"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd'T'HH:mm:ss.SSSX");

        String dateText = now.format(formatter);

        obj.put("nickname", "Virhe");
        obj.put("latitude", "stuff");
        obj.put("longitude", 1);
        obj.put("sent", dateText);
        obj.put("dangertype", "Reindeer");
        int result = testClient.testJSONHTTPSMessage(obj);
        System.out.println(result);
        assertFalse(200 <= result && result <= 299);
        System.out.println("Test failed, latitude and longitude weren't double, make sure that you check the values in your code");
        
    }

    @Test 
    @Order(8)
    @DisplayName("Testing faulty time")
    void testFaultyTime() throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        System.out.println("Testing faulty attributes in message");
        testClient.testRegisterUserJSON(testSettings.getNick(), testSettings.getPassword(), testSettings.getEmail());
        JSONObject obj = new JSONObject();

        ZonedDateTime now =ZonedDateTime.now(ZoneId.of("UTC"));

        double coord1 = ThreadLocalRandom.current().nextDouble(0,180);
        double coord2 = ThreadLocalRandom.current().nextDouble(0,180);

        obj.put("nickname", "Virhe");
        obj.put("latitude", coord2);
        obj.put("longitude", coord1);
        obj.put("sent", now);
        obj.put("dangertype", "Reindeer");
        int result = testClient.testJSONHTTPSMessage(obj);
        System.out.println(result);
        assertFalse(200 <= result && result <= 299);
        System.out.println("Test failed, time format was wrong, make sure that you check that the time is correct in your code");
        
    }


    @Test 
    @Order(9)
    @DisplayName("Sending empty string to registration - must fail")
    void testRegisterRubbish() throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        System.out.println("Testing sending empty string to registration");
        int result = testClient.testRegisterUserJSON("", "", "");
        System.out.println(result);
        assertFalse(200 <= result && result <= 299);

    }

    @Test 
    @Order(10)
    @DisplayName("Sending GET to registration - must fail")
    void testRegisterGet() throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        System.out.println("Sending GET to registration - must fail");
        int result = testClient.testRegisterGet();
        System.out.println(result);
        assertFalse(200 <= result && result <= 299);

    }


    @RepeatedTest(200)
    @Execution(ExecutionMode.CONCURRENT)
    @Order(11)
    @DisplayName("Server load test with large amount of messages")
    void testServerLoading() throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        System.out.println("Server load test with large amount of messages");
        testClient.testRegisterUserJSON(testSettings.getNick(), testSettings.getPassword(), testSettings.getEmail());
        JSONObject obj = new JSONObject();

        ZonedDateTime now =ZonedDateTime.now(ZoneId.of("UTC"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd'T'HH:mm:ss.SSSX");

        String dateText = now.format(formatter);

        double coord1 = ThreadLocalRandom.current().nextDouble(0,180);
        double coord2 = ThreadLocalRandom.current().nextDouble(0,180);

        obj.put("nickname", "Seppo");
        obj.put("latitude", coord1);
        obj.put("longitude", coord2);
        obj.put("sent", dateText);
        obj.put("dangertype", "Moose");
        int result = testClient.testJSONHTTPSMessage(obj);
        assertTrue(200 <= result && result <= 299);
    }


    @Test 
    @Order(12)
    @DisplayName("Testing faulty message 2 - not supported animal type")
    void testFaultyMessage2() throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        System.out.println("Testing faulty animal type in message");
        testClient.testRegisterUserJSON(testSettings.getNick(), testSettings.getPassword(), testSettings.getEmail());
        JSONObject obj = new JSONObject();

        ZonedDateTime now =ZonedDateTime.now(ZoneId.of("UTC"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd'T'HH:mm:ss.SSSX");

        String dateText = now.format(formatter);

        double coord1 = ThreadLocalRandom.current().nextDouble(0,180);
        double coord2 = ThreadLocalRandom.current().nextDouble(0,180);

        obj.put("nickname", "Seppo");
        obj.put("latitude", coord1);
        obj.put("longitude", coord2);
        obj.put("sent", dateText);
        obj.put("dangertype", "NotAReindeer");
        int result = testClient.testJSONHTTPSMessage(obj);
        System.out.println(result);
        assertFalse(200 <= result && result <= 299);
        System.out.println("Test failed, a non-supported value was passed for the dangertype");
        
    }

    @Test 
    @Order(13)
    @DisplayName("Testing faulty message 3 - not json")
    void testFaultyMessage3() throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        System.out.println("Testing faulty json in message");
        testClient.testRegisterUserJSON(testSettings.getNick(), testSettings.getPassword(), testSettings.getEmail());

        int result = testClient.testFaultyMessage("{I am not a json}");
        System.out.println(result);
        assertFalse(200 <= result && result <= 299);
        System.out.println("Test failed, a faulty json was supplied");
        
    }


    @Test
    @Order(14)
    @DisplayName("Testing faulty credentials")
    void testFaultyCredentials() throws IOException, KeyManagementException, KeyStoreException, CertificateException,
            NoSuchAlgorithmException {
        System.out.println("Testing faulty credentials in message");
        testClient.testRegisterUserJSON(testSettings.getNick(), testSettings.getPassword(), testSettings.getEmail());
        JSONObject obj = new JSONObject();

        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd'T'HH:mm:ss.SSSX");

        String dateText = now.format(formatter);

        double coord1 = ThreadLocalRandom.current().nextDouble(0, 180);
        double coord2 = ThreadLocalRandom.current().nextDouble(0, 180);

        obj.put("nickname", "Seppo");
        obj.put("latitude", coord1);
        obj.put("longitude", coord2);
        obj.put("sent", dateText);
        obj.put("dangertype", "Reindeer");

        int result = testClient.testJSONHTTPSMessageWithDifferentCredentials(obj, "Matti:NotMattisPassowrd");
        System.out.println(result);
        assertFalse(200 <= result && result <= 299, "Test failed, wrong password was used");
    }
    
}
