package shencai.commonsample.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import cc.shencai.commonlibrary.utils.ActivityUtil;
import cc.shencai.commonlibrary.widgets.BaseActivity;
import shencai.commonsample.R;
import shencai.commonsample.application.MyApplication;

/**
 * Created by yss on 2017/9/13
 *
 * @version 1.0.0
 */
public class SplashActivity extends BaseActivity {


	@Bind(R.id.llDetailInfo)
	LinearLayout llDetailInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		ButterKnife.bind(this);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
			localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
		}
		initDetailHeight();
	}

	private void initDetailHeight() {
		ViewTreeObserver vto = llDetailInfo.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				llDetailInfo.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				MyApplication.getApplication().setDetailHeight(llDetailInfo.getHeight());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				ActivityUtil.gotoActivity(SplashActivity.this, MainActivity.class);
				finish();
			}
		});
	}
}
