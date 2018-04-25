package shencai.commonsample.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cc.shencai.commonlibrary.utils.ActivityUtil;
import cc.shencai.commonlibrary.utils.StatusBarUtils;
import cc.shencai.commonlibrary.widgets.BaseFragment;
import shencai.commonsample.R;
import shencai.commonsample.adapter.LvMainAdapter;
import shencai.commonsample.beans.FunctionBean;
import shencai.commonsample.ui.fg1content.AutoDisplayCenterActivity;
import shencai.commonsample.ui.fg1content.AutoTextActivity;
import shencai.commonsample.ui.fg1content.BounceScrollActivity;
import shencai.commonsample.ui.fg1content.DoubleListActivity;
import shencai.commonsample.ui.fg1content.ExpandableTvActivity;
import shencai.commonsample.ui.fg1content.IOSdialogActivity;
import shencai.commonsample.ui.fg1content.ImageZoomActivity;
import shencai.commonsample.ui.fg1content.LogOutActivity;
import shencai.commonsample.ui.fg1content.PbLoadingActivity;
import shencai.commonsample.ui.fg1content.PullOutViewAcitivity;
import shencai.commonsample.ui.fg1content.RTextViewActivity;
import shencai.commonsample.ui.fg1content.RoundedImageViewActivity;
import shencai.commonsample.ui.fg1content.SwitchButtonActivity;
import shencai.commonsample.ui.fg1content.ThreeListActivity;
import shencai.commonsample.ui.fg1content.ToastActivity;
import shencai.commonsample.ui.fg1content.TopFloatActivity;
import shencai.commonsample.ui.fg1content.dropdownmenu.DropDownActivity;
import shencai.commonsample.ui.fg1content.pullzoomview.PullMainActivity;
import shencai.commonsample.ui.fg1content.welcomebanner.GuideActivity;

/**
 * Created by yss on 2017/9/14
 *
 * @version 1.0.0
 */
public class TabFragment1 extends BaseFragment {
	@Bind(R.id.ivStatusBar)
	ImageView ivStatusBar;
	@Bind(R.id.tvTitleTop)
	TextView tvTitleTop;
	@Bind(R.id.lvFag)
	ListView lvFag1;
	private View view;

	private List<FunctionBean> functionbeans = new ArrayList<>();

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_tab, container, false);
		ButterKnife.bind(this, view);

		//因为是全屏模式，所以用自己布局里的控件ivstatusbar来替代系统自带的statusbar，并将之高度设置为系统statusbar的高度
		StatusBarUtils.setMyStatusBarHeight(getActivity(), ivStatusBar);

		initData();//设置数据
		initView();//设置界面控件
		initEvent();//设置事件

		return view;
	}

	@Override
	public void initData() {
		super.initData();
		initFunctionBeans();
	}

	@Override
	public void initView() {
		super.initView();
		//为该界面的顶部title赋值
		tvTitleTop.setVisibility(View.VISIBLE);
		tvTitleTop.setText("UI相关");

		//初始化lvMain
		lvFag1.setAdapter(new LvMainAdapter(functionbeans, getActivity()));

	}

	@Override
	public void initEvent() {
		super.initEvent();
		lvFag1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				ActivityUtil.gotoActivity(getActivity(),functionbeans.get(position).getClassName());
			}
		});
	}

	/**
	 * 初始化lvMain的数据functionbeans
	 */
	private void initFunctionBeans() {
		LinkedHashMap dataMap = new LinkedHashMap();
		dataMap.put("指定控件上滑悬停", TopFloatActivity.class);
		dataMap.put("上下回弹效果的ScrollView", BounceScrollActivity.class);
		dataMap.put("加载数据时加载动画", PbLoadingActivity.class);
		dataMap.put("仿IOS效果的各种dialog", IOSdialogActivity.class);
		dataMap.put("单选下拉选择控件", DropDownActivity.class);
		dataMap.put("双选联动控件", DoubleListActivity.class);
		dataMap.put("三选联动控件", ThreeListActivity.class);
		dataMap.put("各种效果的textview", RTextViewActivity.class);
		dataMap.put("下拉头部放大的布局效果", PullMainActivity.class);
		dataMap.put("引导页banner", GuideActivity.class);
		dataMap.put("PhotoView图片浏览缩放控件", ImageZoomActivity.class);
		dataMap.put("自动根据给定的界限缩小字体", AutoTextActivity.class);
		dataMap.put("状态切换的Button,类似iOS", SwitchButtonActivity.class);
		dataMap.put("自动中间对齐的标签布局", AutoDisplayCenterActivity.class);
		dataMap.put("有抽屉效果的Textview", ExpandableTvActivity.class);
		dataMap.put("有抽屉效果的布局", PullOutViewAcitivity.class);
		dataMap.put("圆形的图片控件", RoundedImageViewActivity.class);
		dataMap.put("各种Toast", ToastActivity.class);
		dataMap.put("退出登录的对话框", LogOutActivity.class);

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
