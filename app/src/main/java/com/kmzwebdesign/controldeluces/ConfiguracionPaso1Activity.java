package com.kmzwebdesign.controldeluces;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ConfiguracionPaso1Activity extends AppCompatActivity {

    private Button btn_guardar_alias;
    private int num_canales = 0;
    public LinearLayout ll_canales;
    public LinearLayout ll_datos_canales;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion_paso1);

        sharedPreferences = this.getSharedPreferences("config", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        if(sharedPreferences.getBoolean("config_ini", false)){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        final EditText et_canales = (EditText) findViewById(R.id.et_num_canales);
        final EditText et_nombre_controlador = (EditText) findViewById(R.id.et_nombre_controlador);
        btn_guardar_alias = (Button) findViewById(R.id.btn_aceptar_canales);

        btn_guardar_alias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombreControlador = et_nombre_controlador.getText()+"";


                if (nombreControlador.trim().length() == 0){
                    Toast.makeText(getApplicationContext(), "Introduzca el nombre del controlador", Toast.LENGTH_SHORT).show();
                }else {
                    if (et_canales.getText().toString().trim().length() == 0){
                        Toast.makeText(getApplicationContext(), "Introduzca el nยบ de canales", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent abrirConfiguracion2 = new Intent(getApplicationContext(), ConfiguracionPaso2Activity.class);
                        num_canales = Integer.parseInt(et_canales.getText().toString());
                        abrirConfiguracion2.putExtra("numeroCanales", num_canales);
                        abrirConfiguracion2.putExtra("nombreControlador", nombreControlador);
                        startActivity(abrirConfiguracion2);
                        finish();
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences = this.getSharedPreferences("config", MODE_PRIVATE);
        if(sharedPreferences.getBoolean("config_ini", false)){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}
