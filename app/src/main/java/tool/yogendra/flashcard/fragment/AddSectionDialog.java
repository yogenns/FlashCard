package tool.yogendra.flashcard.fragment;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

import tool.yogendra.flashcard.R;
import tool.yogendra.flashcard.adapter.SectionRecyclerAdapter;
import tool.yogendra.flashcard.database.DBConstants;
import tool.yogendra.flashcard.database.SQLDBManager;
import tool.yogendra.flashcard.database.SectionParams;


public class AddSectionDialog extends DialogFragment {


    private String TAG = "AddSectionDialog";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_section, container, false);
        Button saveSectionBtn = view.findViewById(R.id.saveSectionBtn);
        saveSectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getDialog() != null) {
                    EditText sectionName = getDialog().findViewById(R.id.sectionNameTxt);
                    if (sectionName.getText().toString().isEmpty()) {
                        Toast.makeText(getActivity(), "Please provide name for section ", Toast.LENGTH_LONG).show();
                        return;
                    }
                    ChipGroup colorChipGroup = getDialog().findViewById(R.id.colorChipGroup);
                    Log.i(TAG, " selectedChipId " + colorChipGroup.getCheckedChipId());
                    if (colorChipGroup.getCheckedChipId() == -1) {
                        Toast.makeText(getActivity(), "Please pick a color for section ", Toast.LENGTH_LONG).show();
                        return;
                    }
                    Chip selectedChip = getDialog().findViewById(colorChipGroup.getCheckedChipId());
                    if (selectedChip == null || selectedChip.getChipBackgroundColor() == null) {
                        Toast.makeText(getActivity(), "Please pick a color for section ", Toast.LENGTH_LONG).show();
                        return;
                    }
                    int colorInt = selectedChip.getChipBackgroundColor().getDefaultColor();
                    Log.i(TAG, "sectionName " + sectionName.getText().toString() + " sectionColor " + colorInt);
                    String color = String.format("#%06X", (0xFFFFFF & colorInt));
                    Log.i(TAG, "colorHex " + color);

                    try {
                        SQLDBManager sqldbManager = new SQLDBManager(getActivity());
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(DBConstants.SECTION_NAME, sectionName.getText().toString());
                        contentValues.put(DBConstants.SECTION_COLOR, color);
                        int sectionId = sqldbManager.insertSection(contentValues);
                        if (getActivity() != null) {
                            RecyclerView recyclerView = getActivity().findViewById(R.id.sectionRecyclerView);
                            if (recyclerView.getAdapter() != null) {
                                List<SectionParams> paramList = ((SectionRecyclerAdapter) recyclerView.getAdapter()).getListSections();
                                int insertIndex = paramList.size();
                                Log.i(TAG, "Recycler Adapter Insert Data at " + insertIndex + " with Section Id " + sectionId);
                                ((SectionRecyclerAdapter) recyclerView.getAdapter()).getListSections().add(insertIndex, new SectionParams(sectionId,
                                        sectionName.getText().toString(), color));
                                Log.i(TAG, "Recycler Adapter Notify Data Inserted");
                                recyclerView.getAdapter().notifyItemInserted(insertIndex);
                            }
                        }
                        getDialog().dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Unable to save Section data " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Unable to find Dialog to Save data", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Cancel Button
        Button cancelSectionDialogBtn = view.findViewById(R.id.cancelSectionDialogBtn);
        cancelSectionDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getDialog() != null) {
                    getDialog().dismiss();
                } else {
                    Toast.makeText(getActivity(), "Unable to find Dialog", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
