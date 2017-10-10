package com.example.admin.blockchain;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;


public class NegotiationFormClient extends AppCompatActivity implements View.OnClickListener{

    private Toolbar mtoolbar;
    String data;
    EditText ProjectId,Title,WalletAddress,TimeFrame,HURValue;
    String projectId,title,walletAddress,timeFrame,hurValue;
    private TextView txtProjectId,txtValidTitle,txtValidWalletAddress,txtValidTimeFrame,txtValidHurValue;
    Button Accept,Decline,Back;
    private ImageView imgNavHeaderBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_negotiation_form_client);

        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setTitle("Negotiation Form");
        imgNavHeaderBg = (ImageView) findViewById(R.id.imageView);
        init();
        Accept.setOnClickListener(this);
        Decline.setOnClickListener(this);
        Back.setOnClickListener(this);



    }

    private void init() {
        data = getIntent().getExtras().getString("applier_email");
        Log.d("applier_email",data);
        Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG).show();
        txtProjectId = (TextView) findViewById(R.id.validProjectId);
        txtValidTitle = (TextView) findViewById(R.id.validTitle);
        txtValidWalletAddress = (TextView) findViewById(R.id.validWalletAddress);
        txtValidTimeFrame = (TextView) findViewById(R.id.validTimeFrame);
        txtValidHurValue = (TextView) findViewById(R.id.validHurValue);
        ProjectId = (EditText) findViewById(R.id.project_id);
        Title = (EditText) findViewById(R.id.title);
        WalletAddress = (EditText) findViewById(R.id.wallet_address);
        TimeFrame = (EditText) findViewById(R.id.time_frame);
        HURValue = (EditText) findViewById(R.id.hur_value);
        Accept = (Button) findViewById(R.id.accept);
        Decline = (Button) findViewById(R.id.decline);
        Back= (Button) findViewById(R.id.back);


    }

    @Override
    public void onClick(View v) {


        if (v.getId() == R.id.accept) {
            // Log.d(TAG,"cliclked");
            projectId = ProjectId.getText().toString().trim();
            // Log.d(TAG,name);
            if (projectId == null || projectId.isEmpty()) {
                txtProjectId.setText("ProjectId cannot be empty");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    txtProjectId.setBackground(getDrawable(R.drawable.text_border_error));
                }
                return;
            } else {
                txtProjectId.setText("");
            }

            title = Title.getText().toString().trim();
            if (title == null || title.isEmpty()) {
                txtValidTitle.setText("Tiltle cannot be empty");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    txtValidTitle.setBackground(getDrawable(R.drawable.text_border_error));
                }
                return;
            } else {
                txtValidTitle.setText("");
            }
            walletAddress = WalletAddress.getText().toString().trim();
            if (walletAddress == null || walletAddress.isEmpty()) {
                txtValidWalletAddress.setText("Wallet Address cannot be empty");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    txtValidWalletAddress.setBackground(getDrawable(R.drawable.text_border_error));
                }
                return;
            } else {
                txtValidWalletAddress.setText("");
            }
            timeFrame = TimeFrame.getText().toString().trim();
            if (timeFrame == null || timeFrame.isEmpty()) {
                txtValidTimeFrame.setText("Time frame cannot be empty");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    txtValidTimeFrame.setBackground(getDrawable(R.drawable.text_border_error));
                }
                return;
            } else {
                txtValidTimeFrame.setText("");
            }
            hurValue = HURValue.getText().toString().trim();
            if (hurValue == null || hurValue.isEmpty()) {
                txtValidHurValue.setText("HUR Value cannot be empty");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    txtValidHurValue.setBackground(getDrawable(R.drawable.text_border_error));
                }
                return;
            } else {
                txtValidHurValue.setText("");
            }

            Toast.makeText(getApplicationContext(),"go to next Activity",Toast.LENGTH_LONG).show();
        }

        if(v.getId()==R.id.back)
        {
            onBackPressed();
        }
        if(v.getId()==R.id.decline)
        {
            onBackPressed();
        }

    }
}
