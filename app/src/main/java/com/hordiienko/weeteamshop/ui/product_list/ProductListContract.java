package com.hordiienko.weeteamshop.ui.product_list;

import com.hordiienko.weeteamshop.model.Product;

import java.util.List;

public interface ProductListContract {
    interface View {
        void showProgress();
        void hideProgress();
        void showFooterProgress();
        void hideFooterProgress();
        void appendData(List<Product> products);
        void showFailure(Throwable t);
    }

    interface Presenter {
        void onViewReady();
        void onViewDestroy();
        void onScrollEnd();
    }
}
