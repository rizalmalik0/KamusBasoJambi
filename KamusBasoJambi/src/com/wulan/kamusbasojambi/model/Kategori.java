package com.wulan.kamusbasojambi.model;

public class Kategori {
	private int id_kategori;
	private String nama_kategori;

	public Kategori() {
		// TODO Auto-generated constructor stub
	}

	public Kategori(String nama_kategori) {
		this.nama_kategori = nama_kategori;
	}

	public int getId_kategori() {
		return id_kategori;
	}

	public void setId_kategori(int id_kategori) {
		this.id_kategori = id_kategori;
	}

	public String getNama_kategori() {
		return nama_kategori;
	}

	public void setNama_kategori(String nama_kategori) {
		this.nama_kategori = nama_kategori;
	}

	@Override
	public String toString() {
		return getNama_kategori();
	}
}
