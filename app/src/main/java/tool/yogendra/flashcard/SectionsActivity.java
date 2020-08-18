package tool.yogendra.flashcard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import tool.yogendra.flashcard.adapter.SectionRecyclerAdapter;
import tool.yogendra.flashcard.database.DBConstants;
import tool.yogendra.flashcard.database.SQLDBManager;
import tool.yogendra.flashcard.database.SectionParams;
import tool.yogendra.flashcard.fragment.AddSectionDialog;

public class SectionsActivity extends AppCompatActivity {

    private static final String TAG = "ManageSection";

    RecyclerView recyclerView;
    SectionRecyclerAdapter adapter;
    SQLDBManager sqldbManager;
    List<SectionParams> paramList;
    boolean isAddCard = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sections);
        setTitle(getResources().getString(R.string.menu_sections));

        String str = getIntent().getStringExtra("AddCard");
        Log.i(TAG, "String Extra = " + str);
        if (str != null && str.equals("true")) {
            isAddCard = true;
        }
        addButtonListener();
        addRecyclerView();
    }

    private void addRecyclerView() {
        sqldbManager = new SQLDBManager(this);
        recyclerView = findViewById(R.id.sectionRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        try {
            paramList = sqldbManager.getAllSectionsDataList();
            adapter = new SectionRecyclerAdapter(paramList);
            recyclerView.setAdapter(adapter);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
            itemTouchHelper.attachToRecyclerView(recyclerView);
        } catch (Exception e) {
            Toast.makeText(SectionsActivity.this, "Failed to get Sections Information " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isAddCard) {
            getMenuInflater().inflate(R.menu.save_menu, menu);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            //2saveCardData();
            if (adapter.getSelectedPosition() != -1) {
                SectionParams sectionParams = adapter.getListSections().get(adapter.getSelectedPosition());

                Intent output = new Intent();
                output.putExtra(DBConstants.SECTION_NAME, sectionParams.getSectionName());
                output.putExtra(DBConstants.SECTION_ID, sectionParams.getSectionId());
                output.putExtra(DBConstants.SECTION_COLOR, sectionParams.getSectionColor());
                setResult(RESULT_OK, output);
                finish();
                return true;
            } else {
                Toast.makeText(SectionsActivity.this, "Please Select a Section", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private ItemTouchHelper.Callback createHelperCallback() {
        return new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                deleteSection(viewHolder.getAdapterPosition());
            }

        };
    }

    private void deleteSection(int adapterPosition) {
        Log.i(TAG, "Delete Section at position - " + adapterPosition + " Name - " + paramList.get(adapterPosition).getSectionName());
        AlertDialog.Builder builder = new AlertDialog.Builder(SectionsActivity.this, R.style.datePicker);
        builder.setTitle("Delete Section");
        builder.setIcon(android.R.drawable.ic_menu_delete);
        final int pos = adapterPosition;
        builder.setMessage("Are you sure you want to delete Section : " + paramList.get(adapterPosition).getSectionName());
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {
                try {
                    int sectionId = paramList.get(pos).getSectionId();
                    String sectionName = paramList.get(pos).getSectionName();
                    String sectionColor = paramList.get(pos).getSectionColor();
                    sqldbManager.deleteSection(paramList.get(pos).getSectionId());
                    paramList.remove(pos);
                    adapter.notifyItemRemoved(pos);
                    Log.i(TAG, "Successfully Deleted Section with id - [" + sectionId + "] Name - [" + sectionName + "] Color - [" + sectionColor + "]");
                } catch (Exception e) {
                    Log.e(TAG, "Exception while deleting Section ", e);
                    Toast.makeText(SectionsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                adapter.notifyItemChanged(pos);
            }
        });
        builder.show();
    }

    private void addButtonListener() {
        //Add Button Listener for Open Fragment
        Button addBtn = findViewById(R.id.addSection);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddSectionDialog();
            }
        });
    }

    private void openAddSectionDialog() {
        Log.i(TAG, "Open Dialog to add Section");
        AddSectionDialog dialog = new AddSectionDialog();
        Bundle args = new Bundle();
        dialog.setArguments(args);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("AddAccountDialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        dialog.show(ft, "AddSectionDialog");
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "Resume ManageSection");
        super.onResume();
        addRecyclerView();
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, SectionsActivity.class);
    }
}