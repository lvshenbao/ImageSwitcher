package com.android.lv.imageswitcher.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.lv.imageswitcher.R;
import com.android.lv.imageswitcher.application.MyApplication;
import com.android.lv.imageswitcher.persist.DelayImageItem;
import com.android.lv.imageswitcher.ui.activity.AddEveryDayImageActivity;
import com.android.lv.imageswitcher.ui.activity.ShowActivity;
import com.android.lv.imageswitcher.ui.activity.ShowEveryDayActivity;
import com.android.lv.imageswitcher.ui.activity.UpdateEveryDayImageActivity;
import com.android.lv.imageswitcher.ui.activity.UpdateImageActivity;
import com.android.lv.imageswitcher.util.DateUtil;
import com.android.lv.imageswitcher.util.LvAdapter;
import com.android.lv.imageswitcher.util.LvViewHolder;
import com.bumptech.glide.Glide;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2017/12/13.
 */

public class EverydayFragment extends Fragment {

    private static final String TAG = "EverydayFragment";

    //---------------------------------------------------------------------------
    private MyApplication m_application;

    private LvAdapter<DelayImageItem> m_adapter;
    private ArrayList<DelayImageItem> m_list;

    //---------------------------------------------------------------------------

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate( R.layout.fragment_calendar, container, false);

        m_application = MyApplication.getApplication();
        find(rootView);
        bind();
        rend();
        return rootView;
    }

    //---------------------------------------------------------------------------

    private ListView lv_calendar;
    private TextView tv_add;
    private TextView tv_show;

    //---------------------------------------------------------------------------

    private void find(View rootView){
        lv_calendar = (ListView) rootView.findViewById(R.id.lv_calendar);
        tv_add      = (TextView) rootView.findViewById(R.id.tv_add);
        tv_show     = (TextView) rootView.findViewById(R.id.tv_show);
    }

    private void bind(){
        tv_add.setOnClickListener(onClickListener);
        tv_show.setOnClickListener(onClickListener);

        lv_calendar.setOnItemClickListener(onItemClickListener);
        lv_calendar.setOnItemLongClickListener(onItemLongClickListener);
    }

    private void rend(){

        m_list = new ArrayList<>();

        m_adapter = new LvAdapter<DelayImageItem>(getActivity(), m_list, R.layout.item_image) {
            @Override
            public void convert(LvViewHolder holder, DelayImageItem item) {

                ImageView imageView = holder.getView(R.id.iv_item_image);

                Glide.with(getActivity())
                        .load(Uri.fromFile(new File(item.getImagePath())))
                        .placeholder(R.drawable.image_default)
                        .crossFade()
                        .into(imageView);

                holder.setText(R.id.iv_item_path, item.getImagePath());
                holder.setText(R.id.iv_item_date, DateUtil.formatDateMM(item.getImageDate()));
            }
        };

        lv_calendar.setAdapter(m_adapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        List<DelayImageItem> images = m_application.getDelayImages();
        m_adapter.replaceAll(images);
        m_adapter.notifyDataSetChanged();
    }

    //---------------------------------------------------------------------------

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_add:
                    Intent image_intent = new Intent(getActivity(),  AddEveryDayImageActivity.class);
                    startActivity(image_intent);
                    break;

                case R.id.tv_show:
                    Intent intent = new Intent(getActivity(), ShowEveryDayActivity.class);
                    startActivity(intent);
                    break;

                default:
                    break;
            }
        }
    };

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getActivity(), "ID:" + m_adapter.getItem(position).getId(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), UpdateEveryDayImageActivity.class);
            intent.putExtra("imageId", m_adapter.getItem(position).getId());
            startActivity(intent);
        }
    };

    private AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

            Toast.makeText(getActivity(), "ID:" + m_adapter.getItem(position).getId(), Toast.LENGTH_SHORT).show();

            AlertDialog.Builder normalDialog =
                    new AlertDialog.Builder(getActivity());
            normalDialog.setTitle("确定删除吗");
            normalDialog.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            m_application.deleteDelayImage(m_adapter.getItem(position).getId());
                            List<DelayImageItem> images = m_application.getDelayImages();
                            m_adapter.replaceAll(images);
                            m_adapter.notifyDataSetChanged();
                        }
                    });
            normalDialog.setNegativeButton("取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            // 显示
            normalDialog.show();


            return true;
        }
    };

    //---------------------------------------------------------------------------

    private String getDateFormat(long date)
    {
        if(date<=0) return "";

        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINESE);
        return sdf.format(new Date(date));
    }
}
