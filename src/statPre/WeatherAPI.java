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
    // Gson is an open-source Java library to serialize and deserialize Java objects
    // to JSON//
    public static Map<String, Object> jsonToMap(String str) {
        return new Gson().fromJson(
                str,
                new TypeToken<HashMap<String, Object>>() {
                }.getType());

    }

    public static Map<String, Object> getWeather(String location, String apiKey) throws IOException {

        String urlWeather = "https://api.openweathermap.org/data/2.5/weather?id=" + location +
                "&appid=" + apiKey + "&units=imperial";
        // Build The String//
        StringBuilder data = new StringBuilder();
        // Create The URL//
        URL url = new URL(urlWeather);
        // Open The Connection//
        URLConnection conn = url.openConnection();
        // Read In The Results As A String//
        BufferedReader rd = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        String line;
        while ((line = rd.readLine()) != null) {
            data.append(line);
        }
        rd.close();

        return jsonToMap(data.toString());
    }

    public static Map<String, String> createMap() {

        Map<String, String> cities = new HashMap<>();
        // Key:Value// //Key is has to be unique, but Value can be same-2 teams in
        // Chicago//
        cities.put("Milwaukee Brewers", "5263045");
        cities.put("Los Angeles Angels", "5323810");
        cities.put("St. Louis Cardinals", "4407066");
        cities.put("Arizona Diamondbacks", "5308655");
        cities.put("New York Mets", "5133268");
        cities.put("Philadelphia Phillies", "5205788");
        cities.put("Detroit Tigers", "4990729");
        cities.put("Colorado Rockies", "5419384");
        cities.put("Los Angeles Dodgers", "5368361");
        cities.put("Boston Red Sox", "4930956");
        cities.put("Texas Rangers", "4671240");
        cities.put("Cincinnati Reds", "4508722");
        cities.put("Chicago White Sox", "4887398");
        cities.put("Kansas City Royals", "4393217");
        cities.put("Miami Marlins", "4164138");
        cities.put("Houston Astros", "4699066");
        cities.put("Washington Nationals", "4140963");
        cities.put("San Francisco Giants", "5391959");
        cities.put("Baltimore Orioles", "4347778");
        cities.put("San Diego Padres", "5391811");
        cities.put("Pittsburgh Pirates", "5206379");
        cities.put("Cleveland Guardians", "5150529");
        cities.put("Oakland Athletics", "5378538");
        cities.put("Toronto Blue Jays", "6167865");
        cities.put("Seattle Mariners", "5809844");
        cities.put("Minnesota Twins", "5037649");
        cities.put("Tampa Bay Rays", "4171563");
        cities.put("Atlanta Braves", "4180439");
        cities.put("Chicago Cubs", "4887398");
        cities.put("New York Yankees", "5110253");

        return cities;

    }
}
