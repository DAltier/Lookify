package com.codingdojo.danaaltier.lookify.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.codingdojo.danaaltier.lookify.models.Song;
import com.codingdojo.danaaltier.lookify.services.SongService;

@Controller
public class SongsController {
	
	// Adding the song service as a dependency
	private final SongService songService;
	
	
	// Constructor
	public SongsController(SongService songService) {
		this.songService = songService;
	}
	
	// Methods
	// GET route for index
	@RequestMapping("/")
	public String home() {
		return "index.jsp";
	}
	
	
 	// GET route for all songs
 	@RequestMapping("/dashboard")
 	// Model used for instantiating and binding to our view model
 	public String index(Model model,  @ModelAttribute("song") Song song) {
 		List<Song> songs = songService.allSongs();
 		model.addAttribute("songs", songs);
 		return "dashboard.jsp";
 	}
 	
 	
 	// GET route for creating a new song
 	@RequestMapping("/songs/new")
	public String renderAddSong(@ModelAttribute("addSong")Song song) {
		return "addSong.jsp";
	}
 	
 	
 	// POST route for creating a new song
 	@RequestMapping(value="/songs/new", method=RequestMethod.POST)
  	// @Valid checks for validation
  	// @BindingResult after, checks for errors
	public String createSong(@Valid @ModelAttribute("addSong") Song song, BindingResult result) {
		if(result.hasErrors()) {
			return "addSong.jsp";
		}else {
			songService.addSong(song);
			return "redirect:/dashboard";
		}	
	}
  	
  	
  	// GET route for show id
   	@RequestMapping("/songs/{id}")
   	// @Path for query and Model for binding view
   	public String showSong(Model model, @PathVariable("id") Long myId) {
   		Song mySong = songService.findSong(myId);
   		model.addAttribute("song", mySong);
   		return "showSong.jsp";
   	}
   	
   	
   	// DELETE Route by id
  	@RequestMapping("/delete/{id}")
  	public String deleteSong(@PathVariable("id") Long id) {
  		// Queries and deletes language from PathVariable
  		songService.deleteSong(id);
  		return "redirect:/dashboard";
  	}
  	
  	
  	// GET route for show top 10
  	@RequestMapping("/search/topTen")
	public String topTenSong(Model model) {
		List<Song> top10Songs = songService.getTopTen();
		model.addAttribute("songs", top10Songs);
		return "topTen.jsp";
	}
  	
  	
  	
  	// POST route for searching by artist name
  	@PostMapping("/search")
	public String searchArtist(@RequestParam("artist") String artist) {
		return "redirect:/search/" + artist;
	}
  	
  	
  	// GET route for searching by artist name
  	@RequestMapping("/search/{artist}")
	public String showSearch(Model model, @PathVariable("artist") String artist) {
		List<Song> songs = songService.findByArtist(artist);
		model.addAttribute("songs", songs);
		model.addAttribute("artist", artist);
		return "searchByArtist.jsp";
	}
}
