package com.example.proyectomoviles;

import java.util.ArrayList;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;



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
        
        
        LinearLayout linearMesas = (LinearLayout) findViewById(R.id.linearLMesas);
        linearMesas.setBackgroundColor(Color.LTGRAY);
        
        TextView txtTitulo = (TextView) findViewById(R.id.txtSuperiorMesas);
        txtTitulo.setBackgroundColor(Color.parseColor("#5173DA"));
        txtTitulo.setTextColor(Color.parseColor("#FFFFFF"));
        txtTitulo.setTextSize(30);
        mGridView.setBackgroundColor(Color.LTGRAY);
        
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
		Intent intent = new Intent(this,Pedidos.class);
		
		String numeroDeMesa = "" + arg2;
		
		if(intent != null)
		{
			intent.putExtra("mesa",numeroDeMesa);
			
			startActivity(intent);
		}
	}
	
	private void loadCategoryData() {
		adapter.notifyDataSetChanged();
	}

	class MesaAdapter extends BaseAdapter {
		private ArrayList<Mesa> mGridData;
		private LayoutInflater inflater;

		public MesaAdapter() {
			mGridData = mGridMesas;
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa1_zpschpfmpuv.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa1_zpschpfmpuv.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa1_zpschpfmpuv.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa1_zpschpfmpuv.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa1_zpschpfmpuv.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa1_zpschpfmpuv.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa1_zpschpfmpuv.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa1_zpschpfmpuv.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa1_zpschpfmpuv.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa1_zpschpfmpuv.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa1_zpschpfmpuv.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa1_zpschpfmpuv.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa1_zpschpfmpuv.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa1_zpschpfmpuv.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa1_zpschpfmpuv.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa1_zpschpfmpuv.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa1_zpschpfmpuv.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa1_zpschpfmpuv.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa1_zpschpfmpuv.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa1_zpschpfmpuv.png"));
			
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
			
			//setear imagen
			new AsyncImageLoader(item.getImage(), holder.imagen).execute();
			
			return convertView;
		}

	}
}
