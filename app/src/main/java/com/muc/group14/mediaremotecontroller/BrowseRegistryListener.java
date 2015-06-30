package com.muc.group14.mediaremotecontroller;

import android.app.Activity;
import android.widget.ListAdapter;
import android.widget.Toast;

import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.registry.DefaultRegistryListener;
import org.fourthline.cling.registry.Registry;

/**
 * Created by David on 30.06.2015.
 */
public class BrowseRegistryListener extends DefaultRegistryListener {

    /*public void remoteDeviceDiscoveryStarted(Registry registry, RemoteDevice device) {
        deviceAdded(device);
    }*/

    @Override
    public void remoteDeviceAdded(Registry registry, RemoteDevice device) {
        super.remoteDeviceAdded(registry, device);
    }

    @Override
    public void remoteDeviceRemoved(Registry registry, RemoteDevice device) {
        super.remoteDeviceRemoved(registry, device);
    }

    @Override
    public void localDeviceAdded(Registry registry, LocalDevice device) {
        super.localDeviceAdded(registry, device);
    }

    @Override
    public void localDeviceRemoved(Registry registry, LocalDevice device) {
        super.localDeviceRemoved(registry, device);
    }

    //TODO runOnUi
    public void deviceAdded(final Device device) {
        new Thread() {
            runOnUiThread(new Runnable() {
                public void run () {
                    DeviceDisplay d = new DeviceDisplay(device);
                    int position = listAdapter.getPosition(d);
                }
            }

            );
        }
    }
   /* @Override
    public void remoteDeviceDiscoveryFailed(Registry registry, final RemoteDevice device, Exception ex) {
        ((Activity)this.getContext())runOnUiThread(new Runnable(){
            public void run(){
                Toast.makeText(BrowserActivity.this, "Discovery failed of '"+device.getDisplayString()+
                "': "+ (ex !=null ? ex.toString() : "Couldn't retrieve device/service descriptions"),Toast.LENGTH_LONG).show();
            }
        });
        deviceRemoved(device);
    }*/
}
