package com.wulan.kamusbasojambi;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.wulan.kamusbasojambi.model.Kata;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

// untuk menampilkan list view sesuai keinginan sesuai filter list kata
public class CustomAdapter extends BaseAdapter implements Filterable {
	private List<Kata> originalData = null;
	private List<Kata> filteredData = null;
	private LayoutInflater mInflater;
	private ItemFilter mFilter = new ItemFilter();
	private boolean history;

	public CustomAdapter(Context mContext, List<Kata> kata, boolean history) {
		originalData = kata;
		filteredData = kata;
		mInflater = LayoutInflater.from(mContext);
		this.history = history;
	}

	@Override
	public int getCount() {
		return filteredData.size();
	}

	@Override
	public Object getItem(int position) {
		return filteredData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		if (convertView == null) {
			if (history) {
				convertView = mInflater.inflate(R.layout.history_item, parent,
						false);

				holder = new ViewHolder();
				holder.text = (TextView) convertView
						.findViewById(R.id.txtJambi);
				holder.txtTerjemahan = (TextView) convertView
						.findViewById(R.id.txtIndonesia);
				holder.txtTanggal = (TextView) convertView
						.findViewById(R.id.txtTanggal);
				convertView.setTag(holder);
			} else {
				convertView = mInflater.inflate(R.layout.list_item, parent,
						false);

				holder = new ViewHolder();
				holder.text = (TextView) convertView.findViewById(R.id.txtKata);
				convertView.setTag(holder);
			}
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (history) {
			holder.text.setText(filteredData.get(position).getJambi());
			holder.txtTerjemahan.setText(filteredData.get(position)
					.getIndonesia());
			holder.txtTanggal.setText(filteredData.get(position).getDiakses());
		} else {
			holder.text.setText(filteredData.get(position).toString());
		}

		return convertView;
	}

	static class ViewHolder {
		TextView text;
		TextView txtTanggal;
		TextView txtTerjemahan;
	}

	@Override
	public Filter getFilter() {
		return mFilter;
	}

	private class ItemFilter extends Filter {
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {

			String filterString = constraint.toString().toLowerCase(
					Locale.ENGLISH);

			FilterResults results = new FilterResults();

			final List<Kata> list = originalData;

			int count = list.size();
			final ArrayList<Kata> nlist = new ArrayList<Kata>(count);

			Kata filterableKata;

			for (int i = 0; i < count; i++) {
				filterableKata = list.get(i);
				if (filterableKata.toString().toLowerCase(Locale.ENGLISH)
						.contains(filterString)) {
					nlist.add(filterableKata);
				}
			}

			results.values = nlist;
			results.count = nlist.size();

			return results;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			filteredData = (ArrayList<Kata>) results.values;
			notifyDataSetChanged();
		}

	}
}
