package cc.shencai.commonlibrary.widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 禁止触屏左右滑动的viewpager
 * Created by yss on 2017/8/15
 *
 * @version 1.0.0
 */
public class ForbidSlideViewPager extends ViewPager {
	private boolean isCanScroll = false;

	public ForbidSlideViewPager(Context context) {
		super(context);
	}

	public ForbidSlideViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setScanScroll(boolean isCanScroll) {
		this.isCanScroll = isCanScroll;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if (isCanScroll) {
			return super.onInterceptTouchEvent(arg0);
		} else {
			return false;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		if (isCanScroll) {
			return super.onTouchEvent(arg0);
		} else {
			return false;
		}
	}
}