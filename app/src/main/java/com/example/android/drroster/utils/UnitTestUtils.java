package com.example.android.drroster.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

import java.io.FileOutputStream;

/**
 * Created by Nir on 5/13/2016.
 */
public class UnitTestUtils {

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void writeConfiguration(Context ctx ) {
        try (FileOutputStream openFileOutput =
                     ctx.openFileOutput( "config.txt", Context.MODE_PRIVATE);) {

            openFileOutput.write("This is a test1.".getBytes());
            openFileOutput.write("This is a test2.".getBytes());
        } catch (Exception e) {
            // not handled
        }
    }
}
