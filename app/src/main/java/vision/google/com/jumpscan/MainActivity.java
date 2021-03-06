package vision.google.com.jumpscan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.FallbackServiceBroker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private int counter = 5;
    private TextView userRegistration;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView forgotPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText)findViewById(R.id.etName);
        Password = (EditText)findViewById(R.id.etPassword);
        Info = (TextView) findViewById(R.id.tvInfo);
        Login = (Button)findViewById(R.id.btnLogin);
        userRegistration = (TextView)findViewById(R.id.tvRegister);
        forgotPassword = (TextView)findViewById(R.id.tvForgotPassword);


        Info.setText("No of attempts remaining: 5");

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);


        FirebaseUser user = firebaseAuth.getCurrentUser();



        //checking if a user is already logged in or not
        if(user != null){
            finish();
            startActivity(new Intent(MainActivity.this, SecondActivity.class));
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(Name.getText().toString(),Password.getText().toString());
            }
        });


        // this is the function when the user is on the Login page and wants to click the Register button
        //it will bring the user back to the Register page (Register Activity)
        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });


        // this is the function when the user is on the Login page and wants to reset there password
        //it will bring the user back to the password activity page (Register Activity)
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PasswordActivity.class));
            }
        });

    }





    // Signing in with email and password
    // with a progress dialog

    private void validate(String userName, String userPassword) {

        progressDialog.setMessage("You are nearly Logged in!");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, SecondActivity.class));
                }else
                {
                    Toast.makeText(MainActivity.this, "Login Failed Username or password is incorrect", Toast.LENGTH_SHORT).show();
                    counter--;
                    Info.setText("No of attempts remaining: " + counter);
                    progressDialog.dismiss();
                    if (counter == 0){
                        Login.setEnabled(false);
                    }
                }
            }
        });


    }





    // checking email verification for user to recieve a verify email

    private void checkEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();

        startActivity(new Intent(MainActivity.this, SecondActivity.class));

//       if(emailflag) {
 //           startActivity(new Intent(MainActivity.this, SecondActivity.class));
  //      }else{
    //        Toast.makeText(this, "Verify your email", Toast.LENGTH_SHORT).show();
      //      firebaseAuth.signOut();
       // }


    }

}
