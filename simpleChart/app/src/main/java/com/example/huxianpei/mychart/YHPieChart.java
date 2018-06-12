package com.example.huxianpei.mychart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * 作者：chs on 2016/9/8 16:25
 * 邮箱：657083984@qq.com
 * 饼状图表
 */
public class YHPieChart extends View {
    /**
     * 视图的宽和高
     */
    private int mTotalWidth, mTotalHeight;
    /**
     * 绘制区域的半径
     */
    private float mRadius;

    private Paint mPaint, mLinePaint, mTextPaint;

    private Path mPath;
    /**
     * 扇形的绘制区域
     */
    private RectF mRectF;
    /**
     * 点击之后的扇形的绘制区域
     */
    private RectF mRectFTouch;

    private List<DataEntity> mDataList;
    /**
     * 所有的数据加起来的总值
     */
    private float mTotalValue;
    /**
     * 起始角度的集合
     */
    private float[] angles;
    /**
     * 手点击的部分的position
     */
    private int position = -1;

    private String type;

    private float lastPY = -21;
    private int textH;//半字高
    float lastAngleText;
    float lastSweep;

    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00, 0xFF6495ED, 0xFFE32636, 0xFF800000};
    /**
     * 点击监听
     */
    private OnItemPieClickListener mOnItemPieClickListener;

    //提供精确的小数位四舍五入处理。
    NumberFormat nf;

    public void setOnItemPieClickListener(OnItemPieClickListener onItemPieClickListener) {
        mOnItemPieClickListener = onItemPieClickListener;
    }

    public void setType(String type) {
        this.type = type;
    }

    public interface OnItemPieClickListener {
        void onClick(int position);
    }

    public YHPieChart(Context context) {
        super(context);
        init(context);
    }

    public YHPieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public YHPieChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mRectF = new RectF();
        mRectFTouch = new RectF();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setStrokeWidth(2);
        mLinePaint.setColor(Color.BLACK);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(42);
        textH = 21;

        mPath = new Path();

        nf = NumberFormat.getInstance();//数据格式化
        nf.setMaximumFractionDigits(1);//保留一位小数
        nf.setMinimumFractionDigits(1);//保留一位小数
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w - getPaddingLeft() - getPaddingRight();
        mTotalHeight = h - getPaddingTop() - getPaddingBottom();

        mRadius = (float) (Math.min(mTotalWidth, mTotalHeight) / 2 * 0.58);

        mRectF.left = -mRadius;
        mRectF.top = -mRadius;
        mRectF.right = mRadius;
        mRectF.bottom = mRadius;

        mRectFTouch.left = -mRadius - 16;
        mRectFTouch.top = -mRadius - 16;
        mRectFTouch.right = mRadius + 16;
        mRectFTouch.bottom = mRadius + 16;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDataList == null)
            return;
        canvas.translate(mTotalWidth / 2, mTotalHeight / 2);
        //绘制饼图的每块区域
        drawPiePath(canvas);
    }

    /**
     * 绘制饼图的每块区域 和文本
     *
     * @param canvas
     */
    private void drawPiePath(Canvas canvas) {
        //起始地角度
        float startAngle = 0;
        for (int i = 0; i < mDataList.size(); i++) {
//            float sweepAngle = mDataList.get(i).getSelectNum()/mTotalValue*360-1;//每个扇形的角度
            float sweepAngle = mDataList.get(i).getValue()/mTotalValue*360-1;//每个扇形的角度
            mPaint.setColor(mColors[i%mColors.length]);
            //*******下面的两种方法选其一就可以 一个是通过画路径来实现 一个是直接绘制扇形***********
//            mPath.moveTo(0,0);
//            if(position-1==i){
//                mPath.arcTo(mRectFTouch,startAngle,sweepAngle);
//            }else {
//                mPath.arcTo(mRectF,startAngle,sweepAngle);
//            }
//            canvas.drawPath(mPath,mPaint);
//            mPath.reset();
//            canvas.drawArc(mRectF,startAngle,sweepAngle,true,mPaint);
            if (position - 1 == i) {
                canvas.drawArc(mRectFTouch, startAngle, sweepAngle, true, mPaint);
            } else {
                canvas.drawArc(mRectF, startAngle, sweepAngle, true, mPaint);
            }
            //确定直线的起始和结束的点的位置
            float pxs = (float) (mRadius * Math.cos(Math.toRadians(startAngle + sweepAngle / 2)));
            float pys = (float) (mRadius * Math.sin(Math.toRadians(startAngle + sweepAngle / 2)));
            float pxt = (float) ((mRadius + 30) * Math.cos(Math.toRadians(startAngle + sweepAngle / 2)));
            float pyt = (float) ((mRadius + 30) * Math.sin(Math.toRadians(startAngle + sweepAngle / 2)));
            angles[i] = startAngle;
            startAngle += sweepAngle;
//            startAngle += sweepAngle+1;有间隔
            //绘制线和文本／

            DecimalFormat df = new DecimalFormat("#.0");

            float res =Float.parseFloat(df.format(mDataList.get(i).getValue() * 100 / mTotalValue));
            float v = startAngle % 360;
            if (res > 0 && res < 1) {
                res = 1;
            }

            if (!"0.0".equals(nf.format(res))) {
//                mLinePaint.setColor(YHResourceUtils.getColor(R.color.text_color_second));

//                mTextPaint.setColor(YHResourceUtils.getColor(R.color.text_color_second));
                lastSweep = sweepAngle;
                float angleText = startAngle - sweepAngle / 2;
                String index1 = i+1+").";
                float py = pyt;
                float px = pxt;
                if (isInFour(angleText)) {//第四象限
                    float x = angleText >= 45 && angleText < 70? pxt-mTextPaint.measureText(index1  + nf.format(res) + "%")/2:pxt;
                    lastPY = angleText < 30?textH*2*(i+1):pyt-textH<lastPY?lastPY+textH*2:pyt+textH;
                    py = lastPY - textH;
                    canvas.drawText(index1 + "：" + nf.format(res) + "%",
                            x, lastPY, mTextPaint);
                } else if (isInThree(angleText)) {//第三象限
                    float x = angleText >= 105 && angleText < 140 ? pxt-mTextPaint.measureText(index1  + nf.format(res) + "%")*5/3:pxt - mTextPaint.measureText(index1  + nf.format(res) + "%");
                    px = angleText >= 105 && angleText < 140 ?x+mTextPaint.measureText(index1  + nf.format(res) + "%"):pxt;
                    lastPY = pyt + textH * 3 >= lastPY &&(isInThree(lastAngleText))?lastPY - 2 * textH:pyt + textH;
                    py = lastPY - textH;
                    canvas.drawText(index1  + nf.format(res) + "%",
                            x, lastPY, mTextPaint);
                } else if (isInTwo(angleText)) {//第二象限
                    lastPY = pyt + 2 * textH > lastPY && (isInTwo(lastAngleText)||isInThree(lastAngleText))?lastPY - 2 * textH:pyt;
                    py = lastPY;
                    canvas.drawText(index1  + nf.format(res) + "%",
                            pxt - 3 / 2 * mTextPaint.measureText(index1  + nf.format(res) + "%"), lastPY, mTextPaint);
                } else {//第一象限
                    float x = angleText <= 320 && angleText < 290? pxt-mTextPaint.measureText(index1  + nf.format(res) + "%")/2:pxt;
                    lastPY = angleText > 320 ? pyt-2*textH*(mDataList.size()-i-1):(pyt - 2* textH)>lastPY&&isInOne(lastAngleText)?pyt:isInOne(lastAngleText)?lastPY+2*textH:pyt-2*textH*(mDataList.size()-i-2);
                    py = lastPY;
                    canvas.drawText(index1 + nf.format(res) + "%",
                            x, lastPY, mTextPaint);
                }
                canvas.drawLine(pxs, pys, px, py, mLinePaint);
                lastAngleText = angleText;
            }
        }
    }

    private boolean isTwoSmall(float sweepAngle) {
        return lastSweep+sweepAngle>45;
    }

    private boolean isInOne(float angleText) {
        return angleText % 360.0 >= 270 && angleText % 360.0 <= 360;
    }
    private boolean isInTwo(float angleText) {
        return angleText % 360.0 >= 180.0 && angleText % 360.0 <= 270.0;
    }

    private boolean isInThree(float angleText) {
        return angleText % 360.0 >= 105.0 && angleText % 360.0 <= 180.0;
    }

    private boolean isInFour(float angleText) {
        return angleText % 360.0 >= 0 && angleText % 360.0 <= 120.0;
    }

    public void setDataList(List<DataEntity> dataList) {
        if(dataList == null){
            return;
        }
        this.mDataList = dataList;
        mTotalValue = 0;
        for (DataEntity pieData : mDataList) {
            mTotalValue += pieData.getValue();
        }
        angles = new float[mDataList.size()];
        invalidate();
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                float x = event.getX()-(mTotalWidth/2);
//                float y = event.getY()-(mTotalHeight/2);
//                float touchAngle = 0;
//                if (x<0&&y<0){  //2 象限
//                    touchAngle += 180;
//                }else if (y<0&&x>0){  //1象限
//                    touchAngle += 360;
//                }else if (y>0&&x<0){  //3象限
//                    touchAngle += 180;
//                }
//                //Math.atan(y/x) 返回正数值表示相对于 x 轴的逆时针转角，返回负数值则表示顺时针转角。
//                //返回值乘以 180/π，将弧度转换为角度。
//                touchAngle += Math.toDegrees(Math.atan(y/x));
//                if (touchAngle<0){
//                    touchAngle = touchAngle+360;
//                }
//                float touchRadius = (float) Math.sqrt(y*y+x*x);
//                if (touchRadius< mRadius){
//                    position = -Arrays.binarySearch(angles,(touchAngle))-1;
//                    invalidate();
//                    if(mOnItemPieClickListener!=null){
//                        mOnItemPieClickListener.onClick(position-1);
//                    }
//                }
//                break;
//        }
//        return super.onTouchEvent(event);
//    }
}
