package fr.mespotegeek.piclodio;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.app.DialogFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class MainActivity extends Activity {
	//*****************************
	// variables
	//*****************************
	URLmanager urlmanager = new URLmanager();

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // creation du manager d'URL
        String url = urlmanager.getUrl();
        
       	// si on a une url on charge la webview
        if(url!=""){
        	showWebView(url);
        }else{
        	showSettings();
        }
        
       
    }
	
	@SuppressLint("SetJavaScriptEnabled")
	public void showWebView(String url){
		 // chargement de la vue web
        WebView myWebView = (WebView) findViewById(R.id.webview);
        // activation javascript
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // les retour de page sont pris en charge par le webView
        myWebView.setWebViewClient(new WebViewClient());
        // affichage de la page du rasp
        myWebView.loadUrl(url);
        
	}

	//*****************
	// le menu
	//*****************
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    } 
    
    /**
     * Menu capturing
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                // show the dialogFragment
            	showSettings();
                return true;
            
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Show the dialoguFragment
     */
	private void showSettings() {
		DialogFragment newFragment = MyAlertDialogFragment.newInstance( R.string.setURL);
        newFragment.show(getFragmentManager(), "dialog");
		
	}
	
	/**
	 * Set the url for the webview
	 * @param url
	 */
	public void doPositiveClick(String url) {
        urlmanager.setUrl(url);
        showWebView(url);
        Log.i("FragmentAlertDialog", url);
    }
    
	/**
	 * Do nothing. Just close
	 */
    public void doNegativeClick() {
        // Do stuff here.
        Log.i("FragmentAlertDialog", "Negative click!");
    }
    
	
    /**
     * The DialogFragment Class to ask for the Rpi URL
     * @author Nico
     *
     */
    public static class MyAlertDialogFragment extends DialogFragment {

		public static MyAlertDialogFragment newInstance(int title) {
	        MyAlertDialogFragment frag = new MyAlertDialogFragment();
	        Bundle args = new Bundle();
	        args.putInt("title", title);
	        frag.setArguments(args);
	        return frag;
	    }

	    @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        int title = getArguments().getInt("title");
	        final View view = getActivity().getLayoutInflater().inflate(R.layout.settings, null);
	        return new AlertDialog.Builder(getActivity())
	                .setTitle(title)
	                .setView(view)
	                .setPositiveButton(R.string.valider,
	                    new DialogInterface.OnClickListener() {
	                        public void onClick(DialogInterface dialog, int whichButton) {
	                        	// récup de la saisie user
	                        	String url = ((EditText) view.findViewById(R.id.url_piclodio)).getText().toString();
	                            ((MainActivity)getActivity()).doPositiveClick(url);
	                        }
	                    }
	                )
	                .setNegativeButton(R.string.annuler,
	                    new DialogInterface.OnClickListener() {
	                        public void onClick(DialogInterface dialog, int whichButton) {
	                            ((MainActivity)getActivity()).doNegativeClick();
	                        }
	                    }
	                )
	                .create();
	    }
	}
       
}




