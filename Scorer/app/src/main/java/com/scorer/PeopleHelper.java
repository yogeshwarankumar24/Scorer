package com.scorer;

import android.*;
//import android.content.Context;
//import android.content.pm.PackageManager;
//import android.content.res.AssetManager;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.util.Log;
//
//import com.google.api.client.auth.oauth2.Credential;
//import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
//import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
//import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
//import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
//import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.client.util.ExponentialBackOff;
//import com.google.api.client.util.store.FileDataStoreFactory;
//import com.google.api.services.people.v1.People;
//import com.google.api.services.people.v1.PeopleScopes;
//import com.google.api.services.people.v1.model.EmailAddress;
//import com.google.api.services.people.v1.model.ListConnectionsResponse;
//import com.google.api.services.people.v1.model.Name;
//import com.google.api.services.people.v1.model.Person;
//import com.google.api.services.people.v1.model.PhoneNumber;
//import com.scorer.Activity.NewTeamActivity;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.UUID;
//import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
//import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
//import static com.facebook.FacebookSdk.getApplicationContext;
//import static com.scorer.PeopleActivity.emailid;
//import static com.scorer.PeopleActivity.objActivity;

public class PeopleHelper {

//    private static final List<String> SCOPES = Arrays.asList(PeopleScopes.CONTACTS_READONLY);
//
//    private static final String APPLICATION_NAME = "Scorer";
//    public static HttpTransport httpTransport;
//    public static JacksonFactory jsonFactory;
//    private static FileDataStoreFactory DATA_STORE_FACTORY;
//
//    public static People setUp(Context context, String serverAuthCode) throws IOException {
//        httpTransport = new NetHttpTransport();
//        try
//        {
//        jsonFactory = JacksonFactory.getDefaultInstance();
////        httpTransport = GoogleNetHttpTransport.newTrustedTransport();
//        DATA_STORE_FACTORY = new FileDataStoreFactory(PeopleActivity.Camerapath);
//
//        } catch (Throwable t) {
//            t.printStackTrace();
////            System.exit(1);
//        }
//
//        // Redirect URL for web based applications.
//        // Can be empty too.
////        String redirectUrl = "urn:ietf:wg:oauth:2.0:oob";
//
//        // Exchange auth code for access token
//        GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
//                httpTransport,
//                jsonFactory,
//                GoogleConstants.CLIENT_ID,
//                GoogleConstants.CLIENT_SECRET,
//                serverAuthCode,
//                GoogleConstants.REDIRECT_URI).execute();
//
//        Credential credential = authorize();
//        // credential can then be used to access Google services
//        return new People.Builder(httpTransport, jsonFactory, credential)
//                .setApplicationName(APPLICATION_NAME)
//                .build();
//    }
//    public static Credential authorize() throws IOException {
//        // Load client secrets.
//        AssetManager assetManager = objActivity.getAssets();
//        InputStream in = null;
//        try {
//            in = assetManager.open("client_secret.json");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
////        FileInputStream in = new FileInputStream("file:///android_asset/client_secret.json");
////        InputStreamReader inputStreamReader = new InputStreamReader(in);
////        InputStream in = PeopleHelper.class.getResourceAsStream("/client_secret.json");
//        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(in));
//
//        // Build flow and trigger user authorization request.
//        GoogleAuthorizationCodeFlow flow =
//                new GoogleAuthorizationCodeFlow.Builder(
//                        httpTransport, jsonFactory, clientSecrets, SCOPES)
////                        .setDataStoreFactory(DATA_STORE_FACTORY)
////                        .setAccessType("offline")
//                        .setApprovalPrompt("auto")
//                        .build();
//        GoogleTokenResponse tokenResponse = null;
//        GoogleCredential credential = null;
//        try {
//            tokenResponse = new GoogleAuthorizationCodeTokenRequest(
//                    httpTransport,
//                    jsonFactory,
//                    GoogleConstants.CLIENT_ID,
//                    GoogleConstants.CLIENT_SECRET,
//                    PeopleActivity.emailid,
//                    GoogleConstants.REDIRECT_URI).execute();
//            // Then, create a GoogleCredential object using the tokens from GoogleTokenResponse
//            credential = new GoogleCredential.Builder()
//                    .setClientSecrets(GoogleConstants.CLIENT_ID, GoogleConstants.CLIENT_SECRET)
//                    .setTransport(httpTransport)
//                    .setJsonFactory(jsonFactory)
//                    .build();
//
//            credential.setFromTokenResponse(tokenResponse);
//        } catch (IOException ex)
//        {
//ex.printStackTrace();
//
//            credential = new GoogleCredential.Builder()
//                    .setClientSecrets(GoogleConstants.CLIENT_ID, GoogleConstants.CLIENT_SECRET)
//                    .setTransport(httpTransport)
//                    .setJsonFactory(jsonFactory)
//                    .build();
//            credential.refreshToken();
//        }
//        tokenResponse.getRefreshToken();
//        credential.setFromTokenResponse(tokenResponse);
//
//        return credential;
//    }
//}
//
//// Then, create a GoogleCredential object using the tokens from GoogleTokenResponse
////        GoogleCredential credential = new GoogleCredential.Builder()
////                .setClientSecrets(GoogleConstants.CLIENT_ID, GoogleConstants.CLIENT_SECRET)
////                .setTransport(httpTransport)
////                .setJsonFactory(jsonFactory)
////                .build();
////
////        credential.setFromTokenResponse(tokenResponse);
//
////        String url = flow.newAuthorizationUrl().setRedirectUri(GoogleConstants.REDIRECT_URI).build();
////        Desktop.getDesktop().browse(new URI(url));
////        Desktop desktop = Desktop.getDesktop();
////        desktop.browse(url);
////        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
////        String code = br.readLine();
////        GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(GoogleConstants.REDIRECT_URI).execute();
////        GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
////                .setJsonFactory(jsonFactory)
////                .setClientSecrets(GoogleConstants.CLIENT_ID, GoogleConstants.CLIENT_SECRET)
////                .build()
////                .setFromTokenResponse(response);
////        String accessToken = credential.getAccessToken();
////        String refreshToken = credential.getRefreshToken();
////        Credential credential = flow.loadCredential(PeopleActivity.emailid);
////        GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(
////                objActivity, SCOPES)
////                .setBackOff(new ExponentialBackOff());
////        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
////        System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
//
//
//class PeoplesAsync extends AsyncTask<String, Void, List<String>> {
//
//
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//
////            updateUI();
//
//    }
//
//    @Override
//    protected List<String> doInBackground(String... params) {
//
//        List<String> nameList = new ArrayList<String>();
//
//        try {
//            People peopleService = PeopleHelper.setUp(PeopleActivity.this, params[0]);
//
//            ListConnectionsResponse response = peopleService.people().connections()
//                    .list("people/me")
//                    // This line's really important! Here's why:
//                    // http://stackoverflow.com/questions/35604406/retrieving-information-about-a-contact-with-google-people-api-java
//                    .setRequestMaskIncludeField("person.names,person.emailAddresses,person.phoneNumbers")
//                    .execute();
//            signOut();
//            List<Person> connections = response.getConnections();
//
//            for (Person person : connections) {
//                if (!person.isEmpty()) {
//                    List<Name> names = person.getNames();
//                    List<EmailAddress> emailAddresses = person.getEmailAddresses();
//                    List<PhoneNumber> phoneNumbers = person.getPhoneNumbers();
//
//                    if (phoneNumbers != null)
//                        for (PhoneNumber phoneNumber : phoneNumbers)
//                            Log.d(TAG, "phone: " + phoneNumber.getValue());
//
//                    if (emailAddresses != null)
//                        for (EmailAddress emailAddress : emailAddresses)
//                            Log.d(TAG, "email: " + emailAddress.getValue());
//
//                    if (names != null)
//                        for (Name name : names)
//                            nameList.add(name.getDisplayName());
//
//                }
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return nameList;
//    }
//
//
//    @Override
//    protected void onPostExecute(List<String> nameList) {
//        super.onPostExecute(nameList);
//
//        Log.d(TAG, "nameList: " + nameList);
////            adapter = new PeopleAdapter(nameList);
////            recyclerView.setLayoutManager(new LinearLayoutManager(PeopleActivity.this));
////            recyclerView.setAdapter(adapter);
//
//    }
//}
}