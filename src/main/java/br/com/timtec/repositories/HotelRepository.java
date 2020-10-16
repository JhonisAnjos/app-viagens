package br.com.timtec.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.timtec.model.Hotel;

@RepositoryRestResource(path ="hoteis")
public interface HotelRepository extends JpaRepository<Hotel, Integer>{
	
	public List<Hotel> findByCidade(@Param("cidade") String cidade);

}
