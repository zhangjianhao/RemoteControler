package com.zjianhao.module.electrical.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zjianhao.adapter.abslistview.CommonAdapter;
import com.zjianhao.adapter.abslistview.ViewHolder;
import com.zjianhao.module.electrical.model.DeviceType;
import com.zjianhao.universalcontroller.R;

import java.util.List;

/**
 * Created by 张建浩（Clarence) on 2017-4-20 13:56.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class DeviceTypeAdapter extends CommonAdapter<DeviceType> {
    private DeviceTypeListener listener;
    private ImageLoader loader;

    public interface DeviceTypeListener {
        public void onClickDeviceType(DeviceType item);
    }

    public void setDeviceTypeListener(DeviceTypeListener listener) {
        this.listener = listener;
    }


    public DeviceTypeAdapter(Context context, int layoutId, List<DeviceType> datas) {
        super(context, layoutId, datas);
        loader = ImageLoader.getInstance();
    }

    @Override
    protected void convert(ViewHolder viewHolder, final DeviceType item, int position) {
        viewHolder.setText(R.id.device_type_text, item.getName());
        if (item.getImgUrl() != null) {
            loader.displayImage(item.getImgUrl(), (ImageView) viewHolder.getView(R.id.device_type_img));
        }
        LinearLayout typeItem = viewHolder.getView(R.id.device_type_item);
        typeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickDeviceType(item);
            }
        });

    }
}
