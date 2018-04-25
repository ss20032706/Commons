package shencai.commonsample.ui.fg1content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import cc.shencai.commonlibrary.widgets.BaseActivity;
import shencai.commonsample.R;

/**
 * Created by yss on 2017/9/20
 *
 * @version 1.0.0
 */
public class AutoTextActivity extends BaseActivity{
	private TextView mOutput, mAutofitOutput;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_autotext);

		mOutput = (TextView)findViewById(R.id.output);
		mAutofitOutput = (TextView)findViewById(R.id.output_autofit);

		((EditText)findViewById(R.id.input)).addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
				// do nothing
			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
				mOutput.setText(charSequence);
				mAutofitOutput.setText(charSequence);
			}

			@Override
			public void afterTextChanged(Editable editable) {
				// do nothing
			}
		});
	}
}
