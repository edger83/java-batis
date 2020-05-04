package com.lagou.sqlSession.typehandler;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BaseType {

    static final Set<Class<?>> types = new HashSet<>();
    static {
        types.add(byte.class);
        types.add(boolean.class);
        types.add(short.class);
        types.add(char.class);
        types.add(int.class);
        types.add(float.class);
        types.add(long.class);
        types.add(double.class);
        types.add(Byte.class);
        types.add(Boolean.class);
        types.add(Short.class);
        types.add(Character.class);
        types.add(Integer.class);
        types.add(Float.class);
        types.add(Long.class);
        types.add(Double.class);
        types.add(String.class);
        types.add(Date.class);
        types.add(BigDecimal.class);
        types.add(java.sql.Date.class);
    }

    public static boolean ok(Class<?> type) {
        return types.contains(type);
    }


    private static final ConcurrentHashMap<Class<?>, Integer> SQL_PARAM_TYPE = new ConcurrentHashMap<>();
    static {

        SQL_PARAM_TYPE.put(BigDecimal.class, Types.NUMERIC);
        SQL_PARAM_TYPE.put(BigInteger.class, Types.BIGINT);
        SQL_PARAM_TYPE.put(boolean.class, Types.BOOLEAN);
        SQL_PARAM_TYPE.put(Boolean.class, Types.BOOLEAN);
        SQL_PARAM_TYPE.put(byte[].class, Types.VARBINARY);
        SQL_PARAM_TYPE.put(byte.class, Types.TINYINT);
        SQL_PARAM_TYPE.put(Byte.class, Types.TINYINT);
        SQL_PARAM_TYPE.put(Calendar.class, Types.TIMESTAMP);
        SQL_PARAM_TYPE.put(java.sql.Date.class, Types.DATE);
        SQL_PARAM_TYPE.put(java.util.Date.class, Types.TIMESTAMP);
        SQL_PARAM_TYPE.put(double.class, Types.DOUBLE);
        SQL_PARAM_TYPE.put(Double.class, Types.DOUBLE);
        SQL_PARAM_TYPE.put(float.class, Types.REAL);
        SQL_PARAM_TYPE.put(Float.class, Types.REAL);
        SQL_PARAM_TYPE.put(int.class, Types.INTEGER);
        SQL_PARAM_TYPE.put(Integer.class, Types.INTEGER);
        SQL_PARAM_TYPE.put(LocalDate.class, Types.DATE);
        SQL_PARAM_TYPE.put(LocalDateTime.class, Types.TIMESTAMP);
        SQL_PARAM_TYPE.put(LocalTime.class, Types.TIME);
        SQL_PARAM_TYPE.put(long.class, Types.BIGINT);
        SQL_PARAM_TYPE.put(Long.class, Types.BIGINT);
        SQL_PARAM_TYPE.put(OffsetDateTime.class, Types.TIMESTAMP_WITH_TIMEZONE);
        SQL_PARAM_TYPE.put(OffsetTime.class, Types.TIME_WITH_TIMEZONE);
        SQL_PARAM_TYPE.put(Short.class, Types.SMALLINT);
        SQL_PARAM_TYPE.put(String.class, Types.VARCHAR);
        SQL_PARAM_TYPE.put(Time.class, Types.TIME);
        SQL_PARAM_TYPE.put(Timestamp.class, Types.TIMESTAMP);
        SQL_PARAM_TYPE.put(URL.class, Types.DATALINK);
    }
    public static Integer getSqlTypeid(Class<?> type) {
        return SQL_PARAM_TYPE.get(type);
    }
}
