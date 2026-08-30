package statPre;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellUtil;

public class ExcelInputOutput {

    public static int[] getPlayerLog(BatterwStats player, NationalsPlayer ng, String filename) throws IOException {

        int totalNumberOfGames = 0;
        int totalNumberOfGamesInOp = 0;
        String lastName = player.getLastName();
        String firstName = player.getFirstName();
        FileInputStream inputstream = new FileInputStream(filename);
        XSSFWorkbook workbook = new XSSFWorkbook(inputstream);
        XSSFSheet sheet = workbook.getSheet("Sheet1");
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
                    // Prevent prediction when historical weather data is unavailable //
                } else {
                    System.out.println("There is no temperature data available for " + ng.getGameTemp() + " degrees.");
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

        if (!playerFound) {
            System.out.println("Player not found in Excel file");
        } else {
            System.out.println("Total number of games: " + totalNumberOfGames);
            System.out.println("Total number of optimal games: " + totalNumberOfGamesInOp);
        }

        workbook.close();
        inputstream.close();

        return new int[] { totalNumberOfGames, totalNumberOfGamesInOp };
    }

    public static ArrayList<BatterwStats> openPlayerExcel(String filename) throws IOException {

        ArrayList<BatterwStats> listOfPlayers = new ArrayList<BatterwStats>();
        FileInputStream fileIn = new FileInputStream(filename);
        Workbook workbook = new XSSFWorkbook(fileIn);
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            // Skipping the header row //

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
                System.out.println("Added player: " + player); // Debugging line //
            } catch (Exception e) {
                System.out.println("Error processing row " + row.getRowNum() + ": " + e.getMessage());
            }

        }

        workbook.close();
        fileIn.close();

        return listOfPlayers;

    }

    public static void savePlayerData(List<BatterwStats> listOfPlayers) throws IOException {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Player Stats");

        // Assuming the first row contains headers //
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

            workbook.write(fileOut);

        }

        workbook.close();
        System.out.println("Excel file written successfully.");

    }

    public static void savePredictionData(ArrayList<NationalsPlayer> listOfGames, String filename) throws IOException {

        Workbook workbook;
        Sheet sheet;

        // Check if file exists
        File file = new File(filename);
        if (file.exists()) {
            // Open the existing workbook and sheet
            FileInputStream inputStream = new FileInputStream(file);
            workbook = new XSSFWorkbook(inputStream);
            sheet = workbook.getSheetAt(0);
        } else {
            // Create new workbook and sheet //
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet("Predictions");
        }

        // Create header row if workbook is new //
        if (file.length() == 0) {
            Row headerRow = sheet.createRow(0);
            String[] headers = { "Team Against", "Game Number", "Game Temperature", "Predicted Player Avg Ty",
                    "TY Batting Average", "TY Total Hits", "TY Opt Hits", "LY Total Hits", "LY Opt Hits",
                    "Last Name", "First Name" };
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }
        }

        // Append data //
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

        // Sort by last name and first name //
        if (rowCount > 1) { // If there's data to sort //
            List<Row> rows = new ArrayList<>();
            for (int i = 1; i < rowCount; i++) {
                rows.add(sheet.getRow(i));
            }
            rows.sort(
                    Comparator.comparing(r -> r.getCell(9).getStringCellValue() + r.getCell(10).getStringCellValue()));

            // Rows in sorted order //
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
                        // I can add more other cases if necessary such as if my data is expanded! //
                    }
                }
            }
        }

        // Write changes to the file //
        try (FileOutputStream outputStream = new FileOutputStream(filename)) {
            workbook.write(outputStream);
        }

        workbook.close();
        System.out.println("Predictions saved to Excel file.");

    }

}
