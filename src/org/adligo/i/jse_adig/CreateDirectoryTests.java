package org.adligo.i.jse_adig;

import java.io.File;

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
}
