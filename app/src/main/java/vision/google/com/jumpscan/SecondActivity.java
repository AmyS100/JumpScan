package vision.google.com.jumpscan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class SecondActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button logout;
    EditText genField;
    Button btnGenerate;
    ImageView imageCode;
    String text2QR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        firebaseAuth = FirebaseAuth.getInstance();

        logout = (Button)findViewById(R.id.btnLogOut);
        genField = (EditText)findViewById(R.id.etGenerateField);
        btnGenerate = (Button)findViewById(R.id.btnGenerate);
        imageCode = (ImageView)findViewById(R.id.ivImageCode);

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text2QR = genField.getText().toString().trim();
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try{
                    BitMatrix bitMatrix = multiFormatWriter.encode(text2QR, BarcodeFormat.QR_CODE,200,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    imageCode.setImageBitmap(bitmap);
                }
                catch (WriterException e){
                    e.printStackTrace();
                }
            }
        });



      /*  btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try{
                    BitMatrix bitMatrix = multiFormatWriter.encode("text2QR", BarcodeFormat.QR_CODE, 200,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    imageCode.setImageBitmap(bitmap);
                }
                catch (WriterException e){
                    e.printStackTrace();
                }
            }
        }); */



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });
    }

    // Logout from database and bringing user to main Activity
    private void Logout() {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(SecondActivity.this, MainActivity.class));
    }


    // Code for the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoutMenu: {
                Logout();
            }
            case R.id.profileMenu:
                startActivity(new Intent(SecondActivity.this, ProfileActivity.class));
            case R.id.readerMenu:
                startActivity(new Intent (SecondActivity.this, ReaderActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
