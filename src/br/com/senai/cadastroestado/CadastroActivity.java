package br.com.senai.cadastroestado;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class CadastroActivity extends Activity {

	private ArrayList<String> listaEstados;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastro);
		atualizaListaEstados();
	}
	
	private void atualizaListaEstados() {
		Intent intent = getIntent();
		listaEstados = intent.getStringArrayListExtra("listaEstados");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.cadastro, menu);
		return true;
	}
	
	public void cadastrarEstado(View view) {
		EditText etEstado = (EditText)findViewById(R.id.et_estado);
		String estado = etEstado.getText().toString();
		listaEstados.add(estado);
		listarEstadosCadastrados();
	}

	private void listarEstadosCadastrados() {
		Intent intent = new Intent(this, ListagemActivity.class);
		intent.putStringArrayListExtra("listaEstados", listaEstados);
		startActivity(intent);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_voltar) {
			listarEstadosCadastrados();
		}
		return super.onOptionsItemSelected(item);
	}
}
