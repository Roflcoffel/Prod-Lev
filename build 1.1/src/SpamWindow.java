package se.plushogskolan.ju15.javafx;

import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.stage.*;
import javafx.util.converter.IntegerStringConverter;
import se.plushogskolan.ju15.beans.PersonBean;
import javafx.scene.layout.*;


/**
 * Creates a table of personal data from a file.
 * @version 1.1
 */
public class SpamWindow extends Application {

	TableView<PersonBean> table;
	private ObservableList<PersonBean> personData = FXCollections.observableArrayList();
	SortedList<PersonBean> sortedData = null;
	FilteredList<PersonBean> filteredData = null;

	public static void main(String[] args) {

		System.out.println("Launching JavaFX application.");

		// Start the JavaFX application by calling launch().
		launch(args);
	}

	// Override the init() method.
	public void init() {
		System.out.println("Inside the init() method.");
		loadData();
		/*
		 * // read file Person2 person1 = new Person2("Pelle", "Olsson",
		 * "20040102-1234"); // person1.setFirstName("Pelle"); //
		 * person1.setLastName("Olsson"); // person.setAge(20);
		 * personData.add(person1); Person2 person2 = new Person2("Sven",
		 * "Svensson", "19901022-3456"); // person2.setFirstName("Sven"); //
		 * person2.setLastName("Svensson"); // person.setAge(20);
		 * personData.add(person2); Person2 person3 = new Person2("Anna",
		 * "Johansson", "19950810-2345"); // person3.setFirstName("Anna"); //
		 * person3.setLastName("Johansson"); // person.setAge(20);
		 * personData.add(person3); Person2 person4 = new Person2("Bengt",
		 * "Persson", "19600314-5678"); // person4.setFirstName("Bengt"); //
		 * person4.setLastName("Persson"); // person.setAge(20);
		 * personData.add(person4);
		 */
	}

	// Override the start() method.
	public void start(Stage myStage) {

		System.out.println("Inside the start() method.");

		table = new TableView<PersonBean>();
		table.setEditable(true);
		// Give the stage a title.
		myStage.setTitle("Person Spam App");
		filteredData = new FilteredList<>(personData, p -> true);
		sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(table.comparatorProperty());

		table.setItems(sortedData);
		table.setPrefWidth(500);

		table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		TableColumn<PersonBean, String> fullNameCol = new TableColumn<PersonBean, String>("Full Name");
		fullNameCol.setCellValueFactory(new PropertyValueFactory("fullName"));
		fullNameCol.setCellFactory(TextFieldTableCell.<PersonBean> forTableColumn());
		fullNameCol.setOnEditCommit((CellEditEvent<PersonBean, String> t) -> {
			((PersonBean) t.getTableView().getItems().get(t.getTablePosition().getRow())).setFullName(t.getNewValue());
		});
		
		TableColumn<PersonBean, String> emailCol = new TableColumn<PersonBean, String>("Email");
		emailCol.setCellValueFactory(new PropertyValueFactory("email"));
		emailCol.setCellFactory(TextFieldTableCell.<PersonBean> forTableColumn());
		emailCol.setOnEditCommit((CellEditEvent<PersonBean, String> t) -> {
			((PersonBean) t.getTableView().getItems().get(t.getTablePosition().getRow())).setEmail(t.getNewValue());
		});
		TableColumn<PersonBean, String> genderCol = new TableColumn<PersonBean, String>("Gender");
		genderCol.setCellValueFactory(new PropertyValueFactory("gender"));
		genderCol.setCellFactory(TextFieldTableCell.<PersonBean> forTableColumn());
		genderCol.setOnEditCommit((CellEditEvent<PersonBean, String> t) -> {
			((PersonBean) t.getTableView().getItems().get(t.getTablePosition().getRow())).setGender(t.getNewValue());
		});
		TableColumn<PersonBean, Integer> ageCol = new TableColumn<PersonBean, Integer>("Age");
		ageCol.setCellValueFactory(new PropertyValueFactory("age"));
		ageCol.setCellFactory(TextFieldTableCell.<PersonBean, Integer> forTableColumn(new IntegerStringConverter()));
		ageCol.setOnEditCommit((CellEditEvent<PersonBean, Integer> t) -> {
			((PersonBean) t.getTableView().getItems().get(t.getTablePosition().getRow())).setAge(t.getNewValue());
		});
		table.getColumns().setAll(fullNameCol, emailCol, genderCol, ageCol);

		
		Button allButton = new Button("All");
		allButton.setMinSize(100, 10);
		allButton.setOnAction((ActionEvent) -> {
			System.out.println("Show all!");
			filteredData.setPredicate(showAll());
		});

		Button maleButton = new Button("Male");
		maleButton.setMinSize(100, 10);
		maleButton.setOnAction((ActionEvent) -> {
			System.out.println("Show male");
			filteredData.setPredicate(isMale());
		});
		
		Button femaleButton = new Button("Female");
		femaleButton.setMinSize(100, 10);
		femaleButton.setOnAction((ActionEvent) -> {
			System.out.println("Show female");
			filteredData.setPredicate(isFemale());
		});

		Button over18Button = new Button("Over 18");
		over18Button.setMinSize(100, 10);
		over18Button.setOnAction((ActionEvent) -> {
			filteredData.setPredicate(personbean -> personbean.getAge() > 17);
		});

		Button addButton = new Button("Add row");
		addButton.setMinSize(100, 10);
		addButton.setOnAction((ActionEvent) -> {
			System.out.println("Add me!");
			filteredData.setPredicate(showAll());
			addRow();
		});

		Button removeButton = new Button("Delete row");
		removeButton.setMinSize(100, 10);
		removeButton.setOnAction((ActionEvent) -> {
			System.out.println("delete me!");
			List items = new ArrayList(table.getSelectionModel().getSelectedItems());
			personData.removeAll(items);
			table.getSelectionModel().clearSelection();
		});
		// Create a root node. In this case, a flow layout
		// is used, but several alternatives exist.
		FlowPane rootNode = new FlowPane();

		rootNode.getChildren().addAll(table, allButton, maleButton, femaleButton, over18Button, addButton,
				removeButton);
		// Create a scene.
		Scene myScene = new Scene(rootNode, 800, 800);
		
		// Background Color
		rootNode.setStyle("-fx-background-color: green");

		// Set the scene on the stage.
		myStage.setScene(myScene);

		// Show the stage and its scene.
		myStage.show();
	}
/*
	private void printIt() {
		Iterator<Person2> it = personData.iterator();
		while (it.hasNext()) {
			System.out.println(it.next().getGender());
		}
	}
*/
	/** Description of loadDate())
	 * loads data from mydata.txt.
	 */

	public void loadData() {
		try {
			//System.out.println(Paths.get("mydata.txt").toAbsolutePath());
			List<String> lines = Files.readAllLines(Paths.get("mydata.txt"), Charset.forName("UTF-8"));
			String data[] = new String[5];
			for (String line : lines) {
				data = line.split(",");
				if(!line.isEmpty()){
					PersonBean p = new PersonBean(data[0] + " " + data[1], data[2], data[3], new Integer(data[4]));
					personData.add(p);
				}
			}
			System.out.println("Loaded " + lines.size() + " rows of data.");
		} catch (Exception e) {
			System.out.println("There was a problem loading the file:" + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	/** Description of writeIt()
	* Saves personBean in a file with "," as a separator.
	*/

	public void writeIt() {
		try {
			PrintWriter writer = new PrintWriter("mydata.txt", "UTF-8");
			Iterator<PersonBean> it = personData.iterator();
			String data[] = new String[2];
			while (it.hasNext()) {
				PersonBean p = it.next();
				data = p.getFullName().split(" ");
				writer.println(data[0] + "," + data[1] + "," + p.getEmail() + "," + p.getGender() + "," + p.getAge());
			}
			writer.close();
		} catch (Exception e) {
			System.out.println("There was a problem saving the file:" + e.getMessage());
		}
	}


	private void addRow() {
		System.out.println("new row.");
		personData.add(new PersonBean());
	}

	public static Predicate<PersonBean> showAll() {
		return p -> true;
	}
	
	
	public static Predicate<PersonBean> isMale() {
		return p -> p.getGender().equalsIgnoreCase("Male");
	}

	public static Predicate<PersonBean> isFemale() {
		return p -> p.getGender().equalsIgnoreCase("Female");
	}

	public static Predicate<PersonBean> over18() {
		return p -> p.getAge() > 17;
	}

	// Override the stop() method.
	public void stop() {
		System.out.println("Inside the stop() method.");
		writeIt();
	}
}