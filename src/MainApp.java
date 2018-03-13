import java.sql.Connection;
import java.util.Scanner;

import javafx.scene.layout.HBox;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javafx.scene.paint.Color;
import javafx.event.*;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.CheckBox;
import java.sql.SQLException;


public class MainApp extends Application {
	@Override
	public void start(Stage primaryStage) {
		
		// create Login pane and set properties
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.TOP_CENTER);
									   // top rt  bot left
		pane.setPadding(new Insets(20, 0, 0, 0));
		pane.setHgap(5.5);
		pane.setVgap(5.5);
		
// Create nodes for Login pane
		Label userLabel = new Label("Username:");
		Label passLabel = new Label("Password:");
		
		
		TextField userField = new TextField();
		PasswordField passField = new PasswordField();
		Button loginBtn = new Button("Login");
		// wrong user PASS text
		final Text loginFailText = new Text();
		
		
		// nodes --> pane
		// add nodes to Login pane
		pane.setPrefSize(700, 400);
		pane.add(userLabel, 0, 0);
		pane.add(userField, 1, 0);
		pane.add(passLabel, 0, 1);
		pane.add(passField, 1, 1);
		
		//loginText and bad login text go in separate Hbox that goes inside gridpane		
		HBox loginHbox = new HBox(10);
		loginHbox.setAlignment(Pos.BOTTOM_RIGHT);
		loginHbox.getChildren().add(loginBtn);
		pane.add(loginHbox, 1, 2);
		pane.add(loginFailText, 1, 3);
		
		// pane --> scene
		// add login pane to login scene
		Scene loginScene = new Scene(pane);
		primaryStage.setTitle("Login Page");
		primaryStage.setScene(loginScene);
		primaryStage.show();
		
		
		// inspector pane
		GridPane inspectPane = new GridPane();
		inspectPane.setAlignment(Pos.TOP_CENTER);
									   // top  rt    bot     left
		inspectPane.setPadding(new Insets(15, 15, 15, 15));
		inspectPane.setHgap(5.5);
		inspectPane.setVgap(5.5);
		
// inspection page nodes --> pane
		Text userName = new Text();

		Label restNameLabel = new Label("Restaurant Name:");
		Label restAddressLabel = new Label("Address:");
		Label riskLabel = new Label("Risk:");
		Label resultsLabel = new Label("Results:");
		
		TextField restNameField = new TextField("");
		TextField restAddressField = new TextField("");
		
		// toggle buttons for risk
		ToggleGroup riskToggleButtons = new ToggleGroup();
	   RadioButton lowBtn = new RadioButton("Low");
	   RadioButton medBtn = new RadioButton("Med");
		RadioButton highBtn = new RadioButton("High");
		
		
		// results toggle buttons
		ToggleGroup resultsToggleButtons = new ToggleGroup();
	   RadioButton PASSBtn = new RadioButton("Pass");
	   RadioButton FAILBtn = new RadioButton("Fail");
		
		// add risk radio buttons
		lowBtn.setToggleGroup(riskToggleButtons);
		medBtn.setToggleGroup(riskToggleButtons);
		highBtn.setToggleGroup(riskToggleButtons);
		HBox riskBox = new HBox(20, lowBtn, medBtn, highBtn);
		
	   // add results radio buttons
		PASSBtn.setToggleGroup(resultsToggleButtons);
		FAILBtn.setToggleGroup(resultsToggleButtons);
		HBox resultsBox = new HBox(20, PASSBtn, FAILBtn);
		
		// insert inspection button
		Button insertBtn = new Button("Insert");
		
		// insert notification
		Text insertNote = new Text();
		
// update nodes
      Label updateLabelInspectionID = new Label("Inspection ID:");
      TextField updateTextFieldInspectionID = new TextField("");
      Label riskLabelUpdate = new Label("Risk:");
		Label resultsLabelUpdate = new Label("Results:");
      // toggle buttons for risk
		ToggleGroup riskToggleButtonsUpdate = new ToggleGroup();
	   RadioButton lowBtnUpdate = new RadioButton("Low");
	   RadioButton medBtnUpdate = new RadioButton("Med");
		RadioButton highBtnUpdate = new RadioButton("High");
		
		
		// results toggle buttons
		ToggleGroup resultsToggleButtonsUpdate = new ToggleGroup();
	   RadioButton PASSBtnUpdate = new RadioButton("Pass");
	   RadioButton FAILBtnUpdate = new RadioButton("Fail");
		
		// add risk radio buttons
		lowBtnUpdate.setToggleGroup(riskToggleButtons);
		medBtnUpdate.setToggleGroup(riskToggleButtons);
		highBtnUpdate.setToggleGroup(riskToggleButtons);
		HBox riskBoxUpdate = new HBox(20, lowBtnUpdate, medBtnUpdate, highBtnUpdate);
		lowBtnUpdate.setSelected(true);
	   // add results radio buttons
		PASSBtnUpdate.setToggleGroup(resultsToggleButtonsUpdate);
		FAILBtnUpdate.setToggleGroup(resultsToggleButtonsUpdate);
		HBox resultsBoxUpdate = new HBox(20, PASSBtnUpdate, FAILBtnUpdate);
      PASSBtnUpdate.setSelected(true);
      
      
      Button updateBtn = new Button("Update");
      Text updateNote = new Text();
      
		// CRUD box and buttons
		Button insertInspectBtn = new Button("Insert / Update");
		Button deleteInspectBtn = new Button("Delete");
		Button makeSelectBtn = new Button("Select");
		HBox crudBox = new HBox(10, insertInspectBtn, deleteInspectBtn, makeSelectBtn);
      crudBox.setAlignment(Pos.CENTER);
      
		Button insertInspectBtn2 = new Button("Insert / Update");
		Button deleteInspectBtn2 = new Button("Delete");
		Button makeSelectBtn2 = new Button("Select");
		HBox crudBox2 = new HBox(10, insertInspectBtn2, deleteInspectBtn2, makeSelectBtn2);
	   crudBox2.setAlignment(Pos.CENTER);
      
		Button insertInspectBtn3 = new Button("Insert / Update");
		Button deleteInspectBtn3 = new Button("Delete");
		Button makeSelectBtn3 = new Button("Select");
		HBox crudBox3 = new HBox(10, insertInspectBtn3, deleteInspectBtn3, makeSelectBtn3);
		crudBox3.setAlignment(Pos.CENTER);
     
// insert / update page pane
		inspectPane.setPrefSize(700, 400);
		inspectPane.add(crudBox, 1, 0);
		inspectPane.add(restNameLabel, 0, 1);
		inspectPane.add(restNameField, 1, 1);
		inspectPane.add(restAddressLabel, 0, 2);
		inspectPane.add(restAddressField, 1, 2);
		inspectPane.add(riskLabel, 0, 3);
		inspectPane.add(riskBox, 1, 3);
		inspectPane.add(resultsLabel, 0, 4);
		inspectPane.add(resultsBox, 1, 4);
		inspectPane.add(insertBtn, 2, 5);
		inspectPane.add(insertNote, 1, 5);
      

      inspectPane.add(updateLabelInspectionID, 0, 6);
      inspectPane.add(updateTextFieldInspectionID, 1, 6);
      inspectPane.add(riskLabelUpdate, 0, 7);
		inspectPane.add(riskBoxUpdate, 1, 7);
		inspectPane.add(resultsLabelUpdate, 0, 8);
		inspectPane.add(resultsBoxUpdate, 1, 8);
		inspectPane.add(updateBtn, 2, 9);
		inspectPane.add(updateNote, 1, 9);
      
		
// deletion nodes
		GridPane deletePane = new GridPane();
		deletePane.setPadding(new Insets(15, 15, 15, 15));
		deletePane.setAlignment(Pos.TOP_CENTER);
		deletePane.setHgap(5.5);
		deletePane.setVgap(5.5);
		
		
		Label inspectionIDDeleteLabel = new Label("Inspection ID:");
		TextField inspectionIDDeleteField = new TextField("");
		Label restIDDeleteLabel = new Label("Restaurant ID:");
		TextField restIDDeleteField = new TextField("");
		
		// delete buttons		
		Button deleteBtn = new Button("Delete");
		HBox deleteBtnHbox = new HBox(10, deleteBtn);
		deleteBtnHbox.setAlignment(Pos.CENTER_RIGHT);
		Button deleteBtn2 = new Button("Delete");
		HBox deleteBtnHbox2 = new HBox(10, deleteBtn2);
		deleteBtnHbox2.setAlignment(Pos.CENTER_RIGHT);

		Text deleteRestaurantText = new Text();
		deleteRestaurantText.setText("Delete Restaurant and Inspection Entries");
		deleteRestaurantText.setFill(Color.DODGERBLUE);
		
		Text deleteInspectionText = new Text();
		deleteInspectionText.setText("Delete Inspection Entry");
		deleteInspectionText.setFill(Color.DODGERBLUE);
		
		// delete notification
		Text deleteNote = new Text();
		
// delete page pane
		deletePane.setPrefSize(700, 400);
		deletePane.add(crudBox2, 1, 0);
		
		deletePane.add(deleteInspectionText, 1, 1);
		deletePane.add(inspectionIDDeleteLabel, 0, 2);
		deletePane.add(inspectionIDDeleteField, 1, 2);
		
		deletePane.add(deleteBtnHbox, 2, 3);
		
		deletePane.add(deleteRestaurantText, 1, 4);
		deletePane.add(restIDDeleteLabel, 0, 5);
		deletePane.add(restIDDeleteField, 1, 5);
		
		deletePane.add(deleteBtnHbox2, 2, 6);
		deletePane.add(deleteNote, 1, 6);
				
		
// selection nodes
		GridPane selectPane = new GridPane();
		selectPane.setPadding(new Insets(15, 15, 15, 15));
		selectPane.setAlignment(Pos.TOP_CENTER);
		selectPane.setHgap(5.5);
		selectPane.setVgap(5.5);
		
		Label restNameLabel2 = new Label("Restaurant Name:");
		TextField restNameField2 = new TextField("");
		
		Label resultsLabel2 = new Label("Inspection Results:");
		ToggleGroup resultsToggleButtons2 = new ToggleGroup();
	   RadioButton PASSBtn2 = new RadioButton("PASS");
	   RadioButton FAILBtn2 = new RadioButton("FAIL");
		PASSBtn2.setSelected(true);
		PASSBtn2.setToggleGroup(resultsToggleButtons2);
		FAILBtn2.setToggleGroup(resultsToggleButtons2);
		HBox resultsHbox = new HBox(20, PASSBtn2, FAILBtn2);
		resultsHbox.setPadding(new Insets(15, 15, 15, 15));
		resultsHbox.setAlignment(Pos.CENTER_LEFT);
		
		Label riskLabel2 = new Label("Safety Risk:");
		CheckBox checkBoxLow = new CheckBox("LOW");
		CheckBox checkBoxMed = new CheckBox("MED");
		CheckBox checkBoxHigh = new CheckBox("HIGH");
		HBox riskBox2 = new HBox(20, checkBoxLow, checkBoxMed, checkBoxHigh);
		riskBox2.setPadding(new Insets(15, 15, 15, 15));
		riskBox2.setAlignment(Pos.CENTER_LEFT);
		
		Button selectBtn1 = new Button("Select");
		Button selectBtn2 = new Button("Select");
		
		// select notification
		Text selectNote = new Text();
		
		// select labels
		Text selectByRestaurant = new Text("Select by Restaurant (If left blank, all restaurants will be selected!)");
		selectByRestaurant.setFill(Color.DODGERBLUE);
		Text selectByResults = new Text("Select by Results & Risk (If left blank, no filter will be applied!)");
		selectByResults.setFill(Color.DODGERBLUE);
      
      // inspector info button
      Button selectInspectors = new Button("Select Inspectors");
		HBox selectInspectorsBox = new HBox(selectInspectors);
      selectInspectorsBox.setAlignment(Pos.CENTER);
// select page pane
		selectPane.setPrefSize(700, 400);
		selectPane.add(crudBox3, 1, 0);
		selectPane.add(selectByRestaurant, 1, 1);
		selectPane.add(restNameLabel2, 0, 2);
		selectPane.add(restNameField2, 1, 2);
		selectPane.add(selectBtn1, 2, 3);
		
		selectPane.add(selectByResults, 1, 4);
		selectPane.add(resultsLabel2, 0, 5);
		selectPane.add(resultsHbox, 1, 5);
		selectPane.add(riskLabel2, 0, 6);
		selectPane.add(riskBox2, 1, 6);
		selectPane.add(selectBtn2, 2, 7);
		
		selectPane.add(selectNote, 1, 7);
      
      selectPane.add(selectInspectorsBox, 1, 8);

// Scenes
		deletePane.setGridLinesVisible(false);
		Scene inspectorScene = new Scene(inspectPane);
		Scene deleteScene = new Scene(deletePane);
		Scene selectScene = new Scene(selectPane);
		

		
// login triggers
		loginBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
         public void handle(ActionEvent e) {
				try {
					Connection conn = Connect.getConnection();
					String u = userField.getText();
					String p = passField.getText();
					boolean loginSuccessful = DBOperations.attemptLogin(conn, u, p);
					if(loginSuccessful) {
						userName.setText(u);
						primaryStage.setScene(inspectorScene);
						primaryStage.setTitle("Inspector Page: Insert Inspection");
					}
					else {
						loginFailText.setFill(Color.FIREBRICK);
						loginFailText.setText("Incorrect username or password!");
					}
					conn.close();
				}
				catch (SQLException ex) {
				
				}
			}
      });
		
// insert page triggers		
		//CRUD box Triggers
		insertInspectBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
         public void handle(ActionEvent e) {
				primaryStage.setScene(inspectorScene);
				primaryStage.setTitle("Inspector Page: Insert Inspection");
			}
      });
		
		deleteInspectBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
         public void handle(ActionEvent e) {
				primaryStage.setScene(deleteScene);
				primaryStage.setTitle("Inspector Page: Delete Inspection");
			}
      });
		
		makeSelectBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
         public void handle(ActionEvent e) {
				primaryStage.setScene(selectScene); // selectScene
				primaryStage.setTitle("Inspector Page: Make Selection");
			}
      }); 
		
		//insert button trigger
		insertBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
         public void handle(ActionEvent e) {
				try {
					Connection conn = Connect.getConnection();
					int inspectorID = DBOperations.getInspectorID(conn, userName.getText());
					String restName = restNameField.getText().toUpperCase();
					String restAddress = restAddressField.getText().toUpperCase();
					String risk;
					String result;
					
					
										
					if(lowBtn.isSelected()) {
						risk = "LOW";
					}
					else if (medBtn.isSelected()){
						risk = "MED";
					}
					else {
						risk = "HIGH";
					}
					
					if(PASSBtn.isSelected()) {
						result = "PASS";
					}
					else {
						result = "FAIL";
					}
					
					// make sure no fields blank
					if ((PASSBtn.isSelected() == false && FAILBtn.isSelected() == false) || (restNameField.getText().equals("")) || restAddressField.getText().equals("") 
					 || (lowBtn.isSelected() == false && medBtn.isSelected() == false && highBtn.isSelected() == false)) {
						System.out.println("One or more fields are blank. Please fill in all fields!");
						insertNote.setFill(Color.FIREBRICK);
						insertNote.setText("One or more fields are blank.\nPlease fill in all fields!");
					}
					else {
						int rowsAffected = DBOperations.insert(conn, inspectorID, restName, restAddress, risk, result);
						if (rowsAffected > 0) {
						System.out.println("Inspection inserted successfully!");
						insertNote.setFill(Color.FORESTGREEN);
						insertNote.setText("Inspection inserted successfully!!");
						}
						else {
							System.out.println("This restaurant already exists. Please try again!");
							insertNote.setFill(Color.FIREBRICK);
							insertNote.setText("This restaurant already exists. Please try again!");
						}
						
						//reset fields
						restNameField.setText("");
						restAddressField.setText("");
						lowBtn.setSelected(false);
						medBtn.setSelected(false);
						highBtn.setSelected(false);
						PASSBtn.setSelected(false);
						FAILBtn.setSelected(false);
					}				
					conn.close();

					
				}
				catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
      });
      updateBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
         public void handle(ActionEvent e) {
				try {
					Connection conn = Connect.getConnection();
					int currentInspectorID = DBOperations.getInspectorID(conn, userName.getText());
               int inspectionID;
					String risk;
					String result;
					
					
										
					if(lowBtnUpdate.isSelected()) {
						risk = "LOW";
					}
					else if (medBtnUpdate.isSelected()){
						risk = "MED";
					}
					else {
						risk = "HIGH";
					}
					
					if(PASSBtnUpdate.isSelected()) {
						result = "PASS";
					}
					else {
						result = "FAIL";
					}
					
					// make sure no fields blank
					if ((PASSBtnUpdate.isSelected() == false && FAILBtnUpdate.isSelected() == false) || updateTextFieldInspectionID.getText().equals("") 
					 || (lowBtnUpdate.isSelected() == false && medBtnUpdate.isSelected() == false && highBtnUpdate.isSelected() == false)) {
						System.out.println("One or more fields are blank. Please fill in all fields!");
						updateNote.setFill(Color.FIREBRICK);
						updateNote.setText("One or more fields are blank.\nPlease fill in all fields!");
					}
					else if (!IntCheck.isInteger(updateTextFieldInspectionID.getText())) {
						System.out.println("Inspection ID must be an integer!");
						updateNote.setFill(Color.FIREBRICK);
						updateNote.setText("Inspection ID must be an integer!");                  
               }
               else {
                  inspectionID = Integer.parseInt(updateTextFieldInspectionID.getText());
                  int prevInspectorID = DBOperations.getInspectorIDbyInspectionID(conn, inspectionID);
						if (prevInspectorID == -1) {
							System.out.println("Inspection ID " + inspectionID + " does not exist\nPlease try again!");
   						updateNote.setFill(Color.FIREBRICK);
   						updateNote.setText("Inspection ID " + inspectionID + " does not exist\nPlease try again!");
						}
						else {
							int rowsAffected = DBOperations.update(conn, currentInspectorID, prevInspectorID, risk, result, inspectionID);

							if (rowsAffected > 0) {
	   						System.out.println("Inspection inserted successfully!");
	   						updateNote.setFill(Color.FORESTGREEN);
	   						updateNote.setText("Inspection inserted successfully!!");
							}
							else {
								System.out.println("This restaurant does not exist. Please try again!");
								updateNote.setFill(Color.FIREBRICK);
								updateNote.setText("This restaurant does not exist. Please try again!");
							}
						}

						
						//reset fields
						updateTextFieldInspectionID.setText("");
					}				
					conn.close();

					
				}
				catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
      });
// delete page triggers
		// CRUD box 2
		insertInspectBtn2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
         public void handle(ActionEvent e) {
				primaryStage.setScene(inspectorScene);
				primaryStage.setTitle("Inspector Page: Insert Inspection");
			}
			
      });
		
		deleteInspectBtn2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
         public void handle(ActionEvent e) {
				primaryStage.setScene(deleteScene);
				primaryStage.setTitle("Inspector Page: Delete Inspection");
			}
      });
		
		makeSelectBtn2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
         public void handle(ActionEvent e) {
				primaryStage.setScene(selectScene);
				primaryStage.setTitle("Inspector Page: Make Selection");
			}
      }); 
		
		
		// delete page trigger 
		deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
         public void handle(ActionEvent e) {
				try {
					Connection conn = Connect.getConnection();
					int inspectionID;
					
					if (inspectionIDDeleteField.getText().length() == 0 || !IntCheck.isInteger(inspectionIDDeleteField.getText())) {
							System.out.println("You must enter an integer in the Inspection ID field!");
							deleteNote.setFill(Color.FIREBRICK);
							deleteNote.setText("You must enter an integer in the Inspection ID field!");
					}
					else {
						inspectionID = Integer.parseInt(inspectionIDDeleteField.getText());
						int rowsAffected = DBOperations.deleteInspection(conn, inspectionID);
					
						if (rowsAffected > 0) {
							System.out.println("InspectionID# " + inspectionID + " deleted successfully!");
							deleteNote.setFill(Color.FORESTGREEN);
							deleteNote.setText("InspectionID# " + inspectionID + " deleted successfully!");
						}
						else {
							System.out.println("InspectionID# " + inspectionID +" does NOT exist. Please try again!");
							deleteNote.setFill(Color.FIREBRICK);
							deleteNote.setText("InspectionID# " + inspectionID +" does NOT exist. Please try again!");
						}
					}
					
					conn.close();
					inspectionIDDeleteField.setText("");
				}
				catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
      }); 
		
		// delete restaurant + corresponding inspections (via cascade trigger)
		deleteBtn2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
         public void handle(ActionEvent e) {
				try {
					Connection conn = Connect.getConnection();				
					int restID;
					int inspectorID;
					if (restIDDeleteField.getText().length() == 0 || !IntCheck.isInteger(restIDDeleteField.getText())) {
							System.out.println("You must enter an integer in the Restaurant ID field!");
							deleteNote.setFill(Color.FIREBRICK);
							deleteNote.setText("You must enter an integer in the Restaurant ID field!");
					}
					else {
						restID = Integer.parseInt(restIDDeleteField.getText());
                  inspectorID = DBOperations.getInspectorID(conn, restID);
                  if (inspectorID == -1) {
                     System.out.println("RestaurantID# " + restID + " does NOT exist. Please try again!");
							deleteNote.setFill(Color.FIREBRICK);
							deleteNote.setText("RestaurantID# " + restID + " does NOT exist. Please try again!");
                  }
                  else {

                     
                     int rowsAffected = DBOperations.deleteRestaurant(conn, restID, inspectorID);
					
   						if (rowsAffected > 0) {
   							System.out.println("Successfully deleted "+ rowsAffected + " restaurant with restID# "+ restID +"\nand all of its inspection entries!");
   							deleteNote.setFill(Color.FORESTGREEN);
   							deleteNote.setText("Successfully deleted "+ rowsAffected + " restaurant with restID# "+ restID +"\nand all of its inspection entries!");
   						}
   						else {
   							System.out.println("RestaurantID# " + restID + " does NOT exist. Please try again!");
   							deleteNote.setFill(Color.FIREBRICK);
   							deleteNote.setText("RestaurantID# " + restID + " does NOT exist. Please try again!");
   						}

                  }
                  
											}
					conn.close();
					restIDDeleteField.setText("");
				}
				catch (SQLException ex) {
					ex.printStackTrace();
				}				
			}
      }); 
		
// select page triggers
		// CRUD box 3
		insertInspectBtn3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
         public void handle(ActionEvent e) {
				primaryStage.setScene(inspectorScene);
				primaryStage.setTitle("Inspector Page: Insert Inspection");
            
			}
      });
		
		deleteInspectBtn3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
         public void handle(ActionEvent e) {
				primaryStage.setScene(deleteScene);
				primaryStage.setTitle("Inspector Page: Delete Inspection");
			}
      });
		
		makeSelectBtn3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
         public void handle(ActionEvent e) {
				primaryStage.setScene(selectScene);
				primaryStage.setTitle("Inspector Page: Make Selection");
			}
      }); 
		
		selectBtn1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
         public void handle(ActionEvent e) {
				try {
					Connection conn = Connect.getConnection();
					String restName = restNameField2.getText();
					
					// ArrayList contains selection objects (each object is a ROW of data)
					DBOperations.selectByRestName(conn, restName);
					conn.close();
					restNameField2.setText("");
				}
				catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
      });
		
		selectBtn2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
         public void handle(ActionEvent e) {
				try {
					Connection conn = Connect.getConnection();
					String results = null; 
					String riskLow = null;
					String riskMed = null;
					String riskHigh = null;
					
					// if PASS is selected
					if (PASSBtn2.isSelected()) {
						results = "PASS";
					}
					// if FAIL is selected
					else {
						results = "FAIL";
					}
					
					if (checkBoxLow.isSelected()) {
						riskLow = "LOW";
					}
					if (checkBoxMed.isSelected() == true) {
						riskMed = "MED";
					}
					if (checkBoxHigh.isSelected() == true) {
						riskHigh = "HIGH";
					}
					SelectionByResults selection = new SelectionByResults(results, riskLow, riskMed, riskHigh);
					
					DBOperations.selectByResults(conn, selection);
				
					conn.close();
					inspectionIDDeleteField.setText("");
				}
				catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
      }); 
      
		selectInspectors.setOnAction(new EventHandler<ActionEvent>() {
			@Override
         public void handle(ActionEvent e) {
				try {
					Connection conn = Connect.getConnection();
					DBOperations.selectInspectors(conn);
				
					conn.close();
				}
				catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
      });	
	}
	

	public static void main(String[] args) {
		Application.launch(args);
	}

}