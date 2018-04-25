package shencai.commonsample.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cc.shencai.commonlibrary.utils.ActivityUtil;
import cc.shencai.commonlibrary.utils.StatusBarUtils;
import cc.shencai.commonlibrary.widgets.BaseActivity;
import shencai.commonsample.R;
import shencai.commonsample.adapter.LvMainAdapter;
import shencai.commonsample.beans.FunctionBean;
import shencai.commonsample.ui.fg1content.BounceScrollActivity;
import shencai.commonsample.ui.fg1content.DoubleListActivity;
import shencai.commonsample.ui.fg1content.PbLoadingActivity;
import shencai.commonsample.ui.fg1content.TopFloatActivity;

/**
 * Created by yss on 2017/9/13
 *
 * @version 1.0.0
 */
public class UIListActivity extends BaseActivity {
	@Bind(R.id.lvMain)
	ListView lvMain;
	@Bind(R.id.ivStatusBar)
	ImageView ivStatusBar;
	@Bind(R.id.ivBack)
	ImageView ivBack;
	@Bind(R.id.ivClose)
	ImageView ivClose;
	@Bind(R.id.ivMore)
	ImageView ivMore;
	@Bind(R.id.tvTitleTop)
	TextView tvTitleTop;
	@Bind(R.id.rlTitleBar)
	RelativeLayout rlTitleBar;

	private List<FunctionBean> functionbeans = new ArrayList<>();
	private Context mContext;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uilist);
		//绑定butterknife，这样就能注解布局里的控件
		ButterKnife.bind(this);

		//将该页面设置为全屏模式，即我们设置的布局覆盖了系统自带的statusbar
		StatusBarUtils.hideStatusBar(this);

		//因为是全屏模式，所以用自己布局里的控件ivstatusbar来替代系统自带的statusbar，并将之高度设置为系统statusbar的高度
		StatusBarUtils.setMyStatusBarHeight(this, ivStatusBar);

		initData();//设置数据
		initView();//设置界面控件
		initEvent();//设置事件
	}

	@Override
	public void initData() {
		super.initData();
		mContext = UIListActivity.this;
		initFunctionBeans();

	}

	@Override
	public void initView() {
		super.initView();
		//为该界面的顶部title赋值
		tvTitleTop.setVisibility(View.VISIBLE);
		tvTitleTop.setText("功能演示");

		//初始化lvMain
		lvMain.setAdapter(new LvMainAdapter(functionbeans, mContext));

	}

	@Override
	public void initEvent() {
		super.initEvent();
		lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				ActivityUtil.gotoActivity(UIListActivity.this,functionbeans.get(position).getClassName());
			}
		});
	}

	/**
	 * 初始化lvMain的数据functionbeans
	 */
	private void initFunctionBeans() {
		HashMap dataMap = new HashMap();
		dataMap.put("指定控件上滑悬停", TopFloatActivity.class);
		dataMap.put("上下回弹效果的ScrollView", BounceScrollActivity.class);
		dataMap.put("加载数据时加载动画", PbLoadingActivity.class);
		dataMap.put("双选控件", DoubleListActivity.class);

		Iterator iter = dataMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			Class val = (Class) entry.getValue();
			FunctionBean bean = new FunctionBean();
			bean.setName(key);
			bean.setClassName(val);
			functionbeans.add(bean);
		}
	}


}
