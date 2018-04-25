package shencai.commonsample.ui.fg1content;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cc.shencai.commonlibrary.utils.DisplayUtil;
import cc.shencai.commonlibrary.utils.ImageUtil;
import cc.shencai.commonlibrary.widgets.AutoDisplayChildViewContainer;
import cc.shencai.commonlibrary.widgets.BaseActivity;
import shencai.commonsample.R;
import shencai.commonsample.application.MyApplication;

/**
 * Created by yss on 2017/9/20
 *
 * @version 1.0.0
 */
public class AutoDisplayCenterActivity extends BaseActivity {
	@Bind(R.id.adcvTag)
	AutoDisplayChildViewContainer adcvTag;

	String[] datas = {
			"测试",
			"打发放到",
			"发你好啊啊的",
			"测试",
			"aaaa",
			"大法师法师",
			"测试",
			"打",
			"阿什顿发收到",
			"大师傅阿什顿发阿斯蒂芬"


	};

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_autodisplaycenter);
		ButterKnife.bind(this);
		displayUI(datas);
	}


	/**
	 * 添加标签，并展示
	 */
	private void displayUI(String[] mLabels) {
		for (int i = 0; i < mLabels.length; i++) {
			final String data = mLabels[i];
			final TextView tv = new TextView(MyApplication.getContext());
			tv.setText(data);
			tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
			tv.setGravity(Gravity.CENTER);
			int paddingy = DisplayUtil.dp2px(MyApplication.getContext(), 2);
			int paddingx = DisplayUtil.dp2px(MyApplication.getContext(), 8);
			tv.setPadding(paddingx, paddingy, paddingx, paddingy);
			tv.setClickable(false);

			int shape = GradientDrawable.RECTANGLE;
			int radius = DisplayUtil.dp2px(MyApplication.getContext(), 4);
			int strokeWeight = DisplayUtil.dp2px(MyApplication.getContext(), 1);
			int childBackGround = MyApplication.getContext().getResources().getColor(R.color.detail_tag_solid);
			int stokeColor = MyApplication.getContext().getResources().getColor(R.color.detail_tag_stroke);

			GradientDrawable normalBg = ImageUtil.getShape(shape, radius, strokeWeight, stokeColor, childBackGround);
			StateListDrawable selector = ImageUtil.getSelector(normalBg, normalBg);
			tv.setBackgroundDrawable(selector);
//			ColorStateList colorStateList = DrawableUtils.getColorSelector(stokeColor, stokeColor);
			tv.setTextColor(Color.WHITE);
			adcvTag.addView(tv);
		}
	}
}
