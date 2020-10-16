package br.com.timtec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import br.com.timtec.model.Hotel;
import br.com.timtec.repositories.HotelRepository;

@RestController
public class HoteisService {
	
	@Autowired
	private HotelRepository hotelRepository;

	@GetMapping("/services/hoteis")
	public List<Hotel> findByCidade(String cidade,@RequestHeader(name = "X-API-Version", defaultValue = "1.0") String versaoApi){
		System.out.println("Versão da API: "+ versaoApi);
		
		List<Hotel> hoteis = this.hotelRepository.findByCidade(cidade);
		hoteis.forEach(h -> h.add(Link.valueOf("</quartos/l>; title=\"Quarto básico\"; rel=\"quarto\"")));
		return hoteis;
	}
}
