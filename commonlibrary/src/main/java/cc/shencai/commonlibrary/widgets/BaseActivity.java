package cc.shencai.commonlibrary.widgets;

import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import cc.shencai.commonlibrary.iml.UIInterface;
import cc.shencai.commonlibrary.utils.ActivityUtil;
import cc.shencai.commonlibrary.utils.ToastUtil;

/**
 * Created by yss on 2017/8/24
 *
 * @version 1.0.0
 */
public class BaseActivity extends AppCompatActivity implements UIInterface {
	private long exitTime = 0;

	@Override
	public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
		super.onCreate(savedInstanceState, persistentState);
		hideActionbar();
	}

	private void hideActionbar(){
		ActionBar actionBar = getSupportActionBar();
		actionBar.hide();
	}

	@Override
	public void setContentView(@LayoutRes int layoutResID) {
		if (getSupportActionBar() != null) {
			getSupportActionBar().hide();
		}
		super.setContentView(layoutResID);
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

	/**
	 * 用于主页按2次返回键退出app
	 */
	public void exitAppByAsk() {
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			ToastUtil.showShort(this, "再按一次退出程序");
			exitTime = System.currentTimeMillis();
		} else {
			exitApp();
		}
	}

	/**
	 * 退出app
	 */
	public void exitApp() {
			finish();
			System.exit(0);
	}

	/**
	 * 设置状态栏颜色
	 */
	public void setStatusBar(int color){
		if (Build.VERSION.SDK_INT >= 21) {
			//这个只能修改背景颜色
			getWindow().setStatusBarColor(color);
		}
	}

	/**
	 *handler将要进行的操作
	 */
	public void handlerTodo(Message msg) {
	}

}
