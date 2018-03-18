package com.company.filemanagement;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FileWrapper
{
    //Temporary paths. Need to setup a better way to handle cross-platform file handling.
    //System.getProperty(user.home) will get for both linux and windows,
    //but Documents is not a linux path.
    public final static String USER_PATH = System.getProperty("user.home");
    public final static String DOCUMENT_PATH = USER_PATH + File.separator + "Documents";
    public final static String GITHUB_PATH = DOCUMENT_PATH + File.separator + "GitHub";
    public final static String PROGRAM_PATH = GITHUB_PATH + File.separator + "UasiBookTranslator";
    public final static Charset UTF8 = StandardCharsets.UTF_8;
    
    private final String path;
    private final String fileName;
    private final Charset encoding;

    public FileWrapper(final String path,
                       final String fileName,
                       final Charset encoding)
    {
        this.path = path;
        this.fileName = fileName;
        this.encoding = encoding;
    }

    public String getPath()
    {
        return path;
    }

    public String getFileName()
    {
        return fileName;
    }

    public Charset getEncoding()
    {
        return encoding;
    }

    public File getFile()
    {
        return new File(path + File.separator + fileName);
    }

    public File getFilePath() {
        return new File(path);
    }
}
