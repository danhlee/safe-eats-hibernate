import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.lang.String;
import java.util.ArrayList;

public class DBOperations {
	
   public static boolean attemptLogin(Connection conn, String user, String pass) throws SQLException {
		
		String sql = "SELECT * FROM Users WHERE username='" + user + "' AND pass=SHA('"+ pass + "')";
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(sql);
		
		String dbUser = "", dbPass = "";
		int inspectorID = -1;

		if (result.next()) {
			System.out.println("Login Successful!");
			conn.close();
			return true;
		}
		else {
			System.out.println("Login Failed!");
			conn.close();
			return false;
		}
		
	}
	public static boolean inspectionExists(Connection conn, int inspectionID) throws SQLException {
		String sql = "SELECT inspection_ID FROM Inspections WHERE inspection_ID='" + inspectionID + "'";
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(sql);
		result.last();
		
		return result.getRow() == 1; 
	}
	
	// check to see if restaurant exists
	public static boolean restaurantExists(Connection conn, String restName, String restAddress) throws SQLException {
		String sql = "SELECT COUNT(*) FROM Restaurants WHERE restName='" + restName + "' AND restAddress = '" + restAddress + "'";
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(sql);
		result.last();
		int numOfEntries = result.getInt("COUNT(*)");
		
		return numOfEntries >= 1;
		//return result.getRow() == 1; 
	}

	public static boolean restaurantExists(Connection conn, int restID) throws SQLException {
		String sql = "SELECT COUNT(*) FROM Restaurants WHERE restID='" + restID + "'";
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(sql);
		result.last();
		int numOfEntries = result.getInt("COUNT(*)");
		
		return numOfEntries >= 1;
	}
	
   
	public static int getInspectorID(Connection conn, String username) throws SQLException {
		String sql = "SELECT * FROM Users WHERE username='"+username+"'";
   	
   	Statement statement = conn.createStatement();         // not executing statment multiple times and no Parameters, use Statement object because use minimal resources
   	ResultSet result = statement.executeQuery(sql);       // executeQuery is used for select statements while executeUpdate is used for DML
      result.last();
		int inspectorID = result.getInt(5);
		return inspectorID;
	}
   
	public static int getInspectorID(Connection conn, int restID) throws SQLException {
		String sql = "SELECT COUNT(*) FROM Inspections WHERE restID= "+restID;
   	int inspectorID;
   	Statement statement = conn.createStatement();         // not executing statment multiple times and no Parameters, use Statement object because use minimal resources
   	ResultSet result = statement.executeQuery(sql);       // executeQuery is used for select statements while executeUpdate is used for DML
      result.next();
      
		int count = result.getInt("COUNT(*)");
      if (count == 0) {
         return -1;
      }
      else {
         String sql2 = "SELECT * FROM Inspections WHERE restID="+restID;
      	Statement statement2 = conn.createStatement();         // not executing statment multiple times and no Parameters, use Statement object because use minimal resources
      	ResultSet result2 = statement2.executeQuery(sql2);       // executeQuery is used for select statements while executeUpdate is used for DML
         result2.last();
         inspectorID = result2.getInt("inspectorID");
      }
      return inspectorID;
	}
	/*
   public static void main(String[] args) {
		try {
			// field selects inspection_ID
			Connection conn = Connect.getConnection();
			int inspectionID = 3;
			int currentInspectorID = 3;
			
			//int prevInspectorID = getInspectorIDbyInspectionID(conn, inspectionID);
			String sql = "SELECT * FROM Inspections WHERE inspection_ID= "+inspectionID;
			Statement statement = conn.createStatement();
			ResultSet results = statement.executeQuery(sql);
			results.next();
			int prevInspectorID = results.getInt("inspectorID");

			System.out.println("prev inspector(should be 2) = " + prevInspectorID);
			String risk = "HIGH";
			String result = "FAIL";
			int rowsAffected = DBOperations.update(conn, currentInspectorID, prevInspectorID, risk, result, inspectionID);
			
		}
		catch (SQLException ex){}  
	}
	*/
	public static int getInspectorIDbyInspectionID(Connection conn, int inspectionID) throws SQLException {
		String sqlCheckExists = "SELECT COUNT(*) FROM Inspections WHERE inspection_ID= "+inspectionID;
		Statement statementCheckExists = conn.createStatement();
		ResultSet resultsCheckExists = statementCheckExists.executeQuery(sqlCheckExists);
		resultsCheckExists.next();
		int count = resultsCheckExists.getInt("COUNT(*)");
		if (count == 0) {
			return -1;
		} 
		else {
			String sql = "SELECT * FROM Inspections WHERE inspection_ID= "+inspectionID;
			Statement statement = conn.createStatement();
			ResultSet results = statement.executeQuery(sql);
			
			results.next();
			int prevInspectorID = results.getInt("inspectorID");
	      
	      return prevInspectorID;
		}
		

	}
		
	public static int update(Connection conn, int currentInspectorID, int prevInspectorID, String risk, String results, int inspectionID) throws SQLException {
		// get previous inspectorID for decrement
      

      System.out.println("prev inspector ID = " + prevInspectorID);
      int rowsAffected;
      
      if (!inspectionExists(conn, inspectionID)) {
         rowsAffected = 0;
      }
      else {
         String sql = "UPDATE Inspections SET inspectorID=?, risk=?, results=? WHERE inspection_ID=?";
      	PreparedStatement statement = conn.prepareStatement(sql);
      
      	statement.setInt(1, currentInspectorID);
      	statement.setString(2, risk);
      	statement.setString(3, results);
      	statement.setInt(4, inspectionID);
   		   
      	rowsAffected = statement.executeUpdate();         // execute and returns number of rows affected

         if (rowsAffected > 0) {
            updateInspectionCount(conn, prevInspectorID);
            updateInspectionCount(conn, currentInspectorID);
      	   System.out.println("Inspection ID: " +inspectionID+ " successfully updated by Inspector ID: " + currentInspectorID);
      	}
      }
      
      return rowsAffected;
	}

	public static void updateInspectionCount(Connection conn, int inspectorID) throws SQLException {
		// select inspections for an inspector using inspectorID
		String sql1 = "SELECT * FROM Inspectors WHERE inspectorID=" + inspectorID;
		Statement numInspectionsStatement = conn.createStatement();
		ResultSet result = numInspectionsStatement.executeQuery(sql1);
		result.next();
		int numInspections = result.getInt(2);
		
		// count total number of inspections for the inspector in question
		String sqlCheck = "SELECT COUNT(*) FROM Inspections WHERE inspectorID = " + inspectorID;
		Statement statementCheck = conn.createStatement();
		ResultSet resultCheck = statementCheck.executeQuery(sqlCheck);
		resultCheck.last();
		numInspections = resultCheck.getInt("COUNT(*)");

		// update the numInspections for inspector with count(*) total
		String sql2 = "UPDATE Inspectors SET numInspections=? WHERE inspectorID=?";
   	PreparedStatement statement = conn.prepareStatement(sql2);
   	statement.setInt(1, numInspections); 
   	statement.setInt(2, inspectorID);
	
   	int rowsAffected = statement.executeUpdate();         // execute and returns number of rows affected
   	
      if (rowsAffected > 0) {
   	   System.out.println("Successfully updated number of inspections for inspector# "+ inspectorID +"!");
   	}
	}

	
	public static int getRestID(Connection conn, String restName, String restAddress) throws SQLException{
		String sql = "SELECT * FROM Restaurants WHERE restName = '" +restName+ "' AND restAddress = '" +restAddress+ "'";
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(sql);
		result.last();
		int restID = result.getInt("restID");
		return restID;
	}
	
	
   public static int insert(Connection conn, int inspectorID, String restName, String restAddress, String risk, String results) throws SQLException {
		// NEW restaurant
		// add restaurant to Restaurants table
		// add inspection to Inspections
		if (!restaurantExists(conn, restName, restAddress)) {
			String sql = "INSERT INTO Restaurants (restName, restAddress) VALUES (?, ?)";
	    	
	   	PreparedStatement restStatement = conn.prepareStatement(sql);
			restStatement.setString(1, restName);
			restStatement.setString(2, restAddress);
			int rowsAffected = restStatement.executeUpdate();
			
			if (rowsAffected > 0) {  
         	System.out.println(restName +" was inserted successfully into Restaurants table!");
   		}
			int restID = getRestID(conn, restName, restAddress);
			
			// insert new inspection for new restaurant
			String sql2 = "INSERT INTO Inspections (inspectorID, restID, risk, results) "
	   	            + "VALUES (?, ?, ?, ?)";
	    
	   	PreparedStatement statement = conn.prepareStatement(sql2); 
	   	statement.setInt(1, inspectorID); 	
	   	statement.setInt(2, restID);
			statement.setString(3, risk);  
	   	statement.setString(4, results); 
	   	int rowsAffected2 = statement.executeUpdate();         // takes sql PreparedStatement an returns
	                                                            // either (1) the row count for SQL Data Manipulation Language (DML) statements 
	                                                            // or (2) 0 for SQL statements that return nothing
			// if insert was successful update inspector count
			if (rowsAffected > 0) {
				updateInspectionCount(conn, inspectorID);
			}
			
			return rowsAffected;
		}
		else {
			return 0;
		}	
   }


	// pointer before exception
	public static int deleteInspection(Connection conn, int inspectionID) throws SQLException {
   	int rowsAffected;
		// find inspector for inspectionID
		if (!inspectionExists(conn, inspectionID)) {
			rowsAffected = 0;
		}
		else {
			// get old inspector number (for update afterwards)
			String sqlFindInspector = "SELECT * FROM Inspections WHERE inspection_ID=" + inspectionID;
			Statement statementFindInspector = conn.createStatement();
			ResultSet resultFindInspector = statementFindInspector.executeQuery(sqlFindInspector);
			resultFindInspector.next();
			int inspectorID = resultFindInspector.getInt(2);
			System.out.println(inspectorID);
			
			// delete inspection entry using inspectionID
			String sql = "DELETE FROM Inspections WHERE inspection_ID=?";
	   	PreparedStatement statement = conn.prepareStatement(sql);
	   	statement.setInt(1, inspectionID);    
	   	rowsAffected = statement.executeUpdate();
	      
			
	   	if (rowsAffected > 0) {
				// update old inspector's count (decrement)
				updateInspectionCount(conn, inspectorID);
	   	}
		}
		return rowsAffected;
   }
	
	public static int deleteRestaurant(Connection conn, int restID, int inspectorID) throws SQLException {
   	int  rowsAffected;
		if (!restaurantExists(conn, restID)) {
			rowsAffected = 0;
		}
		else {
			String sql = "DELETE FROM Restaurants WHERE restID=?";
	   	PreparedStatement statement = conn.prepareStatement(sql);
	   
	   	statement.setInt(1, restID);  
	   	rowsAffected = statement.executeUpdate();
	      
	   	if (rowsAffected > 0) {
				updateInspectionCount(conn, inspectorID);
	   	}
		}
		return rowsAffected;

   }

   public static void selectByRestName(Connection conn, String restName) throws SQLException {
   	String restNameCAPS = restName.toUpperCase();
		String sql = "SELECT i.inspection_ID, r.restID, r.restName, restAddress, inspectionDate, results, risk, inspectorID FROM Inspections i, Restaurants r "
			+ "WHERE r.restName LIKE '%"+restNameCAPS+"%' AND r.restID = i.restID;";
   	
   	Statement statement = conn.createStatement();
   	ResultSet result = statement.executeQuery(sql);
		String[] columnNames = {"inspection_ID",
										"restID",
										"restName",
										"restAddress",
										"inspectionDate",
										"results",
										"risk",
                              "inspectorID"};
		System.out.println("****************************************************************************************************************************************************");
		System.out.printf("%-4s %-7s %-40s %-30s %-23s %-4s %-6s %-4s", columnNames[0], columnNames[1], columnNames[2], columnNames[3], columnNames[4], columnNames[5], columnNames[6], columnNames[7]);
      System.out.println();
		
   	while (result.next()) {
 	      int a = result.getInt(1);
 	      int b = result.getInt(2);
 	      String c = result.getString(3);
 	      String d = result.getString(4);
	      String e = result.getString(5);
	      String f = result.getString(6);
	      String g = result.getString(7);
			String h = result.getString(8);
			System.out.printf("%-13d %-7d %-40s %-30s %-23s %-7s %-6s %-4s\n", a, b, c, d, e, f, g, h);
   	}
		System.out.println("****************************************************************************************************************************************************");

   }

   public static void selectByResults(Connection conn, SelectionByResults selection) throws SQLException {
		String results = selection.results;
		String riskLowString = "risk = '" + selection.riskLow + "' ";
		String riskMedString = "risk = '" +  selection.riskMed + "' ";
		String riskHighString = "risk = '" +  selection.riskHigh + "' ";
		
		String risks = " AND ( ";
		
		if (selection.riskLow != null) {
			risks += riskLowString;
		}
		
		if (selection.riskMed != null && selection.riskLow != null) {
			risks += "OR " + riskMedString;
		}
		else if (selection.riskMed == null) {
			
		}
		else {
			risks += riskMedString;
		}
		
		if (selection.riskHigh != null && (selection.riskLow != null || selection.riskMed != null)) {
			risks += "OR " + riskHighString;
		}
		else if (selection.riskHigh == null) {
		
		}
		else {
			risks += riskHighString;
		}
		risks += ")";
		
		if (selection.riskLow == null && selection.riskMed == null && selection.riskHigh == null) {
			risks = "";
		}
		
		String sql = "SELECT i.inspection_ID, r.restID, r.restName, restAddress, inspectionDate, results, risk, inspectorID FROM Inspections i, Restaurants r "
			+ "WHERE r.restID = i.restID AND results = '" +results+ "'" + risks;

   	Statement statement = conn.createStatement();
   	ResultSet result = statement.executeQuery(sql);
		String[] columnNames = {"inspection_ID",
										"restID",
										"restName",
										"restAddress",
										"inspectionDate",
										"results",
										"risk",
                              "inspectorID"};
		System.out.println("****************************************************************************************************************************************************");
		System.out.printf("%-4s %-7s %-40s %-30s %-23s %-4s %-6s %-4s", columnNames[0], columnNames[1], columnNames[2], columnNames[3], columnNames[4], columnNames[5], columnNames[6], columnNames[7]);
      System.out.println();
		
   	while (result.next()) {
 	      int a = result.getInt(1);
 	      int b = result.getInt(2);
 	      String c = result.getString(3);
 	      String d = result.getString(4);
	      String e = result.getString(5);
	      String f = result.getString(6);
	      String g = result.getString(7);
			String h = result.getString(8);
			System.out.printf("%-13d %-7d %-40s %-30s %-23s %-7s %-6s %-4s\n", a, b, c, d, e, f, g, h);
   	}
		System.out.println("****************************************************************************************************************************************************");
   }
   
   public static void selectInspectors(Connection conn) throws SQLException {
      String sql = "SELECT i.inspectorID, username, numInspections FROM Inspectors i, Users u WHERE i.inspectorID = u.inspectorID";
      Statement statement = conn.createStatement();
      ResultSet result = statement.executeQuery(sql);
      
      System.out.println("****************************************************************************************************************************************************");
		System.out.printf("%-20s %-20s %-10s", "inspectorID", "username", "numInspections");
      System.out.println();
      while (result.next()) {
         int inspectorID = result.getInt(1);
         String username = result.getString(2);
         int numInspections = result.getInt(3);
         
         System.out.printf("%-20d %-20s %-10d \n", inspectorID, username, numInspections);
      }
      System.out.println("****************************************************************************************************************************************************");      
      
   }
}