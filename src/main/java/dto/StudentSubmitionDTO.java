package dto;

import java.util.List;

public class StudentSubmitionDTO {
  private String studentId;
  private String studentName;
  private List<DriveFileDTO> driveFiles;
  
  public StudentSubmitionDTO(String studentId) {
    this.studentId = studentId;
  }

  public String getStudentId() {
    return studentId;
  }

  public String getStudentName() {
    return studentName;
  }

  public void setStudentName(String studentName) {
    this.studentName = studentName;
  }

  public List<DriveFileDTO> getDriveFiles() {
    return driveFiles;
  }

  public void setDriveFiles(List<DriveFileDTO> driveFiles) {
    this.driveFiles = driveFiles;
  }  
}
