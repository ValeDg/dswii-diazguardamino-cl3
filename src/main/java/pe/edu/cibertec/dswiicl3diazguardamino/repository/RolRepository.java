package pe.edu.cibertec.dswiicl3diazguardamino.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.cibertec.dswiicl3diazguardamino.model.bd.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Rol findByNomrol(String nombrerol);

}
