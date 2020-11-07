package bg.uni.sofia.fmi.mjt.replicator;

import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

public class DirectoryReplicatorTest {
	private static DirectoryReplicator replicator;
	
	private static void deleteFiles(File file) {
		File[] contents = file.listFiles();
		if (contents != null) {
			for (File f : contents) {
				deleteFiles(f);
			}
		}

		file.delete();
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		replicator = new DirectoryReplicator();

		deleteFiles(new File("sourceDir"));
		deleteFiles(new File("targetDir"));
		
		Files.createDirectories(Paths.get("sourceDir/1"));
		Files.createDirectories(Paths.get("sourceDir/2"));
		Files.createDirectories(Paths.get("sourceDir/3"));
		
		Files.createDirectories(Paths.get("targetDir/3"));
		Files.createDirectories(Paths.get("targetDir/4"));
	}
	
	@Test
	public void test() {
		replicator.replicate(Paths.get("sourceDir"), Paths.get("targetDir"));
		assertEquals(true, Files.exists(Paths.get("targetDir/1")));
		assertEquals(true, Files.exists(Paths.get("targetDir/2")));
		assertEquals(true, Files.exists(Paths.get("targetDir/3")));
		
		assertEquals(false, Files.exists(Paths.get("targetDir/4")));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNonExistingSource() {
		replicator.replicate(Paths.get("sourceDirFake"), Paths.get("targetDir"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNonExistingTarget() {
		replicator.replicate(Paths.get("sourceDir"), Paths.get("targetDirFake"));
	}

}
