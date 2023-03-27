package com.example.conversordemoneda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.conversordemoneda.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel viewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainActivityViewModel.class);

//        Observar cambios con el booleano de "cambioDolar," luego habilitar algun EditText
        viewModel.getCambioDolar().observe(this, cambioDolar -> {
            if (cambioDolar) {
                binding.etDolares.setEnabled(true);
                binding.etEuros.setEnabled(false);
            } else {
                binding.etDolares.setEnabled(false);
                binding.etEuros.setEnabled(true);
            }
        });

//        Si tocan unos de los inputs radio, se cambia la moneda dentro del viewModel
        binding.rgMoneda.setOnCheckedChangeListener((group, checkedId) -> {
            viewModel.cambiarMoneda(checkedId == R.id.rbDolares ? "Dólares" : "Euros");
        });

//        Observar los cambios con el valor de "cambio"
        viewModel.getCambio().observe(this, cambio -> {
            String resultado = String.format(Locale.US, "%.2f", cambio);
            binding.tvCambio.setText(resultado);
        });

//        Si tocan convertir, verificar que halla un valor y enviarlo al viewModel
        binding.btConvertir.setOnClickListener(v -> {
            EditText etDolar = binding.etDolares;
            EditText etEuro = binding.etEuros;

            viewModel.generarCambio(etDolar, etEuro);
        });
    }
}