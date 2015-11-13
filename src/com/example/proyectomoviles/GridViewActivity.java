package com.example.proyectomoviles;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.example.proyectomoviles.GridViewAdapter.ImageLoadTask;
import com.example.proyectomoviles.basededatos.DataBaseHelper;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;



public class GridViewActivity extends ListActivity implements
OnItemClickListener{
	private DataBaseHelper db;
	private MenuAdapter adapter;
	private Menu menuToShow;
	private Intent IntentMenu;
	private Intent intent2;
	static GridViewActivity activityMenu;
	
	//private ArrayList<Pedido> listaPedidosTomadosDesdeCarta;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listamenus);
		db = new DataBaseHelper(this);	
		adapter = new MenuAdapter();
		activityMenu=this;
		setListAdapter(adapter);
		getListView().setOnItemClickListener(this);
		loadMenuData();
	}

	private void loadMenuData() {
		Menu menu;
				
		Cursor cs = db.obtenerMenusConIdCategoria(getIntent().getIntExtra("categoria", 1));
		
		if (cs != null) {
		    while(cs.moveToNext()) {
		    	menu = new Menu();
		        menu.setIdMenu(cs.getInt(0));
		        menu.setNombreMenu(cs.getString(1));
		        menu.setDescripcionMenu(cs.getString(2));
		        menu.setUrlImagenLowQ(cs.getString(3));
		        menu.setUrlImagenHighQ(cs.getString(4));
		        menu.setPrecioMenu(cs.getString(5));
		        adapter.addMenu(menu);
		        
		    }
		    cs.close();
		}
		
		adapter.notifyDataSetChanged();

	}

	class MenuAdapter extends BaseAdapter {
		private ArrayList<Menu> menus;
		private LayoutInflater inflater;

		public MenuAdapter() {
			menus = new ArrayList<Menu>();
			inflater = LayoutInflater.from(GridViewActivity.this);
		}

		public void addMenu(Menu menu) {
			if (menu != null) {
				menus.add(menu);
			}
		}

		@Override
		public int getCount() {
			return menus.size();
		}

		@Override
		public Object getItem(int position) {
			return menus.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		class Holder {
			private TextView txtMenu;
			private TextView txtPrecio;
			private ImageView imagenChicaMenu;
			private Button btnAgregarMenu;
			private ImageButton imgButtonAgregar;

		}

		@Override
		public View getView(final int position, View convertView, ViewGroup arg2) {
			Holder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.menus, null);
				holder = new Holder();
				holder.imagenChicaMenu = (ImageView) convertView
						.findViewById(R.id.imagenChicaMenu);
				holder.txtMenu = (TextView) convertView
						.findViewById(R.id.txtMenu);
				holder.txtPrecio = (TextView) convertView
						.findViewById(R.id.txtPrecio);
				holder.btnAgregarMenu = (Button) convertView.
						findViewById(R.id.btnAgregarMenu);
				holder.imgButtonAgregar= (ImageButton) convertView.
						findViewById(R.id.imgButtonAgregar);

				holder.imgButtonAgregar.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						LinearLayout l =(LinearLayout) v.getParent();
						ListView lista = (ListView)l.getParent();
						
						Menu menuSeleccionado =(Menu) adapter.getItem( lista.getPositionForView(l));
						createIntent();
						//Menu menuSeleccionado = (Menu) adapter.getItem(position);
						
						if(IntentMenu != null)
						{
							IntentMenu.putExtra("menu",menuSeleccionado);
							/*
							listaPedidosTomadosDesdeCarta = (ArrayList<Pedido>) getIntent().getSerializableExtra("menusTomadosDesdeCategoria");
							Pedido p = new Pedido();
							p.setId(menuSeleccionado.getIdMenu());
							p.setCantidad(1);
							p.setEstado("Tomado");
							p.setPrecio(menuSeleccionado.getPrecioMenu());
							p.setNombre(menuSeleccionado.getNombreMenu());
							if(listaPedidosTomadosDesdeCarta == null)
							{
								listaPedidosTomadosDesdeCarta = new ArrayList<Pedido>();
								listaPedidosTomadosDesdeCarta.add(p);
							}
							//aca esta el error cuando intento iniciar la actividad con este putextra 
							//IntentMenu.putExtra("menus", listaPedidosTomadosDesdeCarta);*/
							
							//Bundle b = getIntent().getExtras();
							//ArrayList<Pedido> pedidos = (ArrayList<Pedido>) b.getSerializable("pedidosTomados");
							Pedido p = new Pedido();
							p.setId(menuSeleccionado.getIdMenu());
							p.setCantidad(1);
							p.setEstado("Tomado");
							p.setPrecio("$"+menuSeleccionado.getPrecioMenu());
							p.setNombre(menuSeleccionado.getNombreMenu());
							
							//pedidos.add(p);
							intent2.putExtra("pedidoTomado",p );
							setResult(RESULT_OK, intent2);
							finish();
							
							//startActivity(IntentMenu);
							//GridViewCategorias.getInstance().finish();
							//GridViewActivity.getInstance().finish();
							
							
						}
						
					}
				});
				convertView.setTag(holder);
				
				
				
			} else {
				holder = (Holder) convertView.getTag();
			}

			Menu menu = (Menu) getItem(position);
			new ImageLoadTask(menu.getUrlImagenLowQ(), holder.imagenChicaMenu).execute();
			holder.txtMenu.setText(menu.getNombreMenu());
			holder.txtPrecio.setText("$" + menu.getPrecioMenu());

			return convertView;
		}

	}

	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) 
	{
		menuToShow = (Menu) adapter.getItem(position);
		// custom dialog
					final Dialog dialog = new Dialog(this);
					dialog.setContentView(R.layout.dialogomenu);
					dialog.setTitle("Detalle Menú");

					// set the custom dialog components - text, image and button
					TextView textDescripcion = (TextView) dialog.findViewById(R.id.textDescripcion);
					TextView textNombreMenu = (TextView) dialog.findViewById(R.id.textNombreMenu);
					ImageView imagenGrandeMenu = (ImageView) dialog.findViewById(R.id.imagenGrandeMenu);
					
					
/*
					Button dialogButton = (Button) dialog.findViewById(R.id.btnAtras);
					// if button is clicked, close the custom dialog
					dialogButton.setOnClickListener(new OnClickListener() {
						
					

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					
					*/
					textDescripcion.setText(menuToShow.getDescripcionMenu());
					textNombreMenu.setText(menuToShow.getNombreMenu());
					new ImageLoadTask(menuToShow.getUrlImagenHighQ(), imagenGrandeMenu).execute();
					dialog.show();
		
	}
	
	public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }

    }
	
	public void createIntent(){
     
		intent2 = new Intent(this,GridViewCategorias.class);
		IntentMenu= new Intent(this,Pedidos.class);
	}
	
	public static GridViewActivity getInstance(){
		return activityMenu;
	}
	

/*
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		if (id == DialogInfo.TYPE_ALERT) {
			dialog = createAlertDialog();
		} else if (id == DialogInfo.TYPE_CUSTOM) {
			dialog = createCustomDialog();
		} else if (id == DialogInfo.TYPE_OK_CANCEL) {
			dialog = createOkCancelDialog();
		} else if (id == DialogInfo.TYPE_PROGRESS) {
			dialog = createProgressDialog();
		} else if (id == DialogInfo.TYPE_ALERT_VARIABLE) {
			dialog = createAlertDialog();
		} else {
			dialog = super.onCreateDialog(id);
		}
		return dialog;
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		super.onPrepareDialog(id, dialog);
		if (id == DialogInfo.TYPE_ALERT_VARIABLE) {
			((AlertDialog) dialog).setMessage(dialogInfoToShow.getMessage()
					+ System.currentTimeMillis());
		}
	}

	private Dialog createProgressDialog() {

		OnDismissListener onDimiss = new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				// Se agrega esto para lograr que el progress se ejecute siempre
				// y no sólo la primera vez
				removeDialog(dialogInfoToShow.getType());
			}
		};

		ProgressDialog progressDialog;
		progressDialog = new ProgressDialog(this);
		progressDialog.setOnDismissListener(onDimiss);
		progressDialog.setTitle(dialogInfoToShow.getTitle());
		progressDialog.setMessage(dialogInfoToShow.getMessage());
		progressDialog.setCancelable(true);
		return progressDialog;
	}

	private Dialog createOkCancelDialog() {
		OnClickListener clickOk = new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast toast = Toast.makeText(MainAndroidDialogs.this,
						"Ok seleccionado", Toast.LENGTH_LONG);
				toast.show();
			}
		};

		OnClickListener clickCancel = new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast toast = Toast.makeText(MainAndroidDialogs.this,
						"Cancelado seleccionado", Toast.LENGTH_LONG);
				toast.show();
			}
		};

		OnCancelListener onCancel = new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				Toast toast = Toast.makeText(MainAndroidDialogs.this,
						"El usuario no seleccionó opción", Toast.LENGTH_LONG);
				toast.show();
			}
		};

		Dialog dialog = new AlertDialog.Builder(this)
				.setIcon(R.drawable.icon)
				.setOnCancelListener(onCancel)
				.setTitle(dialogInfoToShow.getTitle())
				.setPositiveButton(dialogInfoToShow.getBtLabelOk(), clickOk)
				.setNegativeButton(dialogInfoToShow.getBtLabelCancel(),
						clickCancel).setMessage(dialogInfoToShow.getMessage())
				.create();
		return dialog;
	}

	private Dialog createCustomDialog() {
		Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.custom_dialog);
		dialog.setTitle(dialogInfoToShow.getTitle());

		TextView text = (TextView) dialog.findViewById(R.id.text);
		text.setText(dialogInfoToShow.getMessage());
		ImageView image = (ImageView) dialog.findViewById(R.id.image);
		image.setImageResource(R.drawable.icon_message);

		return dialog;
	}

	private Dialog createAlertDialog() {
		Dialog dialog = new AlertDialog.Builder(this).setIcon(R.drawable.icon)
				.setTitle(dialogInfoToShow.getTitle())
				.setPositiveButton(dialogInfoToShow.getBtLabelOk(), null)
				.setMessage(dialogInfoToShow.getMessage()).create();
		return dialog;

	 
	  */  
		
	}
	
