package cc.shencai.commonlibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import cc.shencai.commonlibrary.encryption.SpSecurity;


/**
 * SharedPreferences封装类SPUtils，如果存取的是String类型，则是进行了一个加解密的过程。这样会保护隐私，存储的时候，针对String类型会加密，取值时对应着解密
 * Created by yss on 2017/8/14
 * @version 1.0.0
 * 对SharedPreference的使用做了建议的封装，对外公布出put，get，remove，clear等等方法；
 * 注意一点，里面所有的commit操作使用了SharedPreferencesCompat.apply进行了替代，目的是尽可能的使用apply代替commit
 * 首先说下为什么，因为commit方法是同步的，并且我们很多时候的commit操作都是UI线程中，毕竟是IO操作，尽可能异步；
 * 所以我们使用apply进行替代，apply异步的进行写入；
 * 但是apply相当于commit来说是new API呢，为了更好的兼容，所以做了适配；
 */
public class SpUtil {
	private static String FILE_NAME = "sp_config";
	private static SharedPreferences mSharedPreferences;
	private static SharedPreferences.Editor mEditor = null;

	/**
	 * 设置保存在手机里面的文件名，如果不设置的话，就默认为：sp_config
	 * @param fileName
	 */
	public static void setFileName(String fileName){
		FILE_NAME = fileName;
	}

	/**
	 * 初始化
	 * @param ctx
	 */
	public SpUtil(Context ctx) {
		if (mSharedPreferences == null || mEditor == null) {
			mSharedPreferences = ctx.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
			mEditor = mSharedPreferences.edit();
		}
	}

	/**
	 * 初始化，带文件名
	 * @param ctx
	 * @param fileName
	 */
	public SpUtil(Context ctx,String fileName) {
		FILE_NAME = fileName;
		if (mSharedPreferences == null || mEditor == null) {
			mSharedPreferences = ctx.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
			mEditor = mSharedPreferences.edit();
		}
	}

	/**
	 * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
	 *
	 * @param key
	 * @param object
	 */
	public void put(String key, Object object) {

		if (object instanceof String) {
			if (!TextUtils.isEmpty((String) object)) {//不为空的时候先加密。再存
				String code = SpSecurity.Encrypto((String) object);
				mEditor.putString(key, code);
			}
		} else if (object instanceof Integer) {
			mEditor.putInt(key, (Integer) object);
		} else if (object instanceof Boolean) {
			mEditor.putBoolean(key, (Boolean) object);
		} else if (object instanceof Float) {
			mEditor.putFloat(key, (Float) object);
		} else if (object instanceof Long) {
			mEditor.putLong(key, (Long) object);
		} else {
			mEditor.putString(key, object.toString());
		}

		SharedPreferencesCompat.apply();
	}

	/**
	 * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
	 *
	 * @param key
	 * @param defaultObject
	 * @return
	 */
	public static Object get(String key, Object defaultObject) {
		if (defaultObject instanceof String) {
			String code = mSharedPreferences.getString(key, null);
			if (!TextUtils.isEmpty(code)) {
				String unCode = SpSecurity.Decrypto(code);
				return unCode;
			} else {
				return (String) defaultObject;
			}
		} else if (defaultObject instanceof Integer) {
			return mSharedPreferences.getInt(key, (Integer) defaultObject);
		} else if (defaultObject instanceof Boolean) {
			return mSharedPreferences.getBoolean(key, (Boolean) defaultObject);
		} else if (defaultObject instanceof Float) {
			return mSharedPreferences.getFloat(key, (Float) defaultObject);
		} else if (defaultObject instanceof Long) {
			return mSharedPreferences.getLong(key, (Long) defaultObject);
		}

		return null;
	}

	/**
	 * 移除某个key值已经对应的值
	 *
	 * @param key
	 */
	public static void remove(String key) {
		mEditor.remove(key);
		SharedPreferencesCompat.apply();
	}

	/**
	 * 清除所有数据
	 */
	public static void clear() {
		mEditor.clear();
		SharedPreferencesCompat.apply();
	}

	/**
	 * 查询某个key是否已经存在
	 *
	 * @param key
	 * @return
	 */
	public static boolean contains(String key) {
		return mSharedPreferences.contains(key);
	}

	/**
	 * 返回所有的键值对
	 *
	 * @return
	 */
	public static Map<String, ?> getAll() {
		return mSharedPreferences.getAll();
	}

	/**
	 * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
	 */
	private static class SharedPreferencesCompat {
		private static final Method sApplyMethod = findApplyMethod();

		/**
		 * 反射查找apply的方法
		 *
		 * @return
		 */
		@SuppressWarnings({"unchecked", "rawtypes"})
		private static Method findApplyMethod() {
			try {
				Class clz = SharedPreferences.Editor.class;
				return clz.getMethod("apply");
			} catch (NoSuchMethodException e) {
			}

			return null;
		}

		/**
		 * 如果找到则使用apply执行，否则使用commit
		 */
		public static void apply() {
			try {
				if (sApplyMethod != null) {
					sApplyMethod.invoke(mEditor);
					return;
				}
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
			mEditor.commit();
		}
	}

}
