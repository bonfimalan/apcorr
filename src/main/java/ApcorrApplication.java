import java.util.List;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.classroom.Classroom;
import com.google.api.services.classroom.model.Course;
import com.google.api.services.classroom.model.ListCoursesResponse;

import global.GlobalVariables;
import google.Authorization;
import google.ClassroomAPI;

public class ApcorrApplication {

  public static void main(String[] args) throws Exception {
    ClassroomAPI classAPI = new ClassroomAPI();
    List<Course> courses = classAPI.getCourses();
    for(Course course : courses) {
      System.out.println(course.getName() + "\t" + course.getId());
    }
  } 
}
