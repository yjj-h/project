package com.cookandroid.test429

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.ZoneId
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    // Day ViewContainer
    class DayViewContainer(view: View) : ViewContainer(view) {
        val textView: TextView = view.findViewById(R.id.calendarDayText)
    }

    // Month Header ViewContainer
    class MonthViewContainer(view: View) : ViewContainer(view) {
        val textView: TextView = view.findViewById(R.id.monthText)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // PieChart 설정

        val carbsColor = ContextCompat.getColor(this, R.color.carbsColor)
        val proteinColor = ContextCompat.getColor(this, R.color.proteinColor)
        val fatColor = ContextCompat.getColor(this, R.color.fatColor)

        val entries = listOf(
            PieEntry(512f, "탄수화물"),
            PieEntry(280f, "단백질"),
            PieEntry(288f, "지방")
        )

        val dataSet = PieDataSet(entries, "")
        dataSet.colors = listOf(carbsColor, proteinColor, fatColor)

        val data = PieData(dataSet)
        data.setValueTextSize(0f) // 내부 텍스트 제거



        //날짜 텍스트 설정
        //val textViewDate = findViewById<TextView>(R.id.nutrientTitle)
        val currentDate = Calendar.getInstance().time
        val localDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA)
        //textViewDate.text = dateFormat.format(currentDate)

        // CalendarView 설정
        val calendarView = findViewById<CalendarView>(R.id.calendarView)

        // DayBinder 연결
        calendarView.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View): DayViewContainer {
                return DayViewContainer(view)
            }

            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.textView.text = day.date.dayOfMonth.toString()

                // 오늘 날짜 강조
                if (day.date == localDate) {
                    container.textView.setTextColor(Color.RED)
                } else {
                    container.textView.setTextColor(Color.BLACK)
                }
            }
        }

        // MonthHeaderBinder 연결
        calendarView.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View): MonthViewContainer {
                return MonthViewContainer(view)
            }

            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                container.textView.text = "${month.yearMonth.year}년 ${month.yearMonth.monthValue}월"
            }
        }

        // 달력 범위 설정
        calendarView.setup(
            YearMonth.now().minusMonths(6),
            YearMonth.now().plusMonths(6),
            DayOfWeek.SUNDAY
        )
        calendarView.scrollToMonth(YearMonth.now())
    }
}
