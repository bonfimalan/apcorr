package google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.classroom.Classroom;
import com.google.api.services.classroom.model.Course;
import com.google.api.services.classroom.model.CourseWork;
import com.google.api.services.classroom.model.Student;
import com.google.api.services.classroom.model.StudentSubmission;

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

  public List<CourseWork> getCourseWorks(String courseId) {
    try {
      return service
          .courses()
          .courseWork()
          .list(courseId)
          .execute()
          .getCourseWork();
    } catch (IOException e) {
      System.err.println(e.getMessage());
      return null;
      // e.printStackTrace();
    }
  }

  public List<StudentSubmission> getStudentSubmissions(String courseId, String courseWorkId) {
    try {
      return service
          .courses()
          .courseWork()
          .studentSubmissions()
          .list(courseId, courseWorkId)
          .execute()
          .getStudentSubmissions();
    } catch (IOException e) {
      System.err.println(e.getMessage());
      return null;
      // e.printStackTrace();
    }
  }

  public Student getStudentInfo(String courseId, String userId) {
    try {
      return service
          .courses()
          .students()
          .get(courseId, userId)
          .execute();
    } catch (IOException e) {
      System.err.println(e.getMessage());
      return null;
      //e.printStackTrace();
    }
  }
}
