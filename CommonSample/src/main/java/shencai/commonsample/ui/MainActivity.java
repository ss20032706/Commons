package shencai.commonsample.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cc.shencai.commonlibrary.adapter.ViewPagerAdapter;
import cc.shencai.commonlibrary.iml.UIInterface;
import cc.shencai.commonlibrary.utils.StatusBarUtils;
import cc.shencai.commonlibrary.widgets.BaseFragmentActivity;
import cc.shencai.commonlibrary.widgets.ForbidSlideViewPager;
import shencai.commonsample.R;

/**
 * Created by yss on 2017/9/14
 *
 * @version 1.0.0
 */
public class MainActivity extends BaseFragmentActivity implements UIInterface {
	@Bind(R.id.main_tabpager)
	ForbidSlideViewPager mTabPager;
	@Bind(R.id.my_page_message_divider)
	View myPageMessageDivider;
	@Bind(R.id.ivIcon1)
	ImageView ivIcon1;
	@Bind(R.id.tvText1)
	TextView tvText1;
	@Bind(R.id.rlTab1)
	RelativeLayout rlTab1;
	@Bind(R.id.ivIcon2)
	ImageView ivIcon2;
	@Bind(R.id.tvText2)
	TextView tvText2;
	@Bind(R.id.rlTab2)
	RelativeLayout rlTab2;
	@Bind(R.id.ivIcon3)
	ImageView ivIcon3;
	@Bind(R.id.tvText3)
	TextView tvText3;
	@Bind(R.id.rlTab3)
	RelativeLayout rlTab3;
	@Bind(R.id.ivIcon4)
	ImageView ivIcon4;
	@Bind(R.id.tvText4)
	TextView tvText4;
	@Bind(R.id.rlTab4)
	RelativeLayout rlTab4;


	private List<Fragment> views = new ArrayList<>();
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//绑定butterknife，这样就能注解布局里的控件
		ButterKnife.bind(this);

		//将该页面设置为全屏模式，即我们设置的布局覆盖了系统自带的statusbar
		StatusBarUtils.hideStatusBar(this);

//		//因为是全屏模式，所以用自己布局里的控件ivstatusbar来替代系统自带的statusbar，并将之高度设置为系统statusbar的高度
//		StatusBarUtils.setMyStatusBarHeight(this, ivStatusBar);

		//系统版本19以上的时候，就可以把状态栏设置成沉浸式，自动适应主题颜色来渐变
		StatusBarUtils.setShadowStatusBar(this);

		initData();//设置数据
		initView();//设置界面控件
		initEvent();//设置事件
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitApp();
		}
		return true;
	}

	@Override
	public void initData() {
		super.initData();

	}

	@Override
	public void initView() {
		super.initView();
		views.add(new TabFragment1());
		views.add(new TabFragment2());
		views.add(new TabFragment3());
		views.add(new TabFragment1());

		//设置预加载的fragment个数
		mTabPager.setOffscreenPageLimit(3);
		//填充ViewPager的数据适配器
		ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), views);
		mTabPager.setAdapter(viewPagerAdapter);
		mTabPager.setCurrentItem(0);
	}

	@Override
	public void initEvent() {
		super.initEvent();

		rlTab1.setOnClickListener(new MyOnClickListener(0));
		rlTab2.setOnClickListener(new MyOnClickListener(1));
		rlTab3.setOnClickListener(new MyOnClickListener(2));
		rlTab4.setOnClickListener(new MyOnClickListener(3));
	}

	/**
	 * 头标点击监听
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mTabPager.setCurrentItem(index);
			switch (v.getId()) {
				case R.id.rlTab1:
					ivIcon1.setImageResource(R.mipmap.fg1_press);
					ivIcon2.setImageResource(R.mipmap.fg2_normal);
					ivIcon3.setImageResource(R.mipmap.fg3_normal);
					ivIcon4.setImageResource(R.mipmap.fg4_normal);

					tvText1.setTextColor(Color.parseColor(getString(R.string.main_tab_text_blue)));
					tvText2.setTextColor(Color.parseColor(getString(R.string.global_text_gray_string)));
					tvText3.setTextColor(Color.parseColor(getString(R.string.global_text_gray_string)));
					tvText4.setTextColor(Color.parseColor(getString(R.string.global_text_gray_string)));
					break;
				case R.id.rlTab2:
					ivIcon1.setImageResource(R.mipmap.fg1_normal);
					ivIcon2.setImageResource(R.mipmap.fg2_press);
					ivIcon3.setImageResource(R.mipmap.fg3_normal);
					ivIcon4.setImageResource(R.mipmap.fg4_normal);

					tvText1.setTextColor(Color.parseColor(getString(R.string.global_text_gray_string)));
					tvText2.setTextColor(Color.parseColor(getString(R.string.main_tab_text_blue)));
					tvText3.setTextColor(Color.parseColor(getString(R.string.global_text_gray_string)));
					tvText4.setTextColor(Color.parseColor(getString(R.string.global_text_gray_string)));
					break;
				case R.id.rlTab3:
					ivIcon1.setImageResource(R.mipmap.fg1_normal);
					ivIcon2.setImageResource(R.mipmap.fg2_normal);
					ivIcon3.setImageResource(R.mipmap.fg3_press);
					ivIcon4.setImageResource(R.mipmap.fg4_normal);

					tvText1.setTextColor(Color.parseColor(getString(R.string.global_text_gray_string)));
					tvText2.setTextColor(Color.parseColor(getString(R.string.global_text_gray_string)));
					tvText3.setTextColor(Color.parseColor(getString(R.string.main_tab_text_blue)));
					tvText4.setTextColor(Color.parseColor(getString(R.string.global_text_gray_string)));
					break;
				case R.id.rlTab4:
					ivIcon1.setImageResource(R.mipmap.fg1_normal);
					ivIcon2.setImageResource(R.mipmap.fg2_normal);
					ivIcon3.setImageResource(R.mipmap.fg3_normal);
					ivIcon4.setImageResource(R.mipmap.fg4_press);

					tvText1.setTextColor(Color.parseColor(getString(R.string.global_text_gray_string)));
					tvText2.setTextColor(Color.parseColor(getString(R.string.global_text_gray_string)));
					tvText3.setTextColor(Color.parseColor(getString(R.string.global_text_gray_string)));
					tvText4.setTextColor(Color.parseColor(getString(R.string.main_tab_text_blue)));
					break;
			}
		}
	}
}
