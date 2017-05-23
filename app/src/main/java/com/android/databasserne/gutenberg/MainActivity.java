package com.android.databasserne.gutenberg;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.android.databasserne.gutenberg.Adapters.TabFragmentAdapter;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static String database = "mysql";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new TabFragmentAdapter(getSupportFragmentManager(),
                MainActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_item_database) {
            if (Objects.equals(database, "mysql")) {
                database = "neo4j";
                Snackbar.make(this.findViewById(R.id.tabs), "Now using neo4j database", 1000).show();
            } else {
                database = "mysql";
                Snackbar.make(this.findViewById(R.id.tabs), "Now using mysql database", 1000).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}