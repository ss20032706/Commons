package cc.shencai.commonlibrary.utils;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * loading加载UI处理
 * Created by yss on 2017/9/4
 *
 * @version 1.0.0
 */
public class PbLoadingUtils {

	public static final int STYLE_PROGRESSBAR = 0;//子控件类型为progressbar
	public static final int STYLE_TEXTVIEW = 1;//子控件类型为textview



	/**
	 * 显示加载的转圈
	 * @param rlLoading 旋转加载布局的最外层viewgroup
	 */
	public static void loading( RelativeLayout rlLoading) {
		try {
			rlLoading.setVisibility(View.VISIBLE);
			traversalView(rlLoading,STYLE_PROGRESSBAR).setVisibility(View.VISIBLE);
			traversalView(rlLoading,STYLE_TEXTVIEW).setVisibility(View.GONE);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	/**
	 * 显示搜索出错或者内容为空
	 * @param rlLoading 旋转加载布局的最外层viewgroup
	 * @param string      出错或为空时显示的文字
	 * @param drawableTop 出错或为空时显示的图片
	 */
	public static void showErrorOrEmpty(RelativeLayout rlLoading, String string, Drawable drawableTop) {
		try {
			rlLoading.setVisibility(View.VISIBLE);
			ProgressBar pbLoad = (ProgressBar) traversalView(rlLoading,STYLE_PROGRESSBAR);
			TextView loadingFail = (TextView)traversalView(rlLoading,STYLE_TEXTVIEW);
			pbLoad.setVisibility(View.GONE);
			loadingFail.setVisibility(View.VISIBLE);
			loadingFail.setText(string);
			drawableTop.setBounds(0,0, drawableTop.getMinimumWidth(), drawableTop.getMinimumHeight());
			loadingFail.setCompoundDrawables(null, drawableTop, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	/**
	 * 显示搜索结果，隐藏加载转圈
	 * @param rlLoading 旋转加载布局的最外层viewgroup
	 */
	public static void showContent(RelativeLayout rlLoading) {
		try {
			rlLoading.setVisibility(View.GONE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 遍历viewGroup，获取对应的子控件
	 * @param viewGroup 旋转加载布局的最外层viewgroup
	 * @param style 0:progressbar，1：textview
	 * @return
	 */
	public static View traversalView(ViewGroup viewGroup,int style) {
		int count = viewGroup.getChildCount();
		for (int i = 0; i < count; i++) {
			View view2 = viewGroup.getChildAt(i);
			if (view2 instanceof ProgressBar && STYLE_PROGRESSBAR == style ) {
				return view2;
			} else if ( view2 instanceof TextView && STYLE_TEXTVIEW == style) {
				return view2;
			}
		}
		return null;
	}


}
