package nl.maritoldenburger.bakehub.services;

import nl.maritoldenburger.bakehub.dtos.favorite.FavoriteDto;
import nl.maritoldenburger.bakehub.dtos.favorite.FavoriteInputDto;
import nl.maritoldenburger.bakehub.exceptions.AlreadyExistsException;
import nl.maritoldenburger.bakehub.exceptions.BadRequestException;
import nl.maritoldenburger.bakehub.exceptions.RecordNotFoundException;
import nl.maritoldenburger.bakehub.mappers.FavoriteMapper;
import nl.maritoldenburger.bakehub.models.Favorite;
import nl.maritoldenburger.bakehub.models.Recipe;
import nl.maritoldenburger.bakehub.models.User;
import nl.maritoldenburger.bakehub.repositories.FavoriteRepository;
import nl.maritoldenburger.bakehub.repositories.RecipeRepository;
import nl.maritoldenburger.bakehub.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    public FavoriteService(FavoriteRepository favoriteRepository, UserRepository userRepository, RecipeRepository recipeRepository) {

        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
    }

    public List<FavoriteDto> getFavoritesByUser(String username, Long userId) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RecordNotFoundException("User " + userId + " not found"));

        if (!user.getId().equals(userId)) {
            throw new BadRequestException("You can only view your own favorites");
        }

        List<FavoriteDto> dtoFavorites = new ArrayList<>();

        for (Favorite favorite : user.getFavorites()) {
            dtoFavorites.add(FavoriteMapper.toDto(favorite));
        }

        return dtoFavorites;
    }

    public FavoriteDto addFavorite(Long userId, FavoriteInputDto favoriteInputDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RecordNotFoundException("User " + userId + " not found"));

        Recipe recipe = recipeRepository.findById(favoriteInputDto.recipeId)
                .orElseThrow(() -> new RecordNotFoundException("Recipe " + favoriteInputDto.recipeId + " not found"));

        if (favoriteRepository.existsByUserAndRecipe(user, recipe)) {
            throw new AlreadyExistsException("Recipe has already been saved to favorites");
        }

        Favorite favorite = new Favorite(user, recipe);
        Favorite savedFavorite = favoriteRepository.save(favorite);


        return FavoriteMapper.toDto(savedFavorite);
    }

    public void deleteFavorite(Long id, String username) {

        Favorite favorite = favoriteRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Favorite " + id + " not found"));

        if (!favorite.getUser().getUsername().equals(username)) {
            throw new BadRequestException("You can only delete your own favorites");
        }

        favoriteRepository.delete(favorite);
    }
}