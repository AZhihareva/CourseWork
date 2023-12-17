package com.example.app4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.app4.Models.Box;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class BoxTakerActivity extends AppCompatActivity {

    EditText editText_title, editText_notes;
    ImageView imageView_save;
    Box box;
    boolean isOldBox = false;

    private int getRandomColor() {

        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.blue);
        colorCode.add(R.color.brown);
        colorCode.add(R.color.yellow);
        colorCode.add(R.color.cyan);
        colorCode.add(R.color.purple);
        colorCode.add(R.color.pink);
        colorCode.add(R.color.orange);
        colorCode.add(R.color.red);
        colorCode.add(R.color.green);

        Random rnd = new Random();
        int rnd_color = rnd.nextInt(colorCode.size());
        return colorCode.get(rnd_color);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_taker);

        imageView_save = findViewById(R.id.imageView_save);
        editText_notes = findViewById(R.id.editText_notes);
        editText_title = findViewById(R.id.editText_title);

        box = new Box();

        try {

            box = (Box) getIntent().getSerializableExtra("old_box");
            editText_title.setText(box.getTitle());
            editText_notes.setText(box.getNotes());
            isOldBox = true;

        }
        catch(Exception e) {

            e.printStackTrace();
        }

        imageView_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = editText_title.getText().toString();
                String note = editText_notes.getText().toString();

                if(note.isEmpty()) {
                    Toast.makeText(BoxTakerActivity.this, "Please, enter your text", Toast.LENGTH_SHORT).show();
                    return;
                }

                SimpleDateFormat formatt = new SimpleDateFormat("EEE, d MMM yyyy HH:mm");
                Date date = new Date();

                if(!isOldBox) {
                    box = new Box();
                }

                box.setTitle(title);
                box.setNotes(note);
                box.setDate(formatt.format(date));
                box.setColor(getRandomColor());

                Intent intent = new Intent();
                intent.putExtra("box", box);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}