package cn.zdh.mycanvas.clip;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

/**
 * drawable可以理解成画布
 */
public class TestView extends Drawable {
    private Drawable drawable1,drawable2;

    public TestView(Drawable drawable1, Drawable drawable2) {
        this.drawable1 = drawable1;
        this.drawable2 = drawable2;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        drawable1.setBounds(bounds);
        drawable2.setBounds(bounds);
    }

    @Override
    public int getIntrinsicHeight() {
        return drawable1.getIntrinsicHeight();
    }


    @Override
    public int getIntrinsicWidth() {
        return drawable1.getIntrinsicWidth();
    }


    /**
     * 绘制图片
     *
     * @param canvas
     */

    @Override
    public void draw(Canvas canvas) {
        //获取画布的矩形 注意需要重新三个方法
        Rect bounds = getBounds();

        Rect saveRect = new Rect();


        //新建图层
        canvas.save();

        /**
         * 切割图片从那边开始 比如左边，切割的宽 ；切割的高,切割的矩形对象，保存切割后的矩形
         */
        Gravity.apply(Gravity.LEFT, bounds.width()/2, bounds.height(), bounds, saveRect);
        //设置画布显示区域（把裁剪后的图片保存的矩形，设置到画布里面）
        canvas.clipRect(saveRect);
        //绘制（把画布设置到drawable里面）
        drawable1.draw(canvas);
        //合成图层
        canvas.restore();

        Log.e("zdh","----------"+canvas.getSaveCount());


        //扣另外图片
        Gravity.apply(Gravity.RIGHT,bounds.width()/2,bounds.height(),bounds,saveRect);
        canvas.clipRect(saveRect);
        drawable2.draw(canvas);
//        canvas.restore();

//        Log.e("zdh","---------->>>"+canvas.getSaveCount());

    }


    /**
     * 设置drawable 透明度  0完全透明  255是完全不透明
     *
     * @param alpha
     */
    @Override
    public void setAlpha(int alpha) {


    }

    /**
     * 设置颜色滤镜器
     *
     * @param colorFilter
     */
    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    /**
     * 返回次绘图的不透明度
     *
     * @return
     */
    @Override
    public int getOpacity() {
        return 0;
    }
}
