package com.android.lv.imageswitcher.ui.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.lv.imageswitcher.R;
import com.android.lv.imageswitcher.application.MyApplication;
import com.android.lv.imageswitcher.base.BaseActivity;
import com.android.lv.imageswitcher.event.EventImageItem;
import com.android.lv.imageswitcher.persist.ImageItem;
import com.android.lv.imageswitcher.receiver.AlarmReceiver;
import com.android.lv.imageswitcher.util.DateUtil;
import com.android.lv.imageswitcher.util.StringUtil;
import com.bumptech.glide.Glide;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import de.greenrobot.event.EventBus;

/**
 *    图片切换器
 */
public class ShowActivity extends BaseActivity {

    private static final String TAG = "ShowActivity";

    //--------------------------------------------------------------------------

    private MyApplication m_application;

    private ScheduledExecutorService scheduledExecutorService;

    private ArrayList<ImageItem> imageList;
    private ArrayList<ImageView> imageViews;

    //当前页面
    private int currentItem = 0;

    private int  modeType = 0; // 0 默认 5秒； 1 设置间隔时间； 2 设置定时跳转
    private long period   = 0;

    private boolean hasDate = true;

    //--------------------------------------------------------------------------

    @Override
    public void prev(Bundle savedInstanceState) {
        super.prev(savedInstanceState);
        init(R.layout.activity_show);

        EventBus.getDefault().register(this);

        modeType = getIntent().getIntExtra("modeType", 0);
        period   = getIntent().getLongExtra("period", 0);

        Log.i(TAG, "[modeType]" + modeType );
        Log.i(TAG, "[period]"   + period );
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
        imageList = (ArrayList<ImageItem>) m_application.getImages();

        imageViews = new ArrayList<>();
        for(int i = 0; i < imageList.size(); i++){
            ImageView imageView = new ImageView(this);
            if(StringUtil.isNullOrEmpty(imageList.get(i).getImagePath())) return;
            Glide.with(ShowActivity.this)
                .load(Uri.fromFile(new File(imageList.get(i).getImagePath())))
                .placeholder(R.drawable.image_default)
                .crossFade()
                .into(imageView);
            imageViews.add(imageView);
        }

        vp_content.setAdapter(new Madapter());

        if(modeType == 2){
            startRemind();
        }
    }

    @Override
    public void post() {

    }

    @Override
    protected void onStop() {
        super.onStop();

        //停止图片切换
        if(scheduledExecutorService != null){
            scheduledExecutorService.shutdown();
        }
    }

    @Override
    public void destroy() {
        EventBus.getDefault().unregister(this);
        stopRemind();
    }

    //--------------------------------------------------------------------------

    @Override
    protected void onStart() {
        super.onStart();

        Log.i(TAG, "[onStart]" );

        switch (modeType){
            case 0 :
            case 1:
                Log.i(TAG, "[onStart -- modeType]" + modeType );
                //用一个定时器  来完成图片切换
                //Timer 与 ScheduledExecutorService 实现定时器的效果
                scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
                //通过定时器 来完成 每2秒钟切换一个图片
                //经过指定的时间后，执行所指定的任务
                //scheduleAtFixedRate(command, initialDelay, period, unit)
                //command 所要执行的任务
                //initialDelay 第一次启动时 延迟启动时间
                //period  每间隔多次时间来重新启动任务
                //unit 时间单位

                scheduledExecutorService.scheduleAtFixedRate(new ViewPagerTask(), period != 0 ?  period : 5, period != 0 ?  period : 5, TimeUnit.SECONDS);
                break;

            case 2:
                break;
        }
    }

    //--------------------------------------------------------------------------

    //用来完成图片切换的任务
    private class ViewPagerTask implements Runnable{

        public void run() {
            //实现我们的操作
            //改变当前页面
            currentItem = (currentItem + 1) % imageViews.size();
            //Handler来实现图片切换
            handler.obtainMessage().sendToTarget();
        }
    }

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            //设定viewPager当前页面
            vp_content.setCurrentItem(currentItem);
        }
    };

    //--------------------------------------------------------------------------

    /**
     *      适配器
     */
    class Madapter extends PagerAdapter{

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

    private AlarmManager am;
    private Calendar     mCalendar;

    /**
     * 开启提醒
     */
    private void startRemind(){

        //获取当前毫秒值
        long systemTime = System.currentTimeMillis();
        Log.i(TAG, "[系统时间]：" + getDateFormat(systemTime));
        //得到AlarmManager实例
        am = (AlarmManager)getSystemService(ALARM_SERVICE);

        int position = 0;

        for(int i = 0; i < imageList.size(); i++){

            ImageItem imageItem = imageList.get(i);

            long imageDate = imageItem.getImageDate();

            if(systemTime < imageDate){

                Log.i(TAG, "[发出 警告]：" + getDateFormat(imageDate));

                //AlarmReceiver.class为广播接受者
                Intent intent = new Intent(ShowActivity.this, AlarmReceiver.class);
                intent.putExtra("imageId",   imageItem.getId());
                intent.putExtra("imageDate", imageDate);
                intent.putExtra("position" , i);
                PendingIntent pi = PendingIntent.getBroadcast(ShowActivity.this, imageItem.getId(), intent, 0);

                if(Build.VERSION.SDK_INT < 19){
                    am.set(AlarmManager.RTC_WAKEUP, imageDate, pi);
                }else{
                    am.setExact(AlarmManager.RTC_WAKEUP, imageDate, pi);
                }
            }
            else
            {
                position   = i;
            }
        }

        vp_content.setCurrentItem(position);

        // 如果当前时间大于设置的时间，那么就从第二天的设定时间开始
//        if(systemTime > selectTime) {
//            mCalendar.add(Calendar.DAY_OF_MONTH, 1);
//        }

        //**********注意！！下面的两个根据实际需求任选其一即可*********

        /**
         * 单次提醒
         * mCalendar.getTimeInMillis() 上面设置的13点25分的时间点毫秒值
         */
//        am.set(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), pi);

        /**
         * 重复提醒
         * 第一个参数是警报类型；下面有介绍
         * 第二个参数网上说法不一，很多都是说的是延迟多少毫秒执行这个闹钟，但是我用的刷了MIUI的三星手机的实际效果是与单次提醒的参数一样，即设置的13点25分的时间点毫秒值
         * 第三个参数是重复周期，也就是下次提醒的间隔 毫秒值 我这里是一天后提醒
         */
//        am.setRepeating(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), (1000 * 60 * 60 * 24), pi);

    }

    private void startRemindNoDate(){
        Log.i(TAG, "[startRemindNoDate]：");
        //得到日历实例，主要是为了下面的获取时间
        mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(System.currentTimeMillis());

        //是设置日历的时间，主要是让日历的年月日和当前同步
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        // 这里时区需要设置一下，不然可能个别手机会有8个小时的时间差
        mCalendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        //得到AlarmManager实例
        am = (AlarmManager)getSystemService(ALARM_SERVICE);

        int position = 0;

        //获取当前毫秒值
        long systemTime = System.currentTimeMillis();

        int sys_year   = DateUtil.getYear(systemTime);
        int sys_month  = DateUtil.getMonth(systemTime);
        int sys_day    = DateUtil.getDay(systemTime);
        int sys_hour   = DateUtil.getHours(systemTime);
        int sys_minute = DateUtil.getMinute(systemTime);

        for(int i = 0; i < imageList.size(); i++){

            ImageItem imageItem = imageList.get(i);

            long imageDate = imageItem.getImageDate();

            int image_hour   = DateUtil.getHours(imageDate);
            int image_minute = DateUtil.getMinute(imageDate);

            Log.i(TAG, "[图片时间]：" + image_hour + "：" + image_minute);
            Log.i(TAG, "[系统时间]：" + sys_hour   + "：" + sys_minute);

//            if(sys_hour < image_hour || (image_hour == sys_hour && sys_minute < image_minute)){
//                position = i;
//                Log.i(TAG, "[position]：" + position);
//            }

            mCalendar.set(Calendar.YEAR,   sys_year);
            mCalendar.set(Calendar.MONTH,  sys_month - 1);
            mCalendar.set(Calendar.DATE,   sys_day);
            mCalendar.set(Calendar.HOUR,   image_hour);
            mCalendar.set(Calendar.MINUTE, image_minute);
            mCalendar.set(Calendar.SECOND, 0);

            Log.i(TAG, "[发出 警告]imageDate：" + getDateFormat(imageDate));
            Log.i(TAG, "[发出 警告]mCalendar：" + getDateFormat(mCalendar.getTimeInMillis()));

            long selectTime = 0;

            if(systemTime > mCalendar.getTimeInMillis()) {
                Log.i(TAG, "[设置的时间小于当前时间]：" + getDateFormat(mCalendar.getTimeInMillis()));
                Toast.makeText(ShowActivity.this,"", Toast.LENGTH_SHORT).show();
                mCalendar.add(Calendar.DAY_OF_MONTH, 1);
            }

            selectTime = mCalendar.getTimeInMillis();

            Log.i(TAG, "[系统时间]systemTime：" + systemTime);
            Log.i(TAG, "[最后时间]selectTime：" + selectTime);

            long time = (selectTime - systemTime)/1000;

            //AlarmReceiver.class为广播接受者
            Intent intent = new Intent(ShowActivity.this, AlarmReceiver.class);
            intent.putExtra("imageId",   imageItem.getId());
            intent.putExtra("imageDate", time);
            intent.putExtra("position" , i);

            Log.i(TAG, "[延时时间]time：" + time);
            Log.i(TAG, "[广播ID]：" + imageItem.getId());

            PendingIntent pi = PendingIntent.getBroadcast(ShowActivity.this, imageItem.getId(), intent, 0);
            am.setRepeating(AlarmManager.RTC_WAKEUP, selectTime,  AlarmManager.INTERVAL_DAY, pi);
        }

        vp_content.setCurrentItem(position);
    }

    /**
     * 关闭提醒
     */
    private void stopRemind(){
        Log.i(TAG, "[stopRemind]");
        for(int i = 0; i < imageList.size(); i++){
            ImageItem imageItem = imageList.get(i);
            Intent intent = new Intent(ShowActivity.this, AlarmReceiver.class);
            PendingIntent pi = PendingIntent.getBroadcast(ShowActivity.this, imageItem.getId(), intent, 0);
            //取消警报
            if(am != null) am.cancel(pi);
        }
    }

    //--------------------------------------------------------------------------

    private String getDateFormat(long date)
    {
        if(date<=0) return "";

        String pattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINESE);
        return sdf.format(new Date(date));
    }

    //--------------------------------------------------------------------------

    public void onEventMainThread(EventImageItem event) {
        Log.i(TAG, "我收到了消息");
        if(event != null){
           int position = event.getPosition();
           if(imageList != null && imageList.size() > position){
               Log.i(TAG, "[我收到了消息 position]:" + position);
               vp_content.setCurrentItem(position);
           }
        }
    }

}
