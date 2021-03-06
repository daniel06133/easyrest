package com.example.proyectomoviles;

import java.util.ArrayList;


//import com.example.proyectomoviles.MainAndroidDialogs.DialogInfoAdapter.Holder;

import com.example.proyectomoviles.R;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class GridViewCategorias extends Activity implements
OnItemClickListener{
	private GridView mGridView;
    private CategoryAdapter adapter;
    private ArrayList<CategoriaItem> mGridData1;
    //static GridViewCategorias activityCategorias;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.categorias);

        mGridView = (GridView) findViewById(R.id.gvCategorias);
        
       
        	
        //Initialize with empty data
        mGridData1 = new ArrayList<CategoriaItem>();
        adapter = new CategoryAdapter();
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(this);
        //activityCategorias = this;
        
        //Start download
        loadCategoryData();
        
           
    }
    
private void loadCategoryData() {
		adapter.notifyDataSetChanged();
	}

	class CategoryAdapter extends BaseAdapter {
		private ArrayList<CategoriaItem> mGridData;
		private LayoutInflater inflater;

		public CategoryAdapter() {
			mGridData = mGridData1;
			mGridData.add(new CategoriaItem ("http://i1379.photobucket.com/albums/ah133/daniel0613/Entrada_zpslfqhwpmt.png","ENTRADAS"));
			mGridData.add(new CategoriaItem ("http://i1379.photobucket.com/albums/ah133/daniel0613/Tablas_zpsusix7drj.png","TABLAS"));
			mGridData.add(new CategoriaItem ("http://i1379.photobucket.com/albums/ah133/daniel0613/Sandwich_zpsqsqk8qkv.png","S�NDWICHES"));
			mGridData.add(new CategoriaItem ("http://i1379.photobucket.com/albums/ah133/daniel0613/Carnes_zpsmwrjd0do.png","CARNES"));
			mGridData.add(new CategoriaItem ("http://i1379.photobucket.com/albums/ah133/daniel0613/Pizza_zpsjnkuzdia.png","PIZZAS"));
			mGridData.add(new CategoriaItem ("http://i1379.photobucket.com/albums/ah133/daniel0613/Pastas_zpsklotjjwq.png","PASTAS"));
			mGridData.add(new CategoriaItem ("http://i1379.photobucket.com/albums/ah133/daniel0613/Bebida_zpsw8yww7oj.png","BEBIDAS"));
			mGridData.add(new CategoriaItem ("http://i1379.photobucket.com/albums/ah133/daniel0613/helado_zpstsnddaz7.png","POSTRES"));
			
			inflater = LayoutInflater.from(GridViewCategorias.this);
		}

		@Override
		public int getCount() {
			return mGridData.size();
		}

		@Override
		public Object getItem(int position) {
			return mGridData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		class Holder {
			private TextView descripcion;
			private ImageView imagen;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			Holder holder;
			if (convertView == null) {
				holder = new Holder();
				convertView = inflater.inflate(R.layout.categoriaitem,arg2,false);
				 holder.descripcion = (TextView) convertView.findViewById(R.id.grid_item_title);
		            holder.imagen = (ImageView) convertView.findViewById(R.id.grid_item_image);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			CategoriaItem item = mGridData.get(position);
			if(item !=null && holder != null && holder.descripcion != null && holder.imagen != null){
			holder.descripcion.setText(item.getTitle());
			
			new AsyncImageLoader(item.getImage(), holder.imagen).execute();}
			
			return convertView;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		//ir a esa categoria (gridviewactivity)
		Intent intent = new Intent(this,ListadoMenus.class);
		CategoriaItem categoriaSeleccionada = (CategoriaItem) adapter.getItem(arg2);
		
		Integer idCategoria =0;
		String nombreCategoria = categoriaSeleccionada.getTitle();
		
		if (nombreCategoria == "ENTRADAS"){
			idCategoria=1;
		}
		else if (nombreCategoria == "TABLAS") {
			idCategoria=2;			
		}
		else if (nombreCategoria == "S�NDWICHES") {
			idCategoria=3;			
		}
		else if (nombreCategoria == "CARNES") {
			idCategoria=4;			
		}
		else if (nombreCategoria == "PIZZAS") {
			idCategoria=5;			
		}
		else if (nombreCategoria == "PASTAS") {
			idCategoria=6;			
		}
		else if (nombreCategoria == "BEBIDAS") {
			idCategoria=7;			
		}
		else if (nombreCategoria == "POSTRES") {
			idCategoria=8;			
		}
						
		
		if(intent != null)
		{
			intent.putExtra("categoria",idCategoria); 
			startActivityForResult(intent, 90 );
		}	
		
	}
	
	/*public static GridViewCategorias getInstance(){
		return activityCategorias;
	}*/
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/*** S�lo se ejecuta cuando el activity se llam� con
		 * startActivityForResult
		***/
		if (requestCode == 90 & resultCode == RESULT_OK) {
			
			Intent intent = new Intent(this, Pedidos.class);
			Bundle b = data.getExtras();
			intent.putExtra("pedidoTomado", b.getSerializable("pedidoTomado"));
			setResult(RESULT_OK, intent);
			finish();
		}
	}
}
