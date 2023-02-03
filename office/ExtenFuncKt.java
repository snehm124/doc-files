package com.documentreader.docs.pdf.word.document.reader.viewer.office;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;

import com.documentreader.docs.pdf.word.document.reader.viewer.office.fc.openxml4j.opc.PackagingURIHelper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: ExtenFunc.kt */
public final class ExtenFuncKt {
    public static final void copyUriToExternalFilesDir(Activity activity, Uri uri, String str) {
        Intrinsics.checkNotNullParameter(activity, "$this$copyUriToExternalFilesDir");
        Intrinsics.checkNotNullParameter(uri, "uri");
        Intrinsics.checkNotNullParameter(str, "fileName");
        try {
            InputStream openInputStream = activity.getContentResolver().openInputStream(uri);
            String valueOf = String.valueOf(activity.getExternalCacheDir());
            if (openInputStream != null) {
                FileOutputStream fileOutputStream = new FileOutputStream(new File(valueOf + PackagingURIHelper.FORWARD_SLASH_CHAR + str));
                BufferedInputStream bufferedInputStream = new BufferedInputStream(openInputStream);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                byte[] bArr = new byte[1024];
                try {
                    for (int read = bufferedInputStream.read(bArr); read > 0; read = bufferedInputStream.read(bArr)) {
                        bufferedOutputStream.write(bArr, 0, read);
                        bufferedOutputStream.flush();
                        bufferedOutputStream.close();
                        fileOutputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
            activity.runOnUiThread(new ExtenFuncKt$copyUriToExternalFilesDir$1(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static final String getFileNameByUri(Activity activity, Uri uri) {
        Cursor query;
        Intrinsics.checkNotNullParameter(activity, "$this$getFileNameByUri");
        String valueOf = String.valueOf(System.currentTimeMillis());
        if (uri == null || (query = activity.getContentResolver().query(uri, (String[]) null, (String) null, (String[]) null, (String) null)) == null || query.getCount() <= 0) {
            return valueOf;
        }
        query.moveToFirst();
        String string = query.getString(query.getColumnIndexOrThrow("_display_name"));
        Intrinsics.checkNotNullExpressionValue(string, "cursor.getString(cursor.…diaColumns.DISPLAY_NAME))");
        query.close();
        return string;
    }
}
