package oneIndia;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.sql.*;
import javafx.scene.control.Button;
import javax.swing.JButton;


interface dao {
   public static final String DB_URL  ="jdbc:mysql://localhost:3306/"+ "oneIndia?zeroDateTimeBehavior=CONVERT_TO_NULL";
   public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
   public static final String USER = "root";
   public static final String PASS = "root";
}


public class OneIndia extends Application implements dao
{
    int ck=0;
    TableView<Person> table = new TableView<Person>();
    public final ObservableList<Person> data
            = FXCollections.observableArrayList(
                    new Person("Bengaluru", "Bengaluru International Airport", "BLR"),
                    new Person("Chennai", "Kamraj International Airport", "MAA"),
                    new Person("New Delhi", "Indira Gandhi Airport", "DEL"),
                    new Person("Amritsar", "Sri Guru Ram Dass", "LUH"),
                    new Person("Ahmedabad", "Sardar Vallabhbhai Patel International Airport", "AMD"),
                    new Person("Indore", "Devi Ahilya Bai Holkar Airport", "IDR"),
                    new Person("Bhopal", "Raja Bhoj Intenational Airport", "BHO"),
                    new Person("Jammu", "Satvari Airport", "IXJ"),
                    new Person("Mumbai", "Chhatrapati Shivaji International Airport", "BOM")
            );

    @Override
    public void start(Stage stage)
    {
        Scene scene = new Scene(new Group());
        stage.setTitle("Table View");
        stage.setWidth(500);
        stage.setHeight(750);
        stage.setResizable(true);
        final Label label = new Label("OneIndia Airlines");
        label.setFont(new Font("Arial", 20));
        label.setTranslateX(150);
           
        ChoiceBox<String> choiceBox = new ChoiceBox();
        choiceBox.setTranslateX(-10);
        
        TextField textField = new TextField();
        textField.setPromptText("Enter Departure City");
        textField.setTranslateX(-40);
        
        TextField textField1 = new  TextField();
        textField1.setPromptText("Enter Arrival City");
        textField1.setTranslateX(20);
        
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.TOP_CENTER);
        hBox.getChildren().addAll(textField , choiceBox, textField1);
       
        
        DatePicker dates = new DatePicker(LocalDate.now());
        dates.setTranslateX(-45);
        
        final Label label1 = new Label("No. of Passengers");
        label1.setFont(new Font("Arial", 15));
        label1.setTranslateX(20);
        ComboBox combo = new ComboBox();
        combo.setTranslateX(30);
        for(int i=0;i<=10;i++)
        {
            combo.getItems().add(i);
        }
        combo.getSelectionModel().select(2);
        HBox hb = new HBox();
        hb.setAlignment(Pos.TOP_CENTER);
        hb.getChildren().addAll(dates , label1, combo);
        
        
        Button jb = new Button("Submit");
        Button jb1 = new Button("Clear");
        
        table.setEditable(false);
      
        //table.setPrefWidth(900);

        TableColumn firstNameCol = new TableColumn("City");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("firstName"));

        TableColumn lastNameCol = new TableColumn("Airport");
        lastNameCol.setMinWidth(260);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("lastName"));

        TableColumn emailCol = new TableColumn("IATA");
        emailCol.setMinWidth(100);
        emailCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("email"));

        FilteredList<Person> flPerson = new FilteredList(data, p -> true);//Pass the data to a filtered list
        table.setItems(flPerson);//Set the table's items using the filtered list
        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);
        

        choiceBox.getItems().addAll("City", "Airport", "IATA");
        choiceBox.setValue("City");

        textField.setOnKeyReleased(keyEvent ->
        {
            switch (choiceBox.getValue())//Switch on choiceBox value
            {
                case "City":
                    flPerson.setPredicate(p -> p.getFirstName().toLowerCase().contains(textField.getText().toLowerCase().trim()));//filter table by first name
                    break;
                case "Airport":
                    flPerson.setPredicate(p -> p.getLastName().toLowerCase().contains(textField.getText().toLowerCase().trim()));//filter table by first name
                    break;
                case "IATA":
                    flPerson.setPredicate(p -> p.getEmail().toLowerCase().contains(textField.getText().toLowerCase().trim()));//filter table by first name
                    break;
            }
        });
        
        
        final VBox vbox = new VBox();
        vbox.setSpacing(30);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label,hBox, hb, table,jb,jb1);
         ((Group) scene.getRoot()).getChildren().addAll(vbox);
         stage.setScene(scene);
         stage.show();
         
         
         
         
         
        
        table.setOnMousePressed(key -> {
            if(key.getClickCount() >= 2)
            {
                
                if(ck==0)
                {
                    String  sec = table.getItems().get(table.getSelectionModel().getSelectedIndex()).getLastName();
                    textField.setText(sec);
                    ck=1;
                }
                else
                {
                     String  sec = table.getItems().get(table.getSelectionModel().getSelectedIndex()).getLastName();
                    textField1.setText(sec);
                    ck=0;
                }
                    
            }
        });
        
        

        choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
        {//reset table and textfield when new choice is selected
            if (newVal != null)
            {
                textField.setText("");
                flPerson.setPredicate(null);//This is same as saying flPerson.setPredicate(p->true);
            }
        });
        
   
        jb.setOnAction(key -> {
            try {
                    Class.forName(DRIVER);
                    Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
                    Statement stmt = con.createStatement(); 
                    String g = textField.getText();
                    String g1 = textField1.getText();
                    String g2 = dates.getValue().toString();
                    String g3 = combo.getSelectionModel().getSelectedItem().toString();
                    System.out.println(g + g1 + g2 + g3);
                    stmt.executeUpdate("insert into oneindia values(' " + g + " ' , ' " + g1 + " ' , ' " + g2 + " ' , ' " + g3 + " ')");
         stmt.close();
         con.close();
         System.out.println("submitted");
      } catch (ClassNotFoundException | SQLException ex) {
          System.out.println(ex);
      }
       });
        
        jb1.setOnAction(key -> {
            textField1.setText("");
            textField.setText("");
            combo.getSelectionModel().select(0);
            dates.setValue(null);
        });
        
    }

    public static class Person
    {

        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;
        private final SimpleStringProperty email;

        private Person(String fName, String lName, String email)
        {
            this.firstName = new SimpleStringProperty(fName);
            this.lastName = new SimpleStringProperty(lName);
            this.email = new SimpleStringProperty(email);
        }

        public String getFirstName()
        {
            return firstName.get();
        }

        public void setFirstName(String fName)
        {
            firstName.set(fName);
        }

        public String getLastName()
        {
            return lastName.get();
        }

        public void setLastName(String fName)
        {
            lastName.set(fName);
        }

        public String getEmail()
        {
            return email.get();
        }

        public void setEmail(String fName)
        {
            email.set(fName);
        }
    }
    
     public static void main(String[] args)
    {
        launch(args);
    }
}