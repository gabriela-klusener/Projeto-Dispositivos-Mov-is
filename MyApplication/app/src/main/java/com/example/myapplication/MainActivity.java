package com.example.myapplication;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {


    private CustomView customView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        customView = findViewById(R.id.custom_view);


        // Recupera a cor inicial do círculo (preferências ou XML)
        SharedPreferences sharedPreferences = getSharedPreferences(CustomView.PREFS_NAME, MODE_PRIVATE);
        int savedColor = sharedPreferences.getInt(CustomView.COLOR_KEY, getResources().getColor(R.color.circle_color_default));


        customView.updateColor(savedColor); // Seta a cor inicial do círculo
    }


    // Abre o diálogo com três opções de cor
    public void openColorDialog(final CustomView view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecione a Cor");


        // Opções de cor
        String[] colorOptions = {"Vermelho", "Azul", "Verde"};


        // Define o comportamento ao clicar em uma das opções
        builder.setItems(colorOptions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: // Vermelho
                        view.updateColor(Color.RED);
                        break;
                    case 1: // Azul
                        view.updateColor(Color.BLUE);
                        break;
                    case 2: // Verde
                        view.updateColor(Color.GREEN);
                        break;
                }
            }
        });


        // Mostra o diálogo
        builder.create().show();
    }
}
