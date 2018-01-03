package com.android.lv.imageswitcher.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.lv.imageswitcher.R;
import com.android.lv.imageswitcher.application.MyApplication;
import com.android.lv.imageswitcher.persist.DelayImageItem;
import com.android.lv.imageswitcher.persist.ImageItem;
import com.android.lv.imageswitcher.ui.activity.ShowActivity;
import com.android.lv.imageswitcher.ui.activity.ShowEveryDayActivity;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Administrator on 2017/12/3.
 */

public class HomeFragment extends Fragment {

    private MyApplication m_application;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate( R.layout.fragment_home, container, false);
        m_application = MyApplication.getApplication();
        show(rootView);
        return rootView;
    }

    private Button bt_image_open;
    private Button bt_image_every;
    private void show(View rootView){
        bt_image_open  = (Button) rootView.findViewById(R.id.bt_image_open);
        bt_image_every = (Button) rootView.findViewById(R.id.bt_image_every);

        bt_image_open.setOnClickListener(onClickListener);
        bt_image_every.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_image_open:
                    List<ImageItem> images = m_application.getImages();

                    if(images != null && images.size() > 0)
                    {
                        startActivity(new Intent(getActivity(), ShowActivity.class));
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "请先设置图片", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.bt_image_every:
                    List<DelayImageItem> erveryDayaImages = m_application.getDelayImages();

                    if(erveryDayaImages != null && erveryDayaImages.size() > 0)
                    {
                        startActivity(new Intent(getActivity(), ShowEveryDayActivity.class));
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "请先设置图片", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
}
