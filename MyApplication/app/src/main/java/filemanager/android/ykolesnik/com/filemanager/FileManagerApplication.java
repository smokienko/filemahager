package filemanager.android.ykolesnik.com.filemanager;

import android.app.Application;
import android.os.Environment;

/**
 * Created by s.mokiyenko on 11/22/14.
 */
public class FileManagerApplication extends Application {

    private static FileManager fileManager;

    @Override
    public void onCreate() {
        super.onCreate();

        fileManager = new FileManager("/mnt/shared");
    }

    public static FileManager getFileManager() {
        return fileManager;
    }

}
