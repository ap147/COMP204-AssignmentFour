COMP204-16B / COMP242-16B Assignment 4
======================================

Due on **Sunday, 21^(st) August at 11:30pm**.


Experimenting with a big Java Package – JavaFX, Part 2
==============================================

The goal of this exercise is to continue learning about the JavaFX system for 
building user interfaces, through extending the project started in Assignment 3.
You can copy code from, or refer to your Assignment 3 project as needed.

JavaFX is described in the [documentation supplied by Oracle, found here](http://docs.oracle.com/javase/8/).

Preamble
========

1. Fork this repository using the button at the top of the page.
  * Set the visibility level of the project to Private.
2. Clone your new repository to your computer using Git.
3. Remember to commit and push regularly!


Task
====

Complete as much of this assignment as you can. You do not have to complete all items, just get as much done as you can.

* To begin, open the `JavaFX_Project` project with your IDE.
* Create a new empty JavaFX window of sized `900 x 600px`. 
* Divide this new window into 3 equally sized columns. You may want to use a `GridPane` to accomplish this.
* In the right-most pane, add a `TextArea` that completely fills the pane.
  * Make this TextArea non-editable. You will be using this as an output area.
* On the left-most pane, add a Button.
* Create a function that clears the TextArea created earlier and writes some content to it.
  * Try printing a random number to the TextArea each time the function is run.
* Modify your code so that clicking the Button calls your TextArea modifying function.
  * Verify that when you press the Button, the contents of the TextArea changes.
* Add the below class to your project

```java
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
```

* Create an `ObservableList` to hold a collection of these `Item`s with the following code

```java
ObservableList<Item> observable = FXCollections.observableArrayList();
```

* Add a few sample Items to the list.
* Modify your TextArea updating function to print the contents of the list to the TextArea.
  * Try formatting the items tidily in the area.
* Modify your code so that when the list is modified, the TextArea updating function will be called.
* Modify your Button so that when it is pressed, a new Item is added to the list.
  * Just fill the Item with random data at this point.
* In the left-most pane, add 4 `TextField`s and `Label`s for each
  * Product name
  * Product quantity
  * Unit cost
  * Total cost
* Update the `Item` code to allow it to hold a `totalCost` field.
  * Remember to add a `get()` function for this field as with the others.
* Modify your code so that when the Button is pressed, the new Item added to the list contains the data entered in the new TextFields.
  * Only add a new Item if all of the TextFields have content
  * When a new Item is added, clear the contents of the TextFields
* Modify your code so that the TextFields so that when the quantity or unit cost fields are edited, the total cost field is updated to be the product of `quantity * unitCost`.
* Modify your total cost TextField so that if the value shown in it is different from what is expected (ie, it has been modified), the appearance will change.
  * Changing the background of the TextField or the colour of the Text would be good.
  * Remember to reset the appearance when the value is correct again.
* Implement validation for your quantity, unit cost and total cost fields so that they only accept valid characters.
  * The quantity field should only accept `[0-9]*`.
  * The unit cost and total cost fields should accept `[0-9]*\.[0-9]*`.
* Update your TextArea updating code to print the information in the ObservableList in a receipt-like fashion.
  * Mark totals that were edited (ie, aren't equal to `quantity * unitCost`) with an asterisk.
  * Add a `total` line on the last line of the receipt that shows the sum of all total costs in the list.
* In the centre column of your layout, add a table with 4 columns, one for each of the fields in the Item class.
* Bind your table to your ObservableList so that it displays the Items stored within it. The code samples below may help with these last 2 items.

```java
TableView<Item> table = new TableView<Item>();

...

table.setEditable(true);

TableColumn item = new TableColumn("Item Name");
item.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));

table.setItems(observable);
table.getColumns().addAll(item);
```

* Remove any sample items/code from your solution, and submit!

Submission
==========

Upload a zipped copy of your repository to [moodle in the assignment submission](https://elearn.waikato.ac.nz/mod/assign/view.php?id=568044). 
Please download the zip from GitLab using the download button in the top right 
of the project view rather than zipping it from the copy on your local hard drive.


Grading
=======

| Weighting | Allocated to |
|:----------:|------|
| 10% | Correct repository usage and settings |
| 90% | Allocated based on completion and correctness of submission |
