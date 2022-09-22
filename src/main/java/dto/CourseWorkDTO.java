package dto;

import java.util.List;

public class CourseWorkDTO {
  private String id;
  private String courseId;
  private String title;
  private List<StudentSubmitionDTO> submissions;

  public CourseWorkDTO(String id, String courseId, String title) {
    this.id = id;
    this.courseId = courseId;
    this.title = title;
  }

  public String getId() {
    return id;
  }

  public String getCourseId() {
    return courseId;
  }
  
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<StudentSubmitionDTO> getSubmissions() {
    return submissions;
  }

  public void setSubmissions(List<StudentSubmitionDTO> submissions) {
    this.submissions = submissions;
  }
}
