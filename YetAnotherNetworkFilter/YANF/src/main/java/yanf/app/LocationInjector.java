package yanf.app;

import android.support.annotation.NonNull;
import android.util.Log;

import com.github.megatronking.netbare.NetBareUtils;
import com.github.megatronking.netbare.http.HttpBody;
import com.github.megatronking.netbare.http.HttpRequest;
import com.github.megatronking.netbare.http.HttpRequestHeaderPart;
import com.github.megatronking.netbare.injector.InjectorCallback;
import com.github.megatronking.netbare.injector.SimpleHttpInjector;
import com.github.megatronking.netbare.io.HttpBodyInputStream;
import com.github.megatronking.netbare.stream.ByteStream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

import static java.nio.file.attribute.AclEntry.newBuilder;

public class LocationInjector extends SimpleHttpInjector {

    private HttpRequestHeaderPart mHoldRequestHeader;

    private HashSet<String> blacklist;
    private String TAG = "LocationInjector";

    public LocationInjector() {
        this.blacklist = new HashSet<>();
        this.blacklist.add("10.94.122.2");
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
            // 一般不会发生
            return;
        }
        HttpBodyInputStream his = null;
        BufferedReader reader = null;
        DeflaterOutputStream fos = null;
        ByteArrayOutputStream bos = null;

        try {
            his = new HttpBodyInputStream(body);


            reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(his)));

            StringBuilder total = new StringBuilder();
            for (String line; (line = reader.readLine()) != null; ) {
                total.append(line).append('\n');
            }
            Log.i("Reader", "onRequestInject: " + total.toString());

/*
            JsonElement element = new JsonParser().parse(total.toString());
            Log.i("Reader", "parsed: " + element.getAsString());

            if (element == null || !element.isJsonObject()) {
                return;
            }
*/
            JsonObject obj = new JsonObject();
            obj.addProperty("Test", "Test");

            String ret = "{\"data\":[[56,10],[56,10],[56,10],[56,10]],\"filterGranularity\":1}"; //new Gson().toJson(obj.remove("Test"));
            //element.getAsJsonObject().addProperty("Test", "Test");
            //if (locationNode == null || !locationNode.isJsonObject()) {
            //    return;
            //}
            /*
            JsonObject location = locationNode.getAsJsonObject();
            location.addProperty("latitude", 27.99136f);
            location.addProperty("longitude", 86.88915f);
            String injectedBody = element.toString();
            String injectedBody = element.toString();
*/

            bos = new ByteArrayOutputStream();
            fos = new DeflaterOutputStream(bos);
            fos.write(ret.getBytes());
            fos.finish();
            fos.flush();
            byte[] injectBodyData = bos.toByteArray();

            HttpRequestHeaderPart injectHeader = mHoldRequestHeader.newBuilder().replaceHeader("Content-Length", String.valueOf(injectBodyData.length)).build();

            callback.onFinished(injectHeader);

            callback.onFinished(new ByteStream(injectBodyData));
            Log.i(TAG, "Inject wechat location completed!");
        } finally {
            NetBareUtils.closeQuietly(his);
            NetBareUtils.closeQuietly(reader);
            NetBareUtils.closeQuietly(fos);
            NetBareUtils.closeQuietly(bos);
        }
        mHoldRequestHeader = null;

    }

    @Override
    public void onRequestFinished(HttpRequest request) {
        super.onRequestFinished(request);
    }
}
