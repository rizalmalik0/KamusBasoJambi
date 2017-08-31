package com.wulan.kamusbasojambi;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.wulan.kamusbasojambi.model.Kata;

import android.app.Activity;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

//perpindahanradio button Jambi Indonesia atau Indonesia Jambi
public class Terjemahan extends Activity implements OnCheckedChangeListener,
		OnClickListener {
	RadioGroup rgTerjemahan;
	RadioButton rbIndonesiaJambi, rbJambiIndonesia;
	EditText etInputKata, etHasilTerjemahan;
	Button btnTerjemahan;
	DataAdapter data;
	List<Kata> terjemahanKata;
	String tipe;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.terjemahan);

		// inisialisasi
		data = new DataAdapter(this);
		rgTerjemahan = (RadioGroup) findViewById(R.id.rgTerjemahan);
		rbIndonesiaJambi = (RadioButton) findViewById(R.id.rbIndonesiaJambi);
		rbJambiIndonesia = (RadioButton) findViewById(R.id.rbJambiIndonesia);
		etInputKata = (EditText) findViewById(R.id.etInputKata);
		etHasilTerjemahan = (EditText) findViewById(R.id.etHasilTerjemahan);
		btnTerjemahan = (Button) findViewById(R.id.btnTerjemahan);
		terjemahanKata = new ArrayList<Kata>();
		tipe = "jambi indonesia";

		// listener
		rgTerjemahan.setOnCheckedChangeListener(this);
		btnTerjemahan.setOnClickListener(this);
	}

	// memilih salah satu tipe terjemahan
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.rbIndonesiaJambi:
			tipe = "indonesia jambi";
			break;
		case R.id.rbJambiIndonesia:
			tipe = "jambi indonesia";
			break;
		default:
			break;
		}
	}

	// proses terjemahan
	@Override
	public void onClick(View v) {
		String input = etInputKata.getText().toString();
		// membagi kata dari inputan
		String[] split = input.split(" ");
		// hasil terjemahan yang dihapus se
		terjemahanKata.clear();
		String hasil = "";

		// proses menerjemahkan dari database, jika tidak ada maka akan
		// dikembalikan
		data.open();
		for (int i = 0; i < split.length; i++) {
			if (!split[i].trim().equals("")) {
				Kata k = data.getTerjemahan(split[i], tipe);
				terjemahanKata.add(k);
			}
		}
		data.close();
		// mengambil kata jambi atau indonesia dari kata yang sudah didapat
		if (tipe.equals("indonesia jambi")) {
			for (int i = 0; i < terjemahanKata.size(); i++) {
				hasil += " " + terjemahanKata.get(i).getJambi();
			}
		} else if (tipe.equals("jambi indonesia")) {
			for (int i = 0; i < terjemahanKata.size(); i++) {
				hasil += " " + terjemahanKata.get(i).getIndonesia();
			}
		}

		if (hasil.length() != 0) {
			StringBuilder builder = new StringBuilder(
					hasil.toLowerCase(Locale.ENGLISH));
			builder.setCharAt(0, Character.toUpperCase(builder.charAt(0)));
			hasil = builder.toString();
		}

		etHasilTerjemahan.setText(hasil.trim());
	}
}
