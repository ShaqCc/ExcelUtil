package com.bayin.excleutils;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText mEtAddress;
    private List<Order> mOrderList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bt_save).setOnClickListener(this);
        mEtAddress = (EditText) findViewById(R.id.et_address);
        Order order;
        for (int i = 0; i < 5; i++) {
            order = new Order(i+"","123998924"+i,"Jskon"+i,"上海"+i);
            mOrderList.add(order);
        }
    }

    public WritableWorkbook createWorkbook(String fileName) {
        //get the sdcard's directory
        File sdCard = Environment.getExternalStorageDirectory();
        //add on the your app's path
//        File dir = new File(sdCard.getAbsolutePath() + "/JExcelApiTest");
        File dir = new File("/data/data/com.bayin.excleutils");
        //make them in case they're not there
        Log.d(TAG,"dirPath:"+dir.getAbsolutePath());
        //create a standard java.io.File object for the Workbook to use
        File wbfile = new File(dir, fileName);

        WritableWorkbook wb = null;

        try {
            //create a new WritableWorkbook using the java.io.File and
            //WorkbookSettings from above
            wb = Workbook.createWorkbook(new FileOutputStream(wbfile));
        } catch (IOException ex) {
            Log.e(TAG, ex.getStackTrace().toString());
            Log.e(TAG, ex.getMessage());
        }

        return wb;
    }

    public WritableSheet createSheet(WritableWorkbook wb,
                                     String sheetName, int sheetIndex) {
        //create a new WritableSheet and return it
        return wb.createSheet(sheetName, sheetIndex);
    }

    public void writeCell(int columnPosition, int rowPosition, String contents, boolean headerCell,
                          WritableSheet sheet) throws RowsExceededException, WriteException {
        //create a new cell with contents at position
        Label newCell = new Label(columnPosition, rowPosition, contents);

        if (headerCell) {
            //give header cells size 10 Arial bolded
            WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
            //center align the cells' contents
            headerFormat.setAlignment(Alignment.CENTRE);
            newCell.setCellFormat(headerFormat);
        }

        sheet.addCell(newCell);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_save:
//                try {
//                    ExcelUtil.writeExcel(this,mOrderList,"ordersss");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                try{
                    File file = new File("/data/data/com.bayin.excleutils", "fxxfk.xls");
                    // 创建Excel工作表
                    WritableWorkbook wwb;
                    OutputStream os = new FileOutputStream(file);
                    wwb = Workbook.createWorkbook(os);
                    // 添加第一个工作表并设置第一个Sheet的名字
                    WritableSheet sheet = wwb.createSheet("订单", 0);
                    Label label;
                    String[] title = { "订单", "店名", "电话", "地址" };
                    for (int i = 0; i < title.length; i++) {
                        // Label(x,y,z) 代表单元格的第x+1列，第y+1行, 内容z
                        // 在Label对象的子对象中指明单元格的位置和内容
                        label = new Label(i, 0, title[i]);
                        // 将定义好的单元格添加到工作表中
                        sheet.addCell(label);
                    }
                    wwb.write();
                    wwb.close();
                }catch (Exception e){

                }

                break;
        }
    }
}
