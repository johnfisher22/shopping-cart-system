package shoppingCart;


//===================== GROCERY ITEM =====================
class GroceryItem extends Item {

 public GroceryItem(int id, String name, double price, int quantity) {
     super(id, name, price, quantity);
 }

 @Override
 public String getType() {
     return "Grocery";
 }
}