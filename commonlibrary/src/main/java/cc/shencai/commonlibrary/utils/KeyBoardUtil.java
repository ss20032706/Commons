package cc.shencai.commonlibrary.utils;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 软键盘管理
 * Created by yss on 2017/8/14
 * @version 1.0.0
 */
public class KeyBoardUtil {

	private KeyBoardUtil(){
        /* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * 打开软键盘
	 *
	 * @param mEditText
	 *            输入框
	 * @param mContext
	 *            上下文
	 */
	public static void openKeyboard(EditText mEditText, Context mContext)
	{
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
				InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	/**
	 * 关闭软键盘
	 *
	 * @param mEditText
	 *            输入框
	 * @param mContext
	 *            上下文
	 */
	public static void closeKeyboard(EditText mEditText, Context mContext)
	{
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
	}


	/**
	 * 根据当前状态来判断，如果状态为打开，则关闭。否则，反之。
	 * @param mEditText
	 * @param view
	 * @param mContext
	 */
	public static void closeKeyBoardByJudge(EditText mEditText, View view, Context mContext){
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if(imm.isActive(mEditText)){
			view.requestFocus();//使其它view获取焦点.这里因为是在fragment下,所以便用了getView(),可以指定任意其它view
			closeKeyboard(mEditText,mContext);
		}
	}
}
