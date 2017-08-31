package com.wulan.kamusbasojambi.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Kata {
	private int id_kata;
	private String jambi;
	private String indonesia;
	private String contoh_jambi;
	private String contoh_indonesia;
	private String nama_suara;
	private String diakses;
	private int id_kategori;
	private boolean history = false;
	private String bahasa;
	private Kategori kategori;

	public Kategori getKategori() {
		return kategori;
	}

	public void setKategori(Kategori kategori) {
		this.kategori = kategori;
	}

	public Kata() {
		// TODO Auto-generated constructor stub
	}

	public Kata(String bahasa) {
		this.bahasa = bahasa;
	}

	public Kata(boolean history) {
		this.history = history;
	}

	public int getId_kata() {
		return id_kata;
	}

	public void setId_kata(int id_kata) {
		this.id_kata = id_kata;
	}

	public String getJambi() {
		return jambi;
	}

	public void setJambi(String jambi) {
		this.jambi = jambi;
	}

	public String getIndonesia() {
		return indonesia;
	}

	public void setIndonesia(String indonesia) {
		this.indonesia = indonesia;
	}

	public String getContoh_jambi() {
		return contoh_jambi;
	}

	public void setContoh_jambi(String contoh_jambi) {
		this.contoh_jambi = contoh_jambi;
	}

	public String getContoh_indonesia() {
		return contoh_indonesia;
	}

	public void setContoh_indonesia(String contoh_indonesia) {
		this.contoh_indonesia = contoh_indonesia;
	}

	public String getNama_suara() {
		return nama_suara;
	}

	public void setNama_suara(String nama_suara) {
		this.nama_suara = nama_suara;
	}

	public int getId_kategori() {
		return id_kategori;
	}

	public void setId_kategori(int id_kategori) {
		this.id_kategori = id_kategori;
	}

	public String getDiakses() {
		String date = diakses;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.ENGLISH);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date testDate = null;
		try {
			testDate = sdf.parse(date);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM-HH:mm",
				Locale.ENGLISH);
		String newFormat = formatter.format(testDate);

		return newFormat;
	}

	public void setDiakses(String diakses) {
		this.diakses = diakses;
	}

	public boolean isHistory() {
		return history;
	}

	public void setHistory(boolean history) {
		this.history = history;
	}

	@Override
	public String toString() {
		if (bahasa.equals("jambi")) {
			return getJambi();
		} 
		return getIndonesia();
	}
}