package com.apec.android.injector.components;

import com.apec.android.domain.usercase.AddDeliveryUseCase;
import com.apec.android.domain.usercase.DelAddressUseCase;
import com.apec.android.domain.usercase.GetAllAddressUseCase;
import com.apec.android.domain.usercase.GetAreaUseCase;
import com.apec.android.domain.usercase.SetDefaultAddressUseCase;
import com.apec.android.domain.usercase.UpdateDeliveryUseCase;
import com.apec.android.injector.Activity;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.injector.modules.AddressModule;
import com.apec.android.views.activities.AddAddressActivity;
import com.apec.android.views.activities.EditAddressActivity;
import com.apec.android.views.activities.ManageAddressActivity;

import dagger.Component;

/**
 * Created by duanlei on 2016/5/10.
 */
@Activity
@Component(dependencies = AppComponent.class, modules = {AddressModule.class, ActivityModule.class})
public interface AddressComponent extends ActivityComponent {

    void inject(ManageAddressActivity manageAddressActivity);
    void inject(AddAddressActivity addAddressActivity);
    void inject(EditAddressActivity editAddressActivity);

    GetAllAddressUseCase getAllAddressUseCase();
    SetDefaultAddressUseCase setDefaultAddressUseCase();
    DelAddressUseCase delAddressUseCase();
    UpdateDeliveryUseCase updateDeliveryUseCase();
    AddDeliveryUseCase addDeliveryUseCase();
    GetAreaUseCase getAreaUseCase();
}
