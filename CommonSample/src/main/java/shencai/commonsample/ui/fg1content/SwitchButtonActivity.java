package shencai.commonsample.ui.fg1content;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.suke.widget.SwitchButton;

import cc.shencai.commonlibrary.widgets.BaseActivity;
import shencai.commonsample.R;

/**
 * Created by yss on 2017/9/20
 *
 * @version 1.0.0
 */
public class SwitchButtonActivity extends BaseActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_switchbutton);

		SwitchButton switchButton = (SwitchButton) findViewById(R.id.switch_button);
		switchButton.setChecked(true);
		switchButton.isChecked();
		switchButton.toggle();     //switch state
		switchButton.toggle(false);//switch without animation
		switchButton.setShadowEffect(true);//disable shadow effect
		switchButton.setEnabled(false);//disable button
		switchButton.setEnableEffect(false);//disable the switch animation
		switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(SwitchButton view, boolean isChecked) {
				//TODO do your job
			}
		});
	}
}


/**
 * <attr name="sb_shadow_radius" format="reference|dimension"/>       阴影半径
 <attr name="sb_shadow_offset" format="reference|dimension"/>       阴影偏移
 <attr name="sb_shadow_color" format="reference|color"/>            阴影颜色
 <attr name="sb_uncheck_color" format="reference|color"/>           关闭颜色
 <attr name="sb_checked_color" format="reference|color"/>           开启颜色
 <attr name="sb_border_width" format="reference|dimension"/>        边框宽度
 <attr name="sb_checkline_color" format="reference|color"/>         开启指示器颜色
 <attr name="sb_checkline_width" format="reference|dimension"/>     开启指示器线宽
 <attr name="sb_uncheckcircle_color" format="reference|color"/>     关闭指示器颜色
 <attr name="sb_uncheckcircle_width" format="reference|dimension"/> 关闭指示器线宽
 <attr name="sb_uncheckcircle_radius" format="reference|dimension"/>关闭指示器半径
 <attr name="sb_checked" format="reference|boolean"/>               是否选中
 <attr name="sb_shadow_effect" format="reference|boolean"/>         是否启用阴影
 <attr name="sb_effect_duration" format="reference|integer"/>       动画时间，默认300ms
 <attr name="sb_button_color" format="reference|color"/>            按钮颜色
 <attr name="sb_show_indicator" format="reference|boolean"/>        是否显示指示器，默认true：显示
 <attr name="sb_background" format="reference|color"/>              背景色，默认白色
 <attr name="sb_enable_effect" format="reference|boolean"/>         是否启用特效，默认true
 */