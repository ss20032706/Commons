package shencai.commonsample.ui.fg1content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ms.square.android.expandabletextview.ExpandableTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cc.shencai.commonlibrary.widgets.BaseActivity;
import shencai.commonsample.R;
import shencai.commonsample.application.MyApplication;

/**
 * Created by yss on 2017/9/20
 *
 * @version 1.0.0
 */
public class ExpandableTvActivity extends BaseActivity {

	@Bind(R.id.expandable_text)
	TextView expandableText;
	@Bind(R.id.expand_collapse)
	ImageButton expandCollapse;
	@Bind(R.id.expand_text_view)
	ExpandableTextView expandTextView;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expandabletv);
		ButterKnife.bind(this);

		expandTextView.setOnExpandStateChangeListener(new ExpandableTextView.OnExpandStateChangeListener() {
			@Override
			public void onExpandStateChanged(TextView textView, boolean isExpanded) {
				//TODO:展开或者收缩后的操作
				Toast.makeText(MyApplication.getContext(), isExpanded ? "Expanded" : "Collapsed", Toast.LENGTH_SHORT).show();
			}
		});
		//经测试，文字必须是动态设置，如果是xml里面设置，会没有效果
		expandTextView.setText(getString(R.string.expandabletv_text));

	}
}
