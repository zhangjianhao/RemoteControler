package com.zjianhao.module.pc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zjianhao.base.BaseFragment;
import com.zjianhao.module.pc.adapter.PCHostAdapter;
import com.zjianhao.module.pc.model.Host;
import com.zjianhao.module.pc.ui.PCMenuAty;
import com.zjianhao.universalcontroller.AppApplication;
import com.zjianhao.universalcontroller.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 张建浩（Clarence) on 2017-4-10 16:26.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class PCFragment extends BaseFragment implements PCHostAdapter.HostClickListener {

    @InjectView(R.id.pc_host_list)
    RecyclerView recycleView;
    @InjectView(R.id.no_host_remind_info)
    TextView noHostRemindInfo;

    private PCHostAdapter adapter;
    private List<Host> hosts = new ArrayList<>();

    private boolean isFirst = true;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pc_fragment_main, container, false);
        ButterKnife.inject(this, view);
        adapter = new PCHostAdapter(getActivity(), hosts);
        recycleView.setAdapter(adapter);
        adapter.setOnHostClickListener(this);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getActivity().setTitle("我的电脑");
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (isFirst) {
            isFirst = false;
            Intent intent = new Intent(getActivity(), SearchHostAty.class);
            startActivityForResult(intent, SearchHostAty.REQUEST_SEARCH_HOST);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

//    @OnClick(R.id.host_search)
//    public void onClick() {
//        Intent intent = new Intent(getActivity(),SearchHostAty.class);
//        startActivityForResult(intent,SearchHostAty.REQUEST_SEARCH_HOST);
////        startActivity(intent);
//    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.pc_tool_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println("on option item selected" + item.getTitle());
        switch (item.getItemId()) {
            case R.id.pc_search_menu:
                Intent intent = new Intent(getActivity(), SearchHostAty.class);
                startActivityForResult(intent, SearchHostAty.REQUEST_SEARCH_HOST);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SearchHostAty.REQUEST_SEARCH_HOST) {
            ArrayList<Host> hosts = data.getParcelableArrayListExtra("hosts");
            if (hosts.size() > 0)
                ((AppApplication) getActivity().getApplication()).setIp(hosts.get(0).getHostIp());
            this.hosts = hosts;
            showSnackbar(recycleView, "发现" + hosts.size() + "台主机");
            adapter.setData(hosts);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onItemClick(int position) {
        Host host = hosts.get(position);
        ((AppApplication) getActivity().getApplication()).setIp(host.getHostIp());
        Intent intent = new Intent(getActivity(), PCMenuAty.class);
        startActivity(intent);
    }
}
