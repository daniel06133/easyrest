package com.example.proyectomoviles;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

//import com.example.proyectomoviles.GridViewAdapter.ImageLoadTask;
import com.example.proyectomoviles.R.id;
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



public class ListadoMenus extends ListActivity implements
OnItemClickListener{
	private DataBaseHelper db;
	private MenuAdapter adapter;
	private Menu menuToShow;
	private Intent IntentMenu;
	private Intent intent2;
	//static GridViewActivity activityMenu;
	private TextView tituloCategoria;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listamenus);
		tituloCategoria = (TextView)findViewById(id.txtTituloCategoria);
		
		db = new DataBaseHelper(this);	
		adapter = new MenuAdapter();
		//activityMenu=this;
		setListAdapter(adapter);
		getListView().setOnItemClickListener(this);
		loadMenuData();
	}

	private void loadMenuData() {
		Menu menu;
		String categoria="Categoría";
	   int categoriaSelec = getIntent().getIntExtra("categoria", 1);
	   if (categoriaSelec == 1){
			categoria="ENTRADAS";
		}
		else if (categoriaSelec == 2) {
			categoria="TABLAS";			
		}
		else if (categoriaSelec ==3 ) {
			categoria="SÁNDWICHES";			
		}
		else if (categoriaSelec == 4 ) {
			categoria="CARNES";			
		}
		else if (categoriaSelec == 5) {
			categoria="PIZZAS";			
		}
		else if (categoriaSelec == 6 ) {
			categoria="PASTAS";			
		}
		else if (categoriaSelec == 7) {
			categoria="BEBIDAS";			
		}
		else if (categoriaSelec == 8) {
			categoria="POSTRES";			
		}
	   
	    tituloCategoria.setText(categoria);
		Cursor cs = db.obtenerMenusConIdCategoria(categoriaSelec);
		
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
		    
		}
		cs.close();
		
		adapter.notifyDataSetChanged();

	}

	class MenuAdapter extends BaseAdapter {
		private ArrayList<Menu> menus;
		private LayoutInflater inflater;

		public MenuAdapter() {
			menus = new ArrayList<Menu>();
			inflater = LayoutInflater.from(ListadoMenus.this);
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
												
						if(IntentMenu != null)
						{
							IntentMenu.putExtra("menu",menuSeleccionado);
							
							Pedido p = new Pedido();
							p.setId(menuSeleccionado.getIdMenu());
							p.setCantidad(1);
							p.setEstado("Tomado");
							p.setPrecio("$"+menuSeleccionado.getPrecioMenu());
							p.setNombre(menuSeleccionado.getNombreMenu());
							
							intent2.putExtra("pedidoTomado",p );
							setResult(RESULT_OK, intent2);
							finish();							
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
		
					final Dialog dialog = new Dialog(this);
					dialog.setContentView(R.layout.dialogomenu);
					dialog.setTitle("Detalle Menú");

					TextView textDescripcion = (TextView) dialog.findViewById(R.id.textDescripcion);
					TextView textNombreMenu = (TextView) dialog.findViewById(R.id.textNombreMenu);
					ImageView imagenGrandeMenu = (ImageView) dialog.findViewById(R.id.imagenGrandeMenu);
					
		
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
	
	/*public static GridViewActivity getInstance(){
		return activityMenu;
	}*/
	


		
	}
	
