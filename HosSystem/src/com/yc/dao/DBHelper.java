package com.yc.dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class DBHelper {
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

/*	private Connection getConnection() {
		
		Connection con = null;
		
		Context context;
		try {
			context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/mysql_hossys");
			con = dataSource.getConnection();
			
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return con;
	}*/
	
	private Connection getConnection() {
		
		Connection con = null;
		
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hossys", "root", "Nanhua123!");  //Nanhua123!
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	public int update(String sql, Object ... params) {		//... ����������
		int result = 0;
		PreparedStatement pstmt = null;
		Connection con = null;
		con = this.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			setParams(pstmt, params);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.closeAll(con, pstmt, null);
		return result;
	}
	public int update(String sql, List<Object> params) {		
		int result = 0;
		PreparedStatement pstmt = null;
		Connection con = null;
		try {
			con = this.getConnection();
			pstmt = con.prepareStatement(sql);
			setParams(pstmt, params);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			this.closeAll(con, pstmt, null);
		}
		return result;
	}

	public void setParams(PreparedStatement pstmt, Object... params) throws SQLException {
		if(params != null && params.length > 0) {
			for(int i = 0, len = params.length; i < len; i++) {
				pstmt.setObject(i + 1, params[i]);
			}
		}
	}
	public void setParams(PreparedStatement pstmt, List<Object> params) throws SQLException {
		if(params != null && params.size() > 0) {
			for(int i = 0, len = params.size(); i < len; i++) {
				pstmt.setObject(i + 1, params.get(i));
			}
		}
	}
	
	public List<Map<String, Object>> find(String sql, Object ... params) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try {
			con = this.getConnection();
			pstmt = con.prepareStatement(sql);
			this.setParams(pstmt, params);
			rs = pstmt.executeQuery();
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int colCount = rsmd.getColumnCount();
			String[] colNames = new String[colCount];
			for(int i = 0; i < colCount; i++) {
				colNames[i] = rsmd.getColumnName(i + 1).toLowerCase();		//�д�1��ʼ����ȡ������Ĭ�ϴ�д��ת����Сд�������
			}
			
			Map<String, Object> map = null;
			while(rs.next()) {
				map = new HashMap<String, Object>();
				for(String colName : colNames) {
					map.put(colName, rs.getObject(colName));
				}
				
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			this.closeAll(con, pstmt, rs);
		}
		return list;
	}
	
	public int findInt(String sql, Object ... params) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			con = this.getConnection();
			pstmt = con.prepareStatement(sql);
			this.setParams(pstmt, params);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			this.closeAll(con, pstmt, rs);
		}
		return result;
	}
	
	public List<Map<String, Object>> find(String sql, List<Object> params) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try {
			con = this.getConnection();
			pstmt = con.prepareStatement(sql);
			this.setParams(pstmt, params);
			rs = pstmt.executeQuery();
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int colCount = rsmd.getColumnCount();
			String[] colNames = new String[colCount];
			for(int i = 0; i < colCount; i++) {
				colNames[i] = rsmd.getColumnName(i + 1).toLowerCase();		//�д�1��ʼ����ȡ������Ĭ�ϴ�д��ת����Сд�������
			}
			
			Map<String, Object> map = null;
			while(rs.next()) {
				map = new HashMap<String, Object>();
				for(String colName : colNames) {
					map.put(colName, rs.getObject(colName));
				}
				
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			this.closeAll(con, pstmt, rs);
		}
		return list;
	}
	
	public <T> List<T> findObjects(Class<T> c, String sql, Object ... params){
		List<T> list = new ArrayList<T>();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try {
			con = this.getConnection();
			pstmt = con.prepareStatement(sql);
			this.setParams(pstmt, params);
			rs = pstmt.executeQuery();
			//��ȡ����
			ResultSetMetaData rsmd = rs.getMetaData();
			int colCount = rsmd.getColumnCount();
			String[] colNames = new String[colCount];
			for(int i = 0; i < colCount; i++) {
				colNames[i] = rsmd.getColumnName(i + 1).toLowerCase();	
			}
			//��ȡ�������Ͳ�������Ķ�Ӧ��ϵ
			List<Method> methods = this.getSetters(c);
			Map<String, String> types = new HashMap<String, String>();
			Class<?>[] cls = null;
			for(Method method : methods) {
				cls = method.getParameterTypes();
				if(cls != null && cls.length > 0) {
					types.put(method.getName(), cls[0].getSimpleName());
				}
			}
			//ʵ������������б�
			T t = null;
			String methodName = null;
			String mName = null;
			String typeName = null;
			while(rs.next()) {
				t = c.newInstance();
				for(int i = 0; i < colCount; i++) {
					methodName = "set" + colNames[i];
					for(Method method : methods) {
						mName = method.getName();
						if(mName.equalsIgnoreCase(methodName)) {
							typeName = types.get(mName);
							if("int".equals(typeName) || "Integer".equals(typeName)) {
								method.invoke(t, rs.getInt(colNames[i]));
							} else if("double".equalsIgnoreCase(typeName)) {
								method.invoke(t, rs.getDouble(colNames[i]));
							} else if("float".equalsIgnoreCase(typeName)) {
								method.invoke(t, rs.getFloat(colNames[i]));
							} else if("byte[]".equalsIgnoreCase(typeName)) {
								byte[] bt = null;
								Blob blob = rs.getBlob(colNames[i]);
								if(blob != null) {
									bt = blob.getBytes(1, (int)blob.length());
								}
								method.invoke(t, bt);
							} else if("String".equalsIgnoreCase(typeName)) {
								method.invoke(t, rs.getString(colNames[i]));
							} else if("Date".equalsIgnoreCase(typeName)) {
								method.invoke(t, rs.getTimestamp(colNames[i]));
							}
							break;
						}
					}
				}
				list.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}finally {
			this.closeAll(con, pstmt, rs);
		}
		return list;
	}
	public <T> List<T> findObjects(Class<T> c, String sql, List<Object> params){
		List<T> list = new ArrayList<T>();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try {
			con = this.getConnection();
			pstmt = con.prepareStatement(sql);
			this.setParams(pstmt, params);
			rs = pstmt.executeQuery();
			//��ȡ����
			ResultSetMetaData rsmd = rs.getMetaData();
			int colCount = rsmd.getColumnCount();
			String[] colNames = new String[colCount];
			for(int i = 0; i < colCount; i++) {
				colNames[i] = rsmd.getColumnName(i + 1).toLowerCase();	
			}
			//��ȡ�������Ͳ�������Ķ�Ӧ��ϵ
			List<Method> methods = this.getSetters(c);
			Map<String, String> types = new HashMap<String, String>();
			Class<?>[] cls = null;
			for(Method method : methods) {
				cls = method.getParameterTypes();
				if(cls != null && cls.length > 0) {
					types.put(method.getName(), cls[0].getSimpleName());
				}
			}
			//ʵ������������б�
			T t = null;
			String methodName = null;
			String mName = null;
			String typeName = null;
			while(rs.next()) {
				t = c.newInstance();
				for(int i = 0; i < colCount; i++) {
					methodName = "set" + colNames[i];
					for(Method method : methods) {
						mName = method.getName();
						if(mName.equalsIgnoreCase(methodName)) {
							typeName = types.get(mName);
							if("int".equals(typeName) || "Integer".equals(typeName)) {
								method.invoke(t, rs.getInt(colNames[i]));
							} else if("double".equalsIgnoreCase(typeName)) {
								method.invoke(t, rs.getDouble(colNames[i]));
							} else if("float".equalsIgnoreCase(typeName)) {
								method.invoke(t, rs.getFloat(colNames[i]));
							} else if("byte[]".equalsIgnoreCase(typeName)) {
								byte[] bt = null;
								Blob blob = rs.getBlob(colNames[i]);
								if(blob != null) {
									bt = blob.getBytes(1, (int)blob.length());
								}
								method.invoke(t, bt);
							} else if("String".equalsIgnoreCase(typeName)) {
								method.invoke(t, rs.getString(colNames[i]));
							} else if("Date".equalsIgnoreCase(typeName)) {
								method.invoke(t, rs.getTimestamp(colNames[i]));
							}
							break;
						}
					}
				}
				list.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}finally {
			this.closeAll(con, pstmt, rs);
		}
		return list;
	}
	
	public <T> T findObject(Class<T> c, String sql, Object ... params){
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		T t = null;
		
		try {
			con = this.getConnection();
			pstmt = con.prepareStatement(sql);
			this.setParams(pstmt, params);
			rs = pstmt.executeQuery();
			//��ȡ����
			ResultSetMetaData rsmd = rs.getMetaData();
			int colCount = rsmd.getColumnCount();
			String[] colNames = new String[colCount];
			for(int i = 0; i < colCount; i++) {
				colNames[i] = rsmd.getColumnName(i + 1).toLowerCase();	
			}
			//��ȡ�������Ͳ�������Ķ�Ӧ��ϵ
			List<Method> methods = this.getSetters(c);
			Map<String, String> types = new HashMap<String, String>();
			Class<?>[] cls = null;
			for(Method method : methods) {
				cls = method.getParameterTypes();
				if(cls != null && cls.length > 0) {
					types.put(method.getName(), cls[0].getSimpleName());
				}
			}
			//ʵ������������б�
			String methodName = null;
			String mName = null;
			String typeName = null;
			if(rs.next()) {
				t = c.newInstance();
				for(int i = 0; i < colCount; i++) {
					methodName = "set" + colNames[i];
					for(Method method : methods) {
						mName = method.getName();
						if(mName.equalsIgnoreCase(methodName)) {
							typeName = types.get(mName);
							if("int".equals(typeName) || "Integer".equals(typeName)) {
								method.invoke(t, rs.getInt(colNames[i]));
							} else if("double".equalsIgnoreCase(typeName)) {
								method.invoke(t, rs.getDouble(colNames[i]));
							} else if("float".equalsIgnoreCase(typeName)) {
								method.invoke(t, rs.getFloat(colNames[i]));
							} else if("byte[]".equalsIgnoreCase(typeName)) {
								byte[] bt = null;
								Blob blob = rs.getBlob(colNames[i]);
								if(blob != null) {
									bt = blob.getBytes(1, (int)blob.length());
								}
								method.invoke(t, bt);
							} else if("String".equalsIgnoreCase(typeName)) {
								method.invoke(t, rs.getString(colNames[i]));
							} else if("Date".equalsIgnoreCase(typeName)) {
								method.invoke(t, rs.getTimestamp(colNames[i]));
							}
							break;
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}finally {
			this.closeAll(con, pstmt, rs);
		}
		return t;
	}
	public <T> T findObject(Class<T> c, String sql, List<Object> params){
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		T t = null;
		
		try {
			con = this.getConnection();
			pstmt = con.prepareStatement(sql);
			this.setParams(pstmt, params);
			rs = pstmt.executeQuery();
			//��ȡ����
			ResultSetMetaData rsmd = rs.getMetaData();
			int colCount = rsmd.getColumnCount();
			String[] colNames = new String[colCount];
			for(int i = 0; i < colCount; i++) {
				colNames[i] = rsmd.getColumnName(i + 1).toLowerCase();	
			}
			//��ȡ�������Ͳ�������Ķ�Ӧ��ϵ
			List<Method> methods = this.getSetters(c);
			Map<String, String> types = new HashMap<String, String>();
			Class<?>[] cls = null;
			for(Method method : methods) {
				cls = method.getParameterTypes();
				if(cls != null && cls.length > 0) {
					types.put(method.getName(), cls[0].getSimpleName());
				}
			}
			//ʵ������������б�
			String methodName = null;
			String mName = null;
			String typeName = null;
			if(rs.next()) {
				t = c.newInstance();
				for(int i = 0; i < colCount; i++) {
					methodName = "set" + colNames[i];
					for(Method method : methods) {
						mName = method.getName();
						if(mName.equalsIgnoreCase(methodName)) {
							typeName = types.get(mName);
							if("int".equals(typeName) || "Integer".equals(typeName)) {
								method.invoke(t, rs.getInt(colNames[i]));
							} else if("double".equalsIgnoreCase(typeName)) {
								method.invoke(t, rs.getDouble(colNames[i]));
							} else if("float".equalsIgnoreCase(typeName)) {
								method.invoke(t, rs.getFloat(colNames[i]));
							} else if("byte[]".equalsIgnoreCase(typeName)) {
								byte[] bt = null;
								Blob blob = rs.getBlob(colNames[i]);
								if(blob != null) {
									bt = blob.getBytes(1, (int)blob.length());
								}
								method.invoke(t, bt);
							} else if("String".equalsIgnoreCase(typeName)) {
								method.invoke(t, rs.getString(colNames[i]));
							} else if("Date".equalsIgnoreCase(typeName)) {
								method.invoke(t, rs.getTimestamp(colNames[i]));
							}
							break;
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}finally {
			this.closeAll(con, pstmt, rs);
		}
		return t;
	}
	
	
	private <T> List<Method> getSetters(Class<T> c){
		Method[] methods = c.getDeclaredMethods();
		List<Method> list = new ArrayList<Method>();
		for(Method method : methods) {
			if(method.getName().startsWith("set")) {
				list.add(method);
			}
		}
		return list;
	}

	private void closeAll(Connection con, PreparedStatement pstmt, ResultSet rs) {
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}




















