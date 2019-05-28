package com.esper.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.esperJava.model.BaseDataModel;

public class MySQLAccess {

	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	final private String host = "localhost:3306";
	final private String user = "root";
	final private String passwd = "root";

	public List<BaseDataModel> readDataBase() throws Exception {
		List<BaseDataModel> bdmlist=new ArrayList<BaseDataModel>();
		try {

			Class.forName("com.mysql.jdbc.Driver");

			connect = DriverManager
					.getConnection("jdbc:mysql://" + host + "/esper?" + "user=" + user + "&password=" + passwd);
			System.out.println("connection established");

			statement = connect.createStatement();

			resultSet = statement.executeQuery("select usr_name,ec_Activity,ec_Outcome,source_ip from esper.employee");
			//writeResultSet(resultSet);
		
			while(resultSet.next()) {
				System.out.println(resultSet.getString(1));
				System.out.println(resultSet.getString(2));
				System.out.println(resultSet.getString(3));
				BaseDataModel bdm=new BaseDataModel();
				bdm.setUsr_name(resultSet.getString(1));
				
				bdm.setEc_Activity(resultSet.getString(2));
				bdm.setEc_Outcome(resultSet.getString(3));
				bdm.setSource_ip(resultSet.getString(4));
				bdmlist.add(bdm);
			}
			/*for(BaseDataModel b:bdmlist) {
			System.out.println(b.getUsr_name() +"--"+b.getSource_ip()+""+b.getSource_ip());	
			
			}*/

		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}
		return bdmlist;

	}


/*	private void writeResultSet(ResultSet resultSet) throws SQLException {

		while (resultSet.next()) {

			String user = resultSet.getString("usr_name");
			String login = resultSet.getString("ec_Activity");
			String summary = resultSet.getString("ec_Outcome");

			String ip = resultSet.getString("source_ip");
			System.out.println("usr_name: " + user);
			System.out.println("ec_Activity: " + login);
			System.out.println("ec_Outcome: " + summary);
			System.out.println("source_ip: " + ip);
		}
	}*/

	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}

}
