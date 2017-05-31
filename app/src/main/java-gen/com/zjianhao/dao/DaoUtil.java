package com.zjianhao.dao;

import android.content.Context;

import com.zjianhao.entity.AirCmd;
import com.zjianhao.entity.Device;
import com.zjianhao.entity.Keyas;

import java.util.List;

/**
 * Created by 张建浩（Clarence) on 2017-4-23 19:48.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class DaoUtil {
    private DaoManager manager;

    public DaoUtil(Context context) {
        manager = DaoManager.getInstance();
        manager.initManager(context);
    }

    public void insertDevice(Device device) {
        manager.getDaoSession().getDeviceDao().insert(device);
    }

    public void deleteDevice(Device device) {
        manager.getDaoSession().getDeviceDao().delete(device);
    }

    public List<Device> getDeviceList() {
        return manager.getDaoSession().getDeviceDao().loadAll();
    }


    public void insertKeyList(List<Keyas> keys) {
        KeyasDao keyasDao = manager.getDaoSession().getKeyasDao();
        for (Keyas key : keys) {
            keyasDao.insert(key);
        }
    }


    public Keyas queryKey(int deviceId, String cmd) {
        return manager.getDaoSession().getKeyasDao().queryKey(deviceId, cmd);
    }

    public List<Keyas> queryKeys(int deviceId) {
        return manager.getDaoSession().getKeyasDao().queryKeys(deviceId);
    }

    public AirCmd getAirCmd(int deviceId, String cmd, String temp) {
        AirCmdDao airCmdDao = manager.getDaoSession().getAirCmdDao();
        if ("off".equals(cmd)) {
            List<AirCmd> airCmd = airCmdDao.getAirCmd(deviceId, cmd);
            if (airCmd.size() > 0)
                return airCmd.get(0);
            return null;

        } else
            return airCmdDao.getAirCmd(deviceId, cmd, temp);
    }

    public void insertAirCmd(AirCmd airCmd) {
        manager.getDaoSession().getAirCmdDao().insert(airCmd);
    }

    public void updateKey(Keyas key) {
        manager.getDaoSession().getKeyasDao().update(key);
    }

    public void updateDevice(Device device) {
        manager.getDaoSession().getDeviceDao().update(device);
    }

    public void removeDevice(Device device) {
        DaoSession daoSession = manager.getDaoSession();
        daoSession.getDeviceDao().delete(device);
        daoSession.getKeyasDao().delete(device.getDevice_id());
    }


    public List<Device> getLastestDevice(long timestamp) {
        return manager.getDaoSession().getDeviceDao().queryDevice(timestamp);
    }

    public boolean isExist(Device device) {
        return manager.getDaoSession().getDeviceDao().isExist(device);
    }

}
