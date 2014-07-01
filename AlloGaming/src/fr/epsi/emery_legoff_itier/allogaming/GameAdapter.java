package fr.epsi.emery_legoff_itier.allogaming;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GameAdapter extends BaseAdapter {

	List<Game> GameList;
	LayoutInflater inflater;
	
	
	@Override
	public int getCount() {
		
		return GameList.size();
	}

	@Override
	public Object getItem(int position) {
		return GameList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		 
	    if(convertView == null) {
	        holder = new ViewHolder();
	        convertView = inflater.inflate(R.layout.item_game, null);
	 
	        holder.GameName = (TextView)convertView.findViewById(R.id.GameName);
	        holder.GamePlatform = (TextView)convertView.findViewById(R.id.GamePlatform);
	 
	        convertView.setTag(holder);
	    } else {
	        holder = (ViewHolder) convertView.getTag();
	    }
	 
	    holder.GameName.setText(GameList.get(position).getGameName());
	    holder.GamePlatform.setText(GameList.get(position).getGamePlatform());
	 
	    return convertView;
	}
	
	private class ViewHolder {
	    TextView GameName;
	    TextView GamePlatform;
	}
	 
	public GameAdapter(Context context,List<Game> GameList) {
	    inflater = LayoutInflater.from(context);
	    this.GameList = GameList;
	}

}
