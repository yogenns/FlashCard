package tool.yogendra.flashcard.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tool.yogendra.flashcard.R;
import tool.yogendra.flashcard.database.FlashCardParams;

public class FlashCardRecyclerAdapter extends RecyclerView.Adapter<FlashCardRecyclerAdapter.ViewHolder> {
    private List<FlashCardParams> listFlashCards;

    public FlashCardRecyclerAdapter(List<FlashCardParams> listFlashCards) {
        this.listFlashCards = listFlashCards;
    }

    @NonNull
    @Override
    public FlashCardRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_flash, parent, false);
        return new FlashCardRecyclerAdapter.ViewHolder(v);
    }

    int selectedPosition = -1;

    @Override
    public void onBindViewHolder(@NonNull final FlashCardRecyclerAdapter.ViewHolder holder, final int position) {
        final FlashCardParams item = listFlashCards.get(position);
        holder.flashCardFront.setText(item.getFlashCardFront());
        holder.flashCardBack.setText(item.getFlashCardBack());
        if (item.getFlashCardSectionColor() != null && !item.getFlashCardSectionColor().isEmpty()) {
            holder.flashCardSectionColor.setBackgroundColor(Color.parseColor(item.getFlashCardSectionColor()));
        } else {
            holder.flashCardSectionColor.setBackgroundColor(Color.parseColor("#FF424242")); // Gray
        }
        if (selectedPosition == position) {
            holder.flashCardFrontLayout.setVisibility(View.INVISIBLE);
            holder.flashCardBackLayout.setVisibility(View.VISIBLE);
            holder.flashCardView.setCardBackgroundColor(Color.parseColor("#FC0434")); // Red
        } else {
            holder.flashCardFrontLayout.setVisibility(View.VISIBLE);
            holder.flashCardBackLayout.setVisibility(View.INVISIBLE);
            holder.flashCardView.setCardBackgroundColor(Color.parseColor("#FF424242")); // Gray
        }
        holder.flashCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("OnClick", "Front Visibility " + holder.flashCardFrontLayout.getVisibility());
                Log.i("OnClick", "Back Visibility " + holder.flashCardBackLayout.getVisibility());
                if (selectedPosition != position) {
                    selectedPosition = position;
                } else {
                    selectedPosition = -1;
                }
                notifyDataSetChanged();
                //Log.i("OnClick","");
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFlashCards.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView flashCardView;
        TextView flashCardFront;
        TextView flashCardSectionColor;
        TextView flashCardBack;
        LinearLayout flashCardFrontLayout;
        LinearLayout flashCardBackLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            flashCardView = itemView.findViewById(R.id.flashCardView);
            flashCardFront = itemView.findViewById(R.id.flashCardFrontTxt);
            flashCardSectionColor = itemView.findViewById(R.id.flashCardSectionColorTxt);
            flashCardBack = itemView.findViewById(R.id.flashCardBackTxt);
            flashCardFrontLayout = itemView.findViewById(R.id.frontLayout);
            flashCardBackLayout = itemView.findViewById(R.id.backLayout);
        }
    }
}
