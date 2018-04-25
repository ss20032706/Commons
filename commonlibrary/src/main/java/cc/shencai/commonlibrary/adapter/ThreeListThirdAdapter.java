package cc.shencai.commonlibrary.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cc.shencai.commonlibrary.R;


public class ThreeListThirdAdapter extends BaseAdapter {

	private Context context;
	Holder hold;
	private final ArrayList<String> list;
	private int selectedPosition;

	public ThreeListThirdAdapter(Context context, ArrayList<String> list) {
		this.context = context;
		this.list = list;
	}

	public int getCount() {
		return list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View view, ViewGroup viewGroup) {
		if (view == null) {
			view = View.inflate(context, R.layout.item_threelist_third, null);
			hold = new Holder(view);
			view.setTag(hold);
		} else {
			hold = (Holder) view.getTag();
		}
		String text = list.get(position);
		hold.thirdTxt.setText(text);
		if (position == selectedPosition) {
			hold.ivGou.setVisibility(View.VISIBLE);
			hold.thirdTxt.setTextColor(Color.parseColor(context.getString(R.string.three_list_chosen_text_color_blue)));
		} else {
			hold.ivGou.setVisibility(View.INVISIBLE);
			hold.thirdTxt.setTextColor(Color.parseColor(context.getString(R.string.three_list_text_color_gray)));
		}
		return view;
	}

	public void setSelectItem(int selectedPosition) {
		this.selectedPosition = selectedPosition;
	}


	private static class Holder {
		ImageView ivGou;
		TextView thirdTxt;

		public Holder(View view) {
			thirdTxt = (TextView) view.findViewById(R.id.third_txt);
			ivGou = (ImageView) view.findViewById(R.id.ivGou);
		}
	}

}
