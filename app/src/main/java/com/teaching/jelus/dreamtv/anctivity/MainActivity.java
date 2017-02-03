package com.teaching.jelus.dreamtv.anctivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.teaching.jelus.dreamtv.MyApp;
import com.teaching.jelus.dreamtv.R;
import com.teaching.jelus.dreamtv.fragment.MainActivityFragment;
import com.teaching.jelus.dreamtv.task.ReceivingData;

import org.json.JSONObject;

import java.net.URL;
import java.util.concurrent.ExecutorService;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    private ExecutorService mPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mPool = MyApp.getPool();
        replaceFragment(new MainActivityFragment(), MainActivityFragment.TAG, false);
        receiveData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void replaceFragment(Fragment fragment, String tag, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(R.id.fragment_container, fragment, tag);
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();

    }

    private void receiveData() {
        mPool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = ReceivingData.getUrl();
                    String str = ReceivingData.urlToStr(url);
                    JSONObject jsonObject = ReceivingData.strToJson(str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
