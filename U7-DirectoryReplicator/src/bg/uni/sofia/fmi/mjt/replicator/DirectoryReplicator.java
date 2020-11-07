package bg.uni.sofia.fmi.mjt.replicator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class DirectoryReplicator {
	private Set<String> sourceDirFiles;
	private Set<String> targetDirFiles;

	private Path sourceBaseDir;
	private Path targetBaseDir;

	public DirectoryReplicator() {
		sourceDirFiles = new HashSet<String>();
		targetDirFiles = new HashSet<String>();
	}

	private void saveSourceDirFiles(File[] files) {
		for (File file : files) {
			sourceDirFiles.add(sourceBaseDir.relativize(file.toPath()).toString());

			if (file.isDirectory()) {
				saveSourceDirFiles(file.listFiles());
			}
		}
	}
	
	private void saveTargetDirFiles(File[] files) {
		for (File file : files) {
			targetDirFiles.add(targetBaseDir.relativize(file.toPath()).toString());

			if (file.isDirectory()) {
				saveTargetDirFiles(file.listFiles());
			}
		}
	}

	private void deleteTarget(File file) {
		File[] contents = file.listFiles();
		if (contents != null) {
			for (File f : contents) {
				deleteTarget(f);
			}
		}

		file.delete();
	}

	private void deleteTargetDirFiles() {
		for (String s : targetDirFiles) {
			if (!sourceDirFiles.contains(s)) {
				deleteTarget(new File(targetBaseDir + File.separator + s));
			}
		}
	}

	private void copyDirFiles(Path sourceDir) throws IOException {
		for (File file : new File(sourceDir.toString()).listFiles()) {

			String relativePath = sourceBaseDir.relativize(file.toPath()).toString();

			if (!targetDirFiles.contains(relativePath)) {
				Files.copy(file.toPath(), Paths.get(targetBaseDir + File.separator + relativePath));
			}

			if (file.isDirectory()) {
				copyDirFiles(file.toPath());
			}
		}
	}

	public void replicate(Path sourceDir, Path targetDir) {
		if (!Files.exists(sourceDir) || !Files.exists(targetDir) || !Files.isDirectory(sourceDir)
				|| !Files.isDirectory(targetDir)) {
			throw new IllegalArgumentException();
		}

		sourceBaseDir = sourceDir;
		targetBaseDir = targetDir;
		
		saveSourceDirFiles(sourceDir.toFile().listFiles());
		saveTargetDirFiles(targetDir.toFile().listFiles());

		deleteTargetDirFiles();

		try {
			copyDirFiles(sourceDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
