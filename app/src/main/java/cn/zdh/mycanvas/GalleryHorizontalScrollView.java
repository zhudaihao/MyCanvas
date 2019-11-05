package cn.zdh.mycanvas;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GalleryHorizontalScrollView extends HorizontalScrollView implements View.OnTouchListener {
    private static final String TAG = "zdh";
    private LinearLayout container;//图片保存到线性布局里面
    //子view宽
    private int icon_width;
    //线性布局左右的padding距离
    private int centerX;


    public GalleryHorizontalScrollView(Context context) {
        super(context);
        init();
    }


    public GalleryHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GalleryHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    /**
     * 初始化 线性布局
     */
    private void init() {
        container = new LinearLayout(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        container.setLayoutParams(params);
        //设置垂直还是水平滑动 默认水平
//        container.setOrientation(LinearLayout.HORIZONTAL);

        //设置GalleryHorizontalScrollView滑动监听
        setOnTouchListener(this);
    }


    /***
     *  获取自己宽高 和子view宽高
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //获取子view的宽 高（所有子view宽高是一样，所有随便获取一个子view就可以了）
        View childAt = container.getChildAt(0);

        //获取子view的宽
        icon_width = childAt.getWidth();
        //GalleryHorizontalScrollView中心点X
        centerX = getWidth() / 2;

        //处理中心坐标 改为中心图片的作边界
        centerX = centerX - icon_width / 2;

        //设置线性布局的padding属性
        container.setPadding(centerX, 0, centerX, 0);

    }


    /**
     * 把drawable保存到imageView添加到线性布局，再添加到自定义的scrollView
     *
     * @param revealDrawables
     */
    public void addView(Drawable[] revealDrawables) {

        for (int i = 0; i < revealDrawables.length; i++) {
            //获取drawable对象
            Drawable revealDrawable = revealDrawables[i];

            //创建imageView 保存drawable对象
            ImageView imageView = new ImageView(getContext());
            imageView.setImageDrawable(revealDrawable);

            //把ImageView添加到线性布局里面
            container.addView(imageView);

            //处理第一个彩色绘制
            if (i == 0) {
                imageView.setImageLevel(5000);
            }

        }


        //再把线性布局添加到GalleryHorizontalScrollView
        addView(container);

    }


    /**
     * 触摸 监听回调
     *
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //只监听滑动的事件，其他事件不监听
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            //渐变图片
            reveal();
        }


        return false;
    }


    /**
     * 图片渐变代码
     */
    private void reveal() {
        //获取X滚动的距离
        int scrollX = getScrollX();

        //找到滑动后中心的左右两张图片的下标(打印观察，只有这两个下标的图片才做渐变处理)
        //注意下标是从0开始
        int index_left = scrollX / icon_width;
//        Log.e("zdh", "------------index_left " + index_left);
        int index_right = index_left + 1;

        //获取线性布局，当子view是上面左右下标就处理
        int childCount = container.getChildCount();

        for (int i = 0; i < childCount; i++) {
            if (i == index_left || i == index_right) {
                //处理渐变图片
                //获取一张图片占的比例(可以假设level取值范围 0-1000)
                //取5000目的是区分是左画动还是右滑动（小于5000就左，大于就右）
                float ratio = 5000f / icon_width;
                //获取子view    注意取的id因为i可能未index_left 或者index_right
                ImageView iv_left = (ImageView) container.getChildAt(index_left);

                //左边view
                int tag_left = (int) (5000 - scrollX % icon_width * ratio);
                Log.e("zdh", "------------tag_left " + tag_left);
                iv_left.setImageLevel(tag_left);

                //右边view
                if (index_right < container.getChildCount()) {
                    // 注意取的id因为i可能未index_left 或者index_right
                    ImageView iv_right = (ImageView) container.getChildAt(index_right);
                    int tag_right = (int) (10000 - scrollX % icon_width * ratio);
                    Log.e("zdh", "------------tag_right " + tag_right);
                    iv_right.setImageLevel(tag_right);
                }

            } else {
                //图片都是灰色
                //获取子view
                ImageView imageView = (ImageView) container.getChildAt(i);
                //标记（最后会调用drawable的onLevelChange()方法）
                imageView.setImageLevel(0);


            }
        }


    }


}
