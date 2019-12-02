package yanf.app;

import android.support.annotation.NonNull;
import android.util.Log;

import com.github.megatronking.netbare.http.HttpBody;
import com.github.megatronking.netbare.http.HttpRequest;
import com.github.megatronking.netbare.http.HttpRequestHeaderPart;
import com.github.megatronking.netbare.http.HttpResponse;
import com.github.megatronking.netbare.injector.BlockedHttpInjector;
import com.github.megatronking.netbare.injector.InjectorCallback;

import java.util.HashSet;

public class AdvertisementInjector extends ObservableSimpleHttpInjector {

    private HttpRequest httpRequest;
    private HashSet<String> blacklist;


    public AdvertisementInjector(HashSet<String> blacklist) {
        this.blacklist = blacklist;
    }

    @Override
    public boolean sniffRequest(@NonNull HttpRequest request) {
        httpRequest = request;

        for (String s : blacklist) {
            if (httpRequest.url().contains(s)) {
                Log.i("AdvertisementInjector", httpRequest.url() + " is blocked");
                this.notifyHitListeners(new Hit(httpRequest.url(), httpRequest.host(), Hit.HitType.ADVERTISEMENT));
                return true;
            }
        }
        Log.i("AdvertisementInjector", "Passage allowed for url: " + httpRequest.url());
        return false;
    }

    @Override
    public boolean sniffResponse(@NonNull HttpResponse response) {
        return false;
    }

    @Override
    public void onRequestInject(HttpRequestHeaderPart header, InjectorCallback callback) { }

    @Override
    public void onRequestInject(HttpRequest request, HttpBody body, InjectorCallback callback) { }

    @Override
    public void onRequestFinished(HttpRequest request) { }
}
