package nl.maritoldenburger.bakehub.controllers;

import nl.maritoldenburger.bakehub.dtos.favorite.FavoriteDto;
import nl.maritoldenburger.bakehub.dtos.favorite.FavoriteInputDto;
import nl.maritoldenburger.bakehub.services.FavoriteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping
    public ResponseEntity<List<FavoriteDto>> getFavoritesByUser(@PathVariable Long userId, Principal principal) {
        List<FavoriteDto> favorites = favoriteService.getFavoritesByUser(principal.getName(), userId);
        return ResponseEntity.ok(favorites);
    }

    @PostMapping
    public ResponseEntity<FavoriteDto> addFavorite(@PathVariable Long userId, @RequestBody FavoriteInputDto favoriteInputDto) {
        FavoriteDto savedFavorite = favoriteService.addFavorite(userId, favoriteInputDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFavorite);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable Long id, Principal principal) {
        favoriteService.deleteFavorite(id, principal.getName());
        return ResponseEntity.noContent().build();
    }
}