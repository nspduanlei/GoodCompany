package com.apec.android.mvp.views;

import com.apec.android.domain.entities.user.Version;

/**
 * Created by duanlei on 2016/6/24.
 */
public interface MeView extends View {
    void isNewest();
    void noNewest(Version version);
}
