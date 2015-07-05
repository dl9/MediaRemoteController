package com.muc.group14.mediaremotecontroller;


import android.net.wifi.WifiManager;
import android.util.Log;

import org.fourthline.cling.UpnpServiceConfiguration;
import org.fourthline.cling.android.AndroidUpnpServiceConfiguration;
import org.fourthline.cling.android.AndroidUpnpServiceImpl;
import org.fourthline.cling.model.types.ServiceType;
import org.fourthline.cling.model.types.UDAServiceType;

/**
 * Created by David on 30.06.2015.
 */
public class MyUpnpServiceImpl extends AndroidUpnpServiceImpl{

    //TODO include WifiManager
    @Override
    protected UpnpServiceConfiguration createConfiguration() {
        Log.i("test","createConfiguration...");
        return new AndroidUpnpServiceConfiguration(){
            @Override
            public ServiceType[] getExclusiveServiceTypes(){
                return new ServiceType[]{
                        new UDAServiceType("AVTransport")
                };
            }
        };
    }
}
