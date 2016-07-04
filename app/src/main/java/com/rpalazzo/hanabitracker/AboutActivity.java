package com.rpalazzo.hanabitracker;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AboutActivity extends AppCompatActivity {

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
    */

    WebView webView;
    String mimeType = "text/html";
    String encoding = "utf-8";


    String htmlText = "<html> <strong>About Hanabi Tracker</strong> " +
            "<p>Under Construction" +
            "<p>Long press to annotate a card without changing the negative information of other cards.</p>" +
            "<p><a href=\"http://google.github.io/material-design-icons/\">Material Design Icons</a> by Google. Used without modification under the <a href=\"https://creativecommons.org/licenses/by/4.0/legalcode\">Creative Common Attribution 4.0 International License (CC-BY 4.0)</a>.</p>" +
            "<p>Copyright R. Palazzo, 2016. All rights reserved.</p>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        webView = (WebView)findViewById(R.id.webview);
        webView.loadData(htmlText, mimeType, encoding);
        webView.getSettings().setBuiltInZoomControls(true);
        //webView.getSettings().setDisplayZoomControls(false);

        webView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url != null && url.startsWith("http://")) {
                    view.getContext().startActivity(
                            new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

}


