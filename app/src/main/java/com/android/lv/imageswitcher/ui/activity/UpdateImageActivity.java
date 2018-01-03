package com.android.lv.imageswitcher.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.lv.imageswitcher.R;
import com.android.lv.imageswitcher.application.MyApplication;
import com.android.lv.imageswitcher.base.BaseActivity;
import com.android.lv.imageswitcher.persist.ImageItem;
import com.android.lv.imageswitcher.util.StringUtil;
import com.android.lv.imageswitcher.view.datepicker.ZuvDatePicker;
import com.bumptech.glide.Glide;

import java.io.File;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2017/12/4.
 */

public class UpdateImageActivity extends BaseActivity {

    private static final String TAG = "UpdateImageActivity" ;

    //--------------------------------------------------------------------

    private MyApplication m_application;
    //调用系统相册-选择图片
    private static final int IMAGE = 1;

    private String imagePath = "";
    private long   imageDate = 0;

    private int    imageId = 0;

    private ImageItem imageItem = null;

    //--------------------------------------------------------------------

    @Override
    public void prev(Bundle savedInstanceState) {
        super.prev(savedInstanceState);
        init(R.layout.activity_addimage);

        m_application = MyApplication.getApplication();
    }

    private Button bt_add_image;
    private ImageView iv_add_image;
    private TextView tv_add_image;
    private Button    bt_add_deter;

    private TextView       tv_image_date;
    private RelativeLayout rl_add_image;

    @Override
    public void find() {
        bt_add_image  = (Button) findViewById(R.id.bt_add_image);
        iv_add_image  = (ImageView) findViewById(R.id.iv_add_image);
        tv_add_image  = (TextView) findViewById(R.id.tv_add_image);
        bt_add_deter  = (Button) findViewById(R.id.bt_add_deter);
        tv_image_date = (TextView) findViewById(R.id.tv_image_date);

        rl_add_image = (RelativeLayout) findViewById(R.id.rl_add_image);
    }

    @Override
    public void bind() {
        bt_add_image.setOnClickListener(onClickListener);
        bt_add_deter.setOnClickListener(onClickListener);
        rl_add_image.setOnClickListener(onClickListener);
    }

    private ZuvDatePicker date_picker;

    @Override
    public void rend() {

        imageId = getIntent().getIntExtra("imageId", 0);

        date_picker = new ZuvDatePicker(this, new ZuvDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                tv_image_date.setText(time);
            }
        }, "2016-01-01 00:00", "2020-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        date_picker.showSpecificTime(true); // 显示时和分
        date_picker.setIsLoop(true); // 允许循环滚动

        if(imageId > 0){
            imageItem = m_application.getImage(imageId);
        }

        if(imageItem != null){
            String now = getDateFormat(imageItem.getImageDate());
            tv_image_date.setText(now);
            tv_add_image.setText(imageItem.getImagePath());
            showImage(imageItem.getImagePath());
        }
    }

    @Override
    public void post() {

    }

    @Override
    public void destroy() {

    }

    //--------------------------------------------------------------

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_add_image:
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, IMAGE);
                    break;

                case R.id.rl_add_image:
                    // 日期格式为yyyy-MM-dd HH:mm
                    date_picker.show(tv_image_date.getText().toString());
                    break;

                case R.id.bt_add_deter:
                    if(imagePath == null || imagePath.equals(""))
                    {
                        Toast.makeText(UpdateImageActivity.this, "请选择图片", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(tv_image_date.getText().toString() != null && !"".equals(tv_image_date.getText().toString())){
                        imageDate = getStamp(tv_image_date.getText().toString());
                    }

                    Log.i(TAG, "[imagePath]" + imagePath);
                    Log.i(TAG, "[imageDate]" + String.valueOf(imageDate));

                    ImageItem imageItem = new ImageItem();
                    imageItem.setImagePath(imagePath);
                    imageItem.setImageDate(imageDate);

                    m_application.updateImage(imageId, imageItem);

                    Log.i(TAG, "[Size]" + m_application.getImages().size());
                    Toast.makeText(UpdateImageActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    finish();

                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            showImage(imagePath);
            c.close();
        }
    }

    //加载图片
    private void showImage(String path){
        if(StringUtil.isNullOrEmpty(path)) return;
        imagePath = path;
        Glide.with(UpdateImageActivity.this)
                .load(Uri.fromFile(new File(path)))
                .placeholder(R.drawable.image_default)
                .crossFade()
                .into(iv_add_image);

        tv_add_image.setText(imagePath);
    }

    //-------------------------------------------------------------------------------

    //
    private String getDateFormat(long date)
    {
        if(date<=0) return "";

        String pattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINESE);
        return sdf.format(new Date(date));
    }

    private long getStamp(String date)
    {
        if(date==null || date.trim().length()==0) return 0;

        String pattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINESE);
        return sdf.parse(date, new ParsePosition(0)).getTime();
    }

    //-------------------------------------------------------------------------------
}
