package com.android.lv.imageswitcher.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.lv.imageswitcher.event.EventImageItem;
import com.android.lv.imageswitcher.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2017/12/3.
 */

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "ShowEveryDayActivity";

    @Override
    public void onReceive(Context context, Intent intent) {

        //当系统到我们设定的时间点的时候会发送广播，执行这里
        Log.i(TAG, "[AlarmReceiver -- onReceive]" + DateUtil.formatDateDT(new Date().getTime()) );

        int   imageId   = intent.getIntExtra("imageId", 0);
        long  imageDate = intent.getLongExtra("imageDate", 0);
        int   position =  intent.getIntExtra("position", 0);
        Log.i(TAG, "[AlarmReceiver -- position]" + position);
        EventBus.getDefault().post(new EventImageItem(imageId, imageDate, position));
//        if(imageId == 0 && position == 0){
//            EventBus.getDefault().post(new EventEveryDayImageItem(110));
//        }
    }

    private String getDateFormat(long date)
    {
        if(date<=0) return "";

        String pattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINESE);
        return sdf.format(new Date(date));
    }
}