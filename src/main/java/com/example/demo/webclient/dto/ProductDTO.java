package com.example.demo.webclient.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class ProductDTO {

	@Id
	private Long idProduct;
	private ProductTypeDTO productType;
	private String productNumber;
	private String money;
	private AccountTypeDTO accountType;
	private CreditTypeDTO creditType;
	private LocalDate creationDate;
	private Double mount;
	private List<Long> idClient;
	private List<Long> idSignatory;

}
