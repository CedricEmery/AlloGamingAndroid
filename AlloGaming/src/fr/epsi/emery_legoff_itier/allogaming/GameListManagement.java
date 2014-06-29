package fr.epsi.emery_legoff_itier.allogaming;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.epsi.emery_legoff_itier.allogaming.R;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GameListManagement{

	protected ListView m_lv;
	
	protected GameListManagement(){
		
	}
	
	protected List<Game> GetGameList(){
		
		List<Game> gameList = new ArrayList<Game>();
		
		String myurl= "http://192.168.1.200:8080/AlloGamingAPI/webresources/translator/GetGamesList?name=halo";
		
		String response = null;
		HttpClient httpclient = null;
		
		InputStream in = null;
		int http_status;
		HttpURLConnection conn = null;
		
		try {
			URL url = new URL(myurl);
			
			conn = (HttpURLConnection) url.openConnection();

		    // this opens a connection, then sends GET & headers 
		    in = conn.getInputStream(); 

		    // can't get status before getInputStream.  If you try, you'll
		    //  get a nasty exception.
		    http_status = conn.getResponseCode();

		    // better check it first
		    if (http_status / 100 != 2) {
		      // redirects, server errors, lions and tigers and bears! Oh my!
		    }
		  } catch (IOException e) {
		    // Something horrible happened, as in a network error, or you
		    //  foolishly called getResponseCode() before HUC was ready.
		    // Essentially no methods of on "conn" now work, so don't go
		    //  looking for help there.
		  }
		  
		  try {
		    // now you can try to consume the data
			  String result = InputStreamOperations.InputStreamToString(in);
			// On récupère le JSON complet
	            JSONObject jsonObject = null;
				try {
					jsonObject = new JSONObject(result);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            
	            // On récupère le tableau d'objets qui nous concernent
	            JSONArray array = new JSONArray(jsonObject.getString("Game"));
	            
	            // Pour tous les objets on récupère les infos
	            for (int i = 0; i < array.length(); i++) {
	            	
	                Game newGame = LoadNewGame(array.getString(i));
	                gameList.add(newGame); 
	            }
		  } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    // Do like Mom said and practice good hygiene.
		    conn.disconnect(); 
		  }
        
        // On retourne la liste des jeux
        return gameList;
	}
	
	/*protected List<Game> GetGameList(){
		
		List<Game> gameList = new ArrayList<Game>();
		
		String myurl= "http://192.168.1.200:8080/AlloGamingAPI/webresources/translator/GetGamesList?name=halo";
		
		String response = null;
		HttpClient httpclient = null;
		
		try {
			URL url = new URL(myurl);
			
			HttpGet httpget = new HttpGet(url.toURI());
			httpclient = new DefaultHttpClient();
			HttpResponse httpResponse = httpclient.execute(httpget);
			 
			final int statusCode = httpResponse.getStatusLine().getStatusCode();
			
			if (statusCode != HttpStatus.SC_OK) {
				throw new Exception("Got HTTP " + statusCode + " (" + httpResponse.getStatusLine().getReasonPhrase() + ')');
			}
			 
			response = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
			
			// On récupère le JSON complet
            JSONObject jsonObject = new JSONObject(response);
            
            // On récupère le tableau d'objets qui nous concernent
            JSONArray array = new JSONArray(jsonObject.getString("Game"));
            
            // Pour tous les objets on récupère les infos
            for (int i = 0; i < array.length(); i++) {
            	
                Game newGame = LoadNewGame(array.getString(i));
                gameList.add(newGame); 
            }
			 
			} catch (MalformedURLException e1) {
				
				e1.printStackTrace();
			}catch (Exception e) {
				
				e.printStackTrace();			 
			} finally {
				
				if (httpclient != null) {
					
					httpclient.getConnectionManager().shutdown();
					httpclient = null;
			}
		}
        
        // On retourne la liste des jeux
        return gameList;
	}*/
	
	protected Game LoadNewGame(String sGameInformation) throws JSONException{
		
		Game newGame = new Game();
		
		// On récupère un objet JSON du tableau
        JSONObject obj = new JSONObject(sGameInformation);
		
        newGame.setGameId(Integer.parseInt(obj.getString("id")));
        newGame.setGameName(obj.getString("GameTitle"));
        newGame.setGamePlatform(obj.getString("Platform"));
        newGame.setGameOverview(obj.getString("Overview"));
           	
    	//protected List<String> m_sGameGenreList;

        newGame.setGamePlayerNb(obj.getString("Players"));
        newGame.setGamePublisher(obj.getString("Publisher"));
        newGame.setGameDeveloper(obj.getString("Developer"));
        newGame.setGameReleaseDate(obj.getString("ReleaseDate"));
        newGame.setGameRating(obj.getString("Rating"));
    	
    	//protected List<Image> m_GameImageList;
        
        return newGame;
	}
}
