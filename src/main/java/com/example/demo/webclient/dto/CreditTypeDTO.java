package com.example.demo.webclient.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CreditTypeDTO {

	@Id
	@NotNull(message = "SHOULD NOT BE NULL")
	private Long idType;
	@NotNull(message = "SHOULD NOT BE NULL")
	@NotEmpty(message = "ENTER TYPE NAME")
	private String nameType;

}
