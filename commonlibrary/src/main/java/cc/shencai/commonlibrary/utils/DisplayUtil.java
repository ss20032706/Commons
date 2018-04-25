package cc.shencai.commonlibrary.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;

/**
 * 常用单位转换的辅助类
 * Created by yss on 2017/8/14
 * @version 1.0.0
 */

public class DisplayUtil {
	public static int screenWidthPx; //屏幕宽 px
	public static int screenhightPx; //屏幕高 px
	public static float density;//屏幕密度
	public static int densityDPI;//屏幕密度
	public static float screenWidthDip;//  dp单位
	public static float screenHightDip;//  dp单位

	private DisplayUtil()
	{
        /* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * 初始化屏幕尺寸相关的参数
	 * @param context
	 */
	public static void initDisplayOpinion(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		density = dm.density;
		densityDPI = dm.densityDpi;
		screenWidthPx = dm.widthPixels;
		screenhightPx = dm.heightPixels;
		screenWidthDip = DisplayUtil.px2dp(context, dm.widthPixels);
		screenHightDip = DisplayUtil.px2dp(context, dm.heightPixels);
	}

	/**
	 * dp转px
	 *
	 * @param context
	 * @return
	 */
	public static int dp2px(Context context, float dpVal)
	{
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dpVal, context.getResources().getDisplayMetrics());
	}

	/**
	 * sp转px
	 *
	 * @param context
	 * @return
	 */
	public static int sp2px(Context context, float spVal)
	{
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				spVal, context.getResources().getDisplayMetrics());
	}

	/**
	 * px转dp
	 *
	 * @param context
	 * @param pxVal
	 * @return
	 */
	public static float px2dp(Context context, float pxVal)
	{
		final float scale = context.getResources().getDisplayMetrics().density;
		return (pxVal / scale);
	}

	/**
	 * px转sp
	 *
	 * @param pxVal
	 * @return
	 */
	public static float px2sp(Context context, float pxVal)
	{
		return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
	}

	/* height 单位是 px */
	public static void showDropView(Context context, View view, float height) {
		if (view.getVisibility() == View.VISIBLE) {
			return;
		}
		view.setVisibility(View.VISIBLE);
		ValueAnimator valueAnimator = dropAnim(view, 0, (int) height);
		valueAnimator.start();
	}

	public static void hideDropView(Context context, final View view, float height) {
		if (view.getVisibility() != View.VISIBLE) {
			return;
		}
		ValueAnimator valueAnimator = dropAnim(view, (int) height, 0);
		valueAnimator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
				view.setVisibility(View.GONE);
			}
		});

		valueAnimator.start();
	}

	private static ValueAnimator dropAnim(final View view, int start, int end) {
		ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
//		valueAnimator.setInterpolator(new BounceInterpolator());
		valueAnimator.setDuration(200);
		valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				int value = (int) animation.getAnimatedValue();
				ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
				layoutParams.height = value;
				view.setLayoutParams(layoutParams);
			}
		});
		return valueAnimator;
	}

	/**
	 * 圆形展开动画
	 * @param v
	 * @param centerX
	 * @param centerY
	 * @param startRadius
	 * @param endRadius
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public static Animator circularRevealAnim(View v, int centerX, int centerY, float startRadius, float endRadius) {
		return circularRevealAnim(v, centerX, centerY, startRadius, endRadius, 1500);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public static Animator circularRevealAnim(View v, int centerX,  int centerY, float startRadius, float endRadius, long animDuration) {
		Animator animator = ViewAnimationUtils.createCircularReveal(v,
				centerX, centerY, startRadius, endRadius);
		animator.setDuration(1500);
		animator.setInterpolator(new BounceInterpolator());
		animator.start();
		return animator;
	}

	/**
	 * 判断标题是否需要换行,若需要换行，则设置标题grativity靠左，否则默认居中
	 *
	 * @param textView
	 */
	public static void TvOverFlowed(final TextView textView) {
		ViewTreeObserver vto = textView.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				textView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				textView.getHeight();
				double w0 = textView.getWidth();//控件宽度
				double w1 = textView.getPaint().measureText(textView.getText().toString());//文本宽度
				if (w1 >= w0) {
					textView.setGravity(Gravity.START);
				}//需要换行就显示该控件
			}
		});
	}
}
