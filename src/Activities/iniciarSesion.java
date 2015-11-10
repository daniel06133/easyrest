package Activities;

import android.R;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class iniciarSesion extends Activity implements OnClickListener
{
	private EditText txtNombreUsuario;
	private EditText txtContrasena;
	private Button btnIniciarSesion;
	

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.iniciarSesion);
	};
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		
	}
	

}
