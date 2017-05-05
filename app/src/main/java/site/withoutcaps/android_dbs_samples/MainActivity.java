package site.withoutcaps.android_dbs_samples;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private SQL_DB_Helper mDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDbHelper = new SQL_DB_Helper(getApplicationContext());

        Cursor c = mDbHelper.find(mDbHelper, SQL_DB_Helper.TC._ID + "> ?", new String[]{"15"});

        while (c.moveToNext()) {
            long itemId = c.getLong(c.getColumnIndexOrThrow(SQL_DB_Helper.TC._ID));
            String itemTitle = c.getString(c.getColumnIndexOrThrow(SQL_DB_Helper.TC.COLUMN_NAME_TITLE));
            String itemSubtitle = c.getString(c.getColumnIndexOrThrow(SQL_DB_Helper.TC.COLUMN_NAME_SUBTITLE));
            Log.d(TAG, itemId + ": " + itemTitle + " : " + itemSubtitle);
        }
        c.close();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        mDbHelper.close();
        super.onDestroy();
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);
//    }
}
