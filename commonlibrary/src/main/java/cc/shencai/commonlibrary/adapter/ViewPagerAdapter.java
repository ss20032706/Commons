package cc.shencai.commonlibrary.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
	
	private List<Fragment> views;
	
	public ViewPagerAdapter(FragmentManager fm, List<Fragment> views){
		super(fm);
		this.views = views;
	}
	
	@Override
	public int getCount() {
		return views.size();
	}

	@Override
	public Fragment getItem(int arg0) {
		return views.get(arg0);
	}

	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}
	
}
