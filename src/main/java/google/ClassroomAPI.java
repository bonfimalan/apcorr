package google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.classroom.Classroom;
import com.google.api.services.classroom.model.Course;

import global.GlobalVariables;

public class ClassroomAPI {
  private Classroom service;

  public ClassroomAPI() throws GeneralSecurityException, IOException {
    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

    service = new Classroom.Builder(HTTP_TRANSPORT, GlobalVariables.JSON_FACTORY,
        Authorization.getCredentials(HTTP_TRANSPORT))
        .setApplicationName(GlobalVariables.APPLICATION_NAME)
        .build();
  }

  public List<Course> getCourses() {
    List<Course> courses = null;
    try {
      courses = service
          .courses()
          .list()
          .execute()
          .getCourses();
    } catch (IOException e) {
      System.err.println(e.getMessage());
      // e.printStackTrace();
    }
    return courses;
  }

  public List<String> getCourseWorks(String courseId) {
    try {
      service.courses().courseWork().list(courseId);
    } catch (IOException e) {
      System.err.println(e.getMessage());
      // e.printStackTrace();
    }
    
    return null;
  }

  public List<String> getClassWork() {
    return null;
  }

  public void getStudentInfo() {

  }
}
