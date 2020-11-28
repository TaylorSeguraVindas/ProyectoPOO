package segura.taylor.bl.persistencia;

import segura.taylor.bl.entidades.Pais;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PaisDAO {
    private ArrayList<Pais> paises;

    public boolean save(Pais nuevoPais) throws Exception {
        if(!findByID(nuevoPais.getId()).isPresent()) {
            paises.add(nuevoPais);
            return true;
        }

        throw new Exception("Ya existe un Pais con el id especificado");
    }

    public boolean update(Pais PaisActualizado) throws Exception {
        int indicePais = -1;
        int cont = 0;

        for (Pais Pais : paises) {
            if(Pais.getId() == PaisActualizado.getId()) {
                indicePais = cont;
                break;
            }

            cont++;
        }

        if(indicePais != -1) {
            paises.set(indicePais, PaisActualizado);
            return true;
        }

        throw new Exception("El Pais que se desea actualizar no existe");
    }

    public boolean delete(int idPais) throws Exception {
        Optional<Pais> PaisEncontrado = findByID(idPais);

        if(PaisEncontrado.isPresent()) {
            paises.remove(PaisEncontrado.get());
            return true;
        }

        throw new Exception("El Pais que se desea eliminar no existe");
    }

    public List<Pais> findAll() {
        return Collections.unmodifiableList(paises);
    }

    public Optional<Pais> findByID(int id) {
        for (Pais Pais : paises) {
            if(Pais.getId() == id) {
                return Optional.of(Pais);
            }
        }

        return Optional.empty();
    }
}
