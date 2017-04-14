package com.zjianhao.universalcontroller;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.zjianhao.base.BaseActivity;
import com.zjianhao.module.device.SmartDeviceFragment;
import com.zjianhao.module.electrical.ElectricalFragment;
import com.zjianhao.module.pc.PCFragment;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Map<Integer,Fragment> fragmentMap = new HashMap<>();

    Fragment currentFragment;
    public static final int PC_FRAGMENT = 1;
    public static final int ELECTRICAL_FRAGMENT = 2;
    public static final int SMART_DEVICE_FRAGMENT = 3;
    private int whichFragment = PC_FRAGMENT;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //实现侧滑菜单状态栏透明
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("我的电脑");
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        changeFragment(PC_FRAGMENT);
    }


    private void changeFragment(int which){
        whichFragment = which;
        Fragment targetFragment = fragmentMap.get(which);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (which) {
            case PC_FRAGMENT:
                if (targetFragment == null){
                    targetFragment = new PCFragment();
                    fragmentMap.put(which,targetFragment);
                    Log.d("MainActivity", "pcfragment");
                }

                break;
            case ELECTRICAL_FRAGMENT:
                if (targetFragment == null){
                    targetFragment = new ElectricalFragment();
                    fragmentMap.put(which,targetFragment);
                }
                break;
            case SMART_DEVICE_FRAGMENT:
                if (targetFragment == null){
                    targetFragment = new SmartDeviceFragment();
                    fragmentMap.put(which,targetFragment);
                }
                break;
        }
        if (!targetFragment.isAdded()){
            if (currentFragment != null)
                transaction.hide(currentFragment);

                transaction.add(R.id.fragment_container,targetFragment).commit();
        }else{
            //添加过
            if (currentFragment != targetFragment)
                transaction.hide(currentFragment).show(targetFragment).commit();
        }
        currentFragment = targetFragment;
        invalidateOptionsMenu();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
////        getMenuInflater().inflate(R.menu.main,menu);
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        System.out.println("which:"+whichFragment);
//        menu.clear();
//        switch (whichFragment){
//            case PC_FRAGMENT:
////                inflater.inflate(R.menu.pc_tool_menu,menu);
//                menu.findItem(R.menu.pc_tool_menu);
//                toolbar.setTitle("我的电脑");
//                break;
//            case ELECTRICAL_FRAGMENT:
////                inflater.inflate(R.menu.electrical_tool_menu,menu);
//                menu.findItem(R.menu.electrical_tool_menu);
//                toolbar.setTitle("我的家电");
//
//                break;
//            case SMART_DEVICE_FRAGMENT:
//                toolbar.setTitle("智能设备");
//                break;
//        }
//        return super.onPrepareOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_pc) {
            // Handle the camera action
            changeFragment(PC_FRAGMENT);
        } else if (id == R.id.nav_eletrctrical) {
            changeFragment(ELECTRICAL_FRAGMENT);
        } else if (id == R.id.nav_smart_device) {
            changeFragment(SMART_DEVICE_FRAGMENT);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_setting) {

        }
        invalidateOptionsMenu();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
