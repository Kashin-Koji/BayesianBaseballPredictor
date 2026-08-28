// **************************************************
// Title: StatsForWeather Class
// File: StatsForWeather.java
// Authors: Usoff Samantar/Honors Mentor/Professor Frank Seidel
// Description: In this code I have 
// created/edited a database system where I store
// batter statistics, as well as use weather 
// data (temperature) currently to try to 
// predict per game batting average.
// **************************************************

package statPre;

//A package in Java is used to group related classes.
import java.io.File;
import java.util.Comparator;
import java.io.FileOutputStream;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.util.Arrays;
import java.util.ArrayList;
import com.google.gson.*;
import com.google.gson.reflect.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.util.CellUtil;
import java.io.FileInputStream;

public class StatsForWeather {
   // Improvements
   // ImageIcon
   // FileReader
   // Map and Hashmap
   // Gson
   // Json
   // Url
   // Url connection
   // StreamReader
   // BufferReader
   // Bayes' Theroem
   // Apache
   // Excel integration

   // Total player hits during optimal weather conditions (56-75)//
   int totalPlayerHits_Optimal = 0;
   // Bayes' Theorem = P(A|B) = (P(B|A) * P(A))/P(B)//
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
      listOfPlayers = new ArrayList<>();
      FileInputStream fileIn = new FileInputStream("statPre//NBLUP.xlsx");
      Workbook workbook = new XSSFWorkbook(fileIn);
      Sheet sheet = workbook.getSheetAt(0);

      for (Row row : sheet) {
         // Skipping the header row

         if (row.getRowNum() == 0)
            continue;

         try {

            BatterwStats player = new BatterwStats();
            player.setJerseyNumber((int) row.getCell(0).getNumericCellValue());
            player.setLastName(row.getCell(1).getStringCellValue());
            player.setFirstName(row.getCell(2).getStringCellValue());
            player.setBatDominance(row.getCell(3).getStringCellValue());
            player.setLyTotalHits((int) row.getCell(4).getNumericCellValue());
            player.setLyOptHits((int) row.getCell(5).getNumericCellValue());
            player.setLyAtBats((int) row.getCell(6).getNumericCellValue());
            player.setTyTotalHits((int) row.getCell(7).getNumericCellValue());
            player.setTyOptHits((int) row.getCell(8).getNumericCellValue());
            player.setTyAtBats((int) row.getCell(9).getNumericCellValue());

            listOfPlayers.add(player);
            System.out.println("Added player: " + player); // Debugging line
         } catch (Exception e) {
            System.out.println("Error processing row " + row.getRowNum() + ": " + e.getMessage());
         }
         workbook.close();
         fileIn.close();
      }

   }

   public static void addBatter() {
      boolean flag;
      String input = "";
      // Create an new object in ArrayList
      BatterwStats np;
      do {
         np = new BatterwStats();
         do {
            try {
               input = JOptionPane.showInputDialog(null, "What jersey number does the batter wear?");
               np.setJerseyNumber(Integer.parseInt(input));
            } catch (Exception e) {
               JOptionPane.showMessageDialog(null, "ERROR!\nPlease enter jersey number!\n(e.g., 25, 30, 55)",
                     "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
               input = null;
            }
         } while (input == null || input.length() == 0);
         do {
            input = (String) JOptionPane.showInputDialog(null, "What is the batters last name?", "Last Name",
                  JOptionPane.PLAIN_MESSAGE);
            if (input == null || input.length() == 0)
               JOptionPane.showMessageDialog(null,
                     "ERROR!\nPlease enter a last name!\n(e.g., Robinson, James, Bryant )",
                     "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
         } while (input == null || input.length() == 0);
         np.setLastName(input);
         do {
            input = JOptionPane.showInputDialog(null, "What is the batters first name?", "first Name",
                  JOptionPane.PLAIN_MESSAGE);
            if (input == null || input.length() == 0)
               JOptionPane.showMessageDialog(null, "ERROR!\nPlease enter a first name!\n(e.g., John, James, Eddy )",
                     "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
         } while (input == null || input.length() == 0);
         np.setFirstName(input);
         do {
            input = (String) JOptionPane.showInputDialog(null, "What side does the batter bat on?", "Bat Dominance",
                  JOptionPane.QUESTION_MESSAGE, null, battingSide, battingSide[0]);
            if (input == null || input.length() == 0)
               JOptionPane.showMessageDialog(null, "ERROR!\nPlease select a side!\n(e.g., Right, Left, Switch)",
                     "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
         } while (input == null || input.length() == 0);
         np.setBatDominance(input); // no option
         do {
            input = JOptionPane.showInputDialog(null, "How many total hits did the batter get last year?");
            if (input == null || input.length() == 0)
               JOptionPane.showMessageDialog(null, "ERROR!\nPlease enter last years total hits!",
                     "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
         } while (input == null || input.length() == 0);
         np.setLyTotalHits(Integer.parseInt(input));
         do {
            input = JOptionPane.showInputDialog(null,
                  "How many hits did the batter get when the temperature was optimal last year?");
            if (input == null || input.length() == 0)
               JOptionPane.showMessageDialog(null, "ERROR!\nPlease enter last years optimal hits!",
                     "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
         } while (input == null || input.length() == 0);
         np.setLyOptHits(Integer.parseInt(input));
         do {
            input = JOptionPane.showInputDialog(null, "How many at bats did the batter get last year?");
            if (input == null || input.length() == 0)
               JOptionPane.showMessageDialog(null, "ERROR!\nPlease enter last years at bats!",
                     "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
         } while (input == null || input.length() == 0);
         np.setLyAtBats(Integer.parseInt(input));
         do {
            input = JOptionPane.showInputDialog(null, "How many total hits does the batter have this year?");
            if (input == null || input.length() == 0)
               JOptionPane.showMessageDialog(null, "ERROR!\nPlease enter this years total hits!",
                     "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
         } while (input == null || input.length() == 0);
         np.setTyTotalHits(Integer.parseInt(input));
         do {
            input = JOptionPane.showInputDialog(null,
                  "How many hits does the batter have when the temperature was optimal year?");
            if (input == null || input.length() == 0)
               JOptionPane.showMessageDialog(null, "ERROR!\nPlease enter this years optimal hits!",
                     "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
         } while (input == null || input.length() == 0);
         np.setTyOptHits(Integer.parseInt(input));
         do {
            input = JOptionPane.showInputDialog(null, "How many at bats does the batter have last year?");
            if (input == null || input.length() == 0)
               JOptionPane.showMessageDialog(null, "ERROR!\nPlease enter this years at bats!",
                     "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
         } while (input == null || input.length() == 0);
         np.setTyAtBats(Integer.parseInt(input));
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
            "Bat Dominance", "Last Years Total Hits", "Last Years Optimal Hits", "Last Years At Bats",
            "This Years Total Hits", "This Years Optimal Hits", "This Years Total At Bats", "Database Updated" };
      String select;
      if (currentPlayer >= 0) {
         BatterwStats curBat = listOfPlayers.get(currentPlayer);
         do {
            select = (String) JOptionPane.showInputDialog(null, "Please select a Database option:", "Database Option",
                  JOptionPane.PLAIN_MESSAGE,
                  null, updateDatabase, updateDatabase[0]);
            switch (select) {
               case "Jersey Number":
                  curBat.setJerseyNumber(
                        Integer.parseInt(JOptionPane.showInputDialog(null, "What is the batters new jersey number?",
                              "Jersey Number: " + curBat.getJerseyNumber(), JOptionPane.QUESTION_MESSAGE)));
                  break;
               case "Last name":
                  curBat.setLastName(JOptionPane.showInputDialog(null, "What is the batters last name?",
                        "Last Name: " + curBat.getLastName(), JOptionPane.QUESTION_MESSAGE));
                  break;
               case "First Name":
                  curBat.setFirstName(JOptionPane.showInputDialog(null, "What is the batters first name?",
                        "First Name: " + curBat.getFirstName(), JOptionPane.QUESTION_MESSAGE));
                  break;
               case "Bat Dominance":
                  curBat.setBatDominance((String) JOptionPane.showInputDialog(null, "What side does the batter bat on?",
                        "Bat Dominance (Right:Left:Swtich): " + curBat.getBatDominance(), JOptionPane.QUESTION_MESSAGE,
                        null, battingSide, battingSide[0]));
                  break;
               case "Last Years Total Hits":
                  curBat.setLyTotalHits(Integer
                        .parseInt(JOptionPane.showInputDialog(null, "How many hits did the batter get last year?",
                              "Last Years Total Hits: " + curBat.getLyTotalHits(), JOptionPane.QUESTION_MESSAGE)));
                  break;
               case "Last Years Optimal Hits":
                  curBat.setLyOptHits(Integer.parseInt(JOptionPane.showInputDialog(null,
                        "How many hits did the batter get when the temperature was optimal last year?",
                        "Last Years Optimal Hits: " + curBat.getLyOptHits(), JOptionPane.QUESTION_MESSAGE)));
                  break;
               case "Last Years At Bats":
                  curBat.setLyAtBats(Integer
                        .parseInt(JOptionPane.showInputDialog(null, "How many at bats did the batter get last year?",
                              "Last Years Total Hits: " + curBat.getLyAtBats(), JOptionPane.QUESTION_MESSAGE)));
                  break;
               case "This Years Total Hits":
                  curBat.setTyTotalHits(Integer
                        .parseInt(JOptionPane.showInputDialog(null, "How many hits has this batter gotten this year?",
                              "This Years Total Hits: " + curBat.getTyTotalHits(), JOptionPane.QUESTION_MESSAGE)));
                  break;
               case "This Years Optimal Hits":
                  curBat.setTyOptHits(Integer.parseInt(JOptionPane.showInputDialog(null,
                        "How many hits did the batter get when the temperature was optimal this year?",
                        "This Years Optimal Hits: " + curBat.getTyOptHits(), JOptionPane.QUESTION_MESSAGE)));
                  break;
               case "This Years At Bats":
                  curBat.setTyAtBats(Integer.parseInt(
                        JOptionPane.showInputDialog(null, "How many at bats has this batter gotten this year??",
                              "This Years Total At Bats: " + curBat.getTyAtBats(), JOptionPane.QUESTION_MESSAGE)));
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
      int totalNumberOfGames = 0;
      int totalNumberOfGamesInOp = 0;
      double probOfOptTemp;
      NationalsPlayer ng = new NationalsPlayer(); // ng stands for new game
      String excelFilePath = "statPre//TempRangeData.xlsx";

      do {
         find();
         BatterwStats selectedPlayer = listOfPlayers.get(currentPlayer);
         String lastName = selectedPlayer.getLastName();
         String firstName = selectedPlayer.getFirstName();

         // Set ng player to
         ng.setPlayer(selectedPlayer);
         ng.setNewGame(JOptionVCheck.getIntDialog("What game number is this?", "Game Number?",
               "The game number must be an integer."));

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
         String[] key = cities.keySet().toArray(new String[0]);
         Arrays.sort(key);

         // My API Key For Connecion//
         String API_Key = System.getenv("OPENWEATHER_API_KEY");

         // User Location//
         // User Input//
         // Cast Object To String//
         String question = (String) JOptionPane.showInputDialog(null,
               "Which stadium are the Washington Nationals playing in?",
               "Teams", JOptionPane.PLAIN_MESSAGE, null, key, key[0]);
         String location = cities.get(question);
         ng.setTeamAgainst((String) question);
         System.out.println();

         // Found This To Convert Json To Map
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

         FileInputStream inputstream = new FileInputStream(excelFilePath);
         XSSFWorkbook workbook = new XSSFWorkbook(inputstream);
         XSSFSheet sheet = workbook.getSheet("Sheet1");
         totalNumberOfGames = 0;
         totalNumberOfGamesInOp = 0;
         boolean playerFound = false;

         for (Row row : sheet) {
            if (row.getCell(0) != null && row.getCell(1) != null &&
                  row.getCell(0).getStringCellValue().equals(lastName) &&
                  row.getCell(1).getStringCellValue().equals(firstName)) {
               playerFound = true;
               if (row.getCell(2) != null && row.getCell(2).getCellType() == CellType.NUMERIC) {
                  totalNumberOfGames = (int) row.getCell(2).getNumericCellValue();
               } else {
                  System.out.println("Cell for totalNumberOfGames is not numeric or is empty");
               }
               int tempColumnIndex = 0;
               if (ng.getGameTemp() <= 55) {
                  tempColumnIndex = 5;
               } else if (ng.getGameTemp() <= 65) {
                  tempColumnIndex = 8;
               } else if (ng.getGameTemp() <= 75) {
                  tempColumnIndex = 11;
               } else if (ng.getGameTemp() <= 85) {
                  tempColumnIndex = 14;
               } else if (ng.getGameTemp() <= 95) {
                  tempColumnIndex = 17;
               } else if (ng.getGameTemp() <= 105) {
                  tempColumnIndex = 20;
               }

               if (tempColumnIndex > 0 && row.getCell(tempColumnIndex) != null
                     && row.getCell(tempColumnIndex).getCellType() == CellType.NUMERIC) {
                  totalNumberOfGamesInOp = (int) row.getCell(tempColumnIndex).getNumericCellValue();
               } else {
                  System.out.println(
                        "Cell for totalNumberOfGamesInOp is not numeric, is empty, or tempColumnIndex is not set");
               }
               break;
            }
         }

         workbook.close();
         inputstream.close();

         if (!playerFound) {
            System.out.println("Player not found in Excel file");
         } else {
            System.out.println("Total number of games: " + totalNumberOfGames);
         }

         if (totalNumberOfGames > 0) {
            probOfOptTemp = (double) totalNumberOfGamesInOp / totalNumberOfGames; // Cast to double for floating-point
                                                                                  // division
         } else {
            probOfOptTemp = 0; // Handle the case where totalNumberOfGames is 0 to avoid division by zero
         }

         ng.setPredictedPlayerAvgTy((((double)(ng.getPlayer().getLyOptHits() + ng.getPlayer().getTyOptHits()) /
               (ng.getPlayer().getLyTotalHits() + ng.getPlayer().getTyTotalHits())) *
               ng.getPlayer().getTyBattingAverage()) / probOfOptTemp);
         /* I can remove this once I refactor prediction logic */
         System.out.println(probOfOptTemp);
         // Debugging: Print values to check
         System.out.println("Debug Info: ");
         System.out.println("Total number of games (totalNumberOfGames): " + totalNumberOfGames);
         System.out.println(
               "Total number of games in optimal temperature (totalNumberOfGamesInOp): " + totalNumberOfGamesInOp);
         System.out.println("Game Temperature (ng.getGameTemp()): " + ng.getGameTemp());
         System.out.println("Probability of Optimal Temperature (probOfOptTemp): " + probOfOptTemp * 100);
         // I think the issue has to do with the NationalsPlayer class
         System.out.println((((double)(ng.getPlayer().getLyOptHits() + ng.getPlayer().getTyOptHits()) /
               (ng.getPlayer().getLyTotalHits() + ng.getPlayer().getTyTotalHits())) *
               ng.getPlayer().getTyBattingAverage()) / probOfOptTemp);

         listOfGames.add(ng);
         flagNGames = JOptionPane.showConfirmDialog(null, "Do you want to predict another game?", "Predict Another?",
               JOptionPane.YES_NO_OPTION,
               JOptionPane.PLAIN_MESSAGE) == JOptionPane.YES_OPTION;
      } while (flagNGames);
   }

   public static void save(List<BatterwStats> listOfPlayers) throws IOException {
      Workbook workbook = new XSSFWorkbook();
      Sheet sheet = workbook.createSheet("Player Stats");

      // Assuming the first row contains headers
      String[] headers = { "Jersey Number", "Last Name", "First Name", "Bat Dominance",
            "LY Total Hits", "LY Opt Hits", "LY At Bats",
            "TY Total Hits", "TY Opt Hits", "TY At Bats" };
      Row headerRow = sheet.createRow(0);
      for (int i = 0; i < headers.length; i++) {
         headerRow.createCell(i).setCellValue(headers[i]);
      }

      int rowNum = 1;
      for (BatterwStats player : listOfPlayers) {
         Row row = sheet.createRow(rowNum++);
         row.createCell(0).setCellValue(player.getJerseyNumber());
         row.createCell(1).setCellValue(player.getLastName());
         row.createCell(2).setCellValue(player.getFirstName());
         row.createCell(3).setCellValue(player.getBatDominance());
         row.createCell(4).setCellValue(player.getLyTotalHits());
         row.createCell(5).setCellValue(player.getLyOptHits());
         row.createCell(6).setCellValue(player.getLyAtBats());
         row.createCell(7).setCellValue(player.getTyTotalHits());
         row.createCell(8).setCellValue(player.getTyOptHits());
         row.createCell(9).setCellValue(player.getTyAtBats());
      }

      try (FileOutputStream fileOut = new FileOutputStream("statPre//NBLUP.xlsx")) {
         Workbook workbook2 = new XSSFWorkbook();
         workbook.write(fileOut);
      }
      workbook.close();
      System.out.println("Excel file written successfully.");
   }

   public static void savePredict() throws IOException {
      String excelFilePath = "statPre//Predictions.xlsx";
      Workbook workbook;
      Sheet sheet;

      // Check if file exists
      File file = new File(excelFilePath);
      if (file.exists()) {
         // Open the existing workbook and sheet
         FileInputStream inputStream = new FileInputStream(file);
         workbook = new XSSFWorkbook(inputStream);
         sheet = workbook.getSheetAt(0);
      } else {
         // Create new workbook and sheet
         workbook = new XSSFWorkbook();
         sheet = workbook.createSheet("Predictions");
      }

      // Create header row if workbook is new
      if (file.length() == 0) {
         Row headerRow = sheet.createRow(0);
         String[] headers = { "Team Against", "Game Number", "Game Temperature", "Predicted Player Avg Ty",
               "TY Batting Average", "TY Total Hits", "TY Opt Hits", "LY Total Hits", "LY Opt Hits",
               "Last Name", "First Name" };
         for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
         }
      }

      // Append data
      int rowCount = sheet.getLastRowNum() + 1;
      for (NationalsPlayer ng : listOfGames) {
         BatterwStats player = ng.getPlayer();
         Row row = sheet.createRow(rowCount++);
         row.createCell(0).setCellValue(ng.getTeamAgainst());
         row.createCell(1).setCellValue(ng.getNewGame());
         row.createCell(2).setCellValue(ng.getGameTemp());
         row.createCell(3).setCellValue(ng.getPredictedPlayerAvgTy());
         row.createCell(4).setCellValue(player.getTyBattingAverage());
         row.createCell(5).setCellValue(player.getTyTotalHits());
         row.createCell(6).setCellValue(player.getTyOptHits());
         row.createCell(7).setCellValue(player.getLyTotalHits());
         row.createCell(8).setCellValue(player.getLyOptHits());
         row.createCell(9).setCellValue(player.getLastName());
         row.createCell(10).setCellValue(player.getFirstName());
      }

      // Sort by last name and first name
      if (rowCount > 1) { // If there's data to sort
         List<Row> rows = new ArrayList<>();
         for (int i = 1; i < rowCount; i++) {
            rows.add(sheet.getRow(i));
         }
         rows.sort(Comparator.comparing(r -> r.getCell(9).getStringCellValue() + r.getCell(10).getStringCellValue()));

         // Rows in sorted order
         int rowId = 1;
         for (Row rowData : rows) {
            Row row = CellUtil.getRow(rowId++, sheet);
            for (int cn = 0; cn < rowData.getLastCellNum(); cn++) {
               Cell cell = CellUtil.getCell(row, cn);
               Cell c = rowData.getCell(cn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
               switch (c.getCellType()) {
                  case STRING:
                     cell.setCellValue(c.getStringCellValue());
                     break;
                  case NUMERIC:
                     cell.setCellValue(c.getNumericCellValue());
                     break;
                  // I can add more other cases if necessary such as if my data is expanded!!!!!!!
               }
            }
         }
      }

      // Write changes to the file
      try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
         workbook.write(outputStream);
      }

      // Close workbook
      workbook.close();
      System.out.println("Predictions saved to Excel file.");
   }

   public static void find() {
      // To make sure the listOfPlayers is initialized
      if (listOfPlayers == null || listOfPlayers.isEmpty()) {
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
