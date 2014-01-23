package com.kupay;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Deposito extends Fragment implements OnItemSelectedListener {
	Button navicon;
	Button aceptar;
	EditText cantidad;
	Spinner spinner;

	@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
		 View view = View.inflate(this.getActivity(), R.layout.deposito,null);
		 String [] values = {"OXXO","Tarjeta bancaria",};
		  spinner = (Spinner) view.findViewById(R.id.spinner1);
		 ArrayAdapter<String> LTRadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
		    LTRadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		    spinner.setAdapter(LTRadapter);
		
		  navicon = (Button)  view.findViewById(R.id.navicon_dep);
		  aceptar = (Button)  view.findViewById(R.id.deposito_aceptar);
		  cantidad = (EditText) view.findViewById(R.id.cantidad_deposito);
		
		  navicon.setOnClickListener(new OnClickListener() {
	          public void onClick(View view) { 
	        	  Activity act = getActivity();

	        	  if(act instanceof MainConteiner) {
	        	      ((MainConteiner) act).togle();
	        	  }
	         	 
	          }} );
		  aceptar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (!cantidad.getText().toString().equals("")){
					cantidad.setBackgroundColor(Color.WHITE);
					if (spinner.getSelectedItemPosition() == 1){
						listartarjetas();
					}else if (spinner.getSelectedItemPosition() == 0){
						
						// aqui va lo que se hace cuando se deposita por oxxo
						
					}
				}else{
					Toast.makeText(getActivity(), "Espesifica la catidad", Toast.LENGTH_LONG).show();
					cantidad.setBackgroundColor(Color.YELLOW);
				}
			}
		});


		 return view; 
		 
	 }

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		Log.v("app", "nada seleccionado");
	}

	public void listartarjetas() {
		AlertDialog.Builder builderSingle = new AlertDialog.Builder(
				this.getActivity());
		builderSingle.setTitle("Seecciona una tarjeta");
		final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
				this.getActivity(), android.R.layout.select_dialog_item);
		arrayAdapter.add("+ NUEVA TARJETA");
		arrayAdapter.add("Tarjeta 1");
		arrayAdapter.add("Tarjeta 2");
		arrayAdapter.add("Tarjeta 3");
		arrayAdapter.add("Tarjeta 4");
		arrayAdapter.add("Tarjeta 5");
		arrayAdapter.add("Tarjeta 6");
		builderSingle.setNeutralButton("cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builderSingle.setAdapter(arrayAdapter,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Log.v("app", "dialogo " + Integer.toString(which));
						if (which == 0) {
							Intent intent = new Intent(getActivity(),
									NuevaTarjeta.class);
							startActivityForResult(intent, 450);
						}
					}
				});
		builderSingle.show();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == 450 && resultCode == Activity.RESULT_OK){
			Toast.makeText(this.getActivity().getApplicationContext(), data.getStringExtra("tarjeta_id"), Toast.LENGTH_LONG).show();
		//	procesarTarjeta(data.getIntExtra("indideTarjeta", 0));
		}
	}
	
	public void procesarTarjeta(int indiceTarjeta) {
		
	}
}