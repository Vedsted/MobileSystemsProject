package yanf.app;

import android.util.Log;

import com.github.megatronking.netbare.http.HttpRequest;
import com.github.megatronking.netbare.http.HttpRequestHeaderPart;
import com.github.megatronking.netbare.injector.InjectorCallback;

import java.io.IOException;
import java.util.HashSet;

public class CookieInjector extends ObservableSimpleHttpInjector {
    private HttpRequest httpRequest;
    private HttpRequestHeaderPart mHoldRequestHeader;
    private HashSet<String> blacklist;

    public CookieInjector(HashSet<String> blacklist) {
        this.blacklist = blacklist;
    }

    @Override
    public boolean sniffRequest(HttpRequest request) {
        httpRequest = request;
        for (String s : blacklist) {
            if (httpRequest.url().contains(s)){
                Log.i("Cookie Remover", "removing cookies for: " + httpRequest.url());
                this.notifyHitListeners(new Hit(httpRequest.url(), httpRequest.host(), Hit.HitType.COOKIE));
                return true;
            }
        }

        Log.i("Cookie Remover", "Passage allowed for url: " + httpRequest.url());
        return false;
    }

    @Override
    public void onRequestInject(HttpRequestHeaderPart header, InjectorCallback callback) throws IOException {
        mHoldRequestHeader = header;
        HttpRequestHeaderPart injectHeader = mHoldRequestHeader.newBuilder().replaceHeader("Cookie","").build();
        callback.onFinished(injectHeader);
    }

}
