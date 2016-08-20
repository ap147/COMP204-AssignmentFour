package org.waikato.comp204;
//https://www.youtube.com/watch?v=QGGE0WsUslc
//http://stackoverflow.com/questions/32866937/how-to-check-if-textfield-is-empty
//http://docs.oracle.com/javafx/2/layout/builtin_layouts.html
//https://www.youtube.com/watch?v=YtKF1JKtRWM
import javafx.application.Application;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Random;
import java.lang.Object;

//http://stackoverflow.com/questions/28397700/javafx-how-to-write-text-to-a-new-line-in-a-textarea
public class Project extends Application
{
    private static GridPane grid = new GridPane();
    private TextArea textArea = new TextArea();

    private double currentItemTotal;

    private TextField[] leftTextFields = new TextField[4];

    private TableView<Item> table = new TableView<Item>();
    ObservableList<Item> observable = FXCollections.observableArrayList();
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Project");

        setupGrid();
        setupTextArea();
        setupLabels();
        setupTextField();
        setupButton();
        setupTable();

        Scene scene = new Scene(grid, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    private void setupGrid()
    {
        grid.setGridLinesVisible(true);

        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(16.665);
        ColumnConstraints column1point5 = new ColumnConstraints();
        column1point5.setPercentWidth(16.665);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(33.33);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(33.33);

        grid.getColumnConstraints().addAll(column1,column1point5, column2,column3);

        for(int x =0; x < 20; x++)
        {
            RowConstraints row1 = new RowConstraints();
            row1.setPercentHeight(5);
            grid.getRowConstraints().add(row1);
        }
    }
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

        //Product quantity / Unit Cost
        leftTextFields[1].setOnKeyReleased(event -> updateTotal());
        leftTextFields[2].setOnKeyReleased(event -> updateTotal());
        leftTextFields[3].setOnKeyReleased(event -> TotlalAlterted());
    }
    public void TotlalAlterted()
    {
        //Check if unit cost/ Amount exist
        

    }
    private void setupLabels()
    {
        Label labelProductName = new Label("Product Name       : ");
        Label labelProductQuantity = new Label("Product Quantity  : ");
        Label labelUnitCost = new Label("Unit Cost              : ");
        Label labelTotalCost= new Label("Total Cost             : ");

        GridPane.setConstraints(labelProductName,0,0);
        GridPane.setConstraints(labelProductQuantity,0,1);
        GridPane.setConstraints(labelUnitCost,0,2);
        GridPane.setConstraints(labelTotalCost,0,3);
        grid.getChildren().addAll(labelProductName,labelProductQuantity,labelUnitCost,labelTotalCost);
    }
    private void setupTextArea()
    {
        textArea.setEditable(false);
        GridPane.setConstraints(textArea,3,0, 1,20);
        grid.getChildren().add(textArea);
    }
    private void setupButton()
    {
        Button BAdd = new Button("Add");
        BAdd.setOnAction(event -> {
            if(TextFieldChecker())
            {
                System.out.println("Correct Input");
                String inputItemName = leftTextFields[0].getText();
                int inputItemQuantity = Integer.parseInt(leftTextFields[1].getText());
                float inputUnitCost = (float)Double.parseDouble(leftTextFields[2].getText());
                float inputTotal = Float.parseFloat(leftTextFields[3].getText());
                saveItem(inputItemName,inputItemQuantity,inputUnitCost, inputTotal);
            }
        });

        Button BclearNAdd = new Button("Clear");

        BclearNAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clearTextArea();
                textArea.setText(getRandomNumber()+"");
            }
        });

        GridPane.setConstraints(BAdd,1,4);
        GridPane.setConstraints(BclearNAdd,0,4);
        grid.getChildren().addAll(BclearNAdd,BAdd);

    }
    private void setupTable()
    {
        table.setEditable(true);
        TableColumn itemName = new TableColumn("Name");
        TableColumn itemQuantity = new TableColumn("Quantity");
        TableColumn itemUnit = new TableColumn("Cost");
        TableColumn itemTotal = new TableColumn("Total Cost");
        itemName.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        itemQuantity.setCellValueFactory(new PropertyValueFactory<Item, String>("quantity"));
        itemUnit.setCellValueFactory(new PropertyValueFactory<Item, String>("unitCost"));

        itemTotal.setCellValueFactory(new PropertyValueFactory<Item, String>("total"));

        //giving table list to get information from
        table.setItems(observable);
        table.getColumns().addAll(itemName,itemQuantity, itemUnit, itemTotal);

        grid.add(table,2,0,1,20);
    }
    private boolean TextFieldChecker()
    {
        System.out.println("TextField CHecker");
        if(!leftTextFields[0].getText().trim().isEmpty() && CheckIfNum(leftTextFields[1].getText(),false) && CheckIfNum(leftTextFields[2].getText(),true) && CheckIfNum(leftTextFields[3].getText(),true))
        {
            System.out.println("True");
            return true;
        }
        System.out.println("False");
        return false;
    }
    private void saveItem(String ProductName, int Quantity, float Cost, float Total)
    {
        System.out.println("Save Item");
        Item temp = new Item(ProductName, Quantity, Cost, Total);
        System.out.println(temp.getTotal());
        observable.add(temp);
        updateReceipt();
        //updateTextArea();
        clearTextFields();
    }

    private void updateTotal()
    {
        if(CheckIfNum(leftTextFields[2].getText(), true) == true && CheckIfNum(leftTextFields[1].getText(),false) == true) {
            int quantity = Integer.parseInt(leftTextFields[1].getText());
            Double price = Double.parseDouble(leftTextFields[2].getText());

            Double Total = quantity * price;
            leftTextFields[3].setText(""+Total);
        }
        else
        {
            leftTextFields[3].setText("0.0");
        }

    }

    private void updateReceipt()
    {
        textArea.setText("");
        for(int x= 0; x < observable.size(); x++)
        {
            Item temp = observable.get(x);
            textArea.appendText(temp.getName());
            textArea.appendText("\n");
            if(temp.getTotal() == (temp.getUnitCost() * temp.getQuantity()) )
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

        textArea.appendText("Grand Total : $" + getTotalsTotal());

    }
    //Adds the totals of all items in list and returns it
    private double getTotalsTotal()
    {
        double totalsTotal = 0;
        for(int x =0; x < observable.size(); x++)
        {
            totalsTotal = totalsTotal + observable.get(x).getTotal();
        }
        return totalsTotal;
    }
    private void clearTextFields()
    {
        leftTextFields[0].setText("");
        leftTextFields[1].setText("");
        leftTextFields[2].setText("");
        leftTextFields[3].setText("");
    }

    private void clearTextArea()
    {
        textArea.setText("");
    }
    private int getRandomNumber()
    {
        Random ran = new Random();
        return ran.nextInt();
    }

    //retusn true if passed in value is an number, where being double or int
    private static boolean CheckIfNum(String num, boolean isitPrice)
    {
        try{
            //if its a double
            if(isitPrice)
            {
                //try turning it to a double
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
            System.out.println(total);
        }

        public String getName() { return this.name.get(); }
        public int getQuantity() { return this.quantity.get(); }
        public float getUnitCost() { return this.unitCost.get(); }
        public float getTotal(){return this.total.get();}

    }
}



