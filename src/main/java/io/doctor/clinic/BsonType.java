package io.doctor.clinic;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.DynamicParameterizedType;
import org.hibernate.usertype.UserType;
import org.postgresql.util.PGobject;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Properties;

@Slf4j
public class BsonType implements UserType, DynamicParameterizedType {

    public static final String BSON = "BsonType";
    private static final int[] SQL_TYPES = {Types.JAVA_OBJECT};
    private Class<?> returnedClass;
    private Class<?> parametrizedClass;

    @Override
    public boolean equals(Object x, Object y) {
        if (x == y) {
            return true;
        } else if (x == null || y == null) {
            return false;
        } else {
            return x.equals(y);
        }
    }

    @Override
    public int hashCode(Object x) {
        return null == x ? 0 : x.hashCode();
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index,
                            SharedSessionContractImplementor session) throws SQLException {
        PGobject dataObject = new PGobject();
        dataObject.setType("json");

        if (value != null) {
            dataObject.setValue(convertObjectToJson(value));
        }

        st.setObject(index, dataObject);
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names,
                              SharedSessionContractImplementor session, Object owner)
            throws SQLException {
        Object result = resultSet.getObject(names[0]);
        if (result instanceof PGobject) {
            return convertJsonToObject(((PGobject) result).getValue());
        }

        return null;
    }

    private Object convertJsonToObject(String content) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
            mapper.registerModule(new JavaTimeModule());
            JavaType type = createJavaType();
            if (type == null) {
                if (parametrizedClass != null) {
                    return mapper.readValue(content, mapper.getTypeFactory().constructCollectionType(List.class,
                            parametrizedClass));
                } else {
                    return mapper.readValue(content, returnedClass);
                }
            }
            return mapper.readValue(content, type);
        } catch (IOException e) {
            throw new HibernateException(e);
        }
    }

    private String convertObjectToJson(Object object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            mapper.registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public Object deepCopy(Object value) {
        String json = convertObjectToJson(value);
        return convertJsonToObject(json);
    }

    @Override
    public Object replace(Object original, Object target, Object owner) {
        return deepCopy(original);
    }

    @Override
    public Serializable disassemble(Object value) {
        return (Serializable) deepCopy(value);
    }

    @Override
    public Object assemble(Serializable cached, Object owner) {
        return deepCopy(cached);
    }

    @SuppressWarnings("deprecation")
    public JavaType createJavaType() {
        try {
            return SimpleType.construct(returnedClass());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    @Override
    public void setParameterValues(Properties parameters) {
        final ParameterType reader = (ParameterType) parameters.get(PARAMETER_TYPE);
        if (reader != null) {
            this.returnedClass = reader.getReturnedClass();
            if (returnedClass == List.class) {
                try {
                    this.parametrizedClass = (Class) ((ParameterizedType) Class.forName((String) parameters.get(ENTITY))
                            .getDeclaredField((String) parameters.get(PROPERTY)).
                            getGenericType()).getActualTypeArguments()[0];
                } catch (NoSuchFieldException | ClassNotFoundException e) {
                    log.error("Bson initialization error", e);
                    throw new HibernateException(e);
                }
            }
        }
    }

    @Override
    public Class<?> returnedClass() {
        return this.returnedClass;
    }

}
