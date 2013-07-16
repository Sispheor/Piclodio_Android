package fr.mespotegeek.piclodio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.os.Environment;
import android.util.Log;

/**
 * 		URL Manager
 * - Save the URL string into a file
 * - Read the file
 * @author Nico
 *
 */
public class URLmanager {
	
	private String url;
	private File fichierURL = new File("/sdcard/Piclodio/url.txt");

	/**
	 * Default constructor
	 * - Check if URL storage folder exist. create it if not
	 * - Load URL from file if exist
	 */
	public URLmanager() {
		createDirIfNotExists();
		createFileIfNotExists();
		loadURLinFileIfExist();
	}
	
	//**********************
	// Getter et Setter
	//**********************
	public String getUrl() {
		
		return url;
	}

	/**
	 * Save into a file the Rpi url of Piclodio web app
	 * @param url The raspberry pi url of Piclodio on the web server
	 */
	public void setUrl(String url) {
		this.url = url;		
		try {
			FileOutputStream fOut = new FileOutputStream(this.fichierURL);
			OutputStreamWriter oswriter = new OutputStreamWriter(fOut);
			BufferedWriter myOutWriter =new BufferedWriter(oswriter);
			myOutWriter.append(url);
			myOutWriter.newLine();
			myOutWriter.close();
			fOut.close();
			oswriter.close();
			myOutWriter.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		
	}
	
	//**********************
	// Methodes
	//**********************
	/**
	 *  Create the backup folder if not exist
	 */
	public void createDirIfNotExists() {
		
		File sdCard = Environment.getExternalStorageDirectory();
		File dir = new File (sdCard.getAbsolutePath() + "/Piclodio");
		if(!dir.isDirectory()) {
			dir.mkdirs();
			Log.i("URLmanager", "creation du dossier");
		}else{
			Log.i("URLmanager", "pas besoin de creer");
		}
	}
	/**
	 * Create the backup file if not exist in the folder
	 */
	public void createFileIfNotExists(){
		// si le fichier existe deja on le creer pas
		if ( !fichierURL.exists()) {
			try {
				Log.i("URLmanager", "creation fichier");
				fichierURL.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Try to load the URL from the backup file if exist. Set "" by default
	 */
	public void loadURLinFileIfExist(){
		String loadedURL="";
		if ( fichierURL.exists() ) {
			Log.i("lecture", "Trouvé; lecture en cours...");
			try {
				BufferedReader br = new BufferedReader(new FileReader(fichierURL));
				String line;
		        while ((line = br.readLine()) != null) {
		        	loadedURL=line;
		        	
		        }
		        br.close();
		      
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}else{
			Log.i("lecture", "Fichier non trouvé");
		}
		
		this.url=loadedURL;
		
	}

}
