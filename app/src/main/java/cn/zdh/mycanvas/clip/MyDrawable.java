package cn.zdh.mycanvas.clip;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.Gravity;

public class MyDrawable extends Drawable {
    private Drawable drawable1,drawable2;


    public MyDrawable(Drawable drawable1, Drawable drawable2) {
        this.drawable1 = drawable1;
        this.drawable2 = drawable2;
    }


    /**
     *  调用了getBounds方法需要重写这个方法
     * @param bounds
     */
    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        //定好两个当然完了图片的宽高--边界bounds
        drawable1.setBounds(bounds);
        drawable2.setBounds(bounds);

    }

    /***
     * 获取drawable 实际宽度
     * @return
     */
    @Override
    public int getIntrinsicWidth() {
//        return super.getIntrinsicWidth();
        return Math.max(drawable1.getIntrinsicWidth(),drawable2.getIntrinsicWidth());

    }

    @Override
    public int getIntrinsicHeight() {
//        return super.getIntrinsicHeight();
        return Math.max(drawable1.getIntrinsicHeight(),drawable2.getIntrinsicHeight());
    }

    /**
     * 获取drawable 实际宽度
     * @param canvas
     */


    /**
     * 画图
     * @param canvas
     */
    @Override
    public void draw( Canvas canvas) {
        //获取当前自己 drawable 的矩形区域
        Rect bounds = getBounds();

        //存放扣下的图片
        Rect temp=new Rect();

        //新建个图层
        canvas.save();

        //抠图(从左边扣  还是 从右边扣，目标矩形的宽，目标矩形的高，被扣对象，承载对象（存放扣下的图片）);
        Gravity.apply(Gravity.LEFT,bounds.width()/2,bounds.height(),bounds,temp);

        //裁剪画布 即设置画布显示区域
        canvas.clipRect(temp);

        //抠图
        drawable1.draw(canvas);

        //合成 保存图层
        canvas.restore();



        //抠图(从左边扣  还是 从右边扣，目标矩形的宽，目标矩形的高，被扣对象，承载对象（存放扣下的图片）);
        Gravity.apply(Gravity.RIGHT,bounds.width()/2,bounds.height(),bounds,temp);

        //裁剪画布 即设置画布显示区域
        canvas.clipRect(temp);

        //抠图
        drawable2.draw(canvas);

        //合成 保存图层
        canvas.restore();


    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
