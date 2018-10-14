package main.zm.elieli.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

import main.zm.elieli.R;
import main.zm.elieli.adapter.BtItemListAdapter;
import main.zm.elieli.adapter.ListAdapter;
import main.zm.elieli.base.BaseActivity;
import main.zm.elieli.service.NetService;

public class ShowFanActivity extends BaseActivity {

    public ImageView iv_fanpic;
    public TextView tv_fanname;
    public TextView tv_attributes;
    public TextView tv_status;
    public RecyclerView rv_showdialog;
    public BtItemListAdapter btItemListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_fan);
        ImageButton bt_return = findViewById(R.id.bt_return);
        String id = getIntent().getStringExtra("id");
        iv_fanpic = findViewById(R.id.iv_fanpic);
        tv_fanname = findViewById(R.id.tv_fanname);
        tv_attributes = findViewById(R.id.tv_attributes);
        tv_status = findViewById(R.id.tv_status);
        rv_showdialog = findViewById(R.id.rv_showdialog);
        bt_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initListView();
        NetService.getShowFan(id, this);



    }

    private void initListView() {

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_showdialog.setLayoutManager(layoutManager);
        btItemListAdapter=new BtItemListAdapter(this);
        rv_showdialog.setAdapter(btItemListAdapter);



    }
}
