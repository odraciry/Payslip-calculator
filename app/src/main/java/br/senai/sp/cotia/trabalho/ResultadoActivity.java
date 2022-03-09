package br.senai.sp.cotia.trabalho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ResultadoActivity extends AppCompatActivity {

    private TextView tvSL, tvSB, tvTrans, tvAlimen, tvRefei, tvInss, tvIrrf, tvPlano;
    private double salarioL, salarioB, vt, va, vr, inss, irrf, plano;
    private Button btVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        tvSB = findViewById(R.id.tvSB);
        tvAlimen = findViewById(R.id.tvAlimen);
        tvRefei = findViewById(R.id.tvRefei);
        tvTrans = findViewById(R.id.tvTrans);
        tvPlano = findViewById(R.id.tvPlano);
        tvInss = findViewById(R.id.tvInss);
        tvIrrf = findViewById(R.id.tvIrrf);
        tvSL = findViewById(R.id.tvSL);
        btVoltar = findViewById(R.id.bt_voltar);

        salarioB = getIntent().getDoubleExtra("salarioB", 0);
        tvSB.setText(getString(R.string.salario_bruto, salarioB));

        vt = getIntent().getDoubleExtra("vt", 0);
        tvTrans.setText(getString(R.string.transporte, vt));

        va = getIntent().getDoubleExtra("va", 0);
        tvAlimen.setText(getString(R.string.alimentacao, va));

        vr = getIntent().getDoubleExtra("vr", 0);
        tvRefei.setText(getString(R.string.refeicao, vr));

        plano = getIntent().getDoubleExtra("plano", 0);
        tvPlano.setText(getString(R.string.plano_saude, plano));

        inss = getIntent().getDoubleExtra("inss", 0);
        tvInss.setText(getString(R.string.inss, inss));

        irrf = getIntent().getDoubleExtra("irrf", 0);
        tvIrrf.setText(getString(R.string.irrf, irrf));

        salarioL = getIntent().getDoubleExtra("salarioL", 0);
        tvSL.setText(getString(R.string.salario_liquido, salarioL));

        btVoltar.setOnClickListener(v -> {
            Intent main = new Intent(this, MainActivity.class);
            startActivity(main);
            finish();
        });
    }
}