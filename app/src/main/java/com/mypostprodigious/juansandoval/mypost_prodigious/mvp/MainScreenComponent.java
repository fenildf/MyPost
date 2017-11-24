package com.mypostprodigious.juansandoval.mypost_prodigious.mvp;


import com.mypostprodigious.juansandoval.mypost_prodigious.NetworkModule.NetComponent;
import com.mypostprodigious.juansandoval.mypost_prodigious.Utils.CustomScope;
import com.mypostprodigious.juansandoval.mypost_prodigious.mvp.View.MainActivity;

import dagger.Component;

@CustomScope
@Component(dependencies = NetComponent.class, modules = MainScreenModule.class)
public interface MainScreenComponent {
    void inject(MainActivity activity);
}
