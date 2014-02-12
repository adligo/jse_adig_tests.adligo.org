package org.adligo.i.jse_adig_tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.adligo.i.adig.shared.GRegistry;
import org.adligo.i.adig.shared.I_GCheckedInvoker;
import org.adligo.i.jse_adig.FileAppender;
import org.adligo.i.jse_adig.FileAppenderParams;
import org.adligo.i.jse_adig.FileAppenderToken;
import org.adligo.i.jse_adig.JseInvokerNames;
import org.adligo.i.jse_adig.JseRegistry;
import org.adligo.tests.ATest;

public class FileAppenderInvokerTests extends ATest {
	private I_GCheckedInvoker<FileAppenderParams, FileAppender> fileAppenderFactoryInvoker = 
		GRegistry.getCheckedInvoker(JseInvokerNames.FILE_APPENDER_FACTORY_INVOKER, 
				FileAppenderParams.class, FileAppender.class);
	private I_GCheckedInvoker<FileAppenderToken, Boolean> fileAppendInvoker = 
		GRegistry.getCheckedInvoker(JseInvokerNames.FILE_APPENDER_INVOKER, 
				FileAppenderToken.class, Boolean.class);
	
	
	public void setUp() {
		DirectorySetup.setUp();
		JseRegistry.setup();
	}
	
	public void tearDown() {
		DirectorySetup.tearDown();
	}
	
	public void testAppendingToFile() throws Exception {
		String dir = DirectorySetup.getTestDirPath();
		File file = new File(dir + File.separator + "testAppendingToFile.txt");
		FileAppenderParams appenderParams = new FileAppenderParams();
		String fileName = file.getAbsolutePath();
		appenderParams.setFileName(fileName);
		FileAppender appender = fileAppenderFactoryInvoker.invoke(appenderParams);
		FileAppenderToken token = new FileAppenderToken(appender,"line a", true);
		assertTrue(fileAppendInvoker.invoke(token));
		
		List<String> lines = new ArrayList<String>();
		lines.add("line b");
		lines.add("line c");
		token = new FileAppenderToken(appender,lines);
		assertTrue(fileAppendInvoker.invoke(token));
		
		FileReader reader = new FileReader(file);
		BufferedReader br = new BufferedReader(reader);
		assertEquals("line a", br.readLine());
		assertEquals("line b", br.readLine());
		assertEquals("line c", br.readLine());
		assertNull(br.readLine());
		
		br.close();
		reader.close();
		
	}

	
}
