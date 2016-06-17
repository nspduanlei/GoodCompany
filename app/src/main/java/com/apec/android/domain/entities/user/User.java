package com.apec.android.domain.entities.user;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2016/2/25.
 */
public class User extends DataSupport {

    private int userId;
    private String shopName;
    private String name;
    private String phone;

    @Column(ignore = true)
    private Address addrRes;

    private String shopPic;

    public String getShopPic() {
        return shopPic;
    }

    public void setShopPic(String shopPic) {
        this.shopPic = shopPic;
    }

    public Address getAddrRes() {
        return addrRes;
    }

    public void setAddrRes(Address addrRes) {
        this.addrRes = addrRes;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
