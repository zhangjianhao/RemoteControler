package com.zjianhao.universalcontroller;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zjianhao.base.BaseActivity;
import com.zjianhao.http.DefaultCallback;
import com.zjianhao.http.ResponseHeader;
import com.zjianhao.http.RetrofitManager;
import com.zjianhao.model.User;
import com.zjianhao.module.electrical.ui.ElectricalFragment;
import com.zjianhao.module.pc.PCFragment;
import com.zjianhao.service.WakaUpService;
import com.zjianhao.ui.SettingAty;
import com.zjianhao.ui.UserLoginAty;
import com.zjianhao.ui.WebContentAty;
import com.zjianhao.utils.SharePreferenceUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import retrofit2.Call;

import static com.zjianhao.service.Action.ACTION_START_BAIDU_AUDIO;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Map<Integer,Fragment> fragmentMap = new HashMap<>();

    Fragment currentFragment;
    public static final int PC_FRAGMENT = 1;
    public static final int ELECTRICAL_FRAGMENT = 2;
    public static final int SMART_DEVICE_FRAGMENT = 3;
    private int whichFragment = PC_FRAGMENT;
    private Toolbar toolbar;
    private TextView headTextView;
    private DrawerLayout drawer;
    private ImageView headImg;
    private WakeOrderBroadcastReceiver wakeOrderBroadcastReceiver;

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


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        headImg = (ImageView) headerView.findViewById(R.id.head_img);
        headTextView = (TextView) headerView.findViewById(R.id.head_username);
        headTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = ((AppApplication) MainActivity.this.getApplication()).getUser();
                if (user == null) {
                    drawer.closeDrawer(GravityCompat.START);
                    Intent intent = new Intent(MainActivity.this, UserLoginAty.class);
                    startActivity(intent);
                }
            }
        });
        headerView.findViewById(R.id.log_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppApplication) getApplication()).setUser(null);
                headTextView.setText("请先登陆");
//                SharePreferenceUtils.save(MainActivity.this,"user","user_id",null);
//                SharePreferenceUtils.save(MainActivity.this,"user","token",null);
                Toast.makeText(MainActivity.this, "成功退出登陆", Toast.LENGTH_SHORT).show();

            }
        });

        changeFragment(ELECTRICAL_FRAGMENT);
        login();
        changeWakeupServiceState(true);
        wakeOrderBroadcastReceiver = new WakeOrderBroadcastReceiver();

        System.out.println("注册了广播");
        IntentFilter filter = new IntentFilter(ACTION_START_BAIDU_AUDIO);
        registerReceiver(wakeOrderBroadcastReceiver, filter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        User user = ((AppApplication) getApplication()).getUser();
        if (user != null) {
            headTextView.setText(user.getUsername());
            ImageLoader.getInstance().displayImage(user.getHeadImg(), headImg);
        }

    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    public class WakeOrderBroadcastReceiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println("接收到广播");
            switch (intent.getAction()) {
                case ACTION_START_BAIDU_AUDIO:
                    login();
                    break;
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(wakeOrderBroadcastReceiver);
        changeWakeupServiceState(false);
    }

    public void login() {
        User user = ((AppApplication) getApplication()).getUser();
        if (user == null) {
            String userId = SharePreferenceUtils.getStringValue(this, "user", "user_id");
            String token = SharePreferenceUtils.getStringValue(this, "user", "token");
            System.out.println(userId + ":" + token);
            if (userId != null && token != null) {
                Call<ResponseHeader<User>> call = RetrofitManager.getUserApi().tokenVerify(userId, token);
                call.enqueue(new DefaultCallback<User>(toolbar) {
                    @Override
                    public void onResponse(User data) {
                        ((AppApplication) getApplication()).setUser(data);
                        headTextView.setText(data.getUsername());
                        ImageLoader.getInstance().displayImage(data.getHeadImg(), headImg);
                    }

                });
            }
        }

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
                toolbar.setTitle("我的电脑");

                break;
            case ELECTRICAL_FRAGMENT:
                if (targetFragment == null){
                    targetFragment = new ElectricalFragment();
                    fragmentMap.put(which,targetFragment);
                }
                toolbar.setTitle("我的家电");
                break;
            case SMART_DEVICE_FRAGMENT:

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
        } else if (currentFragment instanceof ElectricalFragment) {
            boolean isShow = ((ElectricalFragment) currentFragment).hidePopupWindow();
            if (!isShow)
                super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.head_username) {
            Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
        }

        if (id == R.id.nav_pc) {
            // Handle the camera action
            changeFragment(PC_FRAGMENT);
        } else if (id == R.id.nav_eletrctrical) {
            changeFragment(ELECTRICAL_FRAGMENT);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_setting) {
            Intent intent = new Intent(this, SettingAty.class);
            startActivity(intent);
        } else if (id == R.id.about_author) {
            Intent intent = new Intent(this, WebContentAty.class);
            intent.putExtra("url", Constant.AUTHRO_URL);
            startActivity(intent);
        } else if (id == R.id.author_blogs) {
            Intent intent = new Intent(this, WebContentAty.class);
            intent.putExtra("url", Constant.AUTHRO_BLOGS);
            startActivity(intent);
        }
        invalidateOptionsMenu();

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void changeWakeupServiceState(boolean on) {
        Intent intent = new Intent(this, WakaUpService.class);
        if (on)
            startService(intent);
        else
            stopService(intent);

    }
}
