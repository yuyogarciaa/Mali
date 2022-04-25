package com.example.mali;

import static com.google.android.material.tabs.TabLayout.GRAVITY_FILL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    TabLayout tablayout;
    ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tablayout = findViewById(R.id.tab_layout);
        viewpager = findViewById(R.id.view_pager);


        tablayout.addTab(tablayout.newTab().setText("Login"));
        tablayout.addTab(tablayout.newTab().setText("Signup"));
        tablayout.setTabGravity(GRAVITY_FILL);

        final LoginAdapter adapter = new  LoginAdapter(getSupportFragmentManager(),this,tablayout.getTabCount());
        viewpager.setAdapter(adapter);

        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));

        tablayout.setTranslationY(300);
        tablayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
    }
}