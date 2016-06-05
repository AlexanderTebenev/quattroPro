package com.example.alextebenev.quattropro;

import android.location.Location;
import android.location.LocationListener;
import android.support.design.widget.TabLayout;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.alextebenev.quattropro.adapter.ViewPagerAdapter;
import com.example.alextebenev.quattropro.fragments.CurentWeatherFragment;
import com.example.alextebenev.quattropro.fragments.FiveDaysWeatherFragment;
import com.example.alextebenev.quattropro.utils.Utils;

public class MainActivity extends AppCompatActivity implements CurentWeatherFragment.OnFragmentInteractionListener{
     ViewPagerAdapter adapter;
     ViewPager viewPager;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.today)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.forFiveDays)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

         viewPager = (ViewPager) findViewById(R.id.pager);
            adapter = new ViewPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    public void onCityChanged(String city) {
        FiveDaysWeatherFragment page = (FiveDaysWeatherFragment) adapter.getRegisteredFragment(1);
        if(page!=null){
            page.getWeatherForFiveDays(city,Utils.getApiKey(), "ru");
        }
    }
}
