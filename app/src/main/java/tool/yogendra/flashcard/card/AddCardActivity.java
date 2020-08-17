package tool.yogendra.flashcard.card;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.chip.Chip;

import tool.yogendra.flashcard.R;
import tool.yogendra.flashcard.SectionsActivity;
import tool.yogendra.flashcard.database.DBConstants;
import tool.yogendra.flashcard.database.SQLDBManager;

public class AddCardActivity extends AppCompatActivity {

    private static String TAG = "AddCardActivity";
    private static final int SECTION_ACTIVITY_REQUEST_CODE = 0;
    SQLDBManager sqldbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        setTitle(getResources().getString(R.string.card_new));

        addSectionChipListener();
    }

    private void addSectionChipListener() {
        Chip sectionTxt = findViewById(R.id.card_section);
        sectionTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = SectionsActivity.makeIntent(AddCardActivity.this);
                addIntent.putExtra("AddCard", "true");
                startActivityForResult(addIntent, SECTION_ACTIVITY_REQUEST_CODE);
            }
        });
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

    int sectionId = -1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SectionActivity with an OK result
        Log.i(TAG, "requestCode " + requestCode + " resultCode " + resultCode);
        if (requestCode == SECTION_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                String sectionName = data.getStringExtra(DBConstants.SECTION_NAME);
                sectionId = data.getIntExtra(DBConstants.SECTION_ID, -1);
                String sectionColor = data.getStringExtra(DBConstants.SECTION_COLOR);
                Log.i(TAG, "Selected sectionName " + sectionName + " sectionId " + sectionId + " sectionColor " + sectionColor);
                Chip sectionTxt = findViewById(R.id.card_section);
                sectionTxt.setText(sectionName);

                GradientDrawable shape = new GradientDrawable();
                shape.setShape(GradientDrawable.OVAL);
                shape.setCornerRadii(new float[]{8, 8, 8, 8, 0, 0, 0, 0});
                shape.setColor(Color.parseColor(sectionColor));
                sectionTxt.setChipIcon(shape);
            }
        }
    }

    private void saveCardData() {
        try {
            sqldbManager = new SQLDBManager(this);
            EditText frontTxt = findViewById(R.id.card_front);
            EditText backTxt = findViewById(R.id.card_back);
            Chip sectionTxt = findViewById(R.id.card_section);
            if (sectionTxt.getText().equals(getResources().getString(R.string.card_section)) || sectionId == -1) {
                Log.i(TAG, "Please select a section ");
                Toast.makeText(AddCardActivity.this, "Please select a section ", Toast.LENGTH_LONG).show();
                return;
            }
            if (frontTxt.getText().toString().isEmpty()) {
                Log.i(TAG, "Please provide Front Data ");
                Toast.makeText(AddCardActivity.this, "Please provide Front Data ", Toast.LENGTH_LONG).show();
                return;
            }
            if (backTxt.getText().toString().isEmpty()) {
                Log.i(TAG, "Please provide Back Data ");
                Toast.makeText(AddCardActivity.this, "Please provide Back Data ", Toast.LENGTH_LONG).show();
                return;
            }
            String data = " Front= " + frontTxt.getText() + " Back= " + backTxt.getText() + " Section= " + sectionTxt.getText();
            Log.i(TAG, "Data= " + data);
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBConstants.FLASH_CARD_FRONT, frontTxt.getText().toString());
            contentValues.put(DBConstants.FLASH_CARD_BACK, backTxt.getText().toString());
            contentValues.put(DBConstants.FLASH_CARD_SECTION_ID, sectionId);
            sqldbManager.insertFlashCard(contentValues);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(AddCardActivity.this, "Unable to save Flash Card data " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, AddCardActivity.class);
    }
}