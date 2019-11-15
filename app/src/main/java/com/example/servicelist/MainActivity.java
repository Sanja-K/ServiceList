package com.example.servicelist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    public static  String TAG ="MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RetrofitSingle retrofitSingle =new RetrofitSingle( );
        retrofitSingle.init();

        final Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.service_bot_btn));
        setSupportActionBar(toolbar);

        TabLayout tabLayout = findViewById(R.id.tabs);
        ViewPager viewPager =findViewById(R.id.view_pager);

        PagerAdapter pagerAdapter = new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                tabLayout.getTabCount(),MainActivity.this);
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("Сервисы");
        tabLayout.getTabAt(1).setText("Заявка");

        tabLayout.getTabAt(0).setIcon(android.R.drawable.sym_contact_card);
        tabLayout.getTabAt(1).setIcon(android.R.drawable.ic_menu_edit);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        toolbar.setTitle(getResources().getString(R.string.service_bot_btn));
                        break;

                    case 1:
                        toolbar.setTitle(getResources().getString(R.string.request_bot_btn));
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
