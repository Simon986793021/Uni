package com.sherlockkk.snail;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.sherlockkk.snail.model.PartTimeJob;
import com.sherlockkk.snail.model.SchoolActivity;
import com.sherlockkk.snail.model.Secondary;


/**
 * @author SongJian
 * @created 2016/2/10.
 * @e-mail 1129574214@qq.com
 */
public class MyApplication extends MultiDexApplication {
    private static Context mContext;
    private static MyApplication myApplication;


    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();

        myApplication = this;

        //Leancloud初始化
        AVObject.registerSubclass(PartTimeJob.class);
        AVObject.registerSubclass(SchoolActivity.class);
        AVObject.registerSubclass(Secondary.class);
        AVOSCloud.initialize(this, Constants.LEANCLOUD_ID, Constants.LEANCLOUD_KEY);
        AVOSCloud.setDebugLogEnabled(true);
        //uil初始化
        initImageLoader(this);

    }

    public static MyApplication getInstance() {
        return myApplication;
    }

    public static Context getmContextInstance() {
        return mContext;
    }

    private void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPoolSize(3);
        config.memoryCache(new WeakMemoryCache());
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        ImageLoader.getInstance().init(config.build());
    }


}
