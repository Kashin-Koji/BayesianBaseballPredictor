package statPre;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

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

        }

        workbook.close();
        fileIn.close();

        return listOfPlayers;

    }

}
