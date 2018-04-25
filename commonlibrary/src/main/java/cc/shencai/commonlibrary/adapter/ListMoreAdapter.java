package cc.shencai.commonlibrary.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cc.shencai.commonlibrary.R;


public class ListMoreAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<String> text_list;
	private String selectedMoreListItemText;
	Holder hold;

	public ListMoreAdapter(Context context, ArrayList<String> text_list) {
		this.context = context;
		this.text_list = text_list;
	}

	public int getCount() {
		return text_list.size();
	}

	public String getItem(int position) {
		return text_list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View view, ViewGroup viewGroup) {

		if (view == null) {
			view = View.inflate(context, R.layout.item_more_list, null);
			hold = new Holder(view);
			view.setTag(hold);
		} else {
			hold = (Holder) view.getTag();
		}
		String text = text_list.get(position);
		hold.txt.setText(text);

		if (!TextUtils.isEmpty(selectedMoreListItemText) && text.equals(selectedMoreListItemText)) {
			hold.txt.setTextColor(0xFFFF8C00);
		} else {
			hold.txt.setTextColor(0xFF666666);
		}

		return view;
	}

	public void setSelectItem(String selectedMoreListItemText) {
		this.selectedMoreListItemText = selectedMoreListItemText;
	}

	private static class Holder {
		TextView txt;

		public Holder(View view) {
			txt = (TextView) view.findViewById(R.id.moreitem_txt);
		}
	}
}
