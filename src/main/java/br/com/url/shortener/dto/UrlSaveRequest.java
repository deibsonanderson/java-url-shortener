package br.com.url.shortener.dto;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UrlSaveRequest implements Serializable {

	@Valid
	@NotNull
	@NotEmpty
	private String longer;
	
}
