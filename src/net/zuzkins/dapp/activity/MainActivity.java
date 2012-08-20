package net.zuzkins.dapp.activity;

import android.app.Activity;
import android.os.Bundle;
import net.zuzkins.dapp.R;

/**
 * @author Jiri Zuna
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}
