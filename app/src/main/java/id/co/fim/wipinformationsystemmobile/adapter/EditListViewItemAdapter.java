package id.co.fim.wipinformationsystemmobile.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import id.co.fim.wipinformationsystemmobile.R;
import id.co.fim.wipinformationsystemmobile.model.ItemInBox;

public class EditListViewItemAdapter extends BaseAdapter {
    private Context context;
    private List<ItemInBox> itemList;
    private OnItemDeleteListener deleteListener;

    public EditListViewItemAdapter(Context context, List<ItemInBox> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    public void setOnItemDeleteListener(OnItemDeleteListener listener) {
        this.deleteListener = listener;
    }

    @Override
    public int getCount() {
        return getFilteredCount();
    }

    @Override
    public Object getItem(int position) {
        return getFilteredItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.edit_list_view_item, parent, false);
            holder = new ViewHolder();
            holder.tvItemName = convertView.findViewById(R.id.tvItemName);
            holder.tvItemCode = convertView.findViewById(R.id.tvItemCode);
            holder.tvQuantity = convertView.findViewById(R.id.tvQuantity);
            holder.etQuantity = convertView.findViewById(R.id.etQuantity);
            holder.btnDelete = convertView.findViewById(R.id.btnDelete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ItemInBox item = getFilteredItem(position);
        if (item == null) return convertView;

        holder.tvItemName.setText("Nama: " + item.getItemName());
        holder.tvItemCode.setText("Kode: " + item.getItemCode());
        holder.tvQuantity.setText("Quantity: ");

        // Hapus listener lama untuk mencegah multiple callbacks
        if (holder.etQuantity.getTag() instanceof TextWatcher) {
            holder.etQuantity.removeTextChangedListener((TextWatcher) holder.etQuantity.getTag());
        }

        holder.etQuantity.setText(String.valueOf(item.getQuantity()));

        // Tambahkan TextWatcher baru
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int newQuantity = Integer.parseInt(s.toString().trim());
                    item.setQuantity(newQuantity);
                } catch (NumberFormatException e) {
                    item.setQuantity(0);
                }
            }
        };

        holder.etQuantity.addTextChangedListener(textWatcher);
        holder.etQuantity.setTag(textWatcher);  // Simpan TextWatcher agar bisa dihapus nanti

        holder.btnDelete.setOnClickListener(v -> {
            item.setQuantity(0);
            notifyDataSetChanged();
        });

        return convertView;
    }

    public interface OnItemDeleteListener {
        void onItemDeleted(int position);
    }

    static class ViewHolder {
        TextView tvItemName, tvItemCode, tvQuantity;
        EditText etQuantity;
        Button btnDelete;
    }

    private ItemInBox getFilteredItem(int position) {
        int count = -1;
        for (ItemInBox item : itemList) {
            if (item.getQuantity() > 0) {
                count++;
            }
            if (count == position) {
                return item;
            }
        }
        return null;
    }

    private int getFilteredCount() {
        int count = 0;
        for (ItemInBox item : itemList) {
            if (item.getQuantity() > 0) {
                count++;
            }
        }
        return count;
    }
}