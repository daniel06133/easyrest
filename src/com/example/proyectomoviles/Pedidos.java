package com.example.proyectomoviles;

import java.util.ArrayList;
import java.util.List;

import com.example.proyectomoviles.basededatos.DataBaseHelper;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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
    private boolean isBack;
    private Pedidos actividadPedidos;
    private TextView txtTotal;
    private TextView txtEmpty;
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	isBack = false;
    	actividadPedidos = this;
        setContentView(R.layout.pedidos);
        db = new DataBaseHelper(this);     
                
        LinearLayout linearMesas = (LinearLayout) findViewById(R.id.linearLPedidos);
        linearMesas.setBackgroundColor(Color.LTGRAY);
        
        TextView txtTitulo = (TextView) findViewById(R.id.txtNroMesa);
        txtTitulo.setText("Mesa "+ (getIntent().getIntExtra("mesa",0) + 1));   
        btnAgregarPedido = (ImageButton) findViewById(R.id.btnAgregarMenu);
        btnAgregarPedido.setOnClickListener(this);
        btnRegistrarPedido = (ImageButton) findViewById(R.id.btnRegistrarPedido);
        btnRegistrarPedido.setOnClickListener(this);
        txtTotal = (TextView) findViewById(R.id.txtTotal);
        txtEmpty = (TextView) findViewById(R.id.txtEmpty);
        
        adapter = new PedidoAdapter();
        cargarPedidosPorMesa((getIntent().getIntExtra("mesa",0) + 1));
     
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
        actualizarTotal();
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
        getListView().setOnItemLongClickListener(this);
        loadPedidos();
    }
	
	private void actualizarTotal()
	{
		int total = 0;
		Pedido p;
		for(int i=0;i<=adapter.getCount()-1;i++)
		{
			p = (Pedido)adapter.getItem(i);
			total += Integer.parseInt(p.getPrecio().substring(1));
		}
		txtTotal.setText("TOTAL: $"+total);
	}
	
	private void cargarPedidosPorMesa(int idMesa)
	{
		Pedido p;		
		Cursor cs = db.obtenerMenusConIdMesa(idMesa);
		Integer countCursor = cs.getCount();
		
		if (countCursor > 0) 
		{
			//txtEmpty.setText("");
			while(cs.moveToNext()) 
			{
		    	p = new Pedido();
		        p.setId(cs.getInt(0));
		        p.setNombre(cs.getString(1));
		        p.setCantidad(cs.getInt(2));
		        p.setPrecio("$" + (cs.getInt(3) * p.getCantidad()));
		        p.setEstado("Registrado"); 
		        adapter.addPedido(p);   
		    }		    
		}
		if (countCursor == 0)

		{
			txtEmpty.setText("No hay pedidos registrados aún");
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
			    int nuevoTotal;
			    nuevoTotal = Integer.parseInt(txtTotal.getText().toString().substring(8))-Integer.parseInt(item.getPrecio().substring(1));
			    txtTotal.setText("Total: $" + nuevoTotal);
			    adapter.remove(position);
				getListView().invalidateViews();
				loadPedidos();				
		}
		else
		{
			Toast toast = Toast.makeText(this, "No es posible eliminar un pedido registrado.",
					Toast.LENGTH_SHORT);
			toast.show();
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
						       
						        int nuevoTotal;
						        nuevoTotal = Integer.parseInt(txtTotal.getText().toString().substring(8)) + (precio/(item.getCantidad()-1));
						        txtTotal.setText("Total: $" + nuevoTotal);
						        
						        
						        holder.txtCantidadPedido.setText(""+ item.getCantidad());
								holder.txtPrecioPedido.setText(""+item.getPrecio());
								loadPedidos();}
							    else
							    {
							    	Toast toast = Toast.makeText(actividadPedidos, "No es posible añadir una unidad a un pedido registrado.",
											Toast.LENGTH_SHORT);
									toast.show();
							    }
								
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
				if (isIntentAvailable(intent)) {
				startActivityForResult(intent, 100 );
			}
		}
		
		
		
		
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/*** Sólo se ejecuta cuando el activity se llamó con
		 * startActivityForResult
		***/	
		//Bundle b = data.getExtras();
		if (requestCode == 100  & resultCode == RESULT_OK){
			Pedido p = (Pedido)data.getSerializableExtra("pedidoTomado");
			adapter.addPedido(p);
		    loadPedidos();
		    actualizarTotal();
		}
	}
	
	private boolean isIntentAvailable(Intent intent) {
		final PackageManager packageManager = getPackageManager();
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        if(!isBack)
	        	showDialog();
	        else
	        	isBack = true;
	        return true;
	    }

	    return super.onKeyDown(keyCode, event);
	}
	private void showDialog () {
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setTitle("Alert");
	    builder.setMessage("¿Está seguro que desea salir? Se perderán aquellos pedidos no registrados.");
	    
	    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            finish();
	        }
	    });

	    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            isBack = false;
	        }
	    });
	    builder.show();
	}
	

}
