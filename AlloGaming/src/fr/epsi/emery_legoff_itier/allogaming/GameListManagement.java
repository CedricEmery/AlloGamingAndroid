package fr.epsi.emery_legoff_itier.allogaming;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

import android.widget.ListView;

public class GameListManagement{

	protected ListView m_lv;
	
	protected GameListManagement(){
		
	}
	
	protected List<Game> GetGameList(){
		
		List<Game> gameList = new ArrayList<Game>();
		
		String myUrl= "http://192.168.1.200:8080/AlloGamingAPI/webresources/translator/GetGamesList/halo";
		
		URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";
        try {
           url = new URL(myUrl);
           conn = (HttpURLConnection) url.openConnection();
           conn.setRequestMethod("GET");

           conn.setDoInput(true);

           conn.connect();

           rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
           while ((line = rd.readLine()) != null) {
              result += line;
           }
           rd.close();
        } catch (IOException e) {
           e.printStackTrace();
        } catch (Exception e) {
           e.printStackTrace();
        }
        
     // On récupère le JSON complet
       /* JSONObject jsonObject = new JSONObject(response);
        
        // On récupère le tableau d'objets qui nous concernent
        JSONArray array = new JSONArray(jsonObject.getString("Game"));
        
        // Pour tous les objets on récupère les infos
        for (int i = 0; i < array.length(); i++) {
        	
            Game newGame = LoadNewGame(array.getString(i));
            gameList.add(newGame); 
        }
        // On retourne la liste des jeux*/
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
