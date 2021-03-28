package com.example.mymonitor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;

import com.example.mymonitor.provider.Reading;

import java.util.ArrayList;
import java.util.HashMap;


public class Analysis extends Fragment {

    AnyChartView linechartHeartRate;
    AnyChartView linechartSPO2;
    AnyChartView linechartTemp;

    Cartesian cartesian;
    Cartesian cartesian2;
    Cartesian cartesian3;
    Line series;
    Line series2;
    Line series3;

    Double HeartRate;
    Double Spo2;
    Double Temperature;


    HashMap<String, Reading> readings = new HashMap<>();
    Object[] keyReadings;

    HashMap<Integer, ArrayList<String>> latestReadings = new HashMap<>();

//    ArrayList<DataEntry> value = new ArrayList<>();


    public void setLineChart() {

        ArrayList<DataEntry> HeartRates = new ArrayList<>();
        ArrayList<DataEntry> Spo2s = new ArrayList<>();
        ArrayList<DataEntry> Temps = new ArrayList<>();

        readings = LiveData.getReadingHashMap();

        for(int i =0; i<readings.size(); i++){
            if (readings.size() > 0){
                keyReadings = readings.keySet().toArray();
                assert keyReadings != null;
                HeartRate = Double.parseDouble(readings.get(keyReadings[i]).getHeartRate());
                Spo2 = Double.parseDouble(readings.get(keyReadings[i]).getSP02());
                Temperature = Double.parseDouble(readings.get(keyReadings[i]).getTemperature());
                HeartRates.add(new ValueDataEntry(String.valueOf(keyReadings[i]), HeartRate));
                Spo2s.add(new ValueDataEntry(String.valueOf(keyReadings[i]), Spo2));
                Temps.add(new ValueDataEntry(String.valueOf(keyReadings[i]), Temperature));
            }
            else {
                HeartRates.add(new ValueDataEntry(0, 0));
                Spo2s.add(new ValueDataEntry(0, 0));
                Temps.add(new ValueDataEntry(0, 0));
            }
        }

//        Monthly


//      Chart for Heart Rate:
        APIlib.getInstance().setActiveAnyChartView(linechartHeartRate);
        cartesian = AnyChart.line();
        cartesian.animation(true);
        cartesian.padding(10d, 20d, 5d, 20d);
        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.title("Heart Rate");
        cartesian.yAxis(0).title("Heart Rate");
        cartesian.xAxis(0).title("Time");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);
        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);
        series = cartesian.line(HeartRates);
        series.name("Heart Rate (BPM)");
        series.hovered().markers().enabled(true);
        series.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series.tooltip()
                .position("right")
                .offsetX(5d)
                .offsetY(5d);

        linechartHeartRate.setChart(cartesian);

//      Chart for Blood Oxygen (SPO2)
        APIlib.getInstance().setActiveAnyChartView(linechartSPO2);
        cartesian2 = AnyChart.line();
        cartesian2.animation(true);
        cartesian2.padding(10d, 20d, 5d, 20d);
        cartesian2.crosshair().enabled(true);
        cartesian2.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);
        cartesian2.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian2.title("Blood Oxygen Levels");
        cartesian2.yAxis(0).title("Blood Oxygen");
        cartesian2.xAxis(0).title("Time");
        cartesian2.xAxis(0).labels().padding(5d, 5d, 5d, 5d);
        cartesian2.legend().enabled(true);
        cartesian2.legend().fontSize(13d);
        cartesian2.legend().padding(0d, 0d, 10d, 0d);
        series2 = cartesian2.line(Spo2s);
        series2.name("Blood Oxygen (%)");
        series2.color("#FF0000");
        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series2.tooltip()
                .position("right")
                .offsetX(5d)
                .offsetY(5d);

        linechartSPO2.setChart(cartesian2);


//      Chart for Blood Oxygen (SPO2)
        APIlib.getInstance().setActiveAnyChartView(linechartTemp);
        cartesian3 = AnyChart.line();
        cartesian3.animation(true);
        cartesian3.padding(10d, 20d, 5d, 20d);
        cartesian3.crosshair().enabled(true);
        cartesian3.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);
        cartesian3.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian3.title("Temperature");
        cartesian3.yAxis(0).title("Temperature °C");
        cartesian3.xAxis(0).title("Time");
        cartesian3.xAxis(0).labels().padding(5d, 5d, 5d, 5d);
        cartesian3.legend().enabled(true);
        cartesian3.legend().fontSize(13d);
        cartesian3.legend().padding(0d, 0d, 10d, 0d);
        series3 = cartesian3.line(Temps);
        series3.name("Temperature in °C");
        series3.color("#FF0000");
        series3.hovered().markers().enabled(true);
        series3.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series3.tooltip()
                .position("right")
                .offsetX(5d)
                .offsetY(5d);

        linechartTemp.setChart(cartesian3);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_analysis, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        linechartHeartRate = view.findViewById(R.id.heartRateGraph_Chart);
        linechartHeartRate.setProgressBar(view.findViewById(R.id.Hprogress_bar));
        linechartSPO2 = view.findViewById(R.id.spo2Graph_Chart);
        linechartSPO2.setProgressBar(view.findViewById(R.id.Sprogress_bar));
        linechartTemp = view.findViewById(R.id.temperatureGraph_Chart);
        linechartTemp.setProgressBar(view.findViewById(R.id.Tprogress_bar));
        setLineChart();

        super.onViewCreated(view, savedInstanceState);
    }
}
