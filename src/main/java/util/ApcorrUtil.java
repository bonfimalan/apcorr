package util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import dto.CourseWorkDTO;
import dto.DriveFileDTO;
import dto.StudentSubmitionDTO;
import global.GlobalVariables;
import google.DriveAPI;

public class ApcorrUtil {
  private DriveAPI driveAPI;
  private CourseWorkDTO courseWorkDTO;
  private File rootDirectory;

  public ApcorrUtil(CourseWorkDTO courseWorkDTO) throws IOException, GeneralSecurityException {
    driveAPI = new DriveAPI();
    this.courseWorkDTO = courseWorkDTO;
  }

  public void execute() throws IOException {
    if(!createCourseWorkRootDirectory()) {
      System.out.println("aaaaaaaaaaaaaaaaaaaaaaa"); return;}
    if(!createStudentSubmitDirectories()) return;
  }

  private boolean createCourseWorkRootDirectory() {
    // creates a file with the name of the course work
    File apcorDirectory = new File(GlobalVariables.APCORR_PATH);
    System.out.println(courseWorkDTO.getTitle());
    String title = Util.removeWinFileNameSpecialCarateres(courseWorkDTO.getTitle());
    rootDirectory = new File(apcorDirectory, title);
    return rootDirectory.mkdir();
  }

  private boolean createStudentSubmitDirectories() throws IOException {
    List<StudentSubmitionDTO> studentSubmissionsList = courseWorkDTO.getSubmissions();
    for(StudentSubmitionDTO ssdto : studentSubmissionsList) {
      // create a directory in the rootDirectory with the name of the student
      File studentDirectory = new File(rootDirectory, ssdto.getStudentName());
      // tries to create the directory
      if (!studentDirectory.mkdir()) return false;
      for(DriveFileDTO fileDTO : ssdto.getDriveFiles()) {
        downLoadAndSaveFiles(studentDirectory, fileDTO);
      }
    }

    return true;
  }

  private void downLoadAndSaveFiles(File studentDirectory, DriveFileDTO fileDTO) throws IOException {
    ByteArrayOutputStream byteArrayOutputStream = driveAPI.downloadFile(fileDTO.getId());
    if (byteArrayOutputStream == null) return;

    File studentFile = new File(studentDirectory, fileDTO.getTitle());

    FileOutputStream fileOutputStream = new FileOutputStream(studentFile);

    fileOutputStream.write(byteArrayOutputStream.toByteArray());

    fileOutputStream.close();
  }
}
