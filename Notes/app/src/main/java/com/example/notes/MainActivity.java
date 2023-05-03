package com.example.notes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.notes.Adapter.RVAdapter;
import com.example.notes.Model.Notes;
import com.example.notes.ViewModel.NotesViewModel;
import com.example.notes.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private NotesViewModel notesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notesViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory
                .getInstance((Application) this.getApplicationContext())).get(NotesViewModel.class);
        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DataInsertActivty.class);
                intent.putExtra("type","addMode");
                startActivityForResult(intent, 1);
            }
        });
        binding.RV.setLayoutManager(new LinearLayoutManager(this));
        binding.RV.setHasFixedSize(true);
        RVAdapter adapter = new RVAdapter();
        binding.RV.setAdapter(adapter);

        notesViewModel.getAllNotes().observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                adapter.submitList(notes);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.RIGHT) {
                    notesViewModel.delete(adapter.getNote(viewHolder.getAdapterPosition()));
                    Toast.makeText(MainActivity.this, "Notes deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Updating", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, DataInsertActivty.class);
                    intent.putExtra("type","update");
                    intent.putExtra("title",adapter.getNote(viewHolder.getAdapterPosition()).getTitle());
                    intent.putExtra("disp",adapter.getNote(viewHolder.getAdapterPosition()).getDisp());
                    intent.putExtra("id",adapter.getNote(viewHolder.getAdapterPosition()).getId());
                    startActivityForResult(intent,2);
                }
            }
        }).attachToRecyclerView(binding.RV);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            String title = data.getStringExtra("title");
            String disp = data.getStringExtra("disp");
            Notes notes = new Notes(title, disp);
            notesViewModel.insert(notes);
            Toast.makeText(this, "Successfully added note: " + title, Toast.LENGTH_SHORT).show();
        }else if(requestCode==2){
            String title = data.getStringExtra("title");
            String disp = data.getStringExtra("disp");
            Notes notes = new Notes(title, disp);
            notes.setId(data.getIntExtra("id",0));
            notesViewModel.update(notes);
        }
    }
}