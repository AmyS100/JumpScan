package vision.google.com.jumpscan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentDetailsActivity extends AppCompatActivity {

    TextView txtId, txtAmount, txtStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        txtId = (TextView)findViewById(R.id.tvTxtId);
        txtAmount = (TextView)findViewById(R.id.tvTxtAmount);
        txtStatus = (TextView)findViewById(R.id.tvtxtStatus);


        //get Intent
        Intent intent = getIntent();

        // using JSONObject to show details from TopUpActivity.
        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
            showDetails(jsonObject.getJSONObject("response"),intent.getStringExtra("PaymentAmount"));

        } catch (JSONException e){
            e.printStackTrace();
        }

    }

    //showing the deails taken from the paypal account
    private void showDetails(JSONObject response, String paymentAmount) {
        try {
            txtId.setText(response.getString("id"));
            txtStatus.setText(response.getString("State"));
            txtAmount.setText("€"+paymentAmount);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
