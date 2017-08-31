package com.wulan.kamusbasojambi;

import java.util.List;

import com.wulan.kamusbasojambi.model.Kata;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

// membuat halaman daftar kata dengan event onitemclick pada list view
public class DaftarKata extends Activity implements OnItemClickListener {
	EditText etCari;
	ListView lvKata;
	List<Kata> kata;
	DataAdapter data;
	CustomAdapter adapter; // menampilkan custom sesuai keinginan di list view
	boolean jambi = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.daftar_kata);

		// init
		etCari = (EditText) findViewById(R.id.etCari);
		lvKata = (ListView) findViewById(R.id.listDaftarKata);
		//membuat koneksi ke database
		data = new DataAdapter(this);
		//menampilkan background
		getWindow().setBackgroundDrawableResource(R.drawable.background2);

		// get database semua kata jambi
		data.open();
		kata = data.getSemuaKata("jambi");
		data.close();

		// set adapter
		adapter = new CustomAdapter(this, kata, false);
		lvKata.setAdapter(adapter);
		
		//memfilter adapter dari etCari
		etCari.addTextChangedListener(new TextWatcher() {

			//memfilter kata yang ada atau string diketik 
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				System.out.println("Text [" + s + "]");

				adapter.getFilter().filter(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		// listener pada listview
		lvKata.setOnItemClickListener(this);
	}

	//merubah menu item dengan nama ubah 
	public void ubah(MenuItem item) {
		jambi = !jambi;

		if (jambi) {
			kata.clear();

			data.open();
			kata.addAll(data.getSemuaKata("jambi"));
			data.close();

			adapter = new CustomAdapter(this, kata, false);
			lvKata.setAdapter(adapter);

			item.setTitle("Indonesia");
		} else {
			kata.clear();

			data.open();
			kata.addAll(data.getSemuaKata("indonesia"));
			data.close();

			adapter = new CustomAdapter(this, kata, false);
			lvKata.setAdapter(adapter);

			item.setTitle("Jambi");
		}

		etCari.setText("");
	}

	//membuat option menu dihalaman acivity
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.daftar_kata, menu);// menambahkan xml ke xml baru

		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// mengambil kata dari daftar kata untuk digunakan kedetail kata dengan
		// mengirim id_kata
		Kata k = (Kata) parent.getItemAtPosition(position);
		Intent i = new Intent(DaftarKata.this, DetailTerjemahan.class);
		i.putExtra("id_kata", k.getId_kata());
		startActivity(i);
	}

}
