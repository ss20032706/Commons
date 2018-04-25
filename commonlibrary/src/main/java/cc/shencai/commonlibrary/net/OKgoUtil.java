package cc.shencai.commonlibrary.net;

import android.app.Activity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cc.shencai.commonlibrary.net.callback.StringDialogCallback;
import okhttp3.OkHttpClient;

import static cc.shencai.commonlibrary.net.URLUtil.getURLWithParams;

/**
 * Created by yss on 2017/8/16
 *
 * @version 1.0.0
 */
public class OKgoUtil {

	/**
	 * 最常用的返回类型为String的get请求方式，默认不缓存
	 * @param url
	 *  @param activity 调用该请求的activity实例，用于标记该请求
	 * @param stringCallback 请求的回调函数
	 */
	public static void get(String url, Activity activity,AbsCallback stringCallback){
		OkGo.<String>get(url)
				.tag(activity)
				.cacheMode(CacheMode.NO_CACHE)       //上拉不需要缓存
				.execute(stringCallback);
	}


	/**
	 * 最常用的返回类型为String的get请求方式
	 * @param url
	 * @param activity 调用该请求的activity实例，用于标记该请求
	 * @param cacheMode 缓存模式
	 * @param stringCallback 请求的回调函数
	 */
	public static void get(String url, Activity activity,CacheMode cacheMode,StringCallback stringCallback){
		OkGo.<String>get(url)
				.tag(activity)
				.cacheMode(cacheMode)
				.execute(stringCallback);
	}

	/**
	 * 最常用的返回类型为String的get请求方式
	 * @param url
	 * @param paramsMap 参数集
	 * @param activity 调用该请求的activity实例，用于标记该请求
	 * @param stringCallback 请求的回调函数
	 */
	public static void get(String url, HashMap<String,String> paramsMap,Activity activity,StringCallback stringCallback){
		String urlOK = getURLWithParams(url,paramsMap);
		OkGo.<String>get(urlOK)
				.tag(activity)
				.execute(stringCallback);
	}

	/**
	 *  最常用的返回类型为String并且有多个参数的post请求，默认不缓存
	 * @param url
	 * @param activity 调用该请求的activity实例，用于标记该请求
	 * @param paramsMap 参数集
	 * @param stringCallback 请求的回调函数
	 */
	public static void post(String url, Activity activity, HashMap<String,String> paramsMap, AbsCallback stringCallback){
		PostRequest<String> request = OkGo.<String>post(url)
				.tag(activity)
				.cacheMode(CacheMode.NO_CACHE);

		if ( null != paramsMap) {
			Iterator iter = paramsMap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String key = (String) entry.getKey();
				String val = (String) entry.getValue();
				request.params(key,val);
			}
		}

		request.execute(stringCallback);
	}


	/**
	 *  最常用的返回类型为String并且有多个参数的post请求
	 * @param url
	 * @param activity 调用该请求的activity实例，用于标记该请求
	 * @param paramsMap 参数集
	 * @param cacheMode 缓存模式
	 * @param stringCallback 请求的回调函数
	 */
	public static void post(String url, Activity activity, HashMap<String,String> paramsMap, CacheMode cacheMode,AbsCallback stringCallback){
		PostRequest<String> request = OkGo.<String>post(url)
				.tag(activity)
				.cacheMode(cacheMode);

		if ( null != paramsMap) {
			Iterator iter = paramsMap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String key = (String) entry.getKey();
				String val = (String) entry.getValue();
				request.params(key,val);
			}
		}

		request.execute(stringCallback);
	}

	/**
	 *  最常用的返回类型为String并且有多个参数的post请求，默认不缓存
	 * @param url
	 * @param activity 调用该请求的activity实例，用于标记该请求
	 * @param json 请求的param
	 * @param stringCallback 请求的回调函数
	 */
	public static void post(String url, Activity activity,String json, AbsCallback stringCallback){
		OkGo.<String>post(url)
				.tag(activity)
				.upJson(json)
				.cacheMode(CacheMode.NO_CACHE)
				.execute(stringCallback);
	}

	/**
	 *  最常用的返回类型为String并且有多个参数的post请求
	 * @param url
	 * @param activity 调用该请求的activity实例，用于标记该请求
	 * @param json 请求的param
	 * @param cacheMode 缓存模式
	 * @param stringCallback 请求的回调函数
	 */
	public static void post(String url, Activity activity,String json, CacheMode cacheMode,AbsCallback stringCallback){
		OkGo.<String>post(url)
				.tag(activity)
				.upJson(json)
				.cacheMode(cacheMode)
				.execute(stringCallback);
	}


	/**
	 * 上传一个字符串到服务器
	 * @param url
	 * @param activity
	 * @param content 上传的字符串内容
	 * @param stringDialogCallback 请求的回调函数
	 */

	public static void upString(String url, Activity activity,String content,StringDialogCallback stringDialogCallback){
		OkGo.<String>post(url)
				.tag(activity)
				.upString(content)
				.cacheMode(CacheMode.NO_CACHE)
				.execute(stringDialogCallback);

	}


	/**
	 * 上传文件到服务器
	 * @param url
	 * @param activity
	 * @param file
	 * @param stringCallback
	 */
	public static void upFile(String url, Activity activity,HashMap<String,String> paramsMap, File file, StringCallback stringCallback){
		PostRequest<String> request = OkGo.<String>post(url)
				.tag(activity)
				.isMultipart(true)
//				.upFile(file)
				.params("file",file)
				.cacheMode(CacheMode.NO_CACHE);

		if ( null != paramsMap) {
			Iterator iter = paramsMap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String key = (String) entry.getKey();
				String val = (String) entry.getValue();
				request.params(key,val);
			}
		}

		request.execute(stringCallback);
	}

	/**
	 * 上传文件到服务器
	 * @param url
	 * @param activity
	 * @param path 上传文件的路径
	 * @param stringCallback
	 */
	public static void upFile(String url, Activity activity, HashMap<String,String> paramsMap,String path, StringCallback stringCallback){
		PostRequest<String> request = OkGo.<String>post(url)
				.tag(activity)
				.isMultipart(true)
				.params("file",new File(path))
				.cacheMode(CacheMode.NO_CACHE);

		if ( null != paramsMap) {
			Iterator iter = paramsMap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String key = (String) entry.getKey();
				String val = (String) entry.getValue();
				request.params(key,val);
			}
		}
		request.execute(stringCallback);
	}

	/**
	 * 获取返回的String
	 * @param response
	 * @return
	 */
	public static String getResponseString(Response<String> response){
		return response.body();
	}

	/**
	 * 取消全局默认的OKhttpClient中标识为tag的请求
	 * @param tag 根据tag取消请求
	 */
	public static void cancelRequest(Object tag){
		OkGo.getInstance().cancelTag(tag);
	}

	/**
	 * 取消给定OkHttpClient中标识为tag的请求
	 * @param client
	 * @param tag
	 */
	public static void cancelRequest(OkHttpClient client, Object tag){
		OkGo.cancelTag(client,tag);
	}

	/**
	 * 取消全局默认的OKhttpClient中的所有请求
	 */
	public static void cancellAll(){
		OkGo.getInstance().cancelAll();
	}

	/**
	 * 取消给定OkHttpClient的所有请求
	 * @param client
	 */
	public static void cancellAll(OkHttpClient client){
		OkGo.cancelAll(client);
	}

	/**
	 * 用OKgo下载文件，不支持断点续传
	 * @param url
	 * @param activity
	 * @param fileCallback
	 */
	public static void getFile(String url,Activity activity, FileCallback fileCallback){
		OkGo.<File>get(url)
				.tag(activity)
				.execute(fileCallback);
	}
}
