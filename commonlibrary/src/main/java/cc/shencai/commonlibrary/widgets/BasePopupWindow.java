package cc.shencai.commonlibrary.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import cc.shencai.commonlibrary.iml.UIInterface;

/**
 * Created by yss on 2017/8/15
 *
 * @version 1.0.0
 */
public class BasePopupWindow extends PopupWindow implements UIInterface{
	public  LayoutInflater inflater;
	public Context mContext;
	View.OnClickListener onCancleClickListener;
	View.OnClickListener onConfirmClickListener;

	/**
	 * @param context 上下文
	 */
	public BasePopupWindow(Context context,
							  View.OnClickListener onCancleClickListener,
							  View.OnClickListener onConfirmClickListener) {
		this(context);
		setListener(onCancleClickListener,onConfirmClickListener);
	}

	/**
	 * @param context 上下文
	 */
	public BasePopupWindow(Context context) {
		super(context);
		mContext = context;
		inflater = LayoutInflater.from(context);
	}

	public void setListener( View.OnClickListener onCancleClickListener,
							 View.OnClickListener onConfirmClickListener){
		this.onCancleClickListener = onCancleClickListener;
		this.onConfirmClickListener = onConfirmClickListener;
	}

	/**
	 * 界面初始化,设置布局文件
	 *
	 * @param res 布局文件
	 */
	public void setMyContentView(int res) {
		setContentView(inflater.inflate(res, null));
		setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
		setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		setFocusable(true);
		setBackgroundDrawable(null);
	}

	@Override
	public void initView() {

	}

	@Override
	public void initData() {

	}

	@Override
	public void initEvent() {

	}
}