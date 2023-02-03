// Copyright 2001, FreeHEP.

package com.documentreader.docs.pdf.word.document.reader.viewer.office.thirdpart.emf.data;

import java.io.IOException;

import com.documentreader.docs.pdf.word.document.reader.viewer.office.thirdpart.emf.EMFInputStream;
import com.documentreader.docs.pdf.word.document.reader.viewer.office.thirdpart.emf.EMFRenderer;
import com.documentreader.docs.pdf.word.document.reader.viewer.office.thirdpart.emf.EMFTag;

/**
 * ExtCreateFontIndirectW TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: ExtCreateFontIndirectW.java 10367 2007-01-22 19:26:48Z duns $
 */
public class ExtCreateFontIndirectW extends EMFTag
{

    private int index;

    private ExtLogFontW font;

    public ExtCreateFontIndirectW()
    {
        super(82, 1);
    }

    public ExtCreateFontIndirectW(int index, ExtLogFontW font)
    {
        this();
        this.index = index;
        this.font = font;
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
    {

        return new ExtCreateFontIndirectW(emf.readDWORD(), new ExtLogFontW(emf));
    }

    public String toString()
    {
        return super.toString() + "\n  index: 0x" + Integer.toHexString(index) + "\n"
            + font.toString();
    }

    /**
     * displays the tag using the renderer
     *
     * @param renderer EMFRenderer storing the drawing session data
     */
    public void render(EMFRenderer renderer)
    {
        renderer.storeGDIObject(index, font);
    }
}
