package cc.shencai.commonlibrary.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.ColorMatrixColorFilter;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import cc.shencai.commonlibrary.R;
import cc.shencai.commonlibrary.interfaces.OnUpdateListener;

import static cc.shencai.commonlibrary.utils.ImageUtil.invisToVis;
import static cc.shencai.commonlibrary.utils.ImageUtil.visToInvis;


/** 各种动画效果
 * Created by yss on 2017/8/14.
 */

public class AnimationUtil {

	private AnimationUtil(){
        /* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * 得到从上往下滑入，从下往上滑出的动画
	 * @return
	 */
	public static int getTopToBottom(){
		return R.style.up_to_down_anim_style;
	}

	/**
	 * 得到从左往右滑入，从右往左滑出的动画
	 * @return
	 */
	public static int getLeftToRight(){
		return R.style.left_to_right_anim_style;
	}

	/**
	 * 得到从右往左滑入，从左往右滑出
	 * @return
	 */
	public static int getRightToLeft(){
		return R.style.right_to_left_anim_style;
	}

	/**
	 * 得到从下往上滑入，从上往下滑出
	 * @return
	 */
	public static int getBottomToTop(){
		return R.style.mypopwindow_bottom_anim_style;
	}


	/**
	 * 给视图添加点击效果,让背景变深
	 * */
	public static void addTouchDrak(View view, boolean isClick) {
		view.setOnTouchListener(VIEW_TOUCH_DARK);

		if (!isClick) {
			view.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
				}
			});
		}
	}

	/**
	 * 给视图添加点击效果,让背景变暗
	 * */
	public static void addTouchLight(View view, boolean isClick) {
		view.setOnTouchListener(VIEW_TOUCH_LIGHT);

		if (!isClick) {
			view.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
				}
			});
		}
	}

	/**
	 * 让控件点击时，颜色变深
	 * */
	public static final View.OnTouchListener VIEW_TOUCH_DARK = new View.OnTouchListener() {

		public final float[] BT_SELECTED = new float[] { 1, 0, 0, 0, -50, 0, 1,
				0, 0, -50, 0, 0, 1, 0, -50, 0, 0, 0, 1, 0 };
		public final float[] BT_NOT_SELECTED = new float[] { 1, 0, 0, 0, 0, 0,
				1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 };

		@SuppressWarnings("deprecation")
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				if (v instanceof ImageView) {
					ImageView iv = (ImageView) v;
					iv.setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));
				} else {
					v.getBackground().setColorFilter(
							new ColorMatrixColorFilter(BT_SELECTED));
					v.setBackgroundDrawable(v.getBackground());
				}
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				if (v instanceof ImageView) {
					ImageView iv = (ImageView) v;
					iv.setColorFilter(new ColorMatrixColorFilter(
							BT_NOT_SELECTED));
				} else {
					v.getBackground().setColorFilter(
							new ColorMatrixColorFilter(BT_NOT_SELECTED));
					v.setBackgroundDrawable(v.getBackground());
				}
			}
			return false;
		}
	};

	/**
	 * 让控件点击时，颜色变暗
	 * */
	public static final View.OnTouchListener VIEW_TOUCH_LIGHT = new View.OnTouchListener() {

		public final float[] BT_SELECTED = new float[] { 1, 0, 0, 0, 50, 0, 1,
				0, 0, 50, 0, 0, 1, 0, 50, 0, 0, 0, 1, 0 };
		public final float[] BT_NOT_SELECTED = new float[] { 1, 0, 0, 0, 0, 0,
				1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 };

		@SuppressWarnings("deprecation")
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				if (v instanceof ImageView) {
					ImageView iv = (ImageView) v;
					iv.setDrawingCacheEnabled(true);

					iv.setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));
				} else {
					v.getBackground().setColorFilter(
							new ColorMatrixColorFilter(BT_SELECTED));
					v.setBackgroundDrawable(v.getBackground());
				}
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				if (v instanceof ImageView) {
					ImageView iv = (ImageView) v;
					iv.setColorFilter(new ColorMatrixColorFilter(
							BT_NOT_SELECTED));
				} else {
					v.getBackground().setColorFilter(
							new ColorMatrixColorFilter(BT_NOT_SELECTED));
					v.setBackgroundDrawable(v.getBackground());
				}
			}
			return false;
		}
	};

	/**
	 * 颜色渐变动画
	 *
	 * @param beforeColor 变化之前的颜色
	 * @param afterColor  变化之后的颜色
	 * @param listener    变化事件
	 */
	public static void animationColorGradient(int beforeColor, int afterColor, final OnUpdateListener listener) {
		ValueAnimator valueAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), beforeColor, afterColor).setDuration(3000);
		valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
//                textView.setTextColor((Integer) animation.getAnimatedValue());
				listener.onUpdate((Integer) animation.getAnimatedValue());
			}
		});
		valueAnimator.start();
	}

	/**
	 * 卡片翻转动画
	 *
	 * @param beforeView
	 * @param AfterView
	 */
	public static void cardFilpAnimation(final View beforeView, final View AfterView) {
		Interpolator accelerator = new AccelerateInterpolator();
		Interpolator decelerator = new DecelerateInterpolator();
		if (beforeView.getVisibility() == View.GONE) {
			// 局部layout可达到字体翻转 背景不翻转
			invisToVis = ObjectAnimator.ofFloat(beforeView,
					"rotationY", -90f, 0f);
			visToInvis = ObjectAnimator.ofFloat(AfterView,
					"rotationY", 0f, 90f);
		} else if (AfterView.getVisibility() == View.GONE) {
			invisToVis = ObjectAnimator.ofFloat(AfterView,
					"rotationY", -90f, 0f);
			visToInvis = ObjectAnimator.ofFloat(beforeView,
					"rotationY", 0f, 90f);
		}

		visToInvis.setDuration(250);// 翻转速度
		visToInvis.setInterpolator(accelerator);// 在动画开始的地方速率改变比较慢，然后开始加速
		invisToVis.setDuration(250);
		invisToVis.setInterpolator(decelerator);
		visToInvis.addListener(new Animator.AnimatorListener() {

			@Override
			public void onAnimationEnd(Animator arg0) {
				if (beforeView.getVisibility() == View.GONE) {
					AfterView.setVisibility(View.GONE);
					invisToVis.start();
					beforeView.setVisibility(View.VISIBLE);
				} else {
					AfterView.setVisibility(View.GONE);
					visToInvis.start();
					beforeView.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onAnimationCancel(Animator arg0) {

			}

			@Override
			public void onAnimationRepeat(Animator arg0) {

			}

			@Override
			public void onAnimationStart(Animator arg0) {

			}
		});
		visToInvis.start();
	}

	/**
	 * 缩小动画
	 *
	 * @param view
	 */
	public static void zoomIn(final View view, float scale, float dist) {
		view.setPivotY(view.getHeight());
		view.setPivotX(view.getWidth() / 2);
		AnimatorSet mAnimatorSet = new AnimatorSet();
		ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, scale);
		ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, scale);
		ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", 0.0f, -dist);

		mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX);
		mAnimatorSet.play(mAnimatorScaleX).with(mAnimatorScaleY);
		mAnimatorSet.setDuration(300);
		mAnimatorSet.start();
	}

	/**
	 * 放大动画
	 *
	 * @param view
	 */
	public static void zoomOut(final View view, float scale) {
		view.setPivotY(view.getHeight());
		view.setPivotX(view.getWidth() / 2);
		AnimatorSet mAnimatorSet = new AnimatorSet();

		ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", scale, 1.0f);
		ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", scale, 1.0f);
		ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", view.getTranslationY(), 0);

		mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX);
		mAnimatorSet.play(mAnimatorScaleX).with(mAnimatorScaleY);
		mAnimatorSet.setDuration(300);
		mAnimatorSet.start();
	}

	public static void ScaleUpDowm(View view) {
		ScaleAnimation animation = new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f);
		animation.setRepeatCount(-1);
		animation.setRepeatMode(Animation.RESTART);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(1200);
		view.startAnimation(animation);
	}

	public static void animateHeight(int start, int end, final View view) {
		ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
		valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				int value = (int) animation.getAnimatedValue();//根据时间因子的变化系数进行设置高度
				ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
				layoutParams.height = value;
				view.setLayoutParams(layoutParams);//设置高度
			}
		});
		valueAnimator.start();
	}

	public static ObjectAnimator popup(final View view, final long duration) {
		view.setAlpha(0);
		view.setVisibility(View.VISIBLE);

		ObjectAnimator popup = ObjectAnimator.ofPropertyValuesHolder(view,
				PropertyValuesHolder.ofFloat("alpha", 0f, 1f),
				PropertyValuesHolder.ofFloat("scaleX", 0f, 1f),
				PropertyValuesHolder.ofFloat("scaleY", 0f, 1f));
		popup.setDuration(duration);
		popup.setInterpolator(new OvershootInterpolator());

		return popup;
	}

	public static ObjectAnimator popout(final View view, final long duration, final AnimatorListenerAdapter animatorListenerAdapter) {
		ObjectAnimator popout = ObjectAnimator.ofPropertyValuesHolder(view,
				PropertyValuesHolder.ofFloat("alpha", 1f, 0f),
				PropertyValuesHolder.ofFloat("scaleX", 1f, 0f),
				PropertyValuesHolder.ofFloat("scaleY", 1f, 0f));
		popout.setDuration(duration);
		popout.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
				view.setVisibility(View.GONE);
				if (animatorListenerAdapter != null) {
					animatorListenerAdapter.onAnimationEnd(animation);
				}
			}
		});
		popout.setInterpolator(new AnticipateOvershootInterpolator());

		return popout;
	}
}
