package org.adligo.i.jse_adig_tests;

import java.io.FileWriter;

import org.adligo.i.jse_adig.FileAppender;

public class DelegateFileAppender extends FileAppender {

	DelegateFileAppender(FileWriter p, String p_lineFeed, String p_fileName) {
		super(p, p_lineFeed, p_fileName);
	}

}
