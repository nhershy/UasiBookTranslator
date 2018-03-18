package com.company.filemanagement;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

public class FileWrapperTest {
    private final String TEMP_PATH = System.getProperty("java.io.tmpdir") + File.separator + "UasiBookTranslator";
    private final String TEST_FILE = "tmp.txt";
    private final Charset UTF8 = StandardCharsets.UTF_8;

    private FileWrapper fileWrapper;

    @Before
    public void Before() {
        fileWrapper = new FileWrapper(TEMP_PATH, TEST_FILE, UTF8);
    }

    @Test
    public void getPath() {
        assertEquals(TEMP_PATH, fileWrapper.getPath());
    }

    @Test
    public void getFileName() {
        assertEquals(TEST_FILE, fileWrapper.getFileName());
    }

    @Test
    public void getEncoding() {
        assertEquals(UTF8, fileWrapper.getEncoding());
    }

    @Test
    public void getFile() {
        final File file = new File(TEMP_PATH + File.separator + TEST_FILE);
        assertEquals(file, fileWrapper.getFile());
    }

    @Test
    public void getFilePath() {
        final File file = new File(TEMP_PATH);
        assertEquals(file, fileWrapper.getFilePath());
    }
}