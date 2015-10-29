package cgt.util;

import java.io.File;
import java.io.Serializable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class CGTFile implements Serializable {

    private String filename;
	private String fileRelativePath;
	private FileHandle fileHandle;

	public CGTFile() {
		fileRelativePath = null;
		filename = null;
		fileHandle = null;
	}

	public CGTFile(String fileRelativePath) {
		this();
		this.fileRelativePath = fileRelativePath;
	}

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public File getFile() {
		return new File(fileRelativePath);
	}

	public String getRelativePath() {
		return fileRelativePath;
	}

	public FileHandle getFileHandle() {
		if (fileHandle == null) {
			fileHandle = Gdx.files.internal(fileRelativePath);
		}
		return fileHandle;
	}
}
