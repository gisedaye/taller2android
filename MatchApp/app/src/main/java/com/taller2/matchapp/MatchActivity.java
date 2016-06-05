package com.taller2.matchapp;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import com.andtinder.model.CardModel;
import com.andtinder.view.CardContainer;
import com.andtinder.view.SimpleCardStackAdapter;


public class MatchActivity extends ActionBarActivity {

    /**
     * This variable is the container that will host our cards
     */
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private CardContainer mCardContainer;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        mCardContainer = (CardContainer) findViewById(R.id.layoutview);

          /* Assinging the toolbar object ot the view
    and setting the the Action bar to our toolbar
     */
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer);        // Drawer object Assigned to the view
        drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }


        }; // Drawer Toggle Object Made
        drawer.setDrawerListener(drawerToggle); // Drawer Listener set to the Drawer toggle
        drawerToggle.syncState();               // Finally we set the drawer toggle sync State


        Resources r = getResources();
        SimpleCardStackAdapter adapter = new SimpleCardStackAdapter(this);
        adapter.setShouldFillCardBackground(true);

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