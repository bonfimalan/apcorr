package google;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.drive.Drive;

import global.GlobalVariables;

public class DriveAPI {
  private Drive service;

  public DriveAPI() throws IOException, GeneralSecurityException {
    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

    service = new Drive.Builder(HTTP_TRANSPORT, GlobalVariables.JSON_FACTORY,
        Authorization.getCredentials(HTTP_TRANSPORT))
        .setApplicationName(GlobalVariables.APPLICATION_NAME)
        .build();
  }

  public ByteArrayOutputStream downloadFile(String fileId) throws IOException {
    try {
      OutputStream outputStream = new ByteArrayOutputStream();

      service.files().get(fileId)
        .executeMediaAndDownloadTo(outputStream);
      
      return (ByteArrayOutputStream) outputStream;
    } catch(GoogleJsonResponseException e) {
      System.err.println(e.getDetails());
      return null;
    }
  }
}
