package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.notes.databinding.ActivityDataInsertActivtyBinding;

public class DataInsertActivty extends AppCompatActivity {
    ActivityDataInsertActivtyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataInsertActivtyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String type = getIntent().getStringExtra("type");
        if (type.equals("update")) {
            setTitle("update");
            binding.title.setText(getIntent().getStringExtra("title"));
            binding.disp.setText(getIntent().getStringExtra("disp"));
            int id = getIntent().getIntExtra("id", 0);
            binding.add.setText("Update note");
            binding.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("title", binding.title.getText().toString());
                    intent.putExtra("disp", binding.disp.getText().toString());
                    intent.putExtra("id",id);
                    setResult(RESULT_OK, intent);//indicate that an activity has completed successfully and provides a result back to the calling activity
                    finish();
                }
            });
        } else {
            setTitle("Add Mode");
            binding.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("title", binding.title.getText().toString());
                    intent.putExtra("disp", binding.disp.getText().toString());
                    setResult(RESULT_OK, intent);//indicate that an activity has completed successfully and provides a result back to the calling activity
                    finish();
                }
            }
            );
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DataInsertActivty.this, MainActivity.class));
    }
}