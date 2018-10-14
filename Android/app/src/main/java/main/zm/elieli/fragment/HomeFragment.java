package main.zm.elieli.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wyt.searchbox.SearchFragment;
import com.wyt.searchbox.custom.IOnSearchClickListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import main.zm.elieli.MainActivity;
import main.zm.elieli.R;
import main.zm.elieli.activity.ShowFanActivity;
import main.zm.elieli.adapter.ListAdapter;
import main.zm.elieli.dialog.FanListDialog;
import main.zm.elieli.dialog.SeaarchDialog;
import main.zm.elieli.dialog.WaitDialog;
import main.zm.elieli.service.NetService;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public View ConTenView;
    public RecyclerView rv_home;
    public ListAdapter listAdapter;
    public MainActivity mainActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    public HomeFragment() {

    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ConTenView = inflater.inflate(R.layout.fragment_home, container, false);
        rv_home = ConTenView.findViewById(R.id.rv_home);
        LinearLayout ll_homesearch = ConTenView.findViewById(R.id.ll_homesearch);
        BootstrapButton bt_leaderboard = ConTenView.findViewById(R.id.bt_leaderboard);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mainActivity);
        final SearchFragment searchFragment = SearchFragment.newInstance();
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_home.setLayoutManager(layoutManager);
        listAdapter = new ListAdapter(mainActivity);
        rv_home.setAdapter(listAdapter);
        NetService.getHome(mainActivity, this);
        listAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(mainActivity, ShowFanActivity.class);
                try {
                    intent.putExtra("id", listAdapter.listdate.getJSONObject(position).getString("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);


            }
        });
        searchFragment.setOnSearchClickListener(new IOnSearchClickListener() {
            @Override
            public void OnSearchClick(String keyword) {

                SeaarchDialog seaarchDialog=new SeaarchDialog();
                NetService.search(keyword,seaarchDialog);
                seaarchDialog.show(mainActivity.getSupportFragmentManager(), "");

            }
        });

        ll_homesearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchFragment.show(mainActivity.getSupportFragmentManager(), SearchFragment.TAG);
            }
        });
        bt_leaderboard.setOnClickListener(new BootstrapButton.OnClickListener() {
            @Override
            public void onClick(View view) {

                FanListDialog fanListDialog = new FanListDialog();
                NetService.getLeaderboard(mainActivity, fanListDialog);
                fanListDialog.show(mainActivity.getSupportFragmentManager(), "");
            }
        });


        return ConTenView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (listAdapter == null)
            return;//第一次加载
        if (isVisibleToUser) {//可见
            if (listAdapter.listdate.length() == 0) {
                NetService.getHome(mainActivity, this);
            }
        } else {
        }

    }

}


