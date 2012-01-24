package org.adligo.i.jse_adig;

import java.io.File;

import org.adligo.i.adi.client.InvocationException;
import org.adligo.i.adig.client.GRegistry;
import org.adligo.i.adig.client.I_GCheckedInvoker;
import org.adligo.i.log.client.Log;
import org.adligo.i.log.client.LogFactory;
import org.adligo.tests.ATest;

public class CreateDirectoryTests extends ATest {
	private static final Log log = LogFactory.getLog(CreateDirectoryTests.class);
	
	public void setUp() {
		DirectorySetup.setUp();
	}
	
	public void tearDown() {
		DirectorySetup.tearDown();
	}
	
	public void testCreateDirectory() throws Exception {
		String newDir = DirectorySetup.getTestDirPath() + File.separator + "testCreateDirectory";
		File file = new File(DirectorySetup.getTestDirPath());
		I_GCheckedInvoker<String, Boolean> invoker = GRegistry.getCheckedInvoker(JseInvokerNames.CREATE_DIRECTORY_INVOKER, 
				String.class, Boolean.class);
		JseRegistry.setup();
		
		assertNotNull(invoker);
		assertTrue(invoker.invoke(newDir));
		File [] children = file.listFiles();
		assertEquals(1, children.length);
		assertEquals("testCreateDirectory", children[0].getName());
	}
	
	public void testCreateDirectoryFailureEmpty() {
		
		I_GCheckedInvoker<String, Boolean> invoker = GRegistry.getCheckedInvoker(JseInvokerNames.CREATE_DIRECTORY_INVOKER, 
				String.class, Boolean.class);
		JseRegistry.setup();
		InvocationException caught = null;
		try {
			invoker.invoke(null);
		} catch (InvocationException x) {
			caught = x;
		}
		assertNotNull(caught);
		assertEquals(CreateDirectoryInvoker.class.getName() + 
				CreateDirectoryInvoker.REQUIRES_A_NON_NULL_DIRECTORY_STRING,
				caught.getMessage());
		
		caught = null;
		try {
			invoker.invoke(" ");
		} catch (InvocationException x) {
			caught = x;
		}
		assertNotNull(caught);
		assertEquals(CreateDirectoryInvoker.class.getName() + 
				CreateDirectoryInvoker.REQUIRES_A_NON_NULL_DIRECTORY_STRING,
				caught.getMessage());
		
	}
	
	public void testCreateDirectoryTwice() throws Exception {
		String newDir = DirectorySetup.getTestDirPath() + File.separator + "testCreateDirectory";
		File file = new File(DirectorySetup.getTestDirPath());
		I_GCheckedInvoker<String, Boolean> invoker = GRegistry.getCheckedInvoker(JseInvokerNames.CREATE_DIRECTORY_INVOKER, 
				String.class, Boolean.class);
		JseRegistry.setup();
		
		assertNotNull(invoker);
		assertTrue(invoker.invoke(newDir));
		File [] children = file.listFiles();
		assertEquals(1, children.length);
		assertEquals("testCreateDirectory", children[0].getName());
		
		InvocationException caught = null;
		try {
			invoker.invoke(newDir);
		} catch (InvocationException x) {
			caught = x;
		}
		assertNotNull(caught);
		assertEquals(CreateDirectoryInvoker.class.getName() + 
				CreateDirectoryInvoker.WAS_NOT_ABLE_TO_CREATE_THE_DIRECTORY_S + newDir,
				caught.getMessage());
	}
}
