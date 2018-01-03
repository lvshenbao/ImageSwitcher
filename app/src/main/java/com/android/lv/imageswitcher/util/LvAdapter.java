package com.android.lv.imageswitcher.util;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/1.
 */

public abstract class LvAdapter<T> extends BaseAdapter
{

    //------------------------------------------------------------------------------------

    protected List<T> list;
    protected Context context;
    protected final int 		layoutid;

    public LvAdapter(Context _context, List<T> _list, int _itemlayoutresid)
    {
        this.context = _context;
        this.layoutid = _itemlayoutresid;
        this.list = (_list==null)?new ArrayList<T>():_list;
    }

    //------------------------------------------------------------------------------------

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public T getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final LvViewHolder viewHolder = getViewHolder(position, convertView, parent);
        convert(viewHolder, getItem(position));
        return viewHolder.getConvertView();
    }

    public abstract void convert(LvViewHolder holder, T item);

    private LvViewHolder getViewHolder(int position, View convertView, ViewGroup parent)
    {
        return LvViewHolder.get(context, convertView, parent, layoutid, position);
    }

    //------------------------------------------------------------------------------------

    public boolean contains(T item)
    {
        return list.contains(item);
    }

    public void add(T item)
    {
        list.add(item);
    }
    public void add(int index, T item)
    {
        list.add(index, item);
    }
    public void remove(T item)
    {
        list.remove(item);
    }
    public void remove(int index)
    {
        list.remove(index);
    }

    public void set(T oitem, T nitem)
    {
        set(list.indexOf(oitem), nitem);
    }
    public void set(int index, T item)
    {
        list.set(index, item);
    }

    public void addAll(List<T> _list)
    {
        list.addAll(_list);
    }
    public void replaceAll(List<T> _list)
    {
        list.clear();
        addAll(_list);
    }

    public void clear()
    {
        list.clear();
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer)
    {
        if(observer != null)
        {
            super.unregisterDataSetObserver(observer);
        }
    }

    //------------------------------------------------------------------------------------

}