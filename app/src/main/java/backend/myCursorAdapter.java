package backend;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import project.ecse428.mcgill.ca.snowmore.R;

/**
 * Created by lucien on 2018-02-19.
 */

public class myCursorAdapter extends CursorAdapter {

    public myCursorAdapter(Context context , Cursor cursor) {
        super(context , cursor , 0);
    }

    @Override
    public View newView(Context context , Cursor cursor , ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_view_layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // Find fields to populate in inflated template
        TextView name = (TextView) view.findViewById(R.id.city);
        TextView price = (TextView) view.findViewById(R.id.postalCode);
        TextView address = (TextView) view.findViewById(R.id.address);
        TextView phone = (TextView) view.findViewById(R.id.phone);

        // Extract properties from cursor
        String str_name = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
        int str_price = cursor.getInt(cursor.getColumnIndexOrThrow("Price"));
        String str_address = cursor.getString(cursor.getColumnIndexOrThrow("Street address"));
        String str_phone = cursor.getString(cursor.getColumnIndexOrThrow("Phone"));

        // Populate fields with extracted properties
        name.setText(str_name);
        price.setText(String.valueOf(str_price));
        address.setText(str_address);
        phone.setText(str_phone);
    }
}

