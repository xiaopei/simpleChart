package com.example.huxianpei.mychart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class YHHorBarChart extends View {

    private Context context;
    private  double  maxcount ;
    private  double  mincount ;
    private String textX = "选项";
    private String textY = "人数";
    private int total;
    private int textSize = 42;
    int yUnit = 5;
    private List<YHAnswerOption> list  = new ArrayList<YHAnswerOption>( );
    NumberFormat nf;

    private String type;

    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};

    public void setType(String type) {
        this.type = type;
    }

    public YHHorBarChart(Context context) {
        super(context);
        this.context= context;
        init();
    }

    public YHHorBarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public YHHorBarChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void setList(List<YHAnswerOption> list) {
        this.list = list;
        invalidate();
    }

    public void setTotal(int total) {
        this.total = total==0?1:total;
    }

    private void init(){
        nf = NumberFormat.getInstance();//数据格式化
        nf.setMaximumFractionDigits(1);//保留一位小数
        nf.setMinimumFractionDigits(1);
    }

    @Override
    public void onDraw(Canvas canvas ) {
        canvas.drawColor(Color.WHITE);//设置背景颜色
        getDataSource();
	 /*获取屏幕的宽度*/
        // DisplayMetrics   ds = getResources().getDisplayMetrics();
        //getWindowManager().getDefaultDisplay().getMetrics(ds);
        //int width= ds.widthPixels;

        //在Activity中可以直接使用getWindowManager()获取屏幕的大小	，但在Activity外的类中不能使用getWindowManager()这个方法。
        WindowManager wm = (WindowManager)this.getContext().getSystemService( Context.WINDOW_SERVICE);

        Display display  = wm.getDefaultDisplay();

        int width = 210*(list.size()+1);
        int height = getHeight()-80;//留下标题的空间

        Paint mypaint = new Paint();

        mypaint.setAntiAlias(true);//设置反锯齿效果,使得图像看起来更平滑

        //绘制x,y轴
        int xoffset  = 90;//设置偏移量，按x,y轴等比例缩放
        int yoffset = (int)(height*0.05);

        mypaint.setColor(Color.parseColor("#666666"));
        mypaint.setStyle( Style.FILL);

        //这里面将y轴坐标，画到了80，避免标题显示时，与统计表重合
        canvas.drawLine( 2+xoffset ,height-2-yoffset, 2+xoffset,80,mypaint );//画y轴 ,此时的y轴
        canvas.drawLine(xoffset,82,2+xoffset,80,mypaint);
        canvas.drawLine(xoffset+4,82,2+xoffset,80,mypaint);
        canvas.drawLine( 2+xoffset, height-2-yoffset,width-2,height-2-yoffset,mypaint);//画x轴
        canvas.drawLine( width-4, height-4-yoffset,width-2,height-2-yoffset,mypaint);//画x轴箭头
        canvas.drawLine( width-4, height-yoffset,width-2,height-2-yoffset,mypaint);//画x轴箭头

        int xUnit_value = ( width-2-xoffset)/(list.size( )+1);//将x轴均等份

        int yUnit_value= (height-2-yoffset-80)/yUnit;//减去顶部，为标题预留的80个单元

        //绘制虚线
        mypaint.setColor(Color.parseColor("#F3F3F3"));
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

        mypaint.setColor(Color.parseColor("#666666"));
        mypaint.setStyle(Style.STROKE);
        mypaint.setPathEffect(null);
        mypaint.setStrokeWidth(0);
        mypaint.setTextSize(textSize);

        for( int j = 0 ; j < yUnit; j++)
        {
            double markers = ymarkers*j;
//            canvas.drawText( nf.format(markers), xoffset-mypaint.measureText(nf.format(markers)),height-2-yoffset-(yUnit_value*(j)), mypaint);//xoffset-2避免字体显示的位置太靠近
        }

        canvas.drawText( textY, xoffset-mypaint.measureText(textY),height-yoffset-(yUnit_value*yUnit), mypaint);//xoffset-2避免字体显示的位置太靠近

        //绘制柱状图
        mypaint.setStyle(Style.FILL);
        mypaint.setStrokeWidth(2);

        for( int j = 0; j<list.size();j++ )
        {
            int barWidth = ( int )( xUnit_value/2);//定义统计图中，柱体的宽度。这个可以根据个人喜好自定义，在每一个区间内，只用1/5来显示柱状图
            int startPos = xoffset +2 +xUnit_value*(j+1);//j+1,使得x轴的每一个区间从坐标原点，向后平移一个区间。

            int interval = barWidth/2 ;

            YHAnswerOption map = list.get(j);
            int temp =  map.getSelectNum();
            int barHeight = (int) (temp*(yUnit_value/ymarkers));//这儿存在问题，如果使用的是(temp/ymarkers)*yUnit_value,则每次计算的基数有问题。所以改为temp*( yUnit_value/ymarkers)

            //绘制柱状图
            mypaint.setColor(mColors[j%mColors.length]);
            canvas.drawRect(startPos-barWidth, height-2-yoffset-barHeight,
                    startPos,height-2-yoffset,mypaint);

            //绘制数据值
            DecimalFormat df = new DecimalFormat("#.0");
            float res =Float.parseFloat(df.format(temp * 100 / (float)total));
            mypaint.setColor(Color.parseColor("#666666"));
//            float res = temp * 100 / total;
            String value = nf.format(res)+"%";
            canvas.drawText( value,startPos-barWidth/2-mypaint.measureText(value)/2,height-2-yoffset-barHeight-4  ,mypaint);

            //绘制x轴上面的横坐标
            mypaint.setColor(Color.parseColor("#666666"));
            String str = j+1+")";
            canvas.drawText(str, startPos-interval-mypaint.measureText(str)/2, height-yoffset+textSize, mypaint);//x-interval是为了，让x轴上显示的字，居中显示
        }
        canvas.drawText( textX, width-10-mypaint.measureText(textX),height-yoffset+textSize, mypaint);//xoffset-2避免字体显示的位置太靠近
    }

    /* 获得需要的数据*/
    public void getDataSource() {
        maxcount = 0;
        mincount = 0;
//        ViewDatabase( );//遍历数据库得到count字段,eControl字段，数据存到list中
        //得出list中count的最大，最小值。
        for( YHAnswerOption map:list ) {
            int temp =  map.getSelectNum();
            if( maxcount < temp ) {
                maxcount = temp;
            }
            if( mincount > temp ) {
                mincount = temp;
            }
        }
        maxcount = maxcount == 0 ? 1 : maxcount;
    }
}
