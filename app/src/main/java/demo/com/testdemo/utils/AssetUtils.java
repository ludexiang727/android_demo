package demo.com.testdemo.utils;

import android.content.Context;
import android.content.res.AssetManager;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ludexiang on 2016/4/28.
 */
public class AssetUtils {

    public static String readFile(Context context, String fileName) {
        AssetManager manager = context.getAssets();
        InputStream is = null;
        try {
            is = manager.open(fileName);
            int size = is.available();
            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Convert the buffer into a string.
            String text = new String(buffer, "utf-8");
            return text;
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }


}
