package tool.yogendra.flashcard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import tool.yogendra.flashcard.adapter.FlashCardRecyclerAdapter;
import tool.yogendra.flashcard.card.AddCardActivity;
import tool.yogendra.flashcard.database.FlashCardParams;
import tool.yogendra.flashcard.database.SQLDBManager;
import tool.yogendra.flashcard.utils.Constants;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    FlashCardRecyclerAdapter adapter;
    SQLDBManager sqldbManager;
    List<FlashCardParams> paramList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sqldbManager = new SQLDBManager(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = AddCardActivity.makeIntent(HomeActivity.this);
                startActivity(addIntent);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        addRecyclerView();
        addButtonListener();
    }

    private void addButtonListener() {
        Button summaryBtn = findViewById(R.id.summaryBtn);
        summaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSummaryOfRecords();
            }
        });
    }

    private void addRecyclerView() {
        recyclerView = findViewById(R.id.flashCardRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        try {
            paramList = sqldbManager.getAllFlashCardsDataList();
            adapter = new FlashCardRecyclerAdapter(paramList);
            recyclerView.setAdapter(adapter);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
            itemTouchHelper.attachToRecyclerView(recyclerView);
        } catch (Exception e) {
            Toast.makeText(HomeActivity.this, "Failed to get Flash Card Information " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private ItemTouchHelper.Callback createHelperCallback() {
        return new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //deleteFlashCard(viewHolder.getAdapterPosition());
            }

        };
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        addRecyclerView();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.nav_trash) {
            Intent addIntent = TrashActivity.makeIntent(HomeActivity.this);
            startActivity(addIntent);
        } else if (id == R.id.nav_sections) {
            Intent addIntent = SectionsActivity.makeIntent(HomeActivity.this);
            startActivity(addIntent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showSummaryOfRecords() {
        try {

            Map<String, Integer> records = sqldbManager.getAllAccountsSummary();
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
            builder.setTitle("Records Summary");
            builder.setMessage("Total FlashCard - " + records.get(Constants.SUMMARY_FLASH_CARD_COUNT_KEY) + "\n"
                    + "Total Sections - " + records.get(Constants.SUMMARY_SECTION_COUNT_KEY) + "\n");
            builder.setPositiveButton("Ok", null);
            builder.show();
        } catch (Exception e) {
            Log.e("HomeActivity", "Exception while getting records summary", e);
            Toast.makeText(HomeActivity.this, "Failed to get Summary " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}