package cc.shencai.commonlibrary.utils;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Pattern;

/**
 * APP相关工具类
 * Created by yss on 2017/8/15
 *
 * @version 1.0.0
 */
public class AppInfoUtil {


	/**
	 * 获取本地apk的名称
	 * @param context 上下文
	 * @return String
	 */
	@SuppressWarnings("rawtypes")
	public static String getAppName(Context context) {
		try {
			PackageManager e = context.getPackageManager();
			PackageInfo packageInfo = e.getPackageInfo(context.getPackageName(), 0);
			int labelRes = packageInfo.applicationInfo.labelRes;
			return context.getResources().getString(labelRes);
		} catch (Exception var4) {
			var4.printStackTrace();
			return "unKnown";
		}
	}

	/*
	 * 判断当前应用是否是debug状态
	 *
	 */
	public static boolean isApkInDebug(Context context) {
		try {
			ApplicationInfo info = context.getApplicationInfo();
			return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 获取本地Apk版本名称
	 * @param context 上下文
	 * @return String
	 */
	public static String getVersionName(Context context) {
		String verName = "";
		try {
			verName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return verName;
	}


	/**
	 * 获取本地Apk版本号
	 * @param context 上下文
	 * @return int
	 */
	public static int getVersionCode(Context context) {
		int verCode = -1;
		try {
			verCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return verCode;
	}


	/**
	 * 获取应用图标
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static Drawable getAppIcon(Context context, String packageName) {
		PackageManager pm = context.getPackageManager();
		Drawable appIcon = null;
		try {
			ApplicationInfo applicationInfo = pm.getApplicationInfo(packageName, 0);
			appIcon = applicationInfo.loadIcon(pm);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return appIcon;
	}

	/**
	 * 获取应用第一次安装日期
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static long getAppFirstInstallTime(Context context, String packageName) {
		long lastUpdateTime = 0;
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
			lastUpdateTime = packageInfo.firstInstallTime;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return lastUpdateTime;
	}

	/**
	 * 获取应用更新日期
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static long getAppLastUpdateTime(Context context, String packageName) {
		long lastUpdateTime = 0;
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
			lastUpdateTime = packageInfo.lastUpdateTime;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return lastUpdateTime;
	}

	/**
	 * 获取应用大小
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static long getAppSize(Context context, String packageName) {
		long appSize = 0;
		try {
			ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(packageName, 0);
			appSize = new File(applicationInfo.sourceDir).length();
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return appSize;
	}

	/**
	 * 获取应用apk文件
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static String getAppApk(Context context, String packageName) {
		String sourceDir = null;
		try {
			ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(packageName, 0);
			sourceDir = applicationInfo.sourceDir;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return sourceDir;
	}

	/**
	 * 获取应用的安装市场
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static String getAppInstaller(Context context, String packageName) {
		return context.getPackageManager().getInstallerPackageName(packageName);
	}

	/**
	 * 获取应用签名
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static String getAppSign(Context context, String packageName) {
		try {
			PackageInfo pis = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
			return hexdigest(pis.signatures[0].toByteArray());
		} catch (PackageManager.NameNotFoundException e) {
			throw new RuntimeException("the " + packageName + "'s application not found");
		}
	}

	public static String hexdigest(byte[] paramArrayOfByte) {
		final char[] hexDigits = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
		try {
			MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
			localMessageDigest.update(paramArrayOfByte);
			byte[] arrayOfByte = localMessageDigest.digest();
			char[] arrayOfChar = new char[32];
			for (int i = 0, j = 0; ; i++, j++) {
				if (i >= 16) {
					return new String(arrayOfChar);
				}
				int k = arrayOfByte[i];
				arrayOfChar[j] = hexDigits[(0xF & k >>> 4)];
				arrayOfChar[++j] = hexDigits[(k & 0xF)];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取应用兼容sdk
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static int getAppTargetSdkVersion(Context context, String packageName) {
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
			ApplicationInfo applicationInfo = packageInfo.applicationInfo;
			return applicationInfo.targetSdkVersion;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 获取应用uid
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static int getAppUid(Context context, String packageName) {
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
			ApplicationInfo applicationInfo = packageInfo.applicationInfo;
			return applicationInfo.uid;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 获取Cpu内核数
	 * @return
	 */
	public static int getNumCores() {
		try {
			File dir = new File("/sys/devices/system/cpu/");
			File[] files = dir.listFiles(new FileFilter() {

				@Override
				public boolean accept(File pathname) {
					return Pattern.matches("cpu[0-9]", pathname.getName());
				}

			});
			return files.length;
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}

	/**
	 * 获得root权限
	 * @param context
	 * @return
	 */
	public static boolean getRootPermission(Context context) {
		String packageCodePath = context.getPackageCodePath();
		Process process = null;
		DataOutputStream os = null;
		try {
			String cmd = "chmod 777 " + packageCodePath;
			process = Runtime.getRuntime().exec("su");
			os = new DataOutputStream(process.getOutputStream());
			os.writeBytes(cmd + "\n");
			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();
		} catch (Exception e) {
			return false;
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				process.destroy();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * 获取应用的所有权限
	 * @param context
	 * @param packname
	 * @return
	 */
	public static String[] getAppPermissions(Context context, String packname) {
		String[] requestedPermissions = null;
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(packname, PackageManager.GET_PERMISSIONS);
			requestedPermissions = info.requestedPermissions;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return requestedPermissions;
	}

	/**
	 * 是否有权限
	 * @param context
	 * @param permission
	 * @return
	 */
	public static boolean hasPermission(Context context, String permission) {
		if (context != null && !TextUtils.isEmpty(permission)) {
			try {
				PackageManager packageManager = context.getPackageManager();
				if (packageManager != null) {
					if (PackageManager.PERMISSION_GRANTED == packageManager.checkPermission(permission, context
							.getPackageName())) {
						return true;
					}
					Log.d("AppUtils", "Have you  declared permission " + permission + " in AndroidManifest.xml ?");
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	/**
	 * 应用是否安装
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean isInstalled(Context context, String packageName) {
		boolean installed = false;
		if (TextUtils.isEmpty(packageName)) {
			return false;
		}
		List<ApplicationInfo> installedApplications = context.getPackageManager().getInstalledApplications(0);
		for (ApplicationInfo in : installedApplications) {
			if (packageName.equals(in.packageName)) {
				installed = true;
				break;
			} else {
				installed = false;
			}
		}
		return installed;
	}

	/**
	 * 安装应用
	 * @param context
	 * @param filePath
	 * @return
	 */
	public static boolean installApk(Context context, String filePath) {
		File file = new File(filePath);
		if (!file.exists() || !file.isFile() || file.length() <= 0) {
			return false;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
		return true;
	}

	/**
	 * 卸载应用
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean uninstallApk(Context context, String packageName) {
		if (TextUtils.isEmpty(packageName)) {
			return false;
		}
		Intent i = new Intent(Intent.ACTION_DELETE, Uri.parse("package:" + packageName));
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
		return true;
	}

	/**
	 * 是否是系统应用
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean isSystemApp(Context context, String packageName) {
		boolean isSys = false;
		PackageManager pm = context.getPackageManager();
		try {
			ApplicationInfo applicationInfo = pm.getApplicationInfo(packageName, 0);
			if (applicationInfo != null && (applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
				isSys = true;
			}
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			isSys = false;
		}
		return isSys;
	}

	/**
	 * 服务是否在运行
	 * @param context
	 * @param className
	 * @return
	 */
	public static boolean isServiceRunning(Context context, String className) {
		boolean isRunning = false;
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> servicesList = activityManager.getRunningServices(Integer.MAX_VALUE);
		for (ActivityManager.RunningServiceInfo si : servicesList) {
			if (className.equals(si.service.getClassName())) {
				isRunning = true;
			}
		}
		return isRunning;
	}

	/**
	 * 停止服务
	 * @param context
	 * @param className
	 * @return
	 */
	public static boolean stopRunningService(Context context, String className) {
		Intent intent_service = null;
		boolean ret = false;
		try {
			intent_service = new Intent(context, Class.forName(className));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (intent_service != null) {
			ret = context.stopService(intent_service);
		}
		return ret;
	}

	/**
	 * 结束进程
	 * @param context
	 * @param pid
	 * @param processName
	 */
	public static void killProcesses(Context context, int pid, String processName) {
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		String packageName;
		try {
			if (!processName.contains(":")) {
				packageName = processName;
			} else {
				packageName = processName.split(":")[0];
			}
			activityManager.killBackgroundProcesses(packageName);
			Method forceStopPackage = activityManager.getClass().getDeclaredMethod("forceStopPackage", String.class);
			forceStopPackage.setAccessible(true);
			forceStopPackage.invoke(activityManager, packageName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 运行脚本
	 * @param script
	 * @return
	 */
	public static String runScript(String script) {
		String sRet;
		try {
			final Process m_process = Runtime.getRuntime().exec(script);
			final StringBuilder sbread = new StringBuilder();
			Thread tout = new Thread(new Runnable() {
				public void run() {
					BufferedReader bufferedReader = new BufferedReader(
							new InputStreamReader(m_process.getInputStream()),
							8192);
					String ls_1;
					try {
						while ((ls_1 = bufferedReader.readLine()) != null) {
							sbread.append(ls_1).append("\n");
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							bufferedReader.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			});
			tout.start();

			final StringBuilder sberr = new StringBuilder();
			Thread terr = new Thread(new Runnable() {
				public void run() {
					BufferedReader bufferedReader = new BufferedReader(
							new InputStreamReader(m_process.getErrorStream()),
							8192);
					String ls_1;
					try {
						while ((ls_1 = bufferedReader.readLine()) != null) {
							sberr.append(ls_1).append("\n");
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							bufferedReader.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			});
			terr.start();

			m_process.waitFor();
			while (tout.isAlive()) {
				Thread.sleep(50);
			}
			if (terr.isAlive())
				terr.interrupt();
			String stdout = sbread.toString();
			String stderr = sberr.toString();
			sRet = stdout + stderr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return sRet;
	}

	/**
	 * 启动应用
	 * @param context
	 * @param packagename
	 */
	public static void runApp(Context context, String packagename) {
		context.startActivity(new Intent(context.getPackageManager().getLaunchIntentForPackage(packagename)));
	}


//  /**
//   * 获得当前版本信息
//   * @param keyValues key信息
//   * @return RequestParams
//   */
//  public static RequestParams getRequestParams(HashMap<String,String> keyValues){
//      RequestParams params = new RequestParams();
//      Iterator iterator = keyValues.entrySet().iterator();
//        while(iterator.hasNext()){
//            Map.Entry entry = (Map.Entry) iterator.next();
//            Object key = entry.getKey();
//            params.put((String) key, entry.getValue().toString());
//        }
//      return params;
//  }

	/**
	 * 获得包名
	 *
	 * @param context 上下文
	 * @return 包名
	 */
	public static String getPackageName(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).packageName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取application层级的metadata
	 *
	 * @param context 上下文
	 * @param key     key
	 * @return value
	 */
	public static String getApplicationMetaData(Context context, String key) {
		try {
			Bundle metaData = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData;
			return metaData.get(key).toString();
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获得应用申明的所有权限列表
	 * @param context 上下文
	 * @return 获得应用申明的所有权限列表
	 */
	public static List<String> getPermissions(Context context){
		List<String> permissions=new ArrayList<String>();
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
			permissions.addAll(Arrays.asList(packageInfo.requestedPermissions));

		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return permissions;
	}

	/**
	 * 获取身份证号所有区域编码设置
	 * @return Hashtable
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Hashtable getAreaCodeAll() {
		Hashtable hashtable = new Hashtable();
		hashtable.put("11", "北京");
		hashtable.put("12", "天津");
		hashtable.put("13", "河北");
		hashtable.put("14", "山西");
		hashtable.put("15", "内蒙古");
		hashtable.put("21", "辽宁");
		hashtable.put("22", "吉林");
		hashtable.put("23", "黑龙江");
		hashtable.put("31", "上海");
		hashtable.put("32", "江苏");
		hashtable.put("33", "浙江");
		hashtable.put("34", "安徽");
		hashtable.put("35", "福建");
		hashtable.put("36", "江西");
		hashtable.put("37", "山东");
		hashtable.put("41", "河南");
		hashtable.put("42", "湖北");
		hashtable.put("43", "湖南");
		hashtable.put("44", "广东");
		hashtable.put("45", "广西");
		hashtable.put("46", "海南");
		hashtable.put("50", "重庆");
		hashtable.put("51", "四川");
		hashtable.put("52", "贵州");
		hashtable.put("53", "云南");
		hashtable.put("54", "西藏");
		hashtable.put("61", "陕西");
		hashtable.put("62", "甘肃");
		hashtable.put("63", "青海");
		hashtable.put("64", "宁夏");
		hashtable.put("65", "新疆");
		hashtable.put("71", "台湾");
		hashtable.put("81", "香港");
		hashtable.put("82", "澳门");
		hashtable.put("91", "国外");
		return hashtable;
	}


	/**
	 * 根据身份号返回所在区域信息
	 * @param idCard
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String getIDCardArea(String idCard) {
		Hashtable<String, String> ht = getAreaCodeAll();
		String area = ht.get(idCard.substring(0, 2));
		return area;
	}


	/**
	 * 56名族定义
	 * @return Hashtable
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Hashtable getMinorityAll() {
		Hashtable hashtable = new Hashtable();
		hashtable.put("汉族", "汉族");
		hashtable.put("壮族", "壮族");
		hashtable.put("满族", "满族");
		hashtable.put("回族", "回族");
		hashtable.put("苗族", "苗族");
		hashtable.put("维吾尔族", "维吾尔族");
		hashtable.put("土家族", "土家族");
		hashtable.put("彝族", "彝族");
		hashtable.put("蒙古族", "蒙古族");
		hashtable.put("藏族", "藏族");
		hashtable.put("布依族", "布依族");
		hashtable.put("侗族", "侗族");
		hashtable.put("瑶族", "瑶族");
		hashtable.put("朝鲜族", "朝鲜族");
		hashtable.put("白族", "白族");
		hashtable.put("哈尼族", "哈尼族");
		hashtable.put("哈萨克族", "哈萨克族");
		hashtable.put("黎族", "黎族");
		hashtable.put("傣族", "傣族");
		hashtable.put("畲族", "畲族");
		hashtable.put("傈僳族", "傈僳族");
		hashtable.put("仡佬族", "仡佬族");
		hashtable.put("东乡族", "东乡族");
		hashtable.put("高山族", "高山族");
		hashtable.put("拉祜族", "拉祜族");
		hashtable.put("水族", "水族");
		hashtable.put("佤族", "佤族");
		hashtable.put("纳西族", "纳西族");
		hashtable.put("羌族", "羌族");
		hashtable.put("土族", "土族");
		hashtable.put("仫佬族", "仫佬族");
		hashtable.put("锡伯族", "锡伯族");
		hashtable.put("柯尔克孜族", "柯尔克孜族");
		hashtable.put("达斡尔族", "达斡尔族");
		hashtable.put("景颇族", "景颇族");
		hashtable.put("毛南族", "毛南族");
		hashtable.put("撒拉族", "撒拉族");
		hashtable.put("布朗族", "布朗族");
		hashtable.put("塔吉克族", "塔吉克族");
		hashtable.put("阿昌族", "阿昌族");
		hashtable.put("普米族", "普米族");
		hashtable.put("鄂温克族", "鄂温克族");
		hashtable.put("怒族", "怒族");
		hashtable.put("京族", "京族");
		hashtable.put("基诺族", "基诺族");
		hashtable.put("德昂族", "德昂族");
		hashtable.put("保安族", "保安族");
		hashtable.put("俄罗斯族", "俄罗斯族");
		hashtable.put("裕固族", "裕固族");
		hashtable.put("乌孜别克族", "乌孜别克族");
		hashtable.put("门巴族", "门巴族");
		hashtable.put("鄂伦春族", "鄂伦春族");
		hashtable.put("独龙族", "独龙族");
		hashtable.put("塔塔尔族", "塔塔尔族");
		hashtable.put("赫哲族", "赫哲族");
		hashtable.put("珞巴族", "珞巴族");
		return hashtable;
	}

	/**
	 * 判断app是否是后台运行
	 * @param context
	 * @return
	 */
	public static boolean isBackground(Context context) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
		for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(context.getPackageName())) {
                /*
                BACKGROUND=400 EMPTY=500 FOREGROUND=100
                GONE=1000 PERCEPTIBLE=130 SERVICE=300 ISIBLE=200
                 */
				Log.i(context.getPackageName(), "此appimportace ="
						+ appProcess.importance
						+ ",context.getClass().getName()="
						+ context.getClass().getName());
				if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
					Log.i(context.getPackageName(), "处于后台"
							+ appProcess.processName);
					return true;
				} else {
					Log.i(context.getPackageName(), "处于前台"
							+ appProcess.processName);
					return false;
				}
			}
		}
		return false;
	}

}
