package com.pacificnational.adal;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.naming.ServiceUnavailableException;

import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.AuthenticationException;
import com.microsoft.aad.adal4j.AuthenticationResult;

public class PublicClient {

    //private final static String AUTHORITY = "https://login.windows.net/common";
    //private final static String CLIENT_ID = "9ba1a5c7-f17a-4de9-a1f1-6178c8d51223";

    private String authority = "";
    public String getAuthority() { return authority; }
    public void setAuthority(String authority) { this.authority = authority; }

    private String clientId = "";
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }

    public Object authenticateCredentials(String username, String password) throws Exception {
        try {
            // Authenticate
            AuthenticationResult result = getAccessTokenFromUserCredentials(username, password);

            // Return JWT object to mule flow
            return result;
        } catch (ExecutionException e) {
            // Message contains JSON message from ADAL

            if (e.getCause() != null && e.getCause() instanceof AuthenticationException)
                return e.getCause().getMessage();
            else
                throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    /*
    public static void main(String args[]) throws Exception {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                System.in))) {
            System.out.print("Enter username: ");
            String username = br.readLine();
            System.out.print("Enter password: ");
            String password = br.readLine();

            AuthenticationResult result = getAccessTokenFromUserCredentials(
                    username, password);
            System.out.println("Access Token - " + result.getAccessToken());
            System.out.println("Refresh Token - " + result.getRefreshToken());
            System.out.println("ID Token - " + result.getIdToken());
        }
    }
    */

    private AuthenticationResult getAccessTokenFromUserCredentials(
            String username, String password) throws Exception {
        AuthenticationContext context = null;
        AuthenticationResult result = null;
        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(1);
            context = new AuthenticationContext(authority, false, service);
            Future<AuthenticationResult> future = context.acquireToken(
                    "https://graph.windows.net", clientId, username, password,
                    null);
            result = future.get();
        } finally {
            service.shutdown();
        }

        if (result == null) {
            throw new ServiceUnavailableException(
                    "authentication result was null");
        }
        return result;
    }
}
