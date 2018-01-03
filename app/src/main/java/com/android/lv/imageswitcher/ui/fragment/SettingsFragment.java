package com.android.lv.imageswitcher.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.lv.imageswitcher.R;
import com.android.lv.imageswitcher.application.MyApplication;
import com.android.lv.imageswitcher.persist.ImageItem;
import com.android.lv.imageswitcher.ui.activity.ShowActivity;
import com.android.lv.imageswitcher.util.StringUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/12/3.
 */

public class SettingsFragment extends Fragment {

    private static final String TAG = "SettingsFragment";

    private MyApplication m_application;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate( R.layout.fragment_settings, container, false);
        m_application = MyApplication.getApplication();
        find(rootView);
        bind();
        rend();
        return rootView;
    }

    //------------------------------------------------------------------------------
    private EditText et_set_hour;
    private EditText et_set_minute;
    private EditText et_set_second;
    private Button   bt_add_show;

    private void find(View rootView){
        et_set_hour   = (EditText) rootView.findViewById(R.id.et_set_hour);
        et_set_minute = (EditText) rootView.findViewById(R.id.et_set_minute);
        et_set_second = (EditText) rootView.findViewById(R.id.et_set_second);

        bt_add_show   = (Button) rootView.findViewById(R.id.bt_add_show);
    }

    private void bind(){
        bt_add_show.setOnClickListener(onClickListener);
    }

    private void rend(){
        Log.i(TAG,"[getSettingDate]");
        long m_period = m_application.getPeriod();
        Log.i(TAG,"[m_period]" + m_period);
        if(m_period > 0){
            long m_second = m_period % 60;
            long m_minute = m_period / 60 % 60;
            long m_hour   = m_period / (60*60) % 60;

            et_set_hour.setText(String.valueOf(m_hour));
            et_set_minute.setText(String.valueOf(m_minute));
            et_set_second.setText(String.valueOf(m_second));
        }
    }

    //------------------------------------------------------------------------------

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case  R.id.bt_add_show:
                    if(!isEditTextError(et_set_hour) || !isEditTextError(et_set_minute) || !isEditTextError(et_set_second)){
                        Toast.makeText(getActivity(), "时分秒 至少为 0", Toast.LENGTH_LONG).show();
                        return;
                    }

                    long hour   = 0;
                    long minute = 0;
                    long second = 0;

                    if(isEditTextError(et_set_hour)){

                        long m_hour = Long.valueOf(et_set_hour.getText().toString().trim());
                        if(m_hour > 60){
                            Toast.makeText(getActivity(), "时 超过60", Toast.LENGTH_LONG).show();
                            return;
                        }
                        else
                        {
                            hour = m_hour* 60 * 60;
                        }
                    }

                    if(isEditTextError(et_set_minute)){

                        long m_minute = Long.valueOf(et_set_minute.getText().toString().trim());
                        if(m_minute > 60){
                            Toast.makeText(getActivity(), "分 超过60", Toast.LENGTH_LONG).show();
                            return;
                        }
                        else
                        {
                            minute = m_minute* 60 ;
                        }

                    }

                    if(isEditTextError(et_set_second)){

                        long m_second = Long.valueOf(et_set_second.getText().toString().trim());
                        if(m_second > 60){
                            Toast.makeText(getActivity(), "秒 超过60", Toast.LENGTH_LONG).show();
                            return;
                        }
                        else
                        {
                            second = m_second;
                        }
                    }

                    long period = hour + minute + second;

                    if(period <= 1){
                        Toast.makeText(getActivity(), "设置时间太短", Toast.LENGTH_LONG).show();
                        return;
                    }

                    Toast.makeText(getActivity(), "设置成功", Toast.LENGTH_LONG).show();

                    List<ImageItem> images = m_application.getImages();

                    if(images != null && images.size() > 0){

                        m_application.setPeriod(period);
                        Log.i(TAG,"[setPeriod]" + period);

                        Intent intent = new Intent(getActivity(), ShowActivity.class);
                        intent.putExtra("modeType", 1);
                        intent.putExtra("period",period);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "请先设置图片", Toast.LENGTH_SHORT).show();
                    }

                    break;
            }
        }
    };

    private boolean isEditTextError(EditText editText){
        String str = editText.getText().toString().trim();
        if(str.length()==0 || null == str || "".equals(str)){
            return false;
        }
        return true;
    }
}
