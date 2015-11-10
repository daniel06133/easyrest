package Activities;

import com.example.proyectomoviles.*;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class IniciarSesion extends Activity implements OnClickListener
{
	private EditText txtNombreUsuario;
	private EditText txtContrasena;
	private Button btnIniciarSesion;
	

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
<<<<<<< HEAD
		setContentView(R.layout.iniciarsesion);
=======
		setContentView();
		
>>>>>>> origin/master
	};
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		
	}
	

}
