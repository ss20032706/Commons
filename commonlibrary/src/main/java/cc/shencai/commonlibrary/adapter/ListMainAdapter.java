package cc.shencai.commonlibrary.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cc.shencai.commonlibrary.R;


public class ListMainAdapter extends BaseAdapter {

	private Context context;
	private String selectedMainListItemText;
	Holder hold;
	private final ArrayList<String> list;

	public ListMainAdapter(Context context, ArrayList<String> list) {
		this.context = context;
		this.list = list;
	}

	public int getCount() {
		return list.size();
	}

	public String getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View view, ViewGroup viewGroup) {

		if (view == null) {
			view = View.inflate(context, R.layout.item_main_list, null);
			hold = new Holder(view);
			view.setTag(hold);
		} else {
			hold = (Holder) view.getTag();
		}

		hold.layout.setBackgroundColor(0xFFF2F2F2);
		String text = list.get(position);
		hold.txt.setText(text);
		if (!TextUtils.isEmpty(selectedMainListItemText) && selectedMainListItemText.equals(text)) {
			hold.layout.setBackgroundColor(0xFFFFFFFF);
			hold.txt.setTextColor(0xFFFF8C00);
		} else {
			hold.txt.setTextColor(0xFF666666);
		}
		return view;
	}

	public void setSelectItem(String selectedMainListItemText) {
		this.selectedMainListItemText = selectedMainListItemText;
	}

	public String getSelectItem() {
		return selectedMainListItemText;
	}

	private static class Holder {
		LinearLayout layout;
		TextView txt;

		public Holder(View view) {
			txt = (TextView) view.findViewById(R.id.mainitem_txt);
			layout = (LinearLayout) view.findViewById(R.id.mainitem_layout);
		}
	}
}
