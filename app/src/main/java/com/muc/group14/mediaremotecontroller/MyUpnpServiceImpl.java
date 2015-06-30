package com.muc.group14.mediaremotecontroller;

import android.net.wifi.WifiManager;

import org.fourthline.cling.UpnpServiceConfiguration;
import org.fourthline.cling.android.AndroidUpnpServiceConfiguration;
import org.fourthline.cling.android.AndroidUpnpServiceImpl;
import org.fourthline.cling.model.types.ServiceType;
import org.fourthline.cling.model.types.UDAServiceType;

/**
 * Created by David on 30.06.2015.
 */
public class MyUpnpServiceImpl extends AndroidUpnpServiceImpl{

    @Override
    protected UpnpServiceConfiguration createConfiguration(WifiManager wifiManager) {
        return new AndroidUpnpServiceConfiguration(wifiManager){
            @Override
            public ServiceType[] getExclusiveServiceTypes(){
                return new ServiceType[]{
                        new UDAServiceType("SwitchPower")
                };
            }
        };
    }
}
