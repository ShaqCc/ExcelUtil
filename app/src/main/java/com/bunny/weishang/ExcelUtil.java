package com.bunny.weishang;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import jxl.CellView;
import jxl.Workbook;
import jxl.biff.XFRecord;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * Created by Administrator on 2017/11/19.
 */

public class ExcelUtil {
    //内存地址
    public static String root = Environment.getExternalStorageDirectory()
            .getPath();

    public static void writeExcel(Context context, List<Info> exportOrder,
                                  String fileName) throws Exception {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && getAvailableStorage() > 1000000) {
            Toast.makeText(context, "SD卡不可用", Toast.LENGTH_LONG).show();
            return;
        }
        //日期	鞋款图片	尺码	发货人	收件人地址、电话	快递	备注	运单编号

        String[] title = {"日期", "鞋款图片", "尺码", "发货人", "收件人地址", "电话", "快递", "备注", "运单编号"};
        File file;
        Log.d("xxx", root);
        file = new File(root, fileName + ".xls");

        // 创建Excel工作表
        WritableWorkbook wwb;
        OutputStream os = new FileOutputStream(file);
        wwb = Workbook.createWorkbook(os);
        // 添加第一个工作表并设置第一个Sheet的名字
        WritableSheet sheet = wwb.createSheet("订单", 0);
        //设置格式
        sheet.setRowView(0, 2000);//设置高度
        Label label;
        for (int i = 0; i < title.length; i++) {
            sheet.setColumnView(i, 28);//设置宽度

            // Label(x,y,z) 代表单元格的第x+1列，第y+1行, 内容z
            // 在Label对象的子对象中指明单元格的位置和内容
            label = new Label(i, 0, title[i], getHeader());
            // 将定义好的单元格添加到工作表中
            sheet.addCell(label);
        }
        WritableCellFormat commonFormat = getCommonFormat();
        ////日期	鞋款图片	尺码	发货人	收件人地址、电话	快递	备注	运单编号
        for (int i = 0; i < exportOrder.size(); i++) {
            sheet.setRowView(i + 1, 2400);//设置高度
            Info order = exportOrder.get(i);
            Label date = new Label(0, i + 1, order.getDate(),commonFormat);
            WritableImage image = new WritableImage(1, i + 1, 1, 1, order.getImage());
            Label size = new Label(2, i + 1, order.getSize(),commonFormat);
            Label sender = new Label(3, i + 1, order.getSender(),commonFormat);
            Label address = new Label(4, i + 1, order.getAddress(),commonFormat);
            Label tel = new Label(5, i + 1, order.getTel(),commonFormat);
            Label express = new Label(6, i + 1, order.getExpress(),commonFormat);
            Label remark = new Label(7, i + 1, order.getRemark(),commonFormat);
            Label number = new Label(8, i + 1, order.getNumber(),commonFormat);


            sheet.addCell(date);
            sheet.addImage(image);
            sheet.addCell(size);
            sheet.addCell(sender);
            sheet.addCell(address);
            sheet.addCell(tel);
            sheet.addCell(express);
            sheet.addCell(remark);
            sheet.addCell(number);

            Toast.makeText(context, "写入成功", Toast.LENGTH_LONG).show();
            //弹框
            showAlertDialog(context,file.getAbsolutePath());

        }
        // 写入数据
        wwb.write();
        // 关闭文件
        wwb.close();
    }

    private static void showAlertDialog(final Context context,final String path){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("恭喜");
        builder.setMessage("生成Excel成功!\n"+"文件路径："+path);
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
//        builder.setPositiveButton("打开文件", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                openAssignFolder(context,path);
//            }
//        });
        builder.show();
    }

    public static WritableCellFormat getHeader() throws WriteException {
        WritableFont font = new WritableFont(WritableFont.TIMES, 13,
                WritableFont.BOLD);// 定义字体
        try {
            font.setColour(Colour.RED);// 蓝色字体
        } catch (WriteException e1) {
            e1.printStackTrace();
        }
        WritableCellFormat format = new WritableCellFormat(font);

        try {
            format.setAlignment(jxl.format.Alignment.CENTRE);// 左右居中
            format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 上下居中
             format.setBorder(Border.ALL, BorderLineStyle.THIN,
             Colour.BLACK);// 黑色边框
            format.setBackground(Colour.YELLOW);// 黄色背景
        } catch (WriteException e) {
            e.printStackTrace();
        }
        return format;
    }

    public static WritableCellFormat getCommonFormat() throws WriteException {

        WritableCellFormat format = new WritableCellFormat();

        try {
            format.setAlignment(jxl.format.Alignment.CENTRE);// 左右居中
            format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 上下居中
            // format.setBorder(Border.ALL, BorderLineStyle.THIN,
            // Colour.BLACK);// 黑色边框
        } catch (WriteException e) {
            e.printStackTrace();
        }
        return format;
    }

    /**
     * 获取SD可用容量
     */
    private static long getAvailableStorage() {

        StatFs statFs = new StatFs(root);
        long blockSize = statFs.getBlockSize();
        long availableBlocks = statFs.getAvailableBlocks();
        long availableSize = blockSize * availableBlocks;
        // Formatter.formatFileSize(context, availableSize);
        return availableSize;
    }

    private static void openAssignFolder(Context context,String path){
        File file = new File(path);
        if(null==file || !file.exists()){
            return;
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "file/*");
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
