package statPre;

import java.util.Map;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class WeatherAPI {

    // Convert JSON to Map//
    //Gson is an open-source Java library to serialize and deserialize Java objects to JSON//
    public static Map<String, Object> jsonToMap(String str) {
        return new Gson().fromJson(
            str, 
            new TypeToken<HashMap<String, Object>>() {}.getType()
        );
              
      }

    public static Map<String, Object> getWeather(String location, String apiKey) throws IOException{

        String urlWeather = "https://api.openweathermap.org/data/2.5/weather?id=" + location +
                                "&appid=" + apiKey + "&units=imperial";
        //Build The String//
        StringBuilder data = new StringBuilder();
        //Create The URL//
        URL url = new URL(urlWeather);
        //Open The Connection//
        URLConnection conn = url.openConnection();
        //Read In The Results As A String//
        BufferedReader rd = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        String line;
        while ((line = rd.readLine()) !=null) {
            data.append(line);
        }
        rd.close();
        
        return jsonToMap(data.toString()); 
    }
}


