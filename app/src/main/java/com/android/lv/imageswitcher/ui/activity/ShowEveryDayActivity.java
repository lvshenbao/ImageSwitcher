package com.android.lv.imageswitcher.ui.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.lv.imageswitcher.R;
import com.android.lv.imageswitcher.application.MyApplication;
import com.android.lv.imageswitcher.base.BaseActivity;
import com.android.lv.imageswitcher.event.EventEveryDayImageItem;
import com.android.lv.imageswitcher.event.EventImageItem;
import com.android.lv.imageswitcher.persist.DelayImageItem;
import com.android.lv.imageswitcher.receiver.AlarmReceiver;
import com.android.lv.imageswitcher.util.DateUtil;
import com.bumptech.glide.Glide;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2017/12/13.
 */

public class ShowEveryDayActivity extends BaseActivity {

    private static final String TAG = "ShowEveryDayActivity";

    //--------------------------------------------------------------------------

    private MyApplication m_application;

    private ArrayList<DelayImageItem> imageList;
    private ArrayList<ImageView>      imageViews;

    //当前页面
    private int currentItem = 0;

    private AlarmManager  am;
    private PendingIntent pi;

    //--------------------------------------------------------------------------

    @Override
    public void prev(Bundle savedInstanceState) {
        super.prev(savedInstanceState);
        init(R.layout.activity_show);

        EventBus.getDefault().register(this);

        m_application = MyApplication.getApplication();
    }

    private ViewPager vp_content;

    @Override
    public void find() {
        vp_content = (ViewPager) findViewById(R.id.vp_content);
    }

    @Override
    public void bind() {

        vp_content.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentItem = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void rend() {

        imageList = new ArrayList<>();
        imageList = (ArrayList<DelayImageItem>) m_application.getDelayImages();

        imageViews = new ArrayList<>();
        for(int i = 0; i < imageList.size(); i++){
            ImageView imageView = new ImageView(this);
            Glide.with(ShowEveryDayActivity.this)
                    .load(Uri.fromFile(new File(imageList.get(i).getImagePath())))
                    .placeholder(R.drawable.image_default)
                    .crossFade()
                    .into(imageView);
            imageViews.add(imageView);
        }

        vp_content.setAdapter(new ShowEveryDayActivity.Madapter());

        startRemind();
    }

    @Override
    public void post() {

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void destroy() {
        EventBus.getDefault().unregister(this);
        //取消警报
        if(am != null && pi != null) am.cancel(pi);
    }

    //--------------------------------------------------------------------------

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     *      适配器
     */
    class Madapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViews.size();
        }

        //实例化item
        @Override
        public Object instantiateItem(View arg0, int arg1) {
            //将每个图片加入到ViewPager里
            ((ViewPager)arg0).addView(imageViews.get(arg1));
            return imageViews.get(arg1);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            //将每个图片在ViewPager里释放掉
            ((ViewPager)arg0).removeView((View)arg2);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            //view 和 Object 是不是一个对象
            return arg0 == arg1;
        }


        @Override
        public void finishUpdate(View arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            // TODO Auto-generated method stub
        }

        @Override
        public Parcelable saveState() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
            // TODO Auto-generated method stub

        }
    }

    //===========================================================================

    private Calendar mCalendar;

    private void startRemind(){

//        Timer timer = new Timer();
//        TimerTask task = new TimerTask(){
//            @Override
//            public void run() {
//                checkTime();
//            }
//        };
//
//        timer.schedule(task, 1*1000, 10*1000);

        //得到AlarmManager实例 AlarmReceiver.class为广播接受者
        am = (AlarmManager)getSystemService(ALARM_SERVICE);

        mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(System.currentTimeMillis());

        int sys_hour   = DateUtil.getHours(mCalendar.getTimeInMillis());
        int sys_minute = DateUtil.getMinute(mCalendar.getTimeInMillis());

        Log.i(TAG, "[mCalendar]" + getDateFormat(mCalendar.getTimeInMillis()));

        int  position = 0;

        for(int i = 0; i < imageList.size(); i++){
           DelayImageItem imageItem = imageList.get(i);
           long imageDate = imageItem.getImageDate();

           int image_hour   = DateUtil.getHours(imageDate);
           int image_minute = DateUtil.getMinute(imageDate);

           if((image_hour < sys_hour) || (image_hour == sys_hour && image_minute < sys_minute)){
                position = i;
               Log.i(TAG, "[position]" + position);

               if(position > 0 && position == imageList.size() -1){
                   Log.i(TAG, "[跳后一张 position]" + position);
                   mCalendar.add(Calendar.DAY_OF_MONTH, 1);
                   nextDay();
               }
           }
           else
           {
               Intent intent = new Intent(ShowEveryDayActivity.this, AlarmReceiver.class);

               mCalendar.set(Calendar.HOUR_OF_DAY, image_hour);
               mCalendar.set(Calendar.MINUTE, image_minute);
               mCalendar.set(Calendar.SECOND, 0);

               intent.putExtra("imageId",   imageItem.getId());
               intent.putExtra("imageDate", mCalendar.getTimeInMillis());
               intent.putExtra("position" , i);
               pi = PendingIntent.getBroadcast(ShowEveryDayActivity.this, i, intent, PendingIntent.FLAG_UPDATE_CURRENT);

               Log.i(TAG, "[初始发送警告时间]：" +  getDateFormat(mCalendar.getTimeInMillis()));

               if(Build.VERSION.SDK_INT < 19){
                   am.set(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), pi);
               }else{
                   am.setExact(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), pi);
               }
           }
        }
        Log.i(TAG, "[最终 position]" + position);
        vp_content.setCurrentItem(position);
    }

    private String getDateFormat(long date)
    {
        if(date<=0) return "";

        String pattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINESE);
        return sdf.format(new Date(date));
    }

    //--------------------------------------------------------------------------

    private void nextDay(){
        for(int i = 0; i < imageList.size(); i++){
            DelayImageItem imageItem = imageList.get(i);
            long imageDate = imageItem.getImageDate();

            int image_hour   = DateUtil.getHours(imageDate);
            int image_minute = DateUtil.getMinute(imageDate);

            Intent intent = new Intent(ShowEveryDayActivity.this, AlarmReceiver.class);

            mCalendar.set(Calendar.HOUR_OF_DAY, image_hour);
            mCalendar.set(Calendar.MINUTE, image_minute);
            mCalendar.set(Calendar.SECOND, 0);

            intent.putExtra("imageId",   imageItem.getId());
            intent.putExtra("imageDate", mCalendar.getTimeInMillis());
            intent.putExtra("position" , i);
            pi = PendingIntent.getBroadcast(ShowEveryDayActivity.this, i, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Log.i(TAG, "[发送警告时间]：" +  getDateFormat(mCalendar.getTimeInMillis()));

            if(Build.VERSION.SDK_INT < 19){
                am.set(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), pi);
            }else{
                am.setExact(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), pi);
            }
        }
    }

    //--------------------------------------------------------------------------

    public void onEventMainThread(EventImageItem event) {
        Log.i(TAG, "我收到了消息");

        if(event != null){
            int position = event.getPosition();
            if(imageList != null && imageList.size() > position){
                Log.i(TAG, "[我收到了消息 position]:" + position);
                vp_content.setCurrentItem(position);

                if(position > 0 && position == imageList.size() -1){
                    mCalendar.add(Calendar.DAY_OF_MONTH, 1);
                    nextDay();
                }
            }
        }

//        //获取当前毫秒值
//        long systemTime = System.currentTimeMillis();
//        Log.i(TAG, "[我收到了消息 time]:" + DateUtil.formatDateDT(systemTime));
//
//        if(event != null){
//            if(event.getRequestCode() != 110) return;
//
//            int sys_hour   = DateUtil.getHours(systemTime);
//            int sys_minute = DateUtil.getMinute(systemTime);
//
//            for(int i = 0; i < imageList.size(); i++){
//
//                DelayImageItem delayImageItem = imageList.get(i);
//                long imageDate = delayImageItem.getImageDate();
//
//                int image_hour   = DateUtil.getHours(imageDate);
//                int image_minute = DateUtil.getMinute(imageDate);
//
//                if(image_hour == sys_hour && sys_minute == image_minute){
//                    vp_content.setCurrentItem(i);
//                }
//            }
//        }
    }

//    private void checkTime(){
//        long systemTime = System.currentTimeMillis();
//
//        Log.i(TAG, "[checkTime]:" + DateUtil.formatDateDT(systemTime));
//
//        int sys_hour   = DateUtil.getHours(systemTime);
//        int sys_minute = DateUtil.getMinute(systemTime);
//
//        for(int i = 0; i < imageList.size(); i++){
//
//            DelayImageItem delayImageItem = imageList.get(i);
//            long imageDate = delayImageItem.getImageDate();
//
//            int image_hour   = DateUtil.getHours(imageDate);
//            int image_minute = DateUtil.getMinute(imageDate);
//
//            if(image_hour == sys_hour && sys_minute == image_minute){
//                vp_content.setCurrentItem(i);
//            }
//        }
//    }

}
