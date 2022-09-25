package fr.saphyr.ce;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3FileHandle;
import com.badlogic.gdx.files.FileHandle;

import java.io.File;

public class CELwjgl3Files implements Files {

    static public final String externalPath = System.getProperty("user.home") + File.separator;
    static public final String localPath;
    static {
        final String pathAbsolute = new File("").getAbsolutePath();
        final var builder = new StringBuilder();
        final String[] arr = pathAbsolute.split("tests");
        for(String s : arr) builder.append(s);
        localPath = builder + "assets"+File.separator;
    }

    @Override
    public FileHandle getFileHandle (String fileName, FileType type) {
        return new Lwjgl3FileHandle(fileName, type);
    }

    @Override
    public FileHandle classpath (String path) {
        return getFileHandle(path, FileType.Classpath);
    }

    @Override
    public FileHandle internal (String path) {
        return getFileHandle(path, FileType.Internal);
    }

    @Override
    public FileHandle external (String path) {
        return getFileHandle(path, FileType.External);
    }

    @Override
    public FileHandle absolute (String path) {
        return getFileHandle(path, FileType.Absolute);
    }

    @Override
    public FileHandle local (String path) {
        return getFileHandle(path, FileType.Local);
    }

    @Override
    public String getExternalStoragePath () {
        return externalPath;
    }

    @Override
    public boolean isExternalStorageAvailable () {
        return true;
    }

    @Override
    public String getLocalStoragePath () {
        return localPath;
    }

    @Override
    public boolean isLocalStorageAvailable () {
        return true;
    }
}
