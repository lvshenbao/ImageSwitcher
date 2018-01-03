package com.android.lv.imageswitcher.base;

import android.os.Bundle;

/**
 * 
 * File Description
 *
 * @author			LV
 * @version			0.1
 * @since           0.1
 * @.createdate     2015年7月8日 下午2:52:15
 * @.modifydate     2015年7月8日 下午2:52:15
 * <DT><B>修改历史记录</B>
 * <DD>
 * 
 * </DD>
 * </DT>
 */
public interface ViewRender
{
	//创建及销毁
		void create(Bundle savedInstanceState);
		void destroy();
		
		/**
		 * 预处理
		 */
		void prev(Bundle savedInstanceState);
		
		/**
		 * 定位元素
		 */
		void find();
		
		/**
		 * 绑定事件
		 */
		void bind();
		
		/**
		 * 生成界面
		 */
		void rend();
		
		/**
		 * 后处理
		 */
		void post();
}
