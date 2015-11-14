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
        /*
        TextView txtTitulo = (TextView) findViewById(R.id.txtSuperiorMesas);
        txtTitulo.setBackgroundColor(Color.parseColor("#5173DA"));
        txtTitulo.setTextColor(Color.parseColor("#FFFFFF"));
        txtTitulo.setTextSize(30);
        */
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
		
		Integer numeroDeMesa = arg2;
		
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
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa11_zpshhecjwkf.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa12_zpse2cyfhnc.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa13_zpsuezeprmm.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa14_zpswuxcdhey.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa15_zpsowsdqqk3.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa16_zpsv87cznnd.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa17_zpsk42kkeoi.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa18_zpsdbkjuuqu.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa19_zpsvxdagkkk.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa110_zpsmewynfgv.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa111_zpsxiqh2b14.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa112_zpsu5c2w5ds.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa113_zpsvlsgtc1o.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa114_zps0n0wihdv.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa115_zpserldeprj.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa116_zpsp0cezinx.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa117_zpsdeejzbrr.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa118_zpsaimfwjpe.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa119_zpsktanrzla.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa120_zpscfmmbwda.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa121_zpsplurldol.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa122_zpsmk0nogzs.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa123_zps9pnxdpq4.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa124_zpsmwnx07rz.png"));
			mGridData.add(new Mesa ("http://i1379.photobucket.com/albums/ah133/daniel0613/mesa125_zps1pkix2mr.png"));
			
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
