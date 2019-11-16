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

@MappedTypes(Integer[].class)
public class String2IntArrayTypeHandler implements TypeHandler<Integer[]> {
    /*输入映射*/
    @Override
    public void setParameter(PreparedStatement preparedStatement, int index, Integer[] array, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(index,parseArray2String(array));
    }
    /*输出映射*/
    @Override
    public Integer[] getResult(ResultSet resultSet, String columnName) throws SQLException {
        //根据列名获得数据
        String array = resultSet.getString(columnName);
        //将获得的数据转换成目标类型
        return parseString2IntArray(array);
    }

    @Override
    public Integer[] getResult(ResultSet resultSet, int index) throws SQLException {
        String array = resultSet.getString(index);
        return parseString2IntArray(array);
    }

    @Override
    public Integer[] getResult(CallableStatement callableStatement, int i) throws SQLException {
        String array = callableStatement.getString(i);
        return parseString2IntArray(array);
    }


    private String parseArray2String(Integer[] array) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String arrayString = objectMapper.writeValueAsString(array);
            return arrayString;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;

    }
    private Integer[] parseString2IntArray(String arrayString){
        ObjectMapper objectMapper = new ObjectMapper();
        String s = arrayString.substring(1, arrayString.length() - 1);
        String[] split = s.split(",");
        Integer[] array = new Integer[split.length];
        for (int i = 0; i < split.length; i++) {
            array[i] = Integer.parseInt(split[i]);
        }
        return array;
    }
}
