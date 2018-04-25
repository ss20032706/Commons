package cc.shencai.commonlibrary.utils;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 屏幕相关工具
 * Created by yss on 2017/8/15
 *
 * @version 1.0.0
 */
public class ScreenUtil {
	private ScreenUtil()
	{
        /* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * 获得屏幕宽度
	 *
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context)
	{
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

	/**
	 * 获得屏幕高度
	 *
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context)
	{
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}

	/**
	 * 获得状态栏的高度
	 *
	 * @param context
	 * @return
	 */
	public static int getStatusHeight(Context context)
	{

		int statusHeight = -1;
		try
		{
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height")
					.get(object).toString());
			statusHeight = context.getResources().getDimensionPixelSize(height);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return statusHeight;
	}

	/**
	 * 获取当前屏幕截图，包含状态栏
	 *
	 * @param activity
	 * @return
	 */
	public static Bitmap snapShotWithStatusBar(Activity activity)
	{
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
		view.destroyDrawingCache();
		return bp;

	}

	/**
	 * 获取当前屏幕截图，不包含状态栏
	 *
	 * @param activity
	 * @return
	 */
	public static Bitmap snapShotWithoutStatusBar(Activity activity)
	{
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;

		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
				- statusBarHeight);
		view.destroyDrawingCache();
		return bp;

	}

	/**
	 * 截图并保存到指定路径
	 * @param activity
	 * @param filePath
	 */
	public void screenshotShare(Activity activity , String filePath) {
		// 获取屏幕
		View dView = activity.getWindow().getDecorView();
		dView.setDrawingCacheEnabled(true);
		dView.buildDrawingCache();
		Bitmap bmp = dView.getDrawingCache();
		if (bmp != null) {
			// 获取内置SD卡路径
			File file = new File(filePath);
			if (file.isFile() && file.exists()) {
				file.delete();
				file = new File(filePath);
			}

			FileOutputStream os = null;
			try {
				os = new FileOutputStream(filePath);
				bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
				os.flush();
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 设置添加屏幕的背景透明度
	 *
	 * @param bgAlpha 屏幕透明度0.0-1.0 1表示完全不透明
	 */
	public void setBackgroundAlpha(Activity activity,float bgAlpha) {
		WindowManager.LayoutParams lp = activity.getWindow()
				.getAttributes();
		lp.alpha = bgAlpha;
		activity.getWindow().setAttributes(lp);
	}
}
