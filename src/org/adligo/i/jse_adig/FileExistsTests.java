package org.adligo.i.jse_adig;

import java.io.File;

import org.adligo.i.adig.client.GRegistry;
import org.adligo.i.adig.client.I_GCheckedInvoker;
import org.adligo.i.adig.client.I_GInvoker;
import org.adligo.tests.ATest;

public class FileExistsTests extends ATest {
	private I_GInvoker<String, Boolean> invoker = 
		GRegistry.getInvoker(JseInvokerNames.FILE_EXISTS_INVOKER, 
		String.class, Boolean.class);
	
	public void setUp() {
		DirectorySetup.setUp();
		JseRegistry.setup();
	}
	
	public void tearDown() {
		DirectorySetup.tearDown();
	}
	
	public void testFileDoesNOTExist() {
		assertFalse(invoker.invoke(null));
		assertFalse(invoker.invoke(" "));
		assertFalse(invoker.invoke("nowhere.txt"));
	}
	
	public void testFileDoesExist() throws Exception {
		String testDir = DirectorySetup.getTestDirPath();
		File file = new File(testDir + File.separator + "out.txt");
		file.createNewFile();
		assertTrue(file.exists());
		assertTrue(file.isFile());
		
		assertTrue(invoker.invoke(file.getAbsolutePath()));
	}
}
