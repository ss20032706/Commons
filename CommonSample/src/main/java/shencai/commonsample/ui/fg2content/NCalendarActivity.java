package shencai.commonsample.ui.fg2content;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ruffian.library.RTextView;

import org.joda.time.DateTime;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import cc.shencai.commonlibrary.ncalendar.calendar.NCalendar;
import cc.shencai.commonlibrary.ncalendar.listener.OnCalendarChangedListener;
import cc.shencai.commonlibrary.ncalendar.utils.MyLog;
import cc.shencai.commonlibrary.utils.StatusBarUtils;
import cc.shencai.commonlibrary.widgets.BaseActivity;
import shencai.commonsample.R;
import shencai.commonsample.adapter.RvAdapter;

/**
 * Created by yss on 2017/9/14
 *
 * @version 1.0.0
 *          参考  https://github.com/yannecer/NCalendar
 *          注意  NCalendar内部的布局需要加上背景颜色，只要是不透明的颜色都可以
 *          <p>
 *          ncalendar:2.x.x包含一个月日历MonthCalendar，一个周日历WeekCalendar和一个滑动切换不同视图的NCalendar， 单一日历请使用MonthCalendar或者WeekCalendar。
 *          NCalendar日历包含了周日历和月日历，通过滑动切换不同的视图，交互效果仿miui日历，尽可能的实现miui的交互逻辑。
 *          NCalendar内部需要一个实现了NestedScrollingChild的子类，RecyclerView，NestedScrollView都可以。
 *          <p>
 *          主要Api
 *          <p>
 *          1、监听
 *          <p>
 *          ncalendar.setOnCalendarChangedListener(new OnCalendarChangedListener() {
 * @Override public void onCalendarChanged(DateTime dateTime) {
 * //日历变化回调
 * }
 * });
 * 2、跳转日期
 * <p>
 * 参数为 yyyy-MM-dd 格式的日期
 * <p>
 * ncalendar.setDate("2017-12-31");
 * 3、回到今天
 * <p>
 * ncalendar.toToday();
 * 4、日历切换，月-->周  周-->月
 * <p>
 * ncalendar.toWeek();
 * ncalendar.toMonth();
 * 5、上一月、下一月、上一周、下一周
 * <p>
 * ncalendar.toNextPager();
 * ncalendar.toLastPager();
 * <p>
 * 6、添加指示圆点
 * <p>
 * List<String> list = new ArrayList<>();
 * list.add("2017-09-21");
 * list.add("2017-10-21");
 * list.add("2017-10-1");
 * list.add("2017-10-15");
 * list.add("2017-10-18");
 * list.add("2017-10-26");
 * list.add("2017-11-21");
 * ncalendar.setPoint(list);
 * <p>
 * 7、支持自定义属性，设置NCalendar默认视图、一周的第一天是周日还是周一等
 * <p>
 * NCalendar默认视图,Month 或者 Week，默认是 Month
 * <p>
 * app:defaultCalendar="Month"
 * app:defaultCalendar="Week"
 * <p>
 * <p>
 * 设置一周开始是周一还是周日，Sunday 或者 Monday ，默认是周日Sunday
 * <p>
 * app:firstDayOfWeek="Sunday"
 * app:firstDayOfWeek="Monday"
 * <p>
 * 8、支持自定义日期区间
 * <p>
 * app:startDate="2010-10-01"
 * app:endDate="2018-10-31"
 * <p>
 * 或者代码设置
 * <p>
 * ncalendar.setDateInterval("2017-04-02","2018-01-01");
 * 9、单一月日历、周日历设置默认不选中
 * <p>
 * false为不选中，只有点击或者跳转日期才会选中，默认为true
 * <p>
 * monthcalendar.setDefaultSelect(false);
 * 支持的属性：
 * <p>
 * 属性	描述
 * solarTextColor	公历日期的文本颜色
 * lunarTextColor	农历日期的文本颜色
 * solarTextSize	公历日期的文本大小
 * lunarTextSize	农历日期的文本大小
 * hintColor	不是本月的日期文本颜色
 * selectCircleColor	选中日期和当天的圆颜色
 * selectCircleRadius	选中和当天圆环半径
 * isShowLunar	是否显示农历
 * hollowCircleColor	选中空心圆中间的颜色
 * hollowCircleStroke	选中空心圆圆环粗细
 * calendarHeight	月日历高度
 * defaultCalendar	NCalendar日历默认视图
 * firstDayOfWeek	每周第一天是周日还是周一
 * duration	自动折叠时间
 * isShowHoliday	是否显示节假日
 * holidayColor	节假日“休”字颜色
 * workdayColor	工作日日“班”字颜色
 * pointSize	指示圆点大小
 * pointColor	指示圆点颜色
 * startDate	日期开始时间
 * endDate	日期结束时间
 * backgroundColor	日历背景颜色
 */
public class NCalendarActivity extends BaseActivity implements OnCalendarChangedListener {
	@Bind(R.id.tv_month)
	TextView tvMonth;
	@Bind(R.id.tv_date)
	TextView tvDate;
	@Bind(R.id.ncalendarrrr)
	NCalendar ncalendar;
	@Bind(R.id.recyclerView)
	RecyclerView recyclerView;
	@Bind(R.id.rtvToday)
	RTextView rtvToday;
	@Bind(R.id.rlShowDate)
	RelativeLayout rlShowDate;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ncalendar);
		ButterKnife.bind(this);

		StatusBarUtils.setShadowStatusBar(this);

		//NCalendar内部需要一个实现了NestedScrollingChild的子类，RecyclerView，NestedScrollView都可以
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		RvAdapter rvAdapter = new RvAdapter(this);
		recyclerView.setAdapter(rvAdapter);


		ncalendar.setDateInterval("2010-04-02", "2050-01-01");

		ncalendar.setOnCalendarChangedListener(this);

		ncalendar.post(new Runnable() {
			@Override
			public void run() {

				HashMap<String,Integer> hashMapPoint = new HashMap<String, Integer>();
				hashMapPoint.put("2017-10-23",Color.BLACK);
				hashMapPoint.put("2017-10-30",Color.BLACK);
				hashMapPoint.put("2017-10-1",Color.BLACK);
				hashMapPoint.put("2017-11-30",Color.BLACK);
				hashMapPoint.put("2017-12-01",Color.BLUE);
				hashMapPoint.put("2017-12-02",Color.GREEN);
				hashMapPoint.put("2017-12-03",Color.BLACK);

				ncalendar.setPoint(hashMapPoint);
			}
		});

		rlShowDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if ( View.VISIBLE == rtvToday.getVisibility() ) {
				    toToday();
				}

			}
		});

		ncalendar.setPointSize(5);
		ncalendar.setPointColor(Color.RED);

	}


	@Override
	public void onCalendarChanged(DateTime dateTime) {
		DateTime dateTimeToday = new DateTime().withTimeAtStartOfDay();

		if ( dateTimeToday.equals(dateTime) ) {
		    rtvToday.setVisibility(View.INVISIBLE);
		} else {
			rtvToday.setVisibility(View.VISIBLE);
		}
		tvMonth.setText(dateTime.getMonthOfYear() + "月");
		tvDate.setText(dateTime.getYear() + "年" + dateTime.getMonthOfYear() + "月" + dateTime.getDayOfMonth() + "日");

		MyLog.d("dateTime::" + dateTime);
	}


	/**
	 * 跳转制定日期
	 * @param date yyyy-MM-dd
	 */
	public void setDate(String date) {
		ncalendar.setDate(date);
	}

	public void toMonth() {
		ncalendar.toMonth();
	}

	public void toWeek() {
		ncalendar.toWeek();
	}

	public void toToday() {
		ncalendar.toToday();
	}

	public void toNextPager() {
		ncalendar.toNextPager();
	}

	public void toLastPager() {
		ncalendar.toLastPager();
	}


}

