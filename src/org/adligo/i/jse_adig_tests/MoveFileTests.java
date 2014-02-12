package org.adligo.i.jse_adig_tests;

import java.io.File;
import java.io.IOException;

import org.adligo.i.adi.shared.InvocationException;
import org.adligo.i.adig.shared.GRegistry;
import org.adligo.i.adig.shared.I_GCheckedInvoker;
import org.adligo.i.jse_adig.JseInvokerNames;
import org.adligo.i.jse_adig.JseRegistry;
import org.adligo.i.jse_adig.MoveFileInvoker;
import org.adligo.i.jse_adig.MoveFileToken;
import org.adligo.tests.ATest;

public class MoveFileTests extends ATest {
	private I_GCheckedInvoker<MoveFileToken, Boolean> invoker = 
		GRegistry.getCheckedInvoker(JseInvokerNames.MOVE_FILE_INVOKER, 
		MoveFileToken.class, Boolean.class);
	
	public void setUp() {
		DirectorySetup.setUp();
		JseRegistry.setup();
	}
	
	public void tearDown() {
		DirectorySetup.tearDown();
	}
	
	public void testEmptyToFile() {
		
		MoveFileToken token = new MoveFileToken();
		InvocationException caught = null;
		try {
			invoker.invoke(token);
		} catch (InvocationException x) {
			caught = x;
		}
		assertNotNull(caught);
		assertEquals(MoveFileInvoker.class.getName() + 
				MoveFileInvoker.REQUIRES_A_NON_EMPTY_TO_FILE_PATH_IN_THE_MOVE_FILE_TOKEN,
				caught.getMessage());
		
	}
	
	public void testEmptyFromFile() {
		
		MoveFileToken token = new MoveFileToken();
		token.setTo_file_path("hey.txt");
		InvocationException caught = null;
		try {
			invoker.invoke(token);
		} catch (InvocationException x) {
			caught = x;
		}
		assertNotNull(caught);
		assertEquals(MoveFileInvoker.class.getName() + 
				MoveFileInvoker.REQUIRES_A_NON_EMPTY_FROM_FILE_PATH_IN_THE_MOVE_FILE_TOKEN,
				caught.getMessage());
		
	}
	
	public void testFromDoesNOTExist() {
		
		MoveFileToken token = new MoveFileToken();
		token.setTo_file_path("hey.txt");
		token.setFrom_file_path("bar.txt");
		InvocationException caught = null;
		try {
			invoker.invoke(token);
		} catch (InvocationException x) {
			caught = x;
		}
		assertNotNull(caught);
		assertEquals(MoveFileInvoker.class.getName() + 
				MoveFileInvoker.REQUIRES_A_VALID_FILE_PATH_IN_THE_MOVE_FILE_TOKEN_BUT_THE_FILE_DOESN_T_EXIST,
				caught.getMessage());
	}
	
	public void testToParentDirectoryDoesNOTExist() throws IOException {
		String newDir = DirectorySetup.getTestDirPath() + File.separator + "testToParentDirectoryDoesNOTExist";
		File file = new File(newDir);
		assertTrue(file.mkdirs());
		
		String childA = newDir + File.separator + "a";
		
		String childB = newDir + File.separator + "b.txt";
		File fileB  = new File(childB);
		assertTrue(fileB.createNewFile());
		
		MoveFileToken token = new MoveFileToken();
		token.setTo_file_path(childA + File.separator + "a.txt");
		token.setFrom_file_path(childB);
		InvocationException caught = null;
		try {
			invoker.invoke(token);
		} catch (InvocationException x) {
			caught = x;
		}
		assertNotNull(caught);
		assertEquals(MoveFileInvoker.class.getName() + 
				MoveFileInvoker.REQUIRES_A_TO_FILE_PATH_WITH_A_EXISTING_PARENT_DIRECTORY,
				caught.getMessage());
	}
	
	public void testFailMoveFileBetweenDirs() throws IOException {
		String newDir = DirectorySetup.getTestDirPath() + File.separator + "testToParentDirectoryDoesNOTExist";
		File file = new File(newDir);
		assertTrue(file.mkdirs());
		
		String childA = newDir + File.separator + "a";
		File fileA  = new File(childA);
		assertTrue(fileA.mkdirs());
		
		String childAB = newDir + File.separator + "a" + File.separator + "b.txt";
		File fileAB  = new File(childAB);
		assertTrue(fileAB.createNewFile());
		
		String childB = newDir + File.separator + "b.txt";
		File fileB  = new File(childB);
		assertTrue(fileB.createNewFile());
		
		MoveFileToken token = new MoveFileToken();
		token.setTo_file_path(childAB);
		token.setFrom_file_path(childB);
		InvocationException caught = null;
		try {
			invoker.invoke(token);
		} catch (InvocationException x) {
			caught = x;
		}
		assertNotNull(caught);
		assertEquals(MoveFileInvoker.class.getName() + 
				MoveFileInvoker.CAN_NOT_CURRENTLLY_MOVE_A_FILE_BETWEEN_DIRECTORIES ,
				caught.getMessage());
	}
	
	public void testFailMoveFileOntoSelf() throws IOException {
		String newDir = DirectorySetup.getTestDirPath() + File.separator + "testToParentDirectoryDoesNOTExist";
		File file = new File(newDir);
		assertTrue(file.mkdirs());
		
		
		String childB = newDir + File.separator + "b.txt";
		File fileB  = new File(childB);
		assertTrue(fileB.createNewFile());
		
		MoveFileToken token = new MoveFileToken();
		token.setTo_file_path(childB);
		token.setFrom_file_path(childB);
		InvocationException caught = null;
		try {
			invoker.invoke(token);
		} catch (InvocationException x) {
			caught = x;
		}
		assertNotNull(caught);
		assertEquals(MoveFileInvoker.class.getName() + 
				MoveFileInvoker.CAN_NOT_MOVE_A_FILE_ONTO_ITSELF ,
				caught.getMessage());
	}
	
	public void testFailMoveFile() {
		//TODO no idea how to get it to return false
	}

	public void testMoveFileSuccess() throws Exception {
		String newDir = DirectorySetup.getTestDirPath() + File.separator + "testToParentDirectoryDoesNOTExist";
		File file = new File(newDir);
		assertTrue(file.mkdirs());
		
		String childA = newDir + File.separator + "a.txt";
		File fileA  = new File(childA);
		assertTrue(fileA.mkdirs());
		
		MoveFileToken token = new MoveFileToken();
		token.setTo_file_path(newDir + File.separator + "b.txt");
		token.setFrom_file_path(childA);
		invoker.invoke(token);
		
		File b = new File(newDir + File.separator + "b.txt");
		assertTrue(b.exists());
	}
}
