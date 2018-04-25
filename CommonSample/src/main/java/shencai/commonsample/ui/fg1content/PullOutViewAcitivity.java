package shencai.commonsample.ui.fg1content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cc.shencai.commonlibrary.utils.DisplayUtil;
import cc.shencai.commonlibrary.widgets.BaseActivity;
import shencai.commonsample.R;
import shencai.commonsample.application.MyApplication;

/**
 * Created by yss on 2017/9/20
 *
 * @version 1.0.0
 */
public class PullOutViewAcitivity extends BaseActivity {
	@Bind(R.id.ivDetail)
	ImageView ivDetail;
	@Bind(R.id.llDetailInfo)
	LinearLayout llDetailInfo;


	@Bind(R.id.tvDetailBtn)
	TextView tvDetailBtn;
	int detailHeight = 0;

	private boolean detailInfoIsShow = false;//默认不展示详情

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_pulloutview);
		ButterKnife.bind(this);
	    detailHeight = MyApplication.getApplication().getDetailHeight();

		ivDetail.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (detailInfoIsShow) {
					tvDetailBtn.setText(getString(R.string.ind_detail_btnshow));
					ivDetail.setImageResource(R.drawable.btn_detail_pulldown_selector);
					if (0 != detailHeight) {
						DisplayUtil.hideDropView(PullOutViewAcitivity.this, llDetailInfo, detailHeight);
					} else {
						llDetailInfo.setVisibility(View.GONE);
					}
				} else {
					tvDetailBtn.setText(getString(R.string.ind_detail_btnhide));
					ivDetail.setImageResource(R.drawable.btn_detail_pulldown_selector);
					if (0 != detailHeight) {
						DisplayUtil.showDropView(PullOutViewAcitivity.this, llDetailInfo, detailHeight);
					} else {
						llDetailInfo.setVisibility(View.VISIBLE);
					}
				}
				detailInfoIsShow = !detailInfoIsShow;
			}
		});
	}
}
