package com.example.proyectomoviles;

import java.util.ArrayList;
import java.util.List;

import com.example.proyectomoviles.basededatos.DataBaseHelper;

import android.app.Activity;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class Pedidos extends ListActivity implements OnItemClickListener,OnItemLongClickListener, android.view.View.OnClickListener
{

    private PedidoAdapter adapter;
    private DataBaseHelper db;
    private ImageButton btnAgregarPedido;
    
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
        
        btnAgregarPedido = (ImageButton) findViewById(R.id.btnAgregarMenu);
        btnAgregarPedido.setOnClickListener(this);
       
        adapter = new PedidoAdapter();
        cargarPedidosPorMesa((getIntent().getIntExtra("mesa",0) + 1));
        Menu menu = (Menu) getIntent().getSerializableExtra("menu");
        
        if (menu != null) {
        	Pedido p = new Pedido();
        	p.setCantidad(1);
        	p.setEstado("Tomado");
        	p.setId(menu.getIdMenu());
        	p.setNombre(menu.getNombreMenu());
        	p.setPrecio(menu.getPrecioMenu());
        	adapter.addPedido(p);
		}     
                
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
        getListView().setOnItemLongClickListener(this);
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
	
		 
	}
	
	
	public boolean onItemLongClick(AdapterView<?> arg0, View convertView, int position, long arg3) {
		adapter.remove(position);
		getListView().invalidateViews();
		setListAdapter(adapter);
		loadPedidos();
		return true;
		
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

	@Override
	public void onClick(View v) {
		Intent intent = new Intent((this),GridViewCategorias.class);
		if (isIntentAvailable(intent)) {
			startActivity(intent);
		}
		
		
	}
	private boolean isIntentAvailable(Intent intent) {
		final PackageManager packageManager = getPackageManager();
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}
	
}
