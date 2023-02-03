// Copyright 2002, FreeHEP.

package com.documentreader.docs.pdf.word.document.reader.viewer.office.thirdpart.emf.data;

import java.io.IOException;

import com.documentreader.docs.pdf.word.document.reader.viewer.office.java.awt.Dimension;
import com.documentreader.docs.pdf.word.document.reader.viewer.office.thirdpart.emf.EMFInputStream;
import com.documentreader.docs.pdf.word.document.reader.viewer.office.thirdpart.emf.EMFRenderer;
import com.documentreader.docs.pdf.word.document.reader.viewer.office.thirdpart.emf.EMFTag;

/**
 * SetWindowExtEx TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: SetWindowExtEx.java 10367 2007-01-22 19:26:48Z duns $
 */
public class SetWindowExtEx extends EMFTag
{

    private Dimension size;

    public SetWindowExtEx()
    {
        super(9, 1);
    }

    public SetWindowExtEx(Dimension size)
    {
        this();
        this.size = size;
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
    {

        return new SetWindowExtEx(emf.readSIZEL());
    }

    public String toString()
    {
        return super.toString() + "\n  size: " + size;
    }

    /**
     * displays the tag using the renderer
     *
     * @param renderer EMFRenderer storing the drawing session data
     */
    public void render(EMFRenderer renderer)
    {
        renderer.setWindowSize(size);
    }
}
