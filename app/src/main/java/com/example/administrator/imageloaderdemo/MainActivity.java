package com.example.administrator.imageloaderdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.lemonimagelibrary.cache.CacheMode;
import com.example.lemonimagelibrary.core.LemonImageLoader;

public class MainActivity extends AppCompatActivity {

    ImageView image1;
    ImageView image2;
    ImageView image3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);

        //最简单的请求图片方式
        LemonImageLoader.with(this)
                .load("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2940873513,4245760174&fm=23&gp=0.jpg")
                .into(image1);

        //设置占位图片
        LemonImageLoader.with(this)
                .load("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3882497238,2506895164&fm=23&gp=0.jpg")
                .placeHolder(R.mipmap.ic_launcher)
                .into(image2);

        //设置占位图片,设置缓存策略，可提高用户体验
        //DOUBLE_CAHCE:双缓存，硬盘和内存都会存上，优先使用内存
        //DISK_LRU_CACHE：硬盘缓存，当app卸载时会随着一起被删除
        //MEMORY_LRU_CACHE:内存缓存，使用当前手机内存的8分之1来缓存图片。
        //NO_CAHCE:不需要缓存。
        //默认是使用硬盘缓存。
        LemonImageLoader.with(this)
                .load("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=272013671,438323943&fm=11&gp=0.jpg")
                .placeHolder(R.mipmap.ic_launcher)
                .cachePolicy(CacheMode.MEMORY_LRU_CACHE)
                .into(image3);


    }
}
