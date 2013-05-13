package com.kupay;

import org.json.JSONException;
import org.json.JSONObject;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	ProgressDialog	progress;
	FragmentTabHost mTabHost;
	Button actCC;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v("app", "m1");
		setContentView(R.layout.activity_main);
		  mTabHost = (FragmentTabHost) findViewById (android.R.id.tabhost);
		  actCC = (Button) findViewById(R.id.actCC); 
		 mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		 Eventos();
		 ActualizarCC cc = new ActualizarCC();
		 cc.execute();
		 tabs();
		

	}
	
	
	
    public void Eventos(){
   //cambia el estado        
        
      actCC.setOnClickListener(new View.OnClickListener() {
          public void onClick(View view) { 
        	  Log.v("app", "actualizar cc..");
        	  ActualizarCC cc = new ActualizarCC();
        	 cc.execute();
        	 
          }} );
    }
	
	/*@Override
	protected void onResume() {
		ActualizarCC cc = new ActualizarCC();
		 cc.execute();
	}*/
	private void tabs(){
		Bundle b = new Bundle();
		b.putString("key", "comprar");
		mTabHost.addTab(mTabHost.newTabSpec("comprar").setIndicator("", getResources().getDrawable(R.layout.comp)),capturaQR.class, b);
		mTabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.layout.bgcomp); 
		mTabHost.getTabWidget().getChildAt(0).getLayoutParams().height = 100;
		mTabHost.getTabWidget().setStripEnabled(false);
		//mTabHost.getChildAt(0).getLayoutParams().height = 80;
		
		Log.v("app", "m3");
		b = new Bundle();
		b.putString("key", "transferir");
		mTabHost.addTab(mTabHost.newTabSpec("transferir").setIndicator("", getResources().getDrawable(R.layout.tran)), transferencia.class, b);
		mTabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.layout.bgcomp); 
		mTabHost.getTabWidget().getChildAt(1).getLayoutParams().height = 100;
		mTabHost.getTabWidget().setStripEnabled(false);
		//mTabHost.getChildAt(1).getLayoutParams().height = 80;
		Log.v("app", "m4");
		
		b = new Bundle();
		b.putString("key", "cobrar");
		mTabHost.addTab(mTabHost.newTabSpec("cobrar").setIndicator("",getResources().getDrawable(R.layout.vent)),test.class, b);
		mTabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.layout.bgcomp);
		mTabHost.getTabWidget().getChildAt(2).getLayoutParams().height = 100;
		mTabHost.getTabWidget().setStripEnabled(false);
		//mTabHost.getChildAt(2).getLayoutParams().height = 80;
		Log.v("app", "m5");
	}
	
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
	private class ActualizarCC extends AsyncTask<Void, Integer, JSONObject>{
	   	 
		final String ACTUALIZACION_CC_EXITOSA = "ACTUALIZACION_CC_EXITOSA";
		final String ACTUALIZACION_CC_FALLIDA = "ACTUALIZACION_CC_FALLIDA";
		final String CONEXION_FALLIDA = "CONEXION_FALLIDA";
		@Override
         protected void onPreExecute() {
			//progress = ProgressDialog.show(getApplicationContext(), "Conectando a servidor", "Conectando...");
			
          }
         
          protected JSONObject doInBackground(Void... params) {
			
        	  JSONObject data = new JSONObject();
        	  JSONObject response = new JSONObject();
  			try {
 
      			data.put("emisor", "00000001");
      			data.put("imei", "123456789012345");
      			data.put("pin", 1234);
      			
      			} catch (JSONException e) {
      				// TODO Auto-generated catch block
      				e.printStackTrace();
      			}
  			Post post = new Post(5,data);
        	  
        	
        		  try {
					response = post.exec(getApplicationContext());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				//Log.v("app", "main resp:"+ response.toString());
          	
  
        	  return response;
	
          }
        @Override
          protected void onProgressUpdate (Integer... valores) {
        
          }
        
        @Override
          protected void onPostExecute(JSONObject response) {
        	try {
        	//	progress.dismiss();
   			 String resultado = response.getString("RESULTADO");
   			Log.v("app","RESult: "+ resultado.toString());
   			JSONObject datos = response.getJSONObject("DATOS");
         		if (ACTUALIZACION_CC_EXITOSA.toString().equals(resultado) ){
         			
         			
     				Log.v("app","Datos: "+ datos.toString());
     				TextView cc = (TextView) findViewById(R.id.cantidad);
     				cc.setText("$"+Integer.toString(datos.getInt("SALDO")));
         		}else if(ACTUALIZACION_CC_FALLIDA.toString().equals(resultado)){
         			int duracion=Toast.LENGTH_SHORT;
                    Toast mensaje=Toast.makeText(getApplication(), "error en actualización", duracion);
                    mensaje.show();
         		}else if(CONEXION_FALLIDA.toString().equals(resultado)){
         			int duracion=Toast.LENGTH_SHORT;
                    Toast mensaje=Toast.makeText(getApplication(), "error en conexion", duracion);
                    mensaje.show();
         		}else{
         			int duracion=Toast.LENGTH_SHORT;
                     Toast mensaje=Toast.makeText(getApplication(), "error desconosido", duracion);
                     mensaje.show();
         		}
           	} catch (JSONException e) {
   				// TODO Auto-generated catch block
   				e.printStackTrace();
   			}
        }
		
    }
	

}
