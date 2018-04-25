package cc.shencai.commonlibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by yss on 2017/9/8
 *
 * @version 1.0.0
 */
public class StatusBarUtils {

	private static int statusHeight = -1;//statusbar的高度

	public static int getStatusHeight() {
		return statusHeight;
	}


	/**
	 * 设置状态栏为渐变色
	 *
	 * @param activity
	 */
	public static void setShadowStatusBar(Activity activity) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
			localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
		}
	}

	/**
	 * 隐藏系统界面控件，推荐使用推荐使用推荐使用推荐使用推荐使用推荐使用推荐使用推荐使用推荐使用推荐使用
	 *
	 * @param activity
	 * @return
	 */
	public static boolean hideStatusBar(Activity activity) {
		activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
		if (Build.VERSION.SDK_INT >= 21) {
			View decorView = activity.getWindow().getDecorView();
			int option =
//					View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION//这是控制底部虚拟按键的bar
					View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
							| View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
			decorView.setSystemUiVisibility(option);
//			activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);//这是控制底部虚拟按键的bar
			activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
			return true;
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < 21) {
			WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
			localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
		}
		return false;
	}

	/**
	 * 初始化浅色状态栏相关，
	 * PS: 设置全屏需要在调用super.onCreate(arg0);之前设置setIsFullScreen(true);否则在Android 6.0下非全屏的activity会出错;
	 * SDK19：可以设置状态栏透明，但是半透明的SYSTEM_BAR_BACKGROUNDS会不好看；
	 * SDK21：可以设置状态栏颜色，并且可以清除SYSTEM_BAR_BACKGROUNDS，但是不能设置状态栏字体颜色（默认的白色字体在浅色背景下看不清楚）；
	 * SDK23：可以设置状态栏为浅色（SYSTEM_UI_FLAG_LIGHT_STATUS_BAR），字体就回反转为黑色。
	 * 为兼容目前效果，仅在SDK23才显示沉浸式。
	 */
	public static void initLightThemeStatusBar(Activity activity) {
		Window win = activity.getWindow();
		//KITKAT也能满足，只是SYSTEM_UI_FLAG_LIGHT_STATUS_BAR（状态栏字体颜色反转）只有在6.0才有效
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			win.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
			// 状态栏字体设置为深色，SYSTEM_UI_FLAG_LIGHT_STATUS_BAR 为SDK23增加
			win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
			// 部分机型的statusbar会有半透明的黑色背景
			win.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			win.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			win.setStatusBarColor(Color.TRANSPARENT);// SDK21
		} else if (Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
			win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
			View decorView = win.getDecorView();
			int option =
//					View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION//这是控制底部虚拟按键的bar
					View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
							| View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
			decorView.setSystemUiVisibility(option);
//			activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);//这是控制底部虚拟按键的bar
			win.setStatusBarColor(Color.TRANSPARENT);
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < 21) {
			WindowManager.LayoutParams localLayoutParams = win.getAttributes();
			localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
		}
	}

	/**
	 * 获取系统状态栏高度
	 *
	 * @param context
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			statusHeight = context.getResources().getDimensionPixelSize(resourceId);
		}
		return statusHeight;
	}

	/**
	 * 设置statusbar的颜色
	 *
	 * @param activity
	 * @param color
	 */
	public static void setStatusBarColor(Activity activity, int color) {
		if (Build.VERSION.SDK_INT >= 21) {
			//这个只能修改背景颜色
			activity.getWindow().setStatusBarColor(color);
		}
	}

	/**
	 * 获得状态栏的高度
	 *
	 * @param context
	 * @return
	 */
	public static int getStatusHeight(Context context) {

		try {
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height")
					.get(object).toString());
			statusHeight = context.getResources().getDimensionPixelSize(height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusHeight;
	}

	/**
	 * 动态设置自定义状态栏控件的高度
	 *
	 * @param context
	 * @return
	 */
	public static void setMyStatusBarHeight(Context context, View view) {
		//设置布局参数
		ViewGroup.LayoutParams params = view.getLayoutParams();
		if (-1 == statusHeight) {
			params.height = getStatusHeight(context);
		} else {
			params.height = statusHeight;
		}

		view.setLayoutParams(params);
	}

}
