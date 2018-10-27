package seedu.address.commons.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Helper functions for handling downloaded zip files
 */
public class UnzipUtil {

    private static final int PARAM_BUFFER_SIZE = 1024;
    private static final int PARAM_MININUM_SIZE = 0;
    private static final int PARAM_OFFSET = 0;

    /**
     * Returns if {@code unzipFilePath, targetFilePath and targetFileKeyword} is able to be found
     * e.g. user/main/toUnzip, user/main/target, zipped
     * The zipped file will then be deleted
     * @throws IOException if zipped file cannot be found, or if the buffered stream used to write the file is
     * corrupted
     */
    public static void unzipFile(String unzipFilePath, String unzipFileKeyword,
                                 String currentDirectory, String targetFilePath, String moduleCode) throws IOException {

        final String fullTargetFilePath =  currentDirectory + targetFilePath + "/" + moduleCode;
        File curDir = new File(unzipFilePath);

        final File targetFile = getFile(curDir, unzipFileKeyword);

        final String inputZipFile = unzipFilePath + "/" + targetFile.getName();
        final String outputZipFolder = fullTargetFilePath;

        unZip(inputZipFile,outputZipFolder);

        targetFile.delete();

    }

    /**
     * Returns the target file if it exists within the current directory.
     */
    private static File getFile(File curDir, String keyWord) {
        File targetFile = null;
        String currentName;
        File[] filesList = curDir.listFiles();
        for(File file : filesList){
            if(file.isFile()) {
                currentName = file.getName();
                if(currentName.contains(keyWord)) {
                    targetFile = file;
                }
            }
        }
        return targetFile;
    }

    /**
     * Unzips the target file if it exists within the current directory.
     * @throws IOException in the unlikely case that the buffered stream is corrupted.
     */
    private static void unZip(String zipFile, String outputFolder) throws IOException{
        byte[] buffer = new byte[PARAM_BUFFER_SIZE];
        try {
            //create output directory is not exists
            File folder = new File(outputFolder);
            if(!folder.exists()){
                folder.mkdir();
            }

            // Acquiring the content of the zip file through an input stream
            ZipInputStream zipinputstream = new ZipInputStream(new FileInputStream(zipFile));

            // Acquiring the list entries of the zip file
            ZipEntry zipEntry = zipinputstream.getNextEntry();

            // Unzipping the folder
            while(zipEntry != null) {
                String fileName = zipEntry.getName();
                File newFile = new File(outputFolder + File.separator + fileName);
                //create all non exists folders
                //else you will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fileOutputStream = new FileOutputStream(newFile);
                int len;
                while((len = zipinputstream.read(buffer)) > PARAM_MININUM_SIZE) {
                    fileOutputStream.write(buffer, PARAM_OFFSET, len);
                }
                fileOutputStream.close();
                zipEntry = zipinputstream.getNextEntry();
            }
            zipinputstream.closeEntry();
            zipinputstream.close();

        } catch(IOException ex){
            throw new IOException();
        }
    }
}