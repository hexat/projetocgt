package util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipHelper {
	private static final int BUFFER_SIZE = 1024;

	public void zipDir(String dirName, String nameZipFile)
			throws IOException {
		ZipOutputStream zip = null;
		FileOutputStream fW = null;
		fW = new FileOutputStream(nameZipFile);
		zip = new ZipOutputStream(fW);
		addFolderToZip("", dirName, zip);
		zip.close();
		fW.close();
	}

	private void addFolderToZip(String path, String srcFolder,
			ZipOutputStream zip) throws IOException {
		File folder = new File(srcFolder);
		if (folder.list().length == 0) {
			addFileToZip(path, srcFolder, zip, true);
		} else {
			for (String fileName : folder.list()) {
				if (path.equals("")) {
					addFileToZip(folder.getName(), srcFolder + "/"
							+ fileName, zip, false);
				} else {
					addFileToZip(path + "/" + folder.getName(), srcFolder
							+ "/" + fileName, zip, false);
				}
			}
		}
	}

	private void addFileToZip(String path, String srcFile,
			ZipOutputStream zip, boolean flag) throws IOException {
		File folder = new File(srcFile);
		if (flag) {
			zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()
					+ "/"));
		} else {
			if (folder.isDirectory()) {
				addFolderToZip(path, srcFile, zip);
			} else {
				byte[] buf = new byte[BUFFER_SIZE];
				int len;
				FileInputStream in = new FileInputStream(srcFile);
				zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
				while ((len = in.read(buf)) > 0) {
					zip.write(buf, 0, len);
				}
			}
		}
	}

	private void extractFile(ZipInputStream in, File outdir, String name)
			throws IOException {
		byte[] buffer = new byte[BUFFER_SIZE];
		BufferedOutputStream out = new BufferedOutputStream(
				new FileOutputStream(new File(outdir, name)));
		int count = -1;
		while ((count = in.read(buffer)) != -1)
			out.write(buffer, 0, count);
		out.close();
	}

	private void mkdirs(File outdir, String path) {
		File d = new File(outdir, path);
		if (!d.exists())
			d.mkdirs();
	}

	private String dirpart(String name) {
		int s = name.lastIndexOf(File.separatorChar);
		return s == -1 ? null : name.substring(0, s);
	}

	/***
	 * Extract zipfile to outdir with complete directory structure
	 * 
	 * @param zipfile
	 *            Input .zip file
	 * @param outdir
	 *            Output directory
	 */
	public void extract(File zipfile, File outdir) {
		try {
			ZipInputStream zin = new ZipInputStream(new FileInputStream(
					zipfile));
			ZipEntry entry;
			String name, dir;
			while ((entry = zin.getNextEntry()) != null) {
				name = entry.getName();
				if (entry.isDirectory()) {
					mkdirs(outdir, name);
					continue;
				}
				/*
				 * this part is necessary because file entry can come before
				 * directory entry where is file located i.e.: /foo/foo.txt
				 * /foo/
				 */
				dir = dirpart(name);
				if (dir != null)
					mkdirs(outdir, dir);

				extractFile(zin, outdir, name);
			}
			zin.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}