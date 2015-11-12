package com.example.proyectomoviles;

import java.util.ArrayList;

import com.example.proyectomoviles.basededatos.DataBaseHelper;

import android.app.Activity;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Pedidos extends ListActivity implements OnItemClickListener 
{

    private PedidoAdapter adapter;
    private DataBaseHelper db;
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.pedidos);
        db = new DataBaseHelper(this);        
       
        //TextView txtTotal = (TextView) findViewById(R.id.txtTotal);
        
        LinearLayout linearMesas = (LinearLayout) findViewById(R.id.linearLPedidos);
        linearMesas.setBackgroundColor(Color.LTGRAY);
        
        TextView txtTitulo = (TextView) findViewById(R.id.txtNroMesa);
        txtTitulo.setText("Mesa "+ (getIntent().getIntExtra("mesa",0) + 1));   
       
       // txtTotal.setText("TOTAL $2000");
        adapter = new PedidoAdapter();
        
        cargarPedidosPorMesa((getIntent().getIntExtra("mesa",0) + 1));
        
        
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
        
        //Start download
        loadPedidos();
        
           
    }
	
	private void cargarPedidosPorMesa(int idMesa)
	{
		Pedido p;
		
		Cursor cs = db.obtenerMenusConIdMesa(idMesa);
		
		if (cs != null) {
		    while(cs.moveToNext()) {
		    	p = new Pedido();
		        p.setId(cs.getInt(0));
		        p.setNombre(cs.getString(1));
		        p.setCantidad(cs.getInt(2));
		        p.setPrecio("$" + (cs.getInt(3) * p.getCantidad()));
		        p.setEstado("Registrado");
		        
		        adapter.addPedido(p);   
		    }
		    cs.close();
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View convertView, int position, long arg3) {
	
		adapter.remove(position);
		getListView().invalidateViews();
		setListAdapter(adapter);
		loadPedidos(); 
	}
	
	
	public void onItemLongClick(AdapterView<?> arg0, View convertView, int position, long arg3) {
		
		adapter.remove(position);
		loadPedidos();
		
	}
	
	private void loadPedidos() {
		adapter.notifyDataSetChanged();
	}
	
	class Holder {
		private TextView txtNombrePedido;
		private TextView txtPrecioPedido;
		private TextView txtCantidadPedido;
		private TextView txtEstadoPedido;
		private ImageButton btnAgregar;
	}
	
	class PedidoAdapter extends BaseAdapter {
		private ArrayList<Pedido> listaPedidos;
		private LayoutInflater inflater;

		public PedidoAdapter() {
			listaPedidos = new ArrayList<Pedido>();
			inflater = LayoutInflater.from(Pedidos.this);
		}

		public void addPedido(Pedido p)
		{
			if (p != null)
				listaPedidos.add(p);
		}
		@Override
		public int getCount() {
			return listaPedidos.size();
		}

		@Override
		public Object getItem(int position) {
			return listaPedidos.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		public void remove(int position)
		{
			listaPedidos.remove(position);
		}
		
		
		@Override
		public View getView(final int position, View convertView, ViewGroup arg2) {
			final Holder holder;
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
		            holder.btnAgregar = (ImageButton) convertView
		            		.findViewById(R.id.btnAgregar);
		            holder.btnAgregar.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							Pedido item = (Pedido) adapter.getItem(position);					        
					        int precio = Integer.parseInt(item.getPrecio().substring(1));
					        item.setPrecio("$"+( precio + (precio / (item.getCantidad()))));
					        item.setCantidad((item.getCantidad()+1));
					        holder.txtCantidadPedido.setText(""+ item.getCantidad());
							holder.txtPrecioPedido.setText(""+item.getPrecio());
						}
					});

		            convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			Pedido item = (Pedido) adapter.getItem(position);
			
				
			holder.txtNombrePedido.setText(item.getNombre());
			holder.txtPrecioPedido.setText(item.getPrecio());
			holder.txtCantidadPedido.setText(""+item.getCantidad());
			holder.txtEstadoPedido.setText(item.getEstado()); 
		        
			return convertView;
		}

	}
}
