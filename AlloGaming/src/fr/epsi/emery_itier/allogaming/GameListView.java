package fr.epsi.emery_itier.allogaming;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GameListView extends ActionBarActivity {

	protected ListView m_lv;
	
	public GameListView(){
		//setContentView(R.layout.game_list_view);
		setContentView(getResources().);
        
        // Recuperation de la ListView
		m_lv = (ListView)findViewById(R.layout.game_list_view);

        // Creation d'un adapter à l'aide d'un tableau de String (myList)
		List<Game> gameList = GetGameList();
        ArrayAdapter<Game> myArrayAdapter = new ArrayAdapter<Game>(this, R.layout.game_list_view, gameList);

        // Affectation de l'adapter a la liste view
        m_lv.setAdapter(myArrayAdapter);
	}
	
	protected void LoadListView(){
		
	}
	
	protected List<Game> GetGameList(){
		List<Game> gameList = new ArrayList<Game>();
        
        try {
            String myurl= "http://thegamesdb.net/api/GetGamesList.php?name=x-men";

            URL url = new URL(myurl);
            
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            
            InputStream inputStream = connection.getInputStream();

            String result = InputStreamOperations.InputStreamToString(inputStream);
            
            // On récupère le JSON complet
            JSONObject jsonObject = new JSONObject(result);
            
            // On récupère le tableau d'objets qui nous concernent
            JSONArray array = new JSONArray(jsonObject.getString("Game"));
            
            // Pour tous les objets on récupère les infos
            for (int i = 0; i < array.length(); i++) {
            	
                Game newGame = LoadNewGame(array.getString(i));
                gameList.add(newGame); 
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // On retourne la liste des personnes
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
