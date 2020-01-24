package br.com.url.shortener.dto;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "url")
@NoArgsConstructor
public class Url implements Serializable {
	
	@Id
	private String id;

	private String shorter;

	private String longer;

	public Url(String id, String shorter, String longer) {
		super();
		this.id = id;
		this.shorter = shorter;
		this.longer = longer;
	}
	
	public Url(String id) {
		super();
		this.id = id;
	}	
	
}
