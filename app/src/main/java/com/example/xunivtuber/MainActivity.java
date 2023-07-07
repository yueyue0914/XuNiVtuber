package com.example.xunivtuber;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private List<Object> imagesList;
    private int currentPosition = 0;
    private Handler handler;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        handler = new Handler();

        //监听点击-极光
        ImageView imageView2 = findViewById(R.id.imageView2);
        TextView textView5 = findViewById(R.id.textView5);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Jiguang.class);
                startActivity(intent);
            }
        });

        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Jiguang.class);
                startActivity(intent);
            }
        });

        // 添加轮播图图片
        addSlideshowImages();

        // 开始自动轮播
        startAutoSlideShow();

        // 创建 GestureDetector 实例
        gestureDetector = new GestureDetector(this, new MyGestureListener());

        // 设置触摸监听器
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 将触摸事件传递给 GestureDetector 处理
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

    // 创建 GestureDetector.SimpleOnGestureListener 的子类来处理滑动手势
    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float diffX = e2.getX() - e1.getX();
            float diffY = e2.getY() - e1.getY();

            // 判断滑动方向和距离
            if (Math.abs(diffX) > Math.abs(diffY)
                    && Math.abs(diffX) > SWIPE_THRESHOLD
                    && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffX > 0) {
                    // 右滑，切换到前一张图片
                    currentPosition = (currentPosition - 1 + imagesList.size()) % imagesList.size();
                } else {
                    // 左滑，切换到下一张图片
                    currentPosition = (currentPosition + 1) % imagesList.size();
                }
                viewPager.setCurrentItem(currentPosition, true);
                return true;
            }

            return false;
        }
    }

    private void addSlideshowImages() {
        // 创建轮播图ImageView，并添加到轮播图容器中
        imagesList = new ArrayList<>();
        imagesList.add(R.drawable.image1);
        imagesList.add("https://www.yongjiajiazu.com/shouji/haibao/image1.jpg");
        imagesList.add("https://www.yongjiajiazu.com/shouji/haibao/image2.jpg");
        imagesList.add("https://www.yongjiajiazu.com/shouji/haibao/image3.jpg");
        imagesList.add("https://www.yongjiajiazu.com/shouji/haibao/image4.jpg");
        // 添加更多的轮播图图片...

        // 创建适配器
        SlideshowAdapter adapter = new SlideshowAdapter(imagesList);

        // 设置适配器给ViewPager
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentPosition);

        // 添加页面切换监听器
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 页面滚动时的操作，可以不做任何处理
            }

            @Override
            public void onPageSelected(int position) {
                // 页面选中时的操作
                currentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // 页面滚动状态改变时的操作，可以不做任何处理
            }
        });
    }

    private void startAutoSlideShow() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                currentPosition = (currentPosition + 1) % imagesList.size();
                viewPager.setCurrentItem(currentPosition, true);

                // 切换 textView8 的文本内容
                switchText();

                startAutoSlideShow(); // 递归调用，实现循环轮播
            }
        }, 5000);
    }

    private void switchText() {
        TextView textView8 = findViewById(R.id.textView8);
        String currentText = textView8.getText().toString();

        String newText = "";

        if (currentText.equals("你们是在跟sc聊天还是在跟然然聊天？ ——嘉然今天吃什么")) {
            newText = "你是不是在外面兼职？你是不是小粒Q？——嘉然今天吃什么";
        } else if (currentText.equals("你是不是在外面兼职？你是不是小粒Q？——嘉然今天吃什么")) {
            newText = "谁罕见？骂谁罕见？骂谁罕见！（震声）\n" +
                    "——东雪莲";
        } else if (currentText.equals("谁罕见？骂谁罕见？骂谁罕见！（震声）\n" +
                "——东雪莲")) {
            newText = "才2.9w？我还以为9.2w了呢——阿梓";
        } else {
            newText = "你们是在跟sc聊天还是在跟然然聊天？ ——嘉然今天吃什么";
        }

        textView8.setText(newText);
        // 重新启动定时任务，每隔5秒切换一次文本
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switchText();
            }
        }, 5000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 移除未处理的消息，避免内存泄漏
        handler.removeCallbacksAndMessages(null);
    }

    private class SlideshowAdapter extends androidx.viewpager.widget.PagerAdapter {
        private List<Object> imagesList;

        public SlideshowAdapter(List<Object> imagesList) {
            this.imagesList = imagesList;
        }

        @Override
        public int getCount() {
            return imagesList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(MainActivity.this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            // 使用Picasso加载图片
            if (imagesList.get(position) instanceof String) {
                Picasso.get()
                        .load((String) imagesList.get(position))
                        .placeholder(R.drawable.image1) // 占位符图片，可替换为默认的本地图片
                        .into(imageView);
            } else if (imagesList.get(position) instanceof Integer) {
                imageView.setImageResource((Integer) imagesList.get(position));
            }

            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ImageView) object);
        }
    }
}
