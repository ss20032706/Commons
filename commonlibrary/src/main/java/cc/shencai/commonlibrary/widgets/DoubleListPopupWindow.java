package cc.shencai.commonlibrary.widgets;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import cc.shencai.commonlibrary.R;
import cc.shencai.commonlibrary.adapter.ListMainAdapter;
import cc.shencai.commonlibrary.adapter.ListMoreAdapter;
import cc.shencai.commonlibrary.utils.DisplayUtil;

/**
 * 自定义PopupWindow  主要用来显示ListView
 */
public class DoubleListPopupWindow<T> extends PopupWindow {
	private LayoutInflater inflater;
	private ListView lvMainList;
	private HashMap<String, ArrayList<String>> map;
	private Context ctx;
	private ImageView arrow;
	private Animation selectCloseAnim;
	private Animation selectOpenAnim;
	private Animation animClockwiseOpen;    // 顺时针开启
	private Animation animClockwiseClose;    //顺时针关闭
	private Animation animAntiClockwiseopen;//逆时针开启
	private Animation animAntiClockwiseClose; //逆时针关闭
	private ListView lvMoreList;
	private ListMoreAdapter moreAdapter;
	private ArrayList<String> keyList;
	private ArrayList<String> moreAdapterData;
	private String selectedMoreListItemText;
	private String selectedMainListItemText;
	private Handler confineHandler;
	private int msgWhat;

	private boolean firstDissmiss = false;//选择第一个的时候，是否关闭popwindow
	Bundle bundle = new Bundle();

	public static final String BUNDLE_MAIN = "BUNDLE_MAIN";
	public static final String BUNDLE_MORE = "BUNDLE_MORE";
	/**
	 * @param context        上下文
	 * @param map            map，键为一级菜单，值为二级菜单
	 * @param confineHandler
	 */
	public DoubleListPopupWindow(Context context, HashMap<String, ArrayList<String>> map, Handler confineHandler) {
		super(context);
		ctx = context;
		this.map = map;
		this.confineHandler = confineHandler;
		inflater = LayoutInflater.from(context);
		init();
	}

	public boolean isFirstDissmiss() {
		return firstDissmiss;
	}

	public void setFirstDissmiss(boolean firstDissmiss) {
		this.firstDissmiss = firstDissmiss;
	}

	public void setConfineHandler(Handler confineHandler) {
		this.confineHandler = confineHandler;
	}

	public void setDatas(HashMap<String, ArrayList<String>> map) {
		this.map = map;
	}

	private void init() {
		View view = inflater.inflate(R.layout.double_list_layout, null);
		setContentView(view);
		setWidth(LayoutParams.WRAP_CONTENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		setFocusable(true);
		ColorDrawable dw = new ColorDrawable(0x00FF00);
		setBackgroundDrawable(dw);

		bundle.putString(BUNDLE_MAIN, "");
		bundle.putString(BUNDLE_MORE, "");

		lvMainList = (ListView) view.findViewById(R.id.lv_mainlist);

		keyList = new ArrayList<>();
		Set<String> keySet = map.keySet();
		for (String str : keySet) {
			keyList.add(str);
		}

		lvMoreList = (ListView) view.findViewById(R.id.lv_morelist);
		final ListMainAdapter mainAdapter = new ListMainAdapter(ctx, keyList);
		lvMainList.setAdapter(mainAdapter);
		if (TextUtils.isEmpty(selectedMainListItemText)) {
			selectedMainListItemText = keyList.get(0);
			mainAdapter.setSelectItem(selectedMainListItemText);
			bundle.putString(BUNDLE_MAIN, selectedMainListItemText);
		}

		lvMainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				selectedMainListItemText = keyList.get(position);
				moreAdapterData = map.get(selectedMainListItemText);
				initMoreAdapter(moreAdapterData);
				mainAdapter.setSelectItem(selectedMainListItemText);
				mainAdapter.notifyDataSetInvalidated();
				bundle.putString(BUNDLE_MAIN, selectedMainListItemText);
				if (firstDissmiss && position == 0) {
					Message msg = new Message();
					msg.what = msgWhat;
					bundle.putString(BUNDLE_MAIN, selectedMainListItemText);
					msg.setData(bundle);
					confineHandler.sendMessage(msg);
					dismiss();
				}

			}
		});

		moreAdapterData = map.get(keyList.get(0));
		initMoreAdapter(moreAdapterData);

		lvMoreList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				selectedMoreListItemText = moreAdapterData.get(position);
				moreAdapter.setSelectItem(selectedMoreListItemText);//用文字内容来设置选中，尽量保证唯一性
				moreAdapter.notifyDataSetInvalidated();

				Message msg = new Message();
				msg.what = msgWhat;
				bundle.putString(BUNDLE_MORE, selectedMoreListItemText);
				msg.setData(bundle);
				confineHandler.sendMessage(msg);
				confineHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
										dismiss();
					}
				},100);

			}
		});

		initAnim();
	}

	private void initMoreAdapter(ArrayList<String> list) {
		moreAdapter = new ListMoreAdapter(ctx, list);
		lvMoreList.setAdapter(moreAdapter);
		moreAdapter.notifyDataSetChanged();
	}

	private void initAnim() {
		animClockwiseOpen = AnimationUtils.loadAnimation(ctx, R.anim.icon_clockwise_open);
		animClockwiseClose = AnimationUtils.loadAnimation(ctx, R.anim.icon_clockwise_close);
		animAntiClockwiseopen = AnimationUtils.loadAnimation(ctx, R.anim.icon_anti_clockwise_open);
		animAntiClockwiseClose = AnimationUtils.loadAnimation(ctx, R.anim.icon_anti_clockwise_close);
	}

	/**
	 * 点击textView展开popupWIndow时调用此方法
	 * 默认距离父控件Y方向偏移4dp
	 *
	 * @param v 父控件
	 */
	public void show(View v) {
		show(v, 0, 1);
	}

	/**
	 * 添加支持处理箭头180度旋转
	 *
	 * @param v
	 * @param arrow
	 */
	public void show(View v, ImageView arrow) {
		show(v, 0, 1, arrow);
	}

	/**
	 * @param v 父控件
	 * @param x x偏移量，值为dp
	 * @param y y偏移量，值为dp
	 */
	public void show(View v, float x, float y) {
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);

		int height = wm.getDefaultDisplay().getHeight();
		setHeight(height - location[1] - v.getHeight() - 1);

		showAsDropDown(v, DisplayUtil.dp2px(ctx, x), DisplayUtil.dp2px(ctx, y));
	}

	/**
	 *
	 * @param v 父控件
	 * @param x x偏移量，值为dp
	 * @param y y偏移量，值为dp
	 * @param arrow
	 */
	public void show(View v, float x, float y, ImageView arrow) {
		this.arrow = arrow;
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);

		int height = wm.getDefaultDisplay().getHeight();

		if ((location[1] + getHeight()) > height) {
			showAtLocation(v,
					Gravity.NO_GRAVITY,
					location[0],//x坐标位置
					location[1] - getHeight() - DisplayUtil.dp2px(ctx, y));//Y坐标位置
			arrow.startAnimation(animAntiClockwiseopen);
			selectCloseAnim = animAntiClockwiseClose;
		} else {
			showAsDropDown(v, DisplayUtil.dp2px(ctx, x), DisplayUtil.dp2px(ctx, y));
			arrow.startAnimation(animClockwiseOpen);
			selectCloseAnim = animClockwiseClose;
		}
		setOnDismissListener(dismissListener);
	}


	/**
	 * 设置PopupWindow的宽度和高度
	 *
	 * @param v 依赖的父控件
	 */
	private void setPopupWindowWidthAndHeight(Context ctx, View v) {
		WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
		int height = wm.getDefaultDisplay().getHeight();
		int count = moreAdapterData.size() - 1;
		int dp = count * 45;
		int px = DisplayUtil.dp2px(ctx, dp);
		if (px > height * 0.7f) {
			int i = (int) DisplayUtil.px2dp(ctx, (height * 0.5f));
			this.setHeight(height * 7 / 10);
		} else {
			this.setHeight(px);
		}
	}

	/**
	 * 监听popupwindow取消
	 */
	private OnDismissListener dismissListener = new OnDismissListener() {
		@Override
		public void onDismiss() {
			//收起箭头
			arrow.startAnimation(selectCloseAnim);
		}
	};

	@Override
	public void setOnDismissListener(OnDismissListener onDismissListener) {
		super.setOnDismissListener(onDismissListener);
	}


}
