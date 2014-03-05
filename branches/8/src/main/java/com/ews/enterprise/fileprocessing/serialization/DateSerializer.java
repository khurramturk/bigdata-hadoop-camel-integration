package com.ews.enterprise.fileprocessing.serialization;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.ews.enterprise.fileprocessing.ColumnData;
import com.ews.enterprise.fileprocessing.exceptions.DateRangeException;
/**
 * @author rana.ijaz
 */
public class DateSerializer implements TypeSerializer<Date> {

    /**
     * Generated serial version UID
     */
    private static final long serialVersionUID = -836485799316393149L;
    private final String format;

    public DateSerializer(final String format) {
        this.format = format;
    }

    public final void serialize(final List<ColumnData> columns, final int index,
        final Object value) throws TypeSerializerException {
    	String newValue = "";
    	if (value != null) {
    		newValue = new SimpleDateFormat(format).format(value);
    	}
        columns.add(index,
            new ColumnData(newValue, this.getJavaType()));
    }

    public final Date deserialize(final String[] columns, final int index)
        throws TypeSerializerException {
        try {
            return validateDateFormat(columns[index].trim());
        } catch (ParseException e) {
            throw new TypeSerializerException("Date Format does not match with format selected on Template. "
                + "Format Selected is: " + format);
        } catch (DateRangeException ex) {
            throw new TypeSerializerException("Date Range is not correct : " + ex.getMessage());
        } catch (Exception ex) {
            throw new TypeSerializerException("Import Error: " + ex.getMessage());
        }
    }

    /**
     * It parse the date according to current acceptable date format
     *
     * @param date
     *            It is date
     * @return It returns date object if date is parsed successfully
     */
    private Date validateDateFormat(final String date) throws ParseException, DateRangeException {
        Date dateObj = null;
        SimpleDateFormat sdf = (SimpleDateFormat) DateFormat.getDateInstance();
        sdf.applyPattern(format);
        sdf.setLenient(false);
        dateObj = sdf.parse(date);
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        String yearStr = yearFormat.format(dateObj);
        Integer yearInt = new Integer(yearStr);
        if (yearInt < YEAR_LOW_LIMIT || yearInt > YEAR_HIGH_LIMIT) {
            throw new DateRangeException(
                "Valid date limit is between " + YEAR_LOW_LIMIT + " and " + YEAR_HIGH_LIMIT);
        }

        if (date.equals(sdf.format(dateObj))) {
            return dateObj;
        } else {
            throw new ParseException(
                "Date Format does not match with format selected on Template. Format Selected is: "
                    + format, 0);
        }
    }

    public final String getJavaType() throws TypeSerializerException {
        return java.util.Date.class.getName();
    }

	public final String getFormat() {
		return format;
	}

}
