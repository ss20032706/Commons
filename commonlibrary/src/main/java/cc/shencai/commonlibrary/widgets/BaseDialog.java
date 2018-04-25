package cc.shencai.commonlibrary.widgets;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import cc.shencai.commonlibrary.R;
import cc.shencai.commonlibrary.iml.UIInterface;

/**
 * 基础对话框界面，含有取消和确定按钮的监听，要做的就是自己定义页面UI，然后绑定监听到对应的按钮上
 * Created by yss on 2017/8/15
 *
 * @version 1.0.0
 */
public class BaseDialog extends Dialog implements UIInterface {

	private View.OnClickListener onCancleClickListener;//取消按钮被点击了的监听器
	private View.OnClickListener onConfirmClickListener;//确定按钮被点击了的监听器
	private Context mContext;

	public BaseDialog(Context context){
		super(context, R.style.mydialog_style);
		this.mContext = context;
	}



	public BaseDialog(Context context,View.OnClickListener onCancleClickListener,View.OnClickListener onConfirmClickListener){
		this(context);
		setListener(onCancleClickListener,onConfirmClickListener);
	}

	@Override
	public void initView() {
	}

	@Override
	public void initData() {

	}

	@Override
	public void initEvent() {
//		//设置确定按钮被点击后，向外界提供监听
//		tvClose.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if (yesOnclickListener != null) {
//					yesOnclickListener.onYesClick();
//				}
//			}
//		});

	}

	public void setListener( View.OnClickListener onCancleClickListener,
							 View.OnClickListener onConfirmClickListener){
		this.onCancleClickListener = onCancleClickListener;
		this.onConfirmClickListener = onConfirmClickListener;
	}

}
