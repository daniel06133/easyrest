package com.example.proyectomoviles;

import java.util.ArrayList;
import java.util.List;

import com.example.proyectomoviles.basededatos.DataBaseHelper;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
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
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class Pedidos extends ListActivity implements OnItemClickListener,OnItemLongClickListener, android.view.View.OnClickListener
{

    private PedidoAdapter adapter;
    private DataBaseHelper db;
    private ImageButton btnAgregarPedido;
    private ImageButton btnRegistrarPedido;
    private ArrayList<Pedido> listaPedidosTomados;
    //private ArrayList<Pedido> listaPedidosTomadosDesdeCarta;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
        setContentView(R.layout.pedidos);
        db = new DataBaseHelper(this);     
        listaPedidosTomados = new ArrayList<Pedido>();
        
        //TextView txtTotal = (TextView) findViewById(R.id.txtTotal);
        
        LinearLayout linearMesas = (LinearLayout) findViewById(R.id.linearLPedidos);
        linearMesas.setBackgroundColor(Color.LTGRAY);
        
        TextView txtTitulo = (TextView) findViewById(R.id.txtNroMesa);
        txtTitulo.setText("Mesa "+ (getIntent().getIntExtra("mesa",0) + 1));   
        
        btnAgregarPedido = (ImageButton) findViewById(R.id.btnAgregarMenu);
        btnAgregarPedido.setOnClickListener(this);
        btnRegistrarPedido = (ImageButton) findViewById(R.id.btnRegistrarPedido);
        btnRegistrarPedido.setOnClickListener(this);
       
        adapter = new PedidoAdapter();
        cargarPedidosPorMesa((getIntent().getIntExtra("mesa",0) + 1));
        /*
        listaPedidosTomadosDesdeCarta = (ArrayList<Pedido>)getIntent().getSerializableExtra("menus");
        if(listaPedidosTomadosDesdeCarta != null){
	        if(listaPedidosTomados.size() != listaPedidosTomadosDesdeCarta.size())
	        {
	        	Pedido p = listaPedidosTomadosDesdeCarta.get(listaPedidosTomadosDesdeCarta.size()-1);
	        	adapter.addPedido(p);
	        	listaPedidosTomados.add(p);
	        }
        }*/
        
        Menu menu = (Menu) getIntent().getSerializableExtra("menu");
        
        if (menu != null) {
        	Pedido p = new Pedido();
        	p.setCantidad(1);
        	p.setEstado("Tomado");
        	p.setId(menu.getIdMenu());
        	p.setNombre(menu.getNombreMenu());
        	p.setPrecio("$"+menu.getPrecioMenu());
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
		}
		 cs.close();		   
	
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View convertView, int position, long arg3) {
	
		 
	}
	
	
	public boolean onItemLongClick(AdapterView<?> arg0, View convertView, int position, long arg3) {
		
		Pedido item = (Pedido) adapter.getItem(position);
		if(!item.getEstado().equals("Registrado"))
		{
				//listaPedidosTomados.remove((position -(adapter.getCount()-listaPedidosTomados.size())));
				adapter.remove(position);
				getListView().invalidateViews();
				loadPedidos();				
		}
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
		private Context contexto;

		public PedidoAdapter() {
			listaPedidos = new ArrayList<Pedido>();
			inflater = LayoutInflater.from(Pedidos.this);
		}
		
		public PedidoAdapter(ArrayList<Pedido> pedidos)
		{
			listaPedidos = pedidos;
			inflater = LayoutInflater.from(Pedidos.this);
		}
		
		public PedidoAdapter(Context c)
		{
			inflater = LayoutInflater.from(c);
			contexto = c;
		}
 
		
		public ArrayList<Pedido> getPedidos()
		{
			return listaPedidos;}
		
		public void setPedidos(ArrayList<Pedido> pedidos)
		{
			listaPedidos = pedidos;
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
		public View getView(final int position,View convertView, ViewGroup arg2) {
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
							
							LinearLayout l =(LinearLayout) v.getParent();
							ListView lista = (ListView)l.getParent();
							
							
							/*TextView t = (TextView)l.getChildAt(3);
							if(t.getText().equals("Tomado")){
				*/
							   
							    Pedido item =(Pedido) adapter.getItem( lista.getPositionForView(l));
							  							    
							    if(item.getEstado().equals("Tomado")){
						        int precio = Integer.parseInt(item.getPrecio().substring(1));
						        item.setPrecio("$"+( precio + (precio / (item.getCantidad()))));
						        item.setCantidad((item.getCantidad()+1));				        
						        
						        
						        
						        holder.txtCantidadPedido.setText(""+ item.getCantidad());
								holder.txtPrecioPedido.setText(""+item.getPrecio());
								loadPedidos();}
								//aca creo que estaria pudiendo setear el texto nuevo de cantidad y precio 
								//de la fila que le clickio el signo mas. Pero no estariamos 
								//actualizando ese item del adapter..lo cual luego nos traeria inconsistencias
							/*}
							else
							{
								//mostrar toast o algo que avise que no se puede porque ya esta registrado
								//en cocina
							}*/
						}
					});

		            convertView.setTag(holder);
		            //holder.btnAgregar.setTag(adapter.getItem(position));
			} else {
				holder = (Holder) convertView.getTag();
				//holder.btnAgregar.setTag(adapter.getItem(position));
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
		if (v.getId() == R.id.btnRegistrarPedido ) {
		
			for (int i = 0; i < adapter.getCount(); i++) {
				if (((Pedido)adapter.getItem(i)).getEstado().equals("Tomado")) {
					db.insertarMenuEnMesa((getIntent().getIntExtra("mesa",0) + 1), ((Pedido)adapter.getItem(i)).getId(), ((Pedido)adapter.getItem(i)).getCantidad());
				}
			}
			adapter = null;
			adapter = new PedidoAdapter();
			setListAdapter(adapter);
			getListView().invalidateViews();
	        cargarPedidosPorMesa((getIntent().getIntExtra("mesa",0) + 1));
	        loadPedidos();
			
		}
		
		if (v.getId() == R.id.btnAgregarMenu) {
			Intent intent = new Intent((this),GridViewCategorias.class);
			//intent.putExtra("pedidosTomados",listaPedidosTomados );
			//if(listaPedidosTomados.size() != 0)
			//intent.putExtra("menusTomados", listaPedidosTomados);
			if (isIntentAvailable(intent)) {
				startActivityForResult(intent, 100 );
				//startActivity(intent);
			}
		}
		
		
		
		
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/*** Sólo se ejecuta cuando el activity se llamó con
		 * startActivityForResult
		***/
		
		
		Bundle b = data.getExtras();
		if (requestCode == 100  & resultCode == RESULT_OK){
			Pedido p = (Pedido)b.getSerializable("pedidoTomado");
			adapter.addPedido(p);
		    loadPedidos();
		}
	}
	
	private boolean isIntentAvailable(Intent intent) {
		final PackageManager packageManager = getPackageManager();
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}
	

}
