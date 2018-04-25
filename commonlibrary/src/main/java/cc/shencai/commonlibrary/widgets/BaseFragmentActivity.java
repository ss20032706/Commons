package cc.shencai.commonlibrary.widgets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

import cc.shencai.commonlibrary.iml.UIInterface;
import cc.shencai.commonlibrary.utils.ActivityUtil;
import cc.shencai.commonlibrary.utils.ToastUtil;

/**
 * Created by yss on 2017/9/14
 *
 * @version 1.0.0
 */
public class BaseFragmentActivity extends FragmentActivity implements UIInterface {
	private long exitTime = 0;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			ActivityUtil.finishActivity(this);
		}
		return false;
	}

	public void exitApp() {
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			ToastUtil.showShort(this, "再按一次退出程序");
			exitTime = System.currentTimeMillis();
		} else {
			finish();
			System.exit(0);
		}
	}
}
