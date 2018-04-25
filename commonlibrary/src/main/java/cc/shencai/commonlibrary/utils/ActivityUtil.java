package cc.shencai.commonlibrary.utils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import cc.shencai.commonlibrary.R;

/**
 * activity 之间跳转的实现，包括了动画
 * Created by yss on 2017/8/14.
 */

public class ActivityUtil {

	/**
	 *带跳转动画的activity跳转
	 * @param activity
	 * @param className
	 */
	public static void gotoActivity(Activity activity, Class<?> className){
		activity.startActivity(new Intent(activity,className));
		activity.overridePendingTransition(R.anim.from_right_in, R.anim.to_left_out);
	}

	public static void gotoActivity(Activity activity, Intent intent){
		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.from_right_in, R.anim.to_left_out);
	}

	public static void gotoActivityForResult(Activity fromActivity, Class<?> toClass, int requestCode) {
		Intent intent = new Intent(fromActivity, toClass);
		fromActivity.startActivityForResult(intent, requestCode);
		fromActivity.overridePendingTransition(R.anim.from_right_in, R.anim.to_left_out);
	}

	public static void gotoActivityForResult(Activity fromActivity,Intent intent, int requestCode) {
		fromActivity.startActivityForResult(intent, requestCode);
		fromActivity.overridePendingTransition(R.anim.from_right_in, R.anim.to_left_out);
	}


	/**
	 * 带关闭动画的关闭页面
	 * @param activity
	 */
	public static void finishActivity(Activity activity){
		if (activity != null) {
			activity.finish();
			activity.overridePendingTransition(R.anim.from_left_in, R.anim.to_right_out);
		}

	}

	/**
	 * 判断是否存在指定Activity
	 *
	 * @param context     上下文
	 * @param packageName 包名
	 * @param className   activity全路径类名
	 * @return {@code true}: 是<br>{@code false}: 否
	 */
	public static boolean isExistActivity(Context context, String packageName, String className) {
		Intent intent = new Intent();
		intent.setClassName(packageName, className);
		return !(context.getPackageManager().resolveActivity(intent, 0) == null ||
				intent.resolveActivity(context.getPackageManager()) == null ||
				context.getPackageManager().queryIntentActivities(intent, 0).size() == 0);
	}
}


