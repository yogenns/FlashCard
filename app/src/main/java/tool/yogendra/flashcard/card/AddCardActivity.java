package tool.yogendra.flashcard.card;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.chip.Chip;

import tool.yogendra.flashcard.R;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        setTitle(getResources().getString(R.string.card_new));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            saveCardData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveCardData() {
        EditText frontTxt = findViewById(R.id.card_front);
        EditText backTxt = findViewById(R.id.card_back);
        Chip sectionTxt = findViewById(R.id.card_section);
        String data = " Front= "+frontTxt.getText()+" Back= "+backTxt.getText()+" Section= "+sectionTxt.getText();
        Toast.makeText(AddCardActivity.this, "Data= "+data, Toast.LENGTH_LONG).show();
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, AddCardActivity.class);
    }
}