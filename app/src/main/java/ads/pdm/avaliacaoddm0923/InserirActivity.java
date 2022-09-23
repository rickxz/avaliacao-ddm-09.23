package ads.pdm.avaliacaoddm0923;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class InserirActivity extends AppCompatActivity {
    private EditText txtTexto;
    private RadioGroup radioGroup;
    private RadioButton radVermelho;
    private RadioButton radVerde;
    private RadioButton radAzul;
    private Button btnInsere;
    private Button btnCancela;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir);
        txtTexto = findViewById(R.id.txtTexto);
        radioGroup = findViewById(R.id.radioGroup);
        radVermelho = findViewById(R.id.radVermelho);
        radVerde = findViewById(R.id.radVerde);
        radAzul = findViewById(R.id.radAzul);
        btnInsere = findViewById(R.id.btnInsere);
        btnCancela = findViewById(R.id.btnCancela);
        btnListener btnListener = new btnListener();
        btnInsere.setOnClickListener(btnListener);
        btnCancela.setOnClickListener(btnListener);
    }

    private int verificaRadio(RadioGroup radioGroup) {
        int radioSelecionado = radioGroup.getCheckedRadioButtonId();
        return radioSelecionado;
    }

    private class btnListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            boolean erro = false;
            String texto = txtTexto.getText().toString();
            int cor = 0;
            if (texto.isEmpty() || texto == null) {
                erro = true;
                Toast.makeText(InserirActivity.this, "Texto não informado.", Toast.LENGTH_LONG).show();
            }

            int radioSelecionado = verificaRadio(radioGroup);
            if (radioSelecionado == -1) {
                erro = true;
                Toast.makeText(InserirActivity.this, "Cor não informada.", Toast.LENGTH_LONG).show();
            } else {
                switch (radioSelecionado) {
                    case R.id.radVermelho:
                        cor = Color.RED;
                        break;
                    case R.id.radVerde:
                        cor = Color.GREEN;
                        break;
                    case R.id.radAzul:
                        cor = Color.BLUE;
                        break;
                }
            }

            Button botao = (Button) view;
            switch (botao.getId()) {
                case R.id.btnCancela:
                    setResult(RESULT_CANCELED);
                    finish();
                    break;
                case R.id.btnInsere:
                    if (!erro) {
                        Intent intent = new Intent();
                        intent.putExtra("texto", texto);
                        intent.putExtra("cor", cor);
                        setResult(RESULT_OK, intent);
                        finish();
                        break;
                    }
            }
        }
    }
}