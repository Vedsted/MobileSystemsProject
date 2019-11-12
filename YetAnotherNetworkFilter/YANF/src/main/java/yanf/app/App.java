package yanf.app;

import android.app.Application;
import android.content.Context;

import com.github.megatronking.netbare.NetBare;
import com.github.megatronking.netbare.NetBareUtils;
import com.github.megatronking.netbare.ssl.JKS;

import me.weishu.reflection.Reflection;

public class App extends Application {


    public final static String JSK_ALIAS = "YANF";

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    private JKS mJKS;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        // Create a self-signed certificate
        mJKS = new JKS(this, JSK_ALIAS, JSK_ALIAS.toCharArray(), JSK_ALIAS, JSK_ALIAS, JSK_ALIAS, JSK_ALIAS, JSK_ALIAS);

        // Initialize NetBare
        NetBare.get().attachApplication(this, BuildConfig.DEBUG);
    }

    public JKS getJSK() {
        return mJKS;
    }

    @Override
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // On android Q, we can't access Java8EngineWrapper with reflect.
        if (NetBareUtils.isAndroidQ()) {
            Reflection.unseal(base);
        }
    }

}