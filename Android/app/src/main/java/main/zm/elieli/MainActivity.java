package main.zm.elieli;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wyt.searchbox.SearchFragment;
import com.wyt.searchbox.custom.IOnSearchClickListener;

import main.zm.elieli.base.BaseActivity;
import main.zm.elieli.fragment.BlankFragment;
import main.zm.elieli.fragment.ChasingFragment;
import main.zm.elieli.fragment.DownLoadFragment;
import main.zm.elieli.fragment.HomeFragment;
import main.zm.elieli.service.NetService;

public class MainActivity extends BaseActivity {

    // 每一页就是一个Fragment
    Fragment[] pages = new Fragment[3];
    public  ViewPager rv_chasing;
    ViewPager viewpager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewpager.setCurrentItem(0, true);
                    return true;
                case R.id.navigation_dashboard:
                    viewpager.setCurrentItem(1, true);
                    return true;
                case R.id.navigation_notifications:
                    viewpager.setCurrentItem(2, true);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pages[0] = new HomeFragment();
        pages[1] = new ChasingFragment();
        pages[2] = new DownLoadFragment();

        final BottomNavigationView navigation = findViewById(R.id.navigation);
        viewpager = findViewById(R.id.viewpager);
        viewpager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        viewpager.setOffscreenPageLimit(3);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                navigation.setSelectedItemId(navigation.getMenu().getItem(position).getItemId());

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        NetService.mainActivity=this;

    }


    // ViewPager支持
    private class MyViewPagerAdapter extends FragmentPagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // 一共有几页
        @Override
        public int getCount() {
            return pages.length;
        }

        // 每一页的对象
        @Override
        public Fragment getItem(int position) {
            return pages[position];
        }

    }
}
