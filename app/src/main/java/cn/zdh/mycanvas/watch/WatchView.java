package cn.zdh.mycanvas.watch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义一个表
 */
public class WatchView extends View {
    private Paint paint, paintText, paintPointer;
    private int scale = 12;


    public WatchView(Context context) {
        super(context);
        init();
    }

    public WatchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        paint = new Paint();
        paintText = new Paint();
        paintPointer = new Paint();

        //空心 模式
        paint.setStyle(Paint.Style.STROKE);
        //画笔宽度
        paint.setStrokeWidth(8);
        paint.setAntiAlias(true);//抗锯齿
        paint.setDither(true);//防抖动

        //画笔颜色
        paint.setColor(Color.BLUE);


        //设置画笔
        paintText.setColor(Color.RED);
        paintText.setTextSize(30);

        //指针画笔
        paintPointer.setStrokeWidth(15);


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //1画圆
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - 50, paint);
        //2画时间刻度 和数字
        //一共有12个刻度  0 3 6 9 刻度和其他刻度不同就分开画
        for (int i = 0; i < scale; i++) {
            if (i == 0 || i == 3 || i == 6 || i == 9) {

                //画长刻度
                canvas.drawLine(getWidth() / 2, (getHeight() / 2 - (getWidth() / 2 - 50)), getWidth() / 2, (getHeight() / 2 - (getWidth() / 2 - 50)) + 40, paint);

            } else {
                //画短刻度
                canvas.drawLine(getWidth() / 2, (getHeight() / 2 - (getWidth() / 2 - 50)), getWidth() / 2, (getHeight() / 2 - (getWidth() / 2 - 50)) + 20, paint);

                //画文字
            }

            //画文字
            float textSize = 10;
            if (i == 6) {
                canvas.drawText("9", getWidth() / 2 - textSize, (getHeight() / 2 - (getWidth() / 2 - 50)) + 70, paintText);
            } else if (i == 9) {
                canvas.drawText("6", getWidth() / 2 - textSize, (getHeight() / 2 - (getWidth() / 2 - 50)) + 70, paintText);
            } else {
                canvas.drawText(i + "", getWidth() / 2 - textSize, (getHeight() / 2 - (getWidth() / 2 - 50)) + 70, paintText);
            }

            //每画一次旋转绘制坐标30度=360/12 实现不用计算其他点坐标信息来绘制
            canvas.rotate(30, getWidth() / 2, getHeight() / 2);

        }


        //3画指针
        paintPointer.setStrokeWidth(15);
        canvas.drawLine(getWidth() / 2, getHeight() / 2, getWidth() / 2 + 80, getHeight() / 2, paintPointer);

        paintPointer.setStrokeWidth(10);
        canvas.drawLine(getWidth() / 2, getHeight() / 2-7, getWidth() / 2-20, getHeight() / 2 + 150, paintPointer);

    }
}
