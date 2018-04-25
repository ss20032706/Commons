package shencai.commonsample.ui.fg1content;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import cc.shencai.commonlibrary.widgets.BaseActivity;
import cc.shencai.commonlibrary.widgets.TopFloatScrollView;
import shencai.commonsample.R;

import static shencai.commonsample.R.id.search_edit;


/**
 * Created by yss on 2017/9/13
 *
 * @version 1.0.0
 */
public class TopFloatActivity extends BaseActivity implements TopFloatScrollView.OnScrollListener {
	@Bind(R.id.rlayout)
	RelativeLayout rlayout;
	@Bind(R.id.search02)
	LinearLayout search02;
	@Bind(R.id.myScrollView)
	TopFloatScrollView myScrollView;
	@Bind(R.id.search01)
	LinearLayout search01;
	@Bind(search_edit)
	EditText searchEdit;

	private int searchLayoutTop;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_topfloat);
		ButterKnife.bind(this);
		initEvent();
	}

	@Override
	public void initEvent() {
		super.initEvent();
		myScrollView.setOnScrollListener(this);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			searchLayoutTop = rlayout.getBottom();//获取searchLayout的顶部位置
		}
	}

	//监听滚动Y值变化，通过addView和removeView来实现悬停效果
	@Override
	public void onScroll(int scrollY) {
		if (scrollY >= searchLayoutTop) {
			if (searchEdit.getParent() != search01) {
				search02.removeView(searchEdit);
				search01.addView(searchEdit);
			}
		} else {
			if (searchEdit.getParent() != search02) {
				search01.removeView(searchEdit);
				search02.addView(searchEdit);
			}
		}
	}
}
