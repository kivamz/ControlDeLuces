package com.kmzwebdesign.controldeluces.fragments;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.kmzwebdesign.controldeluces.ListaDispositivosBTActivity;
import com.kmzwebdesign.controldeluces.MainActivity;
import com.kmzwebdesign.controldeluces.R;

import java.util.ArrayList;

public class InicioFragment extends Fragment {

    public static InicioFragment inicioFragment;

    private BluetoothAdapter BTAdapter;
    Button btn_conectar_dispositivo;
    private SharedPreferences sharedPreferences;

    private ProgressDialog mProgressDlg;

    public static int REQUEST_BLUETOOTH = 1;

    private ArrayList<BluetoothDevice> mDeviceList = new ArrayList<BluetoothDevice>();


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

        btn_conectar_dispositivo = (Button) getView().findViewById(R.id.btn_conectar_dispositivo);

        mProgressDlg 		= new ProgressDialog(getActivity());

        mProgressDlg.setMessage("Buscando...");
        mProgressDlg.setCancelable(false);
        mProgressDlg.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                BTAdapter.cancelDiscovery();
            }
        });

        sharedPreferences = this.getActivity().getSharedPreferences("config_ini", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        String nombre_dispositivo = sharedPreferences.getString("dispositivo_bluetooth", null);
        if(nombre_dispositivo == null){
            btn_conectar_dispositivo.setText("Buscar dispositivo");
            btn_conectar_dispositivo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BTAdapter.startDiscovery();
                }
            });
        }else{
            btn_conectar_dispositivo.setText("Conectar a "+nombre_dispositivo);
            btn_conectar_dispositivo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(getActivity(), "Conectar a Dispositivo", Toast.LENGTH_LONG).show();
//                    editor.putString("dispositivo_bluetooth", null);
//                    editor.apply();
//                    startActivity(new Intent(getActivity(), MainActivity.class));
//                    getActivity().finish();
                }
            });
        }

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

        IntentFilter filter = new IntentFilter();

        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        getActivity().registerReceiver(mReceiver, filter);
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void showEnabled() {
//        mStatusTv.setText("Bluetooth is On");
//        mStatusTv.setTextColor(Color.BLUE);
//
//        mActivateBtn.setText("Disable");
//        mActivateBtn.setEnabled(true);
//
//        mPairedBtn.setEnabled(true);
//        mScanBtn.setEnabled(true);
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);

                if (state == BluetoothAdapter.STATE_ON) {
                    showToast("Activado");

                    showEnabled();
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                mDeviceList = new ArrayList<BluetoothDevice>();

                mProgressDlg.show();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                mProgressDlg.dismiss();

                Intent newIntent = new Intent(getActivity(), ListaDispositivosBTActivity.class);

                newIntent.putParcelableArrayListExtra("device.list", mDeviceList);

                startActivity(newIntent);
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                mDeviceList.add(device);

                showToast("Dispositivo Encontrado " + device.getName());
            }
        }
    };

}
