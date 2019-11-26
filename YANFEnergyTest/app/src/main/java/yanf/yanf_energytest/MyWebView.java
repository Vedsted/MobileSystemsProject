package yanf.yanf_energytest;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.net.Uri;
import android.net.http.SslCertificate;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.print.PrintDocumentAdapter;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.autofill.AutofillValue;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.textclassifier.TextClassifier;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebMessage;
import android.webkit.WebMessagePort;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewRenderProcess;
import android.webkit.WebViewRenderProcessClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Map;
import java.util.concurrent.Executor;

public class MyWebView extends WebView {
    
    private final static String TAG = "MyWebView";
    
    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void evaluateJavascript(String script, @Nullable ValueCallback<String> resultCallback) {
        Log.i("MyActivity", "evaluateJavascript: ");
        super.evaluateJavascript(script, resultCallback);

    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
    }

    @Override
    public void setHorizontalScrollbarOverlay(boolean overlay) {
        super.setHorizontalScrollbarOverlay(overlay);
    }

    @Override
    public void setVerticalScrollbarOverlay(boolean overlay) {
        super.setVerticalScrollbarOverlay(overlay);
    }

    @Override
    public boolean overlayHorizontalScrollbar() {
        return super.overlayHorizontalScrollbar();
    }

    @Override
    public boolean overlayVerticalScrollbar() {
        return super.overlayVerticalScrollbar();
    }

    @Nullable
    @Override
    public SslCertificate getCertificate() {
        Log.i(TAG, "getCertificate: ");
        return super.getCertificate();
    }

    @Override
    public void setCertificate(SslCertificate certificate) {
        super.setCertificate(certificate);
    }

    @Override
    public void savePassword(String host, String username, String password) {
        super.savePassword(host, username, password);
    }

    @Override
    public void setHttpAuthUsernamePassword(String host, String realm, String username, String password) {
        super.setHttpAuthUsernamePassword(host, realm, username, password);
    }

    @Nullable
    @Override
    public String[] getHttpAuthUsernamePassword(String host, String realm) {
        return super.getHttpAuthUsernamePassword(host, realm);
    }

    @Override
    public void destroy() {
        Log.i(TAG, "destroy: ");
        super.destroy();
    }

    @Override
    public void setNetworkAvailable(boolean networkUp) {
        Log.i(TAG, "setNetworkAvailable: ");
        super.setNetworkAvailable(networkUp);
    }

    @Nullable
    @Override
    public WebBackForwardList saveState(Bundle outState) {
        Log.i(TAG, "saveState: ");
        return super.saveState(outState);
    }

    @Nullable
    @Override
    public WebBackForwardList restoreState(Bundle inState) {
        Log.i(TAG, "restoreState: ");
        return super.restoreState(inState);
    }

    @Override
    public void loadUrl(String url, Map<String, String> additionalHttpHeaders) {
        Log.i(TAG, "loadUrl: ");
        super.loadUrl(url, additionalHttpHeaders);
    }

    @Override
    public void loadUrl(String url) {
        Log.i(TAG, "loadUrl: ");
        super.loadUrl(url);
    }

    @Override
    public void postUrl(String url, byte[] postData) {
        Log.i(TAG, "postUrl: ");
        super.postUrl(url, postData);
    }

    @Override
    public void loadData(String data, @Nullable String mimeType, @Nullable String encoding) {
        Log.i(TAG, "loadData: ");
        super.loadData(data, mimeType, encoding);
    }

    @Override
    public void loadDataWithBaseURL(@Nullable String baseUrl, String data, @Nullable String mimeType, @Nullable String encoding, @Nullable String historyUrl) {
        Log.i(TAG, "loadDataWithBaseURL: ");
        super.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl);
    }

    @Override
    public void saveWebArchive(String filename) {
        Log.i(TAG, "saveWebArchive: ");
        super.saveWebArchive(filename);
    }

    @Override
    public void saveWebArchive(String basename, boolean autoname, @Nullable ValueCallback<String> callback) {
        Log.i(TAG, "saveWebArchive: ");
        super.saveWebArchive(basename, autoname, callback);
    }

    @Override
    public void stopLoading() {
        Log.i(TAG, "stopLoading: ");
        super.stopLoading();
    }

    @Override
    public void reload() {
        Log.i(TAG, "reload: ");
        super.reload();
    }

    @Override
    public boolean canGoBack() {
        Log.i(TAG, "canGoBack: ");
        return super.canGoBack();
    }

    @Override
    public void goBack() {
        Log.i(TAG, "goBack: ");
        super.goBack();
    }

    @Override
    public boolean canGoForward() {
        Log.i(TAG, "canGoForward: ");
        return super.canGoForward();
    }

    @Override
    public void goForward() {
        Log.i(TAG, "goForward: ");
        super.goForward();
    }

    @Override
    public boolean canGoBackOrForward(int steps) {
        Log.i(TAG, "canGoBackOrForward: ");
        return super.canGoBackOrForward(steps);
    }

    @Override
    public void goBackOrForward(int steps) {
        Log.i(TAG, "goBackOrForward: ");
        super.goBackOrForward(steps);
    }

    @Override
    public boolean isPrivateBrowsingEnabled() {
        Log.i(TAG, "isPrivateBrowsingEnabled: ");
        return super.isPrivateBrowsingEnabled();
    }

    @Override
    public boolean pageUp(boolean top) {
        Log.i(TAG, "pageUp: ");
        return super.pageUp(top);
    }

    @Override
    public boolean pageDown(boolean bottom) {
        Log.i(TAG, "pageDown: ");
        return super.pageDown(bottom);
    }

    @Override
    public void postVisualStateCallback(long requestId, VisualStateCallback callback) {
        Log.i(TAG, "postVisualStateCallback: ");
        super.postVisualStateCallback(requestId, callback);
    }

    @Override
    public void clearView() {
        super.clearView();
    }

    @Override
    public Picture capturePicture() {
        return super.capturePicture();
    }

    @Override
    public PrintDocumentAdapter createPrintDocumentAdapter() {
        return super.createPrintDocumentAdapter();
    }

    @Override
    public PrintDocumentAdapter createPrintDocumentAdapter(String documentName) {
        Log.i(TAG, "createPrintDocumentAdapter: ");
        return super.createPrintDocumentAdapter(documentName);
    }

    @Override
    public float getScale() {
        return super.getScale();
    }

    @Override
    public void setInitialScale(int scaleInPercent) {
        Log.i(TAG, "setInitialScale: ");
        super.setInitialScale(scaleInPercent);
    }

    @Override
    public void invokeZoomPicker() {
        Log.i(TAG, "invokeZoomPicker: ");
        super.invokeZoomPicker();
    }

    @Override
    public HitTestResult getHitTestResult() {
        Log.i(TAG, "getHitTestResult: ");
        return super.getHitTestResult();
    }

    @Override
    public void requestFocusNodeHref(@Nullable Message hrefMsg) {
        Log.i(TAG, "requestFocusNodeHref: ");
        super.requestFocusNodeHref(hrefMsg);
    }

    @Override
    public void requestImageRef(Message msg) {
        Log.i(TAG, "requestImageRef: ");
        super.requestImageRef(msg);
    }

    @Override
    public String getUrl() {
        Log.i(TAG, "getUrl: ");
        return super.getUrl();
    }

    @Override
    public String getOriginalUrl() {
        Log.i(TAG, "getOriginalUrl: ");
        return super.getOriginalUrl();
    }

    @Override
    public String getTitle() {
        Log.i(TAG, "getTitle: ");
        return super.getTitle();
    }

    @Override
    public Bitmap getFavicon() {
        Log.i(TAG, "getFavicon: ");
        return super.getFavicon();
    }

    @Override
    public int getProgress() {
        Log.i(TAG, "getProgress: ");
        return super.getProgress();
    }

    @Override
    public int getContentHeight() {
        Log.i(TAG, "getContentHeight: ");
        return super.getContentHeight();
    }

    @Override
    public void pauseTimers() {
        Log.i(TAG, "pauseTimers: ");
        super.pauseTimers();
    }

    @Override
    public void resumeTimers() {
        Log.i(TAG, "resumeTimers: ");
        super.resumeTimers();
    }

    @Override
    public void onPause() {
        Log.i(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume: ");
        super.onResume();
    }

    @Override
    public void freeMemory() {
        super.freeMemory();
    }

    @Override
    public void clearCache(boolean includeDiskFiles) {
        Log.i(TAG, "clearCache: ");
        super.clearCache(includeDiskFiles);
    }

    @Override
    public void clearFormData() {
        Log.i(TAG, "clearFormData: ");
        super.clearFormData();
    }

    @Override
    public void clearHistory() {
        Log.i(TAG, "clearHistory: ");
        super.clearHistory();
    }

    @Override
    public void clearSslPreferences() {
        Log.i(TAG, "clearSslPreferences: ");
        super.clearSslPreferences();
    }

    @Override
    public WebBackForwardList copyBackForwardList() {
        Log.i(TAG, "copyBackForwardList: ");
        return super.copyBackForwardList();
    }

    @Override
    public void setFindListener(FindListener listener) {
        Log.i(TAG, "setFindListener: ");
        super.setFindListener(listener);
    }

    @Override
    public void findNext(boolean forward) {
        Log.i(TAG, "findNext: ");
        super.findNext(forward);
    }

    @Override
    public int findAll(String find) {
        return super.findAll(find);
    }

    @Override
    public void findAllAsync(String find) {
        Log.i(TAG, "findAllAsync: ");
        super.findAllAsync(find);
    }

    @Override
    public boolean showFindDialog(@Nullable String text, boolean showIme) {
        return super.showFindDialog(text, showIme);
    }

    @Override
    public void clearMatches() {
        Log.i(TAG, "clearMatches: ");
        super.clearMatches();
    }

    @Override
    public void documentHasImages(Message response) {
        Log.i(TAG, "documentHasImages: ");
        super.documentHasImages(response);
    }

    @Override
    public void setWebViewClient(WebViewClient client) {
        Log.i(TAG, "setWebViewClient: ");
        super.setWebViewClient(client);
    }

    @Override
    public WebViewClient getWebViewClient() {
        Log.i(TAG, "getWebViewClient: ");
        return super.getWebViewClient();
    }

    @Nullable
    @Override
    public WebViewRenderProcess getWebViewRenderProcess() {
        Log.i(TAG, "getWebViewRenderProcess: ");
        return super.getWebViewRenderProcess();
    }

    @Override
    public void setWebViewRenderProcessClient(@NonNull Executor executor, @NonNull WebViewRenderProcessClient webViewRenderProcessClient) {
        Log.i(TAG, "setWebViewRenderProcessClient: ");
        super.setWebViewRenderProcessClient(executor, webViewRenderProcessClient);
    }

    @Override
    public void setWebViewRenderProcessClient(@Nullable WebViewRenderProcessClient webViewRenderProcessClient) {
        Log.i(TAG, "setWebViewRenderProcessClient: ");
        super.setWebViewRenderProcessClient(webViewRenderProcessClient);
    }

    @Nullable
    @Override
    public WebViewRenderProcessClient getWebViewRenderProcessClient() {
        Log.i(TAG, "getWebViewRenderProcessClient: ");
        return super.getWebViewRenderProcessClient();
    }

    @Override
    public void setDownloadListener(DownloadListener listener) {
        Log.i(TAG, "setDownloadListener: ");
        super.setDownloadListener(listener);
    }

    @Override
    public void setWebChromeClient(WebChromeClient client) {
        Log.i(TAG, "setWebChromeClient: ");
        super.setWebChromeClient(client);
    }

    @Nullable
    @Override
    public WebChromeClient getWebChromeClient() {
        Log.i(TAG, "getWebChromeClient: ");
        return super.getWebChromeClient();
    }

    @Override
    public void setPictureListener(PictureListener listener) {
        super.setPictureListener(listener);
    }

    @Override
    public void removeJavascriptInterface(@NonNull String name) {
        Log.i(TAG, "removeJavascriptInterface: ");
        super.removeJavascriptInterface(name);
    }

    @Override
    public WebMessagePort[] createWebMessageChannel() {
        Log.i(TAG, "createWebMessageChannel: ");
        return super.createWebMessageChannel();
    }

    @Override
    public void postWebMessage(WebMessage message, Uri targetOrigin) {
        Log.i(TAG, "postWebMessage: ");
        super.postWebMessage(message, targetOrigin);
    }

    @Override
    public WebSettings getSettings() {
        Log.i(TAG, "getSettings: ");
        return super.getSettings();
    }

    @Override
    public void onChildViewAdded(View parent, View child) {
        super.onChildViewAdded(parent, child);
    }

    @Override
    public void onChildViewRemoved(View p, View child) {
        super.onChildViewRemoved(p, child);
    }

    @Override
    public void onGlobalFocusChanged(View oldFocus, View newFocus) {
        super.onGlobalFocusChanged(oldFocus, newFocus);
    }

    @Override
    public void setMapTrackballToArrowKeys(boolean setMap) {
        super.setMapTrackballToArrowKeys(setMap);
    }

    @Override
    public void flingScroll(int vx, int vy) {
        Log.i(TAG, "flingScroll: ");
        super.flingScroll(vx, vy);
    }

    @Override
    public boolean canZoomIn() {
        return super.canZoomIn();
    }

    @Override
    public boolean canZoomOut() {
        return super.canZoomOut();
    }

    @Override
    public void zoomBy(float zoomFactor) {
        Log.i(TAG, "zoomBy: ");
        super.zoomBy(zoomFactor);
    }

    @Override
    public boolean zoomIn() {
        Log.i(TAG, "zoomIn: ");
        return super.zoomIn();
    }

    @Override
    public boolean zoomOut() {
        Log.i(TAG, "zoomOut: ");
        return super.zoomOut();
    }

    @Override
    public void setRendererPriorityPolicy(int rendererRequestedPriority, boolean waivedWhenNotVisible) {
        Log.i(TAG, "setRendererPriorityPolicy: ");
        super.setRendererPriorityPolicy(rendererRequestedPriority, waivedWhenNotVisible);
    }

    @Override
    public int getRendererRequestedPriority() {
        Log.i(TAG, "getRendererRequestedPriority: ");
        return super.getRendererRequestedPriority();
    }

    @Override
    public boolean getRendererPriorityWaivedWhenNotVisible() {
        Log.i(TAG, "getRendererPriorityWaivedWhenNotVisible: ");
        return super.getRendererPriorityWaivedWhenNotVisible();
    }

    @Override
    public void setTextClassifier(@Nullable TextClassifier textClassifier) {
        Log.i(TAG, "setTextClassifier: ");
        super.setTextClassifier(textClassifier);
    }

    @NonNull
    @Override
    public TextClassifier getTextClassifier() {
        Log.i(TAG, "getTextClassifier: ");
        return super.getTextClassifier();
    }

    @NonNull
    @Override
    public Looper getWebViewLooper() {
        Log.i(TAG, "getWebViewLooper: ");
        return super.getWebViewLooper();
    }

    @Override
    protected void onAttachedToWindow() {
        Log.i(TAG, "onAttachedToWindow: ");
        super.onAttachedToWindow();
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        Log.i(TAG, "setLayoutParams: ");
        super.setLayoutParams(params);
    }

    @Override
    public void setOverScrollMode(int mode) {
        Log.i(TAG, "setOverScrollMode: ");
        super.setOverScrollMode(mode);
    }

    @Override
    public void setScrollBarStyle(int style) {
        Log.i(TAG, "setScrollBarStyle: ");
        super.setScrollBarStyle(style);
    }

    @Override
    protected int computeHorizontalScrollRange() {
        Log.i(TAG, "computeHorizontalScrollRange: ");
        return super.computeHorizontalScrollRange();
    }

    @Override
    protected int computeHorizontalScrollOffset() {
        Log.i(TAG, "computeHorizontalScrollOffset: ");
        return super.computeHorizontalScrollOffset();
    }

    @Override
    protected int computeVerticalScrollRange() {
        Log.i(TAG, "computeVerticalScrollRange: ");
        return super.computeVerticalScrollRange();
    }

    @Override
    protected int computeVerticalScrollOffset() {
        Log.i(TAG, "computeVerticalScrollOffset: ");
        return super.computeVerticalScrollOffset();
    }

    @Override
    protected int computeVerticalScrollExtent() {
        Log.i(TAG, "computeVerticalScrollExtent: ");
        return super.computeVerticalScrollExtent();
    }

    @Override
    public void computeScroll() {
        Log.i(TAG, "computeScroll: ");
        super.computeScroll();
    }

    @Override
    public boolean onHoverEvent(MotionEvent event) {
        Log.i(TAG, "onHoverEvent: ");
        return super.onHoverEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent: ");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        Log.i(TAG, "onGenericMotionEvent: ");
        return super.onGenericMotionEvent(event);
    }

    @Override
    public boolean onTrackballEvent(MotionEvent event) {
        Log.i(TAG, "onTrackballEvent: ");
        return super.onTrackballEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i(TAG, "onKeyDown: ");
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.i(TAG, "onKeyUp: ");
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        Log.i(TAG, "onKeyMultiple: ");
        return super.onKeyMultiple(keyCode, repeatCount, event);
    }

    @Override
    public AccessibilityNodeProvider getAccessibilityNodeProvider() {
        Log.i(TAG, "getAccessibilityNodeProvider: ");
        return super.getAccessibilityNodeProvider();
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return super.shouldDelayChildPressedState();
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        Log.i(TAG, "getAccessibilityClassName: ");
        return super.getAccessibilityClassName();
    }

    @Override
    public void onProvideVirtualStructure(ViewStructure structure) {
        Log.i(TAG, "onProvideVirtualStructure: ");
        super.onProvideVirtualStructure(structure);
    }

    @Override
    public void onProvideAutofillVirtualStructure(ViewStructure structure, int flags) {
        Log.i(TAG, "onProvideAutofillVirtualStructure: ");
        super.onProvideAutofillVirtualStructure(structure, flags);
    }

    @Override
    public void autofill(SparseArray<AutofillValue> values) {
        Log.i(TAG, "autofill: ");
        super.autofill(values);
    }

    @Override
    public boolean isVisibleToUserForAutofill(int virtualId) {
        Log.i(TAG, "isVisibleToUserForAutofill: ");
        return super.isVisibleToUserForAutofill(virtualId);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        Log.i(TAG, "onOverScrolled: ");
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        Log.i(TAG, "onWindowVisibilityChanged: ");
        super.onWindowVisibilityChanged(visibility);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(TAG, "onDraw: ");
        super.onDraw(canvas);
    }

    @Override
    public boolean performLongClick() {
        Log.i(TAG, "performLongClick: ");
        return super.performLongClick();
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        Log.i(TAG, "onConfigurationChanged: ");
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        Log.i(TAG, "onCreateInputConnection: ");
        return super.onCreateInputConnection(outAttrs);
    }

    @Override
    public boolean onDragEvent(DragEvent event) {
        Log.i(TAG, "onDragEvent: ");
        return super.onDragEvent(event);
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        Log.i(TAG, "onVisibilityChanged: ");
        super.onVisibilityChanged(changedView, visibility);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        Log.i(TAG, "onWindowFocusChanged: ");
        super.onWindowFocusChanged(hasWindowFocus);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        Log.i(TAG, "onFocusChanged: ");
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }

    @Override
    protected void onSizeChanged(int w, int h, int ow, int oh) {
        Log.i(TAG, "onSizeChanged: ");
        super.onSizeChanged(w, h, ow, oh);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        Log.i(TAG, "onScrollChanged: ");
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.i(TAG, "dispatchKeyEvent: ");
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
        Log.i(TAG, "requestFocus: ");
        return super.requestFocus(direction, previouslyFocusedRect);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(TAG, "onMeasure: ");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean requestChildRectangleOnScreen(View child, Rect rect, boolean immediate) {
        Log.i(TAG, "requestChildRectangleOnScreen: ");
        return super.requestChildRectangleOnScreen(child, rect, immediate);
    }

    @Override
    public void setBackgroundColor(int color) {
        Log.i(TAG, "setBackgroundColor: ");
        super.setBackgroundColor(color);
    }

    @Override
    public void setLayerType(int layerType, Paint paint) {
        Log.i(TAG, "setLayerType: ");
        super.setLayerType(layerType, paint);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Log.i(TAG, "dispatchDraw: ");
        super.dispatchDraw(canvas);
    }

    @Override
    public void onStartTemporaryDetach() {
        Log.i(TAG, "onStartTemporaryDetach: ");
        super.onStartTemporaryDetach();
    }

    @Override
    public void onFinishTemporaryDetach() {
        Log.i(TAG, "onFinishTemporaryDetach: ");
        super.onFinishTemporaryDetach();
    }

    @Override
    public Handler getHandler() {
        Log.i(TAG, "getHandler: ");
        return super.getHandler();
    }

    @Override
    public View findFocus() {
        Log.i(TAG, "findFocus: ");
        return super.findFocus();
    }

    @Override
    public boolean onCheckIsTextEditor() {
        Log.i(TAG, "onCheckIsTextEditor: ");
        return super.onCheckIsTextEditor();
    }
}
