package util;

public class Util {
  public static char[] WIN_FILE_NAME_SPECIAL_CARACTERES = { '\\', '/', '|', '<', '>', '*', ':', '"', '?' };

  public static String removeWinFileNameSpecialCarateres(String fileName) {
    char[] fileNameCharArray = fileName.toCharArray();
    char[] newFileNameCharArray = new char[fileNameCharArray.length];
    char charFileNamePointer;

    for (int i = 0; i < fileNameCharArray.length; i++) {
      charFileNamePointer = fileNameCharArray[i];
      if (isWinFileNameSpecialCarater(charFileNamePointer))
        newFileNameCharArray[i] = ' ';
      else
        newFileNameCharArray[i] = charFileNamePointer;
    }

    return new String(newFileNameCharArray);
  }

  public static boolean isWinFileNameSpecialCarater(char c) {
    for (char specialCarater : WIN_FILE_NAME_SPECIAL_CARACTERES)
      if (c == specialCarater)
        return true;
    return false;
  }
}
