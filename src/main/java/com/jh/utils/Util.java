package com.jh.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.util.Precision;

public class Util {
	public static String formatProbability(double number) {
		return StringUtils.leftPad(String.valueOf(Precision.round(number, 6)), 10);
	}

	public static String getContentFile(String filePath, String encoding) throws IOException {
		File file = new File(filePath);
		return FileUtils.readFileToString(file, encoding);
	}

	public static void generateFile(String content, String filePath, String encoding) throws IOException {
		File file = new File(filePath);
		FileUtils.write(file, content, encoding);
		FileUtils.touch(file);
	}
}
