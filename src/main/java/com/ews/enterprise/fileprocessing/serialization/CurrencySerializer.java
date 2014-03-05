package com.ews.enterprise.fileprocessing.serialization;

import java.math.BigDecimal;
import java.util.List;

import com.ews.enterprise.fileprocessing.ColumnData;
/**
 * @author rana.ijaz
 */
public class CurrencySerializer extends DoubleSerializer implements TypeSerializer<BigDecimal> {

    /**
     * Generated serial Version UID
     */
    private static final long serialVersionUID = -3417510277903487569L;
    private String format;
    private final String paranthesis = "()";

    public CurrencySerializer() {
        format = paranthesis;
    }

    public CurrencySerializer(final String format) {
        this.format = format;
    }

    public final void serialize(final List<ColumnData> columns, final int index,
        final Object value) throws TypeSerializerException {
        String newValue = "";
        if (value != null) {
            newValue = wrapAsPerFormat(value.toString());
        }
        columns.add(index, new ColumnData(newValue, this.getJavaType()));
    }

    public final BigDecimal deserialize(final String[] columns, final int index)
        throws TypeSerializerException {
        try {
            return doTypeChecking(columns[index].trim(), format);
        } catch (TypeSerializerException exception) {
            throw new TypeSerializerException(exception.getMessage());
        } catch (Exception e) {
            throw new TypeSerializerException("Import Error: " + e.getMessage());
        }
    }

    public final BigDecimal doTypeChecking(final String inputAmount, final String negativeFormat)
        throws TypeSerializerException {

        String amountForValidation = inputAmount.replace(",", "");

        // Check For Correct $ position,. Either at Start or at End
        checkValidDollarPattern(amountForValidation, negativeFormat);

        // Now check for negative Format. We can replace $ as it is already
        // validated
        amountForValidation = amountForValidation.replace("$", "").replace(" ", "");
        String amount = validateNegativeNumFormat(amountForValidation, negativeFormat);

        // Now check for valid numbers in amount and get a BigDecimal
        BigDecimal bigDecimalAmount = validateAmountFormat(amount);
        return bigDecimalAmount;
    }

    public final void checkValidDollarPattern(final String amount, final String negativeNumFormat) throws
        TypeSerializerException {
        String amountForDollarValidation = new String(amount);
        amountForDollarValidation = amountForDollarValidation.replace("+", "").replace("-", "");
        // This regex checks for Valid position of $ value. $ can be at
        // beginning or at the end. Decimal is allowed
        String regExForDollarPattern = "([\\\\$]?\\d*[\\\\.]?[0-9]+)|(\\d*[\\\\.]?[0-9]+[\\\\$]?)";

        for (char c : negativeNumFormat.toCharArray()) {
            amountForDollarValidation = amountForDollarValidation.replace(Character.toString(c), "");
        }
        boolean isValid = validateFormat(amountForDollarValidation, regExForDollarPattern);
        if (!isValid) {
            throw new TypeSerializerException("Invalid Dollar Format.");
        }
    }

    public final String getJavaType() throws TypeSerializerException {
        return BigDecimal.class.getName();
    }

}
