
package com.lgarreta.mascotasinfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MascotasInfoActivity extends AppCompatActivity {
    protected TextView nombreInput;
    private TextView tipoInput;
    private TextView edadInput;
    private TextView propietarioInput;
    private TextView cedulaInput;
    private TextView telefonoInput;

    protected Uri fileUri;
    private ActivityResultLauncher<Intent> createFileLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciarInputs();

        // Initialize the ActivityResultLauncher
        createFileLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::onActivityResult);

        // Trigger file creation
        createFile("mascotas-info.txt");

        Button guardarButton = findViewById(R.id.guardarButton);
        //Define and attach click listener
        guardarButton.setOnClickListener(new GuardarClickListener(this));
    }
    private void iniciarInputs () {
        // Inputs
        nombreInput = findViewById(R.id.nombreInput);
        tipoInput = findViewById(R.id.claseInput);
        edadInput = findViewById(R.id.edadInput);
        propietarioInput = findViewById(R.id.propietarioInput);
        cedulaInput = findViewById(R.id.cedulaInput);
        telefonoInput = findViewById(R.id.telefonoInput);
    }

    private void onActivityResult(ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
            fileUri = result.getData().getData();
            //writeToFile(this, fileUri, "Hello, World!");
        }
    }

    private void createFile(String fileName) {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, fileName);
        createFileLauncher.launch(intent);
    }

    public void appendToFile(Context context, Uri uri, String newText) {
        try (OutputStream outputStream = context.getContentResolver().openOutputStream(uri, "wa");
             OutputStreamWriter writer = new OutputStreamWriter(outputStream)) {
            writer.append(newText);
            writer.append("\n"); // Add a newline if needed
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void limpiarInputs() {
        nombreInput.setText("");
        tipoInput.setText("");
        edadInput.setText("");
        propietarioInput.setText("");
        cedulaInput.setText("");
        telefonoInput.setText("");
    }

    public String crearLineaTextoDesdeInputs() {
        // Retrieve the text from the fields
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-MM");
        String text0 = sdf.format(new Date());
        String text1 = nombreInput.getText().toString();
        String text2 = tipoInput.getText().toString();
        String text3 = edadInput.getText().toString();
        String text4 = propietarioInput.getText().toString();
        String text5 = cedulaInput.getText().toString();
        String text6 = telefonoInput.getText().toString();

        String infoString = String.format("\"%s\",'\"%s\",'\"%s\",'\"%s\",'\"%s\",'\"%s\",'\"%s\"\n",
                text0, text1, text2, text3, text4, text5, text6);
        return infoString;
    }
}

class GuardarClickListener implements View.OnClickListener {
    private final MascotasInfoActivity context;

    public GuardarClickListener (MascotasInfoActivity context) {
        this.context = context;
    }
    @Override
    public void onClick(View v) {
            //onEnviarInfo();
        Uri fileUri = context.fileUri;
        String lineaTexto = context.crearLineaTextoDesdeInputs();
        context.appendToFile (context, fileUri, lineaTexto);
        Toast.makeText(context, "Mascota adicionada!", Toast.LENGTH_SHORT).show();
        context.limpiarInputs();
        context.nombreInput.requestFocus();
    }
}
