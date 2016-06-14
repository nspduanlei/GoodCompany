package com.apec.android.mvp.views;

import com.apec.android.domain.entities.user.ShopCartData;

/**
 * Created by duanlei on 2016/6/7.
 */
public interface ShoppingCartView extends View {
    void bindCart(ShopCartData shopCart);

    void onDataEmpty();
    void hideEmpty();
}
