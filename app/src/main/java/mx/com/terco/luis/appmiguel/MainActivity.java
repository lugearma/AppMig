package mx.com.terco.luis.appmiguel;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import mx.com.terco.luis.appmiguel.io.Constant;
import mx.com.terco.luis.appmiguel.io.VolleySingleton;
import mx.com.terco.luis.appmiguel.ui.fragment.DeportesFragment;
import mx.com.terco.luis.appmiguel.ui.fragment.EspectaculosFragment;
import mx.com.terco.luis.appmiguel.ui.fragment.ImagenesFragment;
import mx.com.terco.luis.appmiguel.ui.fragment.InternacionalFragment;
import mx.com.terco.luis.appmiguel.ui.fragment.NacionalFragment;
import mx.com.terco.luis.appmiguel.ui.fragment.PortadaFragment;
import mx.com.terco.luis.appmiguel.ui.fragment.VideosFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private BottomBar mBottomBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //BottomBar config
        setupBottomBar(savedInstanceState, this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getDataPortada();
    }

    private void setupBottomBar(Bundle savedInstanceState, final Context context){
        mBottomBar = BottomBar.attach(this, savedInstanceState);

        mBottomBar.setItemsFromMenu(R.menu.bottombar_menu, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarItemOne) {
                    Toast.makeText(context, "selecccionaste uno", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(context, "selecccionaste dos", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarItemOne) {
                    // The user reselected item number one, scroll your content to top.
                }
            }
        });

        mBottomBar.mapColorForTab(0, "#7B1FA2");
        mBottomBar.mapColorForTab(1, "#FF5252");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        selectItem(id);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getDataPortada() {

        String url = Constant.BASE_URL + Constant.PORTADA_PARAM;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TODO BIEN", response);
                //setupRecyclerview(parsearRespuesta(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ALGO SALIO MAL", error.toString());
            }
        });

        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 90000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 90000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        VolleySingleton.getInstance(this)
                .addToRequestQueue(request);
    }

    private void parsingData() {

    }

    private void selectItem(int position) {

        Fragment newFragment;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        switch (position) {

            case R.id.nav_portada:
                newFragment = new PortadaFragment();
                transaction.replace(R.id.content_frame, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;

            case R.id.nav_deportes:
                newFragment = new DeportesFragment();
                transaction.replace(R.id.content_frame, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;

            case R.id.nav_espectaculos:
                newFragment = new EspectaculosFragment();
                transaction.replace(R.id.content_frame, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;

            /*case 2:
                newFragment = new ImagenesFragment();
                transaction.replace(R.id.content_frame, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;*/

            case R.id.nav_internacional:
                newFragment = new InternacionalFragment();
                transaction.replace(R.id.content_frame, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;

            case R.id.nav_nacional:
                newFragment = new NacionalFragment();
                transaction.replace(R.id.content_frame, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;

            /*case 5:
                newFragment = new VideosFragment();
                transaction.replace(R.id.content_frame, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;*/


        }
    }
}
