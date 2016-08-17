package org.waikato.comp204;
//https://www.youtube.com/watch?v=QGGE0WsUslc
//http://stackoverflow.com/questions/32866937/how-to-check-if-textfield-is-empty
//http://docs.oracle.com/javafx/2/layout/builtin_layouts.html
//https://www.youtube.com/watch?v=YtKF1JKtRWM
import javafx.application.Application;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.lang.Object;

//http://stackoverflow.com/questions/28397700/javafx-how-to-write-text-to-a-new-line-in-a-textarea
public class Project extends Application
{
    private static GridPane grid = new GridPane();
    private TextArea textArea = new TextArea();

    private TextField[] leftTextFields = new TextField[4];

    ObservableList<Item> observable = FXCollections.observableArrayList();
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Project");

        setupGrid();
        setupTextArea();
        setupLabels();
        setupTextField();
        setupButton();

        addToList("a",1,1);
        addToList("b",2,2);

        Scene scene = new Scene(grid, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    private void setupGrid()
    {
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);
        grid.setGridLinesVisible(true);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(33.33);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(33.33);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(33.33);

        grid.getColumnConstraints().addAll(column1, column2,column3);

        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(5);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(5);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(5);
        RowConstraints row4 = new RowConstraints();
        row4.setPercentHeight(5);
        RowConstraints row5 = new RowConstraints();
        row5.setPercentHeight(5);
        RowConstraints row6 = new RowConstraints();
        row6.setPercentHeight(5);
       grid.getRowConstraints().addAll(row1,row2,row3,row4,row5,row6);
    }
    private void setupTextField()
    {
        for(int x =0; x < 4; x++)
        {

        }
    }
    private void setupLabels()
    {
        Label labelProductName = new Label("Product Name         : ");
        Label labelProductQuantity = new Label("Product Quantity : ");
        Label labelUnitCost = new Label("Unit Cost               : ");
        Label labelTotalCost= new Label("Total Cost              : ");

        //grid.add(labelProductName, 0,1);
        GridPane.setConstraints(labelProductName,0,0);
       // grid.add(labelProductQuantity,0,2);
        GridPane.setConstraints(labelProductQuantity,0,1);
        //grid.add(labelUnitCost, 0,3);
        GridPane.setConstraints(labelUnitCost,0,2);
        grid.getChildren().addAll(labelProductName,labelProductQuantity,labelUnitCost);
    }
    private void setupTextArea()
    {
        textArea.setEditable(false);
        GridPane.setConstraints(textArea,2,0, 1,6);
        grid.getChildren().add(textArea);
    }
    private void setupButton()
    {
        Button BclearNAdd = new Button();

        BclearNAdd.setText("Clear");
        BclearNAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clearTextArea();
                textArea.setText(getRandomNumber()+"");
            }
        });
        GridPane.setConstraints(BclearNAdd,0,3);
        grid.getChildren().add(BclearNAdd);

    }

    private void addToList(String _name,int _quantity,float _unitCost)
    {
        Item x = new Item(_name, _quantity, _unitCost);
        observable.add(x);
        updateTextArea();
    }
    private void updateTextArea()
    {
        textArea.setText("");
        System.out.println("List size : "+ observable.size());
        String newline = "\n";
        for(int x =0; x <observable.size(); x++)
        {
            System.out.println(x);
            Item temp = observable.get(x);
            textArea.appendText("Name      : " + temp.getName() +newline);
            textArea.appendText("Quantity  : " + temp.getQuantity()+newline);
            textArea.appendText("Unit Cost : $" + temp.getUnitCost() + newline);
            textArea.appendText(newline);
        }
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

    public static class Item {
        public final SimpleStringProperty name;
        public final SimpleIntegerProperty quantity;
        public final SimpleFloatProperty unitCost;

        public Item(String name, int quantity, float unitCost) {
            this.name = new SimpleStringProperty(name);
            this.quantity = new SimpleIntegerProperty(quantity);
            this.unitCost = new SimpleFloatProperty(unitCost);
        }

        public String getName() { return this.name.get(); }
        public int getQuantity() { return this.quantity.get(); }
        public float getUnitCost() { return this.unitCost.get(); }
    }
}



