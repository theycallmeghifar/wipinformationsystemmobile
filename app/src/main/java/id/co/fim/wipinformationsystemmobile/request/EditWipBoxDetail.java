package id.co.fim.wipinformationsystemmobile.request;

import java.util.List;

import id.co.fim.wipinformationsystemmobile.model.ItemInBox;

public class EditWipBoxDetail {
    private int wipBoxId;
    private List<ItemInBox> itemInBoxList;

    public EditWipBoxDetail(int wipBoxId, List<ItemInBox> itemInBoxList) {
        this.wipBoxId = wipBoxId;
        this.itemInBoxList = itemInBoxList;
    }

    public int getWipBoxId() {
        return wipBoxId;
    }

    public void setWipBoxId(int wipBoxId) {
        this.wipBoxId = wipBoxId;
    }

    public List<ItemInBox> getItemInBoxList() {
        return itemInBoxList;
    }

    public void setItemInBoxList(List<ItemInBox> itemInBoxList) {
        this.itemInBoxList = itemInBoxList;
    }
}
