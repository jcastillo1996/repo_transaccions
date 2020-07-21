package com.example.demo.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "transaccions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaccion {

	@Id
	public Long transaccionId;
	public LocalDateTime transaccionDate;
	public Long accountId;
	public Long creditId;
	public String transaccionType;
	public Double transaccionAmount;

}
