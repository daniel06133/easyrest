package com.example.proyectomoviles;

import java.util.ArrayList;

import com.example.proyectomoviles.Mesas.MesaAdapter;
import com.example.proyectomoviles.Mesas.MesaAdapter.Holder;

import android.app.Activity;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Pedidos extends Activity implements
OnItemClickListener{

	private ListView ListView;
    private PedidoAdapter adapter;
    private ArrayList<Pedido> ListaPedidos;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.pedidos);

        ListView = (ListView) findViewById(R.id.listaPedidos);
        
        
        LinearLayout linearMesas = (LinearLayout) findViewById(R.id.linearLPedidos);
        linearMesas.setBackgroundColor(Color.LTGRAY);
        
        TextView txtTitulo = (TextView) findViewById(R.id.txtNroMesa);
        txtTitulo.setBackgroundColor(Color.parseColor("#5173DA"));
        txtTitulo.setTextColor(Color.parseColor("#FFFFFF"));
        txtTitulo.setTextSize(30);
        
        ListView.setBackgroundColor(Color.LTGRAY);
        
        //Initialize with empty data
        ListaPedidos = new ArrayList<Pedido>();
        adapter = new PedidoAdapter();
        
        ListView.setAdapter(adapter);
        ListView.setOnItemClickListener(this);
        
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
	
	class PedidoAdapter extends BaseAdapter {
		private ArrayList<Pedido> ListaPedidos2;
		private LayoutInflater inflater;

		public PedidoAdapter() {
			ListaPedidos2 = ListaPedidos;
			
			String nroMesa = getIntent().getStringExtra("mesa");
			Pedido p = new Pedido();
			p.setNombre("Pedido mesa" + nroMesa);
			p.setCantidad("Cantidad 1");
			p.setPrecio("$20");
			p.setEstado("Tomado");
			
			ListaPedidos2.add(p);
			
			Pedido p2 = new Pedido();
			p.setNombre("Pedido mesa" + nroMesa);
			p.setCantidad("Cantidad 2");
			p.setPrecio("$200");
			p.setEstado("Registrado");
			
			ListaPedidos2.add(p2);
			
			
			inflater = LayoutInflater.from(Pedidos.this);
		}

		@Override
		public int getCount() {
			return ListaPedidos2.size();
		}

		@Override
		public Object getItem(int position) {
			return ListaPedidos2.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		class Holder {
			private TextView txtNombrePedido;
			private TextView txtPrecioPedido;
			private TextView txtCantidadPedido;
			private TextView txtEstadoPedido;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			Holder holder;
			if (convertView == null) {
				holder = new Holder();
				convertView = inflater.inflate(R.layout.pedido,arg2,false);
		            holder.txtNombrePedido = (TextView) convertView
		            		.findViewById(R.id.txtNombrePedido);
		            holder.txtCantidadPedido = (TextView) convertView
		            		.findViewById(R.id.txtCantidadPedido);
		            holder.txtEstadoPedido = (TextView) convertView
		            		.findViewById(R.id.txtEstadoPedido);
		            holder.txtPrecioPedido = (TextView) convertView
		            		.findViewById(R.id.txtPrecioPedido);

		            convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			Pedido item = ListaPedidos.get(position);
			
			
			holder.txtNombrePedido.setText(item.getNombre());
			holder.txtPrecioPedido.setText(item.getPrecio());
			holder.txtCantidadPedido.setText(item.getCantidad());
			holder.txtEstadoPedido.setText(item.getEstado());
			
			return convertView;
		}

	}

}
