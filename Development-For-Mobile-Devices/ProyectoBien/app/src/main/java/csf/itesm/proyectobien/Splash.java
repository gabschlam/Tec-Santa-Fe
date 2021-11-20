package csf.itesm.proyectobien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

     /*
        CLASE PARA SPLASH DE IMAGEN
     */

    private long tiempodeEspera = 5000; //milisegundos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {

                finish();
                Intent intentoPrincipal = new Intent().setClass(Splash.this, VideoSplash.class);
                startActivity(intentoPrincipal);
            }
        };

        Timer timer = new Timer();
        timer.schedule(tarea, tiempodeEspera);
    }
}
