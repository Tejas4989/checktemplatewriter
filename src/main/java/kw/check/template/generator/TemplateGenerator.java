package kw.check.template.generator;

import kw.check.template.CheckTemplate;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.stream.IntStream;

import static org.apache.poi.xwpf.usermodel.XWPFTable.XWPFBorderType.NONE;
import static org.apache.poi.xwpf.usermodel.XWPFTable.XWPFBorderType;

public class TemplateGenerator {

    private static final String WHITE_RGB = "ffffff";

    //Create Word
    public void createTemplate(CheckTemplate template) throws IOException {
        //Blank Document
        XWPFDocument document = null;
        try {
            document = new XWPFDocument();
            //set page margin
            setPageMargin(document);
            //create Paragraph
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            /*addTab(run, 13);
            run.setText("008811");*/
            addNewLine(run, 4);
            createTableTemplate(document, template);
            writeTemplateToFile(document);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            //Close document
            if (document != null) {
                document.close();
            }
        }
    }

    private static void setPageMargin(XWPFDocument document) {
        CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
        CTPageMar pageMar = sectPr.addNewPgMar();
        pageMar.setLeft(BigInteger.valueOf(286L));
        pageMar.setRight(BigInteger.valueOf(286L));
        pageMar.setTop(BigInteger.valueOf(357L));
        pageMar.setBottom(BigInteger.valueOf(1000L));
    }

    private static void addTab(XWPFRun paragraph, int times) {
        IntStream.range(0, times).forEach(i -> paragraph.addTab());
    }

    private static void addNewLine(XWPFRun paragraph, int times) {
        IntStream.range(0, times).forEach(i -> paragraph.addCarriageReturn());
    }

    public static void addAmounts(XWPFRun run, CheckTemplate template) {
        String amountInWords = template.getAmountInWords();
        addTab(run, 2);
        if (amountInWords.length() <= 50) {
            run.setText("***" + amountInWords + "***");
            addTab(run, (int) Math.ceil((50 - amountInWords.length()) / 6.0));
            addTab(run, 2);
            run.setText("       $ ***" + template.getAmount() + "***");
            addNewLine(run, 3);
        } else {
            // find the last space \s from 50 characters
            int spactAt = amountInWords.substring(0, 50).lastIndexOf(' ');
            run.setText("***" + amountInWords.substring(0, spactAt));
            addTab(run, 3);
            run.setText("       $ ***" + template.getAmount() + "***");
            addNewLine(run, 1);
            addTab(run, 2);
            run.setText(amountInWords.substring(spactAt) + "***");
            addNewLine(run, 2);
        }
    }

    private static String getFormattedAmountsInWords(String amountInWords) {
        if (amountInWords.length() <= 60) {
            return "***" + amountInWords + "***";
        } else {
            // find the last space \s from 50 characters
            int spactAt = amountInWords.substring(0, 60).lastIndexOf(' ');
            return "***" + amountInWords.substring(0, spactAt);
        }
    }

    private static String getRemainingFormattedAmountsInWords(String amountInWords) {
        if (amountInWords.length() > 60) {
            int spactAt = amountInWords.substring(0, 60).lastIndexOf(' ');
            return amountInWords.substring(spactAt) + "***";
        }
        return "";
    }

    public void writeTemplateToFile(XWPFDocument document) throws IOException {
        //Write the Document in file system
        OutputStream out = null;
        try {
            out = new FileOutputStream(new File("template.docx"));
            document.write(out);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
            System.out.println("template.docx  written successfully");
        }
    }

    private static void setTableBorder(XWPFBorderType borderType, XWPFTable table) {
        table.setTopBorder(borderType, 0, 0, WHITE_RGB);
        table.setBottomBorder(borderType, 0, 0, WHITE_RGB);
        table.setLeftBorder(borderType, 0, 0, WHITE_RGB);
        table.setRightBorder(borderType, 0, 0, WHITE_RGB);
        table.setInsideHBorder(borderType, 0, 0, WHITE_RGB);
        table.setInsideVBorder(borderType, 0, 0, WHITE_RGB);
    }

    public void createTableTemplate(XWPFDocument document, CheckTemplate template) {

        //create table
        XWPFTable table = document.createTable();
        table.setWidth("100%");
        setTableBorder(NONE, table);
        //set Table cell margins
        table.setCellMargins(0, 0, 0, 0);
        //create first row
        XWPFTableRow tableRowOne = table.getRow(0);


        XWPFTableCell col1Cell = tableRowOne.getCell(0);
        col1Cell.setWidth("11.5%");
        col1Cell.setText("");
        XWPFTableCell col2Cell = tableRowOne.addNewTableCell();
        col2Cell.setWidth("64.8%");
        col2Cell.setText("");
        XWPFTableCell col3Cell = tableRowOne.addNewTableCell();
        col3Cell.setWidth("23.6%");
        col3Cell.setText("Date: " + template.getDate());

        //create second row
        XWPFTableRow tableRowTwo = table.createRow();

        tableRowTwo.getCell(0).setText("");
        tableRowTwo.getCell(1).setText(getFormattedAmountsInWords(template.getAmountInWords()));
        tableRowTwo.getCell(2).setText("$ ***" + template.getAmount() + "***");

        //create third row
        XWPFTableRow tableRowThree = table.createRow();

        tableRowThree.getCell(0).setText("");
        tableRowThree.getCell(1).setText(getRemainingFormattedAmountsInWords(template.getAmountInWords()));
        tableRowThree.getCell(2).setText("");

        //create fourth row
        XWPFTableRow tableRowFour = table.createRow();

        tableRowFour.getCell(0).setText("");
        tableRowFour.getCell(1).setText("");
        tableRowFour.getCell(2).setText("");

        //create Fifth row
        XWPFTableRow tableRowFive = table.createRow();

        tableRowFive.getCell(0).setText("");
        tableRowFive.getCell(1).setText(template.getPayee());
        tableRowFive.getCell(2).setText("");

    }


}
