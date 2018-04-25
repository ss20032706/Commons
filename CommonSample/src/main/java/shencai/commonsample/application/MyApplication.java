package shencai.commonsample.application;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.xutils.BuildConfig;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import shencai.commonsample.R;
import shencai.commonsample.ui.fg1content.welcomebanner.Engine;
/**
 * Created by yss on 2017/9/13
 *
 * @version 1.0.0
 */
public class MyApplication extends Application {
	private Engine mEngine;

	@SuppressLint("StaticFieldLeak")
	private static Context context;
	@SuppressLint("StaticFieldLeak")
	public static MyApplication instance ;

	private int detailHeight;

	public int getDetailHeight() {
		return detailHeight;
	}

	public void setDetailHeight(int detailHeight) {
		this.detailHeight = detailHeight;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		instance = this;
		context = getApplicationContext();

		//网络请求OKgo要用到
		initOkGo();


		/*
		 * fg2content里面图片选择相关
		 */
		ImageLoaderConfiguration config = ImageLoaderConfiguration.createDefault(this);
		ImageLoader.getInstance().init(config);     //UniversalImageLoader初始化
		x.Ext.init(this);                           //xUtils3初始化

		/*
		引导页（welcomebanner）相关
		 */
		mEngine = new Retrofit.Builder()
				.baseUrl("http://7xk9dj.com1.z0.glb.clouddn.com/banner/api/")
				.addConverterFactory(GsonConverterFactory.create())
				.build().create(Engine.class);
		Fresco.initialize(this);

		/**
		 * xutils相关
		 */
		x.Ext.init(this);
		x.Ext.setDebug(BuildConfig.DEBUG); // 开启debug会影响性能

		// 全局默认信任所有https域名 或 仅添加信任的https域名
		// 使用RequestParams#setHostnameVerifier(...)方法可设置单次请求的域名校验
		x.Ext.setDefaultHostnameVerifier(new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		});

	}

	public Engine getEngine() {
		return mEngine;
	}

	public static DisplayImageOptions imageLoaderOptions = new DisplayImageOptions.Builder()//
			.showImageOnLoading(R.drawable.ic_default_image)         //设置图片在下载期间显示的图片
			.showImageForEmptyUri(R.drawable.ic_default_image)       //设置图片Uri为空或是错误的时候显示的图片
			.showImageOnFail(R.drawable.ic_default_image)            //设置图片加载/解码过程中错误时候显示的图片
			.cacheInMemory(true)                                //设置下载的图片是否缓存在内存中
			.cacheOnDisk(true)                                  //设置下载的图片是否缓存在SD卡中
			.build();                                           //构建完成

	public static ImageOptions xUtilsOptions = new ImageOptions.Builder()//
			.setIgnoreGif(false)                                //是否忽略GIF格式的图片
			.setImageScaleType(ImageView.ScaleType.FIT_CENTER)  //缩放模式
			.setLoadingDrawableId(R.drawable.ic_default_image)       //下载中显示的图片
			.setFailureDrawableId(R.drawable.ic_default_image)       //下载失败显示的图片
			.build();                                           //得到ImageOptions对象

	/**
	 * 获取BaseApplication实例
	 * @return BaseApplication
	 */
	public static MyApplication getApplication(){
		if (instance != null) return instance;
		throw new NullPointerException("u should init first");
	}

	/**
	 * 获取ApplicationContext
	 *
	 * @return ApplicationContext
	 */
	public static Context getContext() {
		if (context != null) return context;
		throw new NullPointerException("u should init first");
	}

	private void initOkGo() {
		//---------这里给出的是示例代码,告诉你可以这么传,实际使用的时候,根据需要传,不需要就不传-------------//
		HttpHeaders headers = new HttpHeaders();
		headers.put("commonHeaderKey1", "commonHeaderValue1");    //header不支持中文，不允许有特殊字符
		headers.put("commonHeaderKey2", "commonHeaderValue2");
		HttpParams params = new HttpParams();
		params.put("commonParamsKey1", "commonParamsValue1");     //param支持中文,直接传,不要自己编码
		params.put("commonParamsKey2", "这里支持中文参数");
		//----------------------------------------------------------------------------------------//

		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		//log相关
		HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
		loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
		loggingInterceptor.setColorLevel(Level.INFO);                               //log颜色级别，决定了log在控制台显示的颜色
		builder.addInterceptor(loggingInterceptor);                                 //添加OkGo默认debug日志
		//第三方的开源库，使用通知显示当前请求的log，不过在做文件下载的时候，这个库好像有问题，对文件判断不准确
		//builder.addInterceptor(new ChuckInterceptor(this));

		//超时时间设置，默认60秒
		builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);      //全局的读取超时时间
		builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);     //全局的写入超时时间
		builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);   //全局的连接超时时间

		//自动管理cookie（或者叫session的保持），以下几种任选其一就行
		//builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));            //使用sp保持cookie，如果cookie不过期，则一直有效
		builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));              //使用数据库保持cookie，如果cookie不过期，则一直有效
		//builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));            //使用内存保持cookie，app退出后，cookie消失

		//https相关设置，以下几种方案根据需要自己设置
		//方法一：信任所有证书,不安全有风险
		HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
		//方法二：自定义信任规则，校验服务端证书
		HttpsUtils.SSLParams sslParams2 = HttpsUtils.getSslSocketFactory(new SafeTrustManager());
		//方法三：使用预埋证书，校验服务端证书（自签名证书）
		//HttpsUtils.SSLParams sslParams3 = HttpsUtils.getSslSocketFactory(getAssets().open("srca.cer"));
		//方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
		//HttpsUtils.SSLParams sslParams4 = HttpsUtils.getSslSocketFactory(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"));
		builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);
		//配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
		builder.hostnameVerifier(new SafeHostnameVerifier());

		// 其他统一的配置
		// 详细说明看GitHub文档：https://github.com/jeasonlzy/
		OkGo.getInstance().init(this)                           //必须调用初始化
				.setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置会使用默认的
				.setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
				.setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
				.setRetryCount(3)                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
				.addCommonHeaders(headers)                      //全局公共头
				.addCommonParams(params);                       //全局公共参数
	}

	/**
	 * 这里只是我谁便写的认证规则，具体每个业务是否需要验证，以及验证规则是什么，请与服务端或者leader确定
	 * 这里只是我谁便写的认证规则，具体每个业务是否需要验证，以及验证规则是什么，请与服务端或者leader确定
	 * 这里只是我谁便写的认证规则，具体每个业务是否需要验证，以及验证规则是什么，请与服务端或者leader确定
	 * 重要的事情说三遍，以下代码不要直接使用
	 */
	private class SafeTrustManager implements X509TrustManager {
		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			try {
				for (X509Certificate certificate : chain) {
					certificate.checkValidity(); //检查证书是否过期，签名是否通过等
				}
			} catch (Exception e) {
				throw new CertificateException(e);
			}
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[0];
		}
	}

	/**
	 * 这里只是我谁便写的认证规则，具体每个业务是否需要验证，以及验证规则是什么，请与服务端或者leader确定
	 * 这里只是我谁便写的认证规则，具体每个业务是否需要验证，以及验证规则是什么，请与服务端或者leader确定
	 * 这里只是我谁便写的认证规则，具体每个业务是否需要验证，以及验证规则是什么，请与服务端或者leader确定
	 * 重要的事情说三遍，以下代码不要直接使用
	 */
	private class SafeHostnameVerifier implements HostnameVerifier {
		@Override
		public boolean verify(String hostname, SSLSession session) {
			//验证主机名是否匹配
			//return hostname.equals("server.jeasonlzy.com");
			return true;
		}
	}


}
