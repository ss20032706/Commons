package cc.shencai.commonlibrary.widgets;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import butterknife.ButterKnife;
import cc.shencai.commonlibrary.iml.UIInterface;

/**
 * Created by yss on 2017/9/14
 *
 * @version 1.0.0
 */
public class BaseFragment extends Fragment implements UIInterface{


	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}

	/**
	 * 将容器替换成指定的fragment
	 * @param fragment 要替换成的fragment
	 * @param id 容器ID
	 */
	private void setView(Fragment fragment,int id) {
		FragmentManager fm = getActivity().getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		transaction.replace(id, fragment);
		transaction.commit();
	}

	@Override
	public void initView() {

	}

	@Override
	public void initData() {

	}

	@Override
	public void initEvent() {

	}
}
