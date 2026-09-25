package ar.edu.um.isa.oncall.service;

import ar.edu.um.isa.oncall.domain.*; // for static metamodels
import ar.edu.um.isa.oncall.domain.DependenciaDeServicio;
import ar.edu.um.isa.oncall.repository.DependenciaDeServicioRepository;
import ar.edu.um.isa.oncall.service.criteria.DependenciaDeServicioCriteria;
import ar.edu.um.isa.oncall.service.dto.DependenciaDeServicioDTO;
import ar.edu.um.isa.oncall.service.mapper.DependenciaDeServicioMapper;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link DependenciaDeServicio} entities in the database.
 * The main input is a {@link DependenciaDeServicioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link DependenciaDeServicioDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DependenciaDeServicioQueryService extends QueryService<DependenciaDeServicio> {

    private static final Logger LOG = LoggerFactory.getLogger(DependenciaDeServicioQueryService.class);

    private final DependenciaDeServicioRepository dependenciaDeServicioRepository;

    private final DependenciaDeServicioMapper dependenciaDeServicioMapper;

    public DependenciaDeServicioQueryService(
        DependenciaDeServicioRepository dependenciaDeServicioRepository,
        DependenciaDeServicioMapper dependenciaDeServicioMapper
    ) {
        this.dependenciaDeServicioRepository = dependenciaDeServicioRepository;
        this.dependenciaDeServicioMapper = dependenciaDeServicioMapper;
    }

    /**
     * Return a {@link Page} of {@link DependenciaDeServicioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DependenciaDeServicioDTO> findByCriteria(DependenciaDeServicioCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DependenciaDeServicio> specification = createSpecification(criteria);
        return dependenciaDeServicioRepository.findAll(specification, page).map(dependenciaDeServicioMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DependenciaDeServicioCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<DependenciaDeServicio> specification = createSpecification(criteria);
        return dependenciaDeServicioRepository.count(specification);
    }

    /**
     * Function to convert {@link DependenciaDeServicioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DependenciaDeServicio> createSpecification(DependenciaDeServicioCriteria criteria) {
        Specification<DependenciaDeServicio> specification = Specification.unrestricted();
        specification = specification.and((root, query, builder) -> {
            if (Long.class != query.getResultType()) {
                root.fetch(DependenciaDeServicio_.dependiente, JoinType.LEFT);
                root.fetch(DependenciaDeServicio_.origen, JoinType.LEFT);
            }
            return null;
        });
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = specification.and(
                Specification.allOf(
                    Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : Specification.unrestricted(),
                    buildRangeSpecification(criteria.getId(), DependenciaDeServicio_.id),
                    buildSpecification(criteria.getPeso(), DependenciaDeServicio_.peso),
                    buildStringSpecification(criteria.getDescripcion(), DependenciaDeServicio_.descripcion),
                    buildSpecification(criteria.getDependienteId(), root ->
                        root.join(DependenciaDeServicio_.dependiente, JoinType.LEFT).get(Servicio_.id)
                    ),
                    buildSpecification(criteria.getOrigenId(), root ->
                        root.join(DependenciaDeServicio_.origen, JoinType.LEFT).get(Servicio_.id)
                    ),
                    buildSpecification(criteria.getImpactoId(), root ->
                        root.join(DependenciaDeServicio_.impactos, JoinType.LEFT).get(ImpactoPropagado_.id)
                    )
                )
            );
        }
        return specification;
    }
}
