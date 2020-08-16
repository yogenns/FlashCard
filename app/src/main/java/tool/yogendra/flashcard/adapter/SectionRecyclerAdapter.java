package tool.yogendra.flashcard.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tool.yogendra.flashcard.R;
import tool.yogendra.flashcard.database.SectionParams;

public class SectionRecyclerAdapter extends RecyclerView.Adapter<SectionRecyclerAdapter.ViewHolder> {

    private List<SectionParams> listSections;
    private String TAG = "SectionRecyclerAdapter";

    public SectionRecyclerAdapter(List<SectionParams> listSections) {
        this.listSections = listSections;
    }

    @NonNull
    @Override
    public SectionRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_section, parent, false);
        return new ViewHolder(v);
    }

    int selectedPosition = -1;

    @Override
    public void onBindViewHolder(@NonNull SectionRecyclerAdapter.ViewHolder holder, final int position) {
        if (selectedPosition == position) {
            holder.sectionCardSelected.setImageResource(R.drawable.ic_checked);
        } else {
            holder.sectionCardSelected.setImageDrawable(null);
        }
        final SectionParams item = listSections.get(position);
        holder.sectionCardName.setText(item.getSectionName());
        holder.sectionCardColor.setBackgroundColor(Color.parseColor(item.getSectionColor()));
        holder.sectionCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = position;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.v(TAG, "Section Size= " + listSections.size());
        return listSections.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView sectionCardView;
        TextView sectionCardName;
        TextView sectionCardColor;
        ImageView sectionCardSelected;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            sectionCardView = itemView.findViewById(R.id.sectionCardView);
            sectionCardName = itemView.findViewById(R.id.sectionCardNameTxt);
            sectionCardColor = itemView.findViewById(R.id.sectionCardColorTxt);
            sectionCardSelected = itemView.findViewById(R.id.sectionCardSelected);
        }
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public List<SectionParams> getListSections() {
        return this.listSections;
    }
}
