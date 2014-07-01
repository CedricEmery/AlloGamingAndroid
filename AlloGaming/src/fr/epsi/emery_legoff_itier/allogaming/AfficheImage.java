package fr.epsi.emery_legoff_itier.allogaming;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AfficheImage extends AsyncTask{

   private ImageView gamePicture;
   private Context context;
   
   public AfficheImage(Context context,ImageView gamePicture) {
      this.context = context;
      this.gamePicture = gamePicture;
   }


   //check Internet conenction.
   private void checkInternetConenction(){
      ConnectivityManager check = (ConnectivityManager) this.context.
      getSystemService(Context.CONNECTIVITY_SERVICE);
      if (check != null) 
      {
         NetworkInfo[] info = check.getAllNetworkInfo();
        
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
         
         Bitmap bmp = BitmapFactory.decodeStream(url.openStream());
         
         return bmp;
      }catch(Exception e){
         return new String("Exception: " + e.getMessage());
      }
   }
   @Override
   protected void onPostExecute(Object result){
      this.gamePicture.setImageBitmap((Bitmap)result);
   }
}