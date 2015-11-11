package com.example.proyectomoviles;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.proyectomoviles.R;

import android.app.Activity;


public class GridViewActivity extends Activity{

	 private static final String TAG = GridViewActivity.class.getSimpleName();
	    private GridView mGridView;
	   //private GridViewAdapter mGridAdapter;
	    private MenusAdapter mGridAdapter;
	    private ArrayList<GridItem> mGridData;
	    private String categoriaSeleccionada = "";
	    private String FEED_URL = "http://javatechig.com/?json=get_recent_posts&count=45";

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	    	super.onCreate(savedInstanceState);
	        setContentView(R.layout.categorias);
	        
	      categoriaSeleccionada = getIntent().getStringExtra("categoria");

	        mGridView = (GridView) findViewById(R.id.gvCategorias);

	        //Carga de imagenes de internet por json
	        
	        mGridData = new ArrayList<GridItem>();
	       // mGridAdapter = new GridViewAdapter(this, R.layout.gridviewitem, mGridData);
	       
	        mGridAdapter = new MenusAdapter();
	        mGridView.setAdapter(mGridAdapter);

	        //Start download
	       // new AsyncHttpTask().execute(FEED_URL); 
	        loadData();
	    }
	    
	    public void loadData()
	    {
	    	mGridAdapter.notifyDataSetChanged();
	    }
	    class MenusAdapter extends BaseAdapter {
			private ArrayList<GridItem> mGridData1;
			private LayoutInflater inflater;

			public  MenusAdapter() {
				mGridData1 = mGridData;
				if(categoriaSeleccionada.equals(""))
				mGridData1.add(new GridItem ("http://cerveceriahondurena.com/_uploads/litro_botella.jpg","CocaCola"));
				if(categoriaSeleccionada.equals("Bebidas"))
				{
					mGridData1.add(new GridItem ("http://cerveceriahondurena.com/_uploads/litro_botella.jpg","CocaCola"));
					mGridData1.add(new GridItem ("http://cerveceriahondurena.com/_uploads/sprite_12_onzi.jpg","Sprite"));
					mGridData1.add(new GridItem ("http://vinotecalavia.com/3500-thickbox_default/paso-de-los-toros-pomelo-x-156-x-15-l.jpg","Paso de los Toros"));
					mGridData1.add(new GridItem ("http://mla-s2-p.mlstatic.com/cerveza-quilmes-porron-local-palermo-4251-MLA3495378982_122012-F.jpg","Quilmes"));
					mGridData1.add(new GridItem ("http://i.huffpost.com/gen/908842/images/o-BUDWEISER-BUDVAR-facebook.jpg","Budweiser"));
					mGridData1.add(new GridItem ("http://www.cerveceriachile.cl/wp-content/uploads/2011/03/stella_img1.png","Stella"));
				}
				if(categoriaSeleccionada.equals("Lomos"))
				{
					mGridData1.add(new GridItem ("http://www.tortaslocashipocampo.net/images/lomo_especial.jpg","Especial"));
					mGridData1.add(new GridItem ("https://api.plupin.com/pictures/promotions/150/pictures/original/5e2611287366c1810b1551ddfa19d3833ac3e408.jpg?1403117995","Completo"));
					mGridData1.add(new GridItem ("http://fotos.imagenesdeposito.com/imagenes/l/lomo_a_lo_pobre_el_plato_de_chile-29710.jpg","Al plato"));
					mGridData1.add(new GridItem ("http://1.bp.blogspot.com/_tQKG-8nLrdI/TCT__HCix1I/AAAAAAAAADU/-kQ8hsbxQfA/s1600/1760POLLO.JPG","De Pollo"));
					mGridData1.add(new GridItem ("http://mariachisite.com/img/lomo-macho.jpg","De la casa"));
					mGridData1.add(new GridItem ("http://www.lomitos348.com/images/home/carousel_01.jpg","Extra Grande"));
				}
				
				
				
				inflater = LayoutInflater.from(GridViewActivity.this);
			}
			
			
			public void setGridData(ArrayList<GridItem> data)
			{
				mGridData1 = data;
				notifyDataSetChanged();
			}
			@Override
			public int getCount() {
				return mGridData1.size();
			}

			@Override
			public Object getItem(int position) {
				return mGridData1.get(position);
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
				GridItem item = mGridData1.get(position);
				
				holder.descripcion.setText(item.getTitle());
				//setiar imagen
				new AsyncImageLoader(item.getImage(), holder.imagen).execute();
				
				return convertView;
			}

		}
	    

	    //Downloading data asynchronously
	    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

	        @Override
	        protected Integer doInBackground(String... params) {
	            Integer result = 0;
	            try {
	                 //Create Apache HttpClient
	                HttpClient httpclient = new DefaultHttpClient();
	                HttpResponse httpResponse = httpclient.execute(new HttpGet(params[0]));
	                int statusCode = httpResponse.getStatusLine().getStatusCode();

	            	
	                // 200 represents HTTP OK
	                if (statusCode == 200) {
	                    String response = streamToString(httpResponse.getEntity().getContent());
	                    parseResult(response);
	                    result = 1; // Successful
	                } else {
	                    result = 0; //"Failed
	                }
	            } catch (Exception e) {
	                Log.d(TAG, e.getLocalizedMessage());
	            }
	            return result;
	        }

	        @Override
	        protected void onPostExecute(Integer result) {
	            // Download complete. Let us update UI
	            if (result == 1) {
	                mGridAdapter.setGridData(mGridData);
	            } else {
	                Toast.makeText(GridViewActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
	            }
	        }
	    }

	    String streamToString(InputStream stream) throws IOException {
	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
	        String line;
	        String result = "";
	        while ((line = bufferedReader.readLine()) != null) {
	            result += line;
	        }

	        // Close stream
	        if (null != stream) {
	            stream.close();
	        }
	        return result;
	    }

	    /**
	     * Parsing the feed results and get the list
	     * @param result
	     */
	    private void parseResult(String result) {
	        try {
	            JSONObject response = new JSONObject(result);
	            JSONArray posts = response.optJSONArray("posts");
	            GridItem item;
	            for (int i = 0; i < posts.length(); i++) {
	                JSONObject post = posts.optJSONObject(i);
	                String title = post.optString("title");
	                item = new GridItem();
	                item.setTitle(title);
	                JSONArray attachments = post.getJSONArray("attachments");
	                if (null != attachments && attachments.length() > 0) {
	                    JSONObject attachment = attachments.getJSONObject(0);
	                    if (attachment != null)
	                        item.setImage(attachment.getString("url"));
	                }
	                mGridData.add(item);
	            }
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	    }
	    
		
	}
