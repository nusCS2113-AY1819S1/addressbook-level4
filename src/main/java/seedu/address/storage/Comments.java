package seedu.address.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * Adds comments section.
 */
public class Comments {

    private File file = new File("src/main/resources/comments/");
    private final String commentsPath = file.getAbsolutePath();
    private int fileName = getLastFile(file);

    private int getLastFile(File folder) {
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles.length==0) {
            return 0;
        }
        String lastFile = listOfFiles[listOfFiles.length - 1].getName();
        return Integer.parseInt(lastFile.substring(0, lastFile.length() - 5));
    }
    public String getFilePath() {
        return this.commentsPath;
    }
    public int getFileName() {
        return this.fileName;
    }

    /**
     * Makes a Html file to store comments.
     */
    public void createHtml(String commentsDirectoryString, int x) {
        FileWriter fWriter = null;
        BufferedWriter writer = null;
        try {
            File f = new File(commentsDirectoryString + "/" + Integer.toString(x) + ".html");
            if (f.exists()) {
                this.fileName++;
                createHtml(commentsDirectoryString, this.fileName);
                return;
            }
            fWriter = new FileWriter(commentsDirectoryString + "/" + Integer.toString(x) + ".html");
            writer = new BufferedWriter(fWriter);
            writer.write("<span>This iss your html content here</span>");
            writer.newLine(); //this is not actually needed for html files - can make your code more readable though
            writer.close(); //make sure you close the writer object
        } catch (Exception e) {
            //catch any exceptions here
            System.out.println("failed");
        }
    }
    public static void main(String[] args){
        Comments comment = new Comments();
        comment.createHtml(comment.getFilePath(),comment.getFileName());
    }
}
