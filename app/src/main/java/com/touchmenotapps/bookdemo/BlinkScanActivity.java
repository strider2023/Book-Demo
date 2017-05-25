package com.touchmenotapps.bookdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.microblink.activity.SegmentScanActivity;
import com.microblink.ocr.ScanConfiguration;
import com.microblink.recognizers.blinkocr.parser.generic.AmountParserSettings;
import com.microblink.recognizers.blinkocr.parser.generic.EMailParserSettings;
import com.microblink.recognizers.blinkocr.parser.generic.RawParserSettings;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BlinkScanActivity extends AppCompatActivity {

    private final int MY_REQUEST_CODE = 1;

    @BindView(R.id.scanned_text)
    TextView scannedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blink_scan);
        ButterKnife.bind(this);

        /** Set the license key */
        Intent intent = new Intent(this, SegmentScanActivity.class);
        intent.putExtra(SegmentScanActivity.EXTRAS_LICENSE_KEY, "LRHBYB35-T5Q3AX6G-X2RF7FSS-WJC2NCI5-6TVUYGXT-X7C7QKE2-HPZAMSMF-Z6HGHF4L");
        ScanConfiguration[] confArray = new ScanConfiguration[] {
                new ScanConfiguration(R.string.amount_title, R.string.amount_msg, "Amount", new AmountParserSettings()),
                new ScanConfiguration(R.string.email_title, R.string.email_msg, "EMail", new EMailParserSettings()),
                new ScanConfiguration(R.string.raw_title, R.string.raw_msg, "Raw", new RawParserSettings())
        };
        intent.putExtra(SegmentScanActivity.EXTRAS_SCAN_CONFIGURATION, confArray);

        startActivityForResult(intent, MY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode == SegmentScanActivity.RESULT_OK && data != null) {
                // perform processing of the data here

                // for example, obtain parcelable recognition result
                Bundle extras = data.getExtras();
                Bundle results = extras.getBundle(SegmentScanActivity.EXTRAS_SCAN_RESULTS);

                // results bundle contains result strings in keys defined
                // by scan configuration name
                // for example, if set up as in step 1, then you can obtain
                // e-mail address with following line
                String email = results.getString("EMail");
                String amount = results.getString("Amount");
                String raw = results.getString("Raw");
                if(email != null) {
                    scannedText.setText(scannedText.getText() + " " + email);
                }
                if(amount != null) {
                    scannedText.setText(scannedText.getText() + " " + amount);
                }
                if(raw != null) {
                    scannedText.setText(scannedText.getText() + " " + raw);
                }
            }
        }
    }
}
