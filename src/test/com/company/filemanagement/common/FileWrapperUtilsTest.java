package com.company.filemanagement.common;

import com.company.filemanagement.FileWrapper;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class FileWrapperUtilsTest {
    private final String TEMP_PATH = System.getProperty("java.io.tmpdir") + File.separator + "UasiBookTranslator";
    private final String TEST_FILE = "tmp.txt";
    private final Charset UTF8 = StandardCharsets.UTF_8;

    private FileWrapper fileWrapper;
    private File resourceFile;

    @Before
    public void Before() {
        resourceFile = new File(TEMP_PATH);
        fileWrapper = new FileWrapper(TEMP_PATH, TEST_FILE, UTF8);
    }

    @Test
    public void createDirectoryWithFileManager_Exists() {
        final FileManagerUtils fileManagerUtils = new FileManagerUtils(fileWrapper);
        fileManagerUtils.createDirectory();
        assertFalse(fileManagerUtils.createDirectory());
        fileManagerUtils.deleteDirectory();
    }

    @Test
    public void createDirectoryWithFileManager_DoesNotExist() {
        final FileManagerUtils fileManagerUtils = new FileManagerUtils(fileWrapper);
        assertTrue(fileManagerUtils.createDirectory());
        fileManagerUtils.deleteDirectory();
    }

    @Test
    public void createDirectoryWithFile_Exists() {
        final FileManagerUtils fileManagerUtils = new FileManagerUtils();
        fileManagerUtils.createDirectory(resourceFile);
        assertFalse(fileManagerUtils.createDirectory(resourceFile));
        fileManagerUtils.deleteDirectory(resourceFile);
    }

    @Test
    public void createDirectoryWithFile_DoesNotExists() {
        final FileManagerUtils fileManagerUtils = new FileManagerUtils();
        assertTrue(fileManagerUtils.createDirectory(resourceFile));
        fileManagerUtils.deleteDirectory(resourceFile);
    }

    @Test
    public void clearFileWithFileManager_Exists() {
        /*final FileManagerUtils fileManagerUtils = new FileManagerUtils(fileWrapper);

        fileManagerUtils.createDirectory();
        try {
            fileManagerUtils.createFile();
            fileManagerUtils.writeToFile("Random text.");
            assertEquals("Random text.\r\n", fileManagerUtils.readFile());
            fileManagerUtils.clearFile();
            assertEquals("", fileManagerUtils.readFile());
            fileManagerUtils.deleteDirectory();
        } catch (IOException ex) {
            fail();
        }*/
    }

    @Test
    public void clearFileWithFileManager_DoesNotExists(){
        final FileManagerUtils fileManagerUtils = new FileManagerUtils(fileWrapper);

        fileManagerUtils.createDirectory();
        try {
            assertFalse(fileManagerUtils.clearFile());
            fileManagerUtils.deleteDirectory();
        } catch (IOException ex) {
            fail();
        }
    }

    @Test
    public void clearFileWithFile_Exists() {
        //TODO: Write Test
        assertTrue(true);
    }

    @Test
    public void clearFileWithFile_DoesNotExists() {
        //TODO: Write Test
        assertTrue(true);
    }

    @Test
    public void deleteDirectoryWithFileManager_Exists() {
        final FileManagerUtils fileManagerUtils = new FileManagerUtils(fileWrapper);
        fileManagerUtils.createDirectory();
        assertTrue(fileManagerUtils.deleteDirectory());
    }

    @Test
    public void deleteDirectoryWithFileManager_DoesNotExist() {
        final FileManagerUtils fileManagerUtils = new FileManagerUtils(fileWrapper);
        assertFalse(fileManagerUtils.deleteDirectory());
    }

    @Test
    public void deleteDirectoryWithFile_Exists() {
        final FileManagerUtils fileManagerUtils = new FileManagerUtils();
        fileManagerUtils.createDirectory(resourceFile);
        assertTrue(fileManagerUtils.deleteDirectory(resourceFile));
    }

    @Test
    public void deleteDirectoryWithFile_DoesNotExist() {
        final FileManagerUtils fileManagerUtils = new FileManagerUtils();
        assertFalse(fileManagerUtils.deleteDirectory(resourceFile));
    }

    @Test
    public void clearFileWithFile() {
        //TODO: Write Test
        assertTrue(true);
    }

    @Test
    public void readFileWithFileManager() {
        //TODO: Write Test
        assertTrue(true);
    }

    @Test
    public void readFileWithFile() {
        //TODO: Write Test
        assertTrue(true);
    }

    @Test
    public void writeToFileWithFileManager() {
        //TODO: Write Test
        assertTrue(true);
    }

    @Test
    public void writeToFileWithFile() {
        //TODO: Write Test
        assertTrue(true);
    }
}