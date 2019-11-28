package yanf.app;

import android.support.annotation.NonNull;
import android.util.Log;

import com.github.megatronking.netbare.NetBareUtils;
import com.github.megatronking.netbare.http.HttpBody;
import com.github.megatronking.netbare.http.HttpRequest;
import com.github.megatronking.netbare.http.HttpRequestHeaderPart;
import com.github.megatronking.netbare.injector.InjectorCallback;
import com.github.megatronking.netbare.injector.SimpleHttpInjector;
import com.github.megatronking.netbare.stream.ByteStream;

import java.io.IOException;
import java.util.HashSet;

public class CookieInterceptor extends SimpleHttpInjector {
    private HttpRequest httpRequest;
    private HttpRequestHeaderPart mHoldRequestHeader;
    private HashSet<String> blacklist;

    public CookieInterceptor(HashSet<String> blacklist) {
        this.blacklist = blacklist;
    }

    @Override
    public boolean sniffRequest(HttpRequest request) {
        httpRequest = request;
        for (String s : blacklist) {
            if (httpRequest.url().contains(s)){
                Log.i("Cookie Remover", "removing cookies for: " + httpRequest.url());
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
