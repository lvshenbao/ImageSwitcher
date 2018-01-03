package com.android.lv.imageswitcher.event;

/**
 * Created by Administrator on 2017/12/5.
 */

public class EventImageItem {

    private int  id;
    private long imageDate;
    private int  position;

    public EventImageItem(int id, long imageDate, int position) {
        this.id = id;
        this.imageDate = imageDate;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getImageDate() {
        return imageDate;
    }

    public void setImageDate(long imageDate) {
        this.imageDate = imageDate;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
