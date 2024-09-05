
package com.lgarreta.mascotasinfo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lgarreta.mascotasinfo.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MascotasInfoActivity extends AppCompatActivity {
    private TextView nombreInput;
    private TextView tipoInput;
    private TextView edadInput;
    private TextView propietarioInput;
    private TextView cedulaInput;
    private TextView telefonoInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //message = findViewById(R.id.clickCounter);
        Button guardarButton = findViewById(R.id.guardarButton);
        //Define and attach click listener
        guardarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEnviarInfo();
            }
        });

        // Inputs
        nombreInput = findViewById(R.id.nombreInput);
        tipoInput = findViewById(R.id.claseInput);
        edadInput = findViewById(R.id.edadInput);
        propietarioInput = findViewById(R.id.propietarioInput);
        cedulaInput = findViewById(R.id.cedulaInput);
        telefonoInput = findViewById(R.id.telefonoInput);

    }

    private void onEnviarInfo() {
        String lineaTexto = crearLineaTextoDesdeInputs();
        String nombreArchivo = "mascotas_datos.txt";

        FileUtils.appendStringToFile (lineaTexto, nombreArchivo);
        // After the form is submitted and data is saved
        Toast.makeText(getApplicationContext(), "Datos guardados en 'mascotas.txt'", Toast.LENGTH_SHORT).show();

        limpiarInputs();
    }

    private void limpiarInputs () {
        nombreInput.setText("");
        tipoInput.setText("");
        edadInput.setText("");
        propietarioInput.setText("");
        cedulaInput.setText("");
        telefonoInput.setText("");
    }

    private String crearLineaTextoDesdeInputs() {
        // Retrieve the text from the fields
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-MM");

        String text0 = sdf.format (new Date());
        String text1 = nombreInput.getText().toString();
        String text2 = tipoInput.getText().toString();
        String text3 = edadInput.getText().toString();
        String text4 = propietarioInput.getText().toString();
        String text5 = cedulaInput.getText().toString();
        String text6 = telefonoInput.getText().toString();

        String infoString = String.format  ("\"%s\",'\"%s\",'\"%s\",'\"%s\",'\"%s\",'\"%s\",'\"%s\"\n",
                text0, text1, text2, text3, text4, text5, text6);
        return infoString;

    }
}
