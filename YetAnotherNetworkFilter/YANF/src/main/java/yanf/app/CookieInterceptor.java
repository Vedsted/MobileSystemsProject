package yanf.app;

import android.util.Log;

import com.github.megatronking.netbare.http.HttpBody;
import com.github.megatronking.netbare.http.HttpRequest;
import com.github.megatronking.netbare.http.HttpRequestHeaderPart;
import com.github.megatronking.netbare.injector.InjectorCallback;
import com.github.megatronking.netbare.injector.SimpleHttpInjector;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;

public class CookieInterceptor extends SimpleHttpInjector {

    private HttpRequestHeaderPart mHoldRequestHeader;

    private HashSet<String> blacklist;

    private String TAG = "CookieInterceptor";

    public CookieInterceptor() {
        this.blacklist = new HashSet<>();
    }


    public CookieInterceptor(HashSet<String> blacklist) {
        this.blacklist = blacklist;
    }

    @Override
    public boolean sniffRequest(HttpRequest request) {

        if (blacklist.contains(getDomainName(request.url()))){
            Log.i(TAG, "removing cookies for: " + request.url());
            return true;
        }

        //Log.i("Cookie Remover", "Passage allowed for url: : " + request.url());
        return false;
    }

    @Override
    public void onRequestInject(HttpRequestHeaderPart header, InjectorCallback callback) throws IOException {
        mHoldRequestHeader = header;

        super.onRequestInject(header, callback);
    }

    @Override
    public void onRequestInject(HttpRequest request, HttpBody body, InjectorCallback callback) throws IOException {
        if (mHoldRequestHeader == null) {
            // rarely happens.
            return;
        }

        HttpRequestHeaderPart injectHeader = mHoldRequestHeader.newBuilder().removeHeader("Cookie").build();

        callback.onFinished(injectHeader);

        mHoldRequestHeader = null;
    }

    @Override
    public void onRequestFinished(HttpRequest request) {
        super.onRequestFinished(request);
    }

    private String getDomainName(String url) {
        String domain = "No Domain Found";

        try {
            URI uri = new URI(url);
            domain = uri.getHost();
            Log.i(TAG, "getDomainName: Found domain name: " + domain);
        } catch(URISyntaxException e) {
            Log.i(TAG, "getDomainName: Syntax error in URL: " + url);
            String[] split = url.split("/");
            domain = split[2];
            URI uri = null;
            try {
                uri = new URI(url);
            } catch (URISyntaxException ex) {
                ex.printStackTrace();
            }
            domain = uri.getHost();
            Log.i(TAG, "getDomainName: Found domain name: " + domain);
        }

        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }
}
