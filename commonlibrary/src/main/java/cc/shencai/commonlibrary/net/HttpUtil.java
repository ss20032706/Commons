//package cc.shencai.commonlibrary.net;
//
//import com.google.gson.Gson;
//import com.zhy.http.okhttp.OkHttpUtils;
//import com.zhy.http.okhttp.builder.GetBuilder;
//import com.zhy.http.okhttp.callback.StringCallback;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
///**
// * Http请求的封装，待完善取消请求以及界面关闭后的资源释放处理
// * Created by yss on 2017/8/15
// *
// * @version 1.0.0
// */
//public class HttpUtil {
//	public static final MediaType JSON
//			= MediaType.parse("application/json; charset=utf-8");
//
//	public static OkHttpClient client = new OkHttpClient();
//
//	/**
//	 * post请求
//	 * @param url 请求的url
//	 * @param json 拼接好的参数
//	 * @return
//	 * @throws IOException
//	 */
//	public static String post(String url, String json) throws IOException {
//		RequestBody body = RequestBody.create(JSON, json);
//		Request request = new Request.Builder()
//				.url(url)
//				.post(body)
//				.build();
//		Response response = client.newCall(request).execute();
//		return response.body().string();
//	}
//
//	/**
//	 * post请求
//	 * @param url 请求的url
//	 * @param bean 请求的实体参数类
//	 * @return
//	 * @throws IOException
//	 */
//	public static String post(String url, Object bean) throws IOException {
//		String json = new Gson().toJson(bean);
//		RequestBody body = RequestBody.create(JSON, json);
//		Request request = new Request.Builder()
//				.url(url)
//				.post(body)
//				.build();
//		Response response = client.newCall(request).execute();
//		return response.body().string();
//	}
//
//	/**
//	 * get请求
//	 * @param url 请求的url
//	 * @param paramsMap 请求的参数，没有参数的话，就传入null
//	 * @param stringCallback 请求的回调
//	 */
//	public static void get(String url, HashMap<String,String> paramsMap,StringCallback stringCallback) {
//		GetBuilder getbuilder = OkHttpUtils.get();
//		getbuilder.url(url);
//		if ( null != paramsMap) {
//			Iterator iter = paramsMap.entrySet().iterator();
//			while (iter.hasNext()) {
//				Map.Entry entry = (Map.Entry) iter.next();
//				String key = (String) entry.getKey();
//				String val = (String) entry.getValue();
//				getbuilder.addParams(key,val);
//			}
//		}
//		getbuilder
//				.build()
//				.execute(stringCallback);
//	}
//
//
//
//
//}
