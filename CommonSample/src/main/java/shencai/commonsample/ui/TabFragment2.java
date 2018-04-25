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
import shencai.commonsample.ui.fg2content.NCalendarActivity;
import shencai.commonsample.ui.fg2content.imagepickerdemo.ImagePickerActivity;
import shencai.commonsample.ui.fg2content.permissions4m.PermissionActivity;

/**
 * Created by yss on 2017/9/14
 *
 * @version 1.0.0
 */
public class TabFragment2 extends BaseFragment {
	@Bind(R.id.ivStatusBar)
	ImageView ivStatusBar;
	@Bind(R.id.tvTitleTop)
	TextView tvTitleTop;
	@Bind(R.id.lvFag)
	ListView lvFag2;
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
		tvTitleTop.setText("功能");

		//初始化lvMain
		lvFag2.setAdapter(new LvMainAdapter(functionbeans, getActivity()));

	}

	@Override
	public void initEvent() {
		super.initEvent();
		lvFag2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
		dataMap.put("permission请求", PermissionActivity.class);
		dataMap.put("仿微信图片选择", ImagePickerActivity.class);
		dataMap.put("仿小米日历", NCalendarActivity.class);

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
