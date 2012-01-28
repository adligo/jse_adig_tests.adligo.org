package org.adligo.i.jse_adig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.adligo.i.adig.client.GRegistry;
import org.adligo.i.adig.client.I_GCheckedInvoker;
import org.adligo.i.adig.client.I_GInvoker;
import org.adligo.tests.ATest;

public class CloseFileAppenderInvokerTests extends ATest {
	private I_GCheckedInvoker<FileAppenderParams, FileAppender> fileAppenderFactoryInvoker = 
		GRegistry.getCheckedInvoker(JseInvokerNames.FILE_APPENDER_FACTORY_INVOKER, 
				FileAppenderParams.class, FileAppender.class);
	private I_GCheckedInvoker<FileAppenderToken, Boolean> fileAppendInvoker = 
		GRegistry.getCheckedInvoker(JseInvokerNames.FILE_APPENDER_INVOKER, 
				FileAppenderToken.class, Boolean.class);
	private I_GInvoker<FileAppender, Boolean> closeFileAppendInvoker = 
		GRegistry.getInvoker(JseInvokerNames.CLOSE_FILE_APPENDER_INVOKER, 
				FileAppender.class, Boolean.class);
	
	public void setUp() {
		DirectorySetup.setUp();
		JseRegistry.setup();
	}
	
	public void tearDown() {
		DirectorySetup.tearDown();
	}
	
	public void testAppendingToFile() throws Exception {
		String dir = DirectorySetup.getTestDirPath();
		File file = new File(dir + File.separator + "closeFileAppenderInvokerTests.TestAppendingToFile.txt");
		FileAppenderParams appenderParams = new FileAppenderParams();
		String fileName = file.getAbsolutePath();
		appenderParams.setFileName(fileName);
		FileAppender appender = fileAppenderFactoryInvoker.invoke(appenderParams);
		FileAppenderToken token = new FileAppenderToken(appender,"line a", true);
		assertTrue(fileAppendInvoker.invoke(token));

		
		FileReader reader = new FileReader(file);
		BufferedReader br = new BufferedReader(reader);
		assertEquals("line a", br.readLine());
		assertNull(br.readLine());
		br.close();
		reader.close();
		
		assertTrue(appender.isOpen());
		assertTrue(closeFileAppendInvoker.invoke(appender));
		assertFalse(appender.isOpen());
	}
}
