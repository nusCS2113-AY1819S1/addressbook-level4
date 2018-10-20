package seedu.address.commons.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Writes and reads files
 */
public class FileUtil {

    private static final String CHARSET = "UTF-8";

    public static boolean isFileExists(Path file) {
        return Files.exists(file) && Files.isRegularFile(file);
    }

    /**
     * Returns true if {@code path} can be converted into a {@code Path} via {@link Paths#get(String)},
     * otherwise returns false.
     * @param path A string representing the file path. Cannot be null.
     */
    public static boolean isValidPath(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException ipe) {
            return false;
        }
        return true;
    }

    /**
     * Returns the Path data that has been converted via {@link Paths#get(String)}
     * @param path A string representing the file path. Cannot be null.
     */
    public static Path getPath(String path) throws InvalidPathException {
        return Paths.get(path);
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories.
     * @throws IOException if the file or directory cannot be created.
     */
    public static void createIfMissing(Path file) throws IOException {
        if (!isFileExists(file)) {
            createFile(file);
        }
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories.
     */
    public static void createFile(Path file) throws IOException {
        if (Files.exists(file)) {
            return;
        }

        createParentDirsOfFile(file);

        Files.createFile(file);
    }

    /**
     * Creates parent directories of file if it has a parent directory
     */
    public static void createParentDirsOfFile(Path file) throws IOException {
        Path parentDir = file.getParent();

        if (parentDir != null) {
            Files.createDirectories(parentDir);
        }
    }

    /**
     * Assumes file exists
     */
    public static String readFromFile(Path file) throws IOException {
        return new String(Files.readAllBytes(file), CHARSET);
    }

    /**
     * Writes given string to a file.
     * Will create the file if it does not exist yet.
     */
    public static void writeToFile(Path file, String content) throws IOException {
        Files.write(file, content.getBytes(CHARSET));
    }

    /**
     * Read each of the file and store them into a list of string and return them
     *
     * Note: Try-Block is placed within the stream as the #close method is called on it,
     * otherwise the underlying file handle is never closed until the garbage collector does it later
     *
     * @see <a href="https://stackoverflow.com/questions/5868369/how-to-read-a-large-text-file-line-by-line-using-java">
     *     https://stackoverflow.com/questions/5868369/how-to-read-a-large-text-file-line-by-line-using-java</a>
     */
    public static List<String> readEachLineFromFile(Path file) throws IOException {
        List<String> list = new ArrayList<>();
        try (Stream<String> stream = Files.lines(file, Charset.defaultCharset())) {
            stream.forEachOrdered(list::add);
        }
        return list;
    }

    /**
     * Return the root location folder of the program is located in
     */
    public static String getRootLocation() {
        Path currentRelativePath = Paths.get("");
        return currentRelativePath.toAbsolutePath().toString();
    }


}
