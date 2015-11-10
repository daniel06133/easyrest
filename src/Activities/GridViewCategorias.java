package Activities;

import java.util.ArrayList;

import com.pas.hellointents.GridViewActivity.AsyncHttpTask;
import com.pas.hellointents.GridViewAdapter.ImageLoadTask;
import com.pas.hellointents.MainAndroidDialogs.DialogInfoAdapter.Holder;

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
    private ArrayList<GridItem> mGridData1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.categorias);

        mGridView = (GridView) findViewById(R.id.gvCategorias);
        
        //Initialize with empty data
        mGridData1 = new ArrayList<GridItem>();
        adapter = new CategoryAdapter();
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(this);
        
        //Start download
        loadCategoryData();
        
           
    }
    
private void loadCategoryData() {
		
		
		adapter.notifyDataSetChanged();
	}

	class CategoryAdapter extends BaseAdapter {
		private ArrayList<GridItem> mGridData;
		private LayoutInflater inflater;

		public CategoryAdapter() {
			mGridData = mGridData1;
			mGridData.add(new GridItem ("http://www.mendebaldea.com/static/servicios/fotos/desayuno_continental_jpg_402x260_crop_q85.jpg","Desayunos"));
			mGridData.add(new GridItem ("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS9Xf3tMaDIoEj5ZorE8NhTbrVytmroXLtKGRfLc-BthAGs4o7T","Tragos"));
			mGridData.add(new GridItem ("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRjLjR7hhA0hOzJFkl9sejG6MkSzpYi7fHNJuM-ybFnD3JkavrAVw","Lomos"));
			mGridData.add(new GridItem ("http://media4.letsbonus.com/products/204000/204035/13789310112075-0-680x276.JPG","Entradas"));
			mGridData.add(new GridItem ("http://kisei.com.ar/wp-content/uploads/2012/01/aseosas-231x256.jpg","Bebidas"));
			mGridData.add(new GridItem ("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQ5ROn_am5tv1AKs1YKu_14qwYqxxK1vmaYCy6AaxO_7HTCOdVylg","Postres"));
			
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
				convertView = inflater.inflate(R.layout.gridviewitem,arg2,false);
				 holder.descripcion = (TextView) convertView.findViewById(R.id.grid_item_title);
		            holder.imagen = (ImageView) convertView.findViewById(R.id.grid_item_image);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			GridItem item = mGridData.get(position);
			
			holder.descripcion.setText(item.getTitle());
			//setiar imagen
			new AsyncImageLoader(item.getImage(), holder.imagen).execute();
			
			return convertView;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		//ir a esa categoria (gridviewactivity)
		Intent intent = new Intent(this,GridViewActivity.class);
		GridItem categoriaSeleccionada = (GridItem) adapter.getItem(arg2);
		
		String nombreCategoria = categoriaSeleccionada.getTitle();
		
		if(intent != null)
		{
			intent.putExtra("categoria",nombreCategoria);
			
			startActivity(intent);
		}	
		
	}
}
