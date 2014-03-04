package com.ews.enterprise.fileprocessing;

import org.apache.poi.poifs.eventfilesystem.POIFSReaderEvent;
import org.apache.poi.poifs.eventfilesystem.POIFSReaderListener;

public class MacroListener implements POIFSReaderListener {
    private boolean macroDetected;

    public final boolean isMacroDetected() {
        return macroDetected;
    }

    public final void processPOIFSReaderEvent(final POIFSReaderEvent event) {
        if (event.getPath().toString().startsWith("\\Macros")
            || event.getPath().toString().startsWith("\\_VBA")) {
            macroDetected = true;
        }

    }
}
