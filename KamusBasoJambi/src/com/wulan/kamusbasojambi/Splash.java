package com.wulan.kamusbasojambi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

public class Splash extends Activity {
	ProgressBar pbSplash;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash); //menampilkan layout

		//inisialisasi apa yang ada di splash
		pbSplash = (ProgressBar) findViewById(R.id.pbSplash);

		//membuat timer (thread)
		Thread timer = new Thread() {
			@Override
			public void run() {
				try {
					sleep(3000);
				} catch (Exception e) {
					// TODO: handle exception
				} finally {
					
					// perpindahan setelah splash ke MenuUtama
					Intent i = new Intent(Splash.this, MenuUtama.class);
					startActivity(i);
					finish();
				}
			}
		};
		
		//memulai timer
		timer.start();
	}
}
