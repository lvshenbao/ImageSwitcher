package com.android.lv.imageswitcher.base;

import java.util.Stack;
import android.app.Activity;
import android.os.Handler;
/**
 * 
 * File Description
 *
 * @author			LV
 * @version			0.1
 * @since           0.1
 * @.createdate     2015年7月8日 下午2:49:46
 * @.modifydate     2015年7月8日 下午2:49:46
 * <DT><B>修改历史记录</B>
 * <DD>
 * 
 * </DD>
 * </DT>
 */
public class ViewStack
{
	
	//-------------------------------------------------------------------------------
	
	private static ViewStack instance;
	private ViewStack()
	{
	}
	public static ViewStack instance()
	{
		if(instance==null)
		{
			instance = new ViewStack();
		}
		return instance;
	}
	
	//-------------------------------------------------------------------------------
	
	private Stack<Activity> activityStack;
	
	// 将当前Activity推入栈中
	public void pushActivity(Activity activity) 
	{
		if (activityStack == null) 
		{
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}
	
	// 退出栈顶Activity
	public void popActivity(Activity activity) 
	{
		activityStack.remove(activity);
		activity = null;
	}
	public void finishActivity(Activity activity) 
	{
		activity.finish();
		activityStack.remove(activity);
		activity = null;
	}
	
	//-------------------------------------------------------------------------------
	
	// 获得当前栈顶Activity
	public Activity currentActivity()
	{
		Activity activity = activityStack.lastElement();
		return activity;
	}
	
	// 退出栈中所有Activity
	public void popAllActivity()
	{
		if (activityStack != null)
		{
			activityStack.clear();
		}
	}
	
	//结束所有Activity
	public void finishAllActivity()
	{
		Activity activity = null;
		try
		{
			while (!activityStack.isEmpty())
			{
				activity = currentActivity();
				if (activity != null)
				{
					finishActivity(activity);
				}
			}
		}
		finally
		{
			activity = null;
		}
	}
	
	public static void closeApplication()
	{
		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				ViewStack.instance().finishAllActivity();
				// System.exit(0);
			}
		}, 0);
	}
	
	//-------------------------------------------------------------------------------

}
