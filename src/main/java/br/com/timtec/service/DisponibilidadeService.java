package br.com.timtec.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.timtec.model.Disponibilidade;
import br.com.timtec.model.Hotel;
import br.com.timtec.model.Quarto;

@RestController
@RequestMapping(path = "/disponibilidade")
public class DisponibilidadeService {
	
	private static final SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");
	
	@GetMapping("/{data}")
	public Disponibilidade recuperarDisponibilidadeParaData(@PathVariable("data") String data) throws ParseException {
		Disponibilidade aleatoria = new Disponibilidade();
		
		Hotel timtec = new Hotel();
		timtec.setNome("TimTec");
		
		Quarto basico = new Quarto();
		basico.setHotel(timtec);
		basico.setTipo("Básico");
		
		aleatoria.setHotel(timtec);
		aleatoria.setQuarto(basico);
		aleatoria.setData(SDF.parse(data));
		
		return aleatoria;
		
	}

}
