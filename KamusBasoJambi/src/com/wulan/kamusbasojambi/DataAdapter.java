package com.wulan.kamusbasojambi;

import java.util.ArrayList;
import java.util.List;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.wulan.kamusbasojambi.model.Kata;
import com.wulan.kamusbasojambi.model.Kategori;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
//untuk mengelola database SQLite
public class DataAdapter extends SQLiteAssetHelper {
	private static final String DATABASE_NAME = "kamus.db";
	private static final int DATABASE_VERSION = 4;
	private SQLiteDatabase mDb;
	Context mContext;
	
	// untuk memanggil database kamus.db
	public DataAdapter(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.mContext = context;
		setForcedUpgrade(DATABASE_VERSION);
	}

	// digunakan untuk mengakses database
	public DataAdapter open() throws SQLException {
		try {
			mDb = this.getReadableDatabase();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this;
	}

	//digunakan untuk menulis database
	public DataAdapter write() throws SQLException {
		try {
			mDb = this.getWritableDatabase();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this;
	}

	//menutup akses database
	@Override
	public void close() {
		mDb.close();
	}

	
	public List<Kata> getSemuaKata(String bahasa) {
		List<Kata> kata = new ArrayList<Kata>();
		String[] columns = new String[] { "id_kata", "indonesia", "jambi",
				"nama_suara", "id_kategori" };

		Cursor cursor = mDb.query("kata", columns, null, null, null, null,
				bahasa);

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			Kata k = new Kata(bahasa);

			k.setId_kata(cursor.getInt(0));
			k.setIndonesia(cursor.getString(1));
			k.setJambi(cursor.getString(2));
			k.setNama_suara(cursor.getString(3));
			k.setId_kategori(cursor.getInt(4));

			kata.add(k);
		}

		return kata;
	}

	//untuk mendapatkan detail kata
	public Kata getDetailKata(int id) {
		//kolom dari database yang akan digunakan
		String[] column = new String[] { "id_kata", "jambi", "indonesia",
				"nama_suara", "contoh_jambi", "contoh_indonesia",
				"nama_kategori" };
		//eksekusi query ke database
		Cursor cursor = mDb.query(
				"kata JOIN kategori ON kata.id_kategori=kategori.id_kategori",
				column, "id_kata=" + id, null, null, null, null);

		cursor.moveToFirst();

		//menyimpan dari database ke interface
		Kata k = new Kata();
		k.setId_kata(cursor.getInt(0));
		k.setJambi(cursor.getString(1));
		k.setIndonesia(cursor.getString(2));
		k.setNama_suara(cursor.getString(3));
		k.setContoh_jambi(cursor.getString(4));
		k.setContoh_indonesia(cursor.getString(5));
		k.setKategori(new Kategori(cursor.getString(6)));

		//mengembalikan kata ke getDetail
		return k;
	}

	public Kata getTerjemahan(String kata, String tipe) {
		Cursor cursor = null;

		String[] column = new String[] { "id_kata", "jambi", "indonesia",
				"nama_suara", "contoh_jambi", "contoh_indonesia", "id_kategori" };

		if (tipe.equals("indonesia jambi")) {
			cursor = mDb.query("kata", column, "indonesia='" + kata
					+ "' COLLATE NOCASE", null, null, null, null);
		} else if (tipe.equals("jambi indonesia")) {
			cursor = mDb.query("kata", column, "jambi='" + kata
					+ "' COLLATE NOCASE", null, null, null, null);
		}

		Kata k = new Kata();
// menampilkan hasil jika ada 
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
			k.setId_kata(cursor.getInt(0));
			k.setJambi(cursor.getString(1));
			k.setIndonesia(cursor.getString(2));
			k.setNama_suara(cursor.getString(3));
			k.setContoh_jambi(cursor.getString(4));
			k.setContoh_indonesia(cursor.getString(5));
			k.setId_kategori(cursor.getInt(6));
		
			//menampilkan jika tidak ada hasilnya dengan mengembalikan 
			//kata yang diinputkan
		} else {
			k.setIndonesia(kata);
			k.setJambi(kata);
		}

		return k;
	}

	public List<Kata> getHistory() {
		List<Kata> kata = new ArrayList<Kata>();
		String[] columns = new String[] { "id_kata", "indonesia", "jambi",
				"nama_suara", "id_kategori", "diakses" };

		//menampung hasil eksekusi query 
		// dengan diurutkan berdasarkan yang terbaru
		Cursor cursor = mDb.query("kata", columns, "diakses IS NOT NULL", null,
				null, null, "datetime(diakses) desc", "10");

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			Kata k = new Kata(true);

			k.setId_kata(cursor.getInt(0));
			k.setIndonesia(cursor.getString(1));
			k.setJambi(cursor.getString(2));
			k.setNama_suara(cursor.getString(3));
			k.setId_kategori(cursor.getInt(4));
			k.setDiakses(cursor.getString(5));

			kata.add(k);
		}

		return kata;
	}

	public void updateData() {

	}

	public void updateDiakses(int id_kata) {
		mDb.execSQL("update kata set diakses=DateTime('now') where id_kata="
				+ id_kata);
	}

	public void clearHistory() {
		mDb.execSQL("update kata set diakses=null");
	}
}
