import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    String Email,Pass;
    private  EditText EmailE,PassE;
    private static final String TAG="MainActivity";
    static private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EmailE=findViewById(R.id.UserName_id);
        PassE=findViewById(R.id.Pass_id);
        mAuth= FirebaseAuth.getInstance();
    }

    public void LogMeIn(View view) {
        Email=EmailE.getText().toString();
        Pass=PassE.getText().toString();

        if(Email.length()>0&&Pass.length()>0) {
            mAuth.signInWithEmailAndPassword(Email, Pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(MainActivity.this, ScannerAct.class);
                                startActivity(intent);
                                Log.d(TAG, "signInWithEmail:success");
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }else{
            Toast.makeText(this,"Enter Valid Email & Password",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
}
