package csf.itesm.mx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private final String MSG = "ACL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(MSG,"Estoy dentro del método onCreate()");
    }

    public void onStart() {
        super.onStart();
        Log.d(MSG,"Estoy dentro del método onStart()");
    }

    public void onResume() {
        super.onResume();
        Log.d(MSG,"Estoy dentro del método onResume()");
    }

    public void onPause() {
        super.onPause();
        Log.d(MSG,"Estoy dentro del método onPause()");
    }

    public void onStop() {
        super.onStop();
        Log.d(MSG,"Estoy dentro del método onStop()");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(MSG,"Estoy dentro del método onDestroy()");
    }
}
