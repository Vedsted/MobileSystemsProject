package yanf.app;

import android.util.Log;

import com.github.megatronking.netbare.http.HttpBody;
import com.github.megatronking.netbare.http.HttpRequest;
import com.github.megatronking.netbare.http.HttpRequestHeaderPart;
import com.github.megatronking.netbare.injector.InjectorCallback;
import com.github.megatronking.netbare.injector.SimpleHttpInjector;

import java.io.IOException;
import java.util.HashSet;

public class CookieInterceptor extends SimpleHttpInjector {

    private HttpRequestHeaderPart mHoldRequestHeader;

    private HashSet<String> blacklist;

    public CookieInterceptor() {
        this.blacklist = new HashSet<>();
    }


    public CookieInterceptor(HashSet<String> blacklist) {
        this.blacklist = blacklist;
    }

    @Override
    public boolean sniffRequest(HttpRequest request) {

        for (String s : blacklist) {
            if (request.url().contains(s)){
                Log.i("Cookie Remover", "removing cookies for: " + request.url());
                return true;
            }
        }

        Log.i("Cookie Remover", "Passage allowed for url: : " + request.url());
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

    public String getDomainName(String url) {
        try {
            URI uri = new URI(url);
            String domain = uri.getHost();
            return domain.startsWith("www.") ? domain.substring(4) : domain;
        } catch(URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
