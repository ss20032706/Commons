package cc.shencai.commonlibrary.widgets;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import cc.shencai.commonlibrary.R;

/**
 * 加载的旋转动画页面，当加载完就将该dialog调用dismiss即可
 * Created by yss on 2017/8/15
 *
 * @version 1.0.0
 */
public class LoadingDialog extends BaseDialog {

	TextView tvContent;

	public LoadingDialog(Context context) {
		super(context);
	}

	public void setDialogCancelable(Boolean cancelable){
		setCancelable(cancelable);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.dialog_loading);

		tvContent = (TextView) findViewById(R.id.tvContent);
		setCancelable(false);
	}

	public void setContent(String content){
		tvContent.setText("" + content);
	}
}
