package com.company.tutorial3.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.osgi.framework.FrameworkUtil;

public class AppInfo {
	
	private AppInfo() {}

	public static String getVersionAsString() {
		return getStringFromResourceFile("resources/productVersion.txt");
	}
	
	public static String getProductID() {
		return "tutorial3";
	}
	
	public static String getFullProductName() {
		return "tutorial3";
	}
	
	public static String getNameAndVersion() {
		return AppInfo.getProductID() + ", v" + AppInfo.getVersionAsString();
	}
	
	public static LocalDate getReleaseDate() {
		String dateFromFile = getStringFromResourceFile("resources/releaseDate.txt");
		return dateFromFile == null ? LocalDate.of(2100, 1, 1) : LocalDate.parse(dateFromFile, DateTimeFormatter.ISO_LOCAL_DATE);
	}
	
	private static String getStringFromResourceFile(String path) {
		URL url = FrameworkUtil.getBundle(AppInfo.class).getEntry(path);
		try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
			return in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}

