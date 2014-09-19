package cgt.util;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class CGTFile {

	private File file;
	private FileHandle fileHandle;
	
	public CGTFile(String filename) {
		file = new File(filename);
		fileHandle = null;
	}
	
	public File getFile() {
		return file;
	}
	
	public FileHandle getFileHandle() {
		if (fileHandle == null) {
			fileHandle = Gdx.files.internal(file.getPath());
		}
		return fileHandle;
	}
}