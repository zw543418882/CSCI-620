package bigdata.projection.group9;
/*
 * CSCI620-INTRODUCTION OF BIG DATA
 * PROJECT PHASE 2
 * GROUP #9
 * AUTHOR: WEI ZENG
 * */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MainView {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

//********************************** Connect to the database *******************//	
		String driverClass = "com.mysql.cj.jdbc.Driver";
		String databaseName = "imdb";
		String serverIp = "localhost";
		String username = "root";
		String password = "P@ssw0rd";
		String jdbcUrl = "jdbc:mysql://" + serverIp + ":3306/" + databaseName
				+ "?serverTimezone=Asia/Shanghai&useSSL=true";
		try {
			Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
			Class.forName(driverClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
//********************************** End ***************************************//

//********************************** Build The Interface *********************//
		// create main frame
		JFrame jf = new JFrame("Movie Search System");
		jf.setSize(700, 600);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// create main panel
		JPanel panel = new JPanel();
		panel.setLayout(null);

		// label 1 for tips
		JLabel label11 = new JLabel("genre");
		label11.setFont(new Font("Dialog", 1, 18));
		label11.setBounds(20, 5, 140, 20);
		panel.add(label11);

		// input box 1
		JTextField textField11 = new JTextField(8);
		textField11.setBounds(10, 30, 120, 50);
		textField11.setFont(new Font(null, Font.PLAIN, 20));
		panel.add(textField11);

		// label 2
		JLabel label12 = new JLabel("runtime");
		label12.setFont(new Font("Dialog", 1, 18));
		label12.setBounds(170, 5, 100, 20);
		panel.add(label12);

		// input box 2
		JTextField textField12 = new JTextField(8);
		textField12.setBounds(160, 30, 120, 50);
		textField12.setFont(new Font(null, Font.PLAIN, 20));
		panel.add(textField12);

		// label 3
		JLabel label13 = new JLabel("startYear");
		label13.setFont(new Font("Dialog", 1, 18));
		label13.setBounds(320, 5, 100, 20);
		panel.add(label13);

		// input box 3
		JTextField textField13 = new JTextField(8);
		textField13.setBounds(310, 30, 120, 50);
		textField13.setFont(new Font(null, Font.PLAIN, 20));
		panel.add(textField13);

		// label 4
		JLabel label14 = new JLabel("Click query1");
		label14.setFont(new Font("Dialog", 1, 14));
		label14.setBounds(440, 45, 100, 20);
		panel.add(label14);

		// table to show query results
		Object[] columnNames = { "tconst" };
		Object[][] rowData = {};
		JTable table = new JTable(rowData, columnNames);
		table.setBounds(20, 150, 500, 1000);
		panel.add(table);

		final JTable[] temp = new JTable[10];

		// query 1 button
		JButton btn1 = new JButton("query1");
		btn1.setFont(new Font(null, Font.PLAIN, 20));
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String condition1 = textField11.getText();
				String condition2 = textField12.getText();
				String condition3 = textField13.getText();
				String sqlString = "select primaryTitle from movie where genre like " + condition1 + " and runtime = "
						+ condition2 + " and startYear = " + condition3 + ";";
				int index = 0;
				String[][] row = new String[200][1];
				try {
					Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
					Statement statement = conn.createStatement();
					ResultSet rSet = statement.executeQuery(sqlString);

					while (rSet.next()) {
						String name = rSet.getString("primaryTitle");
						row[index][0] = name;
						index++;
					}
				} catch (Exception e2) {

				}

				panel.remove(table);
				JTable table1 = new JTable(row, columnNames);
				table1.setBounds(20, 150, 500, 400);
				panel.add(table1);

				label14.setText("Click query2");
				label11.setText("primarytitle");
				label12.setText("birthyear");
				label13.setText("N/A");
				temp[0] = table1;

				panel.updateUI();

			}
		});
		btn1.setBounds(10, 90, 95, 50);
		panel.add(btn1);

		// query 2 button
		JButton btn2 = new JButton("query2");
		btn2.setFont(new Font(null, Font.PLAIN, 20));
		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String condition1 = textField11.getText();
				String condition2 = textField12.getText();
				String sqlString = "select distinct A.PrimaryName from movie as M, actororactress as A where M.primarytitle = "
						+ condition1 + " and A.birthyear = " + condition2 + ";";
				int index = 0;
				String[][] row = new String[200][1];
				try {
					Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
					Statement statement = conn.createStatement();
					ResultSet rSet = statement.executeQuery(sqlString);
					while (rSet.next()) {
						if (index > 100) {
							break;
						} else {
							String name = rSet.getString("PrimaryName");
							row[index][0] = name;
							index++;
						}
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}

				panel.remove(temp[0]);
				Object[] columnNames2 = { "PrimaryName" };
				JTable table2 = new JTable(row, columnNames2);
				table2.setBounds(20, 150, 500, 400);
				panel.add(table2);
				label14.setText("Click query3");
				label11.setText("primaryName");
				label12.setText("deathyear");
				label13.setText("N/A");
				panel.updateUI();
				temp[0] = table2;

			}
		});
		btn2.setBounds(110, 90, 95, 50);
		panel.add(btn2);

		// query 3 button
		JButton btn3 = new JButton("query3");
		btn3.setFont(new Font(null, Font.PLAIN, 20));
		btn3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String condition1 = textField11.getText();
				String condition2 = textField12.getText();
				String sqlString = "select distinct A.primaryName from actororactress as A where A.primaryName like "
						+ condition1 + " and A.deathyear is " + condition2 + ";";
				int index = 0;
				String[][] row = new String[200][1];
				try {
					Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
					Statement statement = conn.createStatement();
					ResultSet rSet = statement.executeQuery(sqlString);
					while (rSet.next()) {
						if (index > 100) {
							break;
						} else {
							String name = rSet.getString("primaryName");
							row[index][0] = name;
							index++;
						}
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}

				panel.remove(temp[0]);
				Object[] columnNames2 = { "primaryName" };
				JTable table3 = new JTable(row, columnNames2);
				table3.setBounds(20, 150, 500, 400);
				panel.add(table3);
				label14.setText("Click query4");
				label11.setText("birthyear");
				label12.setText("deathyear");
				label13.setText("N/A");
				panel.updateUI();
				temp[0] = table3;
			}
		});
		btn3.setBounds(210, 90, 95, 50);
		panel.add(btn3);

		// query 4 button
		JButton btn4 = new JButton("query4");
		btn4.setFont(new Font(null, Font.PLAIN, 20));
		btn4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String condition1 = textField11.getText();
				String condition2 = textField12.getText();
				String sqlString = "select distinct A.primaryName from actororactress as A, Director as D where A.nconst = D.nconst and A.deathyear between " + condition1 + " and " + condition2 
						+ ";";
				int index = 0;
				String[][] row = new String[200][1];
				try {
					Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
					Statement statement = conn.createStatement();
					ResultSet rSet = statement.executeQuery(sqlString);
					while (rSet.next()) {
						if (index > 100) {
							break;
						} else {
							String name = rSet.getString("primaryName");
							row[index][0] = name;
							index++;
						}
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}

				panel.remove(temp[0]);
				Object[] columnNames2 = { "primaryName" };
				JTable table4 = new JTable(row, columnNames2);
				table4.setBounds(20, 150, 500, 400);
				panel.add(table4);
				label14.setText("Click query5");
				label11.setText("genre");
				label12.setText("averageRating");
				label13.setText("N/A");
				panel.updateUI();
				temp[0] = table4;
			}
		});
		btn4.setBounds(310, 90, 95, 50);
		panel.add(btn4);

		// query 5 button
		JButton btn5 = new JButton("query5");
		btn5.setFont(new Font(null, Font.PLAIN, 20));
		btn5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String condition1 = textField11.getText();
				String condition2 = textField12.getText();
				String sqlString = "select distinct M.primaryTitle from movie as M, Rates as R where M.tconst = R.tconst and M.genre = " + condition1 + " and " + "R.averageRating > " + condition2  
						+ ";";
				int index = 0;
				String[][] row = new String[200][1];
				try {
					Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
					Statement statement = conn.createStatement();
					ResultSet rSet = statement.executeQuery(sqlString);
					while (rSet.next()) {
						if (index > 100) {
							break;
						} else {
							String name = rSet.getString("primaryTitle");
							row[index][0] = name;
							index++;
						}
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}

				panel.remove(temp[0]);
				Object[] columnNames2 = { "primaryTitle" };
				JTable table5 = new JTable(row, columnNames2);
				table5.setBounds(20, 150, 500, 400);
				panel.add(table5);
				label14.setText("Click query6");
				label11.setText("startYear");
				label12.setText("runtime");
				label13.setText("genre");
				panel.updateUI();
				temp[0] = table5;

			}
		});
		btn5.setBounds(410, 90, 95, 50);
		panel.add(btn5);

		// query 6 button
		JButton btn6 = new JButton("query6");
		btn6.setFont(new Font(null, Font.PLAIN, 20));
		btn6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String condition1 = textField11.getText();
				String condition2 = textField12.getText();
				String condition3 = textField13.getText();
				String sqlString = "select distinct M.primaryTitle from movie as M where M.startYear = " + condition1 + " and M.runtime > " + condition2 + " and M.genre like " 
						+ condition3 + ";";
				int index = 0;
				String[][] row = new String[200][1];
				try {
					Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
					Statement statement = conn.createStatement();
					ResultSet rSet = statement.executeQuery(sqlString);
					while (rSet.next()) {
						if (index > 100) {
							break;
						} else {
							String name = rSet.getString("primaryTitle");
							row[index][0] = name;
							index++;
						}
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}

				panel.remove(temp[0]);
				Object[] columnNames2 = { "primaryTitle" };
				JTable table6 = new JTable(row, columnNames2);
				table6.setBounds(20, 150, 500, 400);
				panel.add(table6);
				label14.setText("Finish");
				label11.setText("");
				label12.setText("");
				label13.setText("");
				panel.updateUI();
				temp[0] = table6;

			}
		});
		btn6.setBounds(510, 90, 95, 50);
		panel.add(btn6);

		//
		jf.setContentPane(panel);
		jf.setVisible(true);
//********************************** End ***************************************//

	}

}
