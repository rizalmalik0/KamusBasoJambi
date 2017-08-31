package com.wulan.kamusbasojambi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

// 
public class MenuUtama extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_utama);// menampilkan tampilan layout menu
											// utama
	}

	// yang dijalanin waktu diklik
	public void daftarKata(View v) {
		Intent i = new Intent(this, DaftarKata.class);
		startActivity(i);
	}

	public void terjemahan(View v) {
		Intent i = new Intent(this, Terjemahan.class);
		startActivity(i);
	}

	public void aksara(View v) {
		Intent i = new Intent(this, Aksara.class);
		startActivity(i);
	}

	public void history(View v) {
		Intent i = new Intent(this, History.class);
		startActivity(i);
	}

	public void about(View v) {
		Intent i = new Intent(this, About.class);
		startActivity(i);
	}

	public void exit(View v) {
		keluar();
	}
	
	private void keluar() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("Keluar");

		dialog.setMessage("Anda Yakin ingin keluar?");
		dialog.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		dialog.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
			}
		});
		dialog.show();
	}
	
	@Override
	public void onBackPressed() {
		keluar();
	}
}
