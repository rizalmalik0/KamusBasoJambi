package com.wulan.kamusbasojambi;

import java.io.IOException;

import com.wulan.kamusbasojambi.model.Kata;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

// untuk interface detail kata 
//implement mendefinisikan listener yang akan digunakan
public class DetailTerjemahan extends Activity implements OnClickListener,
		OnCompletionListener {
	TextView txtJambi, txtIndonesia, txtContohJambi, txtContohIndonesia,
			txtKategori;
	Button btnSuara;
	DataAdapter data;
	Kata detailKata;
	MediaPlayer media;
	int id_kata;

	//mengganti dengan apa yang diinginkan
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_kata);

		// init untuk inisialisasi java agar bisa diakses
		txtJambi = (TextView) findViewById(R.id.txtJambi);
		txtIndonesia = (TextView) findViewById(R.id.txtIndonesia);
		txtKategori = (TextView) findViewById(R.id.txtKategori);
		txtContohJambi = (TextView) findViewById(R.id.txtContohJambi);
		txtContohIndonesia = (TextView) findViewById(R.id.txtContohIndonesia);
		btnSuara = (Button) findViewById(R.id.btnSuara);

		data = new DataAdapter(this);
		media = new MediaPlayer();
		id_kata = getIntent().getIntExtra("id_kata", 0);

		// adapter untuk memanaggil database
		data.open();
		detailKata = data.getDetailKata(id_kata);
		data.updateDiakses(id_kata);
		data.close();

		// memanggil masing masing elemen ke interface
		txtJambi.setText(detailKata.getJambi());
		txtIndonesia.setText(detailKata.getIndonesia());
		txtKategori.setText(detailKata.getKategori().getNama_kategori());
		txtContohJambi.setText(detailKata.getContoh_jambi());
		txtContohIndonesia.setText(detailKata.getContoh_indonesia());

		// listener ( set button suara setelah )
		btnSuara.setOnClickListener(this);
		media.setOnCompletionListener(this);
	}

	// proses menstop saat activity berhebti
	@Override
	protected void onStop() {
		super.onStop();

		if (media.isPlaying())
			media.stop();

		btnSuara.setText("Suara");
	}

	@Override
	public void onClick(View v) {
		String mp3File = "suara/" + detailKata.getNama_suara();

		//play suara
		AssetFileDescriptor afd;

		try {
			if (media.isPlaying()) {
				media.stop();
				btnSuara.setText("Suara");
			} else {
				afd = getAssets().openFd(mp3File);
				media.reset();
				media.setDataSource(afd.getFileDescriptor(),
						afd.getStartOffset(), afd.getLength());

				media.prepare();
				media.start();
				btnSuara.setText("Stop");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// menghentikan jika mediaplayer selesai den set ulang suara
	@Override
	public void onCompletion(MediaPlayer mp) {
		media.stop();
		btnSuara.setText("Suara");
	}

}
