package com.android.lv.imageswitcher.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

/**
 * Created by Administrator on 2017/12/1.
 */

public class LvViewHolder
{

    //------------------------------------------------------------------------------------

    private final SparseArray<View> views;
    private View convertview;
    private Context context;
    private int position;
    private int layoutid;

    private LvViewHolder(Context _context, ViewGroup _parent, int _layoutid, int _position)
    {
        this.context 	= _context;
        this.layoutid 	= _layoutid;
        this.position 	= _position;
        this.views 		= new SparseArray<>();

        convertview = LayoutInflater.from(_context).inflate(_layoutid, _parent, false);
        convertview.setTag(this);
    }

    //------------------------------------------------------------------------------------

    public static LvViewHolder get(Context _context, View _convertview, ViewGroup _parent, int _layoutid, int _position)
    {
        if (_convertview == null)
        {
            return new LvViewHolder(_context, _parent, _layoutid, _position);
        }
        else
        {
            LvViewHolder holder = (LvViewHolder) _convertview.getTag();
            holder.position		 = _position;
            return holder;
        }
    }

    public View getConvertView()
    {
        return convertview;
    }

    public Context getContext()
    {
        return context;
    }

    public int getPosition()
    {
        return position;
    }

    public int getLayoutId()
    {
        return layoutid;
    }

    //------------------------------------------------------------------------------------

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId)
    {
        View view = views.get(viewId);
        if (view == null)
        {
            view = convertview.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    //------------------------------------------------------------------------------------

    public LvViewHolder setTag(int viewId, Object tag)
    {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public LvViewHolder setTag(int viewId, int key, Object tag)
    {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public LvViewHolder setVisible(int viewId, boolean visible)
    {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public LvViewHolder setBackgroundColor(int viewId, int color)
    {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public LvViewHolder setBackgroundResource(int viewId, int resId)
    {
        View view = getView(viewId);
        view.setBackgroundResource(resId);
        return this;
    }

    public LvViewHolder setText(int viewId, String text)
    {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    public LvViewHolder setTextColor(int viewId, int color)
    {
        TextView view = getView(viewId);
        view.setTextColor(color);
        return this;
    }

    public LvViewHolder setTypeface(int viewId, Typeface typeface)
    {
        TextView view = getView(viewId);
        view.setTypeface(typeface);
        view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        return this;
    }

    public LvViewHolder setChecked(int viewId, boolean checked)
    {
        CheckBox view = getView(viewId);
        view.setChecked(checked);
        return this;
    }

    public LvViewHolder setPressed(int viewId, boolean pressed)
    {
        View view = getView(viewId);
        view.setPressed(pressed);
        return this;
    }

    public LvViewHolder setImageBitmap(int viewId, Bitmap bm)
    {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    //----------------------------------------------------------------------------------------------

    public <T> LvViewHolder setImageResource(int viewId, T data)
    {
        glide_to_view(viewId, Glide.with(context), data, 0, 0);
        return this;
    }

    public <T> LvViewHolder setImageResource(int viewId, T data, int errresid)
    {
        glide_to_view(viewId, Glide.with(context), data, errresid, 0);
        return this;
    }

    public <T> LvViewHolder setImageResource(int viewId, T data, int errresid, int phresid)
    {
        glide_to_view(viewId, Glide.with(context), data, errresid, phresid);
        return this;
    }

    public <T> LvViewHolder setImageResource(Activity activity, int viewId, T data)
    {
        glide_to_view(viewId, Glide.with(activity), data, 0, 0);
        return this;
    }

    public <T> LvViewHolder setImageResource(Activity activity, int viewId, T data, int errresid)
    {
        glide_to_view(viewId, Glide.with(activity), data, errresid, 0);
        return this;
    }

    public <T> LvViewHolder setImageResource(Activity activity, int viewId, T data, int errresid, int phresid)
    {
        glide_to_view(viewId, Glide.with(activity), data, errresid, phresid);
        return this;
    }

    public <T> LvViewHolder setImageResource(FragmentActivity activity, int viewId, T data)
    {
        glide_to_view(viewId, Glide.with(activity), data, 0, 0);
        return this;
    }

    public <T> LvViewHolder setImageResource(FragmentActivity activity, int viewId, T data, int errresid)
    {
        glide_to_view(viewId, Glide.with(activity), data, errresid, 0);
        return this;
    }

    public <T> LvViewHolder setImageResource(FragmentActivity activity, int viewId, T data, int errresid, int phresid)
    {
        glide_to_view(viewId, Glide.with(activity), data, errresid, phresid);
        return this;
    }

    public <T> LvViewHolder setImageResource(Fragment fragment, int viewId, T data)
    {
        glide_to_view(viewId, Glide.with(fragment), data, 0, 0);
        return this;
    }

    public <T> LvViewHolder setImageResource(Fragment fragment, int viewId, T data, int errresid)
    {
        glide_to_view(viewId, Glide.with(fragment), data, errresid, 0);
        return this;
    }

    public <T> LvViewHolder setImageResource(Fragment fragment, int viewId, T data, int errresid, int phresid)
    {
        glide_to_view(viewId, Glide.with(fragment), data, errresid, phresid);
        return this;
    }

    //  Uri uri, byte[] data, File file, int resid, String url

    //----------------------------------------------------------------------------------------------

    private <T> void glide_to_view(int viewId, RequestManager manager, T data, int errresid, int phresid)
    {
        DrawableRequestBuilder<T> builder = manager.load(data).dontAnimate();

        if(errresid>0) builder = builder.error(errresid);
        if(phresid>0) builder = builder.placeholder(phresid);
        setImageResource(viewId, builder);
    }

    public <T> LvViewHolder setImageResource(int viewId, DrawableRequestBuilder<T> builder)
    {
        ImageView view = getView(viewId);
        builder.into(view);
        return this;
    }

    //----------------------------------------------------------------------------------------------

	/*
	public ZuvViewHolder setImageResource(int viewId, String uri, DisplayImageOptions options, ImageLoadingListener listener)
	{
		ImageView view = getView(viewId);
		ImageLoader.getInstance().displayImage(uri, view, options, listener);
		return this;
	}
	*/

    public LvViewHolder setListViewAdapter(int viewId, BaseAdapter adapter)
    {
        ListView view = getView(viewId);
        view.setAdapter(adapter);
        return this;
    }

    public LvViewHolder setGridViewAdapter(int viewId, BaseAdapter adapter)
    {
        GridView view = getView(viewId);
        view.setAdapter(adapter);
        return this;
    }

    public LvViewHolder setViewOnItemClickListener(int viewId, AdapterView.OnItemClickListener listener)
    {
        AbsListView view = getView(viewId);
        view.setOnItemClickListener(listener);
        return this;
    }

    //------------------------------------------------------------------------------------

    public LvViewHolder setOnClickListener(int viewId, View.OnClickListener listener)
    {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public LvViewHolder setOnCheckedListener(int viewId, CompoundButton.OnCheckedChangeListener oncheckedlistener)
    {
        CheckBox view = getView(viewId);
        view.setOnCheckedChangeListener(oncheckedlistener);
        return this;
    }

    public LvViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener)
    {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public LvViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener)
    {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    //------------------------------------------------------------------------------------

}

