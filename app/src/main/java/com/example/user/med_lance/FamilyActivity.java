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

public class FamilyActivity extends AppCompatActivity {

    private Spinner mSpinner2;
    private Spinner mPayment;
    private EditText mAge;
    private EditText mCondition;
    private EditText mName;
    private Button mSend;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabaseUsers;
    private FirebaseAuth mAuth;
    private EditText mShopping_family;
    TextView textLat;
    TextView textLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);

        mSpinner2=(Spinner)findViewById(R.id.spinner2);
        String [] relationship={"Wife","Husband","Kid","Sibling"};
        ArrayAdapter adapter1=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,relationship);
        mSpinner2.setAdapter(adapter1);

        mPayment=(Spinner)findViewById(R.id.paymentFamily);
        String [] payment={"M-pesa","Paypal","Equity Bank"};
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,payment);
        mPayment.setAdapter(adapter);

        mAge=(EditText)findViewById(R.id.AgeFamily);
        mCondition=(EditText)findViewById(R.id.conditionFamily);
        mName=(EditText)findViewById(R.id.nameFamily);
        mProgress=new ProgressDialog(this);
        mDatabaseUsers= FirebaseDatabase.getInstance().getReference().child("Family Request");
        mAuth= FirebaseAuth.getInstance();
        mShopping_family=(EditText)findViewById(R.id.shopping_family);

        mSend=(Button)findViewById(R.id.SendFamily);
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendFamily();
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

    private void SendFamily() {

        final String payment = mPayment.getSelectedItem().toString().trim();
        final String relationship = mSpinner2.getSelectedItem().toString().trim();
        final String condition = mCondition.getText().toString().trim();
        final String name = mName.getText().toString().trim();
        final String shopping = mShopping_family.getText().toString().trim();
        final String Age = mAge.getText().toString().trim();
        final String user_id=mAuth.getCurrentUser().getUid();
        if (!TextUtils.isEmpty(relationship)&&!TextUtils.isEmpty(payment)&&!TextUtils.isEmpty(condition)&&!TextUtils.isEmpty(name)&&
        !TextUtils.isEmpty(Age)&&!TextUtils.isEmpty(shopping)) {
            mProgress.setMessage("Sending");
            mProgress.show();
            mDatabaseUsers.child(user_id).child("payment").setValue(payment);
            mDatabaseUsers.child(user_id).child("description").setValue(condition);
            mDatabaseUsers.child(user_id).child("relationship").setValue(relationship);
            mDatabaseUsers.child(user_id).child("Full Name").setValue(name);
            mDatabaseUsers.child(user_id).child("Age").setValue(Age);
            mDatabaseUsers.child(user_id).child("shopping").setValue(shopping);


            mProgress.dismiss();

            Intent mainIntent = new Intent(FamilyActivity.this, Chooser.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);

            Toast.makeText(FamilyActivity.this,"your request has been sent",Toast.LENGTH_LONG).show();
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
