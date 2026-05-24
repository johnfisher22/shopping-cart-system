package shoppingCart;

//===================== ELECTRONIC ITEM =====================
class ElectronicItem extends Item {

 public ElectronicItem(int id, String name, double price, int quantity) {
     super(id, name, price, quantity);
 }

 @Override
 public String getType() {
     return "Electronic";
 }
}