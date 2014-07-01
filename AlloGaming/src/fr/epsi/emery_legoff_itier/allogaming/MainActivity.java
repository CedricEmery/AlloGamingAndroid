package fr.epsi.emery_legoff_itier.allogaming;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

public class MainActivity extends ActionBarActivity {


	private String[] drawerItemsList;
	private ListView myDrawer;
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerLayout drawerLayout;
	
	private Spinner choixPlateform;
	private EditText choixJeux;
	
	private ListView lv;
	
	private GameListManagement myGameListManagement;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game_activity);
        
        InitView();
		
    }
    
    protected void InitView(){
    	InitLateralMenu();
    	//InitPlatformSpiner();
    	InitChoixJeux();
    	InitGameListView();
    }
    
    protected void InitChoixJeux(){
    	
    	choixJeux = (EditText) findViewById(R.id.choixJeux);
    	choixJeux.setOnEditorActionListener(new OnEditorActionListener() {
    	   
    		@Override
    	    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
    	        boolean handled = false;
    	        if (actionId == EditorInfo.IME_ACTION_SEARCH) {

    	        	if(!choixJeux.getText().toString().isEmpty())
    	        	{
						try {
							String url = getString(R.string.mon_ip);
							url +=  "/AlloGamingAPI/webresources/translator/GetGamesList/" + URLEncoder.encode(choixJeux.getText().toString(),"UTF-8");
						
	    	        		LoadGameList(url);
	    	        		
	        	            handled = true;
	        	            
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

    	        	}
    	        	else{
    	        		Toast bread = Toast.makeText(getApplicationContext(), "Veuillez indiquer le nom d'un jeux", Toast.LENGTH_LONG);
    	        		bread.show();
    	        	}
    	        		
    	        }
    	        InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
    	        imm.hideSoftInputFromWindow(choixJeux.getWindowToken(), 0);
    	        
    	        return handled;
    	    }

    	});
    }
    
    
    protected void InitPlatformSpiner(){
    	// Spinner element
		addListenerOnButton();
		addListenerOnSpinnerItemSelection();
    }
    
    protected void InitGameListView(){
    	lv = (ListView) findViewById(R.id.listJeux);
    	// React to user clicks on item
    	lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    	 
    	     public void onItemClick(AdapterView<?> parentAdapter, View view, int position, long id) {
    	    	 Intent newActivity = new Intent(getApplicationContext(), GameCvActivity.class);  
    	    	 
    	    	 List<Game> gameList = myGameListManagement.GetGameList();
    	    	 
    	    	 int gameId = gameList.get(position).getGameId();
    	    	 
    	    	 newActivity.putExtra("Id", String.valueOf(gameId));
    	    	 
				 startActivity(newActivity); 
    	     }
    	});
    }
    
    protected void InitLateralMenu(){
    	drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		
		drawerItemsList = getResources().getStringArray(R.array.items);
		myDrawer = (ListView) findViewById(R.id.my_drawer);
		myDrawer.setAdapter(new ArrayAdapter<String>(this, R.layout.content_sliding_menu, drawerItemsList));

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		
		mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_launcher, R.string.ouverture, R.string.fermeture) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(R.string.titre);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(R.string.titre_apres_ouverture);
				invalidateOptionsMenu();
			}
		};
		drawerLayout.setDrawerListener(mDrawerToggle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		
		switch (item.getItemId()) {
			case R.id.action_settings:
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	  
	 // add items into spinner dynamically
	  public void addListenerOnSpinnerItemSelection() {
		choixPlateform = (Spinner) findViewById(R.id.choixPlateform);
		choixPlateform.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	  }
	 
	  // get the selected dropdown list value
	  public void addListenerOnButton() {
	 
		  choixPlateform = (Spinner) findViewById(R.id.choixPlateform);
		
	  }
	  
	  protected void LoadGameList(String url){
		  
		  myGameListManagement = new GameListManagement(this, lv);
		  myGameListManagement.execute(url);		  
	  }
}
