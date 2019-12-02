package yanf.app;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.github.megatronking.netbare.NetBare;
import com.github.megatronking.netbare.NetBareConfig;
import com.github.megatronking.netbare.NetBareListener;
import com.github.megatronking.netbare.http.HttpInjectInterceptor;
import com.github.megatronking.netbare.http.HttpInterceptorFactory;
import com.github.megatronking.netbare.injector.HttpInjector;
import com.github.megatronking.netbare.ssl.JKS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<String, HttpInjector> injectors = new HashMap<>();
    private static final String COOKIE_INJECTOR = "COOKIE_INJECTOR";
    private static final String ADS_INJECTOR = "ADS_INJECTOR";
    private HttpInjector cookieInjector;
    private HttpInjector adsInjector;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeFilterSelection();
        initializeInterceptors();
        initializeSwitches();

        mNetBare = NetBare.get();

        mActionButton = findViewById(R.id.action);
        mActionButton.setOnClickListener((View view) -> {
            if (mNetBare.isActive()) {
                mNetBare.unregisterNetBareListener(this);
                mNetBare.stop();
                enableUIElements();
            } else {
                prepareNetBare();
                disableUIElements();
            }
        });

        mNetBare.registerNetBareListener(this);
    }

    private void disableUIElements() {
        findViewById(R.id.switchAds).setEnabled(false);
        findViewById(R.id.switchCookies).setEnabled(false);
        findViewById(R.id.spinner).setEnabled(false);
        mActionButton.setText(R.string.stop_yanf);
    }

    private void enableUIElements() {
        findViewById(R.id.switchAds).setEnabled(true);
        findViewById(R.id.switchCookies).setEnabled(true);
        findViewById(R.id.spinner).setEnabled(true);
        mActionButton.setText(R.string.start_yanf);
    }

    private void initializeFilterSelection() {
        ((Spinner)findViewById(R.id.spinner)).setOnItemSelectedListener(this);
        currentlySelectedBlackList = "None";
    }

    private void initializeInterceptors() {
        this.cookieInjector = new CookieInterceptor(LoadBlackList.loadBlackList(this).getDomains(currentlySelectedBlackList));
        this.adsInjector = new AdvertisementInjector(LoadBlackList.loadBlackList(this).getDomains(currentlySelectedBlackList));
    }

    private void initializeSwitches() {
        Switch switchCookies = findViewById(R.id.switchCookies);
        Switch switchAds = findViewById(R.id.switchAds);
        switchCookies.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    if (!injectors.containsKey(COOKIE_INJECTOR)) {
                        injectors.put(COOKIE_INJECTOR, cookieInjector);
                    }
                    // Set the style of the switch
                    switchCookies.setSwitchTextAppearance(getApplicationContext(), R.style.SwitchTextAppearanceOn);
                } else {
                    if (injectors.containsKey(COOKIE_INJECTOR)) {
                        injectors.remove(COOKIE_INJECTOR);
                    }
                    switchCookies.setSwitchTextAppearance(getApplicationContext(), R.style.SwitchTextAppearanceOff);
                }
            }
        });

        switchAds.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    if (!injectors.containsKey(ADS_INJECTOR)) {
                        injectors.put(ADS_INJECTOR, adsInjector);
                    }
                    // Set the style of the switch
                    switchAds.setSwitchTextAppearance(getApplicationContext(), R.style.SwitchTextAppearanceOn);
                } else {
                    if (injectors.containsKey(ADS_INJECTOR)) {
                        injectors.remove(ADS_INJECTOR);
                    }
                    switchAds.setSwitchTextAppearance(getApplicationContext(), R.style.SwitchTextAppearanceOff);
                }
            }
        });
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

        List<HttpInterceptorFactory> interceptorFactories = getInterceptorFactoriesList(this.injectors.values().toArray(new HttpInjector[0]));
        mNetBare.start(NetBareConfig.defaultHttpConfig(App.getInstance().getJSK(), interceptorFactories));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_PREPARE) {
            prepareNetBare();
        }
    }

    private List<HttpInterceptorFactory> getInterceptorFactoriesList(HttpInjector... injectors) {
        ArrayList<HttpInterceptorFactory> factories = new ArrayList<>();

        for (int i = 0; i < injectors.length; i++) {
            factories.add(HttpInjectInterceptor.createFactory(injectors[i]));
        }

        return factories;
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        this.currentlySelectedBlackList = String.valueOf(parent.getItemAtPosition(pos));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { }

}
