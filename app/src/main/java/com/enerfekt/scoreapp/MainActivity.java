package com.enerfekt.scoreapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.enerfekt.scoreapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private EditText[] guessField = new EditText[21];
    private EditText[] answerField = new EditText[21];
    private TextView[] diffText = new TextView[21];
    private Button[] calcButton = new Button[21];

    private int nr = 0;
    private int total = 0;

    private int subTotal = 0;
    private int subTotal2 = 0;
    private int subTotal3 = 0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.refresh:
                // Finish and restart activity
                finish();
                startActivity(getIntent());
                Toast.makeText(this, "Startar om!", Toast.LENGTH_SHORT).show();
                break;

            // Next menu item
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("0-100 | Poängräknaren");



        // Set arrays
        guessField = new EditText[] {binding.guessField1, binding.guessField2, binding.guessField3, binding.guessField4, binding.guessField5, binding.guessField6, binding.guessField7, binding.guessField8, binding.guessField9, binding.guessField10, binding.guessField11, binding.guessField12, binding.guessField13, binding.guessField14, binding.guessField15, binding.guessField16, binding.guessField17, binding.guessField18, binding.guessField19, binding.guessField20, binding.guessField21};
        answerField = new EditText[] {binding.answerField1, binding.answerField2, binding.answerField3, binding.answerField4, binding.answerField5, binding.answerField6, binding.answerField7, binding.answerField8, binding.answerField9, binding.answerField10, binding.answerField11, binding.answerField12, binding.answerField13, binding.answerField14, binding.answerField15, binding.answerField16, binding.answerField17, binding.answerField18, binding.answerField19, binding.answerField20, binding.answerField21};
        diffText = new TextView[] {binding.diffText1, binding.diffText2, binding.diffText3, binding.diffText4, binding.diffText5, binding.diffText6, binding.diffText7, binding.diffText8, binding.diffText9, binding.diffText10, binding.diffText11, binding.diffText12, binding.diffText13, binding.diffText14, binding.diffText15, binding.diffText16, binding.diffText17, binding.diffText18, binding.diffText19, binding.diffText20, binding.diffText21};
        calcButton = new Button[] {binding.calcButton1, binding.calcButton2, binding.calcButton3, binding.calcButton4, binding.calcButton5, binding.calcButton6, binding.calcButton7, binding.calcButton8, binding.calcButton9, binding.calcButton10, binding.calcButton11, binding.calcButton12, binding.calcButton13, binding.calcButton14, binding.calcButton15, binding.calcButton16, binding.calcButton17, binding.calcButton18, binding.calcButton19, binding.calcButton20, binding.calcButton21};

    }
    public void calculate(View v){

        int guessInput;
        int answerInput;

        // Check if any field is empty and return
        if(TextUtils.isEmpty(guessField[nr].getText()) == true){
            v.setBackgroundColor(Color.parseColor("#b00020"));
            Toast.makeText(this, "Du måste svara något!", Toast.LENGTH_SHORT).show();
            return;
        }else if(TextUtils.isEmpty(answerField[nr].getText()) == true){
            v.setBackgroundColor(Color.parseColor("#b00020"));
            Toast.makeText(this, "Du måste svara något!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(checkInput(Integer.parseInt(guessField[nr].getText().toString()))){
            guessInput = Integer.parseInt(guessField[nr].getText().toString());
        }else{
            Toast.makeText(this, "Svara mellan 0-100", Toast.LENGTH_SHORT).show();
            // Change input text to red
            guessField[nr].setTextColor(Color.parseColor("#FF0000"));
            // Set button to red
            v.setBackgroundColor(Color.parseColor("#b00020"));
            // End method
            return;
        }

        if(checkInput(Integer.parseInt(answerField[nr].getText().toString()))){
            answerInput = Integer.parseInt(answerField[nr].getText().toString());
        }else{
            Toast.makeText(this, "Svara mellan 0-100", Toast.LENGTH_SHORT).show();
            // Change input text to red
            answerField[nr].setTextColor(Color.parseColor("#FF0000"));
            // Set button to red
            v.setBackgroundColor(Color.parseColor("#b00020"));
            // End method
            return;
        }
        //Values were correct

        // Change input text back to black
        guessField[nr].setTextColor(getResources().getColor(R.color.text_light));
        answerField[nr].setTextColor(getResources().getColor(R.color.text_light));

        // Disable fields
        guessField[nr].setEnabled(false);
        answerField[nr].setEnabled(false);

        int result = calcDiff(guessInput, answerInput);
        // Check for correct answer
        if(result == 0){
            result = -10;
            diffText[nr].setTextColor(Color.parseColor("#669900"));
        }

        diffText[nr].setText(String.valueOf(result));
        // Hide button
        v.setVisibility(View.INVISIBLE);
        //Add to total
        total += result;
        binding.total.setText("Poäng: " + total);

        // Increase row number
        if(nr < 21){
            // Increase number
            nr++;
        }
        if(nr <= 20){
            // Enable next button
            calcButton[nr].setVisibility(1);
        }

        // Check and update subtotal
        updateSubTotal();



    }
    public int calcDiff(int guess, int answer) {
        int result = guess - answer;
        return Math.abs(result);
    }
    public boolean checkInput(int input){
        if(input >= 0 && input <= 100){
            return true;
        }else{
            return false;
        }
    }
    public void updateSubTotal(){

        // set total text
        if(nr == 7){
            //set subtotal text
            subTotal = total;
            binding.subTotal1.setText("Delsumma: " + subTotal);
        }
        if(nr == 14){
            subTotal2 = total - subTotal;
            binding.subTotal2.setText("Delsumma: " + subTotal2);
        }
        if(nr == 21){
            subTotal3 = total - (subTotal + subTotal2);
            binding.subTotal3.setText("Delsumma: " + subTotal3);
        }
    }
}