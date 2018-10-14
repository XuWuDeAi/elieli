package main.zm.elieli.dialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;

import org.json.JSONException;

import main.zm.elieli.MainActivity;
import main.zm.elieli.R;
import main.zm.elieli.activity.ShowFanActivity;
import main.zm.elieli.adapter.FanHorListAdapter;
import main.zm.elieli.service.NetService;

/**
 * Created by zm on 2018/10/10.
 */

public class FanListDialog extends DialogFragment {


    public MainActivity mainActivity;
    public View ConTenView;
    public RecyclerView rv_dialog;
    public FanHorListAdapter fanHorListAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ConTenView = inflater.inflate(R.layout.dialog_fanlist, container);
        rv_dialog = ConTenView.findViewById(R.id.rv_dialog);
        initListView();
        return ConTenView;
    }

    private void initListView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(mainActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL); //设置垂直
        rv_dialog.setLayoutManager(layoutManager);

        fanHorListAdapter = new FanHorListAdapter(mainActivity);
        rv_dialog.setAdapter(fanHorListAdapter);
        fanHorListAdapter.setOnItemClickListener(new FanHorListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(mainActivity, ShowFanActivity.class);
                try {
                    intent.putExtra("id",fanHorListAdapter.listdate.getJSONObject(position).getString("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // 当对话框显示时，调整对话框的窗口位置
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

            // 设置对话框的窗口显示
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.dimAmount = 0.3f; // 背景灰度
            lp.gravity = Gravity.TOP;// 靠上显示
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.height = ScreenUtils.getScreenHeight() - 210 - SizeUtils.dp2px(56f);
            window.setAttributes(lp);
        }


    }
}
