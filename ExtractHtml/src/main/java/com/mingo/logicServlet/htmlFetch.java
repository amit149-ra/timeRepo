package com.mingo.logicServlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class htmlFetch {

	public static Map<String, String> getListTagAsString(String url) throws IOException {
		URL u = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) u.openConnection();

		// Set request method
		con.setRequestMethod("GET");

		// Read the response

		try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
			String All_line;
			Map<String,String> lineMap = new HashMap<String, String>();

			while ((All_line = in.readLine()) != null) {
				if (All_line.contains("latest-stories__item")) {

					StringBuilder response = new StringBuilder();
					String li_line,key = null,value = null;
					while ((li_line = in.readLine()) != null) {
						if (li_line.contains("</li>")) {
							break;
						}
						if(li_line.contains("href")) {
							key = getKey("<a href=\"", li_line, ">");
						}
						if(li_line.contains("latest-stories__item-headline")) {
							value = getKey("<h3 class=\"latest-stories__item-headline\">", li_line, "</h3>");
						}
						lineMap.put(key, value);
					}
					
				}
			}
			return lineMap;
		}
	}
	public static String getKey(String StartString,String mainString,String endString) {
		
		if(StartString==null||mainString==null||endString==null) {
			System.out.println("Wrong input");
			return null;
		}
		
		int start = mainString.indexOf(StartString);
	      if (start != -1) {
	          int end = mainString.indexOf(endString, start + StartString.length());
	          if (end != -1) {
	              return mainString.substring(start + StartString.length(), end);
	          }
	      }
		
	      System.out.println("something is wrong");
		return null;
		
	}
}


