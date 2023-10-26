package com.py.springbootrest.controller;

import com.py.springbootrest.dto.CustomResponse;
import com.py.springbootrest.entity.Parameter;
import com.py.springbootrest.service.ParameterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/parameter")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class ParameterController {

    private final ParameterService parameterService;

    @GetMapping()
    public ResponseEntity<CustomResponse<Page<Parameter>>> findAll(@RequestParam(defaultValue = "10") int offset,
                                                                   @RequestParam(defaultValue = "1") int page,
                                                                   @RequestParam(defaultValue = "id") String orderBy,
                                                                   @RequestParam(defaultValue = "desc") String orderDir) {

        var response = parameterService.findAll(offset, page, orderBy, orderDir);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Parameter>> findById(@PathVariable Long id) {

        var response = parameterService.findById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<CustomResponse<Parameter>> create(@RequestBody Parameter body) {

        var response = parameterService.create(body);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Parameter>> update(@PathVariable Long id, @RequestBody Parameter body) {

        var response = parameterService.update(id, body);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Void>> delete(@PathVariable Long id) {

        var response = parameterService.delete(id);
        return ResponseEntity.ok(response);
    }
}
