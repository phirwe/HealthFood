package com.example.poorwa.healthfood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.DashPathEffect;
import android.graphics.PathEffect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GraphView extends AppCompatActivity {

        TextToSpeech t1;

        ProfileDataInterface profileDataInterface = new ProfileDataInterface(this);
        MealDataInterface mealDataInterface = new MealDataInterface(this);
        Profile profile = new Profile();
        Meal meal;
        List<Meal> mealList;

        float idealY;
        float idealR = 6;
        float idealG = 3;

        float consumedY = 0;
        float consumedR = 0;
        float consumedG = 0;

        String tospeak = "";
        String displaytext = "";
        TextView BMIText;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_graph_view);

            idealY = Float.valueOf(profileDataInterface.getInfo().getGender());
            SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
            Date dateObj = new Date();
            String newDateStr = curFormater.format(dateObj);
            mealList = mealDataInterface.getAll(newDateStr);
            BMIText = (TextView) findViewById(R.id.BMIText);
            ProfileDataInterface profileDataInterface = new ProfileDataInterface(this);
            float BMIheight = Float.parseFloat(profileDataInterface.getInfo().getHeight());
            float BMIweight = Float.parseFloat(profileDataInterface.getInfo().getWeight());
            DecimalFormat df = new DecimalFormat("0.00");
            float BMI = (BMIweight) / (BMIheight * BMIheight);
            BMI = Float.parseFloat(df.format(BMI));
            String BMIfinal = String.valueOf(BMI);
            BMIText.setTextColor(Color.WHITE);

            BMIText.setText("BMI = " + BMIfinal);

            if(mealList.size() > 0) {
                mealList = mealDataInterface.getAll(newDateStr);
                for(Meal meal : mealList) {
                    switch(meal.getType()) {
                        case "Red":
                            consumedR += Float.valueOf(meal.getCalories())/80;
                            Log.println(Log.ASSERT, "Red Consumed", String.valueOf(consumedR));
                            break;
                        case "Green":
                            consumedG += Float.valueOf(meal.getCalories())/80;
                            Log.println(Log.ASSERT, "Green Consumed", String.valueOf(consumedG));
                            break;
                        case "Yellow":
                            consumedY += Float.valueOf(meal.getCalories())/80;
                            Log.println(Log.ASSERT, "Yellow Consumed", String.valueOf(consumedY));
                            break;
                    }
                }
            }
            else {
                Toast.makeText(getBaseContext(), "You have not eaten anything today!", Toast.LENGTH_LONG).show();
            }

            t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if(status != TextToSpeech.ERROR) {
                        t1.setLanguage(Locale.ENGLISH);
                        t1.setSpeechRate((float) 0.825);
                    }
                }
            });

            Button hearbutton = (Button) findViewById(R.id.hearbutton);

            hearbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(consumedY < idealY)
                        tospeak = "Eat some more grains, oil and sugar, ";
                    else
                        tospeak = "You have eaten enough carbohydrates, ";

                    t1.speak(tospeak, TextToSpeech.QUEUE_FLUSH, null);

                    while(t1.isSpeaking());

                    t1.playSilence(200, TextToSpeech.QUEUE_FLUSH, null);



                    if(consumedR < idealR)
                        tospeak = "Eat some more dairy products, meat, fish, eggs and beans, ";
                    else
                        tospeak = "You have eaten enough proteins, ";

                    t1.speak(tospeak, TextToSpeech.QUEUE_FLUSH, null);

                    while(t1.isSpeaking());

                    t1.playSilence(200, TextToSpeech.QUEUE_FLUSH, null);

                    if(consumedG < idealG)
                        tospeak = "Eat some more fruits and vegetables, ";
                    else
                        tospeak = "You have eaten enough dietary fibre, ";

                    t1.speak(tospeak, TextToSpeech.QUEUE_FLUSH, null);


                    tospeak = "";
                }
            });

            Button viewbutton = (Button) findViewById(R.id.viewbutton);

            viewbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(consumedY < idealY)
                        displaytext = displaytext + "You have not eaten enough \"yellow\" food. Yellow food includes grains, sugar, fats and oils. You should eat some more of these for the next meal.\n\n";
                    else if(consumedY == idealY)
                        displaytext = displaytext + "You have eaten enough \"yellow\" food. Yellow food includes grains, sugar, fats and oils.\n\n";
                    else if(consumedY > idealY)
                        displaytext = displaytext + "You have taken too much \"yellow\" food. Yellow food includes grains, sugar, fats and oils. You should cut down on these for the next meal.\n\n";


                    if(consumedR < idealR)
                        displaytext = displaytext + "You have not eaten enough \"red\" food. Red food includes fish, meat, eggs, milk and dairy products. You should eat some more of these for the next meal.\n\n";
                    else if(consumedR == idealR)
                        displaytext = displaytext + "You have eaten enough \"red\" food. Red food includes fish, meat, eggs, milk and dairy products.\n\n";
                    else if(consumedR > idealR)
                        displaytext = displaytext + "You have taken too much \"red\" food. Red food includes fish, meat, eggs, milk and dairy products. You should cut down on these for the next meal.\n\n";



                    if(consumedG < idealG)
                        displaytext = displaytext + "You have not eaten enough \"green\" food. Green food includes vegetables, fruits, potatoes and mushrooms. You should eat some more of these for the next meal.\n\n";
                    else if(consumedG == idealG)
                        displaytext = displaytext + "You have eaten enough \"green\" food. Green food includes vegetables, fruits, potatoes and mushrooms.\n\n";
                    else if(consumedG > idealG)
                        displaytext = displaytext + "You have taken too much \"green\" food. Green food includes vegetables, fruits, potatoes and mushrooms. You should cut down on these for the next meal.\n\n";


                    displayAlert(displaytext);

                    displaytext = "";

                }
            });




            ImageView drawingImageView;

            drawingImageView = (ImageView) this.findViewById(R.id.DrawingImageView);
            Bitmap bitmap = Bitmap.createBitmap((int) getWindowManager()
                    .getDefaultDisplay().getWidth(), (int) getWindowManager()
                    .getDefaultDisplay().getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawingImageView.setImageBitmap(bitmap);

            Paint paint = new Paint();
            paint.setStrokeWidth(10);
            Display display = getWindowManager().getDefaultDisplay();

            drawingImageView.setBackgroundColor(Color.BLACK);

            float width = canvas.getWidth();
            Log.println(Log.ASSERT, "Width", String.valueOf(width));
            float height = canvas.getHeight();
            Log.println(Log.ASSERT, "Height", String.valueOf(height));


            //get center
            //int width = display.getWidth();
            //int height = display.getHeight();
            float centroidx = width / 2;
            float centroidy = height * 2 / 3;

            paint.setColor(Color.BLACK);


            float axislength;
            if(width < height)
                axislength = (float) (width / (2 * 0.866));
            else
                axislength = (float) (height / 2.2);

            //if consumed is greater than ideal
            float ratioY = idealY / consumedY;
            float ratioR = idealR / consumedR;
            float ratioG = idealG / consumedG;
            float lengthY = axislength;
            float lengthR = axislength;
            float lengthG = axislength;

            if(ratioY < 1)
                lengthY = (float) (axislength * ratioY);
            if(ratioR < 1)
                lengthR = (float) (axislength * ratioR);
            if(ratioG < 1)
                lengthG = (float) (axislength * ratioG);

            if(ratioY < 1 || ratioG < 1 || ratioR < 1) {
                if (lengthY < lengthR && lengthY < lengthG)
                    axislength = lengthY;
                else if (lengthR < lengthY && lengthR < lengthG)
                    axislength = lengthR;
                else if (lengthG < lengthR && lengthG < lengthY)
                    axislength = lengthG;
            }


            //decide ends of axes and draw them
            float endYx = centroidx;
            float endYy = centroidy - axislength; //centroidy - 500
            paint.setColor(Color.YELLOW);
            canvas.drawLine(centroidx, centroidy, endYx, endYy, paint);

            float endRx = (float) (centroidx - (axislength * 0.866));//centroidx - 433;
            float endRy = (float) (centroidy + (axislength * 0.5));//centroidy + 250;
            paint.setColor(Color.RED);
            canvas.drawLine(centroidx, centroidy, endRx, endRy, paint);

            float endGx = (float) (centroidx + (axislength * 0.866));//centroidx + 433;
            float endGy = (float) (centroidy + (axislength * 0.5));//centroidy + 250;
            paint.setColor(Color.GREEN);
            canvas.drawLine(centroidx, centroidy, endGx, endGy, paint);


            //draw consumed triangle
            float consYx = centroidx;
            float consYy = (float) (centroidy - ((consumedY / idealY) * axislength));

            float consRx = (float) (centroidx - ((consumedR / idealR) * axislength * 0.866));
            float consRy = (float) (centroidy + ((consumedR / idealR) * axislength * 0.5));

            float consGx = (float) (centroidx + ((consumedG / idealG) * axislength * 0.866));
            float consGy = (float) (centroidy + ((consumedG / idealG) * axislength * 0.5));

            paint.setColor(Color.WHITE);

            canvas.drawLine(consYx, consYy, consRx, consRy, paint);
            canvas.drawLine(consYx, consYy, consGx, consGy, paint);
            canvas.drawLine(consGx, consGy, consRx, consRy, paint);


            DashPathEffect peff = new DashPathEffect(new float[]{ 65, 30 }, 0);

            paint.setPathEffect(peff);

            //draw ideal triangle
            paint.setColor(Color.WHITE);
            canvas.drawLine((float) (centroidx), (float) (centroidy - axislength), (float) (centroidx - (axislength * 0.866)), (float) (centroidy + (axislength * 0.5)), paint);
            canvas.drawLine((float) (centroidx), (float) (centroidy - axislength), (float) (centroidx + (axislength * 0.866)), (float) (centroidy + (axislength * 0.5)), paint);
            canvas.drawLine((float) (centroidx + (axislength * 0.866)), (float) (centroidy + (axislength * 0.5)), (float) (centroidx - (axislength * 0.866)), (float) (centroidy + (axislength * 0.5)), paint);

        }

        public void displayAlert(String displaytext) {
            new AlertDialog.Builder(this)
                    .setTitle("Diet Advice")
                    .setMessage(displaytext)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //finish();
                        }
                    })
                    .show();
        }
}