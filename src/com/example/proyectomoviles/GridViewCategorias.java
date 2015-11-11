package com.example.proyectomoviles;

import java.util.ArrayList;

//import com.pas.hellointents.GridViewActivity.AsyncHttpTask;
//import com.pas.hellointents.GridViewAdapter.ImageLoadTask;
//import com.pas.hellointents.MainAndroidDialogs.DialogInfoAdapter.Holder;

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
			mGridData.add(new GridItem ("https://photos-3.dropbox.com/t/2/AAA_qqhNfIHqsVC8jGwn0r1FSaP9nwb893R-8J24BPPx1Q/12/67847608/png/32x32/1/_/1/2/Entrada.png/EOK1sqcDGOkiIAIoAg/jGVrNVCbEhu_PtK5US62cUYQTOrLVZkH1InNVc3G8Io?size=1024x768&size_mode=2","Entradas"));
			mGridData.add(new GridItem ("https://photos-5.dropbox.com/t/2/AACRfjM2gvJKKY97dmtYjs167H-As_YqQWOLyDD9CyXMKg/12/67847608/png/32x32/1/_/1/2/Tablas.png/EOK1sqcDGOkiIAIoAg/vFGPm4j7b5UIxG3XkBi2NmvJDSsM1S6R8t0sq7tAODY?size=1024x768&size_mode=2","Tablas"));
			mGridData.add(new GridItem ("https://photos-5.dropbox.com/t/2/AADf1809_uPNeMbMdmL0uJF4oTr7Q9DYUNHA0Og1yl4dig/12/67847608/png/32x32/1/_/1/2/Sandwich.png/EOK1sqcDGOkiIAIoAg/PWs8O4gZbLkQcq-4a2D6OWval3zRZ-CiZtPCbjZuQ_0?size=1024x768&size_mode=2","Sandwiches"));
			mGridData.add(new GridItem ("https://photos-2.dropbox.com/t/2/AAA_ZKjRBSg6ccGLa-hVWw-SDYbyAUoMMM0llRCgy3EmSQ/12/67847608/png/32x32/1/_/1/2/Carnes.png/EOK1sqcDGOkiIAIoAg/aXnMwy16ov5J6l1XFkB-cMhjMUgbBMzcDy9ZWQ8SkEw?size=1024x768&size_mode=2","Carnes"));
			mGridData.add(new GridItem ("https://photos-5.dropbox.com/t/2/AAD0_kvLusT97aqRE2Ct6LBShB7ekYfiJiUgXDR621yFPQ/12/67847608/png/32x32/1/_/1/2/Pizza.png/EOK1sqcDGOkiIAIoAg/TwCZMCIKhmtWQ_x-gAfWgrtLIgo7G9Y1HyiD2bOZhfQ?size=1024x768&size_mode=2","Pizzas"));
			mGridData.add(new GridItem ("https://photos-2.dropbox.com/t/2/AABSqN4Sh8VgOG1lwhUnbutNzviJHhybErO8e2Y8YjD0Lg/12/67847608/png/32x32/1/_/1/2/Pastas.png/EOK1sqcDGOkiIAIoAg/ku8B1eCfwr_BFmZc_5fyna_gqWjzBT557NUP3RkI2FY?size=1024x768&size_mode=2","Pastas"));
			mGridData.add(new GridItem ("https://photos-2.dropbox.com/t/2/AACJ7TGq2I5T9rK3dcwRatvYj09lraGEdhQqWwafJNlH6w/12/67847608/png/32x32/1/_/1/2/Bebida.png/EOK1sqcDGOkiIAIoAg/4YeknzjzTBjT2oGcpfqicXPzHjFLpzuql2P-ZwYSe-8?size=1024x768&size_mode=2","Bebidas"));
			mGridData.add(new GridItem ("https://photos-5.dropbox.com/t/2/AADYIm0ofBI-O9jK4_ZxqY1vk2IieGCkGOvzEDGUMuZs3Q/12/67847608/png/32x32/1/_/1/2/helado.png/EOK1sqcDGOkiIAIoAg/_52iJDVX5vgEUULga7VhhJeKBXLC1XV82-foo5UYX_s?size=1024x768&size_mode=2","Postres"));
			
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
