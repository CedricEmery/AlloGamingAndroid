package fr.epsi.emery_legoff_itier.allogaming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GameCvManagement extends AsyncTask{

   private Context context;
   private Game theGame = new Game();
   
   private ImageView gamePicture;
   private TextView gameName;
   private TextView gamePlatform;
   private TextView gameRate;
   private TextView gameDeveloper;
   private TextView gameOverview;
   
   static String baseImgUrl = "http://thegamesdb.net/banners/";
   
   public GameCvManagement(Context context, ImageView gamePicture, TextView gameName, 
		   TextView gamePlatform, TextView gameRate, TextView gameDeveloper, TextView gameOverview) {
	   
      this.context = context;
      this.gamePicture = gamePicture;
      this.gameName = gameName;
      this.gamePlatform = gamePlatform;
      this.gameRate = gameRate;
      this.gameDeveloper = gameDeveloper;
      this.gameOverview = gameOverview;
   }

   protected Game GetGame(){
		
	      return theGame;
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
	   
	   String imageUrl = theGame.getGameImageList().get(0).getsImageUrl();
	   
	   AfficheImage monImage = new AfficheImage(context, this.gamePicture);
	   monImage.execute(imageUrl);
	   
	   this.gameName.setText("Nom : " + theGame.getGameName());
	   this.gamePlatform.setText("Platforme : " + theGame.getGamePlatform());
	   this.gameRate.setText("Note : " + theGame.getGameRating() + " sur 10");
	   this.gameDeveloper.setText("Developer par : " + theGame.getGameDeveloper());
	   this.gameOverview.setText("Vue d'ensemble : \n" + theGame.getGameOverview());
   }
   
   protected void CreatListGame(String jsonStr){
	   JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(jsonStr);
		
			JSONObject jsonGame = jsonObject.getJSONObject("Data").getJSONObject("Game");
            //System.out.println(array.toString());
            theGame = LoadNewGame(jsonGame);

			
      } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
      }
   }
   
   protected Game LoadNewGame(JSONObject sGameInformation) {
	   Game newGame = new Game();

       try {
    	   if(!sGameInformation.isNull("id")){
	            newGame.setGameId(sGameInformation.getInt("id"));
    	   }
	
    	   if(!sGameInformation.isNull("GameTitle")){
	            newGame.setGameName(sGameInformation.getString("GameTitle"));
    	   }
	       if(!sGameInformation.isNull("Platform")){
	            newGame.setGamePlatform(sGameInformation.getString("Platform"));
	       }
	       if(!sGameInformation.isNull("Overview")){
	            newGame.setGameOverview(sGameInformation.getString("Overview"));
	       }
	       if(!sGameInformation.isNull("Genres")){
	            newGame.setGameGenreList(GenerateListGenre(sGameInformation.getJSONObject("Genres")));
	       }
	       if(!sGameInformation.isNull("Players")){
	            newGame.setGamePlayerNb(sGameInformation.get("Players").toString());
	        }
	       if(!sGameInformation.isNull("Publisher")){
	           newGame.setGamePublisher(sGameInformation.getString("Publisher"));
	       }
	       if(!sGameInformation.isNull("Developer")){
	           newGame.setGameDeveloper(sGameInformation.getString("Developer"));
	       }
	       if(!sGameInformation.isNull("ReleaseDate")){
	           newGame.setGameReleaseDate(sGameInformation.getString("ReleaseDate"));
	       }
	       if(!sGameInformation.isNull("Rating")){
	            newGame.setGameRating(Double.toString(sGameInformation.getDouble("Rating")));
	       }
	       if(!sGameInformation.isNull("Images")){
	            newGame.setGameImageList(GenerateListImage(sGameInformation.getJSONObject("Images")));
	       }
	       //System.out.println(newGame.getGameImageList().get(0).getsImageUrl());
	       } catch (JSONException e) {
	   		// TODO Auto-generated catch block
	   		e.printStackTrace();
   		}
       return newGame;
   }
   
   protected List<Image> GenerateListImage(JSONObject listImageInformation){
	   
       List<Image> listImage = new ArrayList<Image>();
       JSONArray array;
		try {
			array = listImageInformation.getJSONArray("boxart");
		
	       Image image = null;
	       for(int i = 0; i < array.length(); i++){
	           JSONObject imageJson = array.getJSONObject(i);
	           if("front".equals(imageJson.getString("side"))){
	               image = new Image();
	               image.setsImageUrl(baseImgUrl + imageJson.getString("content"));
	               image.setiImageWidth(imageJson.getInt("width"));
	               image.setiImageHeight(imageJson.getInt("height"));
	               listImage.add(image);
	           }
	       }
	       } catch (JSONException e) {
	   		// TODO Auto-generated catch block
	   		e.printStackTrace();
	       }
       return listImage;
   }
   
   protected List<String> GenerateListGenre(JSONObject listGenre){
       List<String> newListGenre = new ArrayList<String>();
       Object genre;
	try {
		genre = listGenre.get("genre");
	
       if(genre instanceof JSONArray){
           JSONArray array = listGenre.getJSONArray("genre");
           for(int i = 0; i < array.length(); i++){
               newListGenre.add(array.getString(i));
           }
       }
       else{
           newListGenre.add(listGenre.getString("genre"));
       }
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       return newListGenre;
   }
}