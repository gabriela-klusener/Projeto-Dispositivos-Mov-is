package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;

public class CustomView extends View {

    private Paint paint;
    private int circleColor;
    public static final String PREFS_NAME = "ColorPreferences";
    public static final String COLOR_KEY = "circle_color";
    private SharedPreferences sharedPreferences;

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setAntiAlias(true);

        // Inicializa as preferências
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Define a cor padrão do XML ou azul se não tiver no XML
        circleColor = sharedPreferences.getInt(COLOR_KEY, getResources().getColor(R.color.circle_color_default));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        // Define a cor do círculo principal e preenche o círculo maior
        paint.setColor(circleColor);
        paint.setStyle(Paint.Style.FILL); // Preencher o círculo principal
        int radius = Math.min(width, height) / 3;
        canvas.drawCircle(width / 2, height / 2, radius, paint);

        // Desenha os círculos concêntricos na cor preta (contorno)
        paint.setColor(Color.BLACK); // Muda para a cor preta
        paint.setStyle(Paint.Style.STROKE); // Contorno dos círculos concêntricos
        paint.setStrokeWidth(5); // Espessura do contorno
        for (int i = 1; i <= 4; i++) {
            canvas.drawCircle(width / 2, height / 2, radius * i / 4, paint);
        }

        // Desenha retas ortogonais dentro do círculo
        paint.setStrokeWidth(5); // Espessura das linhas
        // Linha vertical (de cima para baixo)
        canvas.drawLine(width / 2, height / 2 - radius, width / 2, height / 2 + radius, paint);
        // Linha horizontal (da esquerda para a direita)
        canvas.drawLine(width / 2 - radius, height / 2, width / 2 + radius, height / 2, paint);
    }

    // Método para atualizar a cor do círculo
    public void updateColor(int newColor) {
        circleColor = newColor;
        invalidate(); // Redesenha o componente
        saveColor(newColor); // Salva a cor nas Preferências
    }

    // Salva a cor nas Preferências
    private void saveColor(int color) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(COLOR_KEY, color);
        editor.apply();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // Chama o diálogo de seleção de cor com três opções
            ((MainActivity) getContext()).openColorDialog(this);
        }
        return true;
    }
}
