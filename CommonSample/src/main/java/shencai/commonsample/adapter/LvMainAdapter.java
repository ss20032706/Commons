package shencai.commonsample.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import shencai.commonsample.R;
import shencai.commonsample.beans.FunctionBean;

/**
 * Created by yss on 2017/9/13
 *
 * @version 1.0.0
 */
public class LvMainAdapter extends BaseAdapter {

	private Context mContext;
	private List<FunctionBean> datas;
	private ViewHolder holder;

	public LvMainAdapter(List<FunctionBean> datas, Context mContext) {
		this.mContext = mContext;
		this.datas = datas;
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public FunctionBean getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.item_lvmain, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		FunctionBean bean = datas.get(position);
		holder.tvName.setText(bean.getName());
		return convertView;
	}

	static class ViewHolder {
		@Bind(R.id.tvName)
		TextView tvName;

		ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
	}
}
