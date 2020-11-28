package segura.taylor.bl.persistencia;

import segura.taylor.bl.entidades.Genero;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class GeneroDAO {
    private ArrayList<Genero> generos;

    public boolean save(Genero nuevoGenero) throws Exception {
        if(!findByID(nuevoGenero.getId()).isPresent()) {
            generos.add(nuevoGenero);
            return true;
        }

        throw new Exception("Ya existe un Genero con el id especificado");
    }

    public boolean update(Genero GeneroActualizado) throws Exception {
        int indiceGenero = -1;
        int cont = 0;

        for (Genero Genero : generos) {
            if(Genero.getId() == GeneroActualizado.getId()) {
                indiceGenero = cont;
                break;
            }

            cont++;
        }

        if(indiceGenero != -1) {
            generos.set(indiceGenero, GeneroActualizado);
            return true;
        }

        throw new Exception("El Genero que se desea actualizar no existe");
    }

    public boolean delete(int idGenero) throws Exception {
        Optional<Genero> GeneroEncontrado = findByID(idGenero);

        if(GeneroEncontrado.isPresent()) {
            generos.remove(GeneroEncontrado.get());
            return true;
        }

        throw new Exception("El Genero que se desea eliminar no existe");
    }

    public List<Genero> findAll() {
        return Collections.unmodifiableList(generos);
    }

    public Optional<Genero> findByID(int id) {
        for (Genero Genero : generos) {
            if(Genero.getId() == id) {
                return Optional.of(Genero);
            }
        }

        return Optional.empty();
    }
}
