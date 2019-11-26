package yanf.app;

import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.util.Log;

import com.github.megatronking.netbare.http.HttpBody;
import com.github.megatronking.netbare.http.HttpRequest;
import com.github.megatronking.netbare.http.HttpRequestHeaderPart;
import com.github.megatronking.netbare.http.HttpResponse;
import com.github.megatronking.netbare.http.HttpResponseHeaderPart;
import com.github.megatronking.netbare.injector.BlockedHttpInjector;
import com.github.megatronking.netbare.injector.InjectorCallback;
import com.github.megatronking.netbare.injector.SimpleHttpInjector;
import com.github.megatronking.netbare.stream.ByteStream;
import com.github.megatronking.netbare.stream.Stream;

import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashSet;

public class AdvertisementInjector extends SimpleHttpInjector {

    private HttpRequest httpRequest;
    private HashSet<String> blacklist;
    private HttpRequestHeaderPart mHoldRequestHeader;



    public AdvertisementInjector() {
        this.blacklist = new HashSet<>();
        this.blacklist.add("10.94.122.2");
        this.blacklist.add("facebook.com");
    }

    public AdvertisementInjector(HashSet<String> blacklist) {
        this.blacklist = blacklist;
    }

    @Override
    public boolean sniffRequest(@NonNull HttpRequest request) {
        httpRequest = request;
        ByteBuffer buffer;

        for (String s : blacklist) {
            if (httpRequest.url().contains(s)) {
                Log.i("AdvertisementInjector", httpRequest.url() + " is blocked");
                return true;
            }
        }

        Log.i("AdvertisementInjector", "Passage allowed for url: : " + httpRequest.url());
        return false;
    }

    @Override
    public boolean sniffResponse(@NonNull HttpResponse response) {
        return false;
    }

    /**
     * @Override public boolean sniffRequest(HttpRequest request) {
     * httpRequest = request;
     * <p>
     * for (String s : blacklist) {
     * if (httpRequest.url().contains(s)){
     * Log.i("AdvertisementInjector", "removing advertisements for: " + httpRequest.url());
     * return true;
     * }
     * }
     * <p>
     * Log.i("AdvertisementInjector", "Passage allowed for url: : " + httpRequest.url());
     * return false;
     * }
     **/

    @Override
    public void onRequestInject(HttpRequestHeaderPart header, InjectorCallback callback) throws IOException { }

    @Override
    public void onRequestInject(HttpRequest request, HttpBody body, InjectorCallback callback) throws IOException {
          httpRequest = request;
          ByteStream byteStream = new ByteStream(new byte[1]);
          callback.onFinished(byteStream);
          Log.i("AdvertisementInjector", "This site is blocked");
    }

    @Override
    public void onRequestFinished(HttpRequest request) { }
}
