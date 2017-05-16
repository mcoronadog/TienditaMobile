package com.example.shop.tienditaapp2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button boton;
    EditText usu,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usu=(EditText) findViewById(R.id.campo_usu);
        pass=(EditText) findViewById(R.id.campo_pass);
        boton=(Button) findViewById(R.id.button);

        boton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(usu.getText().toString().equals("root") && pass.getText().toString().equals("root")) {
                    Toast.makeText(getApplicationContext(),"Has ingresado exitosamente", Toast.LENGTH_LONG).show();
                    Intent intent1=new Intent(MainActivity.this, Home.class);
                    startActivity(intent1);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Datos incorrectos", Toast.LENGTH_LONG).show();
                    usu.setText("");
                    pass.setText("");
                }
            }
        });
    }
}
