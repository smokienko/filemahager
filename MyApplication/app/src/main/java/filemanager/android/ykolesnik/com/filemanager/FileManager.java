package filemanager.android.ykolesnik.com.filemanager;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by s.mokiyenko on 11/22/14.
 */
public class FileManager {

    private File currentFolder;
    private String rootFolderPath;
    private ArrayList<File> bufferedFiles;
    private ArrayList<String> selectedFiles;
    private String searchQuery;

    public static final String ROOT_FOLDER_NAME = "...";

    public FileManager(final String currentFolderPath) {
        rootFolderPath = currentFolderPath;
        this.bufferedFiles = new ArrayList<File>();
        this.selectedFiles = new ArrayList<String>();
        this.currentFolder = new File(currentFolderPath);
    }

    public List<String> getFolders(){
        List<String> folders = new ArrayList<String>();
        if (!currentFolder.getAbsolutePath().equals(rootFolderPath)){
            folders.add(ROOT_FOLDER_NAME);
        }
        for (File file : currentFolder.listFiles()){
            if (file.isDirectory()){
                folders.add(file.getName());
            }
        }
        return folders;
    }

    public List<String> getFolderContent(){
        return new ArrayList<String>(Arrays.asList(currentFolder.list()));
    }

    public String getCurrentFolder() {
        return currentFolder.getName();
    }

    public void goToFolder(final String folderName) {
        if (folderName.equals(ROOT_FOLDER_NAME)) {
            currentFolder = currentFolder.getParentFile();
            return;
        }
            selectedFiles.clear();
            currentFolder = createFileFromString(currentFolder, folderName);
    }

    public boolean delete() {
        boolean result = false;
        if (!selectedFiles.isEmpty()) {
            for (String file : selectedFiles) {
                File deleteFile = createFileFromString(currentFolder, file);
                result = deleteFile.delete();
            }
            selectedFiles.clear();
        }
        return result;
    }

    public void copy() {
        if(!selectedFiles.isEmpty()) {
            bufferedFiles.clear();
            for (String fileName : selectedFiles){
                bufferedFiles.add(createFileFromString(currentFolder, fileName));
            }
        }
    }

    public ArrayList<String> getSelectedFiles() {
        return selectedFiles;
    }

    public void addSelectedFile(String selectedFile) {
        this.selectedFiles.add(selectedFile);
    }

    public void removeFromSelectedFiles(String selectedFile) {
        this.selectedFiles.remove(selectedFile);
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    private File createFileFromString(final File folder, final String filename) {
        return new File(new StringBuilder(folder.getAbsolutePath()).append("/" + filename).toString());
    }

    public void paste (){
        if (!bufferedFiles.isEmpty()){
            for (File pasteFile : bufferedFiles) {
                if (pasteFile.isDirectory()) {
                    File directory = new File(currentFolder, pasteFile.getName());
                    if (!directory.exists()) {
                        directory.mkdir();
                        for (File file : pasteFile.listFiles()) {
                            copyFile(file, directory);
                        }
                    }
                } else {
                    copyFile(pasteFile, currentFolder);
                }
            }
        }
    }

    public boolean merge2htmlFiles(){
        if(selectedFiles.size() == 2 && selectedFiles.get(0).contains(".html") && selectedFiles.get(1).contains(".html")) {
           File firstHtm = createFileFromString(currentFolder,selectedFiles.get(0));
           File secondHtm = createFileFromString(currentFolder,selectedFiles.get(1));
           File resultFile =  createFileFromString(currentFolder,"Merge_Result.html");
            try {
                FileHtmlManager.mergeTwoHtmlFiles(firstHtm,secondHtm,resultFile);
            } catch (IOException e) {
                Log.e("Merging html", "exception happened", e);
                return false;
            }
            return true;
        }
        return false;
    }

    private void copyFile(File sourceFilee, File directory){
        File file = new File(directory,sourceFilee.getName());
        if(!file.exists()){
            FileChannel sourceChannel = null;
            FileChannel destChannel = null;
            try {
                sourceChannel = new FileInputStream(sourceFilee).getChannel();
                destChannel = new FileOutputStream(file).getChannel();
                destChannel.transferFrom(sourceChannel,0,sourceChannel.size());
            } catch (FileNotFoundException e) {
                Log.e("File not found while coping", sourceFilee.getName(),e);
            } catch (IOException e) {
                Log.e("IO error while coping", sourceFilee.getName(),e);
            } finally {
                if(sourceChannel != null){
                    try {
                        sourceChannel.close();
                    } catch (IOException e) {
                        Log.e("IO error while closing sourceChanel", sourceFilee.getName(),e);
                    }
                }
                if (destChannel != null) {
                    try {
                        destChannel.close();
                    } catch (IOException e) {
                        Log.e("IO error while closing destChane", sourceFilee.getName(),e);
                    }
                }
            }
        }
    }

}
