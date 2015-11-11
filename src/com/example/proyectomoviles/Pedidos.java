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
import android.widget.EditText;
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
        //TextView txtTotal = (TextView) findViewById(R.id.txtTotal);
        
        LinearLayout linearMesas = (LinearLayout) findViewById(R.id.linearLPedidos);
        linearMesas.setBackgroundColor(Color.LTGRAY);
        
        TextView txtTitulo = (TextView) findViewById(R.id.txtNroMesa);
        txtTitulo.setText("Mesa "+ (getIntent().getIntExtra("mesa",0) + 1));    
       // txtTotal.setText("TOTAL $2000");
        
       // ListView.setBackgroundColor(Color.LTGRAY);
        
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
			
			Integer nroMesa = getIntent().getIntExtra("mesa",0);
			Pedido p = new Pedido();
			p.setNombre("MILANESA DE CARNE A LA NAPOLITANA CON PAPAS AL HORNO");
			p.setCantidad("3");
			p.setPrecio("$20");
			p.setEstado("Tomado");
			
			ListaPedidos2.add(p);
			
			Pedido p2 = new Pedido();
			p2.setNombre("Coca cola");
			p2.setCantidad("5");
			p2.setPrecio("$8922");
			p2.setEstado("Registrado");
			
			ListaPedidos2.add(p2);
			
			Pedido p3 = new Pedido();
			p3.setCantidad("23");
			p3.setNombre("VACIO RELLENO ACOMPAÑADO DE CROQUETAS DE FIDEO.");
			p3.setPrecio("$380,50");
			p3.setEstado("Registrado");
			
			ListaPedidos2.add(p3);
			
			
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
