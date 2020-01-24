package br.com.url.shortener.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.url.shortener.constants.Letter;
import br.com.url.shortener.dto.Url;
import br.com.url.shortener.dto.UrlSaveRequest;
import br.com.url.shortener.repository.CashRepository;
import br.com.url.shortener.repository.RepositoryUrlShortener;

@Service
public class ServiceUrlShortener {

	@Value("${shortener.path:http://localhost:8081/me.li/}")
	private String path;

	@Value("${time-to-live:5}")
	private String timeToLive;

	private static final String PATH_NOT_FOUND = "/not-found/404";

	@Autowired
	private RepositoryUrlShortener repository;
	
	@Autowired
	private CashRepository cashRepository; 
	
	public Page<Url> queryUrlPageable(int skip, int top) {
		final Pageable page = PageRequest.of(skip, top);
		return repository.findAll(page);
	}

	public String queryUrl(String url) {
		String reponse = PATH_NOT_FOUND;
		Object cash = cashRepository.getFromCache(url, path + url);
		if(cash == null){
			Optional<Url> urlrepo = repository.findById(url);
			if (urlrepo.isPresent()) {
				reponse = urlrepo.get().getLonger();
			}
			cashRepository.putOnCache(url, path + url, reponse);
		}else{
			reponse = cash.toString();
		}
		
		return reponse;
	}

	public Url saveUrl(final UrlSaveRequest url) {
		String hash = mountHash();
		return repository.save(new Url(hash, path + hash, url.getLonger()));
	}

	public void removeUrl(String url) {
		repository.delete(new Url(url));
	}

	private String mountHash() {
		String sequenceNumbers = generateSequence().toString();
		StringBuilder hash = new StringBuilder();
		for (char c : sequenceNumbers.toCharArray()) {
			hash.append(Letter.searchEnum(c));
		}
		return hash.toString();
	}

	private Long generateSequence() {
		return new Date().getTime();
	}
}
