package com.seguridadlimite.models.eps.infraestructure.web;

import com.seguridadlimite.models.eps.application.IEpsService;
import com.seguridadlimite.models.eps.domain.Eps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EpsController {

	@Autowired
	private IEpsService service;


	@GetMapping("/epss")
	public List<Eps> index() {
		List<Eps> l;
		
		l = service.findAll();
		
		Collections.sort(l, new Comparator<Eps>() {
			   public int compare(Eps obj1, Eps obj2) {
			      return obj1.getNombre().compareTo(obj2.getNombre());
			   }
			});
		
		return l;
	}

	@GetMapping("/eps/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Eps getById(@PathVariable Long id) {
		return service.findById(id);
	}

	@PostMapping("saveEps")
	@ResponseStatus(HttpStatus.CREATED)
	public Eps create(@RequestBody Eps entity) {
		return service.save(entity);
	}

	@PutMapping("updateEps/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Eps update(@RequestBody Eps entity, @PathVariable Long id) {
		Eps epsActual = service.findById(id);

		//cursoActual.setDuraciontotal(entity.getDuraciontotal());
		return service.save(epsActual);
	}

	@DeleteMapping("/deleteEps/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
}
