package com.example.proyectomoviles;

import java.util.List;

import com.example.proyectomoviles.basededatos.DataBaseHelper;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class IniciarSesion extends Activity implements  android.view.View.OnClickListener
{
	private EditText txtNombreUsuario;
	private EditText txtContrasena;
	private Button btnIniciarSesion;
	private String usuario;
	private String contrasena;
	private DataBaseHelper db;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.iniciarsesion);
		db = new DataBaseHelper(this); 
		btnIniciarSesion = (Button) findViewById(R.id.btnIniciarSesion);
		txtNombreUsuario = (EditText) findViewById(R.id.txtNombreUsuario);
		txtContrasena = (EditText) findViewById(R.id.txtContrasena);
		
		btnIniciarSesion.setOnClickListener(this);
		
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;
		
		if(v.getId() == R.id.btnIniciarSesion)
			usuario = txtNombreUsuario.getText().toString();
		    contrasena = txtContrasena.getText().toString();
		    
		    if(db.existeUsuario(usuario, contrasena))
		    {
		    	intent = new Intent((this),Mesas.class);
		    	if (isIntentAvailable(intent)) {
					//Aquí validar usuario y contraseña contra la base de datos.
					startActivity(intent);
				} else {
					Toast toast = Toast.makeText(this, "Intent can't be handled",
							Toast.LENGTH_SHORT);
					toast.show();
				}
		    }
		    else
		    {
		    	Toast toast = Toast.makeText(this, "Usuario o contraseña inválidos.",
						Toast.LENGTH_SHORT);
				toast.show();
		    }

		
	}
	
	
	private boolean isIntentAvailable(Intent intent) {
		final PackageManager packageManager = getPackageManager();
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

}
