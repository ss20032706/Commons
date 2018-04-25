package shencai.commonsample.ui.fg2content.permissions4m;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.joker.annotation.PermissionsCustomRationale;
import com.joker.annotation.PermissionsDenied;
import com.joker.annotation.PermissionsGranted;
import com.joker.annotation.PermissionsNonRationale;
import com.joker.annotation.PermissionsRationale;
import com.joker.annotation.PermissionsRequestSync;
import com.joker.api.Permissions4M;
import com.joker.api.support.PermissionsPageManager;
import com.joker.api.wrapper.ListenerWrapper;
import com.joker.api.wrapper.Wrapper;

import cc.shencai.commonlibrary.utils.ToastUtil;
import cc.shencai.commonlibrary.widgets.BaseActivity;
import shencai.commonsample.R;

import static shencai.commonsample.ui.fg2content.permissions4m.PermissionActivity.CALENDAR_CODE;
import static shencai.commonsample.ui.fg2content.permissions4m.PermissionActivity.CAMERA_CODE;
import static shencai.commonsample.ui.fg2content.permissions4m.PermissionActivity.LOCATION_CODE;
import static shencai.commonsample.ui.fg2content.permissions4m.PermissionActivity.SENSORS_CODE;

/**
 * Created by yss on 2017/9/15
 *
 * @version 1.0.0
 */

@PermissionsRequestSync(permission = {Manifest.permission.BODY_SENSORS, Manifest.permission
		.ACCESS_FINE_LOCATION, Manifest.permission.READ_CALENDAR,Manifest.permission.CAMERA},
		value = {SENSORS_CODE, LOCATION_CODE, CALENDAR_CODE,CAMERA_CODE})
public class PermissionActivity extends BaseActivity {
	public static final int CALENDAR_CODE = 1;
	public static final int SENSORS_CODE = 2;
	public static final int LOCATION_CODE = 3;
	private static final int CALL_LOG_CODE = 4;
	private static final int AUDIO_CODE = 5;
	private static final int READ_CONTACTS_CODE = 6;
	public static final int CAMERA_CODE = 7;
	private Button mCallButton;
	private Button mAudioButton;
	private Button mOneButton;
	private Button mManagerButton;
	private Button mPermissionPageButton;
	private Button mPageListenerButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_permission);
		mCallButton = (Button) findViewById(R.id.btn_calendar);
		mAudioButton = (Button) findViewById(R.id.btn_audio);
		mOneButton = (Button) findViewById(R.id.btn_one);
		mManagerButton = (Button) findViewById(R.id.btn_manager);
		mPermissionPageButton = (Button) findViewById(R.id.btn_permission_page);
		mPageListenerButton = (Button) findViewById(R.id.btn_page_listener);

		// 通话记录
		mCallButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Permissions4M.get(PermissionActivity.this)
						.requestPermissions(Manifest.permission.READ_CALL_LOG)
						.requestCodes(CALL_LOG_CODE)
						.requestPageType(Permissions4M.PageType.MANAGER_PAGE)
						.request();
			}
		});

		// 录音
		mAudioButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Permissions4M.get(PermissionActivity.this)
						.requestPermissions(Manifest.permission.RECORD_AUDIO)
						.requestCodes(AUDIO_CODE)
						.requestPageType(Permissions4M.PageType.ANDROID_SETTING_PAGE)
						.request();
			}
		});

		// 一键申请
		mOneButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Permissions4M
						.get(PermissionActivity.this)
						.requestSync();
			}
		});

		mManagerButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(PermissionsPageManager.getIntent(PermissionActivity.this));
			}
		});

		mPermissionPageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(PermissionsPageManager.getSettingIntent(PermissionActivity.this));
			}
		});

		mPageListenerButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Permissions4M.get(PermissionActivity.this)
						.requestPermissions(Manifest.permission.READ_CONTACTS)
						.requestCodes(READ_CONTACTS_CODE)
						.requestListener(new ListenerWrapper.PermissionRequestListener() {
							@Override
							public void permissionGranted(int code) {
								ToastUtil.showShort(PermissionActivity.this,"读取通讯录权限成功 in activity with listener");
							}

							@Override
							public void permissionDenied(int code) {
								ToastUtil.showShort(PermissionActivity.this,"读取通讯录权失败 in activity with listener");
							}

							@Override
							public void permissionRationale(int code) {
								ToastUtil.showShort(PermissionActivity.this,"请打开读取通讯录权限 in activity with listener");
							}
						})
						.requestPageType(Permissions4M.PageType.MANAGER_PAGE)
						.requestPage(new Wrapper.PermissionPageListener() {
							@Override
							public void pageIntent(final Intent intent) {
								new AlertDialog.Builder(PermissionActivity.this)
										.setMessage("用户您好，我们需要您开启读取通讯录权限申请：\n请点击前往设置页面\n(in activity with listener)")
										.setPositiveButton("前往设置页面", new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog, int which) {
												startActivity(intent);
											}
										})
										.setNegativeButton("取消", new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog, int which) {
												dialog.dismiss();
											}
										})
										.show();
							}
						})
						.request();
			}
		});
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
			grantResults) {
		Permissions4M.onRequestPermissionsResult(PermissionActivity.this, requestCode, grantResults);
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}

	//====================================================================
	@PermissionsGranted({LOCATION_CODE, SENSORS_CODE, CALENDAR_CODE,CAMERA_CODE})
	public void syncGranted(int code) {
		switch (code) {
			case LOCATION_CODE:
				ToastUtil.showShort(PermissionActivity.this,"地理位置权限授权成功 in activity with annotation");
				Log.d("TAG", "syncGranted:  地理位置权限授权成功 in activity with annotation");
				break;
			case SENSORS_CODE:
				ToastUtil.showShort(PermissionActivity.this,"传感器权限授权成功 in activity with annotation");
				Log.d("TAG", "syncGranted:  传感器权限授权成功 in activity with annotation");
				break;
			case CALENDAR_CODE:
				ToastUtil.showShort(PermissionActivity.this,"读取日历权限授权成功 in activity with annotation");
				Log.d("TAG", "syncGranted:  读取日历权限授权成功 in activity with annotation");
				break;
			case CAMERA_CODE:
				ToastUtil.showShort(PermissionActivity.this,"读取日历权限授权成功 in activity with annotation");
				Log.d("TAG", "syncGranted:  读取摄像头权限授权成功 in activity with annotation");
				break;
			default:
				break;
		}
	}

	@PermissionsDenied({LOCATION_CODE, SENSORS_CODE, CALENDAR_CODE,CAMERA_CODE})
	public void syncDenied(int code) {
		switch (code) {
			case LOCATION_CODE:
				ToastUtil.showShort(PermissionActivity.this,"地理位置权限授权失败 in activity with annotation");
				Log.d("TAG", "syncDenied:  地理位置权限授权失败 in activity with annotation");
				break;
			case SENSORS_CODE:
				ToastUtil.showShort(PermissionActivity.this,"传感器权限授权失败 in activity with annotation");
				Log.d("TAG", "syncDenied:  传感器权限授权失败 in activity with annotation");
				break;
			case CALENDAR_CODE:
				ToastUtil.showShort(PermissionActivity.this,"读取日历权限授权失败 in activity with annotation");
				Log.d("TAG", "syncDenied:  读取日历权限授权失败 in activity with annotation");
				break;
			case CAMERA_CODE:
				ToastUtil.showShort(PermissionActivity.this,"读取日历权限授权失败 in activity with annotation");
				Log.d("TAG", "syncDenied:  读取摄像头权限授权失败 in activity with annotation");
				break;
			default:
				break;
		}
	}

	@PermissionsRationale({LOCATION_CODE, SENSORS_CODE, CALENDAR_CODE,CAMERA_CODE})
	public void syncRationale(int code) {
		switch (code) {
			case LOCATION_CODE:
				ToastUtil.showShort(PermissionActivity.this,"请开启地理位置权限 in activity with annotation");
				Log.d("TAG", "syncRationale:  请开启地理位置权限 in activity with annotation");
				break;
			case SENSORS_CODE:
				ToastUtil.showShort(PermissionActivity.this,"请开启传感器权限 in activity with annotation");
				Log.d("TAG", "syncRationale:  请开启传感器权限 in activity with annotation");
				break;
			case CALENDAR_CODE:
				ToastUtil.showShort(PermissionActivity.this,"请开启读取日历权限 in activity with annotation");
				Log.d("TAG", "syncRationale:  请开启读取日历权限 in activity with annotation");
				break;
			case CAMERA_CODE:
				ToastUtil.showShort(PermissionActivity.this,"请开启读取日历权限 in activity with annotation");
				Log.d("TAG", "syncRationale:  请开启读取摄像头权限 in activity with annotation");
				break;
			default:
				break;
		}
	}

	//====================================================================
	@PermissionsGranted(CALL_LOG_CODE)
	public void storageAndCallGranted() {
		ToastUtil.showShort(PermissionActivity.this,"读取通话记录权限授权成功 in activity with annotation");
	}

	@PermissionsDenied(CALL_LOG_CODE)
	public void storageAndCallDenied() {
		ToastUtil.showShort(PermissionActivity.this,"读取通话记录权限授权失败 in activity with annotation");
	}

	@PermissionsRationale(CALL_LOG_CODE)
	public void storageAndCallNonRationale() {
		ToastUtil.showShort(PermissionActivity.this,"请开启读取通话记录权限授权 in activity with annotation");
	}

	//====================================================================
	@PermissionsGranted(AUDIO_CODE)
	public void smsAndAudioGranted() {
		ToastUtil.showShort(PermissionActivity.this,"录音权限申请成功 in activity with annotation");
	}

	@PermissionsDenied(AUDIO_CODE)
	public void smsAndAudioDenied() {
		ToastUtil.showShort(PermissionActivity.this,"录音权限申请失败 in activity with annotation");
	}

	@PermissionsCustomRationale(AUDIO_CODE)
	public void smsAndAudioCustomRationale() {
		new AlertDialog.Builder(this)
				.setMessage("录音权限申请：\n我们需要您开启录音权限(in activity with annotation)")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Permissions4M.get(PermissionActivity.this)
								.requestOnRationale()
								.requestPermissions(Manifest.permission.RECORD_AUDIO)
								.requestCodes(AUDIO_CODE)
								.request();
					}
				})
				.show();
	}

	//===================================================================
	@PermissionsNonRationale({AUDIO_CODE, CALL_LOG_CODE})
	public void storageAndCallRationale(int code, final Intent intent) {
		switch (code) {
			case AUDIO_CODE:
				new AlertDialog.Builder(PermissionActivity.this)
						.setMessage("用户您好，我们需要您开启读取录音权限\n请点击前往设置页面\n(in activity with listener)")
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								startActivity(intent);
							}
						})
						.setNegativeButton("取消", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();
							}
						})
						.show();
				break;
			case CALL_LOG_CODE:
				new AlertDialog.Builder(PermissionActivity.this)
						.setMessage("用户您好，我们需要您开启读取通话记录权限\n请点击前往设置页面\n(in activity with listener)")
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								startActivity(intent);
							}
						})
						.setNegativeButton("取消", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();
							}
						})
						.show();
				break;
			default:
				break;
		}
	}
}
