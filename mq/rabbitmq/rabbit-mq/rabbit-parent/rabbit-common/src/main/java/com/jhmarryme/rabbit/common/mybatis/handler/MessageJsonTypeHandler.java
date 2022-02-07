package com.jhmarryme.rabbit.common.mybatis.handler;

import com.jhmarryme.rabbit.api.Message;
import com.jhmarryme.rabbit.common.util.FastJsonConvertUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author Jiahao Wang
 */
public class MessageJsonTypeHandler extends BaseTypeHandler<Message> {

    /**
     * 在生成 SQL 语句时，将对象转换为数据库中的 类型
     *
     * @param ps
     * @param i
     * @param parameter
     * @param jdbcType
     * @throws SQLException
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Message parameter,
            JdbcType jdbcType) throws SQLException {
        
        ps.setString(i, FastJsonConvertUtil.convertObjectToJSON(parameter));
    }

    // 下面三个都是从结果集中转换成  实体类

    /**
     * 从数据库读取时，将 字段中的 字符串转换为实体类
     *
     * @param rs
     * @param columnName
     * @return
     * @throws SQLException
     */
    @Override
    public Message getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
    	String value = rs.getString(columnName);
    	if(null != value && !StringUtils.isBlank(value)) {
    		return FastJsonConvertUtil.convertJSONToObject(rs.getString(columnName), Message.class);
    	}
    	return null;  
    }

    @Override
    public Message getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    	String value = rs.getString(columnIndex);
    	if(null != value && !StringUtils.isBlank(value)) {
    		return FastJsonConvertUtil.convertJSONToObject(rs.getString(columnIndex), Message.class);
    	}
    	return null;         
    }

    @Override
    public Message getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    	String value = cs.getString(columnIndex);
    	if(null != value && !StringUtils.isBlank(value)) {
    		return FastJsonConvertUtil.convertJSONToObject(cs.getString(columnIndex), Message.class);
    	}
    	return null;   
    }

}