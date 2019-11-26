package yanf.yanf_energytest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import static android.os.BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER;

public class MainActivity extends AppCompatActivity {

    private Button btnStart;
    private EditText inputNumberOfReqs, inputTarget;
    private ProgressBar progressbar;
    private WebView webView;
    private TextView txtEngergy;
    private int numberOfRequestsGoal, numberOfRequestsSent;
    private String target;
    private float batteryStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputNumberOfReqs = findViewById(R.id.inputNumberOfRequests);
        inputTarget = findViewById(R.id.inputTarget);
        progressbar = findViewById(R.id.progRequests);
        webView = findViewById(R.id.webView);
        txtEngergy = findViewById(R.id.txtEnergyConsumed);
        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(view -> startTest(view));

        this.batteryStart = getBatteryInPercent();

        Toast.makeText(this, "Application ready", Toast.LENGTH_SHORT).show();
    }

    public void startTest(View view) {

        this.target = inputTarget.getText().toString();
        this.numberOfRequestsGoal = Integer.valueOf(inputNumberOfReqs.getText().toString());
        this.numberOfRequestsSent = 0;
        this.progressbar.setMax(numberOfRequestsGoal);
        this.progressbar.setMin(0);
        this.progressbar.setProgress(0);

        webView.getSettings().setJavaScriptEnabled(true);


        this.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (numberOfRequestsSent == numberOfRequestsGoal) {
                    Log.i("MainActivity", "Required number of requests met");
                    txtEngergy.setText("Engergy consumed: " + (batteryStart - getBatteryInPercent()) + "%");
                    return;
                }

                numberOfRequestsSent++;
                Log.i("MainActivity", "Sending request nr:" + numberOfRequestsSent);

                updateProgress(numberOfRequestsSent);
                Log.i("MainActivity", "Finished loading ");
            }
        });

        updateProgress(0);
    }

    private void updateProgress(int currentReqNumber){
        this.progressbar.setProgress(currentReqNumber);
        this.webView.loadUrl(this.target);
        Log.i("MainActivity", "update");
    }

    private float getBatteryInPercent(){
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, ifilter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPct = level * 100 / (float)scale;
        return batteryPct;
    }

}
