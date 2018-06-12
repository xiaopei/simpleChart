package com.example.huxianpei.mychart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class YHMultiBarChart extends View {

    private Context context;
    private  double  maxcount ;
    private  double  mincount ;
    private int textSize = 42;
    int yUnit = 5;
    private List<YHWorkBarchartBean.Statistics> list  = new ArrayList<YHWorkBarchartBean.Statistics>( );
    NumberFormat nf;
    private int size;
    private int dp = 56;
    //绘制x,y轴
    int xoffset  = 0;//设置偏移量，按x,y轴等比例缩放
    int yoffset;
    int xUnit_value;
    int height;
    int totalHeight;
    private String type;
    private OnItemSelectedListener onItemSelectedListener;
    int downX,downY;
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};

    public void setType(String type) {
        this.type = type;
    }

    public YHMultiBarChart(Context context) {
        super(context);
        this.context= context;
        init();
    }

    public YHMultiBarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public YHMultiBarChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public int getDp() {
        return dp;
    }

    public void setList(List<YHWorkBarchartBean.Statistics> list) {
        this.list = list;
        size = list.size()>6?list.size():6;
        invalidate();
    }

    private void init(){
        nf = NumberFormat.getInstance();//数据格式化
        nf.setMaximumFractionDigits(1);//保留一位小数
        nf.setMinimumFractionDigits(1);
    }

    @Override
    public void onDraw(Canvas canvas ) {
//        getDataSource();

        int width = 3*dp*(size)+3*dp/2;
        height = getHeight();//留下标题的空间

        Paint mypaint = new Paint();
        mypaint.setAntiAlias(true);//设置反锯齿效果,使得图像看起来更平滑

        //绘制x,y轴
        yoffset = (int)(height*0.2);
//        mypaint.setColor(YHResourceUtils.getColor(R.color.text_color_second));
//        mypaint.setStyle( Style.FILL);

//        //这里面将y轴坐标，画到了80，避免标题显示时，与统计表重合
//        canvas.drawLine( 2+xoffset ,height-2-yoffset, 2+xoffset,80,mypaint );//画y轴 ,此时的y轴
//        canvas.drawLine(xoffset,82,2+xoffset,80,mypaint);
//        canvas.drawLine(xoffset+4,82,2+xoffset,80,mypaint);
//        canvas.drawLine( 2+xoffset, height-2-yoffset,width-2,height-2-yoffset,mypaint);//画x轴
//        canvas.drawLine( width-4, height-4-yoffset,width-2,height-2-yoffset,mypaint);//画x轴箭头
//        canvas.drawLine( width-4, height-yoffset,width-2,height-2-yoffset,mypaint);//画x轴箭头

        xUnit_value = 3*dp;//将x轴均等份

        int yUnit_value= (height-yoffset)/yUnit;//减去顶部，为标题预留的80个单元

        //绘制虚线
        mypaint.setColor(Color.parseColor("#f3f3f3"));
        mypaint.setStyle( Style.STROKE);
        mypaint.setStrokeWidth( 1 );
        //PathEffect 是指路径的方式，DashPathEffect是PathEffect的一个子类，
        // 其中的float数组，必须为偶数，且>=2,指定了多少长度的实线，之后再画多少长度的虚线。
        //程序中，是先绘制长度为1的实线，再绘制长度为3的空白，1是偏移量。
        mypaint.setPathEffect( new DashPathEffect( new float[]{1,3},1 ) );

//        for( int j = 0 ; j < yUnit-1; j++)//这个虚线的边界解决
//        {
//            canvas.drawLine(2+xoffset, height-2-yoffset-(yUnit_value*(j+1)), width-2,height-2-yoffset-(yUnit_value*(j+1)) ,mypaint);
//        }

        //在y轴方向上，刻度赋值
        double ymarkers = ( (maxcount-mincount) * 11/10)/yUnit;// 为了避免查询的最大值，显示到屏幕的顶端,这样的图像显示的比例为4/5
        totalHeight = (int) (maxcount*(yUnit_value/ymarkers));

        mypaint.setColor(Color.parseColor("#666666"));
        mypaint.setStyle(Style.STROKE);
        mypaint.setPathEffect(null);
        mypaint.setStrokeWidth(0);
        mypaint.setTextSize(textSize);

//        for( int j = 0 ; j < yUnit; j++){
//            double markers = ymarkers*j;
//            canvas.drawText( nf.format(markers), xoffset-mypaint.measureText(nf.format(markers)),height-2-yoffset-(yUnit_value*(j)), mypaint);//xoffset-2避免字体显示的位置太靠近
//        }

//        canvas.drawText( textY, xoffset-mypaint.measureText(textY),height-yoffset-(yUnit_value*yUnit), mypaint);//xoffset-2避免字体显示的位置太靠近

        //绘制柱状图
        mypaint.setStyle(Style.FILL);
        mypaint.setStrokeWidth(2);

        for( int j = 0; j<size;j++ ) {
            int barWidth = ( int )( xUnit_value/2);//定义统计图中，柱体的宽度。这个可以根据个人喜好自定义，在每一个区间内，只用1/5来显示柱状图
            int startPos = xoffset +xUnit_value*(j+1);//j+1,使得x轴的每一个区间从坐标原点，向后平移一个区间。

            int interval = barWidth/2 ;

            //绘制柱状图
            mypaint.setColor(Color.BLACK);
            canvas.drawRect(startPos-barWidth, height-yoffset-totalHeight,
                    startPos,height-yoffset,mypaint);
                if(j<list.size()){
                    YHWorkBarchartBean.Statistics map = list.get(j);
                    int totalBarHeight = (int) (map.getTotal()*(yUnit_value/ymarkers));
                    mypaint.setColor(mColors[j % mColors.length]);
                    canvas.drawRect(startPos - barWidth, height - yoffset - totalBarHeight,
                            startPos, height - yoffset, mypaint);
                    int total = 0;
                    if(list.get(j).getList() != null && !list.get(j).getList().isEmpty()) {
                        int barHeight = 0;
                        for (int k = 0; k < list.get(j).getList().size(); k++) {
                            int temp = map.getList().get(k).getCounts() + total;
                            barHeight = (int) (temp * (yUnit_value / ymarkers));
                            int startH = (int) (total * (yUnit_value / ymarkers));
                            total = temp;
                            mypaint.setColor(mColors[k % mColors.length]);
                            canvas.drawRect(startPos - barWidth, height - yoffset - barHeight,
                                    startPos, height - yoffset - startH, mypaint);
                        }
                    }else{
                        total = map.getCounts();
                    }
                    //绘制柱状图总值
                    mypaint.setColor(Color.parseColor("#999999"));
                    mypaint.setTextSize(textSize);
                    canvas.drawText(total+"", startPos-interval-mypaint.measureText(total+"")/2, height-yoffset - 8 - totalBarHeight, mypaint);//x-interval是为了，让x轴上显示的字，居中显示
                    mypaint.setColor(Color.parseColor("#999999"));
                    mypaint.setTextSize(textSize);
                    //绘制x轴上面的横坐标
                    String str = list.get(j).getX_time();
                    canvas.rotate(30,startPos - interval*2, height - yoffset + textSize / 2);
                    canvas.drawText(str, startPos - interval*2, height - yoffset + textSize/2, mypaint);//x-interval是为了，让x轴上显示的字，居中显示
                    canvas.rotate(-30,startPos - interval*2, height - yoffset + textSize / 2);
                }
        }
//      canvas.drawText( textX, width-10-mypaint.measureText(textX),height-yoffset+textSize, mypaint);//xoffset-2避免字体显示的位置太靠近
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = (int)event.getX();
                downY = (int)event.getY();
                break;
            case MotionEvent.ACTION_UP:
                int x = (int)event.getX();
                int y = (int)event.getY();
                if(x - downX > 5 || x - downX < -5 || y - downY > 5 || y - downY < -5){
                    break;
                }
                for (int i=0;i<list.size();i++){
                    if(x > xoffset + xUnit_value*(i+1/2) && x < xoffset + xUnit_value*(i+1)
                            && y > height-yoffset-totalHeight&&y < height-yoffset){
                        if(onItemSelectedListener != null){
                            onItemSelectedListener.onItem(i);
                        }
                    }
                }

                break;
            default:break;
        }
        return true;
    }

    /* 获得需要的数据*/
    public void getDataSource() {
        maxcount = 0;
        mincount = 0;
//        ViewDatabase( );//遍历数据库得到count字段,eControl字段，数据存到list中
        //得出list中count的最大，最小值。
        for(int i = 0;i < list.size(); i++) {
            YHWorkBarchartBean.Statistics map = list.get(i);
            int temp =  0;
            for (YHWorkBarchartBean.Info info : map.getList()){
                temp += info.getCounts();
            }
            map.setTotal(temp);
            if( maxcount < temp ) {
                maxcount = temp;
            }
            if( mincount > temp ) {
                mincount = temp;
            }
        }
        maxcount = maxcount == 0 ? 1 : maxcount * 1.2;
    }

    public void setMaxMin(double maxcount,double mincount){
        this.maxcount = maxcount;
        this.mincount = mincount;
    }

    public interface OnItemSelectedListener{
        public void onItem(int item);
    }
}
