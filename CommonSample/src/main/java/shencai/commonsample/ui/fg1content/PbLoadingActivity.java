package shencai.commonsample.ui.fg1content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cc.shencai.commonlibrary.utils.PbLoadingUtils;
import cc.shencai.commonlibrary.widgets.BaseActivity;
import cc.shencai.commonlibrary.widgets.LoadingDialog;
import shencai.commonsample.R;

/**
 * Created by yss on 2017/9/14
 *
 * @version 1.0.0
 */
public class PbLoadingActivity extends BaseActivity implements View.OnClickListener {
	@Bind(R.id.tvLoading)
	TextView tvLoading;
	@Bind(R.id.tvLoadFail)
	TextView tvLoadEmpty;
	@Bind(R.id.tvLoadEmpty)
	TextView tvLoadError;
	@Bind(R.id.loading_fail)
	TextView loadingFail;
	@Bind(R.id.pbLoad)
	ProgressBar pbLoad;
	@Bind(R.id.rlLoading)
	RelativeLayout rlLoading;
	@Bind(R.id.tvShowContent)
	TextView tvShowContent;
	@Bind(R.id.tvLoadingDialog)
	TextView tvLoadingDialog;

	LoadingDialog loadingDialog;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		ButterKnife.bind(this);

		initEvent();
	}

	@Override
	public void initEvent() {
		super.initEvent();
		tvLoading.setOnClickListener(this);
		tvLoadError.setOnClickListener(this);
		tvLoadEmpty.setOnClickListener(this);
		tvShowContent.setOnClickListener(this);
		tvLoadingDialog.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tvLoadFail:
				PbLoadingUtils.showErrorOrEmpty(rlLoading, "加载失败", getResources().getDrawable(R.mipmap.error));
				break;
			case R.id.tvLoadEmpty:
				PbLoadingUtils.showErrorOrEmpty(rlLoading, "暂无数据", getResources().getDrawable(R.mipmap.empty));
				break;
			case R.id.tvLoading:
				PbLoadingUtils.loading(rlLoading);
				break;
			case R.id.tvShowContent:
				PbLoadingUtils.showContent(rlLoading);
				break;
			case R.id.tvLoadingDialog:
				if ( null == loadingDialog ) {
				    loadingDialog = new LoadingDialog(this);
				}
				loadingDialog.show();
				loadingDialog.setContent("正在加载……");
				loadingDialog.setCancelable(true);
				break;
		}

	}
}
