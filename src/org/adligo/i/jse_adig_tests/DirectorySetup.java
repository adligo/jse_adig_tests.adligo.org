package org.adligo.i.jse_adig_tests;

import java.io.File;

import org.adligo.i.log.shared.Log;
import org.adligo.i.log.shared.LogFactory;

public class DirectorySetup {
	private static final Log log = LogFactory.getLog(DirectorySetup.class);
	
	public static void setUp() {
		String test_dir = getTestDirPath();
		File testDir = new File(test_dir);
		if (testDir.exists()) {
			tearDown();
		}
		boolean made = testDir.mkdir();
		if (!made) {
			throw new RuntimeException("unable to create parent test directory " + test_dir);
		}
	}
	
	public static void tearDown() {
		String test_dir = getTestDirPath();
		File testDir = new File(test_dir);
		
		recursiveDelete(testDir);
		boolean deleted = testDir.delete();
		if (!deleted) {
			throw new RuntimeException("unable to remove parent test directory " + test_dir);
		}
	}

	public static String getTestDirPath() {
		File file = new File(".");
		String path = file.getAbsolutePath();
		log.debug("running in " + path);
		//remove dot
		path = path.substring(0, path.length() - 1);
		// on mac os this already had a file seperator so I assume it will on other os's
		String test_dir = path + "test_dir";
		return test_dir;
	}

	private static void recursiveDelete(File testDir) {
		File [] children = testDir.listFiles();
		for (int i = 0; i < children.length; i++) {
			File loopFile = children[i];
			if (loopFile.isFile()) {
				loopFile.delete();
			} else if (loopFile.isDirectory()) {
				recursiveDelete(loopFile);
				loopFile.delete();
			}
		}
	}
}
