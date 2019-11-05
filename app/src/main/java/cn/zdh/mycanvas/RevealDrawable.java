package cn.zdh.mycanvas;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;

public class RevealDrawable extends Drawable {
    private Drawable mUnselectedDrawable, mSelectedDrawable;

    //水平合适垂直滚动
    private int mOrientation;
    public static final int HORIZONTAL = 1;
    public static final int VERTICAL = 2;

    private Rect mTempRect = new Rect();

    public RevealDrawable(Drawable drawable1, Drawable drawable2, int mOrientation) {
        this.mUnselectedDrawable = drawable1;
        this.mSelectedDrawable = drawable2;
        this.mOrientation = mOrientation;
    }


    /**
     * 调用了getBounds方法需要重写这个方法
     *
     * @param bounds
     */
    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        //定好两个当然完了图片的宽高--边界bounds
        mUnselectedDrawable.setBounds(bounds);
        mSelectedDrawable.setBounds(bounds);

    }

    @Override
    public int getIntrinsicWidth() {
        //得到Drawable的实际宽度
        return Math.max(mSelectedDrawable.getIntrinsicWidth(),
                mUnselectedDrawable.getIntrinsicWidth());
    }

    @Override
    public int getIntrinsicHeight() {
        //得到Drawable的实际高度
        return Math.max(mSelectedDrawable.getIntrinsicHeight(),
                mUnselectedDrawable.getIntrinsicHeight());
    }




    /**
     * 画图
     *
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        //获取imageView调用setImageLevel方法传递来的int值
        int level = getLevel();//0-10000
        Log.e("zdh", "-----------draw" + level);
        if (level == 10000 || level == 0) {
            //左边区域和右边区域绘制灰色
            mUnselectedDrawable.draw(canvas);
        } else if (level == 5000) {
            //全部选中---设置成彩色

            mSelectedDrawable.draw(canvas);
        } else {
            //左右view抠图 绘制

            //得到当前自身drawable的矩形区域
            Rect bounds = getBounds();

/**
 * ------------------------------------1绘制灰色部分-------------------------------------------------------------
 */
            {
                float ratio = (level / 5000f) - 1f;

                //判断横向滚动
                int w = bounds.width();
                if (mOrientation == HORIZONTAL) {
                    //
                    w = (int) (w * Math.abs(ratio));
                }

                //判断竖向滚动
                int h = bounds.height();
                if (mOrientation == VERTICAL) {
                    h = (int) (h * Math.abs(ratio));
                }

                //开始抠图
                //判断图片从左边开始 还是右边开始（以5000）
                int gravity = ratio < 0 ? Gravity.LEFT : Gravity.RIGHT;
                Gravity.apply(gravity, w, h, bounds, mTempRect);
                canvas.save();
                canvas.clipRect(mTempRect);
                mUnselectedDrawable.draw(canvas);
                canvas.restore();
            }

/**
 * ----------------------------------------//2绘制彩色部分---------------------------------------------------------
 */
            {

                float ratio = (level / 5000f) - 1f;
                int w = bounds.width();
                if (mOrientation == HORIZONTAL) {
                    //注意绘制彩色部分宽， 矩形宽减去滑动出去的宽
                    w -= (int) (w * Math.abs(ratio));
                }
                int h = bounds.height();
                if (mOrientation == VERTICAL) {
                    //注意绘制彩色部分高， 矩形高减去滑动出去的高
                    h -= (int) (h * Math.abs(ratio));
                }



                //开始抠图
                //判断图片从左边开始 还是右边开始（以5000）
                int gravity = ratio < 0 ? Gravity.RIGHT : Gravity.LEFT;
                Gravity.apply(gravity, w, h, bounds, mTempRect);
                canvas.save();
                canvas.clipRect(mTempRect);
                mSelectedDrawable.draw(canvas);
                canvas.restore();


            }


        }


//        test(canvas);


    }

    private void test(Canvas canvas) {
        //获取当前自己 drawable 的矩形区域
        Rect bounds = getBounds();

        //存放扣下的图片
        Rect temp = new Rect();

        //新建个图层
        canvas.save();

        //抠图(从左边扣  还是 从右边扣，目标矩形的宽，目标矩形的高，被扣对象，承载对象（存放扣下的图片）);
        Gravity.apply(Gravity.LEFT, bounds.width() / 2, bounds.height(), bounds, temp);

        //裁剪画布 即设置画布显示区域
        canvas.clipRect(temp);

        //抠图
        mUnselectedDrawable.draw(canvas);

        //合成 保存图层
        canvas.restore();


        //抠图(从左边扣  还是 从右边扣，目标矩形的宽，目标矩形的高，被扣对象，承载对象（存放扣下的图片）);
        Gravity.apply(Gravity.RIGHT, bounds.width() / 2, bounds.height(), bounds, temp);

        //裁剪画布 即设置画布显示区域
        canvas.clipRect(temp);

        //抠图
        mSelectedDrawable.draw(canvas);

        //合成 保存图层
        canvas.restore();
    }

    /**
     * 对标记的重绘(灰色的)
     */
    @Override
    protected boolean onLevelChange(int level) {
        //重绘-->调用draw方法
        invalidateSelf();
        return true;
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
