package com.seguridadlimite.models.arl;

import com.seguridadlimite.iservices.IArlService;
import com.seguridadlimite.models.entity.Arl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ArlController {

	@Autowired
	private IArlService service;

	@GetMapping("/arls")
	public List<Arl> index() {
		List<Arl> l;

		l = service.findAll();
		
		Collections.sort(l, new Comparator<Arl>() {
			   public int compare(Arl obj1, Arl obj2) {
			      return obj1.getNombre().compareTo(obj2.getNombre());
			   }
		});
		
		return l;
	}
/*
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Arl getById(@PathVariable Integer id) {
		return service.findById(id);
	}

	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public Arl create(@RequestBody Arl entity) {
		return service.save(entity);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Arl update(@RequestBody Arl entity, @PathVariable Integer id) {
		Arl arl = service.findById(id);
		arl.setNombre(entity.getNombre());

		return service.save(arl);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) {
		service.delete(id);
	}

 */
}
