package com.example.huxianpei.mychart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.Z;

public class MainActivity extends AppCompatActivity {
    private List<DataEntity> list  = new ArrayList<DataEntity>( );
    char[] chars = new char[]{'A','B','C','D','E','F',
            'G','H','I','J','K','M',
            'N','L','O','P','Q','R',
            'S','T','U','V','W','X',
            'Y','Z'};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for(int i = 0;i<10;i++){
            DataEntity entity = new DataEntity(chars[i]+"",i+1);
            list.add(entity);
        }
        BarChart barChart = (BarChart) findViewById(R.id.bar_chart);
        barChart.setList(list);
        PieChart pieChart = (PieChart) findViewById(R.id.pie_chart);
        pieChart.setDataList(list);
    }
}
