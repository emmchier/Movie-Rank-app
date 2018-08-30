package com.integrador.grupo2android.proyectointegrador.Vista.Fragments;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.integrador.grupo2android.proyectointegrador.Controlador.ControladorSeries;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Tv;
import com.integrador.grupo2android.proyectointegrador.R;
import com.integrador.grupo2android.proyectointegrador.Util.ResultListener;
import com.integrador.grupo2android.proyectointegrador.Util.TMDBHelper;
import com.integrador.grupo2android.proyectointegrador.Vista.Adapters.AdapterRecyclerBuscadorSeries;
import java.util.List;

public class FragmentBuscadorSeries extends Fragment implements AdapterRecyclerBuscadorSeries.EscuchadorRecyclerSeries {
    private ImageButton imageButtonSearch;
    private EditText editTextSearch;
    private RecyclerView recyclerView;
    private AdapterRecyclerBuscadorSeries adapterRecyclerBuscadorSeries;
    private String editTextBuscarTexto;
    private EscuchadorFragmentoBuscadorSeries escuchadorFragmentoSeries;

    public FragmentBuscadorSeries() {
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        this.escuchadorFragmentoSeries = (EscuchadorFragmentoBuscadorSeries) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buscador_series, container, false);

        imageButtonSearch = view.findViewById(R.id.imageButtonSearch);
        editTextSearch = view.findViewById(R.id.editTextSearch);
        recyclerView = view.findViewById(R.id.recyclerViewListadoBuscadorSeries); //Cambiar


        imageButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextBuscarTexto = editTextSearch.getText().toString();
                if (editTextBuscarTexto.length() < 3) {
                    Toast.makeText(getActivity(), "Tu busqueda debe contener al menos 3 caracteres", Toast.LENGTH_SHORT).show();
                } else {
                    ControladorSeries controladorSeries = new ControladorSeries(getActivity().getApplicationContext());
                    ResultListener<List<Tv>> escuchadorVista = new ResultListener<List<Tv>>() {
                        @Override
                        public void finish(List<Tv> resultado) {
                            adapterRecyclerBuscadorSeries.setTv(resultado);
                        }
                    };
                    controladorSeries.searchSeriesText(escuchadorVista, TMDBHelper.language_SPANISH, editTextBuscarTexto, 1);

                }
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(editTextSearch.getWindowToken(), 0);
            }
        });

        //RECYCLERVIEW
        adapterRecyclerBuscadorSeries = new
        AdapterRecyclerBuscadorSeries(this);
        recyclerView.setAdapter(adapterRecyclerBuscadorSeries);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }
    @Override
    public void seleccionaronSerie(Integer posicion) {
        escuchadorFragmentoSeries.seleccionaronSerie(posicion, editTextBuscarTexto);
    }

    public interface EscuchadorFragmentoBuscadorSeries {
        void seleccionaronSerie(Integer posicion, String texto);
    }
}
