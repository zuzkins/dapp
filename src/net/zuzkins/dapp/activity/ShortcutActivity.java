package net.zuzkins.dapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import net.zuzkins.dapp.R;

import java.util.List;

public class ShortcutActivity extends Activity {

    ListView list;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final List<PackageInfo> packages = getPackageManager().getInstalledPackages(PackageManager.GET_ACTIVITIES);
        setContentView(R.layout.shortcut);

        this.list = (ListView) findViewById(R.id.appList);
        list.setAdapter(createPackagesAdapter(packages));
    }

    @Override
    protected void onStart() {
        super.onStart();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, final View view, final int i, final long l) {
                final PackageInfo itemAtPosition = (PackageInfo) adapterView.getItemAtPosition(i);
                createIntent(itemAtPosition);
            }
        });
    }

    private void createIntent(final PackageInfo info) {
        final Uri packageUri = Uri.parse("package:" + info.packageName);
        final Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageUri);

        final Intent shortcut = new Intent();
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, uninstallIntent);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Shortcut Test");
        //shortcut.xml.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconResource);
        setResult(RESULT_OK, shortcut);
        finish();
    }

    private ListAdapter createPackagesAdapter(final List<PackageInfo> packages) {
        return new ListAdapter() {
            @Override
            public boolean areAllItemsEnabled() {
                return true;
            }

            @Override
            public boolean isEnabled(final int i) {
                return true;
            }

            @Override
            public void registerDataSetObserver(final DataSetObserver dataSetObserver) {
            }

            @Override
            public void unregisterDataSetObserver(final DataSetObserver dataSetObserver) {
            }

            @Override
            public int getCount() {
                return packages.size();
            }

            @Override
            public Object getItem(final int i) {
                return packages.get(i);
            }

            @Override
            public long getItemId(final int i) {
                return i;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getView(final int i, final View view, final ViewGroup viewGroup) {
                final ViewGroup v;
                final TextView tv;
                if (view == null) {
                    LayoutInflater inflater = (LayoutInflater)ShortcutActivity.this
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = (ViewGroup) inflater.inflate(R.layout.rowlayout, viewGroup, false);
                    tv = new TextView(ShortcutActivity.this);
                    v.addView(tv);
                } else {
                    v = (ViewGroup) view;
                    tv = (TextView) v.getChildAt(0);
                }
                final PackageInfo item = (PackageInfo) getItem(i);
                tv.setText(item.packageName);
                return v;
            }

            @Override
            public int getItemViewType(final int i) {
                return 0;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public boolean isEmpty() {
                return packages.isEmpty();
            }
        };
    }
}
