package com.android.lv.imageswitcher.ui.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;

import com.android.lv.imageswitcher.R;
import com.android.lv.imageswitcher.base.BaseActivity;
import com.android.lv.imageswitcher.ui.fragment.CalendarFragment;
import com.android.lv.imageswitcher.ui.fragment.EverydayFragment;
import com.android.lv.imageswitcher.ui.fragment.HomeFragment;
import com.android.lv.imageswitcher.ui.fragment.SettingsFragment;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

/**
 * Created by Administrator on 2017/12/3.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private ResideMenu resideMenu;

    @Override
    public void prev(Bundle savedInstanceState) {
        super.prev(savedInstanceState);
        init(R.layout.activity_main);

        if( savedInstanceState == null ){
            changeFragment(new HomeFragment());
        }
    }

    @Override
    public void find() {

    }

    @Override
    public void bind() {

    }

    private ResideMenuItem itemHome;
    private ResideMenuItem itemCalendar;
    private ResideMenuItem itemEveryDay;
    private ResideMenuItem itemSettings;


    @Override
    public void rend() {
        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.mipmap.menu_background);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        itemHome     = new ResideMenuItem(this, R.mipmap.icon_home,      "Home");
        itemCalendar = new ResideMenuItem(this, R.mipmap.icon_calendar,  "Calendar");
        itemEveryDay = new ResideMenuItem(this, R.mipmap.icon_today,     "EveryDay");
        itemSettings = new ResideMenuItem(this, R.mipmap.icon_settings,  "Settings");

        itemHome.setOnClickListener(this);
        itemCalendar.setOnClickListener(this);
        itemEveryDay.setOnClickListener(this);
        itemSettings.setOnClickListener(this);

        resideMenu.addMenuItem(itemHome,     ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemCalendar, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemEveryDay, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_LEFT);

        // You can disable a direction by setting ->
         resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
    }

    @Override
    public void post() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }


    //==============================================================================================

    @Override
    public void onClick(View view) {

        if (view == itemHome){
            changeFragment(new HomeFragment());
        }else if (view == itemCalendar){
            changeFragment(new CalendarFragment());
        }else if (view == itemEveryDay){
            changeFragment(new EverydayFragment());
        }else if (view == itemSettings){
            changeFragment(new SettingsFragment());
        }

        resideMenu.closeMenu();
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
//            Toast.makeText(MainActivity.this, "Menu is opened!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void closeMenu() {
//            Toast.makeText(MainActivity.this, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };

    //==============================================================================================

    private void changeFragment(Fragment targetFragment){
//        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
