package id.co.fim.wipinformationsystemmobile.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import id.co.fim.wipinformationsystemmobile.R;
import id.co.fim.wipinformationsystemmobile.model.ItemInBox;

public class AddListViewItemAdapter extends ArrayAdapter<ItemInBox> {
    private Context context;
    private List<ItemInBox> allItemList;
    private List<ItemInBox> itemList;

    public AddListViewItemAdapter(Context context, List<ItemInBox> allItemList, List<ItemInBox> itemList) {
        super(context, R.layout.add_list_view_item, itemList);
        this.context = context;
        this.allItemList = new ArrayList<>(allItemList);
        this.itemList = new ArrayList<>(itemList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.add_list_view_item, parent, false);
            holder = new ViewHolder();
            holder.spinnerItemCode = convertView.findViewById(R.id.spnItemCode);
            holder.etItemName = convertView.findViewById(R.id.etItemName);
            holder.etQuantity = convertView.findViewById(R.id.etQuantity);
            holder.btnDelete = convertView.findViewById(R.id.btnDelete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ItemInBox currentItem = itemList.get(position);
        if (currentItem == null) return convertView;

        // Set data ke tampilan
        holder.etItemName.setText(currentItem.getItemName());
        holder.etQuantity.setText(String.valueOf(currentItem.getQuantity()));

        // Ambil semua itemCode untuk Spinner
        List<String> itemCodeList = new ArrayList<>();
        for (ItemInBox item : allItemList) {
            itemCodeList.add(item.getItemCode());
        }

        // Adapter untuk Spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(context, R.layout.spinner_item, itemCodeList);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_option);
        holder.spinnerItemCode.setAdapter(spinnerAdapter);

        // Atur pilihan Spinner
        int selectedPosition = itemCodeList.indexOf(currentItem.getItemCode());
        if (selectedPosition >= 0) {
            holder.spinnerItemCode.setSelection(selectedPosition);
        }

        // Simpan perubahan pada Spinner
        holder.spinnerItemCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String selectedItemCode = itemCodeList.get(pos);
                currentItem.setItemCode(selectedItemCode);

                // Update itemName sesuai itemCode
                for (ItemInBox item : allItemList) {
                    if (item.getItemCode().equals(selectedItemCode)) {
                        currentItem.setItemName(item.getItemName());
                        holder.etItemName.setText(item.getItemName());
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        holder.etQuantity.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        holder.etQuantity.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        holder.etQuantity.setKeyListener(DigitsKeyListener.getInstance("0123456789."));


        // Simpan perubahan pada Quantity
        holder.etQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    try {
                        currentItem.setQuantity(Integer.parseInt(s.toString()));
                    } catch (NumberFormatException e) {
                        currentItem.setQuantity(0);
                    }
                }

                Log.d("ITEM LIST UPDATE", "DATA: " + itemList.toString());
                Log.d("ITEM LIST UPDATE", "SIZE: " + itemList.size());
            }
        });

        // Hapus item dari list
        holder.btnDelete.setOnClickListener(v -> {
            if (position >= 0 && position < itemList.size()) {
                itemList.remove(position);
                notifyDataSetChanged();
            }

            Log.d("ITEM LIST UPDATE", "DATA: " + itemList.toString());
            Log.d("ITEM LIST UPDATE", "SIZE: " + itemList.size());
        });

        return convertView;
    }

    public List<ItemInBox> getItemList() {
        return new ArrayList<>(itemList);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    public void updateAllItemListData(List<ItemInBox> newItems) {
        Set<String> existingItemCodes = new HashSet<>();
        for (ItemInBox item : allItemList) {
            existingItemCodes.add(item.getItemCode());
        }

        for (ItemInBox newItem : newItems) {
            if (!existingItemCodes.contains(newItem.getItemCode())) {
                allItemList.add(newItem);
                existingItemCodes.add(newItem.getItemCode());
            }
        }

        notifyDataSetChanged();
    }

    public void addListItemData(ItemInBox newItem) {
        itemList.add(newItem);
        notifyDataSetChanged();
        Log.d("ITEM LIST UPDATE", "DATA: " + itemList.toString());
        Log.d("ITEM LIST UPDATE", "SIZE: " + itemList.size());
    }

    public void updateItemListData(List<ItemInBox> newItems) {
        for (int i = 0; i < newItems.size(); i++) {
            boolean exists = false;
            for (ItemInBox existingItem : itemList) {
                if (existingItem.getItemCode().equals(newItems.get(i).getItemCode())) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                itemList.add(newItems.get(i));
            }
        }
        notifyDataSetChanged();
    }

    static class ViewHolder {
        Spinner spinnerItemCode;
        EditText etItemName;
        EditText etQuantity;
        AppCompatImageButton btnDelete;
    }
}
