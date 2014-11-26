package filemanager.android.ykolesnik.com.filemanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Checkable;
import android.widget.ListView;

import java.util.List;

/**
 * Created by s.mokiyenko on 11/22/14.
 */
public class FolderContentFragment extends Fragment implements UpdatableFragment{

    private ListView mFilesView;
    private ArrayAdapter<String> mAdapter;
    private List<String> mFiles;

    public static FolderContentFragment newInstance() {
        FolderContentFragment fragment = new FolderContentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        mFilesView = (ListView) view.findViewById(R.id.lv_files);
        mFiles = FileManagerApplication.getFileManager().getFolderContent();
        mAdapter = new FolderAdapter(
                getActionBar().getThemedContext(),
                R.layout.listview_item,
                R.id.tv_item_name,
                mFiles);
        mFilesView.setAdapter(mAdapter);
        mFilesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (((Checkable) view).isChecked()) {
                    FileManagerApplication.getFileManager().addSelectedFile(mFiles.get(i));
                } else {
                    FileManagerApplication.getFileManager().removeFromSelectedFiles(mFiles.get(i));
                }
            }
        });
        return view;
    }

    public void updateFolderContent() {
        mFiles.clear();
        mFiles.addAll(FileManagerApplication.getFileManager().getFolderContent());
        for (int i = 0; i < mFiles.size(); i++){
            mFilesView.setItemChecked(i,false);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onUpdate() {
        updateFolderContent();
    }

    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }
}
