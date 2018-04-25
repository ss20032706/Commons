package shencai.commonsample.ui.fg1content;

import android.os.Bundle;
import android.support.annotation.Nullable;

import cc.shencai.commonlibrary.widgets.BaseActivity;
import shencai.commonsample.R;

/**
 * Created by yss on 2017/9/13
 *
 * @version 1.0.0
 */
public class BounceScrollActivity extends BaseActivity{

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_bouncescroll);
	}
}
