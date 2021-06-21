package com.codingdojo.danaaltier.lookify.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.danaaltier.lookify.models.Song;


@Repository
public interface SongRepository extends CrudRepository<Song, Long> {
	
	// Find a song by the artist
	List<Song> findByArtist(String artist);
	
	// Find top 10 songs
	List<Song> findTop10ByOrderByRatingDesc();
	
}
