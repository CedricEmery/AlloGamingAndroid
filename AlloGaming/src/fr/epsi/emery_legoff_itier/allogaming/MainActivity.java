package fr.epsi.emery_legoff_itier.allogaming;

import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity{


	private String[] drawerItemsList;
	private ListView myDrawer;
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerLayout drawerLayout;
	
	private Spinner choixPlateform;
	
	private List<Game> gameList;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        setContentView(R.layout.game_activity);
        
        InitView();
		
		// Spinner element
		addListenerOnButton();
		addListenerOnSpinnerItemSelection();
		
		LoadGameList();
		
		ListView lv = (ListView) findViewById(R.id.listJeux);
		
		String[] listeStrings = {"France","Allemagne","Russie"};
		 
		lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listeStrings));
		
		GameAdapter adapter = new GameAdapter(this, gameList);
		lv.setAdapter(adapter);
		
    }
    
    protected void InitView(){
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
	  
	  protected void LoadGameList(){
		  GameListManagement myGameListManagement = new GameListManagement();
		  
		  gameList = myGameListManagement.GetGameList();
	  }
}
