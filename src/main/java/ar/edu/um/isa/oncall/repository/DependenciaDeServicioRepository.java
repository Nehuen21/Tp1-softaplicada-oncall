package ar.edu.um.isa.oncall.repository;

import ar.edu.um.isa.oncall.domain.DependenciaDeServicio;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DependenciaDeServicio entity.
 */
@Repository
public interface DependenciaDeServicioRepository
    extends JpaRepository<DependenciaDeServicio, Long>, JpaSpecificationExecutor<DependenciaDeServicio>
{
    default Optional<DependenciaDeServicio> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<DependenciaDeServicio> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<DependenciaDeServicio> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select dependenciaDeServicio from DependenciaDeServicio dependenciaDeServicio left join fetch dependenciaDeServicio.dependiente left join fetch dependenciaDeServicio.origen",
        countQuery = "select count(dependenciaDeServicio) from DependenciaDeServicio dependenciaDeServicio"
    )
    Page<DependenciaDeServicio> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select dependenciaDeServicio from DependenciaDeServicio dependenciaDeServicio left join fetch dependenciaDeServicio.dependiente left join fetch dependenciaDeServicio.origen"
    )
    List<DependenciaDeServicio> findAllWithToOneRelationships();

    @Query(
        "select dependenciaDeServicio from DependenciaDeServicio dependenciaDeServicio left join fetch dependenciaDeServicio.dependiente left join fetch dependenciaDeServicio.origen where dependenciaDeServicio.id =:id"
    )
    Optional<DependenciaDeServicio> findOneWithToOneRelationships(@Param("id") Long id);
}
