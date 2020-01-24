package br.com.url.shortener.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.url.shortener.dto.Url;

@Repository
public interface RepositoryUrlShortener extends MongoRepository<Url, String> {
	
	public void deleteByShorter(String shorter);
	
}
