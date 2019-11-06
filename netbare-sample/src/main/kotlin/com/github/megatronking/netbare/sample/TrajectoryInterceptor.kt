package com.github.megatronking.netbare.sample

import android.util.Log
import com.github.megatronking.netbare.NetBareUtils
import com.github.megatronking.netbare.http.HttpBody
import com.github.megatronking.netbare.http.HttpRequest
import com.github.megatronking.netbare.http.HttpRequestHeaderPart
import com.github.megatronking.netbare.http.HttpResponseHeaderPart
import com.github.megatronking.netbare.injector.InjectorCallback
import com.github.megatronking.netbare.injector.SimpleHttpInjector
import com.github.megatronking.netbare.io.HttpBodyInputStream
import com.github.megatronking.netbare.stream.ByteStream
import com.google.gson.JsonParser
import java.io.ByteArrayOutputStream
import java.io.InputStreamReader
import java.io.Reader
import java.util.zip.DeflaterOutputStream
import java.util.zip.InflaterInputStream

class TrajectoryInterceptor : SimpleHttpInjector() {


    companion object {
        const val TAG = "TrajectoryInjector"
        const val TARGET = "http://10.94.122.2"
    }

    private var mHoldRequestHeader: HttpRequestHeaderPart? = null

    override fun sniffRequest(request: HttpRequest): Boolean {
        val shouldInject = request.url().startsWith(TARGET)
        if (shouldInject) {
            Log.i(TAG, "Start trajectory API request interception!")
        }
        return shouldInject
    }

    override fun onRequestInject(header: HttpRequestHeaderPart, callback: InjectorCallback) {
        mHoldRequestHeader = header;

        super.onRequestInject(header, callback)
    }

    override fun onRequestInject(request: HttpRequest, body: HttpBody, callback: InjectorCallback) {

        if (mHoldRequestHeader == null) {
            // 一般不会发生
            return
        }

        var his: HttpBodyInputStream? = null
        var reader: Reader? = null
        var fos: DeflaterOutputStream? = null
        var bos: ByteArrayOutputStream? = null
        try {
            his = HttpBodyInputStream(body)
            Log.i(TAG, body.toString());

            // Read from the input stream
            //reader = InputStreamReader(InflaterInputStream(his))

            // Det fejl
            /*val element = JsonParser().parse(reader)
            if (element == null || !element.isJsonObject) {
                return
            }
            val dataNode = element.asJsonObject.get("data")
            if (dataNode == null || !dataNode.isJsonObject) {
                return
            }

            // Parse the specific node
            val locationArray = dataNode.asJsonObject
            Log.i(TAG, locationArray.toString())*/

            val injectedBody = "{[[0.0,0.0]], 5}"

            bos = ByteArrayOutputStream()
            fos = DeflaterOutputStream(bos)
            fos.write(injectedBody.toByteArray())
            fos.finish()
            fos.flush()
            val injectBodyData = bos.toByteArray()

            // 更新header的content-length
            val injectHeader = mHoldRequestHeader!!
                    .newBuilder()
                    .replaceHeader("Content-Length", injectBodyData.size.toString())
                    .build()
            // 先将header发射出去
            callback.onFinished(injectHeader)
            // 再将响应体发射出去
            callback.onFinished(ByteStream(injectBodyData))


        } finally {
            NetBareUtils.closeQuietly(his)
            NetBareUtils.closeQuietly(reader)
            NetBareUtils.closeQuietly(fos)
            NetBareUtils.closeQuietly(bos)
        }

        mHoldRequestHeader = null
    }

    override fun onRequestFinished(request: HttpRequest) {
        super.onRequestFinished(request)
    }
}