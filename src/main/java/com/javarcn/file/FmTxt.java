package com.javarcn.file;

import com.javarcn.util.Constants;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by jiacheng on 2017/9/12 0012.
 */
public class FmTxt {

    public static void save(List<String> list) {

        try {
            PrintWriter writer = new PrintWriter(Constants.FM_TXT);
            for (String s : list) {
                writer.write(s + "\r\n");
            }
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

}
