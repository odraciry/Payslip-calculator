package br.senai.sp.cotia.trabalho;

import static br.senai.sp.cotia.trabalho.R.id.refeicao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText slBruto, numDependentes;
    private Button btCalcular, btLimpar;
    private RadioGroup refeicaoGroup, alimentacaoGroup, transporteGroup;
    private double vr, vt, va;
    private Spinner spinner;
    private double plano;
    private double irrf;
    private double inss;
    private double salarioL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slBruto = findViewById(R.id.sl_bruto);
        numDependentes = findViewById(R.id.num_dependentes);
        btCalcular = findViewById(R.id.bt_calcular);
        btLimpar = findViewById(R.id.bt_limpar);
        refeicaoGroup = findViewById(R.id.refeicao);
        alimentacaoGroup = findViewById(R.id.alimentacao);
        transporteGroup = findViewById(R.id.transporte);
        spinner = findViewById(R.id.spinner_plano);

        btCalcular.setOnClickListener(v -> {
            if (slBruto.getText().toString().isEmpty()) {
                Toast.makeText(getBaseContext(), R.string.valida_sb, Toast.LENGTH_SHORT).show();
            } else if (numDependentes.getText().toString().isEmpty()) {
                Toast.makeText(getBaseContext(), R.string.valida_dependentes, Toast.LENGTH_SHORT).show();
            } else {
                double salarioB = Double.parseDouble(slBruto.getText().toString());
                int numDep = Integer.parseInt(numDependentes.getText().toString());
                //calculo do plano
                if (salarioB <= 3000) {
                    if (spinner.getSelectedItemPosition() == 0) {
                        plano = 60;
                    } else if (spinner.getSelectedItemPosition() == 1) {
                        plano = 80;
                    } else if (spinner.getSelectedItemPosition() == 2) {
                        plano = 95;
                    } else {
                        plano = 130;
                    }
                } else {
                    if (spinner.getSelectedItemPosition() == 0) {
                        plano = 80;
                    } else if (spinner.getSelectedItemPosition() == 1) {
                        plano = 110;
                    } else if (spinner.getSelectedItemPosition() == 2) {
                        plano = 135;
                    } else {
                        plano = 180;
                    }
                }
                //calculo
                switch (transporteGroup.getCheckedRadioButtonId()) {
                    case R.id.sim_transporte:
                        vt = salarioB * 0.06;
                        break;
                    case R.id.nao_transporte:
                        vt = 0;
                        break;
                    default:
                        Toast.makeText(getBaseContext(), R.string.valida_vt, Toast.LENGTH_SHORT).show();
                        return;
                }
                switch (alimentacaoGroup.getCheckedRadioButtonId()) {
                    case R.id.sim_alimentacao:
                        if (salarioB <= 3000) {
                            va = 15;
                        } else if (salarioB <= 5000) {
                            va = 25;
                        } else {
                            va = 35;
                        }
                        break;
                    case R.id.nao_alimentacao:
                        va = 0;
                        break;
                    default:
                        Toast.makeText(getBaseContext(), R.string.valida_va, Toast.LENGTH_SHORT).show();
                        return;
                }
                switch (refeicaoGroup.getCheckedRadioButtonId()) {
                    case R.id.sim_refeicao:
                        if (salarioB <= 3000) {
                            vr = 2.6 * 22;
                        } else if (salarioB <= 5000) {
                            vr = 3.65 * 22;
                        } else {
                            vr = 6.5 * 22;
                        }
                        break;
                    case R.id.nao_refeicao:
                        vr = 0;
                        break;
                    default:
                        Toast.makeText(getBaseContext(), R.string.valida_vr, Toast.LENGTH_SHORT).show();
                        return;

                }


                if (salarioB < 1212.01) {
                    inss = salarioB * 0.075;
                } else if (salarioB < 2427.36) {
                    inss = salarioB * 0.09 - 18.18;
                } else if (salarioB < 3641.04) {
                    inss = salarioB * 0.12 - 91;
                } else if (salarioB < 7087.23) {
                    inss = salarioB * 0.14 - 163.82;
                } else {
                    inss = 828.39;
                }

                irrf = salarioB - inss - (189.59 * numDep);
                if (irrf < 1903.99) {
                    irrf = 0;
                } else if (irrf < 2826.66) {
                    irrf = irrf * 0.075 - 142.80;
                } else if (irrf < 3751.06) {
                    irrf = irrf * 0.15 - 354.80;
                } else if (irrf < 4664.69) {
                    irrf = irrf * 0.225 - 636.13;
                } else {
                    irrf = irrf * 0.275 - 869.36;
                }
                salarioL = salarioB - inss - vt - vr - va - irrf - plano;


                Intent result = new Intent(this, ResultadoActivity.class);

                result.putExtra("salarioL", salarioL);
                result.putExtra("salarioB", salarioB);
                result.putExtra("vt", vt);
                result.putExtra("vr", vr);
                result.putExtra("va", va);
                result.putExtra("irrf", irrf);
                result.putExtra("plano", plano);
                result.putExtra("inss", inss);

                startActivity(result);
                finish();
            }
            Log.wtf("", salarioL + "");

        });
        btLimpar.setOnClickListener(l -> {
            slBruto.setText("");
            numDependentes.setText("");
            spinner.setSelection(0);
            alimentacaoGroup.clearCheck();
            refeicaoGroup.clearCheck();
            transporteGroup.clearCheck();
            slBruto.requestFocus();
        });
    }
}
