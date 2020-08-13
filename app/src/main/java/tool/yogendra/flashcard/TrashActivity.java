package tool.yogendra.flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class TrashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash);
        setTitle(getResources().getString(R.string.menu_sections));
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, TrashActivity.class);
    }
}