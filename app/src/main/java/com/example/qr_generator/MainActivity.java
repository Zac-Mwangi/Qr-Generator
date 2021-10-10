package com.example.qr_generator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class MainActivity extends AppCompatActivity {

    EditText et_input;
    Button btn_generate;
    ImageView iv_output;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_input = findViewById(R.id.et_input);
        btn_generate = findViewById(R.id.btn_generate);
        iv_output = findViewById(R.id.iv_output);

        btn_generate.setOnClickListener(v -> {
            //get inputs from edit text
            String sText = et_input.getText().toString().trim();
            if (sText.equals("")) {
                Toast.makeText(getApplicationContext(), "Input cannot be empty", Toast.LENGTH_SHORT).show();
            } else {
                //initialize MultiFormatWriter
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                //initialize bitMatrix, in encode surround with try catch
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(sText, BarcodeFormat.QR_CODE, 500, 500);

                    //initialize barcode encoder
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();

                    //initialize bitmap
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

                    //set Bitmap on imageView
                    iv_output.setImageBitmap(bitmap);

                    //initialize input manager
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(
                            Context.INPUT_METHOD_SERVICE
                    );

                    //hide soft keyboard
                    inputMethodManager.hideSoftInputFromWindow(et_input.getApplicationWindowToken(), 0);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}