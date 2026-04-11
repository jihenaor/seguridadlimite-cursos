package com.seguridadlimite.models.disenocurricular.application;

import com.seguridadlimite.models.disenocurricular.config.DisenocurricularCacheConfig;
import com.seguridadlimite.models.disenocurricular.domain.Disenocurricular;
import com.seguridadlimite.models.disenocurricular.infraestructure.IDisenocurricularDao;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindDisenocurricularByIdnivelService {

	private final IDisenocurricularDao dao;

	@Transactional(readOnly = true)
	@Cacheable(cacheNames = DisenocurricularCacheConfig.CACHE_DISENO_POR_NIVEL, key = "'nivel-' + #idnivel")
	public List<Disenocurricular> find(Long idnivel) {
		return dao.findByIdnivelOrderByDiaAsc(idnivel);
	}

	@Transactional
	@CacheEvict(cacheNames = DisenocurricularCacheConfig.CACHE_DISENO_POR_NIVEL, key = "'nivel-' + #disenocurricular.idnivel")
	public void save(Disenocurricular disenocurricular) {
		dao.save(disenocurricular);
	}
}
