package global;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

public class GlobalVariables {
  public static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
  public static final String APPLICATION_NAME = "Apcorr";
}
