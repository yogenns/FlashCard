package tool.yogendra.flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class SectionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sections);
        setTitle(getResources().getString(R.string.menu_trash));
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, SectionsActivity.class);
    }
}