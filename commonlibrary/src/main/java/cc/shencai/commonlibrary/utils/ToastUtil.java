package cc.shencai.commonlibrary.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import cc.shencai.commonlibrary.R;


/**
 * Created by yss on 2017/8/14
 *
 * @version 1.0.0
 */
public class ToastUtil {
	private ToastUtil()
	{
        /* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	public static boolean isShow = true;

	/**
	 * 短时间显示Toast
	 *
	 * @param context
	 * @param message
	 */
	public static void showShort(Context context, CharSequence message)
	{
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 短时间显示Toast
	 *
	 * @param context
	 * @param message
	 */
	public static void showShort(Context context, int message)
	{
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 长时间显示Toast
	 *
	 * @param context
	 * @param message
	 */
	public static void showLong(Context context, CharSequence message)
	{
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	/**
	 * 长时间显示Toast
	 *
	 * @param context
	 * @param message
	 */
	public static void showLong(Context context, int message)
	{
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	/**
	 * 自定义显示Toast时间
	 *
	 * @param context
	 * @param message
	 * @param duration
	 */
	public static void show(Context context, CharSequence message, int duration)
	{
		if (isShow)
			Toast.makeText(context, message, duration).show();
	}

	/**
	 * 自定义显示Toast时间
	 *
	 * @param context
	 * @param message
	 * @param duration
	 */
	public static void show(Context context, int message, int duration)
	{
		if (isShow)
			Toast.makeText(context, message, duration).show();
	}

	/**
	 * 自定义的toast，显示在屏幕中间，圆边
	 * @param context
	 * @param message
	 * @param time
	 */
	public static void showToast(Context context, String message,int time) {
		if ( isShow ) {
			//加载Toast布局
			View toastRoot = LayoutInflater.from(context).inflate(R.layout.view_toast, null);
			//初始化布局控件
			TextView mTextView = (TextView) toastRoot.findViewById(R.id.tvToast);
			//为控件设置属性
			mTextView.setText(message);
			//Toast的初始化
			Toast toastStart = new Toast(context);
			//获取屏幕高度
			WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			int height = wm.getDefaultDisplay().getHeight();
			//Toast的Y坐标是屏幕高度的1/2，不会出现不适配的问题
			toastStart.setGravity(Gravity.TOP, 0, height/2);
			toastStart.setDuration(time);
			toastStart.setView(toastRoot);
			toastStart.show();
		}
	}

}
