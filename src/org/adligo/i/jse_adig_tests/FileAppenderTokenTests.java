package org.adligo.i.jse_adig_tests;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.adligo.i.jse_adig.FileAppender;
import org.adligo.i.jse_adig.FileAppenderToken;
import org.adligo.i.jse_adig.JseRegistry;
import org.adligo.tests.ATest;

public class FileAppenderTokenTests extends ATest {
	
	public void setUp() {
		DirectorySetup.setUp();
		JseRegistry.setup();
	}
	
	public void tearDown() {
		DirectorySetup.tearDown();
	}
	
	public void testNullAppenderSingleLine() {
		Exception caught = null;
		try {
			new FileAppenderToken(null, "hey",true);
		} catch (Exception x) {
			caught = x;
		}
		assertNotNull(caught);
		assertEquals(FileAppenderToken.FILE_APPENDER_TOKEN_DOES_NOT_ACCEPT_A_NULL_FILE_APPENDER,
				caught.getMessage());
	}
	
	public void testNullAppenderMultiLine() {
		Exception caught = null;
		try {
			new FileAppenderToken(null, new ArrayList<String>());
		} catch (Exception x) {
			caught = x;
		}
		assertNotNull(caught);
		assertEquals(FileAppenderToken.FILE_APPENDER_TOKEN_DOES_NOT_ACCEPT_A_NULL_FILE_APPENDER,
				caught.getMessage());
	}
	
	public void testNullMultiLine() throws IOException {
		Exception caught = null;
		FileAppender appender = getFileAppender();
		try {
			
			new FileAppenderToken(appender, null);
		} catch (Exception x) {
			caught = x;
		}
		assertNotNull(caught);
		assertEquals(FileAppenderToken.FILE_APPENDER_TOKEN_DOES_NOT_ACCEPT_A_NULL_LINES_COLLECTION,
				caught.getMessage());
	}
	
	public void testNullSingleLine() throws IOException {
		Exception caught = null;
		FileAppender appender = getFileAppender();
		try {
			
			new FileAppenderToken(appender, null, true);
		} catch (Exception x) {
			caught = x;
		}
		assertNotNull(caught);
		assertEquals(FileAppenderToken.FILE_APPENDER_TOKEN_DOES_NOT_ACCEPT_NULL_DATA,
				caught.getMessage());
	}
	private FileAppender getFileAppender() throws IOException {
		String testDir = DirectorySetup.getTestDirPath();
		File file = new File(testDir + File.separator + "foo.txt");
		file.createNewFile();
		assertTrue(file.exists());
		FileWriter writer = new FileWriter(file);
		
		return new MockFileAppender(writer, "\n", file.getAbsolutePath());
	}
}
