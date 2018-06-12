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
        int size = (int)(Math.random()*5);
        for(int i = 0;i<size;i++){
            DataEntity entity = new DataEntity(chars[i]+"",(int)(Math.random()*100));
            list.add(entity);
        }

        for(int i = 0;i<5;i++){
            DataEntity entity = new DataEntity(chars[i]+"",1);
            list.add(entity);
        }
        for(int i = 0;i<5 - size;i++){
            DataEntity entity = new DataEntity(chars[i]+"",(int)(Math.random()*100));
            list.add(entity);
        }
        BarChart barChart = (BarChart) findViewById(R.id.bar_chart);
        barChart.setList(list);
        YHPieChart pieChart = (YHPieChart) findViewById(R.id.pie_chart);
        pieChart.setDataList(list);
    }
}
