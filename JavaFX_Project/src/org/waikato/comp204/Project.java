package org.waikato.comp204;
//https://www.youtube.com/watch?v=QGGE0WsUslc
//http://stackoverflow.com/questions/32866937/how-to-check-if-textfield-is-empty
//http://docs.oracle.com/javafx/2/layout/builtin_layouts.html
//https://www.youtube.com/watch?v=YtKF1JKtRWM
//https://www.youtube.com/watch?v=lVdtE2BNd88
//http://stackoverflow.com/questions/16225225/javafx-scene-css-doesnt-work
//http://stackoverflow.com/questions/28397700/javafx-how-to-write-text-to-a-new-line-in-a-textarea

import javafx.application.Application;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.Random;


//Creates a progam which allows the user to add an items with its genral information and presents it in a recipt and a orcganized table fashion.
public class Project extends Application
{
    //Gets used to setout the layout of the program
    private static GridPane grid = new GridPane();

    //Recipt is printed on this, (Right side)
    private TextArea textArea = new TextArea();
    //Stores textfields that user inputs information about item
    private TextField[] leftTextFields = new TextField[4];
    //Displays all the items added (Middle)
    private TableView<Item> table = new TableView<Item>();
    //Stores all the added items
    ObservableList<Item> observable = FXCollections.observableArrayList();


    //calls other methods to setup parts of the program at a time, then presents the program
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Project");

        //calling methods which setup parts of the program
        setupGrid();
        setupTextArea();
        setupLabels();
        setupTextField();
        setupButton();
        setupTable();
        //setting size
        Scene scene = new Scene(grid, 900, 600);
        //Adding css file which changes label background,change text-field background color
        scene.getStylesheets().add(
                getClass().getResource("stylesheet.css").toExternalForm()
        );
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

    //Splits window into 3 halfs vertically, each one used for diffrent things (label/textfields, Table, TextArea)
    private void setupGrid()
    {
        grid.setGridLinesVisible(false);

        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);

        //Spliting it into 3 equal sectors (1 = labels 1.5 = textfields , 2 = table, 3 = textarea)
        //1
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(16.665);
        //1.5
        ColumnConstraints column1point5 = new ColumnConstraints();
        column1point5.setPercentWidth(16.665);
        //2
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(33.33);
        //3
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(33.33);
        //Adding them to grid
        grid.getColumnConstraints().addAll(column1,column1point5, column2,column3);
        //Creating 20 rows
        for(int x =0; x < 20; x++)
        {
            RowConstraints row1 = new RowConstraints();
            row1.setPercentHeight(5);
            grid.getRowConstraints().add(row1);
        }
    }

    //Setting up inputs where user enters informaiton about item, create, position
    private void setupTextField()
    {
        TextField Temp;
        //Creating TextBoxes, Positioning them, Storing in list
        for(int x =0; x < 4; x++)
        {
            Temp = new TextField();
            leftTextFields[x] = Temp;
            GridPane.setConstraints(Temp,1,x);
            grid.getChildren().add(Temp);
        }

        //Product quantity/Unit Cost, update the tolal field
        leftTextFields[1].setOnKeyReleased(event -> updateTotal());
        leftTextFields[2].setOnKeyReleased(event -> updateTotal());
        //if user alters total field check if textfield background color needs to be changed
        leftTextFields[3].setOnKeyReleased(event -> TotlalAlterted());
    }

    //Checks what user altered the total textfield too, if correct then make sure textfiedl color is white or else red
    public void TotlalAlterted()
    {
        double CorrectTotal = 0;
        //Getting what user changed total too
        double UserTotal = Double.parseDouble(leftTextFields[3].getText());

        //If input in unit text box and amount text box is added, then calculate if correct
        if(!leftTextFields[1].getText().trim().isEmpty() && !leftTextFields[2].getText().trim().isEmpty())
        {
            //Work out the correct total and round it to 2dp
            int units = Integer.parseInt(leftTextFields[1].getText());
            float amount = Float.parseFloat(leftTextFields[2].getText());

            CorrectTotal = units * amount;
            CorrectTotal = Math.round(CorrectTotal * 100.0)/ 100.0;
            //round what user input was to 2dp
            UserTotal = Math.round(UserTotal * 100.0)/ 100.0;

            System.out.println("Correct Total :" + CorrectTotal);
            System.out.println("User Input Total : " + UserTotal);
        }

        //user total is not correct so change color
        if(CorrectTotal == 0 || UserTotal != CorrectTotal)
        {
            //Change background Color To Red
            System.out.println("Changing Total textbox background color to : RED ");
            leftTextFields[3].setId("text-field-red");
        }
        else
        {
            //Change background Color To Black
            System.out.println("Changing Total textbox background color to : White ");
            leftTextFields[3].setId("text-field-white");
        }
    }

    //Setups up item labels on grid (left hand side)
    private void setupLabels()
    {
        Label labelProductName = new Label("Product Name       : ");
        Label labelProductQuantity = new Label("Product Quantity  : ");
        Label labelUnitCost = new Label("Unit Cost              : ");
        Label labelTotalCost= new Label("Total Cost             : ");
        //positioning labels
        GridPane.setConstraints(labelProductName,0,0);
        GridPane.setConstraints(labelProductQuantity,0,1);
        GridPane.setConstraints(labelUnitCost,0,2);
        GridPane.setConstraints(labelTotalCost,0,3);
        //adding labels to grid
        grid.getChildren().addAll(labelProductName,labelProductQuantity,labelUnitCost,labelTotalCost);
    }

    //Setup where recipt of items will be shown (right hand side)
    private void setupTextArea()
    {
        //User cant edit recipt/textarea,
        textArea.setEditable(false);
        //positioning and setting how many rows it will take then adding
        GridPane.setConstraints(textArea,3,0, 1,20);
        grid.getChildren().add(textArea);
    }

    //creates, positions add button which takes input from textfields and calls a method to save them to list
    private void setupButton()
    {
        Button BAdd = new Button("Add");
        BAdd.setOnAction(event -> {
            //if inputs are valid
            if(TextFieldChecker())
            {
                //extract info out of textfields
                System.out.println("Correct Input");
                String inputItemName = leftTextFields[0].getText();
                int inputItemQuantity = Integer.parseInt(leftTextFields[1].getText());
                float inputUnitCost = Float.parseFloat(leftTextFields[2].getText());
                double inputTotal = Float.parseFloat(leftTextFields[3].getText());
                inputTotal = Math.round(inputTotal * 100.0) / 100.0;
                //save item to list
                saveItem(inputItemName,inputItemQuantity,inputUnitCost, (float)inputTotal);
            }
        });
        //adding button to grid
        GridPane.setConstraints(BAdd,1,4);
        grid.getChildren().add(BAdd);
    }

    //sets out table (middle) , sets its coloums and links them to list and adds it to grid
    private void setupTable()
    {
        //creating colums
        table.setEditable(true);
        TableColumn itemName = new TableColumn("Name");
        TableColumn itemQuantity = new TableColumn("Quantity");
        TableColumn itemUnit = new TableColumn("Cost");
        TableColumn itemTotal = new TableColumn("Total Cost");
        itemName.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        itemQuantity.setCellValueFactory(new PropertyValueFactory<Item, String>("quantity"));
        itemUnit.setCellValueFactory(new PropertyValueFactory<Item, String>("unitCost"));
        itemTotal.setCellValueFactory(new PropertyValueFactory<Item, String>("total"));

        //giving table the list to get information from
        table.setItems(observable);
        //adding colums to table
        table.getColumns().addAll(itemName,itemQuantity, itemUnit, itemTotal);

        grid.add(table,2,0,1,20);
    }

    //Checks all textfieldsm if they are all valid
    private boolean TextFieldChecker()
    {
        System.out.println("TextField Checker");
        //if item name exists, unit is an float, amount is an int
        if(!leftTextFields[0].getText().trim().isEmpty() && CheckIfNum(leftTextFields[1].getText(),false) && CheckIfNum(leftTextFields[2].getText(),true) && CheckIfNum(leftTextFields[3].getText(),true))
        {
            System.out.println("True");
            return true;
        }
        System.out.println("False");
        return false;
    }

    //Creates a item using passed in parameters and saves in list
    private void saveItem(String ProductName, int Quantity, float Cost, float Total)
    {
        System.out.println("Save Item");
        Item temp = new Item(ProductName, Quantity, Cost, Total);
        //adding to list
        observable.add(temp);
        updateReceipt();
        //clearing textfields to "", setting total txtfield background to whie incase it was red
        clearTextFields();
        leftTextFields[3].setId("text-field-white");
    }

    //when unit or amount textbox value is altered, this method alters total textbox value accordingly
    private void updateTotal()
    {
        //if input is valid (0-9)
        if(CheckIfNum(leftTextFields[2].getText(), true) == true && CheckIfNum(leftTextFields[1].getText(),false) == true) {
            int quantity = Integer.parseInt(leftTextFields[1].getText());
            Double price = Double.parseDouble(leftTextFields[2].getText());
            //Calculate total (2dp)
            Double Total = quantity * price;
            Total = Math.round(Total * 100.0) / 100.0;
            //set it as textfieldvalue
            leftTextFields[3].setText("" + Total);
        }
        else
        {
            leftTextFields[3].setText("0.0");
        }
        //since we just corrected it
        leftTextFields[3].setId("text-field-white");
    }

    //update textarea when add button is pressed
    private void updateReceipt()
    {
        //clearing textarea
        textArea.setText("");
        //going through loop adding every item in recipt like fashion
        for(int x= 0; x < observable.size(); x++)
        {
            Item temp = observable.get(x);
            textArea.appendText(temp.getName());
            textArea.appendText("\n");
            //working out total value of item is correct
            double Total = temp.getTotal();
            Total = Math.round(Total * 100.0) / 100.0;

            double CorrectTotal = temp.getUnitCost() * temp.getQuantity();
            CorrectTotal = Math.round(CorrectTotal * 100.0) / 100.0;
            //if correct add present normally other wise add '*' at end
            if(CorrectTotal == Total)
            {
                textArea.appendText("$" + temp.getUnitCost() + " Each  X" + temp.getQuantity() + "   $" + temp.getTotal());
            }
            else
            {
                textArea.appendText("$" + temp.getUnitCost() + " Each  X" + temp.getQuantity() + "   $" + temp.getTotal()+"*");
            }
            textArea.appendText("\n");
        }
        textArea.appendText("\n");
        textArea.appendText("\n");
        textArea.appendText("------------------------------");
        textArea.appendText("\n");
        //Total of all items Totals
        textArea.appendText("Grand Total : $" + getTotalsTotal());
    }

    //Adds the totals of all items in list and returns it
    private double getTotalsTotal()
    {
        double totalsTotal = 0;
        //looping through list adding up totals
        for(int x =0; x < observable.size(); x++)
        {
            totalsTotal = totalsTotal + observable.get(x).getTotal();
        }
        //rounding totals total to 2dp
        totalsTotal = Math.round(totalsTotal * 100.0)/100.0;
        return totalsTotal;
    }

    //Simple method which sets all text-fields values to ""
    private void clearTextFields()
    {
        leftTextFields[0].setText("");
        leftTextFields[1].setText("");
        leftTextFields[2].setText("");
        leftTextFields[3].setText("");
    }

    //retusn true if passed in value is an number, where being float or int
    private static boolean CheckIfNum(String num, boolean isitPrice)
    {
        try{
            //if its a double
            if(isitPrice)
            {
                //try turning it to a float
                float x = Float.parseFloat(num);
                return true;
            }
            else
            {
                int x = Integer.parseInt(num);
                return true;
            }
        }
        catch(Exception e)
        {
            System.out.println("Error caused in 'CheckIfNum' method:  " + e);
        }
        return false;
    }

    //Class used to store bits of information about an item
    public static class Item {
        public final SimpleStringProperty name;
        public final SimpleIntegerProperty quantity;
        public final SimpleFloatProperty unitCost;
        public final SimpleFloatProperty total;


        public Item(String name, int quantity, float unitCost, float total) {
            this.name = new SimpleStringProperty(name);
            this.quantity = new SimpleIntegerProperty(quantity);
            this.unitCost = new SimpleFloatProperty(unitCost);
            this.total = new SimpleFloatProperty(total);
        }

        public String getName() { return this.name.get();}
        public int getQuantity() { return this.quantity.get();}
        public float getUnitCost() { return this.unitCost.get();}
        public float getTotal(){return this.total.get();}
    }
}



