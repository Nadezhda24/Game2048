package com.example.a2048;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class Regictration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regictration);
    }

    public void onClick(View view) {
        final int[] status = {0}; // 1 - есть соединение с сервером: 0 - Нет соединения с сервером
        final Intent intent = new Intent(Regictration.this, Game.class);
        final EditText editTextName = (EditText) findViewById(R.id.editTextName);
        final EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        Client client_obj = null ;
        Thread thread = new Thread(new Runnable() { @Override public void run() {  try {

            Client client_obj = new Client();
            client_obj.run("[r] " + editTextName.getText()+ " " + editTextPassword.getText());
            status[0] = 1;

        } catch (final IOException e) {
            runOnUiThread(new Runnable() {

                public void run() {
                    final Toast toast = Toast.makeText(getApplicationContext(),
                            "Нет соединения с сервером. Вы вошли как анонимный игрок.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });

        } } }); thread.start();

        if ( editTextName.getText().length() == 0 && editTextPassword.getText().length() == 0 ) status[0] = 0;

        intent.putExtra("name", editTextName.getText().toString());
        intent.putExtra("skill", client_obj.ansServer);
        intent.putExtra("st", status[0]);
        startActivity(intent);


    }
}
