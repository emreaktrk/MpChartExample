package com.ibtech.chart

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.renderer.XAxisRenderer
import com.github.mikephil.charting.utils.Transformer
import com.github.mikephil.charting.utils.ViewPortHandler
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.random.Random

private const val GRAN = 1f

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setup()
        bind()
    }

    private fun setup() {
        chart.xAxis.run {
            granularity = 1f
            valueFormatter = MonthValueFormatter()
            textColor = Color.WHITE
            textSize = 12f
            axisMaximum = 0f
            axisMaximum = 12f
            setDrawAxisLine(false)
            setDrawGridLines(false)
            setDrawLabels(true)
            setLabelCount(7, true)
            setCenterAxisLabels(false)
            setCenterAxisLabels(true)
        }
        chart.axisLeft.run {
            granularity = GRAN
            setDrawAxisLine(false)
            setDrawGridLines(false)
            setDrawLabels(false)
        }
        chart.axisRight.run {
            granularity = GRAN
            setDrawAxisLine(false)
            setDrawGridLines(false)
            setDrawLabels(false)
        }
        chart.run {
            setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                override fun onNothingSelected() = update()

                override fun onValueSelected(e: Entry, h: Highlight) = update(e)
            })
            setScaleEnabled(false)
            legend.isEnabled = false
            background = ContextCompat.getDrawable(applicationContext, R.drawable.background_chart)
            description.isEnabled = false
            extraTopOffset = 24f
            data = bind()
        }
    }

    private fun bind(): LineData {
        val randoms = generate()
        val entries = randoms.mapIndexed { index, datum -> Entry(index.toFloat(), datum) }
        val set = LineDataSet(entries, "Sales").apply {
            lineWidth = 3f
            setColor(ContextCompat.getColor(applicationContext, R.color.iceBlue), 255)
            setDrawCircles(false)
            setDrawFilled(false)
            setDrawHighlightIndicators(false)
        }
        return LineData(set).apply {
            setDrawValues(false)
        }
    }

    private fun generate(): List<Float> = List(13) {
        Random.nextInt(100).toFloat()
    }

    private fun update(selection: Entry? = null) {
        val icon = ContextCompat.getDrawable(applicationContext, R.drawable.ic_selected)

        chart.lineData.dataSets.forEach {
            for (i in 0 until it.entryCount) {
                val current = it.getEntryForIndex(i)
                if (selection != null && current == selection) {
                    current.icon = icon
                } else {
                    current.icon = null
                }
            }
        }
    }
}

class MonthValueFormatter : ValueFormatter() {
    private val months = Stack<String>()

    init {
        with(months) {
            add("ARA")
            add("KAS")
            add("EYL")
            add("AĞU")
            add("6 TEM")
            add("HAZ")
            add("MAY")
            add("NİS")
            add("MAR")
            add("ŞUB")
            add("OCA")
        }
    }

    override fun getFormattedValue(value: Float): String {
        return "MAY"
    }
}

class AxisRenderer(viewPortHandler: ViewPortHandler?, xAxis: XAxis?, trans: Transformer?) :
    XAxisRenderer(viewPortHandler, xAxis, trans) {

}