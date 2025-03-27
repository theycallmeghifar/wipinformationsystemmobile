package id.co.fim.wipinformationsystemmobile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemInBox {
    @SerializedName("itemCode")
    @Expose
    private String itemCode;
    @SerializedName("itemName")
    @Expose
    private String itemName;
    @SerializedName("quantity")
    @Expose
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

    @Override
    public String toString() {
        return "ItemInBox{" +
                "itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                '}';
    }

}
