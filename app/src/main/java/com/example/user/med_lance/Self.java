package com.example.user.med_lance;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Self extends AppCompatActivity {
private Spinner mSpinner;
    private EditText mDescription;
    private Button mSend;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabaseUsers;
    private FirebaseAuth mAuth;
    private EditText mShopping;
    TextView textLat;
    TextView textLong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self);

        mSend=(Button)findViewById(R.id.send);
        mDescription=(EditText)findViewById(R.id.description);
        mProgress=new ProgressDialog(this);
        mDatabaseUsers= FirebaseDatabase.getInstance().getReference().child("Self Request");
        mAuth= FirebaseAuth.getInstance();
        mShopping=(EditText)findViewById(R.id.shopping_self);

        mSpinner=(Spinner)findViewById(R.id.spinner);
        String [] payment={"M-pesa","Paypal","Equity Bank"};
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,payment);
        mSpinner.setAdapter(adapter);

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send();
            }
        });

        textLat = (TextView) findViewById(R.id.textLat);
        textLong = (TextView) findViewById(R.id.textLong);

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener ll = new mylocationlistener();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 5, ll);
    }

    private void send() {

        final String payment = mSpinner.getSelectedItem().toString().trim();
        final String description = mDescription.getText().toString().trim();
        final String shopping_centre = mShopping.getText().toString().trim();
        final String Latitude = textLat.getText().toString().trim();
        final String Longitude = textLong.getText().toString().trim();
        final String user_id=mAuth.getCurrentUser().getUid();
        if (!TextUtils.isEmpty(description)&&!TextUtils.isEmpty(payment)&&!TextUtils.isEmpty(shopping_centre)&&!TextUtils.isEmpty(Latitude)
                &&!TextUtils.isEmpty(Longitude)) {
            mProgress.setMessage("Sending");
            mProgress.show();
            mDatabaseUsers.child(user_id).child("payment").setValue(payment);
            mDatabaseUsers.child(user_id).child("description").setValue(description);
            mDatabaseUsers.child(user_id).child("shopping centre").setValue(shopping_centre);
            mDatabaseUsers.child(user_id).child("Latitude").setValue(Latitude);
            mDatabaseUsers.child(user_id).child("Longitude").setValue(Longitude);


            mProgress.dismiss();

            Intent mainIntent = new Intent(Self.this, Chooser.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);
            Toast.makeText(Self.this,"your request has been sent",Toast.LENGTH_LONG).show();
        }

    }
    class mylocationlistener implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {

            if(location!=null){
                double pLong = location.getLongitude();
                double pLat=location.getLatitude();

                textLat.setText(Double.toString(pLat));
                textLong.setText(Double.toString(pLong));

            }
        }

        @Override
        public void onStatusChanged(String s, int status, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

}
