package com.ln.mybaits.typeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.ln.mybaits.bean.EmpStatus;

/**自定义类型处理器
 * 1、实现TypeHandler接口。或者继承BaseTypeHandler
 * @author LiNian
 *
 */
public class MyEnumEmpStatusTypeHandler implements TypeHandler<EmpStatus>{

	/**
	 * 定义当前数据如何保存到数据库中
	 */
	@Override
	public void setParameter(PreparedStatement ps, int i, EmpStatus parameter, JdbcType jdbcType) throws SQLException {
		// TODO Auto-generated method stub
		System.out.println("要保存的状态码："+parameter.getCode());
		ps.setString(i, parameter.getCode().toString());
	}
	
	@Override
	public EmpStatus getResult(ResultSet rs, String columnName) throws SQLException {
		// TODO Auto-generated method stub
		// 需要根据从数据库中拿到的枚举的状态码
		int code=rs.getInt(columnName);
		System.out.println("从数据库中获取的状态码："+code);
		EmpStatus status=EmpStatus.getEmpStatusByCode(code);
		return status;
	}

	@Override
	public EmpStatus getResult(ResultSet rs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		int code=rs.getInt(columnIndex);
		System.out.println("从数据库中获取的状态码："+code);
		EmpStatus status=EmpStatus.getEmpStatusByCode(code);
		return status;
	}

	@Override
	public EmpStatus getResult(CallableStatement cs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		int code=cs.getInt(columnIndex);
		System.out.println("从数据库中获取的状态码："+code);
		EmpStatus status=EmpStatus.getEmpStatusByCode(code);
		return status;
	}



}
