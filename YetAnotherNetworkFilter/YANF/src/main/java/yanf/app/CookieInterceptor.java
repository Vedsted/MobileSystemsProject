package yanf.app;

import android.util.Log;

import com.github.megatronking.netbare.http.HttpBody;
import com.github.megatronking.netbare.http.HttpRequest;
import com.github.megatronking.netbare.http.HttpRequestHeaderPart;
import com.github.megatronking.netbare.injector.InjectorCallback;
import com.github.megatronking.netbare.injector.SimpleHttpInjector;

import java.io.IOException;

public class CookieInterceptor extends SimpleHttpInjector {

    private HttpRequestHeaderPart mHoldRequestHeader;

    @Override
    public boolean sniffRequest(HttpRequest request) {
        Log.i("Cookie Remover", "removing cookies for: " + request.url());
        return true;
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
}
