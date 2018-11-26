package com.hordiienko.weeteamshop.ui.product_list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hordiienko.weeteamshop.R;
import com.hordiienko.weeteamshop.model.Product;
import com.hordiienko.weeteamshop.ui.adapter.ProductListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListActivity extends AppCompatActivity implements ProductListContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.progressLoading)
    ProgressBar progressLoading;

    @BindView(R.id.recyclerProducts)
    RecyclerView recyclerProducts;

    private ProductListAdapter adapter;
    private ProductListContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);

        initToolbar();
        initRecycler();
        initPresenter();

        presenter.onViewReady();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.onViewDestroy();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    private void initRecycler() {
        adapter = new ProductListAdapter(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerProducts.setLayoutManager(layoutManager);
        recyclerProducts.setAdapter(adapter);
        recyclerProducts.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            public void onScrollEnd() {
                presenter.onScrollEnd();
            }
        });
    }

    private void initPresenter() {
        presenter = new ProductListPresenter(this);
    }

    @Override
    public void showProgress() {
        progressLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressLoading.setVisibility(View.GONE);
    }

    @Override
    public void showFooterProgress() {
        // you cannot call this method from RecyclerView.OnScrollListener.onScrolled
        recyclerProducts.post(() -> adapter.addLoadingFooter());
    }

    @Override
    public void hideFooterProgress() {
        adapter.removeLoadingFooter();
    }

    @Override
    public void appendData(List<Product> products) {
        adapter.append(products);
    }

    @Override
    public void showFailure(Throwable t) {
        Toast.makeText(this, getString(R.string.communication_error), Toast.LENGTH_LONG).show();
    }
}
