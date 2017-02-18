package com.kmzwebdesign.controldeluces;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ConfiguracionPaso2Activity extends AppCompatActivity {

    EditText[] nombre_canales;
    private SharedPreferences sharedPreferences;
    String nombreControlador;
    int numeroCanales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion_paso2);

        Bundle info = getIntent().getExtras();
        nombreControlador = info.getString("nombreControlador");
        numeroCanales = info.getInt("numeroCanales");

        sharedPreferences = this.getSharedPreferences("config", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        nombre_canales = new EditText[numeroCanales];
        Toast.makeText(this, "Nº canales "+numeroCanales, Toast.LENGTH_LONG).show();
        LinearLayout ll_lista_canales = (LinearLayout) findViewById(R.id.ll_lista_canales);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,0,0,24);
        for (int i = 1; i <= numeroCanales; i++){
            EditText editText = new EditText(this);
            editText.setHint("Nombre canal " + i);
            editText.setMaxLines(1);
            editText.setLayoutParams(layoutParams);
            nombre_canales[i-1] = editText;

            ll_lista_canales.addView(editText);
            Button button = new Button(this);
            button.setText(R.string.guardar);
            button.setTextColor(Color.BLACK);
            button.setLayoutParams(layoutParams);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < nombre_canales.length; i++){
                        if(nombre_canales[i].getText().toString().trim().length() == 0){
                            Toast.makeText(getApplicationContext(), "Introduce el nombre del canal "+(i+1), Toast.LENGTH_LONG).show();
                            break;
                        }

                    }
                    editor.putBoolean("config_ini", true);
                    editor.putInt("numeroControladores", 1);
                    editor.putString("nombreControlador_1", nombreControlador);
                    editor.putInt("canalesControlador_1", numeroCanales);

                    if(editor.commit()){
                        Toast.makeText(getApplicationContext(), "Configuración finalizada.", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                }
            });
            ll_lista_canales.addView(button);
        }
    }

}
