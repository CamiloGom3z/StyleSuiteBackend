package com.backendstyle.myapp.service.impl;

import com.backendstyle.myapp.domain.CategoriaProducto;
import com.backendstyle.myapp.repository.CategoriaProductoRepository;
import com.backendstyle.myapp.service.CategoriaProductoService;
import com.backendstyle.myapp.service.dto.CategoriaProductoDTO;
import com.backendstyle.myapp.service.mapper.CategoriaProductoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CategoriaProducto}.
 */
@Service
@Transactional
public class CategoriaProductoServiceImpl implements CategoriaProductoService {

    private final Logger log = LoggerFactory.getLogger(CategoriaProductoServiceImpl.class);

    private final CategoriaProductoRepository categoriaProductoRepository;

    private final CategoriaProductoMapper categoriaProductoMapper;

    public CategoriaProductoServiceImpl(
        CategoriaProductoRepository categoriaProductoRepository,
        CategoriaProductoMapper categoriaProductoMapper
    ) {
        this.categoriaProductoRepository = categoriaProductoRepository;
        this.categoriaProductoMapper = categoriaProductoMapper;
    }

    @Override
    public CategoriaProductoDTO save(CategoriaProductoDTO categoriaProductoDTO) {
        log.debug("Request to save CategoriaProducto : {}", categoriaProductoDTO);
        CategoriaProducto categoriaProducto = categoriaProductoMapper.toEntity(categoriaProductoDTO);
        categoriaProducto = categoriaProductoRepository.save(categoriaProducto);
        return categoriaProductoMapper.toDto(categoriaProducto);
    }

    @Override
    public CategoriaProductoDTO update(CategoriaProductoDTO categoriaProductoDTO) {
        log.debug("Request to save CategoriaProducto : {}", categoriaProductoDTO);
        CategoriaProducto categoriaProducto = categoriaProductoMapper.toEntity(categoriaProductoDTO);
        categoriaProducto = categoriaProductoRepository.save(categoriaProducto);
        return categoriaProductoMapper.toDto(categoriaProducto);
    }

    @Override
    public Optional<CategoriaProductoDTO> partialUpdate(CategoriaProductoDTO categoriaProductoDTO) {
        log.debug("Request to partially update CategoriaProducto : {}", categoriaProductoDTO);

        return categoriaProductoRepository
            .findById(categoriaProductoDTO.getId())
            .map(existingCategoriaProducto -> {
                categoriaProductoMapper.partialUpdate(existingCategoriaProducto, categoriaProductoDTO);

                return existingCategoriaProducto;
            })
            .map(categoriaProductoRepository::save)
            .map(categoriaProductoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoriaProductoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CategoriaProductos");
        return categoriaProductoRepository.findAll(pageable).map(categoriaProductoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategoriaProductoDTO> findOne(Long id) {
        log.debug("Request to get CategoriaProducto : {}", id);
        return categoriaProductoRepository.findById(id).map(categoriaProductoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CategoriaProducto : {}", id);
        categoriaProductoRepository.deleteById(id);
    }
}
