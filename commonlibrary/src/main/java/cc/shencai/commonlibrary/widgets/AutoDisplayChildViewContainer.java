package cc.shencai.commonlibrary.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import cc.shencai.commonlibrary.utils.DisplayUtil;

/**
 * 自动分配子view的位置, 具有自动换行,水平居中的功能
 * Created by yss on 2017/8/15
 *
 * @version 1.0.0
 */
public class AutoDisplayChildViewContainer extends ViewGroup {
	private int parentWidth;
	private int totaleft = 0;
	private int totalTop = 0;
	//	private int margin = 20;
	private int marginVertical = 20;
	private int marginHorizontal = 20;
	private int maxChildHeight = 0;
	private int totalRight = 0;

	public AutoDisplayChildViewContainer(Context context) {
		super(context);
	}

	public AutoDisplayChildViewContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AutoDisplayChildViewContainer(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        /* 测量父布局 */
		parentWidth = measureSize(widthMeasureSpec, DisplayUtil.dp2px(getContext(), 240));

		int count = getChildCount();
		int tempMaxChildHeight = 0;
		int tempTotalHeight = 0;
		int tempTotalRight = 0;
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			if (child.getVisibility() != View.GONE) {

                /* 测量子布局 */
				if (child.getMeasuredWidth() > parentWidth) {
					child.measure(parentWidth, child.getMeasuredHeight() );
				} else {
					child.measure(child.getMeasuredWidth() , child.getMeasuredHeight() );
				}
				tempMaxChildHeight = Math.max(tempMaxChildHeight, child.getMeasuredHeight() + marginVertical);
				tempTotalRight += child.getMeasuredWidth() + marginHorizontal;
				if (tempTotalRight > parentWidth) {//要换行
					tempTotalHeight += tempMaxChildHeight + marginVertical;//总高度加上这一子行的高度
					tempMaxChildHeight = child.getMeasuredHeight() + marginVertical;
					tempTotalRight = child.getMeasuredWidth()+ marginHorizontal;
				}
			}
		}

        /* 获取适配子布局后的高度 */
		int parentHeight = tempTotalHeight + tempMaxChildHeight ;
		setMeasuredDimension(parentWidth, parentHeight);
	}

	private int measureSize(int measureSpec, int defaultSize) {
		int mode = MeasureSpec.getMode(measureSpec);
		int size = MeasureSpec.getSize(measureSpec);
		int result = defaultSize;

		if (mode == MeasureSpec.EXACTLY) {
			result = size;
		} else if (mode == MeasureSpec.AT_MOST) {
			result = Math.max(size, result);
		}

		return result;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		int count = getChildCount();
		int lineViewCount = 0;

		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			if (child.getVisibility() != View.GONE) {
				if (i != 0) {
                    /* child 的 left 是上个子 view 的宽加上 margin */
					totaleft = totaleft + getChildAt(i - 1).getMeasuredWidth() + marginHorizontal;
				} else {
					totaleft = marginHorizontal;
					totalTop = 0;
					maxChildHeight = child.getMeasuredHeight() + marginVertical;
				}
                /* child 的 right */
				totalRight = totaleft + child.getMeasuredWidth() + marginHorizontal;
                /* 如果 rigth 大于 父布局的宽， 则换行 */
				if (totalRight > parentWidth) {//此时肯定是i不为0的情况,分行的情况
					totaleft = totaleft - marginHorizontal - getChildAt(i - 1).getMeasuredWidth();
					totalRight = totalRight - child.getMeasuredWidth() - marginHorizontal;
					adjustLine(lineViewCount, i); // 调整这一行的子布局的位置
					lineViewCount = 0;  // 这一行的子 child 的数量充值
					totalTop += maxChildHeight + marginVertical;
					totaleft = marginHorizontal;
					maxChildHeight = child.getMeasuredHeight() + marginVertical;
					totalRight = totaleft + child.getMeasuredWidth() + marginHorizontal;
				} else {
					maxChildHeight = Math.max(maxChildHeight, child.getMeasuredHeight() + marginVertical);
				}

				child.layout(
						totaleft,
						totalTop,
						totalRight,
						totalTop + child.getMeasuredHeight()
				);

                /* 统计这一行的子view的数量 */
				lineViewCount++;
			}
		}

        /* 调整最后一行子布局的位置 */

		adjustLine(lineViewCount, count);

	}

	/* 调整一行，让这一行的子布局水平居中 */
	private void adjustLine(int lineViewCount, int i) {
		totaleft = (parentWidth - totalRight + marginHorizontal * 2) / 2;
		int marginTop;
		for (int lineViewNumber = lineViewCount; lineViewNumber > 0; lineViewNumber--) {
			View lineViewChild = getChildAt(i - lineViewNumber);
			totalRight = totaleft + lineViewChild.getMeasuredWidth();
			if (lineViewChild.getMeasuredHeight() != maxChildHeight) {
				marginTop = (maxChildHeight - lineViewChild.getMeasuredHeight()) / 2;
			} else {
				marginTop = 0;
			}
			lineViewChild.layout(totaleft, totalTop + marginTop, totalRight, totalTop + marginTop + lineViewChild.getMeasuredHeight());
			totaleft += lineViewChild.getMeasuredWidth() + marginHorizontal;
		}
	}
}