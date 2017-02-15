package com.kmzwebdesign.controldeluces.fragments;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kmzwebdesign.controldeluces.R;

public class InicioFragment extends Fragment {

    public static InicioFragment inicioFragment;

    //private DeviceListFragment mDeviceListFragment;
    private BluetoothAdapter BTAdapter;

    public static int REQUEST_BLUETOOTH = 1;


    public InicioFragment() {
        // Required empty public constructor
    }

    public static InicioFragment newInstance(){
        if (inicioFragment == null){
            inicioFragment = new InicioFragment();
        }
        return inicioFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BTAdapter = BluetoothAdapter.getDefaultAdapter();
        // Phone does not support Bluetooth so let the user know and exit.
        if (BTAdapter == null) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("No compatible")
                    .setMessage("Tu teléfono no soporta Bluetooth")
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        if (!BTAdapter.isEnabled()) {
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBT, REQUEST_BLUETOOTH);
        }else {
            Toast.makeText(getActivity(), "Bluetooth conectado", Toast.LENGTH_LONG).show();
        }
    }
}
