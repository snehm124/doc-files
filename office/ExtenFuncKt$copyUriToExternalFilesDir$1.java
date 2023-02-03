package com.documentreader.docs.pdf.word.document.reader.viewer.office;

import android.net.Uri;

import java.io.FileNotFoundException;

/* compiled from: ExtenFunc.kt */
final class ExtenFuncKt$copyUriToExternalFilesDir$1 implements Runnable {
    final /* synthetic */ Uri $uri;

    ExtenFuncKt$copyUriToExternalFilesDir$1(Uri uri) {
        this.$uri = uri;
    }

    public final void run() {
        try {
            throw new FileNotFoundException("Can't open" + this.$uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
