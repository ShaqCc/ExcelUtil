package com.bunny.weishang.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bunny.weishang.FileUtils;
import com.bunny.weishang.ImageUtils;
import com.bunny.weishang.Info;
import com.bunny.weishang.InfoHelper;
import com.bunny.weishang.R;
import com.zfdang.multiple_images_selector.ImagesSelectorActivity;
import com.zfdang.multiple_images_selector.SelectorSettings;

import java.util.ArrayList;

public class UpdateActivity extends AppCompatActivity {
    private static Info mOrder = null;//上个页面带来的数据
    private ArrayList<String> mResults;
    private ImageView image;
    private static boolean isUpdate = false;
    private Button bt_delete;
    private InfoHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        helper = new InfoHelper(UpdateActivity.this);

        final EditText sender = (EditText) findViewById(R.id.sender);
        final EditText date = (EditText) findViewById(R.id.date);
        final EditText size = (EditText) findViewById(R.id.size);
        final EditText address = (EditText) findViewById(R.id.address);
        final EditText tel = (EditText) findViewById(R.id.tel);
        final EditText express = (EditText) findViewById(R.id.express);
        final EditText remark = (EditText) findViewById(R.id.remark);
        final EditText number = (EditText) findViewById(R.id.number);
        image = (ImageView) findViewById(R.id.iv_pic);
        bt_delete = (Button) findViewById(R.id.delete);
        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUpdate){
                    helper.delete(mOrder);
                    Toast.makeText(UpdateActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //setdata
        if (mOrder != null) {
            sender.setText(mOrder.getSender());
            date.setText(mOrder.getDate());
            size.setText(mOrder.getSize());
            address.setText(mOrder.getAddress());
            tel.setText(mOrder.getTel());
            express.setText(mOrder.getExpress());
            remark.setText(mOrder.getRemark());
            number.setText(mOrder.getNumber());
            bt_delete.setVisibility(View.VISIBLE);
        }else {
            bt_delete.setVisibility(View.GONE);
        }
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePic();
            }
        });

        Button submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Info info = new Info();
                info.setAddress(address.getText().toString());
                info.setDate(date.getText().toString());
                info.setExpress(express.getText().toString());
                info.setNumber(number.getText().toString());
                info.setRemark(remark.getText().toString());
                info.setSender(sender.getText().toString());
                info.setSize(size.getText().toString());
                info.setTel(tel.getText().toString());
                if (mResults != null && mResults.size() > 0) {
                    info.setImage(FileUtils.getBytes(mResults.get(0)));
                }
                if (isUpdate && mOrder!=null) {
                    info.setId(mOrder.getId());
                    int update = helper.update(info);
                    Log.d("xxx","更新："+update);
                    Toast.makeText(UpdateActivity.this,"修改成功!",Toast.LENGTH_SHORT).show();
                } else
                {
                    helper.create(info);
                    Toast.makeText(UpdateActivity.this, "一条记录添加成功！\n返回点击生成Excel", Toast.LENGTH_SHORT).show();
                }
//                List<Info> list = helper.queryAll();
//                for (Info i:list) {
//                    i.toString();
//                }
            }
        });
    }

    public static void launchMe(Context activity, Info order, boolean update) {
        Intent intent = new Intent(activity, UpdateActivity.class);
        activity.startActivity(intent);
        mOrder = order;
        isUpdate = update;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOrder = null;
    }

    private void choosePic() {
        Intent intent = new Intent(this, ImagesSelectorActivity.class);
        intent.putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER, 1);
        intent.putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE, 100000);
        intent.putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA, true);
        startActivityForResult(intent, 222);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 222) {
            if (resultCode == Activity.RESULT_OK) {
                mResults = data.getStringArrayListExtra(SelectorSettings.SELECTOR_RESULTS);
                assert mResults != null;
                //更新item的图片
                Bitmap bitmap = ImageUtils.decodeSampledBitmapFromFilePath(mResults.get(0), 180, 180);
                image.setImageBitmap(bitmap);
            }
        }
    }
}
