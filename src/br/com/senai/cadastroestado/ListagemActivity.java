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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListagemActivity extends ListActivity {

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

		ListView lv = getListView();

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				registerForContextMenu(view);
				view.showContextMenu();
			}
		});
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
			Toast.makeText(this, "Editar", Toast.LENGTH_LONG).show();
		} else if (id == R.id.action_excluir) {
			Toast.makeText(this, "Excluir", Toast.LENGTH_LONG).show();
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.listagem, menu);
		return true;
	}

	private void cadastrarNovoEstado() {
		Intent intent = new Intent(this, CadastroActivity.class);
		intent.putStringArrayListExtra("listaEstados", listaEstados);
		startActivity(intent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_novo_estado) {
			cadastrarNovoEstado();
		}
		return super.onOptionsItemSelected(item);
	}
}
