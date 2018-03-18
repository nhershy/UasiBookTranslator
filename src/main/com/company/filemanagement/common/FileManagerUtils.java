package com.company.filemanagement.common;

import com.company.filemanagement.FileWrapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileManagerUtils {
    private final FileWrapper FILE_WRAPPER;
    private final String NEW_LINE = System.lineSeparator();
    private String lastInstanceOfLine;

    public FileManagerUtils() {
        this.FILE_WRAPPER = null;
        this.lastInstanceOfLine = "";
    }

    public FileManagerUtils(final FileWrapper FILE_WRAPPER) {
        this.FILE_WRAPPER = FILE_WRAPPER;
        this.lastInstanceOfLine = "";
    }

    /*
    Creates directory if it is not found.
    Uses file from FILE_WRAPPER.
    Returns if the directory was created.
     */
    public boolean createDirectory() {
        return createDirectorIfNotFound(FILE_WRAPPER.getFilePath());
    }

    /*
    Creates directory if it is not found.
    Uses file parameters for directory path.
    Returns if the directory was created.
     */
    public boolean createDirectory(final File file) {
        return createDirectorIfNotFound(file);
    }

    public boolean deleteDirectory() {
        return deleteDirectoryIfExists(FILE_WRAPPER.getFilePath());
    }

    public boolean deleteDirectory(final File file) {
        return deleteDirectoryIfExists(file);
    }

    public boolean createFile() throws IOException {
        return createFileIfNotFound(FILE_WRAPPER.getFile());
    }

    public boolean createFile(final File file) throws IOException {
        return createFileIfNotFound(file);
    }

    /*
    Clears the file.
    Uses FILE_WRAPPER file.
     */
    public boolean clearFile() throws IOException {
        if (!FILE_WRAPPER.getFile().exists()) {
            return false;
        }

        try (PrintWriter pw = new PrintWriter(FILE_WRAPPER.getFile())) {
            pw.print("");
            return true;
        }
    }

    /*
    Clears the file.
    Uses the parameter file.
     */
    public boolean clearFile(final File file) throws IOException {
        if (!file.exists()) {
            return false;
        }

        try (PrintWriter pw = new PrintWriter(file)) {
            pw.print("");
            return true;
        }
    }

    public String readNextLine() throws IOException {
        try(Scanner scanner = new Scanner(FILE_WRAPPER.getFile())) {
            scanner.useDelimiter(NEW_LINE);
            String line;
            final boolean hasNext = scanner.hasNext();
            if (lastInstanceOfLine == "" && hasNext) {
                line = scanner.next();
                lastInstanceOfLine = line;
                return line;
            } else if (hasNext) {
                while (scanner.hasNext()) {
                    if (scanner.next() != lastInstanceOfLine) {
                        continue;
                    }
                    line = scanner.next();
                    lastInstanceOfLine = line;
                    return line;
                }
            }
        }
        return null;
    }

    public String readNextLine(final File file) throws IOException {
        try(Scanner scanner = new Scanner(file)) {
            scanner.useDelimiter(NEW_LINE);
            String line;
            final boolean hasNext = scanner.hasNext();
            if ("".equals(lastInstanceOfLine) && hasNext) {
                line = scanner.next();
                lastInstanceOfLine = line;
                return line;
            } else if (hasNext) {
                while (scanner.hasNext()) {
                    line = scanner.next();
                    if (!line.equals(lastInstanceOfLine)) {
                        continue;
                    } else if (scanner.hasNext()) {
                        line = scanner.next();
                        lastInstanceOfLine = line;
                        return line;
                    }
                }
            }
        }
        return null;
    }

    public void writeToFile(final String output, final boolean append) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_WRAPPER.getFile(), append))) {
            pw.println(output);
        }
    }

    public void writeToFile(final File file, final String output, final boolean append) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(file, append))) {
            pw.println(output);
        }
    }

    /*
    Checks if the directory exists. If it does not, it will create it.
    Returns if the directory was created.
     */
    private boolean createDirectorIfNotFound(final File file) {
        if (!file.exists()) {
            file.mkdir();
            return true;
        }
        return false;
    }

    private boolean deleteDirectoryIfExists(final File file) {
        if (file.exists()) {
            for (final String fileName : file.list()) {
                final File deleteFile = new File(file.getPath(), fileName);
                deleteFile.delete();
            }
            file.delete();
            return true;
        }
        return false;
    }

    private boolean createFileIfNotFound(final File file) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
            return true;
        }
        return false;
    }
}
