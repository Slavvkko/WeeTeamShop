package com.hordiienko.weeteamshop.ui.product;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.hordiienko.weeteamshop.Constants;
import com.hordiienko.weeteamshop.R;
import com.hordiienko.weeteamshop.model.Product;
import com.hordiienko.weeteamshop.utils.GlideApp;
import com.hordiienko.weeteamshop.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductActivity extends AppCompatActivity {
    public static final String EXTRA_PRODUCT = "EXTRA_PRODUCT";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.thumb)
    ImageView thumb;

    @BindView(R.id.name)
    TextView name;

    @BindView(R.id.description)
    TextView description;

    @BindView(R.id.reference)
    TextView reference;

    @BindView(R.id.price)
    TextView price;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ButterKnife.bind(this);

        initToolbar();
        initViews();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void initViews() {
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            Product product = bundle.getParcelable(EXTRA_PRODUCT);

            if (product != null) {
                name.setText(product.getName());
                description.setText(product.getDescription());
                reference.setText(getString(R.string.product_ref, product.getReference()));
                price.setText(getString(R.string.product_price, Utils.formatPrice(product.getPrice())));

                if (product.getDefaultImageId() != -1) {
                    String imageUrl = Utils.formatImageUrl(product.getId(), product.getDefaultImageId());
                    GlideUrl glideUrl = new GlideUrl(imageUrl, new LazyHeaders.Builder()
                            .addHeader("Authorization", Constants.API_CREDENTIALS)
                            .build());

                    GlideApp.with(this)
                            .load(glideUrl)
                            .placeholder(R.drawable.placeholder)
                            .into(thumb);
                } else {
                    thumb.setImageResource(R.drawable.placeholder);
                }
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
