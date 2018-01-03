package com.android.lv.imageswitcher.persist;

import android.net.Uri;

import cc.zuv.android.database.annotation.Table;
import cc.zuv.android.database.annotation.Unique;

/**
 * Created by Administrator on 2017/12/3.
 */
@Table(name="tbl_imageitem_1")
public class ImageItem {

    @Unique
    private int    id;
    private long   ImageDate;
    private String imagePath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public long getImageDate() {
        return ImageDate;
    }

    public void setImageDate(long imageDate) {
        ImageDate = imageDate;
    }
}
