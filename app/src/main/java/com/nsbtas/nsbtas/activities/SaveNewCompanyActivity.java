package com.nsbtas.nsbtas.activities;

import static com.nsbtas.nsbtas.utils.MultiStepPaymentFormHelper.getCurrentPage;
import static com.nsbtas.nsbtas.utils.MultiStepPaymentFormHelper.setRequiredFields;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.contentful.java.cma.CMACallback;
import com.contentful.java.cma.model.CMAEntry;
import com.google.android.material.textfield.TextInputLayout;
import com.nsbtas.nsbtas.R;
import com.nsbtas.nsbtas.network.CMAClient;
import com.nsbtas.nsbtas.utils.Callback;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class SaveNewCompanyActivity extends AppCompatActivity {

    private TextInputLayout etCompanyName;
    private TextInputLayout etCustomerName;
    private TextInputLayout etPhoneNumber;
    private TextInputLayout etEmailAddress;
    private TextInputLayout etNote;
    private TextInputLayout etPhysicalAddress;
    private Callback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_new_company);

        SharedPreferences sharedPreferences = getSharedPreferences("NSBTAS", Context.MODE_PRIVATE);
        AppCompatButton btnSave = findViewById(R.id.btnSave);
        etCompanyName = findViewById(R.id.etCompanyName);
        etCustomerName = findViewById(R.id.etCustomerName); // required
        etPhoneNumber = findViewById(R.id.etPhoneNumber); // required
        etEmailAddress = findViewById(R.id.etEmailAddress);
        etNote = findViewById(R.id.etNote); // required
        etPhysicalAddress = findViewById(R.id.etPhysicalAddress); // required
        List<TextInputLayout> requiredFields = Arrays.asList(etCustomerName, etPhoneNumber, etNote, etPhysicalAddress);

        this.callback = this::finish;

        btnSave.setOnClickListener(view -> {
            /*
            for (TextInputLayout field : requiredFields) {
                if (Objects.requireNonNull(field.getEditText()).getText().toString().isEmpty()) {
                    field.setError("Bu alan boş bırakılamaz");
                }
            }
             */
            final CMAEntry newCompany = new CMAEntry()
                    .setId(String.valueOf(UUID.randomUUID()))
                    .setField("companyName", "en-US", getCompanyName())
                    .setField("customerName", "en-US", getCustomerName())
                    .setField("phoneNumber", "en-US", getPhoneNumber())
                    .setField("emailAddress", "en-US", getEmailAddress())
                    .setField("note", "en-US", getNote())
                    .setField("address", "en-US", getPhysicalAddress());

            CMAClient.getClient().entries().async().create("company", newCompany, new CMACallback<CMAEntry>() {
                @Override
                protected void onSuccess(CMAEntry resultCompany) {
                    CMAClient.getClient().entries().async().fetchOne(sharedPreferences.getString("userEntryId", "empty"), new CMACallback<CMAEntry>() {
                        @Override
                        protected void onSuccess(CMAEntry result) {
                            List<CMAEntry> cmaCompanies = result.getField("companies", "en-US");
                            cmaCompanies.add(resultCompany);
                            result.setField("companies", "en-US", cmaCompanies);
                            CMAClient.getClient().entries().async().update(result, new CMACallback<CMAEntry>() {
                                @Override
                                protected void onSuccess(CMAEntry result) {
                                    CMAClient.getClient().entries().async().publish(result, new CMACallback<CMAEntry>() {
                                        @Override
                                        protected void onSuccess(CMAEntry result) {
                                        }
                                    });
                                }

                                @Override
                                protected void onFailure(RuntimeException exception) {
                                    super.onFailure(exception);
                                }
                            });
                        }
                    });
                }

                @Override
                protected void onFailure(RuntimeException exception) {
                    super.onFailure(exception);
                }
            });
            this.callback.callback();
        });
    }

    public String getCompanyName() {
        return Objects.requireNonNull(etCompanyName.getEditText()).getText().toString();
    }

    public String getCustomerName() {
        return Objects.requireNonNull(etCustomerName.getEditText()).getText().toString();
    }

    public String getEmailAddress() {
        return Objects.requireNonNull(etEmailAddress.getEditText()).getText().toString();
    }

    public String getPhoneNumber() {
        return Objects.requireNonNull(etPhoneNumber.getEditText()).getText().toString();
    }

    public String getPhysicalAddress() {
        return Objects.requireNonNull(etPhysicalAddress.getEditText()).getText().toString();
    }

    public String getNote() {
        return Objects.requireNonNull(etNote.getEditText()).getText().toString();
    }
}