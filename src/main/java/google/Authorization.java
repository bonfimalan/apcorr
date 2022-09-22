package google;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.classroom.ClassroomScopes;
import com.google.api.services.drive.DriveScopes;

import global.GlobalVariables;


public class Authorization {
  private static final String TOKENS_DIRECTORY_PATH = "tokens";
  private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
  private static final List<String> SCOPES = List.of(
    ClassroomScopes.CLASSROOM_COURSES_READONLY,
    ClassroomScopes.CLASSROOM_COURSEWORK_STUDENTS_READONLY,
    ClassroomScopes.CLASSROOM_STUDENT_SUBMISSIONS_STUDENTS_READONLY,
    ClassroomScopes.CLASSROOM_ROSTERS_READONLY,
    DriveScopes.DRIVE_READONLY
  );

  public static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
    InputStream in = Authorization.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
    if(in == null)
      throw new FileNotFoundException("Resource no found: " + CREDENTIALS_FILE_PATH);
    
    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(GlobalVariables.JSON_FACTORY, new InputStreamReader(in));

    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
      HTTP_TRANSPORT, GlobalVariables.JSON_FACTORY, clientSecrets, SCOPES)
      .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
      .setAccessType("offline")
      .build();
    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();

    return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
  }
}
