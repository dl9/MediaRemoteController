package com.muc.group14.mediaremotecontroller;
import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Debug;
import android.os.IBinder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.fourthline.cling.android.AndroidUpnpService;
import org.fourthline.cling.android.AndroidUpnpServiceImpl;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.registry.DefaultRegistryListener;
import org.fourthline.cling.registry.Registry;

public class MainActivity extends ListActivity {

    private AndroidUpnpService upnpService;
    private ArrayAdapter<DeviceDisplay> listAdapter;
    private BrowseRegistryListener browseRegistryListener = new BrowseRegistryListener();

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            upnpService = (AndroidUpnpService) service;
            listAdapter.clear();
            upnpService.getRegistry().addListener(browseRegistryListener);

            Log.d("test", "abc");
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
        Log.d("test", "onCreate");
        //org.seamless.util.logging.LoggingUtil.resetRootHandler(new FixedAndroidLogHandler());

        listAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        setListAdapter(listAdapter);

        getApplicationContext().bindService(
                new Intent(this, AndroidUpnpServiceImpl.class),
                serviceConnection,
                Context.BIND_AUTO_CREATE
        );
        Log.d("listAdapter Count", ""+listAdapter.getCount());
        for(int i=0; i<listAdapter.getCount(); i++){
            Log.d("listAdapter", listAdapter.getItem(i).toString());
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(upnpService!=null){
            upnpService.getRegistry().removeListener(browseRegistryListener);
        }

        getApplicationContext().unbindService(serviceConnection);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        menu.add(0, 0, 0,R.string.searchLAN).setIcon(android.R.drawable.ic_menu_search);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case 0:
                if(upnpService == null)
                    break;
                Toast.makeText(this, R.string.searchingLAN, Toast.LENGTH_SHORT).show();
                upnpService.getRegistry().removeAllRemoteDevices();
                upnpService.getControlPoint().search();
                break;
        }
        return false;

    }

    protected class BrowseRegistryListener extends DefaultRegistryListener {

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

        public void deviceAdded(final Device device) {
            runOnUiThread(new Runnable() {
                public void run () {
                    DeviceDisplay d = new DeviceDisplay(device);
                    int position = listAdapter.getPosition(d);
                    if(position >= 0) {
                        listAdapter.remove(d);
                        listAdapter.insert(d, position);
                    }
                    else {
                        listAdapter.add(d);
                    }
                }
            });
        }

        public void deviceRemoved(final Device device){
            runOnUiThread(new Runnable(){
                public void run(){
                    listAdapter.remove(new DeviceDisplay(device));
                }
            });
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
}