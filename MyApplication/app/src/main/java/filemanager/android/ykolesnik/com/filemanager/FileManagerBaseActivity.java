package filemanager.android.ykolesnik.com.filemanager;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by s.mokiyenko on 11/25/14.
 */
public class FileManagerBaseActivity extends ActionBarActivity {

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
    }

    private MenuItemCompat.OnActionExpandListener searchExpandListener = new MenuItemCompat.OnActionExpandListener() {
        @Override
        public boolean onMenuItemActionExpand(MenuItem menuItem) {
            onSearchStarted();
            return true;
        }

        @Override
        public boolean onMenuItemActionCollapse(MenuItem menuItem) {
            onSearchFinished();
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Only show items in the action bar relevant to this screen
        // if the drawer is not showing. Otherwise, let the drawer
        // decide what to show in the action bar.
        getMenuInflater().inflate(R.menu.main, menu);
        restoreActionBar();
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SupportMenuItem searchItem = (SupportMenuItem) menu.findItem(R.id.action_search);
        searchItem.setSupportOnActionExpandListener(searchExpandListener);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
        if (searchableInfo != null) {
            searchView.setSearchableInfo(searchableInfo);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_delete:
                onDeleteFilePressed();
                break;
            case R.id.action_copy:
                onCopyFilePressed();
                break;
            case R.id.action_paste:
                onPasteFilePressed();
                break;
            case R.id.action_merge:
                onMergeFilePressed();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void onDeleteFilePressed(){
        FileManagerApplication.getFileManager().delete();
        updateFragmentUI();
        showMessageToast("File has been deleted");
    }

    public void onCopyFilePressed() {
        FileManagerApplication.getFileManager().copy();
        showMessageToast("File has been copied");
    }

    public void onPasteFilePressed() {
        FileManagerApplication.getFileManager().paste();
        updateFragmentUI();
        showMessageToast("File has been pasted");
    }

    public void onMergeFilePressed(){
        FileManagerApplication.getFileManager().merge2htmlFiles();
        updateFragmentUI();
        showMessageToast("File has been pasted");
    }

    public void showMessageToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void updateFragmentUI(){
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment instanceof UpdatableFragment) {
                ((UpdatableFragment) fragment).onUpdate();
            }
        }
    }

    protected void onSearchStarted(){

    }

    protected void onSearchFinished(){

    }
}
