package cc.shencai.commonlibrary.ncalendar.adapter;

import android.content.Context;
import android.view.ViewGroup;

import org.joda.time.DateTime;

import cc.shencai.commonlibrary.ncalendar.listener.OnClickMonthViewListener;
import cc.shencai.commonlibrary.ncalendar.view.MonthView;

/**
 * Created by 闫彬彬 on 2017/8/28.
 * QQ:619008099
 */

public class MonthAdapter extends CalendarAdapter {

    private OnClickMonthViewListener mOnClickMonthViewListener;

    public MonthAdapter(Context mContext, int count, int curr, DateTime dateTime, OnClickMonthViewListener onClickMonthViewListener) {
        super(mContext, count, curr, dateTime);
        this.mOnClickMonthViewListener = onClickMonthViewListener;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        MonthView nMonthView = (MonthView) mCalendarViews.get(position);
        if (nMonthView == null) {
            int i = position - mCurr;
            DateTime dateTime = this.mDateTime.plusMonths(i);
            nMonthView = new MonthView(mContext, dateTime, mOnClickMonthViewListener);
            mCalendarViews.put(position, nMonthView);
        }
        container.addView(nMonthView);
        return nMonthView;
    }
}
