package csf.itesm.proyectobien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoSplash extends AppCompatActivity {

    /*
        CLASE PARA SPLASH DE VIDEO
     */

    private VideoView muestraVideo;
    private MediaController controlador;

    private String usuario;
    public static final String ROL_ADMIN = "Administrador";
    private DBHelper databaseHelper;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_splash);

        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);

        /*
            Si SharedPreferences contiene username y password, entonces ya esta iniciada sesion
            Se pone en esta clase para no mostrar video siempre
         */
        if(sharedPreferences.contains("username") && sharedPreferences.contains("password")){

            usuario = sharedPreferences.getString("username", "");
            databaseHelper = new DBHelper(this);

            if (databaseHelper.checarRol(usuario).equals(ROL_ADMIN)) {
                startActivity(new Intent(VideoSplash.this,Administrador.class));
            }

            else {
                Intent login = new Intent(VideoSplash.this, Games.class);
                startActivity(login);
                Games.showName = 0;
                finish();
            }

        }

        try {
            muestraVideo = findViewById(R.id.videoView);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

            Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.videosplash);
            muestraVideo.setVideoURI(video);

            muestraVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                public void onCompletion(MediaPlayer mp) {
                    otraActividad();
                }
            });

            muestraVideo.start();

        } catch(Exception ex) {
            otraActividad();
        }
    }

    private void otraActividad() {
        if(isFinishing())
            return;
        startActivity(new Intent(this, Login.class));
        finish();
    }
}
