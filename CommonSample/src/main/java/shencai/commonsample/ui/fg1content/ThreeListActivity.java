package shencai.commonsample.ui.fg1content;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cc.shencai.commonlibrary.beans.ThreeListBean;
import cc.shencai.commonlibrary.utils.ActivityUtil;
import cc.shencai.commonlibrary.widgets.BaseActivity;
import cc.shencai.commonlibrary.widgets.ThreeListPopupWindow;
import shencai.commonsample.R;
import shencai.commonsample.application.MyApplication;

/**
 * Created by yss on 2017/9/15
 *
 * @version 1.0.0
 */
public class ThreeListActivity extends BaseActivity implements View.OnClickListener {
	@Bind(R.id.tvThreeList)
	TextView tvThreeList;
	@Bind(R.id.ivArrow)
	ImageView ivArrow;
	@Bind(R.id.rlThreeList)
	RelativeLayout rlThreeList;

	public ThreeListPopupWindow threeListPopupWindow;
	private ThreeListBean threeListBean;


	/**
	 * 为防止handler导致的内存泄漏，可以用弱引用的方式来解决这个问题
	 */
	private final ConfineHandler mHandler = new ConfineHandler(this);
	private static class ConfineHandler extends Handler {
		private WeakReference<ThreeListActivity> mActivity;

		public ConfineHandler(ThreeListActivity instance) {
			mActivity = new WeakReference<ThreeListActivity>(instance);
		}

		@Override
		public void handleMessage(Message msg) {
			if (null != mActivity.get()) {
				mActivity.get().handlerTodo(msg);
			}
		}
	}

	/*
	处理handler的回调函数
	 */
	public void handlerTodo(Message msg) {
//		Bundle industryData = msg.getData();
//		String mainString = industryData.getString(BUNDLE_MAIN);
//		String moreString = industryData.getString(BUNDLE_MORE);
//		etSearch.setText(mainString + moreString);
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_threelist);
		ButterKnife.bind(this);

		initData();
		initView();
		initEvent();
	}




	@Override
	public void initView() {
		super.initView();
		threeListPopupWindow = new ThreeListPopupWindow(MyApplication.getContext(),threeListBean,mHandler);
		threeListPopupWindow.setAnimationStyle(R.style.mypopwindow_right_anim_style);
	}

	@Override
	public void initEvent() {
		super.initEvent();
		rlThreeList.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.iv_back:
				ActivityUtil.finishActivity(ThreeListActivity.this);
				break;

			case R.id.rlThreeList:
				if (threeListPopupWindow != null) {
					threeListPopupWindow.show(rlThreeList, ivArrow);
				}
				break;
		}
	}

	@Override
	public void initData() {
		super.initData();

		threeListBean = new ThreeListBean();
		threeListBean.setTitle1("省");
		threeListBean.setTitle2("市");
		threeListBean.setTitle3("区");

		List<ThreeListBean.FirstBean> firstBeens = new ArrayList<>();
		ThreeListBean.FirstBean firstbean = new ThreeListBean.FirstBean();
		firstbean.setFirstName("浙江省");

		List<ThreeListBean.SecondBean> secondBeens = new ArrayList<>();
		ThreeListBean.SecondBean secondBean = new ThreeListBean.SecondBean();
		secondBean.setSecondName("杭州市");

		List<ThreeListBean.ThirdBean> thirdBeans = new ArrayList<>();
		ThreeListBean.ThirdBean thirdBean = new ThreeListBean.ThirdBean();
		thirdBean.setThirdName("拱墅区");
		ThreeListBean.ThirdBean thirdBean1 = new ThreeListBean.ThirdBean();
		thirdBean1.setThirdName("西湖区");
		ThreeListBean.ThirdBean thirdBean2 = new ThreeListBean.ThirdBean();
		thirdBean2.setThirdName("上城区");
		ThreeListBean.ThirdBean thirdBean3 = new ThreeListBean.ThirdBean();
		thirdBean3.setThirdName("下城区");
		thirdBeans.add(thirdBean);
		thirdBeans.add(thirdBean1);
		thirdBeans.add(thirdBean2);
		thirdBeans.add(thirdBean3);
		secondBean.setThirdBeans(thirdBeans);

		ThreeListBean.SecondBean secondBean1 = new ThreeListBean.SecondBean();
		secondBean1.setSecondName("宁波市");
		List<ThreeListBean.ThirdBean> thirdBeans1 = new ArrayList<>();
		ThreeListBean.ThirdBean thirdBean10 = new ThreeListBean.ThirdBean();
		thirdBean10.setThirdName("海曙区");
		ThreeListBean.ThirdBean thirdBean11 = new ThreeListBean.ThirdBean();
		thirdBean11.setThirdName("镇海区");
		ThreeListBean.ThirdBean thirdBean12 = new ThreeListBean.ThirdBean();
		thirdBean12.setThirdName("北仑区");
		ThreeListBean.ThirdBean thirdBean13 = new ThreeListBean.ThirdBean();
		thirdBean13.setThirdName("象山区");
		thirdBeans1.add(thirdBean10);
		thirdBeans1.add(thirdBean11);
		thirdBeans1.add(thirdBean12);
		thirdBeans1.add(thirdBean13);
		secondBean1.setThirdBeans(thirdBeans1);

		secondBeens.add(secondBean);
		secondBeens.add(secondBean1);

		firstbean.setSecondBeens(secondBeens);
		firstBeens.add(firstbean);


		ThreeListBean.FirstBean firstbean1 = new ThreeListBean.FirstBean();
		firstbean1.setFirstName("江苏省");

		List<ThreeListBean.SecondBean> secondBeens2 = new ArrayList<>();
		ThreeListBean.SecondBean secondBean2 = new ThreeListBean.SecondBean();
		secondBean2.setSecondName("南京市");

		List<ThreeListBean.ThirdBean> thirdBeans2 = new ArrayList<>();
		ThreeListBean.ThirdBean thirdBean20 = new ThreeListBean.ThirdBean();
		thirdBean20.setThirdName("玄武区");
		ThreeListBean.ThirdBean thirdBean21 = new ThreeListBean.ThirdBean();
		thirdBean21.setThirdName("溧水县");
		ThreeListBean.ThirdBean thirdBean22 = new ThreeListBean.ThirdBean();
		thirdBean22.setThirdName("江宁区");
		ThreeListBean.ThirdBean thirdBean23 = new ThreeListBean.ThirdBean();
		thirdBean23.setThirdName("高淳县");
		thirdBeans2.add(thirdBean20);
		thirdBeans2.add(thirdBean21);
		thirdBeans2.add(thirdBean22);
		thirdBeans2.add(thirdBean23);
		secondBean2.setThirdBeans(thirdBeans2);

		ThreeListBean.SecondBean secondBean3 = new ThreeListBean.SecondBean();
		secondBean3.setSecondName("苏州市");
		List<ThreeListBean.ThirdBean> thirdBeans3 = new ArrayList<>();
		ThreeListBean.ThirdBean thirdBean30 = new ThreeListBean.ThirdBean();
		thirdBean30.setThirdName("平江区");
		ThreeListBean.ThirdBean thirdBean31 = new ThreeListBean.ThirdBean();
		thirdBean31.setThirdName("相城区");
		ThreeListBean.ThirdBean thirdBean32 = new ThreeListBean.ThirdBean();
		thirdBean32.setThirdName("常熟市");
		ThreeListBean.ThirdBean thirdBean33 = new ThreeListBean.ThirdBean();
		thirdBean33.setThirdName("吴中区");
		thirdBeans3.add(thirdBean30);
		thirdBeans3.add(thirdBean31);
		thirdBeans3.add(thirdBean32);
		thirdBeans3.add(thirdBean33);
		secondBean3.setThirdBeans(thirdBeans3);

		secondBeens2.add(secondBean2);
		secondBeens2.add(secondBean3);
		firstbean1.setSecondBeens(secondBeens2);

		firstBeens.add(firstbean1);

		threeListBean.setFirstBeens(firstBeens);
	}
}
