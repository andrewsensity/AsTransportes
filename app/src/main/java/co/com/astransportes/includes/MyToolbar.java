package co.com.astransportes.includes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import co.com.astransportes.R;

public class MyToolbar {

    public static void createToolbar(AppCompatActivity activity, String title, Boolean back){
        Toolbar mToolbar = activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(mToolbar);
        activity.getSupportActionBar().setTitle(title);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(back);
    }
}
