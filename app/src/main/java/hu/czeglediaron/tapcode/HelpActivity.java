package hu.czeglediaron.tapcode;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class HelpActivity extends AppCompatActivity {

    WebView wvWikipedia;
    Button bWikipedia;

    ConnectivityManager cm;
    NetworkInfo ni;

    boolean wikipediaPageLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        setTitle(getResources().getString(R.string.app_name) + "\\" + getResources().getString(R.string.mi_help));

        wvWikipedia = (WebView) findViewById(R.id.wv_wikipedia);
        bWikipedia = (Button) findViewById(R.id.b_wikipedia);

        cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        ni = cm.getActiveNetworkInfo();

        if(ni == null) {
            bWikipedia.setEnabled(false);
        }
    }

    public void onClick_bWikipedia(View view) {
        if(!wikipediaPageLoaded) {
            wikipediaPageLoaded = true;
            bWikipedia.setText(getResources().getString(R.string.b_wikipedia_showtable));

            wvWikipedia.setVisibility(View.VISIBLE);
            wvWikipedia.setWebViewClient(new WebViewClient());
            wvWikipedia.loadUrl("https://en.wikipedia.org/wiki/Tap_code");
        } else {
            wikipediaPageLoaded = false;
            bWikipedia.setText(getResources().getString(R.string.b_wikipedia_showpage));

            wvWikipedia.setVisibility(View.INVISIBLE);
        }
    }
}
