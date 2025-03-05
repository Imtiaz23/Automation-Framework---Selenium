package framework.commonutils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class GetTestData {

    private static Logger log = Logger.getLogger(GetTestData.class.getName());


    public static String[][] getdataFromDataSource(String FileName, String WorkSheet, String TableName)
    {
        String[][] loanData = null;
        try
        {
            Workbook workbook = Workbook.getWorkbook(new File(FileName));
            Sheet sheet = workbook.getSheet(WorkSheet);

            int startRow,startCol, endRow, endCol;
            int ci = 0, cj;

            Cell tableStart=sheet.findCell(TableName);

            startRow=tableStart.getRow();
            startCol= tableStart.getColumn();
            startCol = startCol + 1;

            System.out.println("StartRow: "+ startRow);
            System.out.println("StartCol: "+ startCol);

            Cell tableEnd= sheet.findCell(TableName, startCol,startRow, 200, 64000,  false);
            endRow=tableEnd.getRow();

            endCol = tableEnd.getColumn();
            endCol = endCol - 1;

            System.out.println("EndRow: "+ endRow);
            System.out.println("EndCol: "+ endCol);

            loanData = new String[(endRow-startRow)+1][endCol];

            for (int i= startRow; i <= endRow ; i++, ci++)
            {
                System.out.println("ci: "+ ci);
                cj = 0;
                for (int j= startCol; j <= endCol; j++, cj++)
                {
                    loanData[ci][cj] = sheet.getCell(j, i).getContents();
                    System.out.println(loanData[ci][cj]);
                }
            }
        }
        catch (FileNotFoundException ex)
        {
            ex.getMessage();
            ex.printStackTrace();
            System.out.println(ex);
            log.error("Ex:"+ex);
        }
        catch (Exception ex)
        {
            ex.getMessage();
            System.out.println(ex);
            log.error("Ex:"+ex);

        }

        log.info("Succesfully fetched the data from excel");
        return loanData;

    }

    @SuppressWarnings("unused")
    public static  Iterator<Object[]> getdataList(String FileName, String WorkSheet, String TableName)
    {
        //List<String> testData = new ArrayList<String>();

        List<Object[]> testData = new ArrayList<Object[]>();
        try
        {
            Workbook workbook = Workbook.getWorkbook(new File(FileName));
            Sheet sheet = workbook.getSheet(WorkSheet);

            int startRow,startCol, endRow, endCol;
            int ci = 0, cj;

            Cell tableStart=sheet.findCell(TableName);

            startRow=tableStart.getRow();
            startCol= tableStart.getColumn();
            startCol = startCol + 1;

            System.out.println("StartRow: "+ startRow);
            System.out.println("StartCol: "+ startCol);

            Cell tableEnd= sheet.findCell(TableName, startCol,startRow, 200, 64000,  false);
            endRow=tableEnd.getRow();

            endCol = tableEnd.getColumn();
            endCol = endCol - 1;

            System.out.println("EndRow: "+ endRow);
            System.out.println("EndCol: "+ endCol);


            String temp = null;
            List<String> tableData = new ArrayList<String>();
            //loanData = new String[(endRow-startRow)+1][endCol];

            for (int i= startRow; i <= endRow ; i++, ci++)
            {
                System.out.println("ci: "+ ci);
                cj = 0;
                for (int j= startCol; j <= endCol; j++, cj++)
                {
                    temp = sheet.getCell(j, i).getContents();
                    System.out.println(temp);
                    tableData.add(temp);
                }
                testData.add(new Object[] {tableData} );
            }
        }

        catch (FileNotFoundException ex)
        {
            ex.getMessage();
            ex.printStackTrace();
            System.out.println(ex);
            log.error("Ex:"+ex);
        }
        catch (Exception ex)
        {
            ex.getMessage();
            System.out.println(ex);
            log.error("Ex:"+ex);

        }

        log.info("Succesfully fetched the data from excel");
        return testData.iterator();

    }

}