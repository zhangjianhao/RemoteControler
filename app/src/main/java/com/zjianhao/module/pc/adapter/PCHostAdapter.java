package com.zjianhao.module.pc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zjianhao.module.pc.holder.PCHostHolder;
import com.zjianhao.module.pc.model.Host;
import com.zjianhao.universalcontroller.R;

import java.util.List;

/**
 * Created by 张建浩（Clarence) on 2017-4-11 16:03.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class PCHostAdapter extends RecyclerView.Adapter<PCHostHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<Host> hosts;
    private HostClickListener listener;

    public interface HostClickListener{
        public void onItemClick(int position);
    }
    public PCHostAdapter(Context context, List<Host> hosts) {
        this.context = context;
        this.hosts = hosts;
        inflater = LayoutInflater.from(context);
    }

    public void setOnHostClickListener(HostClickListener listener){
        this.listener = listener;
    }

    @Override
    public PCHostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.pc_host_item,parent,false);
        PCHostHolder hostHolder = new PCHostHolder(view,listener);

        return hostHolder;
    }

    @Override
    public void onBindViewHolder(PCHostHolder holder, int position) {
        Host host = hosts.get(position);
        holder.hostIp.setText(host.getHostIp());
        holder.hostname.setText(host.getHostname());
        holder.sysType.setText(host.getSysType());
    }

    @Override
    public int getItemCount() {
        return hosts.size();
    }

    public void setData(List<Host> hosts){
        this.hosts.clear();
        this.hosts.addAll(hosts);
        notifyDataSetChanged();
    }

    public void addData(Host host){
        hosts.add(host);
        notifyItemInserted(hosts.size());
    }
}
