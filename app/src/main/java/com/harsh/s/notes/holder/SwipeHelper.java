package com.harsh.s.notes.holder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeHelper extends ItemTouchHelper.SimpleCallback {

    HomeActivity.Recyclerview_tasksAdapter adapter;
    public SwipeHelper(HomeActivity.Recyclerview_tasksAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition();
        if(direction == ItemTouchHelper.LEFT){
            adapter.refresh();
        } else if (direction == ItemTouchHelper.RIGHT) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(adapter.getContext());
            dialog.setTitle("Are you sure?");
            dialog.setMessage("Are you sure you want to delete this Task?\nThis Can't be undone.");
            dialog.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    adapter.delete(position);
                }
            });
            dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    adapter.notifyItemChanged(position);
                }
            });
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    adapter.refresh();
                }
            });
            dialog.show();

        }else{

        }



    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        Drawable icon;
        ColorDrawable background;

        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 20;

        if(dX>0){
            icon = ContextCompat.getDrawable(adapter.getContext(), android.R.drawable.ic_menu_delete);
            background = new ColorDrawable(0xFFF44336);
        }else{
            icon = ContextCompat.getDrawable(adapter.getContext(), android.R.drawable.ic_menu_edit);
            background = new ColorDrawable(0xFF0084C2);
        }

        int iconMargin = (itemView.getHeight()-icon.getIntrinsicHeight())/2;
        int iconTop = itemView.getTop()+ (itemView.getHeight()-icon.getIntrinsicHeight())/2;
        int iconBottom = iconTop + icon.getIntrinsicHeight();

        if(dX>0){
            //right
            int iconLeft = itemView.getLeft() + iconMargin;
            int iconRight = itemView.getLeft() +iconMargin + icon.getIntrinsicWidth();
            icon.setBounds(iconLeft,iconTop,iconRight,iconBottom);


            background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft()+((int)dX+backgroundCornerOffset), itemView.getBottom());


        }else if(dX <0){
            //left
            int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
            int iconRight = itemView.getRight() + iconMargin;
            icon.setBounds(iconLeft,iconTop,iconRight,iconBottom);


            background.setBounds(itemView.getRight() + ((int)dX) - backgroundCornerOffset, itemView.getTop(), itemView.getRight(), itemView.getBottom());

        }else{
            background.setBounds(0,0,0,0);
        }
        background.draw(c);
        icon.draw(c);


    }
}
