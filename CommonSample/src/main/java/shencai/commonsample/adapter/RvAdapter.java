package shencai.commonsample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cc.shencai.commonlibrary.ncalendar.utils.MyLog;
import shencai.commonsample.R;


/**
 * Created by necer on 2017/6/7.
 */

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.MyViewHolder> {

    private Context context;

    public RvAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_, parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        TextView textView = holder.textView;
        textView.setText("-----"+position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyLog.d("position:::::" + position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 50;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_);
        }
    }

}




