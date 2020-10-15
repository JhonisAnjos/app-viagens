package br.com.timtec.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.timtec.model.Hotel;

@RepositoryRestResource(path ="hoteis")
public interface HotelRepository extends JpaRepository<Hotel, Integer>{

}
