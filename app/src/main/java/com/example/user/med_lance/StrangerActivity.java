package com.example.user.med_lance;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class StrangerActivity extends AppCompatActivity {

    private Spinner mAge;
    private Spinner mGender;
    private EditText mApproximateAge;
    private EditText mDescriptrion;
    private Button mSend;
    private EditText mShopping;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabaseUsers;
    private FirebaseAuth mAuth;
    TextView textLat;
    TextView textLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stranger);

        mAge=(Spinner)findViewById(R.id.Age);
        String [] Age={"Senior Citizen","Youth","Child"};
        ArrayAdapter adapter1=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,Age);
       mAge.setAdapter(adapter1);

        mGender=(Spinner)findViewById(R.id.Gender);
        String [] gender={"Female","Male"};
        ArrayAdapter adapter2=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,gender);
        mGender.setAdapter(adapter2);
        mApproximateAge=(EditText)findViewById(R.id.ApproximateAge);
        mDescriptrion=(EditText)findViewById(R.id.Condition);
        mSend=(Button)findViewById(R.id.sendStranger);
        mShopping=(EditText)findViewById(R.id.shoppingcentre);
        mProgress=new ProgressDialog(this);
        mDatabaseUsers= FirebaseDatabase.getInstance().getReference().child("Stranger Request");
        mAuth= FirebaseAuth.getInstance();

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

        final String Age = mAge.getSelectedItem().toString().trim();
        final String Gender = mGender.getSelectedItem().toString().trim();
        final String condition = mDescriptrion.getText().toString().trim();
        final String nearest_shopping_center = mShopping.getText().toString().trim();
        final String Approximate_age = mApproximateAge.getText().toString().trim();
        final String user_id=mAuth.getCurrentUser().getUid();
        if (!TextUtils.isEmpty(Gender)&&!TextUtils.isEmpty(nearest_shopping_center)&&!TextUtils.isEmpty(condition)&&!TextUtils.isEmpty(Approximate_age)&&
                !TextUtils.isEmpty(Age)) {
            mProgress.setMessage("Sending");
            mProgress.show();
            mDatabaseUsers.child(user_id).child("Gender").setValue(Gender);
            mDatabaseUsers.child(user_id).child("description").setValue(condition);
            mDatabaseUsers.child(user_id).child("Shopping center").setValue(nearest_shopping_center);
            mDatabaseUsers.child(user_id).child("Approximate_Age").setValue(Approximate_age);
            mDatabaseUsers.child(user_id).child("Age").setValue(Age);


            mProgress.dismiss();

            Intent mainIntent = new Intent(StrangerActivity.this, Chooser.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);
            Toast.makeText(StrangerActivity.this,"your request has been sent",Toast.LENGTH_LONG).show();
        }

    }
    class mylocationlistener implements LocationListener {

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
