package com.cookandroid.test429

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.kizitonwose.calendarview.ui.ViewContainer
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.Calendar
import java.util.Locale

class home : AppCompatActivity() {

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
        setContentView(R.layout.home)

        // PieChart 설정
        val pieChart = findViewById<PieChart>(R.id.pieChart)
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

        pieChart.data = data
        pieChart.setDrawEntryLabels(false)
        pieChart.description.isEnabled = false
        pieChart.centerText = "1,280 kcal"
        pieChart.setCenterTextSize(18f)
        pieChart.invalidate()

        // 날짜 텍스트 설정
        val textViewDate = findViewById<TextView>(R.id.nutrientTitle)
        val currentDate = Calendar.getInstance().time
        val localDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA)
        textViewDate.text = dateFormat.format(currentDate)
    }
}
