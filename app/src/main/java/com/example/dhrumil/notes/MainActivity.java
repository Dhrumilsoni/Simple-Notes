package com.example.dhrumil.notes;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.FragmentTransaction;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v7.widget.SearchView;


public class MainActivity extends AppCompatActivity implements MainActivityFragment.Onqwertylistener,opennote.Onnulllistener{

    FragmentManager fragmentManager;
    DrawerLayout mDrawerLayout;
    ListView mDrawerList;
    String sidebarvalues[]=new String[3];
    MainActivity thisobject=this;
    ActionBarDrawerToggle mDrawerToggle;
    MainActivityFragment mainfragment;
    boolean b=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*SharedPreferences sharedPreferences=getPreferences(MODE_PRIVATE);
        b=sharedPreferences.getBoolean("View",b);
        */

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Notes");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        sidebarvalues[0]="Home";
        sidebarvalues[1]="Trash/Deleted";
        sidebarvalues[2]="Settings";
        mDrawerLayout=(DrawerLayout) findViewById(R.id.drawerlayout);
        mDrawerList=(ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, sidebarvalues));

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

        };
        //mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerLayout.addDrawerListener(mDrawerToggle);


        mainfragment = new MainActivityFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();// begin  FragmentTransaction
        ft.add(R.id.fragment_container, mainfragment,"MainFragment");                                // add    Fragment
        ft.commit();

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        //setContentView(R.layout.activity_opennote);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //final MainActivity thisobject = this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        /*menu.add(1,Menu.FIRST+1,Menu.FIRST+1,"List View");
        menu.add(1,Menu.FIRST+2,Menu.FIRST+2,"Grid View");
        menu.add(1,Menu.FIRST+3,Menu.FIRST+3,"Text Mode");
        menu.add(1,Menu.FIRST+4,Menu.FIRST+4,"Canvas Mode");
        menu.add(1,Menu.FIRST+5,Menu.FIRST+5,"Deleted");
        */
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.app_bar_search));
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search Notes");
        searchView.getRootView().requestFocus();

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        /*Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }*/
        //Toast.makeText(this,SearchManager.QUERY+"",Toast.LENGTH_SHORT).show();
        return true;
    }

    /*private void doSearch(String query) {
        Intent intent = new Intent(this, SearchableActivity.class);
        intent.setAction(Intent.ACTION_SEARCH);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(SearchManager.QUERY, searchView.getQuery().toString());
        startActivity(intent);
    }*/

    boolean favourite=false;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id==R.id.action_fav){
            if(favourite==false){
                favourite=true;
                mainfragment.hideNonFav();
                Toast.makeText(this,"Got it",Toast.LENGTH_SHORT).show();
            }
            else{
                favourite=false;
                mainfragment.showNonFav();
                Toast.makeText(this,"I am here",Toast.LENGTH_SHORT).show();
            }
        }
        else if(item.getTitle()=="Deleted"){
            Intent intent=new Intent(this,RecycleBin.class);
            startActivity(intent);
        }
        /*else if (item.getTitle()=="List View"){
            b=true;
            MainActivityFragment mainActivityFragment=(MainActivityFragment) getSupportFragmentManager().findFragmentByTag("MainFragment");
            if(mainActivityFragment!=null){
                mainActivityFragment.Columns(b);
            }
        }
        else if (item.getTitle()=="Grid View"){
            b=false;
            MainActivityFragment mainActivityFragment=(MainActivityFragment) getSupportFragmentManager().findFragmentByTag("MainFragment");
            if(mainActivityFragment!=null){
                mainActivityFragment.Columns(b);
            }
        }
        else if(item.getTitle()=="Text Mode"){

        }
        else if(item.getTitle()=="Canvas Mode"){

        }*/
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onNoteSelected(int pos) {
        mDrawerLayout=(DrawerLayout) findViewById(R.id.drawerlayout);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        opennote opennotefrag=new opennote();
        Bundle args=new Bundle();
        args.putInt("My Key",pos);
        opennotefrag.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,opennotefrag,"Opennote Fragment").addToBackStack(null).commit();
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void removeelement() {
        MainActivityFragment f1=(MainActivityFragment) getSupportFragmentManager().findFragmentByTag("MainFragment");
        f1.removeelement();
    }

    private class DrawerItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(position==0){
                android.app.Fragment mainFragment = getFragmentManager().findFragmentByTag("MainFragment");
                if(mainFragment==null || !mainFragment.isVisible()){
                    MainActivityFragment mainActivityFragment=new MainActivityFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,mainActivityFragment,"MainFragment").commit();
                }
            }
            else if(position==1){
                Intent intent=new Intent(thisobject,RecycleBin.class);
                startActivity(intent);
            }
        }
    }
    /*public void onStop(){
        super.onStop();
        SharedPreferences share.dPreferences=getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("View",b);
        editor.commit();
    }*/

}
