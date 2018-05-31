package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
*Please NOTE: I am resubmitting this project. Therefore, I have created a new Layout
* from scratch. The XML file for new layout is activity_detail_revised.
 */

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    TextView alsoKnownTV,originTV,descriptionTV,ingredientsTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_revised);

        ImageView ingredientsIv = getLayoutInflater().inflate(R.layout.header_image,null).findViewById(R.id.image_iv2);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
//        Log.d("tag",""+position);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            //Log.d("tag","sandwich is null");
            closeOnError();
            return;
        }



        populateUI(sandwich);

        expandableListView = findViewById(R.id.expandableListView);
        expandableListView.addHeaderView(getLayoutInflater().inflate(R.layout.header_image,null));

        //New updated Library of Picasso has been used
        Picasso.get()
                .load(sandwich.getImage())
                .placeholder(R.drawable.ic_broken_image)
                .error(R.drawable.ic_broken_image)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());


        expandableListAdapter = new ExpandableListViewAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expandableListView.setAdapter(expandableListAdapter);


    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        alsoKnownTV = findViewById(R.id.also_known_tv);
        originTV = findViewById(R.id.origin_tv);
        descriptionTV = findViewById(R.id.description_tv);
        ingredientsTV = findViewById(R.id.ingredients_tv);

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listDataHeader.add("Also Known As");
        listDataHeader.add("Place of Origin");
        listDataHeader.add("Description");
        listDataHeader.add("Ingredients");

        //Preparing List and adding it in HashMap data structure (i.e. List Data of child )

            ArrayList<String> alsoKnownAsList = (ArrayList<String>) sandwich.getAlsoKnownAs();
            listDataChild.put(listDataHeader.get(0), alsoKnownAsList);

        List<String> originList = new ArrayList<>();
        originList.add(sandwich.getPlaceOfOrigin());
        listDataChild.put(listDataHeader.get(1),originList);


        List<String> descriptionList = new ArrayList<>();
        descriptionList.add(sandwich.getDescription());
        listDataChild.put(listDataHeader.get(2),descriptionList);


            ArrayList<String> ingredientsList = (ArrayList<String>) sandwich.getIngredients();
            listDataChild.put(listDataHeader.get(3), ingredientsList);
    }
}
