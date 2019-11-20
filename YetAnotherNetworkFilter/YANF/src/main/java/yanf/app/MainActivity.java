package yanf.app;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;


import com.github.megatronking.netbare.NetBare;
import com.github.megatronking.netbare.NetBareConfig;
import com.github.megatronking.netbare.NetBareListener;
import com.github.megatronking.netbare.http.HttpInjectInterceptor;
import com.github.megatronking.netbare.http.HttpInterceptorFactory;
import com.github.megatronking.netbare.ssl.JKS;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NetBareListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = "MainActivity";
    private int REQUEST_CODE_PREPARE = 1;


    private NetBare mNetBare;

    private Button mActionButton;

    private MozillaBlackList blackList;
    private String currentlySelectedBlackList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.blackList = LoadBlackList.loadBlackList(this);
        ((Spinner)findViewById(R.id.spinner)).setOnItemSelectedListener(this);

        mNetBare = NetBare.get();

        mActionButton = findViewById(R.id.action);
        mActionButton.setOnClickListener((View view) -> {
            if (mNetBare.isActive()) {
                mNetBare.unregisterNetBareListener(this);
                mNetBare.stop();
            } else {
                prepareNetBare();
            }
        });

        mNetBare.registerNetBareListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNetBare.unregisterNetBareListener(this);
        mNetBare.stop();
    }

    @Override
    public void onServiceStarted() {
        this.runOnUiThread(() -> mActionButton.setText(R.string.stop_yanf));
    }


    @Override
    public void onServiceStopped() {
        this.runOnUiThread(() -> mActionButton.setText(R.string.start_yanf));
    }


    private void prepareNetBare() {
        // Install a self-signed certificate
        if (!JKS.isInstalled(this, App.JSK_ALIAS)) {
            try {
                JKS.install(this, App.JSK_ALIAS, App.JSK_ALIAS);
            } catch (IOException e) {
                Log.e(TAG, "prepareNetBare: installation failed");
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }
            return;
        }
        // Configuring VPN
        Intent intent = NetBare.get().prepare();

        if (intent != null) {
            startActivityForResult(intent, REQUEST_CODE_PREPARE);
            return;
        }
        // Start the NetBare service
        mNetBare.start(NetBareConfig.defaultHttpConfig(App.getInstance().getJSK(), interceptorFactories()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_PREPARE) {
            prepareNetBare();
        }
    }

    private List<HttpInterceptorFactory> interceptorFactories() {
        HttpInterceptorFactory interceptor = HttpInjectInterceptor.createFactory(new CookieInterceptor(this.blackList.getDomains(this.currentlySelectedBlackList)));
        //HttpInterceptorFactory interceptor = HttpInjectInterceptor.createFactory(new AdvertisementInjector(this.blackList.getDomains(this.currentlySelectedBlackList)));
        //HttpInterceptorFactory interceptor = HttpInjectInterceptor.createFactory(new LocationInjector());
        //HttpInterceptorFactory interceptor = HttpInjectInterceptor.createFactory(new CookieInterceptor());

        return Arrays.asList(interceptor);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        this.currentlySelectedBlackList = String.valueOf(parent.getItemAtPosition(pos));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // fuck this
    }
}
