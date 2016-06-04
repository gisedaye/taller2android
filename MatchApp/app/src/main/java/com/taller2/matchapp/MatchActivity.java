package com.taller2.matchapp;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Window;
import com.andtinder.model.CardModel;
import com.andtinder.view.CardContainer;
import com.andtinder.view.SimpleCardStackAdapter;


public class MatchActivity extends ActionBarActivity {

    /**
     * This variable is the container that will host our cards
     */
    private CardContainer mCardContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        mCardContainer = (CardContainer) findViewById(R.id.layoutview);

        Resources r = getResources();

        SimpleCardStackAdapter adapter = new SimpleCardStackAdapter(this);

        adapter.setShouldFillCardBackground(true);

        adapter.add(new CardModel("Title1", "Person Name (26)", r.getDrawable(R.drawable.picture1)));
        adapter.add(new CardModel("Title2", "Person Name (26)", r.getDrawable(R.drawable.picture2)));
        adapter.add(new CardModel("Title3", "Person Name (26)", r.getDrawable(R.drawable.picture3)));
        adapter.add(new CardModel("Title4", "Person Name (26)", r.getDrawable(R.drawable.picture1)));
        adapter.add(new CardModel("Title5", "Person Name (26)", r.getDrawable(R.drawable.picture2)));
        adapter.add(new CardModel("Title6", "Person Name (26)", r.getDrawable(R.drawable.picture3)));
        adapter.add(new CardModel("Title1", "Person Name (26)", r.getDrawable(R.drawable.picture1)));
        adapter.add(new CardModel("Title2", "Person Name (26)", r.getDrawable(R.drawable.picture2)));
        adapter.add(new CardModel("Title3", "Person Name (26)", r.getDrawable(R.drawable.picture3)));
        adapter.add(new CardModel("Title4", "Person Name (26)", r.getDrawable(R.drawable.picture1)));
        adapter.add(new CardModel("Title5", "Person Name (26)", r.getDrawable(R.drawable.picture2)));
        adapter.add(new CardModel("Title6", "Person Name (26)", r.getDrawable(R.drawable.picture3)));
        adapter.add(new CardModel("Title1", "Person Name (26)", r.getDrawable(R.drawable.picture1)));
        adapter.add(new CardModel("Title2", "Person Name (26)", r.getDrawable(R.drawable.picture2)));
        adapter.add(new CardModel("Title3", "Person Name (26)", r.getDrawable(R.drawable.picture3)));
        adapter.add(new CardModel("Title4", "Person Name (26)", r.getDrawable(R.drawable.picture1)));
        adapter.add(new CardModel("Title5", "Person Name (26)", r.getDrawable(R.drawable.picture2)));
        adapter.add(new CardModel("Title6", "Person Name (26)", r.getDrawable(R.drawable.picture3)));
        adapter.add(new CardModel("Title1", "Person Name (26)", r.getDrawable(R.drawable.picture1)));
        adapter.add(new CardModel("Title2", "Person Name (26)", r.getDrawable(R.drawable.picture2)));
        adapter.add(new CardModel("Title3", "Person Name (26)", r.getDrawable(R.drawable.picture3)));
        adapter.add(new CardModel("Title4", "Person Name (26)", r.getDrawable(R.drawable.picture1)));
        adapter.add(new CardModel("Title5", "Person Name (26)", r.getDrawable(R.drawable.picture2)));
        adapter.add(new CardModel("Title6", "Person Name (26)", r.getDrawable(R.drawable.picture3)));
        adapter.add(new CardModel("Title1", "Person Name (26)", r.getDrawable(R.drawable.picture1)));
        adapter.add(new CardModel("Title2", "Person Name (26)", r.getDrawable(R.drawable.picture2)));
        adapter.add(new CardModel("Title3", "Person Name (26)", r.getDrawable(R.drawable.picture3)));
        adapter.add(new CardModel("Title4", "Person Name (26)", r.getDrawable(R.drawable.picture1)));
        adapter.add(new CardModel("Title5", "Person Name (26)", r.getDrawable(R.drawable.picture2)));

        CardModel cardModel = new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.picture1));
        cardModel.setOnClickListener(new CardModel.OnClickListener() {
            @Override
            public void OnClickListener() {
                Log.i("Swipeable Cards", "I am pressing the card");
            }
        });

        cardModel.setOnCardDismissedListener(new CardModel.OnCardDismissedListener() {
            @Override
            public void onLike() {
                Log.i("Swipeable Cards", "I like the card");
            }

            @Override
            public void onDislike() {
                Log.i("Swipeable Cards", "I dislike the card");
            }
        });

        adapter.add(cardModel);

        mCardContainer.setAdapter(adapter);
    }
}