package com.wulan.kamusbasojambi;

import java.util.List;

import com.wulan.kamusbasojambi.model.Kata;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

//menampilkan listkata pada history
public class History extends Activity implements OnItemClickListener {
	ListView lvHistory;
	TextView txtKosong;
	List<Kata> kata;
	DataAdapter data;
	CustomAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history);

		// init
		lvHistory = (ListView) findViewById(R.id.lvHistory);
		txtKosong = (TextView) findViewById(R.id.txtKosong);
		data = new DataAdapter(History.this);

		// listener
		lvHistory.setOnItemClickListener(this);
	}

	// update data setelah diklik
	@Override
	protected void onResume() {
		super.onResume();

		// get data
		data.open();
		kata = data.getHistory();
		data.close();

		// adapter
		adapter = new CustomAdapter(this, kata, true);
		adapter.notifyDataSetChanged();
		lvHistory.setAdapter(adapter);
		
		if (kata.isEmpty()) {
			lvHistory.setVisibility(View.GONE);
			txtKosong.setVisibility(View.VISIBLE);
		} 
	}

	//clear pada menu item yang digunaka untuk clear history
	public void clear(MenuItem item) {
		data.write();
		data.clearHistory();
		data.close();

		//notifikasi jika data berubah
		kata.clear();
		adapter.notifyDataSetChanged();
		lvHistory.setAdapter(adapter);

		// kosong
		lvHistory.setVisibility(View.GONE);
		txtKosong.setVisibility(View.VISIBLE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.history, menu);

		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Kata k = (Kata) parent.getItemAtPosition(position);
		Intent i = new Intent(History.this, DetailTerjemahan.class);
		i.putExtra("id_kata", k.getId_kata());
		startActivity(i);
	}
}
