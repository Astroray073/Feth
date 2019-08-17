package com.kyigames.feth;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.kyigames.feth.utils.ResourceUtils;

import java.util.List;

import devlight.io.library.ntb.NavigationTabBar;

public class MainActivity extends AppCompatActivity {
    private static final int StartPageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ResourceUtils.initialize(this);
        initUI();
    }

    private void initUI() {
        final ViewPager viewPager = findViewById(R.id.view_pager);
        final PageManager pageManager = new PageManager(this);

        viewPager.setAdapter(pageManager);

        final NavigationTabBar navigationTabBar = findViewById(R.id.ntb_horizontal);
        final List<NavigationTabBar.Model> models = pageManager.buildTabMenus();

        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, StartPageIndex);
    }
}
