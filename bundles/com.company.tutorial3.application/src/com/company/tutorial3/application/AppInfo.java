package com.company.tutorial3.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.FrameworkUtil;

import com.company.tutorial3.application.utils.IconsMapping;

@Singleton
@Creatable
public class AppInfo {
	
	public String getVersionAsString() {
		return getStringFromResourceFile("resources/productVersion.txt");
	}
	
	public String getProductID() {
		return "tutorial3";
	}
	
	public String getFullProductName() {
		return "tutorial3";
	}

	public String getAppVendorSiteUrl() {
		return "www.amalgamasimulation.com";
	}

	public String getAppVendorEmail() {
		return "info@amalgamasimulation.com";
	}

	public String getAppVendorName() {
		return "Amalgama LLC";
	}

	public Image getApplicationIcon() {
		return IconsMapping.getImage("/icons/logo_64.png");
	}
	
	public String getNameAndVersion() {
		return getProductID() + ", v" + getVersionAsString();
	}
	
	public LocalDate getReleaseDate() {
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

