package fr.epsi.emery_legoff_itier.allogaming;


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
		
		try {
			URL url = new URL(myurl);
			
			HttpGet httpget = new HttpGet("http://www.vogella.com");
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
	}
	
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
