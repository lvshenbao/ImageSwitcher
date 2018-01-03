package com.android.lv.imageswitcher.event;

/**
 * Created by Administrator on 2017/12/14.
 */

public class EventEveryDayImageItem {

    private int requestCode;

    public EventEveryDayImageItem(int requestCode) {
        this.requestCode = requestCode;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

}
