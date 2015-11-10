package Activities;

import java.util.ArrayList;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import com.example.proyectomoviles.*;

public class Mesas extends Activity implements
OnItemClickListener {

	private GridView mGridView;
    private MesaAdapter adapter;
    private ArrayList<Mesa> mGridMesas;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.mesas);

        mGridView = (GridView) findViewById(R.id.mesas);
        
        //Initialize with empty data
        mGridMesas = new ArrayList<Mesa>();
        adapter = new MesaAdapter();
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(this);
        
        //Start download
        loadCategoryData();
        
           
    }
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
	
	private void loadCategoryData() {
		adapter.notifyDataSetChanged();
	}

	class MesaAdapter extends BaseAdapter {
		private ArrayList<Mesa> mGridData;
		private LayoutInflater inflater;

		public MesaAdapter() {
			mGridData = mGridMesas;
			mGridData.add(new Mesa ("http://www.mendebaldea.com/static/servicios/fotos/desayuno_continental_jpg_402x260_crop_q85.jpg"));
			mGridData.add(new Mesa ("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS9Xf3tMaDIoEj5ZorE8NhTbrVytmroXLtKGRfLc-BthAGs4o7T"));
			mGridData.add(new Mesa ("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRjLjR7hhA0hOzJFkl9sejG6MkSzpYi7fHNJuM-ybFnD3JkavrAVw"));
			mGridData.add(new Mesa ("http://media4.letsbonus.com/products/204000/204035/13789310112075-0-680x276.JPG"));
			mGridData.add(new Mesa ("http://kisei.com.ar/wp-content/uploads/2012/01/aseosas-231x256.jpg"));
			mGridData.add(new Mesa ("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQ5ROn_am5tv1AKs1YKu_14qwYqxxK1vmaYCy6AaxO_7HTCOdVylg"));
			
			inflater = LayoutInflater.from(Mesas.this);
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
			private ImageView imagen;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			Holder holder;
			if (convertView == null) {
				holder = new Holder();
				convertView = inflater.inflate(R.layout.gridviewitem,arg2,false);
		            holder.imagen = (ImageView) convertView.findViewById(R.id.mesa);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			Mesa item = mGridData.get(position);
			
			//setiar imagen
			new AsyncImageLoader(item.getImage(), holder.imagen).execute();
			
			return convertView;
		}

	}
}
