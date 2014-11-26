package filemanager.android.ykolesnik.com.filemanager;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.List;

/**
 * Created by s.mokiyenko on 11/22/14.
 */
public class FolderAdapter extends ArrayAdapter<String>{

    private int mTvId;

    public FolderAdapter(Context context, int resource) {
        super(context, resource);
    }

    public FolderAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public FolderAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
    }

    public FolderAdapter(Context context, int resource, int textViewResourceId, String[] objects) {
        super(context, resource, textViewResourceId, objects);
        mTvId = textViewResourceId;
    }

    public FolderAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
    }

    public FolderAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
        super(context, resource, textViewResourceId, objects);
        mTvId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        String searchQuery = FileManagerApplication.getFileManager().getSearchQuery();
        if (searchQuery != null && mTvId > 0){
            TextView textView = (TextView) view.findViewById(mTvId);
            String source = textView.getText().toString();
            if (source.contains(".html")){
                source = source.replaceAll(searchQuery,"<font color='red'>"+searchQuery+"</font>");
                textView.setText(Html.fromHtml(source));
            }
        }
        return view;
    }
}
