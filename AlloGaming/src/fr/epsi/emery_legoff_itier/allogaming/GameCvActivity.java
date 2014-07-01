package fr.epsi.emery_legoff_itier.allogaming;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class GameCvActivity extends ActionBarActivity{
	
	protected Game theGame = new Game();
	
	private ImageView gamePicture;
	private TextView gameName;
	private TextView gamePlatform;
	private TextView gameRate;
	private TextView gameDeveloper;
	private TextView gameOverview;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.game_cv_activity);

        
        gamePicture = (ImageView) findViewById(R.id.imageView);
    	gameName = (TextView) findViewById(R.id.gameName);
    	gamePlatform = (TextView) findViewById(R.id.gamePlatform);
    	gameRate = (TextView) findViewById(R.id.gameRate);
    	gameDeveloper = (TextView) findViewById(R.id.gameDeveloper);
    	gameOverview = (TextView) findViewById(R.id.gameOverview);
    	
    	
        InitView();
    }
	
	protected void InitView(){
        
        getActionBar().setTitle("Détail du jeux :");
		invalidateOptionsMenu();
		
		LoadGameData();
	}

	protected void LoadGameData(){

        Bundle myBundle = getIntent().getExtras();
        String id =(String) myBundle.getCharSequence("Id");
        
		try {
			
			String url = getString(R.string.mon_ip);
			url +=  "/AlloGamingAPI/webresources/translator/GetGame/" + URLEncoder.encode(id,"UTF-8");
	        LoadGame(url);
			theGame.setGameId(Integer.parseInt(id));
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	protected void LoadGame(String url){
		  
		  GameCvManagement myGameCvManagement = new GameCvManagement(this, gamePicture,gameName,gamePlatform,
				  gameRate,gameDeveloper,gameOverview);
		  myGameCvManagement.execute(url);		  
	  }
}
