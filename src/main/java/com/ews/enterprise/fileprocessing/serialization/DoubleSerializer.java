package com.ews.enterprise.fileprocessing.serialization;

import java.math.BigDecimal;
import java.util.List;

import com.ews.enterprise.fileprocessing.ColumnData;
/**
 * @author rana.ijaz
 */
public class DoubleSerializer implements TypeSerializer<BigDecimal> {

    /**
     * Generated serial Version UID
     */
    private static final long serialVersionUID = -3417510277903487569L;
    private String format;
    private final String paranthesis = "()";
    private final String negative = "-";

    public DoubleSerializer() {
        format = paranthesis;
    }

    public DoubleSerializer(final String format) {
        this.format = format;
    }

    public void serialize(final List<ColumnData> columns, final int index,
        final Object value) throws TypeSerializerException {
        String newValue = "";
        if (value != null) {
            newValue = wrapAsPerFormat(value.toString());
        }
        columns.add(index, new ColumnData(newValue, this.getJavaType()));
    }

    public BigDecimal deserialize(final String[] columns, final int index)
        throws TypeSerializerException {
        try {
            if (columns[index].trim().contains("$") || columns[index].trim().contains(",")) {
                throw new TypeSerializerException("Invalid data format.");
            }
            return doTypeChecking(columns[index].trim(), format);
        } catch (TypeSerializerException exception) {
            throw new TypeSerializerException(exception.getMessage());
        } catch (Exception e) {
            throw new TypeSerializerException("Import Error: " + e.getMessage());
        }
    }

    public BigDecimal doTypeChecking(final String inputAmount, final String negativeFormat)
        throws TypeSerializerException {

        String amount = validateNegativeNumFormat(inputAmount, negativeFormat);

        // Now check for valid numbers in amount and get a BigDecimal
        BigDecimal bigDecimalAmount = validateAmountFormat(amount);
        return bigDecimalAmount;
    }

    protected final  BigDecimal validateAmountFormat(final String amount)
        throws TypeSerializerException {
        // This regex checks for valid numbers in amount.
        String regExForNumber = "(\\+|-)?((([0-9]+)|)+(([0-9]+)|(\\.[0-9]+)))";
        boolean isValid = validateFormat(amount, regExForNumber);
        if (isValid) {
            try {
                return new BigDecimal(amount);
            } catch (Exception ex) {
                throw new TypeSerializerException("Exception while validating format. " + ex.getMessage());
            }
        } else {
            throw new TypeSerializerException("Invalid Data Format.");
        }

    }

    protected final boolean validateFormat(
        final String amount, final String amountFormat) {
        boolean isInValidFormat = false;
        if (amount != null && amount.matches(amountFormat)) {
            isInValidFormat = true;
        }
        return isInValidFormat;
    }

    protected final  String validateNegativeNumFormat(final String amount, final String negativeNumFormat)
        throws TypeSerializerException {
        boolean isNegativeAmount = false;
        String negativeCheckAmount = new String(amount);
        try {
            int negativeFormatPosition = 0;
            if (negativeNumFormat != null) {
                for (char c : negativeNumFormat.toCharArray()) {
                    if (negativeCheckAmount.indexOf(Character.toString(c)) == negativeFormatPosition) {
                        isNegativeAmount = true;
                        negativeCheckAmount = negativeCheckAmount.replace(Character.toString(c), "");
                        negativeFormatPosition = negativeCheckAmount.length() - 1;
                    } else {
                        isNegativeAmount = false;
                    }
                }
                negativeCheckAmount = isNegativeAmount ? "-" + negativeCheckAmount : amount;
            } else {
                throw new TypeSerializerException("No format found for negative numbers.");
            }
        } catch (Exception ex) {
            throw new TypeSerializerException("Exception while validating negative format. " + ex.getMessage());
        }
        return negativeCheckAmount;
    }

    public String getJavaType() throws TypeSerializerException {
        return BigDecimal.class.getName();
    }

    public final  String wrapAsPerFormat(final String newValue) {
        // Number is always in correct format. If we have more format in future
        // we can add them here.
        StringBuilder modifiedValue = new StringBuilder(newValue);
        if (format.equalsIgnoreCase(paranthesis)) {
            if (newValue.startsWith(negative)) {
                String value = newValue.replace("-", "");
                modifiedValue = new StringBuilder();
                modifiedValue = modifiedValue.append("(").append(value).append(")");
            }
        }
        return modifiedValue.toString();
    }
}
