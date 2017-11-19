package com.bunny.weishang.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bunny.weishang.ExcelUtil;
import com.bunny.weishang.Info;
import com.bunny.weishang.InfoHelper;
import com.bunny.weishang.Order;
import com.bunny.weishang.OrderAdapter;
import com.bunny.weishang.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private List<Info> mDataList;
    private OrderAdapter orderAdapter;
    private InfoHelper infoHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mDataList = new ArrayList<>();
        orderAdapter = new OrderAdapter(mDataList);
        recyclerView.setAdapter(orderAdapter);

        findViewById(R.id.tv_add).setOnClickListener(this);
        findViewById(R.id.tv_excel).setOnClickListener(this);

        //生成数据库helper
        infoHelper = new InfoHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //query db
        mDataList = infoHelper.queryAll();
        orderAdapter.refresh(mDataList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_excel:
                if (orderAdapter != null && orderAdapter.getItemCount() > 0) {
                    //生成excel
                    try {
                        ExcelUtil.writeExcel(MainActivity.this,mDataList,"发货单");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "请添加数据！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_add:
                UpdateActivity.launchMe(MainActivity.this, null, false);
                break;
        }
    }

}
