package segura.taylor.bl.persistencia;

import segura.taylor.bl.entidades.Artista;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ArtistaDAO {
    private ArrayList<Artista> artistas;

    public boolean save(Artista nuevoArtista) throws Exception {
        if(!findByID(nuevoArtista.getId()).isPresent()) {
            artistas.add(nuevoArtista);
            return true;
        }

        throw new Exception("Ya existe un artista con el id especificado");
    }

    public boolean update(Artista artistaActualizado) throws Exception {
        int indiceArtista = -1;
        int cont = 0;

        for (Artista artista : artistas) {
            if(artista.getId() == artistaActualizado.getId()) {
                indiceArtista = cont;
                break;
            }

            cont++;
        }

        if(indiceArtista != -1) {
            artistas.set(indiceArtista, artistaActualizado);
            return true;
        }

        throw new Exception("El artista que se desea actualizar no existe");
    }

    public boolean delete(int idArtista) throws Exception {
        Optional<Artista> artistaEncontrado = findByID(idArtista);

        if(artistaEncontrado.isPresent()) {
            artistas.remove(artistaEncontrado.get());
            return true;
        }

        throw new Exception("El artista que se desea eliminar no existe");
    }

    public List<Artista> findAll() {
        return Collections.unmodifiableList(artistas);
    }

    public Optional<Artista> findByID(int id) {
        for (Artista artista : artistas) {
            if(artista.getId() == id) {
                return Optional.of(artista);
            }
        }

        return Optional.empty();
    }
}
