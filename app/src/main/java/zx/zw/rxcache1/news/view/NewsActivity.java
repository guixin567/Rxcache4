package zx.zw.rxcache1.news.view;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import zx.zw.rxcache1.R;
import zx.zw.rxcache1.base.BaseBindActivity;
import zx.zw.rxcache1.databinding.ActivityMainBinding;
import zx.zw.rxcache1.net.BaseObserver;
import zx.zw.rxcache1.news.adapter.NewsAdapter;
import zx.zw.rxcache1.news.model.bean.NewsBean;
import zx.zw.rxcache1.news.viewmodel.NewsViewModel;

public class NewsActivity extends BaseBindActivity<ActivityMainBinding> {

    private NewsAdapter mNewsAdapter;
    private NewsViewModel mNewsViewModel;
    private Observable<NewsBean> mNewsList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        super.initData();
        mNewsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        mNewsList = mNewsViewModel.getNewsList("top");

        mNewsList.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<List<NewsBean>>(NewsActivity.this) {
                    @Override
                    protected void onSuccess(List<NewsBean> data) {
                        mNewsAdapter.setNewData(data);
                        mNewsAdapter.notifyDataSetChanged();
                    }
                });

    }

    @Override
    protected void initAdapter() {
        super.initAdapter();
        mBinding.rvNews.setLayoutManager(new LinearLayoutManager(this));;
        mNewsAdapter = new NewsAdapter(R.layout.recycle_item_news, null);
        mBinding.rvNews.setAdapter(mNewsAdapter);

    }
}
