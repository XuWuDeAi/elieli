package main.zm.elieli.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import main.zm.elieli.fragment.BlankFragment;


/**
 * Created by cui on 2017/9/5.
 */

public class MainFragAdapter extends FragmentPagerAdapter {

    String[] titles = {
            "周一", "周二", "周三"
            , "周四", "周五", "周六", "周日"
    };
    // 每一页就是一个Fragment
    BlankFragment[] pages = {new BlankFragment().setPostion(0),new BlankFragment().setPostion(1),new BlankFragment().setPostion(2),new BlankFragment().setPostion(3),new BlankFragment().setPostion(4),new BlankFragment().setPostion(5),new BlankFragment().setPostion(6)};

    public MainFragAdapter(FragmentManager fm) {
        super(fm);


    }
    @Override
    public Fragment getItem(int position) {
        return pages[position];
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
