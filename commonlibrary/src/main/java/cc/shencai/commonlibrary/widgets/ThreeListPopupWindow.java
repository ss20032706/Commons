package cc.shencai.commonlibrary.widgets;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import cc.shencai.commonlibrary.R;
import cc.shencai.commonlibrary.adapter.ThreeListFirstAdapter;
import cc.shencai.commonlibrary.adapter.ThreeListSecondAdapter;
import cc.shencai.commonlibrary.adapter.ThreeListThirdAdapter;
import cc.shencai.commonlibrary.beans.ThreeListBean;
import cc.shencai.commonlibrary.beans.ThreeListResultBean;
import cc.shencai.commonlibrary.utils.DisplayUtil;

/**
 * 自定义PopupWindow  主要用来显示ListView
 */
public class ThreeListPopupWindow extends PopupWindow {
	private LayoutInflater inflater;
	private ListView lvList1;
	private ListView lvList2;
	private ListView lvList3;
	private TextView tvTitle1;
	private TextView tvTitle2;
	private TextView tvTitle3;
	private HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
	private Context ctx;
	private ImageView arrow;
	private Animation selectCloseAnim;
	private Animation animClockwiseOpen;    // 顺时针开启
	private Animation animClockwiseClose;    //顺时针关闭
	private Animation animAntiClockwiseopen;//逆时针开启
	private Animation animAntiClockwiseClose; //逆时针关闭
	private ThreeListFirstAdapter listAdapter1;
	private ThreeListSecondAdapter listAdapter2;
	private ThreeListThirdAdapter listAdapter3;
	private Handler confineHandler;
	private ArrayList<String> data1 = new ArrayList<String>();
	private ArrayList<String> data2 = new ArrayList<String>();
	private ArrayList<String> data3 = new ArrayList<String>();

	private int selectedPosition1 = 0;
	private int selectedPosition2 = 0;
	private int selectedPosition3 = 0;
	private ThreeListBean threeListBean;
	private ThreeListResultBean resultBean = new ThreeListResultBean();

	/**
	 * @param context        上下文
	 * @param confineHandler
	 */
	public ThreeListPopupWindow(Context context, ThreeListBean threeListBean, Handler confineHandler) {
		super(context);
		ctx = context;
		this.confineHandler = confineHandler;
		this.threeListBean = threeListBean;
		inflater = LayoutInflater.from(context);
		init();
	}

	public void setConfineHandler(Handler confineHandler) {
		this.confineHandler = confineHandler;
	}

	private void init() {
		View view = inflater.inflate(R.layout.three_list_layout, null);
		setContentView(view);
		setWidth(LayoutParams.WRAP_CONTENT);
		setHeight(LayoutParams.MATCH_PARENT);
		setFocusable(true);
		ColorDrawable dw = new ColorDrawable(0x00FF00);
		setBackgroundDrawable(dw);

		lvList1 = (ListView) view.findViewById(R.id.lv_list1);
		lvList2 = (ListView) view.findViewById(R.id.lv_list2);
		lvList3 = (ListView) view.findViewById(R.id.lv_list3);

		tvTitle1 = (TextView) view.findViewById(R.id.tvTitle1);
		tvTitle2 = (TextView) view.findViewById(R.id.tvTitle2);
		tvTitle3 = (TextView) view.findViewById(R.id.tvTitle3);

		if (TextUtils.isEmpty(threeListBean.getTitle1())) {
		    tvTitle1.setText(threeListBean.getTitle1());
		}

		if (TextUtils.isEmpty(threeListBean.getTitle2())) {
			tvTitle2.setText(threeListBean.getTitle2());
		}

		if (TextUtils.isEmpty(threeListBean.getTitle3())) {
			tvTitle3.setText(threeListBean.getTitle3());
		}

		//初始化lvList1
		try {
			prepareAllData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		initListAllAdapter();
		lvList1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				selectedPosition1 = position;
				selectedPosition2 = 0;
				selectedPosition3 = 0;
				prepareData2();
				notifyAdapter1();
				notifyAdapter2();
				notifyAdapter3();
				resultBean.setFirstName(data1.get(selectedPosition1));
			}
		});


		lvList2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				selectedPosition2 = position;
				selectedPosition3 = 0;
				prepareData3();
				notifyAdapter2();
				notifyAdapter3();
				resultBean.setSecondName(data2.get(selectedPosition2));
			}
		});


		lvList3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				selectedPosition3 = position;
				notifyAdapter3();
				resultBean.setThirdName(data3.get(selectedPosition3));
				if (null != confineHandler) {
					Message msg = confineHandler.obtainMessage();
					msg.obj = resultBean;
					confineHandler.sendMessage(msg);
				}
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


	private void prepareAllData() {
		data1.clear();
		for (int i = 0; i < threeListBean.getFirstBeens().size(); i++) {
			data1.add(threeListBean.getFirstBeens().get(i).getFirstName());
		}
		prepareData2();
		prepareData3();
	}

	private void prepareData2() {
		data2.clear();
		for (int i = 0; i < threeListBean.getFirstBeens().get(selectedPosition1).getSecondBeens().size(); i++) {
			data2.add(threeListBean.getFirstBeens().get(selectedPosition1).getSecondBeens().get(i).getSecondName());
		}
		prepareData3();
	}

	private void prepareData3() {
		data3.clear();
		for (int i = 0; i < threeListBean.getFirstBeens().get(selectedPosition1)
				.getSecondBeens().get(selectedPosition2).getThirdBeans().size(); i++) {
			data3.add(threeListBean.getFirstBeens().get(selectedPosition1)
					.getSecondBeens().get(selectedPosition2).getThirdBeans().get(i)
					.getThirdName());
		}
	}

	private void initListAllAdapter() {
		listAdapter1 = new ThreeListFirstAdapter(ctx, data1);
		lvList1.setAdapter(listAdapter1);
		listAdapter2 = new ThreeListSecondAdapter(ctx, data2);
		lvList2.setAdapter(listAdapter2);
		listAdapter3 = new ThreeListThirdAdapter(ctx, data3);
		lvList3.setAdapter(listAdapter3);
	}

	private void notifyAdapter1() {
		listAdapter1.setSelectItem(selectedPosition1);
		listAdapter1.notifyDataSetChanged();
	}

	private void notifyAdapter2() {
		listAdapter2.setSelectItem(selectedPosition2);
		listAdapter2.notifyDataSetChanged();
	}

	private void notifyAdapter3() {
		listAdapter3.setSelectItem(selectedPosition3);
		listAdapter3.notifyDataSetChanged();
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
	 * 添加支持处理箭头90度旋转
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
