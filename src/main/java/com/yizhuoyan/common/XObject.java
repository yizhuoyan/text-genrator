package com.yizhuoyan.common;

import java.io.Serializable;

/**
 * 超级对象，可以快速进行类型转化
 */
public class XObject implements Serializable {

    private final Object value;

    private XObject(Object v) {
        this.value = v;
    }

    public static XObject of(Object v) {
        return new XObject(v);
    }

    public static XObject of(Object v, Object ifNull) {
        return new XObject(v == null ? ifNull : v);
    }

    public Byte asByte() {
        if (value == null) return null;
        if (value instanceof Byte) {
            return (Byte) value;
        }
        if (value instanceof Number) {
            return (((Number) value)).byteValue();
        }
        return Byte.parseByte(value.toString());
    }

    public Short asShort() {
        if (value == null) return null;
        if (value instanceof Short) {
            return (Short) value;
        }
        if (value instanceof Number) {
            return (((Number) value)).shortValue();
        }
        return Short.parseShort(value.toString());
    }


    public Integer asInteger() {
        if (value == null) return null;
        if (value instanceof Integer) {
            return (Integer) value;
        }
        if (value instanceof Number) {
            return (((Number) value)).intValue();
        }
        return Integer.parseInt(value.toString());
    }

    public Character asCharacter() {
        if (value == null) return null;
        if (value instanceof Character) {
            return ((Character) value);
        }
        if (value instanceof Number) {
            return (char) (((Number) value)).intValue();
        }
        final String s = value.toString();
        if (s.length() == 0) return '\0';
        return s.charAt(0);
    }

    public Long asLong() {
        if (value == null) return null;
        if (value instanceof Long) {
            return ((Long) value);
        }
        if (value instanceof Number) {
            return (((Number) value)).longValue();
        }
        return Long.parseLong(value.toString());
    }

    public Float asFloat() {
        if (value == null) return null;
        if (value instanceof Float) {
            return ((Float) value);
        }
        if (value instanceof Number) {
            return (((Number) value)).floatValue();
        }
        return Float.parseFloat(value.toString());
    }

    public Double asDouble() {
        if (value == null) return null;
        if (value instanceof Float) {
            return ((Double) value);
        }
        if (value instanceof Number) {
            return (((Number) value)).doubleValue();
        }
        return Double.parseDouble(value.toString());
    }

    /**
     */
    public Boolean asBoolean() {
        if (value == null) return null;
        if (value instanceof Boolean) {
            return ((Boolean) value);
        }
        if (value instanceof Number) {
            return (((Number) value)).intValue()!=0;
        }
        return Boolean.parseBoolean(value.toString());
    }



    public String asString() {
        if (value == null) return null;
        if (value instanceof CharSequence) {
            return ((CharSequence) value).toString();
        }
        return value.toString();
    }
    public <T> T[] asArray() {
        if (value == null) return null;
        if (value.getClass().isArray()) {
            return (T[]) value;
        }
        throw new UnsupportedOperationException();
    }

    public XObject asXObject() {
        if (value == null) return null;
        if (value instanceof XObject) {
            return ((XObject) value);
        }
        return  new XObject(value);
    }
    public <T> T as() {
        return (T) value;
    }

}
