package shencai.commonsample.ui.fg1content;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Toast;

import cc.shencai.commonlibrary.widgets.BaseActivity;
import es.dmoral.toasty.Toasty;
import shencai.commonsample.R;

import static android.graphics.Typeface.BOLD_ITALIC;

/**
 * Created by yss on 2017/9/25
 *
 * @version 1.0.0
 */
public class ToastActivity extends BaseActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_toast);

		findViewById(R.id.button_error_toast).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toasty.error(ToastActivity.this, "This is an error toast.", Toast.LENGTH_SHORT, true).show();
			}
		});
		findViewById(R.id.button_success_toast).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toasty.success(ToastActivity.this, "Success!", Toast.LENGTH_SHORT, true).show();
			}
		});
		findViewById(R.id.button_info_toast).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toasty.info(ToastActivity.this, "Here is some info for you.", Toast.LENGTH_SHORT, true).show();
			}
		});
		findViewById(R.id.button_warning_toast).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toasty.warning(ToastActivity.this, "Beware of the dog.", Toast.LENGTH_SHORT, true).show();
			}
		});
		findViewById(R.id.button_normal_toast_wo_icon).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toasty.normal(ToastActivity.this, "Normal toast with icon").show();
			}
		});
		findViewById(R.id.button_normal_toast_w_icon).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Drawable icon = getResources().getDrawable(R.drawable.ic_launcher);
				Toasty.normal(ToastActivity.this, "Normal toast with icon", icon).show();
			}
		});
		findViewById(R.id.button_info_toast_with_formatting).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toasty.info(ToastActivity.this, getFormattedMessage()).show();
			}
		});
	}

	private CharSequence getFormattedMessage() {
		final String prefix = "Formatted ";
		final String highlight = "bold italic";
		final String suffix = " text";
		SpannableStringBuilder ssb = new SpannableStringBuilder(prefix).append(highlight).append(suffix);
		int prefixLen = prefix.length();
		ssb.setSpan(new StyleSpan(BOLD_ITALIC),
				prefixLen, prefixLen + highlight.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return ssb;
	}
}
