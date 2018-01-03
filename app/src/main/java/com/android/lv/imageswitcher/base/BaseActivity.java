package com.android.lv.imageswitcher.base;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Window;

/**
 * 
 * File Description
 *
 * @author			LV
 * @version			0.1
 * @since           0.1
 * @.createdate     2015年7月8日 下午4:09:35
 * @.modifydate     2015年7月8日 下午4:09:35
 * <DT><B>修改历史记录</B>
 * <DD>
 * 
 * </DD>
 * </DT>
 */
public abstract class BaseActivity extends ViewRenderFragmentActivity
{
	// ---------------------------------------------------------------

		@Override
		public void prev(Bundle savedInstanceState)
		{
			requestWindowFeature(Window.FEATURE_NO_TITLE);  //无标题
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //禁止横屏
		}

		protected void init(int resid)
		{
			if(resid>0)
			{
				setContentView(resid);
			}

		}

		// ---------------------------------------------------------------

}
