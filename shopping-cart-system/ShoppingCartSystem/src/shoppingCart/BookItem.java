package shoppingCart;

//===================== BOOK ITEM =====================
class BookItem extends Item {

 public BookItem(int id, String name, double price, int quantity) {
     super(id, name, price, quantity);
 }

 @Override
 public String getType() {
     return "Book";
 }
}
