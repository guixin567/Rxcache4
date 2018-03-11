package zx.zw.rxcache1.news.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import java.io.File;

import io.reactivex.Observable;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import zx.zw.rxcache1.net.NetUtil;
import zx.zw.rxcache1.net.api.NewsApi;
import zx.zw.rxcache1.net.api.NewsProviders;
import zx.zw.rxcache1.news.model.bean.NewsBean;

/**
 * @author zxKueen 2018/3/10 14:18
 *         email 4994766@qq.com
 */
public class NewsViewModel  extends AndroidViewModel{


    private final NewsProviders mNewsProviders;

    public NewsViewModel(@NonNull Application application) {
        super(application);
        File cacheDir = application.getCacheDir();
        mNewsProviders = new RxCache.Builder()
                .persistence(cacheDir, new GsonSpeaker())
                .using(NewsProviders.class);
    }

    public Observable<NewsBean> getNewsList(String type){
        return mNewsProviders.getNewsList(NetUtil.getInstance().create(NewsApi.class).getNewsList(type));
    }
}
