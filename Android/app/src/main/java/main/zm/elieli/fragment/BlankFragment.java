package main.zm.elieli.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;

import java.util.Calendar;

import main.zm.elieli.MainActivity;
import main.zm.elieli.R;
import main.zm.elieli.activity.ShowFanActivity;
import main.zm.elieli.adapter.FanHorListAdapter;
import main.zm.elieli.adapter.ListAdapter;
import main.zm.elieli.service.NetService;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {
    int postion;
    Boolean isOneLoad = true;
    String[] titles = {
            "周一", "周二", "周三"
            , "周四", "周五", "周六", "周日"
    };
    public MainActivity mainActivity;
    public View ConTenView;
    public ListAdapter listAdapter;
    RecyclerView rv_blank;

    public BlankFragment setPostion(int postion) {
        this.postion = postion;
        return this;
    }

    public BlankFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ConTenView = inflater.inflate(R.layout.fragment_blank, container, false);
        rv_blank = ConTenView.findViewById(R.id.rv_blank);
        initListView();
        NetService.getCharsing(mainActivity, titles[postion],this);
        return ConTenView;
    }


    private void initListView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(mainActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL); //设置垂直
        rv_blank.setLayoutManager(layoutManager);
        listAdapter = new ListAdapter(mainActivity);
        rv_blank.setAdapter(listAdapter);

        listAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                Calendar cal = Calendar.getInstance();
//                int day = cal.get( Calendar.DAY_OF_WEEK );
//                mainActivity.toast(day+"");

                Intent intent = new Intent(mainActivity, ShowFanActivity.class);
                try {
                    intent.putExtra("id",listAdapter.listdate.getJSONObject(position).getString("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (ConTenView == null) {
            return;//第一次加载
        }
        if (isVisibleToUser) {//可见
            //更新数据

        } else {
        }

    }


}
