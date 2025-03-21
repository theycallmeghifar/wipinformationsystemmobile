package id.co.fim.wipinformationsystemmobile.model;

public class ItemInBox {
    private String itemCode;
    private String itemName;
    private int quantity;

    public ItemInBox() {}

    public ItemInBox(String itemCode, String itemName, int quantity) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.quantity = quantity;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
