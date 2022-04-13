package com.company.tutorial3.application.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;

public class FileUtils {

	private FileUtils() {
	}

	public static String createFile(String filePath) {
		try {
			File file = new File(filePath);
			file.createNewFile();
			return file.getAbsolutePath();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean checkIsFileAndExists(String filePath) {
		File file = new File(filePath);
		return file.exists() && file.isFile();
	}

	public static InputStream getInputStream(String filePath) {
		InputStream io = FileUtils.class.getResourceAsStream(filePath);
		if (io == null) {
			throw new RuntimeException("Resource not found: " + filePath);
		} else {
			return new BufferedInputStream(io);
		}
	}

}

