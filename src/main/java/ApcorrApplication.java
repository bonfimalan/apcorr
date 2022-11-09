import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.api.services.classroom.model.Attachment;
import com.google.api.services.classroom.model.Course;
import com.google.api.services.classroom.model.CourseWork;
import com.google.api.services.classroom.model.DriveFile;
import com.google.api.services.classroom.model.Student;
import com.google.api.services.classroom.model.StudentSubmission;

import dto.CourseWorkDTO;
import dto.DriveFileDTO;
import dto.StudentSubmitionDTO;
import google.ClassroomAPI;
import util.ApcorrUtil;

import javafx.application.Application;
import javafx.stage.Stage;

public class ApcorrApplication extends Application{
  // private static CourseWorkDTO courseWorkDTO;

  public void start(Stage stagePrimario) throws Exception {
    stagePrimario.show();
  }

  public static void main(String[] args) throws Exception {
    launch(args);
    /*
    ClassroomAPI classAPI = new ClassroomAPI();
    List<Course> courses = null;
    Scanner scanner = new Scanner(System.in);

    // requests the lit of courses
    courses = classAPI.getCourses();

    for (int i = 0; i < courses.size(); i++) {
      System.out.println(i + "\t" + courses.get(i).getName() + "\t" + courses.get(i).getId());
    }

    System.out.println("=====================================");

    // gets the chose course id
    System.out.print("Type the name of the course that you want: ");
    int courseIndex = scanner.nextInt();
    String courseId = courses.get(courseIndex).getId();

    // requests the course works of a couse
    List<CourseWork> courseWorks = classAPI.getCourseWorks(courseId);

    for (int i = 0; i < courseWorks.size(); i++) {
      CourseWork cw = courseWorks.get(i);
      if (cw.getWorkType().equals("ASSIGNMENT") && cw.getMaxPoints() != null)
        System.out.println(i + "\t" + cw.getTitle());
    }

    // gets the chose course id
    System.out.print("Type the name of the course work that you want: ");
    int courseWorkIndex = scanner.nextInt();
    String courseWorkId = courseWorks.get(courseWorkIndex).getId();
    String courseWorkTitle = courseWorks.get(courseWorkIndex).getTitle();
     
    CourseWorkDTO courseWorkDTO = generateCourseWorkDTO(courseId, courseWorkId,
    courseWorkTitle, classAPI);
    ApcorrUtil util = new ApcorrUtil(courseWorkDTO);
    util.execute();

    /*for (CourseWork courseWork : courseWorks) {
      if (courseWork.getWorkType().equals("ASSIGNMENT") && courseWork.getMaxPoints() != null) {
        CourseWorkDTO courseWorkDTO = generateCourseWorkDTO(courseId, courseWork.getId(), courseWork.getTitle(),
            classAPI);
        ApcorrUtil util = new ApcorrUtil(courseWorkDTO);
        util.execute();
      }
    }*/

    // process the courseWorkDTO here

    //scanner.close();
    
  }

  public static CourseWorkDTO generateCourseWorkDTO(String courseId, String courseWorkId, String courseTitle,
      ClassroomAPI classAPI) {
    CourseWorkDTO courseWorkDTO = new CourseWorkDTO(courseWorkId, courseId, courseTitle);
    List<StudentSubmission> studentSubmissions = classAPI.getStudentSubmissions(courseWorkDTO.getCourseId(),
        courseWorkDTO.getId());
    List<StudentSubmitionDTO> studentSubmitionDTOs = new ArrayList<>();
    for (StudentSubmission ss : studentSubmissions) {
      try {
        studentSubmitionDTOs.add(generateSubmitionDTO(ss, classAPI));
      } catch (Exception e) {
        System.err.println(e.getMessage());
      }
    }

    courseWorkDTO.setSubmissions(studentSubmitionDTOs);
    return courseWorkDTO;
  }

  public static StudentSubmitionDTO generateSubmitionDTO(StudentSubmission ss, ClassroomAPI classAPI) throws Exception {
    // crates a new student submition DTO with the id
    StudentSubmitionDTO studentSubmitionDTO = new StudentSubmitionDTO(ss.getUserId());
    // used to get the name of the student, used to create the directory
    Student student = classAPI.getStudentInfo(ss.getCourseId(), ss.getUserId());
    // sets the student name
    studentSubmitionDTO.setStudentName(student.getProfile().getName().getFullName());

    // get the attachments from the student submition
    List<Attachment> attachments = ss.getAssignmentSubmission().getAttachments();
    if (attachments == null)
      throw new Exception("Student don't have attachments!");

    studentSubmitionDTO.setDriveFiles(generatDriveFileDTOs(attachments));

    return studentSubmitionDTO;
  }

  public static List<DriveFileDTO> generatDriveFileDTOs(List<Attachment> attachments) {
    List<DriveFileDTO> lFileDTOs = new ArrayList<>();
    DriveFile driveFileTempReference;

    for (Attachment at : attachments) {
      driveFileTempReference = at.getDriveFile();
      lFileDTOs.add(new DriveFileDTO(driveFileTempReference.getId(), driveFileTempReference.getTitle()));
    }

    return lFileDTOs;
  }
}
