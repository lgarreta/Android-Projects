package com.lgarreta.mascotasinfo;

import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {

    public static void appendStringToFile(String string, String fileName) {
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(dir, fileName);

        try {
            // Ensure the directory exists
            if (!dir.exists()) {
                dir.mkdirs();
            }
            // Create or open the file for appending
            FileOutputStream fos = new FileOutputStream(file, true);
            // Write the string to the file
            fos.write(string.getBytes());
            fos.write("\n".getBytes());
            // Close the file
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
