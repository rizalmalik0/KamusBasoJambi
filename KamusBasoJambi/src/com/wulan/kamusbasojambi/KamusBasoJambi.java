package com.wulan.kamusbasojambi;

import android.app.Application;

//mengubah seluruh font pada aplikasi
public final class KamusBasoJambi extends Application{
	@Override
	public void onCreate() {
		super.onCreate();
		
        //dari font monospace ke dosmiltatorce
		FontsOverride.setDefaultFont(this, "MONOSPACE", "Dosmilcatorce.ttf");
	}
}
