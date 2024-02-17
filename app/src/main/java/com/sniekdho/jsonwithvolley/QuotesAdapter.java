package com.sniekdho.jsonwithvolley;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.QuotesViewHolder> {

    private Context context;
    private ArrayList<Quotes> quotesArrayList;

    public QuotesAdapter(Context context, ArrayList<Quotes> quotesArrayList) {
        this.context = context;
        this.quotesArrayList = quotesArrayList;
    }

    @NonNull
    @Override
    public QuotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_view, parent, false);

        return new QuotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuotesViewHolder holder, int position) {
        Quotes quotes = quotesArrayList.get(position);
        holder.quoteTV.setText(quotes.getQuote());
        holder.authorTV.setText(quotes.getAuthor());

        holder.copyImageBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("textView", holder.quoteTV.getText().toString().trim());
                assert clipboardManager != null;
                clipboardManager.setPrimaryClip(clipData);

                /*
                ClipData clipData1 = ClipData.newHtmlText("textview", holder.quoteTV.getText().toString().trim(), null);
                clipboardManager.setPrimaryClip(clipData1);
                */

                Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show();


                /*
                ClipboardManager clipboardManager1 = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData.Item item = clipboardManager1.getPrimaryClip().getItemAt(0);
                displayTV.setText(item.getText().toString());

                Toast.makeText(context, "Pasted", Toast.LENGTH_SHORT).show();
                */
            }
        });

        holder.shareImageBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, holder.quoteTV.getText().toString().trim());
                shareIntent.setType("text/plain");
                shareIntent = Intent.createChooser(shareIntent, "Share Via:");
                context.startActivity(shareIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return quotesArrayList.size();
    }

    public class QuotesViewHolder extends RecyclerView.ViewHolder{

        private TextView quoteTV, authorTV;
        private ImageButton copyImageBTN, shareImageBTN;

        public QuotesViewHolder(@NonNull View itemView) {
            super(itemView);

            quoteTV = itemView.findViewById(R.id.quoteTV);
            authorTV = itemView.findViewById(R.id.authorTV);

            copyImageBTN = itemView.findViewById(R.id.copyImageBTN);
            shareImageBTN = itemView.findViewById(R.id.shareImageBTN);
        }
    }

}
