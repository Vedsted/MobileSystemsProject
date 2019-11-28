package yanf.app;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;


import com.github.megatronking.netbare.NetBare;
import com.github.megatronking.netbare.NetBareConfig;
import com.github.megatronking.netbare.NetBareListener;
import com.github.megatronking.netbare.http.HttpInjectInterceptor;
import com.github.megatronking.netbare.http.HttpInterceptorFactory;
import com.github.megatronking.netbare.ssl.JKS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity implements NetBareListener, AdapterView.OnItemSelectedListener {
    private static final String TAG = "MainActivity";
    private int REQUEST_CODE_PREPARE = 1;

    private NetBare mNetBare;

    private Button mActionButton;
    private ToggleButton adsButton;
    private ToggleButton cookieButton;

    private boolean adsClicked = false;
    private boolean cookieClicked = false;

    private String currentlySelectedBlackList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Spinner)findViewById(R.id.spinner)).setOnItemSelectedListener(this);

        cookieButton = findViewById(R.id.cookieButton);
        cookieButton.setOnClickListener(v -> {
            Log.i("Cookie button ", "is clicked");
            cookieClicked = true;
        });

        adsButton = findViewById(R.id.adsButton);
        adsButton.setOnClickListener(v -> {
            Log.i("Advertisement button ", "is clicked");
            adsClicked = true;
        });

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
        Log.i(TAG, "onDestroy: Netbare destroyed");
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
        Log.i(TAG, "prepareNetBare: Netbare starting");

        if(adsClicked) {
            mNetBare.start(NetBareConfig.defaultHttpConfig(App.getInstance().getJSK(), adsInterceptor()));
        }

        if (cookieClicked) {
            mNetBare.start(NetBareConfig.defaultHttpConfig(App.getInstance().getJSK(), cookiesInterceptor()));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_PREPARE) {
            prepareNetBare();
        }
    }

    private List<HttpInterceptorFactory> adsInterceptor() {
        HttpInterceptorFactory adInterceptor = HttpInjectInterceptor.createFactory(new AdvertisementInjector(LoadBlackList.loadBlackList(this).getDomains(currentlySelectedBlackList)));
        return Arrays.asList(adInterceptor);
    }

    private List<HttpInterceptorFactory> cookiesInterceptor() {
        HttpInterceptorFactory cookieInterceptor = HttpInjectInterceptor.createFactory(new CookieInterceptor(LoadBlackList.loadBlackList(this).getDomains(currentlySelectedBlackList)));
        return Arrays.asList(cookieInterceptor);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        this.currentlySelectedBlackList = String.valueOf(parent.getItemAtPosition(pos));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { }

    }
