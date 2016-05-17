package com.example.android.drroster.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;

import com.example.android.drroster.MainActivity;
import com.example.android.drroster.UI.DisplayToast;
import com.example.android.drroster.databases.ItemMainViewHelper;
import com.example.android.drroster.databases.RosterHelper;
import com.example.android.drroster.databases.ShiftHelper;
import com.example.android.drroster.models.ItemMainView;
import com.example.android.drroster.utils.DateUtils;
import com.example.android.drroster.utils.UIUtils;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class exportToExcel extends IntentService {

    private static final int STYLE_CELL_HEADER = 0;
    private static final int STYLE_CELL_NORMAL = 1;
    private static final int STYLE_CELL_WEEKEND = 2;
    Date currentMonth;
    ArrayList<ItemMainView> listData;
    //Handler to call toast from background
    Handler mHandler;
    private static Workbook wb;

    public exportToExcel() {
        super("ExportToExcel");
        mHandler = new Handler();
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        //Check for properly initialized current month from long and then check for roster in that month
        if (intent.getExtras() != null) {
            Long temp = intent.getLongExtra(MainActivity.CURRENT_MONTH_KEY, 0);
            if (temp > 0) {
                currentMonth = new Date(temp);
                if(RosterHelper.isAvailableRoster(currentMonth)){
                    createExcelSheet(currentMonth);
                }
                else {
                    mHandler.post(new DisplayToast(this, "No Roster for this month"));
                }
            }
        }

    }

    private void createExcelSheet(Date currentMonth) {

        // Construct the data from db and set it in Strings arrays
        listData = ItemMainViewHelper.buildMainItemMonthList(currentMonth);
        ArrayList<String> mTitles = buildTitlesStringList();
        ArrayList<ArrayList<String>> excelDataTable = buildExcelDataTable(listData);
        //add the data to excel sheet
        boolean success = saveExcelFile(this, currentMonth, mTitles, excelDataTable);
        if (success){
            mHandler.post(new DisplayToast(this, "File save in" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + " folder"));


        }
        else {
            mHandler.post(new DisplayToast(this, "Error occurred while trying to save excel file"));
        }

    }

    private static boolean saveExcelFile(Context context,
                                         Date month, ArrayList<String> titles,
                                         ArrayList<ArrayList<String>> dataTable) {

        // check if available and not read only
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.w("FileUtils", "Storage not available or read only");
            return false;
        }
        //if no roster was made for this month

        boolean success = false;
        String fileName = "Roster - " +
                UIUtils.getMonthName(month, Calendar.LONG) + " " +
                DateUtils.getYearFromDate(month) +
                ".xls";

        //New Workbook
        wb = new HSSFWorkbook();

        //New Sheet
        Sheet sheet1 = null;
        sheet1 = wb.createSheet(UIUtils.getMonthName(month, Calendar.LONG)
                + " " + DateUtils.getYearFromDate(month));

        //Generate titles
        sheet1 = createTitleRow(sheet1, titles);
        sheet1 = createDataRows(sheet1,dataTable);

        // Create a path where we will place our List of objects on external storage
        success = saveFile(context,fileName);


        return success;
    }

    private static boolean saveFile(Context context, String fileName) {

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);

        FileOutputStream os = null;
        boolean success = false;
        try {
            os = new FileOutputStream(file);
            wb.write(os);
            Log.w("FileUtils", "Writing file" + file);
            success = true;
        } catch (IOException e) {
            Log.w("FileUtils", "Error writing " + file, e);
        } catch (Exception e) {
            Log.w("FileUtils", "Failed to save file", e);
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception ex) {
            }
        }
        return success;
    }

    private static Sheet createDataRows(Sheet sheet1, ArrayList<ArrayList<String>> dataTable) {



        for (int rowI = 1;rowI < dataTable.size();rowI ++) {
            Row row = sheet1.createRow(rowI);
            ArrayList<String> rowData = dataTable.get(rowI);
            Cell c = null;

            for (int colI = 0; colI < rowData.size(); colI++) {
                boolean weekend = isWeekend(rowData.get(0));
                int style = (weekend) ? STYLE_CELL_WEEKEND : STYLE_CELL_NORMAL;
                CellStyle cs = createStyledCell(style);

                c = row.createCell(colI);
                c.setCellValue(rowData.get(colI));
                c.setCellStyle(cs);
            }

        }
        return sheet1;
    }

    private static boolean isWeekend(String s) {
        Date date = DateUtils.getDateFromString(s);
        if (date != null && DateUtils.isWeekend(date)){
            return true;
        }
        return false;
    }

    private static Sheet createTitleRow(Sheet sheet1, ArrayList<String> titles) {
        CellStyle cs = createStyledCell(STYLE_CELL_HEADER);
        Row row = sheet1.createRow(0);
        Cell c = null;
        for (int i = 0; i < titles.size(); i++) {
            c = row.createCell(i);
            c.setCellValue(titles.get(i));
            c.setCellStyle(cs);
            sheet1.setColumnWidth(i, (15 * 250));
        }

        return sheet1;
    }

    private static CellStyle createStyledCell(int style) {

        CellStyle cs = wb.createCellStyle();
        switch (style) {
            case STYLE_CELL_HEADER:
                //Cell style for header row
                cs.setFillForegroundColor(HSSFColor.TEAL.index);
                cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                break;
            case STYLE_CELL_NORMAL:
                //Cell style for normal row

                break;
            case STYLE_CELL_WEEKEND:
                //Cell style for header row
                cs.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
                cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                break;
        }

        return cs;
    }

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private ArrayList<ArrayList<String>> buildExcelDataTable(ArrayList<ItemMainView> dataTable) {
        ArrayList<ArrayList<String>> excelDataTable = new ArrayList<>();

        for (int i = 0; i < dataTable.size(); i++) {
            ArrayList<String> mDataRow = buildExcelRowStringList(dataTable.get(i));
            excelDataTable.add(mDataRow);
        }
        return excelDataTable;
    }

    private ArrayList<String> buildExcelRowStringList(ItemMainView item) {
        ArrayList<String> mData = new ArrayList<>();

        mData.add(UIUtils.getDayExcelFormat(item.getDay()));
        mData.add(item.getFirstCallName());
        mData.add(item.getSecondCallName());
        mData = handleThirdCall(mData,item);
        mData = handleDutyTypes(mData,item);
        mData.add(UIUtils.getStringFromArray(item.getLeaveDatePeople()));
        return mData;
    }

    private ArrayList<String> buildTitlesStringList() {
        ArrayList<String> mTitles = new ArrayList<>();

        mTitles.add("Date");
        mTitles.add("1st Call");
        mTitles.add("2nd Call");
        mTitles = handleThirdCallTitles(mTitles);
        mTitles = handleDutyTypesTitles(mTitles);
        mTitles.add("Leave");
        return mTitles;
    }

    private ArrayList<String> handleDutyTypesTitles(ArrayList<String> mTitles) {
        //Loop on first day to check duty type
        for (Pair pair : listData.get(0).getDutyTypeDoerPairArray()) {
            if (pair.first != null && !(pair.first).equals("")) {
                mTitles.add((String) pair.first);
            }
        }
        return mTitles;
    }

    private ArrayList<String> handleThirdCallTitles(ArrayList<String> mTitles) {
        if (ShiftHelper.isThereThirdCall(currentMonth)) {
            mTitles.add("3rd Call");
        } //add 3rd call title only if needed
        return mTitles;
    }

    private ArrayList<String> handleDutyTypes(ArrayList<String> mData , ItemMainView item) {
        //Loop on current item day to check duty doer
        for (Pair pair : item.getDutyTypeDoerPairArray()){
            if (pair.second == null || (pair.second).equals("")){
                mData.add("-");
            }else {
                mData.add((String) pair.second);
            }
        }
        return mData;
    }

    private ArrayList<String> handleThirdCall(ArrayList<String> mTitles, ItemMainView item) {
        if (ShiftHelper.isThereThirdCall(currentMonth)) {
            mTitles.add(item.getThirdCallName());
        } //add 3rd call title only if needed
        return mTitles;
    }
}
