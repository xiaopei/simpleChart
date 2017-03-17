package com.example.huxianpei.mychart;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class BarChart extends View {

    private  Context  context;
    private  double  maxcount ;
    private  double  mincount ;
    private  List<DataEntity> list  = new ArrayList<DataEntity>( );
    NumberFormat nf;

    public  BarChart(Context context) {
        super(context);
        this.context= context;
        init();
    }

    public BarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BarChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void setList(List<DataEntity> list) {
        this.list = list;
    }

    private void init(){
        nf = NumberFormat.getInstance();//数据格式化
        nf.setMaximumFractionDigits(0);//保留一位小数
        nf.setMinimumFractionDigits(0);
    }

    @Override
    public void onDraw(Canvas  canvas )
    {
        canvas.drawColor(Color.WHITE);//设置背景颜色
        getDataSource();
	 /*获取屏幕的宽度*/
        // DisplayMetrics   ds = getResources().getDisplayMetrics();
        //getWindowManager().getDefaultDisplay().getMetrics(ds);
        //int width= ds.widthPixels;

        //在Activity中可以直接使用getWindowManager()获取屏幕的大小	，但在Activity外的类中不能使用getWindowManager()这个方法。
        WindowManager wm = (WindowManager)this.getContext().getSystemService( Context.WINDOW_SERVICE);

        Display  display  = wm.getDefaultDisplay();

        int width = display.getWidth()-80;
        int height = getHeight()-80;//留下标题的空间

        Paint  mypaint = new Paint ();

        mypaint.setAntiAlias(true);//设置反锯齿效果,使得图像看起来更平滑

        //绘制工作区
        mypaint.setColor( Color.YELLOW);//勾勒边沿
        mypaint.setStrokeWidth( 2 );
        canvas.drawRect(0, 0,width, height, mypaint);
        mypaint.setColor( Color.WHITE);//设置背景为白色
        mypaint.setStrokeWidth( 0);
        canvas.drawRect(2,2, width-2,height-2,mypaint);

        //绘制x,y轴
        int xoffset  = (int)(width*0.1);//设置偏移量，按x,y轴等比例缩放
        int yoffset = (int)(height*0.1);

        mypaint.setColor(Color.GREEN);
        mypaint.setStyle( Style.FILL);

        //这里面将y轴坐标，画到了80，避免标题显示时，与统计表重合
        canvas.drawLine( 2+xoffset ,height-2-yoffset, 2+xoffset,80,mypaint );//画y轴 ,此时的y轴
        canvas.drawLine( 2+xoffset, height-2-yoffset,width-2,height-2-yoffset,mypaint);//画x轴
//
//        //显示标题
//        mypaint.setTextSize(20);//设置画笔的颜色
//        mypaint.setColor(Color.GREEN);

        int xUnit_value = ( width-2-xoffset)/(list.size( )+1);//将x轴均等份
        //绘制y轴，将y轴分成12等份
        int yUnit = 5;
        int yUnit_value= (height-2-yoffset-80)/yUnit;//减去顶部，为标题预留的80个单元

        //绘制虚线
        mypaint.setColor( Color.LTGRAY);
        mypaint.setStyle( Style.STROKE);
        mypaint.setStrokeWidth( 1 );
        //PathEffect 是指路径的方式，DashPathEffect是PathEffect的一个子类，
        // 其中的float数组，必须为偶数，且>=2,指定了多少长度的实线，之后再画多少长度的虚线。
        //程序中，是先绘制长度为1的实线，再绘制长度为3的空白，1是偏移量。
        mypaint.setPathEffect( new DashPathEffect( new float[]{ 1,3},1 ) );

        for( int j = 0 ; j < yUnit-1; j++)//这个虚线的边界解决
        {
            canvas.drawLine(2+xoffset, height-2-yoffset-(yUnit_value*(j+1)), width-2,height-2-yoffset-(yUnit_value*(j+1)) ,mypaint);
        }

        //在y轴方向上，刻度赋值
        double ymarkers = ( (maxcount-mincount) *5/4)/yUnit;// 为了避免查询的最大值，显示到屏幕的顶端,这样的图像显示的比例为4/5

        mypaint.setColor(Color.CYAN);
        mypaint.setStyle(Style.STROKE);
        mypaint.setPathEffect(null);
        mypaint.setStrokeWidth(0);
        mypaint.setTextSize(20);

        for( int j = 0 ; j < 5; j++)
        {
            double markers = ymarkers*j;
            canvas.drawText( nf.format(markers), xoffset-20,height-2-yoffset-(yUnit_value*(j)), mypaint);//xoffset-2避免字体显示的位置太靠近
        }

        //绘制柱状图
        mypaint.setStyle(Style.FILL);
        mypaint.setStrokeWidth(2);

        for( int j = 0; j<list.size();j++ )
        {

            int barWidth = ( int )( xUnit_value/2);//定义统计图中，柱体的宽度。这个可以根据个人喜好自定义，在每一个区间内，只用1/5来显示柱状图
            int startPos = xoffset +2 +xUnit_value*(j+1);//j+1,使得x轴的每一个区间从坐标原点，向后平移一个区间。

            int interval = barWidth/2 ;

            DataEntity map = list.get(j);
            int temp =  map.getValue();
            int barHeight = (int) (temp*(yUnit_value/ymarkers));//这儿存在问题，如果使用的是(temp/ymarkers)*yUnit_value,则每次计算的基数有问题。所以改为temp*( yUnit_value/ymarkers)

            //绘制柱状图
            mypaint.setColor(Color.MAGENTA);
            canvas.drawRect(startPos-barWidth, height-2-yoffset-barHeight,
                    startPos,height-2-yoffset,mypaint);

            //绘制数据值
            mypaint.setColor(Color.BLUE);
            canvas.drawText( String.valueOf(temp),startPos-barWidth,height-2-yoffset-barHeight-4  ,mypaint);

            //绘制x轴上面的横坐标
            mypaint.setColor(Color.CYAN);
            String  str = map.getName();
            canvas.drawText(str, startPos-interval-barWidth/3, height-yoffset+20, mypaint);//x-interval是为了，让x轴上显示的字，居中显示

        }

        //绘制一个统计表的小方块
        mypaint.setColor(Color.MAGENTA);
        canvas.drawRect( 2+xoffset, height-2-yoffset/2, 16+xoffset,height-2-yoffset/2-16   ,mypaint);
    }

    /* 获得需要的数据*/
    public void getDataSource() {

        maxcount = 0;
        mincount = 0;

//        ViewDatabase( );//遍历数据库得到count字段,eControl字段，数据存到list中

        //得出list中count的最大，最小值。
        for( DataEntity map:list )
        {
            int temp =  map.getValue();
            if( maxcount < temp )
            {
                maxcount = temp;
            }
            if( mincount > temp )
            {
                mincount = temp;
            }

        }
    }


}
