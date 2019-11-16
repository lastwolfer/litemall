package com.pandax.litemall.handler;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(String[].class)
public class String2ArrayTypeHandler implements TypeHandler<String[]> {
    /*输入映射*/
    @Override
    public void setParameter(PreparedStatement preparedStatement, int index, String[] strings, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(index,parseArray2String(strings));
    }
    /*输出映射*/
    @Override
    public String[] getResult(ResultSet resultSet, String columnName) throws SQLException {
        //根据列名获得数据
        String string = resultSet.getString(columnName);
        //将获得的数据转换成目标类型
        return parseString2Array(string);
    }

    @Override
    public String[] getResult(ResultSet resultSet, int index) throws SQLException {
        String string = resultSet.getString(index);
        return parseString2Array(string);
    }

    @Override
    public String[] getResult(CallableStatement callableStatement, int i) throws SQLException {
        String string = callableStatement.getString(i);
        return parseString2Array(string);
    }


    private String parseArray2String(String[] strings) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String s = objectMapper.writeValueAsString(strings);
            return s;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;

    }
    private String[] parseString2Array(String arrayString){
        ObjectMapper objectMapper = new ObjectMapper();
        String[] strings = new String[0];
        try {
            strings = objectMapper.readValue(arrayString, String[].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return strings;
    }
}
