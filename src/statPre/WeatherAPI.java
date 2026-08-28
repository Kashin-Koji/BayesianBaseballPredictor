package statPre;

import java.util.Map;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class WeatherAPI {

    // Convert JSON to Map//
    //Gson is an open-source Java library to serialize and deserialize Java objects to JSON//
    public static Map<String, Object> jsonToMap(String str) {
        return new Gson().fromJson(
            str, 
            new TypeToken<HashMap<String, Object>>() {}.getType()
        );
              
      }

}
