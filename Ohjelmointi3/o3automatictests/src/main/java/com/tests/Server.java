package com.tests;
import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.*;
import java.util.concurrent.Executors;

import javax.net.ssl.*;
import java.security.*;

/**
 * Hello world!
 *
 */
public class Server {
    
    private static SSLContext serverSSLContext(String[] arg) throws Exception {
        String password = "salasana";
        String path = "C:/Users/henri/ohjelmointi3/group-0180-project/keystore.jks";
        if(arg.length >= 2) {
            password = arg[1];
            path = arg[0];
        }
        char[] passphrase = password.toCharArray();
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream(path), passphrase);

        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, passphrase);

       TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
       tmf.init(ks);

       SSLContext ssl = SSLContext.getInstance("TLS");
       ssl.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
       return ssl;
    }

    

    public static void main( String[] args ) throws Exception {
        try {
        HttpsServer server = HttpsServer.create(new InetSocketAddress(8001), 0); 
        SSLContext sslContext = serverSSLContext(args);
        server.setHttpsConfigurator(new HttpsConfigurator(sslContext) {
            public void configure (HttpsParameters params) {
                InetSocketAddress remote = params.getClientAddress();
                SSLContext c = getSSLContext();
                SSLParameters sslparams = c.getDefaultSSLParameters();
                params.setSSLParameters(sslparams);
            }
        });
        HttpContext context = server.createContext("/warning", new Messagehandler());
        UserAuthenticator userAuthenticator = new UserAuthenticator();
        context.setAuthenticator(userAuthenticator);
        
        
        server.createContext("/registration", new RegistrationHandler(userAuthenticator));

        server.setExecutor(Executors.newCachedThreadPool());
        server.start();

        }  catch (Exception e) {
           e.printStackTrace();
        }
    }
}
