package shencai.commonsample.ui.fg1content;

import android.os.Bundle;
import android.support.annotation.Nullable;

import cc.shencai.commonlibrary.widgets.BaseActivity;
import shencai.commonsample.R;

/**
 * Created by yss on 2017/9/22
 *
 * @version 1.0.0
 */
public class RoundedImageViewActivity extends BaseActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_roundedimageview);

	}
}



/**
 * https://github.com/vinc3m1/RoundedImageView
 *
 *
 * Add the following to your build.gradle to use:

 repositories {
 mavenCentral()
 }

 dependencies {
 compile 'com.makeramen:roundedimageview:2.3.0'
 }
 Usage

 Define in xml:

 <com.makeramen.roundedimageview.RoundedImageView
 xmlns:app="http://schemas.android.com/apk/res-auto"
 android:id="@+id/imageView1"
 android:src="@drawable/photo1"
 android:scaleType="fitCenter"
 app:riv_corner_radius="30dip"
 app:riv_border_width="2dip"
 app:riv_border_color="#333333"
 app:riv_mutate_background="true"
 app:riv_tile_mode="repeat"
 app:riv_oval="true" />
 Or in code:

 RoundedImageView riv = new RoundedImageView(context);
 riv.setScaleType(ScaleType.CENTER_CROP);
 riv.setCornerRadius((float) 10);
 riv.setBorderWidth((float) 2);
 riv.setBorderColor(Color.DKGRAY);
 riv.mutateBackground(true);
 riv.setImageDrawable(drawable);
 riv.setBackground(backgroundDrawable);
 riv.setOval(true);
 riv.setTileModeX(Shader.TileMode.REPEAT);
 riv.setTileModeY(Shader.TileMode.REPEAT);
 Or make a Transformation for Picasso:

 Transformation transformation = new RoundedTransformationBuilder()
 .borderColor(Color.BLACK)
 .borderWidthDp(3)
 .cornerRadiusDp(30)
 .oval(false)
 .build();

 Picasso.with(context)
 .load(url)
 .fit()
 .transform(transformation)
 .into(imageView);
 *
 * */