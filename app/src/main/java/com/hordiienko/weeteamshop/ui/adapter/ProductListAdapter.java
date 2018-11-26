package com.hordiienko.weeteamshop.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.hordiienko.weeteamshop.Constants;
import com.hordiienko.weeteamshop.R;
import com.hordiienko.weeteamshop.model.Product;
import com.hordiienko.weeteamshop.ui.product.ProductActivity;
import com.hordiienko.weeteamshop.utils.GlideApp;
import com.hordiienko.weeteamshop.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_PROGRESS = 1;

    private Context context;
    private List<Product> productList;

    public ProductListAdapter(Context context) {
        this.context = context;

        productList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            return new ProductViewHolder(LayoutInflater.from(context).inflate(R.layout.view_product, viewGroup, false));
        } else {
            return new ProgressViewHolder(LayoutInflater.from(context).inflate(R.layout.view_progress, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ProductViewHolder) {
            Product product = productList.get(position);
            ProductViewHolder holder = (ProductViewHolder) viewHolder;

            holder.name.setText(product.getName());
            holder.description.setText(product.getDescription());
            holder.reference.setText(context.getString(R.string.product_ref, product.getReference()));
            holder.price.setText(context.getString(R.string.product_price, Utils.formatPrice(product.getPrice())));

            if (product.getDefaultImageId() != -1) {
                String imageUrl = Utils.formatImageUrl(product.getId(), product.getDefaultImageId());
                GlideUrl glideUrl = new GlideUrl(imageUrl, new LazyHeaders.Builder()
                        .addHeader("Authorization", Constants.API_CREDENTIALS)
                        .build());

                GlideApp.with(context)
                        .load(glideUrl)
                        .placeholder(R.drawable.placeholder)
                        .into(holder.thumb);
            } else {
                holder.thumb.setImageResource(R.drawable.placeholder);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return productList.get(position) == null ? VIEW_TYPE_PROGRESS : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void append(List<Product> products) {
        productList.addAll(products);
        notifyDataSetChanged();
    }

    public void addLoadingFooter() {
        productList.add(null);
        notifyItemInserted(productList.size() - 1);
    }

    public void removeLoadingFooter() {
        int position = productList.size() - 1;

        productList.remove(position);
        notifyItemRemoved(position);
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ProductActivity.class);
            intent.putExtra(ProductActivity.EXTRA_PRODUCT, productList.get(getAdapterPosition()));

            context.startActivity(intent);
        }
    }

    public class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
