package com.jvgl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.graphics.Color;

public class ResourceReader
{
	public static String readFromResource(final Context context,
			final int resource)
	{
		final InputStream inputStream = context.getResources().openRawResource(resource);
		final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

		String tempText;
		final StringBuilder text = new StringBuilder();

		try
		{
			while ((tempText = bufferedReader.readLine()) != null)
			{
				text.append(tempText);
				text.append('\n');
			}
		}
		catch (IOException e)
		{
			return null;
		}

		return text.toString();
	}

        public static void getColourFromResource(Context _context, int _name, Vec3 _writeTo){
        int colours = _context.getResources().getColor(_name);
        int r = Color.red(colours);
        int g = Color.green(colours);
        int b = Color.blue(colours);
        _writeTo.set(r, g, b);
    }
}
