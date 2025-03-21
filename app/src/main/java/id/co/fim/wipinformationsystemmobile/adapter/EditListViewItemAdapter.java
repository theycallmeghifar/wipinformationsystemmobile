package id.co.fim.wipinformationsystemmobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import id.co.fim.wipinformationsystemmobile.R;
import id.co.fim.wipinformationsystemmobile.model.ItemInBox;

public class EditListViewItemAdapter extends BaseAdapter {
    private Context context;
    private List<ItemInBox> itemList;

    public EditListViewItemAdapter(Context context, List<ItemInBox> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.edit_list_view_item, parent, false);
        }

        // Ambil elemen UI dari item_in_box.xml
        TextView tvItemName = convertView.findViewById(R.id.tvItemName);
        TextView tvItemCode = convertView.findViewById(R.id.tvItemCode);
        TextView tvQuantity = convertView.findViewById(R.id.tvQuantity);
        EditText etQuantity = convertView.findViewById(R.id.etQuantity);

        // Set data ke UI
        ItemInBox item = itemList.get(position);
        tvItemName.setText("Nama: " + item.getItemName());
        tvItemCode.setText("Kode: " + item.getItemCode());
        tvQuantity.setText("Quantity: ");
        etQuantity.setText(item.getQuantity());

        return convertView;
    }
}
