package fr.epsi.emery_legoff_itier.allogaming;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

public class AlloGaming{

	private List<Platform> m_PlatformList;
	private List<Game> m_GameList;

	public AlloGaming(){
		
		
	}
	
	private void LoadPlatformList(){
		m_PlatformList = new ArrayList<Platform>();
		
	}
	
	private InputStream sendRequest(URL url) throws Exception {
		 
        try {
            // Ouverture de la connexion
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
 
            // Connexion à l'url
            urlConnection.connect();
 
            // Si le serveur nous répond avec un code OK
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return urlConnection.getInputStream();
            }
        } catch (Exception e) {
            //throw new Exception(&quot;&quot;);
        }
 
        return null;
 
    }
}
