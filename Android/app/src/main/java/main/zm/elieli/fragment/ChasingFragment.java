package main.zm.elieli.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import main.zm.elieli.MainActivity;
import main.zm.elieli.R;
import main.zm.elieli.adapter.MainFragAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChasingFragment extends Fragment {

    public View ConTenView;
    public MainActivity mainActivity;
    private ArrayList<Fragment> mFragments = new ArrayList<>();


    public ChasingFragment() {
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
        ConTenView = inflater.inflate(R.layout.fragment_chasing, container, false);
        ViewPager rv_chasing = ConTenView.findViewById(R.id.rv_chasing);
        mainActivity.rv_chasing=rv_chasing;
        MainFragAdapter adapter = new MainFragAdapter(getChildFragmentManager());
        rv_chasing.setAdapter(adapter);
        /** indicator圆角色块 */
        SlidingTabLayout tl_10 = ConTenView.findViewById(R.id.tl_10);
        tl_10.setViewPager(rv_chasing);

        rv_chasing.setCurrentItem(getWeekOfDate(new Date()));
        return ConTenView;
    }


    /**
     * 获取当前日期是星期几<br>
     *
     * @param date
     * @return 当前日期是星期几
     */
    public int getWeekOfDate(Date date) {
        int[] weekDays = { 6, 0, 1, 2, 3, 4, 5};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

}
