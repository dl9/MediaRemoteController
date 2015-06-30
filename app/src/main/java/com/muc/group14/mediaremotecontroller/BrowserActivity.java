package com.muc.group14.mediaremotecontroller;

import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.ArrayAdapter;

import org.fourthline.cling.android.AndroidUpnpService;
import org.fourthline.cling.android.AndroidUpnpServiceImpl;
import org.fourthline.cling.model.meta.Device;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by David on 30.06.2015.
 */
public class BrowserActivity extends ListActivity {

    private AndroidUpnpService upnpService;
    private ArrayAdapter listAdapter;
    private BrowseRegistryListener browseRegistryListener = new BrowseRegistryListener();

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            upnpService = (AndroidUpnpService) service;
            listAdapter.clear();
            upnpService.getRegistry().addListener(browseRegistryListener);

            for(Device device: upnpService.getRegistry().getDevices()){
                browseRegistryListener.deviceAdded(device);
            }
            upnpService.getControlPoint().search();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            upnpService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1);
        setListAdapter(listAdapter);

        getApplicationContext().bindService(new Intent(this, AndroidUpnpServiceImpl.class),serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(upnpService!=null){
            upnpService.getRegistry().removeListener(browseRegistryListener);
        }

        getApplicationContext().unbindService(serviceConnection);
    }
}
