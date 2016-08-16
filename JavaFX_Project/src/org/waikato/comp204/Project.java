package org.waikato.comp204;
//https://www.youtube.com/watch?v=QGGE0WsUslc
//http://stackoverflow.com/questions/32866937/how-to-check-if-textfield-is-empty
//http://docs.oracle.com/javafx/2/layout/builtin_layouts.html

import javafx.application.Application;
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
import java.util.Random;


public class Project extends Application
{
    private static GridPane grid = new GridPane();
    private TextArea textArea = new TextArea();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Project");

        setupGrid();
        setupTextField();
        setupButton();

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
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(33.33);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(33.33);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(33.33);

        grid.getColumnConstraints().addAll(column1, column2,column3);
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(500);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(500);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(500);
        grid.getRowConstraints().addAll(row1,row2,row3);
    }
    private void setupTextField()
    {
        textArea.setEditable(false);
        grid.add(textArea, 2, 0,1,3);
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
        
        grid.getChildren().add(BclearNAdd);

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


}



