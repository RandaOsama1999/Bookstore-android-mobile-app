package com.example.project.ShoppingCart;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.project.Books.UserProfileBooks;
import com.example.project.Mail.GMailSender;
import com.example.project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class paymentmethod extends AppCompatActivity  {
    ArrayList<String> booksPurchased=new ArrayList<>();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String user_id=user.getUid();
    double totalprice=0.0;
    DatabaseReference databaseReference,databaseReference2,databaseReference3,databaseReference4;
    ImageView visacard,mastercard;
    EditText cardnumber,cardholder,etexpdate,etcvv;
    TextWatcher textWatcher;
    TextView fill,warning,expdate,cvv;
    Button buy;
    boolean formisgood;
    Animation animShake;
    final Calendar myCalendar = Calendar.getInstance();
    AlertDialog.Builder builder1;
    Context con=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentmethod);

        visacard = findViewById(R.id.visa);
        mastercard = findViewById(R.id.mastercard);
        cardnumber = findViewById(R.id.cardnumber);
        cardholder = findViewById(R.id.cardholder);
        warning = findViewById(R.id.warning);
        fill = findViewById(R.id.fill);
        etexpdate = findViewById(R.id.etexpdate);
        etcvv = findViewById(R.id.etcvv);
        expdate = findViewById(R.id.expdat);
        cvv = findViewById(R.id.cvv);
        buy=findViewById(R.id.click);
        animShake = AnimationUtils.loadAnimation(con, R.anim.shake);
        textWatcher=new TextWatcher() {

            private static final char space = ' ';







            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                if (!cardnumber.getText().toString().isEmpty()) {
                    if (cardnumber.getText().toString().charAt(0) == '4') {
                        mastercard.setColorFilter(ContextCompat.getColor(con, R.color.darkmode));
                        visacard.clearColorFilter();
//                        cardnumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.visaicon, 0);
                    } else if (cardnumber.getText().toString().charAt(0) == '5') {
                        visacard.setColorFilter(ContextCompat.getColor(con, R.color.darkmode));
                        mastercard.clearColorFilter();
//                        cardnumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.mastericon, 0);

                    } else {

                        mastercard.setColorFilter(ContextCompat.getColor(con, R.color.darkmode));
                        visacard.setColorFilter(ContextCompat.getColor(con, R.color.darkmode));
//                        cardnumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

                    }


                }


                else {
                    mastercard.clearColorFilter();
                    visacard.clearColorFilter();
//                    cardnumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);


                }


            }




            @Override
            public void afterTextChanged(Editable editable) {
                if (cardnumber.getText().length() > 0 && (cardnumber.getText().length() % 5) == 0) {
                    final char c = cardnumber.getText().charAt(cardnumber.getText().length() - 1);
                    if (space == c) {
                        cardnumber.getText().delete(cardnumber.getText().length() - 1, cardnumber.getText().length());
                    }
                }

                if (cardnumber.getText().length() > 0 && (cardnumber.getText().length() % 5) == 0) {
                    char c = cardnumber.getText().charAt(cardnumber.getText().length() - 1);

                    if (Character.isDigit(c) && TextUtils.split(cardnumber.getText().toString(), String.valueOf(space)).length <= 3) {
                        cardnumber.getText().insert(cardnumber.getText().length() - 1, String.valueOf(space));
                    }
                }
            }


        };
        cardnumber.addTextChangedListener(textWatcher);


        etcvv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    cvv.setVisibility(View.INVISIBLE);
                }
            }
        });
        cardnumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if(b){
                    warning.setVisibility(View.INVISIBLE);
                }
            }
        });
        cardholder.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    fill.setVisibility(View.INVISIBLE);

                }
            }
        });
        cardholder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fill.setVisibility(View.INVISIBLE);
            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        etexpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(con, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                expdate.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void clicked(View view) {
        formisgood=true;
        String test2 = cardnumber.getText().toString().replace(" ","");
        String test=cardholder.getText().toString().replace(" ","");
        int garab=test2.length();
        if(test.isEmpty()&& test.length()<=2){
            fill.setVisibility(View.VISIBLE);
            formisgood=false;

            if(!test2.isEmpty()) {
                if(test2.length()!=16 || (test2.charAt(0)!='4' && test2.charAt(0)!='5') ) {
                    formisgood = false;
                    warning.setVisibility(View.VISIBLE);
                }
            }
            else {
                warning.setVisibility(View.VISIBLE);
                formisgood = false;
            }
        }
        if(etexpdate.getText().toString().isEmpty()){
            expdate.setVisibility(View.VISIBLE);
            formisgood=false;
        }
        if(etcvv.getText().toString().length()<3){
            cvv.setVisibility(View.VISIBLE);
            formisgood=false;
        }
        if(formisgood==false){
            buy.startAnimation(animShake);
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));
            } else {

                v.vibrate(500);
            }
        }
        else {
            builder1 = new AlertDialog.Builder(con);
            builder1.setTitle("Payment Confirmaiton")
                    .setMessage("CardHolder : "+cardholder.getText().toString()+"\n"+
                            "CardNumber: "+cardnumber.getText().toString()+"\n"+
                            "ExpDate : "+etexpdate.getText().toString()+"\n"+
                            "CVV : "+etcvv.getText().toString()
                    )
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            databaseReference= FirebaseDatabase.getInstance().getReference("cart");
                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot bookstore : dataSnapshot.getChildren()) {
                                        if (bookstore.child("userid").getValue().toString().equals(user_id)) {
                                            double Price=Double.parseDouble(bookstore.child("price").getValue().toString());
                                            //Book bookp=new Book(bookstore.child("bookTitle").getValue().toString(),Price);
                                            booksPurchased.add(bookstore.child("bookTitle").getValue().toString());
                                            totalprice=totalprice+Price;
                                        }
                                    }
                                    Date c = Calendar.getInstance().getTime();
                                    System.out.println("Current time => " + c);
                                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                                    String formattedDate = df.format(c);

                                    Payment payment=new Payment(user_id,String.valueOf(totalprice),formattedDate);
                                    databaseReference2= FirebaseDatabase.getInstance().getReference("payment");
                                    databaseReference2.push().setValue(payment);
                                    for (DataSnapshot bookstore2 : dataSnapshot.getChildren()) {
                                        if (bookstore2.child("userid").getValue().toString().equals(user_id)) {
                                            final String bookid=bookstore2.child("id").getValue().toString();

                                            databaseReference3= FirebaseDatabase.getInstance().getReference("books");
                                            databaseReference3.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    for (DataSnapshot bookstore3 : dataSnapshot.getChildren()) {
                                                        if (bookstore3.child("id").getValue().toString().equals(bookid)) {
                                                            int quan=Integer.parseInt(bookstore3.child("quantity").getValue().toString())-1;
                                                            bookstore3.child("quantity").getRef().setValue(quan);
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                            bookstore2.getRef().removeValue();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            Toast.makeText(con, "Thank you", Toast.LENGTH_SHORT).show();
                            databaseReference4= FirebaseDatabase.getInstance().getReference("users");
                            databaseReference4.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot bookstore4 : dataSnapshot.getChildren()) {
                                        if (bookstore4.child("id").getValue().toString().equals(user_id)) {
                                            String usermail=bookstore4.child("email").getValue().toString();
                                            String fname=bookstore4.child("fname").getValue().toString();
                                            String lname=bookstore4.child("lname").getValue().toString();
                                            if (Build.VERSION.SDK_INT > 9) {
                                                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                                StrictMode.setThreadPolicy(policy);
                                            }
                                            String formattedString = booksPurchased.toString()
                                                    .replace("[", "")  //remove the right bracket
                                                    .replace("]", "")  //remove the left bracket
                                                    .trim();
                                            try {
                                                GMailSender sender = new GMailSender("eqraabookstorenoreply@gmail.com", "eqraa123");
                                                sender.sendMail("Thank you for your purchase!",
                                                        "Hi " + fname + " " + lname + ", \n Just to let you know we've recieved your order of the following books: " +
                                                                formattedString
                                                                + " with total " + String.valueOf(totalprice) + "EGP, paid with visa and it's now being processed. The" +
                                                                " delivery will be within 2-5 working days.",
                                                        "eqraabookstorenoreply@gmail.com", usermail);
                                            } catch (Exception e) {
                                                Log.e("SendMail", e.getMessage(), e);
                                            }

                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            Intent intent=new Intent(getApplicationContext(), UserProfileBooks.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();


        }
    }
    private void updateLabel()
    {
        String myFormat = "MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etexpdate.setText(sdf.format(myCalendar.getTime()));
    }
}
