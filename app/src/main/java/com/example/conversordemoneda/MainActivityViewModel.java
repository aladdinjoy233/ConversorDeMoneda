package com.example.conversordemoneda;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MainActivityViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> cambioDolar = null;
    private MutableLiveData<Double> cambio = null;

    private Context context;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<Boolean> getCambioDolar() {
        if (cambioDolar == null) {
            cambioDolar = new MutableLiveData<>();
        }
        return cambioDolar;
    }

    public LiveData<Double> getCambio() {
        if (cambio == null) {
            cambio = new MutableLiveData<>();
        }
        return cambio;
    }

    public void cambiarMoneda(String moneda) {
        if (moneda.equals("Dólares")) {
            cambioDolar.setValue(true);
        } else {
            cambioDolar.setValue(false);
        }
    }

    public void generarCambio(EditText etDolar, EditText etEuro) {
        if (cambioDolar == null) { return; }
        double resultado;

        if (TextUtils.isEmpty(etDolar.getText()) && TextUtils.isEmpty(etEuro.getText())) {
            return;
        } else if (!TextUtils.isEmpty(etDolar.getText()) && cambioDolar.getValue()) {
            resultado = Double.parseDouble(etDolar.getText().toString()) * 0.93;
        } else if (!TextUtils.isEmpty(etEuro.getText()) && !cambioDolar.getValue()) {
            resultado = Double.parseDouble(etEuro.getText().toString()) / 0.93;
        } else {
            return;
        }

        cambio.setValue(resultado);
    }
}
