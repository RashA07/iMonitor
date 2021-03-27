package com.example.mymonitor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;

import com.example.mymonitor.provider.Patient;
import com.example.mymonitor.provider.Reading;

import java.util.ArrayList;
import java.util.HashMap;


public class Analysis extends Fragment {

    AnyChartView linechart;
    Handler handler;
    Cartesian cartesian;
    Line series;
    Line series2;

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
            keyReadings = readings.keySet().toArray();
            assert keyReadings != null;
            HeartRate = Double.parseDouble(readings.get(keyReadings[i]).getHeartRate());
            Spo2 = Double.parseDouble(readings.get(keyReadings[i]).getSP02());
            Temperature = Double.parseDouble(readings.get(keyReadings[i]).getTemperature());
            HeartRates.add(new ValueDataEntry(String.valueOf(keyReadings[i]), HeartRate));
            Spo2s.add(new ValueDataEntry(String.valueOf(keyReadings[i]), Spo2));
            Temps.add(new ValueDataEntry(String.valueOf(keyReadings[i]), Temperature));
        }



        cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 20d, 5d, 20d);
        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.title("test");
        cartesian.yAxis(0).title("Heart Rate");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        series2 = cartesian.line(Spo2s);
        series = cartesian.line(HeartRates);
        series.name("Heart Rate");
        series2.name("Blood Oxygen");
        series2.color("#FF0000");
        series.hovered().markers().enabled(true);
        series2.hovered().markers().enabled(true);
        series.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series.tooltip()
                .position("right")
                .offsetX(5d)
                .offsetY(5d);
        series2.tooltip()
                .position("right")
                .offsetX(5d)
                .offsetY(5d);
        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);
        linechart.setChart(cartesian);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_analysis, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_analysis, container, false);
        linechart = view.findViewById(R.id.any_chart_view);
        setLineChart();

        super.onViewCreated(view, savedInstanceState);
    }
}
