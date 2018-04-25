package cc.shencai.commonlibrary.utils;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * 检查权限的工具类
 * Created by yss on 2017/8/15
 *
 * @version 1.0.0
 */
public class PermissionsChecker {

	private final Context mContext;

	public PermissionsChecker(Context context) {
		mContext = context.getApplicationContext();
	}

	// 判断权限集合
	public boolean lacksPermissions(String... permissions) {
		for (String permission : permissions) {
			if (lacksPermission(permission)) {
				return true;
			}
		}
		return false;
	}

	// 判断是否缺少权限
	public boolean lacksPermission(String permission) {
		return ContextCompat.checkSelfPermission(mContext, permission) ==
				PackageManager.PERMISSION_DENIED;
	}
}
