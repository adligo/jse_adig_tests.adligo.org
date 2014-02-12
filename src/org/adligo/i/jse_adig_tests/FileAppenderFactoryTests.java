package org.adligo.i.jse_adig_tests;

import java.io.File;

import org.adligo.i.adi.shared.InvocationException;
import org.adligo.i.adig.shared.GRegistry;
import org.adligo.i.adig.shared.I_GCheckedInvoker;
import org.adligo.i.jse_adig.FileAppender;
import org.adligo.i.jse_adig.FileAppenderFactoryInvoker;
import org.adligo.i.jse_adig.FileAppenderParams;
import org.adligo.i.jse_adig.JseInvokerNames;
import org.adligo.i.jse_adig.JseRegistry;
import org.adligo.tests.ATest;

public class FileAppenderFactoryTests extends ATest {
	private I_GCheckedInvoker<FileAppenderParams, FileAppender> invoker = 
		GRegistry.getCheckedInvoker(JseInvokerNames.FILE_APPENDER_FACTORY_INVOKER, 
				FileAppenderParams.class, FileAppender.class);
	
	public void setUp() {
		DirectorySetup.setUp();
		JseRegistry.setup();
	}
	
	public void tearDown() {
		DirectorySetup.tearDown();
	}
	
	public void testGetFileAppenderFailed() {
		InvocationException caught = null;
		try {
			invoker.invoke(null);
		} catch (InvocationException x) {
			caught = x;
		}
		assertNotNull(caught);
		assertEquals(FileAppenderFactoryInvoker.class.getName() + 
				FileAppenderFactoryInvoker.REQUIRES_A_NON_NULL_FILE_APPENDER_PARAMS_ARGUMENT,
				caught.getMessage());
		
		FileAppenderParams params = new FileAppenderParams();
		caught = null;
		try {
			invoker.invoke(params);
		} catch (InvocationException x) {
			caught = x;
		}
		assertNotNull(caught);
		assertEquals(FileAppenderFactoryInvoker.class.getName() + 
				FileAppenderFactoryInvoker.COULD_NOT_CREATE_NEW_FILE + "null",
				caught.getMessage());
	}
	
	public void testGetFileAppenderFailedFileAlradyExists() throws Exception {
		String testDir = DirectorySetup.getTestDirPath();
		File nowhere = new File(testDir + File.separator + "nowhere.txt");
		nowhere.createNewFile();
		assertTrue(nowhere.exists());
		
		InvocationException caught = null;
		FileAppenderParams params = new FileAppenderParams();
		params.setFileName(nowhere.getAbsolutePath());
		caught = null;
		try {
			invoker.invoke(params);
		} catch (InvocationException x) {
			caught = x;
		}
		assertNotNull(caught);
		assertEquals(FileAppenderFactoryInvoker.class.getName() + 
				FileAppenderFactoryInvoker.COULD_NOT_CREATE_NEW_FILE + nowhere.getAbsolutePath(),
				caught.getMessage());
	}
	
	public void testGetFileAppender() throws Exception {
		String testDir = DirectorySetup.getTestDirPath();
		File nowhere = new File(testDir + File.separator + "nowhere.txt");
		
		FileAppenderParams params = new FileAppenderParams();
		params.setFileName(nowhere.getAbsolutePath());
		FileAppender appender = invoker.invoke(params);
		assertNotNull(appender);
		assertTrue(appender.isOpen());
		assertEquals(FileAppenderParams.UNIX_LINE_FEED, appender.getLineFeed());
	}
}
