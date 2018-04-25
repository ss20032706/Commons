package shencai.commonsample.ui.fg1content;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import cc.shencai.commonlibrary.utils.ActivityUtil;
import cc.shencai.commonlibrary.utils.ToastUtil;
import cc.shencai.commonlibrary.widgets.BaseActivity;
import cc.shencai.commonlibrary.widgets.DoubleListPopupWindow;
import shencai.commonsample.R;

import static cc.shencai.commonlibrary.widgets.DoubleListPopupWindow.BUNDLE_MAIN;
import static cc.shencai.commonlibrary.widgets.DoubleListPopupWindow.BUNDLE_MORE;

/**
 * Created by yss on 2017/9/14
 *
 * @version 1.0.0
 */
public class DoubleListActivity extends BaseActivity implements View.OnClickListener {


	@Bind(R.id.iv_back)
	ImageView ivBack;
	@Bind(R.id.et_search)
	EditText etSearch;
	@Bind(R.id.bt_search_clear)
	ImageView btSearchClear;
	@Bind(R.id.ll_search)
	LinearLayout llSearch;
	@Bind(R.id.v_line)
	View vLine;
	@Bind(R.id.tvConfine1)
	TextView tvConfine1;
	@Bind(R.id.ivConfine1)
	ImageView ivConfine1;
	@Bind(R.id.llDouble)
	LinearLayout llDouble;

	private DoubleListPopupWindow<Object> doubleListPopupWindow;
	private InputMethodManager inputMethodManager;
	private HashMap<String, ArrayList<String>> hashMap = new HashMap<>();//不限行业选择控件的数据


	/**
	 * 为防止handler导致的内存泄漏，可以用弱引用的方式来解决这个问题
	 */
	private final ConfineHandler mHandler = new ConfineHandler(this);

	private static class ConfineHandler extends Handler {
		private WeakReference<DoubleListActivity> mActivity;

		public ConfineHandler(DoubleListActivity instance) {
			mActivity = new WeakReference<DoubleListActivity>(instance);
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
		Bundle industryData = msg.getData();
		String mainString = industryData.getString(BUNDLE_MAIN);
		String moreString = industryData.getString(BUNDLE_MORE);
		etSearch.setText(mainString + moreString);
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doublelist);
		ButterKnife.bind(this);

		initData();
		initView();
		initEvent();
	}

	@Override
	public void initData() {
		super.initData();
		ArrayList<String> moreList = new ArrayList<>();
		ArrayList<String> moreList1 = new ArrayList<>();
		moreList1.add("吴中区");
		moreList1.add("相城区");
		moreList1.add("姑苏区");
		moreList1.add("工业园区");
		ArrayList<String> moreList2 = new ArrayList<>();
		moreList2.add("静安区");
		moreList2.add("闸北区");
		moreList2.add("闵行区");
		moreList2.add("普陀区");
		ArrayList<String> moreList3 = new ArrayList<>();
		moreList3.add("台北市");
		moreList3.add("桃园县");
		moreList3.add("金门县");
		moreList3.add("高雄市");
		ArrayList<String> moreList4 = new ArrayList<>();
		moreList4.add("东京");
		moreList4.add("大阪");
		moreList4.add("京都");
		moreList4.add("神户");
		hashMap.put("上海", moreList2);
		hashMap.put("苏州", moreList1);
		hashMap.put("台湾", moreList3);
		hashMap.put("日本", moreList4);

		inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
	}

	@Override
	public void initView() {
		super.initView();
		doubleListPopupWindow = new DoubleListPopupWindow(DoubleListActivity.this, hashMap, mHandler);
		doubleListPopupWindow.setAnimationStyle(R.style.mypopwindow_left_anim_style);
	}

	@Override
	public void initEvent() {
		super.initEvent();
		ivBack.setOnClickListener(this);
		btSearchClear.setOnClickListener(this);
		llDouble.setOnClickListener(this);

		etSearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (!"".equals(etSearch.getText().toString())) {
					btSearchClear.setVisibility(View.VISIBLE);
				} else {
					btSearchClear.setVisibility(View.GONE);
				}
			}
		});
		etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				//当actionId == XX_SEND 或者 XX_DONE时都触发
				//或者event.getKeyCode == ENTER 且 event.getAction == ACTION_DOWN时也触发
				//注意，这是一定要判断event != null。因为在某些输入法上会返回null。
				if (actionId == EditorInfo.IME_ACTION_SEND
						|| actionId == EditorInfo.IME_ACTION_DONE
						|| (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
					//处理事件
					String searchText = etSearch.getText().toString().trim();
					//当点击事件执行到ACTION_UP的时候才发起搜索
					// 先隐藏键盘
					((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
							.hideSoftInputFromWindow(DoubleListActivity.this.getCurrentFocus()
									.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

					ToastUtil.showShort(DoubleListActivity.this, "搜索字段：" + searchText);
				}
				return true;
			}
		});
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.iv_back:
				ActivityUtil.finishActivity(DoubleListActivity.this);
				break;

			case R.id.llDouble:
				if (doubleListPopupWindow != null) {
					hideKeyboard();
					doubleListPopupWindow.show(llDouble, ivConfine1);
				}
				break;
			case R.id.bt_search_clear:
				etSearch.setText("");
				break;
		}
	}


	/**
	 * 判断当前虚拟键盘是显示状态，就隐藏虚拟键盘
	 *
	 * @return
	 */
	private boolean hideKeyboard() {
		if (inputMethodManager.isActive(etSearch)) {//因为是在fragment下，所以用了getView()获取view，也可以用findViewById（）来获取父控件
			ivBack.requestFocus();//使其它view获取焦点.这里因为是在fragment下,所以便用了getView(),可以指定任意其它view
			inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			return true;
		}
		return false;
	}

}
