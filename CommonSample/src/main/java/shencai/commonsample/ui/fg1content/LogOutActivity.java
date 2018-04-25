package shencai.commonsample.ui.fg1content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import cc.shencai.commonlibrary.widgets.BaseActivity;
import cc.shencai.commonlibrary.widgets.SelectDialog;
import shencai.commonsample.R;

/**
 * Created by yss on 2017/11/14
 *
 * @version 1.0.0
 */
public class LogOutActivity extends BaseActivity implements View.OnClickListener {
	SelectDialog logoutDialog;
	List<String> namesLogout = new ArrayList<>();

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logout);

		findViewById(R.id.btLogout).setOnClickListener(this);
		namesLogout.add(getString(R.string.select_dialog_ok));
		namesLogout.add(getString(R.string.select_dialog_cancel));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btLogout:
				showDialog(new SelectDialog.SelectDialogListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						switch (position) {
							case 0:
								finish();
								System.exit(0);
								break;
							case 1:

								break;
						}
					}
				}, namesLogout, "确定要退出软件吗？");
				break;
		}
	}

	public SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names, String title) {
		SelectDialog dialog = new SelectDialog(LogOutActivity.this, R.style
				.transparentFrameWindowStyle,
				listener, names, title);
		if (!isFinishing()) {
			dialog.show();
			dialog.setBtnCancelVisible(View.GONE);
		}

		return dialog;
	}
}
