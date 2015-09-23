package br.edu.ifce.cgt.application;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class CGTFileZip {
	private static final int BUFFER_SIZE = 1024;
	private List<String> fileList;
	private static String OUTPUT_ZIP_FILE = "CGT.pcgt";
	private static String SOURCE_FOLDER = null;
	

	public CGTFileZip(String outputFile, String inputDir) {
		SOURCE_FOLDER = inputDir;
		
		String substr = outputFile.substring(outputFile.length() - 4);
		if(substr.equals(".cgt")){
			OUTPUT_ZIP_FILE = outputFile;
		}else{
			OUTPUT_ZIP_FILE = outputFile+".pcgt";
		}
		
		fileList = new ArrayList<String>();
	}

	public void zipar() throws IOException {
		this.generateFileList(new File(SOURCE_FOLDER));
		this.zipIt(OUTPUT_ZIP_FILE);
	}
	
	private void zipIt(String zipFile) throws IOException {
		byte[] buffer = new byte[BUFFER_SIZE];
		String source = "";
		FileOutputStream fos = null;
		ZipOutputStream zos = null;

        source = SOURCE_FOLDER.substring(
                SOURCE_FOLDER.lastIndexOf("\\") + 1,
                SOURCE_FOLDER.length());
        fos = new FileOutputStream(zipFile);
        zos = new ZipOutputStream(fos);

        FileInputStream in = null;

        for (String file : this.fileList) {
            ZipEntry ze = new ZipEntry(file);
            zos.putNextEntry(ze);
            in = new FileInputStream(SOURCE_FOLDER + File.separator + file);
            int len;
            while ((len = in.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
            in.close();
        }

        zos.closeEntry();
        zos.close();
	}

	public void generateFileList(File node) {
		// add file only
		if (node.isFile()) {
			fileList.add(generateZipEntry(node.toString()));

		}

		if (node.isDirectory()) {
			String[] subNote = node.list();
			for (String filename : subNote) {
				generateFileList(new File(node, filename));
			}
		}
	}

	private String generateZipEntry(String file) {
		return file.substring(SOURCE_FOLDER.length() + 1, file.length());
	}

    /**
     * Extracts a zip file specified by the zipFilePath to a directory specified by
     * destDirectory (will be created if does not exists)
     * @param zipFilePath
     * @param destDirectory
     * @throws IOException
     */
    public static void unzip(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry entry = zipIn.getNextEntry();
        // iterates over entries in the zip file
        while (entry != null) {
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                File dir = new File(filePath);
                dir.getParentFile().mkdirs();
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }
    /**
     * Extracts a zip entry (file entry)
     * @param zipIn
     * @param filePath
     * @throws IOException
     */
    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
    
    @SuppressWarnings("resource")
	public static InputStream getFromZipFile(File arquivoLev, String arquivoProcurado){
    	ZipFile zipFile = null;
    	
    	String paraLinux = arquivoProcurado.replace("/", "\\");
		try {
			zipFile = new ZipFile(arquivoLev);
			Enumeration<? extends ZipEntry> entries = zipFile.entries();

	        while(entries.hasMoreElements()){
	            ZipEntry entry = entries.nextElement();
	            InputStream stream = zipFile.getInputStream(entry);
	            System.out.println("---> "+entry.getName());
	            if(entry.getName().equals(arquivoProcurado) || entry.getName().equals(paraLinux)){	            	
	            	return stream;
	            }
	        }
	        
		} catch (IOException e) {e.printStackTrace();} 
		return null;
    }


}
