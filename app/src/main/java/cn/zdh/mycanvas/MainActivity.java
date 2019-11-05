package cn.zdh.mycanvas;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import cn.zdh.mycanvas.clip.TestView;

public class MainActivity extends AppCompatActivity {
    private ImageView iv;


    private int[] mImgIds = new int[]{ //7个
            R.drawable.avft,
            R.drawable.box_stack, R.drawable.bubble_frame,
            R.drawable.bubbles, R.drawable.bullseye,
            R.drawable.circle_filled, R.drawable.circle_outline,

            R.drawable.avft, R.drawable.box_stack,
            R.drawable.bubble_frame, R.drawable.bubbles,
            R.drawable.bullseye, R.drawable.circle_filled,
            R.drawable.circle_outline
    };
    private int[] mImgIds_active = new int[]{
            R.drawable.avft_active,
            R.drawable.box_stack_active, R.drawable.bubble_frame_active,
            R.drawable.bubbles_active, R.drawable.bullseye_active,
            R.drawable.circle_filled_active, R.drawable.circle_outline_active,

            R.drawable.avft_active, R.drawable.box_stack_active,
            R.drawable.bubble_frame_active, R.drawable.bubbles_active,
            R.drawable.bullseye_active, R.drawable.circle_filled_active,
            R.drawable.circle_outline_active
    };


    public Drawable[] revealDrawables;
    protected int level = 5000;
    private GalleryHorizontalScrollView hzv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        test();


        int a=10;
        Log.e("zdh","------------*****"+(a-=a));
        init();
        initData();
    }

    //初始化数据
    private void initData() {
        revealDrawables = new Drawable[mImgIds.length];

        for (int i = 0; i < mImgIds.length; i++) {
            Drawable drawable1 = getResources().getDrawable(mImgIds[i]);
            Drawable drawable2 = getResources().getDrawable(mImgIds_active[i]);

            RevealDrawable revealDrawable = new RevealDrawable(drawable1, drawable2, RevealDrawable.HORIZONTAL);

            revealDrawables[i] = revealDrawable;
        }

        //把数据添加到自定义view里面
        hzv.addView(revealDrawables);

    }

    //初始化view
    private void init() {
        hzv = findViewById(R.id.ghs);
    }

    //切割drawable
    private void test() {
//        iv = findViewById(R.id.iv);
        Drawable drawable1 = getResources().getDrawable(R.drawable.avft);
        Drawable drawable2 = getResources().getDrawable(R.drawable.avft_active);

        TestView myDrawable = new TestView(drawable1, drawable2);

//        TestView myDrawable = new TestView(drawable2);

        iv.setImageDrawable(myDrawable);


//        iv.setImageResource(R.mipmap.book_bg);
    }
}
