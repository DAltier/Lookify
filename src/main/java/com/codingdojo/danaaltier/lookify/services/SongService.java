package com.codingdojo.danaaltier.lookify.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codingdojo.danaaltier.lookify.models.Song;
import com.codingdojo.danaaltier.lookify.repositories.SongRepository;

@Service
public class SongService {
	
	// Adding the song repository as a dependency
	private final SongRepository songRepo;
	
	
	public SongService(SongRepository songRepo) {
		this.songRepo = songRepo;
	}
	
	
	// Adds a new song to the DB
	public void addSong(Song song) {
		songRepo.save(song);
	}
	
	// Returns a list of all the songs
	public List<Song> allSongs() {
		return (List<Song>) songRepo.findAll();
	}
	
	
	// Finds a song by the ID, if it exists
	public Song findSong(Long myId) {
		Optional<Song> findSong = songRepo.findById(myId);
		
		if (findSong.isPresent()) {
			return findSong.get();
		} else {
			return null;
		}
	}
	
	
	// Deletes a song from the DB
	public void deleteSong(Long id) {
		songRepo.deleteById(id);
	}
	
	
	// Returns a list of top 10 songs in the DB
	public List<Song> getTopTen() {
		return songRepo.findTop10ByOrderByRatingDesc();
	} 
	
	
	// Finds a song by the name of the artist
	public List<Song> findByArtist(String name) {
		return songRepo.findByArtist(name);
	}
}
