package br.com.timtec.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.timtec.model.Hotel;
import br.com.timtec.repositories.HotelRepository;

@RestController
@RequestMapping("/services/hoteis")
public class HoteisService {

	@Autowired
	private HotelRepository hotelRepository;

	@GetMapping
	public ResponseEntity<List<Hotel>> findByCidade(String cidade,
			@RequestHeader(name = "X-API-Version", defaultValue = "1.0") String versaoApi) {
		System.out.println("Versão da API: " + versaoApi);

		List<Hotel> hoteis = this.hotelRepository.findByCidade(cidade);
		StringBuilder builder = new StringBuilder();
		hoteis.forEach(h -> {
			h.add(Link.valueOf("</quartos/l>; title=\"Quarto básico\"; rel=\"quarto\""));
			builder.append(this.calculateHotelHash(h));

		});
		return ResponseEntity.ok().eTag(builder.toString()).body(hoteis);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Integer id) {

		Optional<Hotel> hotel = this.hotelRepository.findById(id);

		if (!hotel.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok().eTag(this.calculateHotelHash(hotel.get())).body(hotel.get());
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<?> updateHotel(Hotel hotel, @RequestHeader(name = "if-Match") String eTag, @PathVariable("id") Integer id) {
		ResponseEntity<?> hotelResponse = this.findById(id);
		
		if(!hotelResponse.getStatusCode().is2xxSuccessful()) {
			return hotelResponse;
		}
		
		String calculoEtag = hotelResponse.getHeaders().getETag().toString();
		if(!eTag.equals(calculoEtag)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		
		hotel.setId(id);
		this.hotelRepository.save(hotel);
		
		return ResponseEntity.noContent().build();

	}

	public String calculateHotelHash(Hotel hotel) {
		StringBuilder builder = new StringBuilder();
		DigestUtils.appendMd5DigestAsHex(new byte[] { hotel.getId().byteValue() }, builder);
		DigestUtils.appendMd5DigestAsHex(hotel.getNome().getBytes(), builder);
		DigestUtils.appendMd5DigestAsHex(hotel.getEndereco().getBytes(), builder);
		DigestUtils.appendMd5DigestAsHex(hotel.getCidade().getBytes(), builder);

		return builder.toString();
	}
}
