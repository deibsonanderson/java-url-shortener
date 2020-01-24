package br.com.url.shortener.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import br.com.url.shortener.dto.Url;
import br.com.url.shortener.dto.UrlSaveRequest;
import br.com.url.shortener.service.ServiceUrlShortener;

@RestController
public class ControllerUrlShortener {

	@Autowired
	private ServiceUrlShortener service;

	@GetMapping(value = { "/me.li/pageable" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Url>> queryUrlPageable(@RequestParam("skip") int skip,
			@RequestParam("top") int top) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(service.queryUrlPageable(skip, top));
	}
	
	@GetMapping(value = { "/me.li/{url}" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public RedirectView queryUrl(@PathVariable("url") final String url) {
		return new RedirectView(service.queryUrl(url));
	}

	@GetMapping(value = { "/not-found/404" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> notFound() {
		return ResponseEntity.status(HttpStatus.OK)
				.body("Pagina não encontrada!!!\nEntre em contato com o administrador do sistema.");
	}
	
	@PostMapping(value = { "/me.li" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Url> saveUrl(@Valid @RequestBody UrlSaveRequest url) {
		return ResponseEntity.status(HttpStatus.OK).body(service.saveUrl(url));
	}

	@DeleteMapping(value = { "/me.li/{url}" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> removeUrl(@PathVariable("url") final String url) {
		service.removeUrl(url);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
