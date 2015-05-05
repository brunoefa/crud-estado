package br.com.senai.cadastroestado;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListagemActivity extends ListActivity {
	
	private String itemEstado = "";
	protected static final String ACAO_CADASTRAR = "cadastrar";
	protected static final String ACAO_EDITAR = "editar";

	private ArrayList<String> listaEstados = new ArrayList<String>(
			Arrays.asList("Acre (AC)", "Alagoas (AL)", "Amapá (AP)",
					"Amazonas (AM)", "Bahia (BA)", "Ceará (CE)",
					"Distrito Federal (DF)", "Espírito Santo (ES)",
					"Goiás (GO)", "Maranhão (MA)", "Mato Grosso (MT)",
					"Mato Grosso do Sul (MS)", "Minas Gerais (MG)",
					"Pará (PA) ", "Paraíba (PB)", "Paraná (PR)",
					"Pernambuco (PE)", "Piauí (PI)", "Rio de Janeiro (RJ)",
					"Rio Grande do Norte (RN)", "Rio Grande do Sul (RS)",
					"Rondônia (RO)", "Roraima (RR)", "Santa Catarina (SC)",
					"São Paulo (SP)", "Sergipe (SE)", "Tocantins (TO)"));

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		atualizaListaEstados();
		carregaLista();
	}

	private void atualizaListaEstados() {
		Intent intent = getIntent();
		ArrayList<String> listaAuxiliar = intent
				.getStringArrayListExtra("listaEstados");
		if (listaAuxiliar != null) {
			listaEstados = listaAuxiliar;
		}
	}

	private void carregaLista() {
		ArrayAdapter<String> listaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaEstados);
		this.setListAdapter(listaAdapter);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		this.itemEstado = (String)l.getItemAtPosition(position);
		registerForContextMenu(v);
		v.showContextMenu();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Ações");

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.acoescrud, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_editar) {
			editarItem(this.itemEstado);
		} else if (id == R.id.action_excluir) {
			excluirItem(this.itemEstado);
		}
		return super.onContextItemSelected(item);
	}
	
	private void editarItem(String itemEstado) {
		Intent intent = new Intent(this, FormularioActivity.class);
		intent.putStringArrayListExtra("listaEstados", listaEstados);
		intent.putExtra("itemEstado", itemEstado);
		intent.putExtra("acao", ACAO_EDITAR);
		startActivity(intent);
	}
	
	private void excluirItem(String itemEstado) {
		listaEstados.remove(itemEstado);
		carregaLista();
	}

	private void cadastrarItem() {
		Intent intent = new Intent(this, FormularioActivity.class);
		intent.putStringArrayListExtra("listaEstados", listaEstados);
		intent.putExtra("acao", ACAO_CADASTRAR);
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.listagem, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_novo_estado) {
			cadastrarItem();
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void mostrarMensagem(String m) {
		Toast.makeText(this, m, Toast.LENGTH_LONG).show();
	}
}
