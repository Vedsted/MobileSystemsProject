package yanf.app;

import android.util.Log;

import com.github.megatronking.netbare.http.HttpBody;
import com.github.megatronking.netbare.http.HttpRequest;
import com.github.megatronking.netbare.http.HttpRequestHeaderPart;
import com.github.megatronking.netbare.injector.InjectorCallback;
import com.github.megatronking.netbare.injector.SimpleHttpInjector;

import java.io.IOException;
import java.util.HashSet;

public class AdvertisementInjector extends SimpleHttpInjector {

    private HttpRequestHeaderPart mHoldRequestHeader;

    private HashSet<String> blacklist;

    public AdvertisementInjector() {
        this.blacklist = new HashSet<>();
    }


    public AdvertisementInjector(HashSet<String> blacklist) {
        this.blacklist = blacklist;
        this.blacklist.add("adnami.io");
    }

    @Override
    public boolean sniffRequest(HttpRequest request) {

        for (String s : blacklist) {
            if (request.url().contains(s)){
                Log.i("AdvertisementInjector", "removing advertisements for: " + request.url());
                return true;
            }
        }

        Log.i("AdvertisementInjector", "Passage allowed for url: : " + request.url());
        return false;
    }

    @Override
    public void onRequestInject(HttpRequestHeaderPart header, InjectorCallback callback) throws IOException {
        //mHoldRequestHeader = header;

        //super.onRequestInject(header, callback);

        // Ignore
    }

    @Override
    public void onRequestInject(HttpRequest request, HttpBody body, InjectorCallback callback) throws IOException {

        // ignore
    }

    @Override
    public void onRequestFinished(HttpRequest request) {
        // Ignore
    }
}
