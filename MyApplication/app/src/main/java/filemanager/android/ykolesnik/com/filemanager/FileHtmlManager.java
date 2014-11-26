package filemanager.android.ykolesnik.com.filemanager;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by s.mokiyenko on 11/26/14.
 */
public class FileHtmlManager {

    static void mergeTwoHtmlFiles(File firstHtml, File secondHtml, File mergeResult) throws IOException {
        Document firstHtmlDoc = Jsoup.parse(firstHtml, "UTF-8");
        Document secondHtmlDoc = Jsoup.parse(secondHtml, "UTF-8");
        Element firstHtmlBody = firstHtmlDoc.getElementsByTag("body").get(0);

        for (Element element : secondHtmlDoc.getElementsByTag("Body").get(0).getAllElements()) {
            firstHtmlBody.appendChild(element);
        }

        saveHmlToFile(mergeResult, firstHtmlDoc.body().html());

    }

    private static File saveHmlToFile(File saveFile, String content) throws IOException {
        FileWriter fileWriter = new FileWriter(saveFile);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(content);
        bufferedWriter.close();
        Log.d("Merging", "finished successfully");
        return saveFile;
    }

}
