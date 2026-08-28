package statPre;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
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

        workbook.close();
        inputstream.close();
        if (!playerFound) {
            System.out.println("Player not found in Excel file");
        } else {
            System.out.println("Total number of games: " + totalNumberOfGames);
            System.out.println("Total number of optimal games: " + totalNumberOfGamesInOp);
        }

        return new int[] { totalNumberOfGames, totalNumberOfGamesInOp };
    }

}
