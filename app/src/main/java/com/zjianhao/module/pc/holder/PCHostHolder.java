package com.zjianhao.module.pc.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zjianhao.module.pc.adapter.PCHostAdapter;
import com.zjianhao.universalcontroller.R;

/**
 * Created by 张建浩（Clarence) on 2017-4-11 16:05.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class PCHostHolder extends RecyclerView.ViewHolder{
    public TextView hostname;
    public TextView hostIp;
    public TextView sysType;



    public PCHostHolder(View itemView, final PCHostAdapter.HostClickListener listener) {
        super(itemView);
        hostname = (TextView) itemView.findViewById(R.id.hostname);
        hostIp = (TextView) itemView.findViewById(R.id.host_ip);
        sysType = (TextView) itemView.findViewById(R.id.sys_type);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null)
                    listener.onItemClick(getAdapterPosition());
            }
        });

    }
}
