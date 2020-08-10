import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerAct extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    private DatabaseReference databaseReference;
    String batch, byConduct, Room, CourseCon, Name;
    private FirebaseUser user;
    Handler handler;
    LinearLayout linearLay, SuccessLay;
    private DatabaseReference currentRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);
        setContentView(R.layout.activity_scanner);
        linearLay = findViewById(R.id.linearLay_id);
        SuccessLay = findViewById(R.id.SuccessLay_id);

        linearLay.addView(mScannerView);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public void handleResult(com.google.zxing.Result result) {
        Intent intent1 = getIntent();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("CLASS");
        builder.setMessage(result.getText());
        Room = result.getText();
        MarkMyAttendance();
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        mScannerView.resumeCameraPreview(this);

    }

    ValueEventListener listener = null;

    private void MarkMyAttendance() {
        //Toast.makeText(this,"in MarkAttend",Toast.LENGTH_SHORT).show();
        if (user != null) {
            //Toast.makeText(this,"Authenticated user",Toast.LENGTH_SHORT).show();
            currentRef = databaseReference.child("Class Rooms").child(Room);
            listener = currentRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        Toast.makeText(ScannerAct.this, "Authenticated user", Toast.LENGTH_SHORT).show();
                        batch = dataSnapshot.child("Batch").getValue(String.class);
                        byConduct = dataSnapshot.child("By").getValue(String.class);
                        CourseCon = dataSnapshot.child("Course").getValue(String.class);
                        DatabaseReference NameRef = databaseReference.child("users").child(batch);
                        if (user != null) {
                            NameRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Name = dataSnapshot.child(user.getUid()).getValue(String.class);
                                    Setter();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(ScannerAct.this, "Course not registered", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            });
                        }

                    } catch (Exception e) {
                        Toast.makeText(ScannerAct.this, "Error:" + e, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            // No user is signed in
        }
    }
    private void Setter()
    {
        final Date date = new Date();
        final SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy");

        try {
            DatabaseReference tempRef = databaseReference.child(formatter.format(date)).child(batch).child(CourseCon).child(Name);
            tempRef.setValue("P");
            linearLay.setVisibility(View.GONE);
            SuccessLay.setVisibility(View.VISIBLE);
            currentRef.removeEventListener(listener);
            FirebaseAuth.getInstance().signOut();
            finish();
        }catch (Exception e){
            Toast.makeText(ScannerAct.this, "Course not registered", Toast.LENGTH_SHORT).show();
        }
    }
}
