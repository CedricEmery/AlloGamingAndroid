package fr.epsi.emery_legoff_itier.allogaming;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GameListManagement extends AsyncTask{

   private Context context;
   private List<Game> gameList = new ArrayList<Game>();
   private ListView listeJeux;
   
   public GameListManagement(Context context, ListView lv) {
      this.context = context;
      this.listeJeux = lv;
   }

   protected List<Game> GetGameList(){
		
	      return gameList;
   }

   //check Internet conenction.
   private void checkInternetConenction(){
      ConnectivityManager check = (ConnectivityManager) this.context.
      getSystemService(Context.CONNECTIVITY_SERVICE);
      if (check != null) 
      {
         NetworkInfo[] info = check.getAllNetworkInfo();
         /*if (info != null) 
            for (int i = 0; i <info.length; i++) 
            if (info[i].getState() == NetworkInfo.State.CONNECTED)
            {
            	Toast.makeText(context, "Internet is connected",
               Toast.LENGTH_SHORT).show();
            }*/

      }
      else{
         Toast.makeText(context, "not conencted to internet",
         Toast.LENGTH_SHORT).show();
          }
   }
   
   protected void onPreExecute(){
      checkInternetConenction();
   }
   
   @Override
   protected Object doInBackground(Object... arg0) {
      try{
    	  
         String link = (String)arg0[0];
         URL url = new URL(link);
         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
         conn.setReadTimeout(10000);
         conn.setConnectTimeout(15000);
         conn.setRequestMethod("GET");
         conn.setDoInput(true);
         conn.connect();
         
         InputStream is = conn.getInputStream();
         BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8") );
         
         String data = null;
         String jsonData = "";
         
         while ((data = reader.readLine()) != null){
        	 jsonData += data + "\n";
         }
         
         return jsonData;
         
      }catch(Exception e){
    	  
         return new String("Exception: " + e.getMessage());
      }
   }
   
   @Override
   protected void onPostExecute(Object result){
	   
		CreatListGame(result.toString());
		if(!gameList.isEmpty()){

			GameAdapter adapter = new GameAdapter(this.context, gameList);
			listeJeux.setAdapter(adapter);
		}
		else{
			Toast.makeText(context, "Aucun jeux ne correspond à ce nom...", Toast.LENGTH_LONG).show();
		}
   }
   
   protected void CreatListGame(String jsonStr){
	   JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(jsonStr);
		
	       System.out.println(jsonObject.toString());
	       // On récupère le tableau d'objets qui nous concernent
	       
	       JSONArray array = null;
	
			array = jsonObject.getJSONObject("Data").getJSONArray("Game");
		
	       //System.out.println(array.toString());
	       for (int i = 0; i < array.length(); i++) {
	           
	           Game newGame = null;
			
				newGame = LoadNewGame(array.getJSONObject(i));
			
	           gameList.add(newGame);
	       }
      } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
      }
   }
   
   protected static Game LoadNewGame(JSONObject sGameInformation) {
       Game newGame = new Game();

       try {
    	   
    	   newGame.setGameId(sGameInformation.getInt("id"));
	       newGame.setGameName(sGameInformation.getString("GameTitle"));
	       newGame.setGamePlatform(sGameInformation.getString("Platform"));
	       
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	    return newGame;
   }
}