// **************************************************
// Title: StatsForWeather Class
// File: StatsForWeather.java
// Authors: Usoff Samantar/Honors Mentor/Professor Frank Seidel
// Description: In this Java file I have 
// created a database system where I store
// batter statistics, as well as use weather 
// data (temperature) currently to try to 
// predict per game batting average.
// **************************************************

package statPre;

//A package in Java is used to group related classes.
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.util.Arrays;
import java.util.ArrayList;

public class StatsForWeather {

   // Total player hits during optimal weather conditions (56-75) //
   int totalPlayerHits_Optimal = 0;
   // Bayes' Theorem = P(A|B) = (P(B|A) * P(A))/P(B) //
   public static ArrayList<BatterwStats> listOfPlayers = new ArrayList<>();
   private static ArrayList<NationalsPlayer> listOfGames = new ArrayList<NationalsPlayer>();
   private static int currentPlayer = -1;
   private static String[] battingSide = { "Right", "Left", "Switch" };

   public static void main(String[] args) throws IOException {

      String input = "";
      String[] editDatabase = { "Select option", "Open Batter file", "Add Batter", "Update Batter", "Predict Game",
            "List All Batters", "Save All Batter Stats", "Save Game Prediction", "Find Batter", "Exit Program" };
      String options = "";
      do {
         options = (String) JOptionPane.showInputDialog(null, "Please select a database option:", "Batter Database",
               JOptionPane.PLAIN_MESSAGE,
               null, editDatabase, editDatabase[0]);
         if (options == null) {
            // Handle the cancel/close action; for example, break out of the loop
            break;
         }
         switch (options) {
            case "Open Batter file":
               open();
               break;
            case "Add Batter":
               addBatter();
               break;
            case "Update Batter":
               updateStats();
               break;
            case "Predict Game":
               predict();
               break;
            case "List All Batters":
               for (BatterwStats batDisp : listOfPlayers) {
                  JOptionPane.showMessageDialog(null, "Nationals Batters:\n" + batDisp,
                        "Player #" + batDisp.getJerseyNumber(), JOptionPane.INFORMATION_MESSAGE, batDisp.getImage());
               }
               break;
            case "Save All Batter Stats":
               save(listOfPlayers);
               break;
            case "Save Game Prediction":
               savePredict();
               break;
            case "Find Batter":
               find();

         }
      } while (!options.equals(editDatabase[editDatabase.length - 1]));
      JOptionPane.showMessageDialog(null, "Logging Off.....");

   }// main

   public static void open() throws IOException {

      listOfPlayers = ExcelInputOutput.openPlayerExcel("statPre//NBLUP.xlsx");

   }

   public static void addBatter() {
      boolean flag;
      String input = "";
      BatterwStats np;
      do {
         np = new BatterwStats();
         np.setJerseyNumber(JOptionVCheck.getIntDialog("What jersey number does the batter wear?",
               "Batter's Jersey Number", "Please enter an integer (Example: 3)"));
         np.setLastName(JOptionVCheck.getTextDialog("What is the batters last name?", "Batter's Last Name",
               "Please enter a string (Example: James)"));

         np.setFirstName(JOptionVCheck.getTextDialog("What is the batters first name?", "Batter's First Name",
               "Please enter a string (Example: Paul)"));
         do {
            input = (String) JOptionPane.showInputDialog(null, "What side does the batter bat on?", "Bat Dominance",
                  JOptionPane.QUESTION_MESSAGE, null, battingSide, battingSide[0]);
            if (input == null || input.length() == 0)
               JOptionPane.showMessageDialog(null, "ERROR!\nPlease select a side!\n(e.g., Right, Left, Switch)",
                     "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
         } while (input == null || input.length() == 0);
         np.setBatDominance(input); // no option
         np.setLyTotalHits(JOptionVCheck.getIntDialog("How many total hits did the batter get last year?",
               "Batter's Total Hits Last Year", "Please enter an integer (Example: 3)"));
         np.setLyOptHits(JOptionVCheck.getIntDialog(
               "How many hits did the batter get when the temperature was optimal last year?",
               "Batter's Total Optimal Hits Last Year", "Please enter an integer (Example: 3)"));
         np.setLyAtBats(JOptionVCheck.getIntDialog("How many at bats did the batter get last year?",
               "Batter's Total At Bats Last Year", "Please enter an integer (Example: 3)"));
         np.setTyTotalHits(JOptionVCheck.getIntDialog("How many total hits does the batter have this year?",
               "Batter's Total Hits This Year", "Please enter an integer (Example: 3)"));
         np.setTyOptHits(JOptionVCheck.getIntDialog(
               "How many hits does the batter have when the temperature was optimal this year?",
               "Batter's Total Optimal Hits This Year", "Please enter an integer (Example: 3)"));
         np.setTyAtBats(JOptionVCheck.getIntDialog("How many at bats does the batter have this year?",
               "Batter's Total At Bats This Year", "Please enter an integer (Example: 3)"));
         listOfPlayers.add(np);
         JOptionPane.showMessageDialog(null, "Nationals Player Information:\n" + np, "Player #" +
               np.getJerseyNumber(), JOptionPane.INFORMATION_MESSAGE, np.getImage());
         flag = JOptionPane.showConfirmDialog(null, "Do you want to add another batter?", "ADD ANOTHER?",
               JOptionPane.YES_NO_OPTION,
               JOptionPane.PLAIN_MESSAGE) == JOptionPane.YES_OPTION;
      } while (flag);
   }

   public static void updateStats() {
      find();
      String[] updateDatabase = { "Select attribute to update:", "Jersey Number", "Last Name", "First Name",
            "Bat Dominance", "Last Years Total Hits", "Last Years Optimal Hits", "Last Years Total At Bats",
            "This Years Total Hits", "This Years Optimal Hits", "This Years Total At Bats", "Database Updated" };
      String select;
      if (currentPlayer >= 0) {
         BatterwStats curBat = listOfPlayers.get(currentPlayer);
         do {
            select = (String) JOptionPane.showInputDialog(null, "Please select a Database option:", "Database Option",
                  JOptionPane.PLAIN_MESSAGE,
                  null, updateDatabase, updateDatabase[0]);

            if (select == null) {
               return;
            }

            switch (select) {
               case "Jersey Number":
                  curBat.setJerseyNumber(JOptionVCheck.getIntDialog("What jersey number does the batter wear now?",
                        "Batter's Jersey Number", "Please enter an integer (Example: 3)"));
                  break;
               case "Last Name":
                  curBat.setLastName(JOptionVCheck.getTextDialog("What is the batters last name now?",
                        "Batter's Last Name", "Please enter a string (Example: James)"));
                  break;
               case "First Name":
                  curBat.setFirstName(JOptionVCheck.getTextDialog("What is the batters first name now?",
                        "Batter's First Name", "Please enter a string (Example: Paul)"));
                  break;
               case "Bat Dominance":
                  String temp = (String) JOptionPane.showInputDialog(null, "What side does the batter bat on?",
                        "Bat Dominance (Right:Left:Switch): " + curBat.getBatDominance(), JOptionPane.QUESTION_MESSAGE,
                        null, battingSide, battingSide[0]);
                  if (temp != null) {
                     curBat.setBatDominance(temp);
                  }
                  break;
               case "Last Years Total Hits":
                  curBat.setLyTotalHits(JOptionVCheck.getIntDialog("How many total hits did the batter get last year?",
                        "Batter's Total Hits Last Year", "Please enter an integer (Example: 3)"));
                  break;
               case "Last Years Optimal Hits":
                  curBat.setLyOptHits(JOptionVCheck.getIntDialog(
                        "How many hits did the batter get when the temperature was optimal last year?",
                        "Batter's Total Optimal Hits Last Year", "Please enter an integer (Example: 3)"));
                  break;
               case "Last Years Total At Bats":
                  curBat.setLyAtBats(JOptionVCheck.getIntDialog("How many at bats did the batter get last year?",
                        "Batter's Total At Bats Last Year", "Please enter an integer (Example: 3)"));
                  break;
               case "This Years Total Hits":
                  curBat.setTyTotalHits(
                        JOptionVCheck.getIntDialog("How many total hits does the batter have this year?",
                              "Batter's Total Hits This Year", "Please enter an integer (Example: 3)"));
                  break;
               case "This Years Optimal Hits":
                  curBat.setTyOptHits(JOptionVCheck.getIntDialog(
                        "How many hits does the batter have when the temperature was optimal this year?",
                        "Batter's Total Optimal Hits This Year", "Please enter an integer (Example: 3)"));
                  break;
               case "This Years Total At Bats":
                  curBat.setTyAtBats(JOptionVCheck.getIntDialog("How many at bats does the batter have this year?",
                        "Batter's Total At Bats This Year", "Please enter an integer (Example: 3)"));
                  break;
            }
         } while (!select.equals("Database Updated"));
         JOptionPane.showMessageDialog(null, "Updated Batter:\n" + curBat,
               "Player #" + curBat.getJerseyNumber(), JOptionPane.INFORMATION_MESSAGE, curBat.getImage());
      } else {
         JOptionPane.showMessageDialog(null, "Batter not selected!");
         currentPlayer = -1;
      }
   }

   public static void predict() throws IOException {
      boolean flagNGames = true;
      double probOfOptTemp;

      String excelFilePath = "statPre//TempRangeData.xlsx";

      do {

         NationalsPlayer ng = new NationalsPlayer(); // ng stands for new game
         find();

         // Return to main if no player was selected //
         if (currentPlayer < 0) {
            return;
         }

         BatterwStats selectedPlayer = listOfPlayers.get(currentPlayer);
         // Set ng player to
         ng.setPlayer(selectedPlayer);
         ng.setNewGame(JOptionVCheck.getIntDialog("What game number is this?", "Game Number?",
               "The game number must be an integer."));

         Map<String, String> cities = WeatherAPI.createMap();

         String[] key = cities.keySet().toArray(new String[0]);
         Arrays.sort(key);

         String API_Key = System.getenv("OPENWEATHER_API_KEY");

         String question = (String) JOptionPane.showInputDialog(null,
               "Which stadium are the Washington Nationals playing in?",
               "Teams", JOptionPane.PLAIN_MESSAGE, null, key, key[0]);

         // Return to main menu //
         if (question == null) {
            return;
         }

         String location = cities.get(question);
         ng.setTeamAgainst((String) question);
         System.out.println();

         Map<String, Object> respMap = WeatherAPI.getWeather(location, API_Key);
         Map<String, Object> mainMap = WeatherAPI.jsonToMap(respMap.get("main").toString());
         Map<String, Object> windMap = WeatherAPI.jsonToMap(respMap.get("wind").toString());

         // Casting to create an ArrayList
         // To get percipitation I will need the paid API_Key
         ArrayList<Map<String, Object>> weather = (ArrayList<Map<String, Object>>) respMap.get("weather");
         Map<String, Object> weatherMap = weather.get(0);
         JOptionPane.showMessageDialog(null,
               "Nationals vs " + question + "\n" + "Current Temperature: " + mainMap.get("temp") +
                     "\n" + "Current Temperature Feels Like: " + mainMap.get("feels_like") + "\n" +
                     "Current Wind Speed (mile/hour): " + windMap.get("speed"));
         ng.setGameTemp(Double.parseDouble(mainMap.get("feels_like").toString()));

         int gameData[] = ExcelInputOutput.getPlayerLog(selectedPlayer, ng, excelFilePath);

         if (gameData[0] > 0 && gameData[1] > 0) {
            probOfOptTemp = Statistics.calculateOptimalTempProb(gameData[0], gameData[1]);
         } else {
            JOptionPane.showMessageDialog(null,
                  "There is not enough temperature data to make a prediction for " + ng.getGameTemp() + " degrees.");
            continue;
         }

         ng.setPredictedPlayerAvgTy(Statistics.calculatePredictedAverage(ng, probOfOptTemp));

         listOfGames.add(ng);
         flagNGames = JOptionPane.showConfirmDialog(null, "Do you want to predict another game?", "Predict Another?",
               JOptionPane.YES_NO_OPTION,
               JOptionPane.PLAIN_MESSAGE) == JOptionPane.YES_OPTION;
      } while (flagNGames);
   }

   public static void save(List<BatterwStats> listOfPlayers) throws IOException {

      ExcelInputOutput.savePlayerData(listOfPlayers);

   }

   public static void savePredict() throws IOException {

      String excelFilePath = "statPre//Predictions.xlsx";
      ExcelInputOutput.savePredictionData(listOfGames, excelFilePath);

   }

   public static void find() {
      // To make sure the listOfPlayers is initialized
      if (listOfPlayers == null || listOfPlayers.isEmpty()) {
         currentPlayer = -1;
         JOptionPane.showMessageDialog(null, "No players loaded.");
         return;
      }

      String[] playerList = new String[listOfPlayers.size() + 1];
      playerList[0] = "Select A Player";
      for (int i = 1; i < playerList.length; i++) {
         BatterwStats player = listOfPlayers.get(i - 1);
         playerList[i] = (i) + ":" + player.getJerseyNumber() + " " + player.getFirstName() + " ("
               + player.getLastName() + ")";
      }

      String npSelected = (String) JOptionPane.showInputDialog(null, "Please select a Batter:", "Nationals Batter",
            JOptionPane.PLAIN_MESSAGE, null, playerList, playerList[0]);

      if (npSelected != null && npSelected.indexOf(":") >= 0) {
         currentPlayer = Integer.parseInt(npSelected.substring(0, npSelected.indexOf(":"))) - 1;
         BatterwStats cb = listOfPlayers.get(currentPlayer);
         JOptionPane.showMessageDialog(null, "Nationals Batter:\n" + cb, "Player #" + cb.getJerseyNumber(),
               JOptionPane.INFORMATION_MESSAGE, cb.getImage());
      } else {
         JOptionPane.showMessageDialog(null, "Batter not selected.");
         currentPlayer = -1;
      }
   }

}
