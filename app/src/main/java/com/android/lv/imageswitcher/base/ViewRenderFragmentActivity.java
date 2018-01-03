package com.android.lv.imageswitcher.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * 
 * File Description
 *
 * @author			LV
 * @version			0.1
 * @since           0.1
 * @.createdate     2015年7月8日 下午2:54:07
 * @.modifydate     2015年7月8日 下午2:54:07
 * <DT><B>修改历史记录</B>
 * <DD>
 * 
 * </DD>
 * </DT>
 */
public abstract class ViewRenderFragmentActivity extends FragmentActivity implements ViewRender
{
	//-------------------------------------------------------------------------------

		@Override
		public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			create(savedInstanceState);
		}
		
		@Override
		public void finish() 
		{
			super.finish();
		}
		
		@Override
		public void onDestroy()
		{
			super.onDestroy();
			destroy();
		}
		
		//-------------------------------------------------------------------------------
		
		@Override
		public void create(Bundle savedInstanceState)
		{
			prev(savedInstanceState);
			find();
			bind();
			rend();
			post();
		}
		
		//-------------------------------------------------------------------------------

}
