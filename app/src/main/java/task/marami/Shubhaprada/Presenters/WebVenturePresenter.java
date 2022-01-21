package task.marami.Shubhaprada.Presenters;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import task.marami.Shubhaprada.Interfaces.IWebVenture;
import task.marami.Shubhaprada.Models.ProjectsData;

public class WebVenturePresenter implements IWebVenture.WebVenturePresenter {
    Context context;
    IWebVenture.WebVentureView WVV;

    public WebVenturePresenter(Context context, IWebVenture.WebVentureView WVV) {
        this.context = context;
        this.WVV = WVV;
    }

    @Override
    public void onLoadVenture(ProjectsData projectsData, WebView webView) {
        String link = projectsData.getLink();
        WVV.onStartProg();
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                WVV.onStopProg();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                WVV.onWebError();
            }
        });
        if (link.isEmpty()){
            WVV.onLinkError();
        }else{
            webView.loadUrl(link);
        }

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }
}
