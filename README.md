## [我的博客](http://blog.csdn.net/sw5131899) 

### 手写高并发图片加载框架

图片加载框架在当前已经是相当的成熟了，想要有创新。就是需要从图片加载速度和图片缓存，
还有内存占用和生命周期监控上去做文章。glide做的就比较好。出自squre团队的良心作品。
该团队可以说是安卓业界良心，还有最近很火的retrofit和okhttp均出自该大神团队，还有很多呢。
框架用的不少，一直没什么时间去研究研究底层，总觉得还是要写一个自己用的吧，还是那句话。
至少出了问题知道在哪。也可以知道在什么地方去优化，同时也可以加强对大神框架的理解，
源码阅读能力都会有所提高。于是去参考目前比较流行的图片加载框架源码。当初的ImageLoader-universal,
还有Glide。还有facebook出品的图片加载框架。都看了看大概的思路和逻辑。
决定采用带有优先级的阻塞式队列来实现高并发图片请求。使用builder链式调度，说实话呢。要说好吧，
只能说是非常一般的框架。但是这个LemonImageLoader可以作为从未了解过图片加载框架底层的程序猿们入门的框架。
并没有采用一些太过新奇的技术。简单易懂，快速上手。

### 框架设计逻辑图

![](https://github.com/SingleShu/LemonImageLoaderDemo/raw/master/logo/ImageLoader.png)

### ImageLoaderUML

![](https://github.com/SingleShu/LemonImageLoaderDemo/raw/master/logo/ImageLoaderUML.png)

	
整体思路如上所示。具体的还是要看源码才行。
使用方式：导入gradle:
```Java  
compile 'com.singleshu888:LemonImageLoader:1.0.1'
```

### API介绍
所有的都是通过LemonImageLoader这个类静态调用，非常方便。

#### with(Context context)
设置图片加载的上下文。

####load(String url)
使用网络地址加载图片，底层自动选择图片加载器

####load(File file)
加载本地图片， 并会对图片进行压缩，适应控件。

####load(@DrawableRes int resId)
加载本地资源图片


####loaderPolicy(IPolicy policy)
加载策略，是对阻塞式队列起作用。是先进先出，还是先进后出的一个选择。像在ListView使用时希望后面的图片先加载，
就可以设置加载策略。

####cachePolicy(CacheMode mode)
CacheMode是一个枚举类型。
DOUBLE_CAHCE:双缓存，硬盘和内存都会存上，优先使用内存
DISK_LRU_CACHE：硬盘缓存，当app卸载时会随着一起被删除
MEMORY_LRU_CACHE:内存缓存，使用当前手机内存的8分之1来缓存图片。
NO_CAHCE:不需要缓存。
默认是使用硬盘缓存。

####placeHolder(@DrawableRes int resId)
占位图片，失败或者加载过程显示的图片

####into(ImageView imageView)
显示的控件，具体加载逻辑。

####onFinishListener(BitmapListener bitmapListener)
图片加载完成后不显示，选择回调处理。


```Java
/**
 * Created by ShuWen on 2017/3/27.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //使用默认的初始化全局队列
        GlobalConfig.initDefault();
        //设置当前手机并发的线程数
//        GlobalConfig.init(3);

    }
}
```
初始化全局变量，其实就是把队列全局化。达到一个方便管理的目的。

```Java
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
```
这是一个很好的入门级图片加载框架，感兴趣的朋友可以down下去自己看看，然后自己封一个框架，可以参考Glide的原理，
创建一个空的fragment，对图片加载进行生命周期监控。这个优化很不错。大家可以去看看。觉得有用和学习价值的请给个Star。谢谢。

