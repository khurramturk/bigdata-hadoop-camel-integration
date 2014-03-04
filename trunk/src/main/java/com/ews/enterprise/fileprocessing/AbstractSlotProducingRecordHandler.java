package com.ews.enterprise.fileprocessing;

public abstract class AbstractSlotProducingRecordHandler implements RecordHandler {

    private final int windowSize;
    private Slot slot;

    protected AbstractSlotProducingRecordHandler() {
        this(-1);
    }

    protected AbstractSlotProducingRecordHandler(final int windowSize) {
        this.windowSize = windowSize;
    }

    public final void handleRecord(final Record record) throws FileImportException {
        if (slot == null) {
            slot = new Slot();
        }
        try {
            Slot rowSlot = new Slot();
            for (Column column : record.getColumns()) {
                Slot columnSlot = new Slot();

                columnSlot.addValue(column.getValue());
                rowSlot.addValue(columnSlot);
            }
            slot.addValue(rowSlot);
            if (windowSize > 0 && slot.getValues().length >= windowSize) {
                handleSlot(slot);
                slot = new Slot();
            }
        } catch (Exception e) {
            throw new FileImportException(e);
        }
    }

    public final void eof() throws FileImportException {
        handleSlot(slot);
        slot = new Slot();
    }

    protected abstract void handleSlot(final Slot slotToHandle) throws FileImportException;

}
