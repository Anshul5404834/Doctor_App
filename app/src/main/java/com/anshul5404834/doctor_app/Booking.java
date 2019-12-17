package com.anshul5404834.doctor_app;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class Booking extends AppCompatActivity implements PaymentResultListener {
    String code;
    String uid;
    String phone;
    TextView textView;
    EditText message_edit;
    DateFormat dateFormat2= new SimpleDateFormat("dd/MM");
    String date=dateFormat2.format(Calendar.getInstance().getTime());
    DateFormat dateFormat3= new SimpleDateFormat("HH:mm");
    String time=dateFormat3.format(Calendar.getInstance().getTime());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_layout);
        Button continue_button= findViewById(R.id.continue_button);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.button);
        Button send_message=findViewById(R.id.send_message_booking);

        textView = findViewById(R.id.textView6);
        textView.setVisibility(View.INVISIBLE);

        message_edit = findViewById(R.id.mes_booking);
        Checkout.preload(getApplicationContext());
        send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_message.startAnimation(myAnim);
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.setType("image/png");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using "), 12121);
            }
        });
        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continue_button.startAnimation(myAnim);
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("issue");
                DatabaseReference databaseReference1=databaseReference.child(getIntent().getStringExtra("ref"));
                //        databaseReference1.setValue(new specialtiy_pojo(message_edit.getText().toString()));
                databaseReference1.child("message").setValue((message_edit.getText().toString()));
                startPayment();

            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            try {
                // When an Image is picked
                if (requestCode == 12121 && resultCode == RESULT_OK
                        && null != data) {
                    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("issue");
                    DatabaseReference databaseReference1=databaseReference.child(getIntent().getStringExtra("ref"));
                    // Get the Image from data
                    if(data.getData()!=null){
                        Uri mImageUri=data.getData();

                        textView.setText(mImageUri.getLastPathSegment().toString());
                        save_image_to_firebase(databaseReference1.getKey(),mImageUri);
                    } else {
                        if (data.getClipData() != null) {
                            ClipData mClipData = data.getClipData();
                            for (int i = 0; i < mClipData.getItemCount(); i++) {
                                ClipData.Item item = mClipData.getItemAt(i);
                                Uri uri = item.getUri();

                                String text=uri.getLastPathSegment().toString();
                                textView.setText(textView.getText()+"\n"+text);
                                save_image_to_firebase(databaseReference1.getKey(),uri);
                            }
                        }

                    }
                    Toast.makeText(this, getString(R.string.Images_has_been_uploaded_successfully),Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, getApplicationContext().getString(R.string.have_not_picked_image),
                            Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                        .show();
            }
            super.onActivityResult(requestCode, resultCode, data);

        }


public void save_image_to_firebase(String ref_key,Uri uri){
    final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("issue").child(ref_key).child("Images");
    final StorageReference profile_photo_Reference= FirebaseStorage.getInstance().getReference().child("booking_photos").child(uri.getLastPathSegment());
    //compressing image
    textView.setVisibility(View.VISIBLE);
    Bitmap bmp = null;
    try {
        bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
    } catch (IOException e) {
        e.printStackTrace();
    }
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    //here you can choose quality factor in third parameter(ex. i choosen 25)
    bmp.compress(Bitmap.CompressFormat.JPEG, 20, baos);
    byte[] fileInBytes = baos.toByteArray();
    // adding to data base
    profile_photo_Reference.putBytes(fileInBytes).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            profile_photo_Reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Uri uri1 = uri;
                    String uri_string = uri1.toString();
                    uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    phone = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();

                    //  date=date+"/2019";
                    //  String string_time=date+" at "+ time;
                    specialtiy_pojo friendlyMessage = new specialtiy_pojo(uri_string);
                    databaseReference.push().child("image").setValue(uri_string);
                    //               uri_string,string_time,uid);
                ;
                }


            });
        }
    });
}
        // ...

        @Override
        public void onPaymentSuccess(String razorpayPaymentID) {
            /**
             * Add your logic here for a successful payment response
             */
            FirebaseDatabase database=FirebaseDatabase.getInstance();
            DatabaseReference reference=database.getReference("Appointment").child(getIntent().getStringExtra("ref"));
            reference.child("paid").setValue(true);

            Intent intent= new Intent(Booking.this,Thankyou.class);
            startActivity(intent);
        }

        @Override
        public void onPaymentError(int code, String response) {
            /**
             * Add your logic here for a failed payment response
             *
             */
            setContentView(R.layout.thankyou);
            TextView abcdefgh=findViewById(R.id.abcdefgh);
            abcdefgh.setText(response);
            TextView abcd=findViewById(R.id.booked);
            abcd.setText(getApplicationContext().getString(R.string.payment_unsuccessful));
            Button button= findViewById(R.id.thankyou_button);
            button.setText(getApplicationContext().getString(R.string.retry));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button);

                    button.startAnimation(myAnim);
                   startPayment();
                }
            });
        }

    public void startPayment() {
        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();

        /**
         * Set your logo here
         */

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            /**
             * Merchant Name
             * eg: ACME Corp || HasGeek etc.
             *
             */
            options.put("name", "Rekop");

            /**
             * Description can be anything
             * eg: Order #123123
             *     Invoice Payment
             *     etc.
             */
            options.put("description", "Order #123456");

            options.put("currency", "INR");

            /**
             * Amount is always passed in PAISE
             * Eg: "500" = Rs 5.00
             */
            options.put("amount", getIntent().getStringExtra("amount")+"00");

            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e("Razorpay", "Error in starting Razorpay Checkout", e);
        }
    }

}