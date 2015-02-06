package cgt.util;

import java.io.File;
import java.io.Serializable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class CGTFile implements Serializable {

    private String filename;
	private File file;
	private FileHandle fileHandle;
	
	public CGTFile(String filepath) {
		file = new File(filepath);
		fileHandle = null;
	}

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
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
