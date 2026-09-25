package ar.edu.um.isa.oncall.service;

import ar.edu.um.isa.oncall.domain.*; // for static metamodels
import ar.edu.um.isa.oncall.domain.ImpactoPropagado;
import ar.edu.um.isa.oncall.repository.ImpactoPropagadoRepository;
import ar.edu.um.isa.oncall.service.criteria.ImpactoPropagadoCriteria;
import ar.edu.um.isa.oncall.service.dto.ImpactoPropagadoDTO;
import ar.edu.um.isa.oncall.service.mapper.ImpactoPropagadoMapper;
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
 * Service for executing complex queries for {@link ImpactoPropagado} entities in the database.
 * The main input is a {@link ImpactoPropagadoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link ImpactoPropagadoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ImpactoPropagadoQueryService extends QueryService<ImpactoPropagado> {

    private static final Logger LOG = LoggerFactory.getLogger(ImpactoPropagadoQueryService.class);

    private final ImpactoPropagadoRepository impactoPropagadoRepository;

    private final ImpactoPropagadoMapper impactoPropagadoMapper;

    public ImpactoPropagadoQueryService(
        ImpactoPropagadoRepository impactoPropagadoRepository,
        ImpactoPropagadoMapper impactoPropagadoMapper
    ) {
        this.impactoPropagadoRepository = impactoPropagadoRepository;
        this.impactoPropagadoMapper = impactoPropagadoMapper;
    }

    /**
     * Return a {@link Page} of {@link ImpactoPropagadoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ImpactoPropagadoDTO> findByCriteria(ImpactoPropagadoCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ImpactoPropagado> specification = createSpecification(criteria);
        return impactoPropagadoRepository.findAll(specification, page).map(impactoPropagadoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ImpactoPropagadoCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<ImpactoPropagado> specification = createSpecification(criteria);
        return impactoPropagadoRepository.count(specification);
    }

    /**
     * Function to convert {@link ImpactoPropagadoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ImpactoPropagado> createSpecification(ImpactoPropagadoCriteria criteria) {
        Specification<ImpactoPropagado> specification = Specification.unrestricted();
        specification = specification.and((root, query, builder) -> {
            if (Long.class != query.getResultType()) {
                root.fetch(ImpactoPropagado_.incidente, JoinType.LEFT);
                root.fetch(ImpactoPropagado_.servicio, JoinType.LEFT);
                root.fetch(ImpactoPropagado_.dependencia, JoinType.LEFT);
            }
            return null;
        });
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = specification.and(
                Specification.allOf(
                    Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : Specification.unrestricted(),
                    buildRangeSpecification(criteria.getId(), ImpactoPropagado_.id),
                    buildSpecification(criteria.getSeveridad(), ImpactoPropagado_.severidad),
                    buildRangeSpecification(criteria.getDesde(), ImpactoPropagado_.desde),
                    buildRangeSpecification(criteria.getHasta(), ImpactoPropagado_.hasta),
                    buildSpecification(criteria.getIncidenteId(), root ->
                        root.join(ImpactoPropagado_.incidente, JoinType.LEFT).get(Incidente_.id)
                    ),
                    buildSpecification(criteria.getServicioId(), root ->
                        root.join(ImpactoPropagado_.servicio, JoinType.LEFT).get(Servicio_.id)
                    ),
                    buildSpecification(criteria.getDependenciaId(), root ->
                        root.join(ImpactoPropagado_.dependencia, JoinType.LEFT).get(DependenciaDeServicio_.id)
                    )
                )
            );
        }
        return specification;
    }
}
