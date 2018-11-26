package com.hordiienko.weeteamshop.ui.product_list;

import com.hordiienko.weeteamshop.model.Product;
import com.hordiienko.weeteamshop.network.NetworkHelper;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProductListPresenter implements ProductListContract.Presenter {
    private ProductListContract.View view;

    private Disposable disposable;
    private int loadedCount;
    private boolean loading;
    private boolean progressShow;
    private boolean footerProgressShow;

    public ProductListPresenter(ProductListContract.View view) {
        this.view = view;

        loadedCount = 0;
    }

    @Override
    public void onViewReady() {
        progressShow = true;
        view.showProgress();
        loadData();
    }

    @Override
    public void onViewDestroy() {
        view = null;

        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Override
    public void onScrollEnd() {
        if (!loading) {
            footerProgressShow = true;
            view.showFooterProgress();
            loadData();
        }
    }

    private void loadData() {
        loading = true;

        Single<List<Product>> single = NetworkHelper.getInstance().getProducts(loadedCount);

        single.subscribeOn(Schedulers.io())
                .delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void endLoading() {
        if (footerProgressShow) {
            footerProgressShow = false;
            view.hideFooterProgress();
        } else if (progressShow) {
            progressShow = false;
            view.hideProgress();
        }

        loading = false;
    }

    private SingleObserver<List<Product>> observer = new SingleObserver<List<Product>>() {
        @Override
        public void onSubscribe(Disposable d) {
            disposable = d;
        }

        @Override
        public void onSuccess(List<Product> products) {
            endLoading();

            if (products.size() > 0) {
                view.appendData(products);
                loadedCount += products.size();
            }
        }

        @Override
        public void onError(Throwable e) {
            endLoading();
            view.showFailure(e);
        }
    };
}
