package shoppingCart;


//===================== ABSTRACT ITEM CLASS =====================
abstract class Item {

 private int id;
 private String name;
 private double price;
 private int quantity;

 public Item(int id, String name, double price, int quantity) {

     this.id = id;
     this.name = name;
     this.price = price;
     this.quantity = quantity;
 }

 public int getId() {
     return id;
 }

 public double getPrice() {
     return price;
 }

 public int getQuantity() {
     return quantity;
 }

 public void setQuantity(int quantity) {
     this.quantity = quantity;
 }

 public abstract String getType();

 @Override
 public String toString() {

     return "Type : " + getType()
             + " | ID : " + id
             + " | Name : " + name
             + " | Price : $" + price
             + " | Quantity : " + quantity;
 }
}
