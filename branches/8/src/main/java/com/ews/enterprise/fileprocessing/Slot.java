package com.ews.enterprise.fileprocessing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Slot implements Serializable {

	private static final long serialVersionUID = -7923269877120261747L;
	private static final String SLOT_TYPE_ERROR = "Slots can only be composed of other Slots and or Strings";
    private Slot parent;

    private String id; // this will be the UUID
    private final List<Object> values = new ArrayList<Object>(0);

    public final void addValue(final Object v) throws Exception {

        if (v instanceof Slot || v instanceof String) {
            if (v instanceof Slot) {
                ((Slot) v).setParent(this);
            }

            this.values.add(v);
        } else {
            throw new Exception(SLOT_TYPE_ERROR);
        }
    }

    public final void replaceValue(final int index, final Object replacementValue) throws Exception {

        if (replacementValue instanceof Slot || replacementValue instanceof String) {
            this.values.set(index, replacementValue);
        } else {
            throw new Exception(SLOT_TYPE_ERROR);
        }
    }

    public final Object removeValue(final int index) {
        return this.values.remove(index);
    }

    public final Object[] getValues() {
        return values.toArray();
    }

    // returns concatenated plain string slots; in case of a plain string slot
    // returns its string

    @Override
    public final String toString() {
        StringBuilder slotString = new StringBuilder();

        if (values.size() != 0) {
            for (Iterator<?> i = values.iterator(); i.hasNext();) {
                Object o = i.next();
                slotString.append(o.toString());
            }
        }
        return slotString.toString();
    }

    public final void clear() {
        values.clear();
    }

    public final String getId() {
        return id;
    }

    public final void setId(final String id) {
        this.id = id;
    }

    public final Slot getParent() {
        return parent;
    }

    public final void setParent(final Slot parent) {
        this.parent = parent;
    }
}
