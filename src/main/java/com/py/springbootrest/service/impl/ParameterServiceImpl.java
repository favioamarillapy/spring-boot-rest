package com.py.springbootrest.service.impl;

import com.py.springbootrest.common.Messages;
import com.py.springbootrest.dto.CustomResponse;
import com.py.springbootrest.entity.Parameter;
import com.py.springbootrest.repository.ParameterRepository;
import com.py.springbootrest.service.ParameterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParameterServiceImpl extends GenericServiceImpl implements ParameterService {

    private final ParameterRepository parameterRepository;

    @Override
    public CustomResponse<Page<Parameter>> findAll(int offset, int page, String orderBy, String orderDir) {
        log.info("Request {}: offset[{}], page[{}], orderBy[{}], orderDir[{}]", getUserName(), offset, page, orderBy, orderDir);

        try {
            var sort = Sort.by(orderDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, orderBy);
            Pageable pageable = PageRequest.of(offset, page, sort);

            Page<Parameter> record = parameterRepository.findAll(pageable);

            var response = CustomResponse.<Page<Parameter>>builder()
                    .data(record)
                    .error(false)
                    .message(Messages.SUCCESS_GET)
                    .build();
            log.info("Response {}: {}", getUserName(), response);

            return response;
        } catch (Exception e) {

            log.error("Error {}: {}", getUserName(), e.getMessage(), e);
            return CustomResponse.<Page<Parameter>>builder()
                    .data(null)
                    .error(true)
                    .message(e.getMessage())
                    .build();
        }
    }

    @Override
    public CustomResponse<Parameter> findById(Long id) {
        log.info("Request {}: id[{}]", getUserName(), id);
        try {
            var record = parameterRepository.findById(id)
                    .orElseThrow(() -> new Exception("Could not found record"));

            var response = CustomResponse.<Parameter>builder()
                    .data(record)
                    .error(false)
                    .message(Messages.SUCCESS_GET)
                    .build();
            log.info("Response {}: {}", getUserName(), response);

            return response;
        } catch (Exception e) {

            log.error("Error {}: {}", getUserName(), e.getMessage(), e);
            return CustomResponse.<Parameter>builder()
                    .data(null)
                    .error(true)
                    .message(e.getMessage())
                    .build();
        }
    }

    @Override
    public CustomResponse<Parameter> create(Parameter entity) {
        log.info("Request {}: entity[{}]", getUserName(), entity);

        try {
            var record = parameterRepository.save(entity);

            var response = CustomResponse.<Parameter>builder()
                    .data(record)
                    .error(false)
                    .message(Messages.SUCCESS_SAVE)
                    .build();
            log.info("Response {}: {}", getUserName(), response);

            return response;
        } catch (Exception e) {
            return CustomResponse.<Parameter>builder()
                    .data(null)
                    .error(true)
                    .message(e.getMessage())
                    .build();
        }
    }

    @Override
    public CustomResponse<Parameter> update(Long id, Parameter entity) {
        log.info("Request {}: id[{}], entity[{}]", getUserName(), id, entity);

        try {
            var record = parameterRepository.findById(id)
                    .orElseThrow(() -> new Exception("Could not found record"));

            entity.setId(id);
            record = parameterRepository.save(entity);

            var response = CustomResponse.<Parameter>builder()
                    .data(record)
                    .error(false)
                    .message(Messages.SUCCESS_SAVE)
                    .build();
            log.info("Response {}: {}", getUserName(), response);

            return response;
        } catch (Exception e) {

            log.error("Error {}: {}", getUserName(), e.getMessage(), e);
            return CustomResponse.<Parameter>builder()
                    .data(null)
                    .error(true)
                    .message(e.getMessage())
                    .build();
        }
    }

    @Override
    public CustomResponse<Void> delete(Long id) {
        log.info("Request {}: id[{}]", getUserName(), id);

        try {
            var record = parameterRepository.findById(id)
                    .orElseThrow(() -> new Exception("Could not found record"));

            parameterRepository.deleteById(id);

            var response = CustomResponse.<Void>builder()
                    .data(null)
                    .error(false)
                    .message(Messages.SUCCESS_DELETE)
                    .build();
            log.info("Response {}: {}", getUserName(), response);

            return response;
        } catch (Exception e) {

            log.error("Error {}: {}", getUserName(), e.getMessage(), e);
            return CustomResponse.<Void>builder()
                    .data(null)
                    .error(true)
                    .message(e.getMessage())
                    .build();
        }
    }
}
