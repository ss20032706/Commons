package baidumapsdk.demo1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;

import cc.shencai.commonlibrary.widgets.BaseActivity;

/**
 * Created by yss on 2017/12/28
 *
 * @version 1.0.0
 */
public class MapActivity extends BaseActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		// 注册 SDK 广播监听者
		IntentFilter iFilter = new IntentFilter();
		iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK);
		iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
		iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
		mReceiver = new SDKReceiver();
		registerReceiver(mReceiver, iFilter);
	}

	/**
	 * 构造广播监听类，监听 SDK key 验证以及网络异常广播
	 */
	public class SDKReceiver extends BroadcastReceiver {

		public void onReceive(Context context, Intent intent) {
			String s = intent.getAction();
			TextView text = (TextView) findViewById(R.id.textView);
			text.setTextColor(Color.RED);
			if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
				text.setText("key 验证出错! 错误码 :" + intent.getIntExtra
						(SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE, 0)
						+  " ; 请在 AndroidManifest.xml 文件中检查 key 设置");
			} else if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK)) {
				text.setText("key 验证成功! 功能可以正常使用");
				text.setTextColor(Color.YELLOW);
			} else if (s.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
				text.setText("网络出错");
			}
		}
	}

	private SDKReceiver mReceiver;


}
