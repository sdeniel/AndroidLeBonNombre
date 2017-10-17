package com.jeux.sdeniel.megagame;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText txtNumber;
    private Button btnCompare;
    private TextView lblResult;
    private ProgressBar pgbScore;
    private TextView lblHistory;

    private int searchedValue;
    private int score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNumber = (EditText) findViewById(R.id.txtNumber); //View : classe de base dont hérite tous les widgets.
        btnCompare = (Button) findViewById(R.id.btnCompare);
        lblResult = (TextView) findViewById(R.id.lblResult);
        pgbScore = (ProgressBar) findViewById(R.id.pgbScore);
        lblHistory = (TextView) findViewById(R.id.lblHistory);

        btnCompare.setOnClickListener(btnCompareListener);

        init();

    }

    private void init(){
        score = 0;
        searchedValue = 1 + (int) (Math.random() * 100);
        Log.i("Debug valeur", "valeur cherchée : " + searchedValue);

        txtNumber.setText("");
        lblResult.setText("");
        pgbScore.setProgress((score));
        lblHistory.setText("");

        txtNumber.requestFocus(); // on met le focus sur la valeur à saisir
    }

    private void congratulation(){
        lblResult.setText(R.string.strCongratulations);

        AlertDialog retryAlert = new AlertDialog.Builder( this ).create(); // pop up, boite de dialogue
        retryAlert.setTitle( R.string.app_name);
        retryAlert.setMessage(getString(R.string.strMessage, score));

        // clic bouton OK pour recommencer
        retryAlert.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.strOk), new AlertDialog.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                init();
            }
        });

        // clic bouton de refus NON pour ne pas recommencer
        retryAlert.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.strNon), new AlertDialog.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        retryAlert.show();
    }

    private void perdu(){
        lblResult.setText(R.string.strPerdu);

        AlertDialog retryAlert = new AlertDialog.Builder( this ).create(); // pop up, boite de dialogue
        retryAlert.setTitle( R.string.app_name);
        retryAlert.setMessage(getString(R.string.strMessagePerdu));

        // clic bouton OK pour recommencer
        retryAlert.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.strOk), new AlertDialog.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                init();
            }
        });

        // clic bouton de refus NON pour ne pas recommencer
        retryAlert.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.strNon), new AlertDialog.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        retryAlert.show();
    }


    private View.OnClickListener btnCompareListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            do{
                // testouille vérification evenement fonctionne sur appui bouton
            Log.i("Debug info", "Bouton cliqué");

            String strNumber = txtNumber.getText().toString();
            if (strNumber.equals("")) return;

            int enteredValue = Integer.parseInt(strNumber);

            lblHistory.append(strNumber + "\r\n");
            pgbScore.incrementProgressBy( 1 );
            score++;

            if (enteredValue == searchedValue) {
                congratulation();
            } else if (enteredValue < searchedValue){
                lblResult.setText(R.string.strGreater); // message externalisé pour dire de mettre une valeur supérieure
            }
            else {
                lblResult.setText(R.string.strLower);
            }

            txtNumber.setText("");
            } while (score < 10);

            perdu();

        }
    };
}
