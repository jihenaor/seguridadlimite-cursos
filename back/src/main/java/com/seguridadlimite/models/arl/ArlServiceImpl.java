package com.seguridadlimite.models.arl;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.iservices.IArlService;
import com.seguridadlimite.models.dao.IArlDao;
import com.seguridadlimite.models.entity.Arl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArlServiceImpl implements IArlService {

    private final IArlDao dao;

    @Override
    @Transactional(readOnly = true)
    public List<Arl> findAll() {
        return (List<Arl>) dao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Arl findById(Integer id) {
        return dao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Arl save(Arl entity) {
        return dao.save(entity);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        dao.deleteById(id);
    }
}
