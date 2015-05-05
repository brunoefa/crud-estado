package br.com.senai.cadastroestado;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class FormularioActivity extends Activity {

	Intent intent;
	private ArrayList<String> listaEstados;
	private String item = "";
	private String acao; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastro);
		this.intent = getIntent();
		atualizaListaEstados();
		
		this.acao = intent.getStringExtra("acao");
		if (ListagemActivity.ACAO_EDITAR.equals(acao)) {
			this.item = intent.getStringExtra("itemEstado");
			preencheFormulario(this.item);
		}
	}
	
	
	private void preencheFormulario(String item) {
		EditText etItemEstado = (EditText)findViewById(R.id.et_estado);
		etItemEstado.setText(item);
	}
	
	private void atualizaListaEstados() {
		listaEstados = this.intent.getStringArrayListExtra("listaEstados");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.cadastro, menu);
		return true;
	}
	
	public void salvarEstado(View view) {
		Log.i("ESTADO", "Estou no salvar: " + this.acao);
		EditText etEstado = (EditText)findViewById(R.id.et_estado);
		String estado = etEstado.getText().toString();
		if (ListagemActivity.ACAO_EDITAR.equals(this.acao)) {
			Log.i("ESTADO", "Estou no ifão");
			listaEstados.remove(this.item);
		}
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
