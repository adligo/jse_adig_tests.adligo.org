package org.adligo.i.jse_adig;

import java.io.File;

import org.adligo.i.adig.client.GRegistry;
import org.adligo.i.adig.client.I_GInvoker;
import org.adligo.tests.ATest;

public class DirectoryExistsTests extends ATest {
	private I_GInvoker<String, Boolean> invoker = 
		GRegistry.getInvoker(JseInvokerNames.DIRECTORY_EXISTS_INVOKER, 
		String.class, Boolean.class);
	
	public void setUp() {
		DirectorySetup.setUp();
		JseRegistry.setup();
	}
	
	public void tearDown() {
		DirectorySetup.tearDown();
	}
	
	public void testDirectoryDoesNOTExist() {
		assertFalse(invoker.invoke(null));
		assertFalse(invoker.invoke(" "));
		assertFalse(invoker.invoke("nowhere"));
	}
	
	public void testDirectoryDoesExist() {
		String testDir = DirectorySetup.getTestDirPath();
		File dir = new File(testDir + File.separator + "childDir");
		dir.mkdirs();
		assertTrue(dir.exists());
		assertTrue(dir.isDirectory());
		
		assertTrue(invoker.invoke(dir.getAbsolutePath()));
	}
}
