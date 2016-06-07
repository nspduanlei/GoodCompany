package com.apec.android.mvp.views;

import com.apec.android.domain.entities.user.ShopCart;

/**
 * Created by duanlei on 2016/6/7.
 */
public interface ShoppingCartView extends View {
    void bindCart(ShopCart shopCart);
}
