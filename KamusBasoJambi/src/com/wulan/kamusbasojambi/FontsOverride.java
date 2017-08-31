package com.wulan.kamusbasojambi;

import java.lang.reflect.Field;
import android.content.Context;
import android.graphics.Typeface;

//mengubah font android dengan font yang diinginkan
//dari asset
public final class FontsOverride {

	public static void setDefaultFont(Context context,
			String staticTypefaceFieldName, String fontAssetName) {
		final Typeface regular = Typeface.createFromAsset(context.getAssets(),
				fontAssetName);
		replaceFont(staticTypefaceFieldName, regular);
	}

	protected static void replaceFont(String staticTypefaceFieldName,
			final Typeface newTypeface) {
		try {
			final Field staticField = Typeface.class
					.getDeclaredField(staticTypefaceFieldName);
			staticField.setAccessible(true);
			staticField.set(null, newTypeface);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}