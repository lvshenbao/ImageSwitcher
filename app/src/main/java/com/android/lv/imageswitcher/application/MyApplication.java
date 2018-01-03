package com.android.lv.imageswitcher.application;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.lv.imageswitcher.Iconfig;
import com.android.lv.imageswitcher.persist.DelayImageItem;
import com.android.lv.imageswitcher.persist.ImageItem;

import java.util.ArrayList;
import java.util.List;

import cc.zuv.android.database.SmartRecord;
import cc.zuv.android.fileio.SharePref;

/**
 * Created by Administrator on 2017/12/1.
 */

public class MyApplication  extends Application implements Iconfig{

    private static final String TAG = "MyApplication";

    //----------------------------------------------------------------

    private Context context;
    private static MyApplication myApplication = null;

    private SharePref sharepref;

    //----------------------------------------------------------------

    public static MyApplication getApplication() {
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        context = getApplicationContext();

        init_smartrecord();
        init_sharepref(context);
    }

    SmartRecord smartrecord;

    public SmartRecord getDbHelper()
    {
        return smartrecord;
    }

    private void init_smartrecord()
    {
        Log.i(TAG,"[init_smartrecord]" );
        String dbFile = DB_FILE;
        smartrecord = SmartRecord.create(this, null, dbFile, true, DB_VERSION, dbupdatelistener);
    }

    SmartRecord.DbUpdateListener dbupdatelistener = new SmartRecord.DbUpdateListener()
    {
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.i(TAG,"oldVersion="+oldVersion+ " + newVersion="+newVersion);
        }
    };

    //----------------------------------------------------------------------------

    public List<ImageItem> getImages(){
        return smartrecord.findAll(ImageItem.class, "imageDate Asc");
    }

    public List<DelayImageItem> getDelayImages(){
        return smartrecord.findAll(DelayImageItem.class, "imageDate Asc");
    }

    public ImageItem getImage(int ImageId){
        List<ImageItem> images = smartrecord.findAllByWhere(ImageItem.class, "id=" + ImageId);
        if(images != null && images.size() > 0){
            return images.get(0);
        }
        else {
            return null;
        }
    }

    public DelayImageItem getDelayImage(int ImageId){
        List<DelayImageItem> images = smartrecord.findAllByWhere(DelayImageItem.class, "id=" + ImageId);
        if(images != null && images.size() > 0){
            return images.get(0);
        }
        else {
            return null;
        }
    }

    public void saveImage(ImageItem imageItem){
        Log.i(TAG,"[saveImage]" );
        if(imageItem != null){
            Log.i(TAG,"[save]" );
            smartrecord.save(imageItem);
        }
    }

    public void saveDelayImage(DelayImageItem imageItem){
        Log.i(TAG,"[saveImage]" );
        if(imageItem != null){
            Log.i(TAG,"[save]" );
            smartrecord.save(imageItem);
        }
    }

    public void updateImage(int id, ImageItem imageItem){
        if(id > 0 && imageItem != null){
            smartrecord.update(imageItem, "id=" + id);
        }
    }

    public void updateDelayImage(int id, DelayImageItem imageItem){
        if(id > 0 && imageItem != null){
            smartrecord.update(imageItem, "id=" + id);
        }
    }

    public void deleteImage(int id){
        if(id > 0){
            smartrecord.deleteById(ImageItem.class, id);
        }
    }

    public void deleteDelayImage(int id){
        if(id > 0){
            smartrecord.deleteById(DelayImageItem.class, id);
        }
    }

    //------------------------------------------------------------------------

    private void init_sharepref(Context context)
    {
        String spFile = "imageswitcher";
        sharepref = new SharePref(context, spFile);
    }

    public void setPeriod(long period){
        sharepref.trans_begin();
        sharepref.setLong("period", period);
        sharepref.trans_commit();
    }

    public long getPeriod(){
        return sharepref.getLong("period", 0);
    }
}
