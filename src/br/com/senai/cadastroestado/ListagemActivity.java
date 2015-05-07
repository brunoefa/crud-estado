package br.com.senai.cadastroestado;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListagemActivity extends ListActivity {
	
	private String itemEstado = "";
	private ListView listViewEstado;
	protected static final String ACAO_CADASTRAR = "cadastrar";
	protected static final String ACAO_EDITAR = "editar";

	private ArrayList<String> listaEstados = new ArrayList<String>(
			Arrays.asList("Acre (AC)", "Alagoas (AL)", "Amap� (AP)",
					"Amazonas (AM)", "Bahia (BA)", "Cear� (CE)",
					"Distrito Federal (DF)", "Esp�rito Santo (ES)",
					"Goi�s (GO)", "Maranh�o (MA)", "Mato Grosso (MT)",
					"Mato Grosso do Sul (MS)", "Minas Gerais (MG)",
					"Par� (PA) ", "Para�ba (PB)", "Paran� (PR)",
					"Pernambuco (PE)", "Piau� (PI)", "Rio de Janeiro (RJ)",
					"Rio Grande do Norte (RN)", "Rio Grande do Sul (RS)",
					"Rond�nia (RO)", "Roraima (RR)", "Santa Catarina (SC)",
					"S�o Paulo (SP)", "Sergipe (SE)", "Tocantins (TO)"));

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		listViewEstado = getListView();
		atualizaListaEstados();
		carregaLista();
	}

	private void atualizaListaEstados() {
		Intent intent = getIntent();
		ArrayList<String> listaAuxiliar = intent.getStringArrayListExtra("listaEstados");
		if (listaAuxiliar != null) {
			listaEstados = listaAuxiliar;
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		SparseBooleanArray checkedItemPositions = listViewEstado.getCheckedItemPositions();
		l.setItemChecked(position, checkedItemPositions.get(position));
	}
	
	private void carregaLista() {
		ArrayAdapter<String> listaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, listaEstados);
		this.setListAdapter(listaAdapter);
		listViewEstado.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		listViewEstado.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				itemEstado = (String)listViewEstado.getItemAtPosition(position);
				mostrarMensagem(itemEstado);
				registerForContextMenu(listViewEstado);
				listViewEstado.showContextMenu();
				return true;
			}
		});
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("A��es");

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.acoescrud, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_editar) {
			editarItem(this.itemEstado);
		} else if (id == R.id.action_excluir) {
			confirmarExclusao();
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
	
	private void excluirItem() {
		excluirItem(this.itemEstado);
	}
	
	private void excluirItem(String item) {
		listaEstados.remove(item);
	}
	
	private void confirmarExclusao() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Tem certeza que deseja excluir?");
		alert.setNegativeButton("N�o", null);
		alert.setPositiveButton("Sim", 
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					excluirItem();
					carregaLista();
					mostrarMensagem("Item removido com sucesso");
				}
			}
		);
		alert.show();
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
		} else if (id == R.id.action_excluir_selecionados) {
			confirmaExclusaoEmLote();
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void confirmaExclusaoEmLote() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Tem certeza que deseja excluir todos os itens?");
		alert.setNegativeButton("N�o", null);
		alert.setPositiveButton("Sim", 
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					excluirSelecionados();
				}
			}
		);
		alert.show();
	}
	
	private void excluirSelecionados() {  
		SparseBooleanArray checkedItemPositions = listViewEstado.getCheckedItemPositions();
		int count = listViewEstado.getCount();
		ArrayList<String> itensSelecionados = new ArrayList<String>();
		for (int i = 0; i < count; i++) {
		    if (checkedItemPositions.get(i)) {
		        String item = (String)listViewEstado.getItemAtPosition(i);
		        itensSelecionados.add(item);
		    }
		}
		
		for (String checked : itensSelecionados) {
			excluirItem(checked);
		}
		
		carregaLista();
		mostrarMensagem("Itens removidos com sucesso");
	}
	
	private void mostrarMensagem(String m) {
		Toast.makeText(this, m, Toast.LENGTH_LONG).show();
	}
}
