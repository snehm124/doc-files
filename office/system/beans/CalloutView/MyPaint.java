package com.documentreader.docs.pdf.word.document.reader.viewer.office.system.beans.CalloutView;

import android.graphics.Paint;

class MyPaint extends Paint {
	public MyPaint() {
		super();
		setAntiAlias(true);
		setDither(true);
		setStyle(Style.STROKE);
		setStrokeJoin(Join.ROUND);
		setStrokeCap(Cap.ROUND);
	}
}
