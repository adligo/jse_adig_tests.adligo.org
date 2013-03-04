package org.adligo.i.jse_adig;

import java.io.File;
import java.io.FileWriter;

import org.adligo.tests.ATest;

public class FileAppenderTests extends ATest {
	public void setUp() {
		DirectorySetup.setUp();
		JseRegistry.setup();
	}
	
	public void tearDown() {
		DirectorySetup.tearDown();
	}
	
	public void testConstructorNullWriter() {
		Exception caught = null;
		try {
			new FileAppender(null, null, null);
		} catch (Exception x) {
			caught = x;
		}
		assertNotNull(caught);
		assertTrue(caught instanceof NullPointerException);
		assertEquals(FileAppender.FILE_APPENDER_DOES_NOT_ACCEPT_A_NULL_WRITER, 
				caught.getMessage());
	}

	public void testConstructorNullLineFeed() throws Exception {
		
		String testDir = DirectorySetup.getTestDirPath();
		File file = new File(testDir + File.separator + "foo.txt");
		file.createNewFile();
		assertTrue(file.exists());
		FileWriter writer = new FileWriter(file);
		
		Exception caught = null;
		try {
			new FileAppender(writer, null, null);
		} catch (Exception x) {
			caught = x;
		}
		assertNotNull(caught);
		assertTrue(caught instanceof NullPointerException);
		assertEquals(FileAppender.FILE_APPENDER_DOES_NOT_ACCEPT_A_NULL_LINE_FEED, 
				caught.getMessage());
	}

	public void testConstructorNullFileName() throws Exception {
		
		String testDir = DirectorySetup.getTestDirPath();
		File file = new File(testDir + File.separator + "foo.txt");
		file.createNewFile();
		assertTrue(file.exists());
		FileWriter writer = new FileWriter(file);
		
		Exception caught = null;
		try {
			new FileAppender(writer, FileAppenderParams.UNIX_LINE_FEED, null);
		} catch (Exception x) {
			caught = x;
		}
		assertNotNull(caught);
		assertTrue(caught instanceof NullPointerException);
		assertEquals(FileAppender.FILE_APPENDER_DOES_NOT_ACCEPT_A_EMPTY_FILE_NAME, 
				caught.getMessage());
	}
	
	public void testConstructorEmptyFileName() throws Exception {
		
		String testDir = DirectorySetup.getTestDirPath();
		File file = new File(testDir + File.separator + "foo.txt");
		file.createNewFile();
		assertTrue(file.exists());
		FileWriter writer = new FileWriter(file);
		
		Exception caught = null;
		try {
			new FileAppender(writer, FileAppenderParams.UNIX_LINE_FEED, " ");
		} catch (Exception x) {
			caught = x;
		}
		assertNotNull(caught);
		assertTrue(caught instanceof NullPointerException);
		assertEquals(FileAppender.FILE_APPENDER_DOES_NOT_ACCEPT_A_EMPTY_FILE_NAME, 
				caught.getMessage());
	}

	public void testConstructor() throws Exception {
		
		String testDir = DirectorySetup.getTestDirPath();
		File file = new File(testDir + File.separator + "foo.txt");
		file.createNewFile();
		assertTrue(file.exists());
		FileWriter writer = new FileWriter(file);
		
		String fileName = file.getAbsolutePath();
		new FileAppender(writer, "\n", fileName);
		
	}
}