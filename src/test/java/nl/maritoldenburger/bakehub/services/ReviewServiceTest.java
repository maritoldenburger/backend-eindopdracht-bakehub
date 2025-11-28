package nl.maritoldenburger.bakehub.services;

import nl.maritoldenburger.bakehub.dtos.review.ReviewDto;
import nl.maritoldenburger.bakehub.dtos.review.ReviewInputDto;
import nl.maritoldenburger.bakehub.exceptions.BadRequestException;
import nl.maritoldenburger.bakehub.exceptions.RecordNotFoundException;
import nl.maritoldenburger.bakehub.models.Recipe;
import nl.maritoldenburger.bakehub.models.Review;
import nl.maritoldenburger.bakehub.models.User;
import nl.maritoldenburger.bakehub.repositories.RecipeRepository;
import nl.maritoldenburger.bakehub.repositories.ReviewRepository;
import nl.maritoldenburger.bakehub.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    ReviewRepository reviewRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeService recipeService;

    @InjectMocks
    ReviewService reviewService;

    User user1 = new User();
    User user2 = new User();

    Recipe recipe1 = new Recipe();

    Review review1 = new Review();
    Review review2 = new Review();

    ReviewInputDto reviewInputDto ;

    @BeforeEach
    void setUp() {

        ReflectionTestUtils.setField(reviewService, "uploadLocation", "src/test/resources/uploads");

        user1.setUsername("annie_ananas");
        user1.setEmail("annieananas@bakehub.com");

        user2.setUsername("bobbybanaan");
        user2.setEmail("bobbiebanaan@bakehub.com");

        recipe1.setName("Speculoos cheesecake");
        recipe1.setDescription("Een romige no-bake cheesecake");
        recipe1.setInstructions("1. Verkruimel speculooskoekjes en meng met gesmolten boter.");
        recipe1.setServings(10);
        recipe1.setPreparationTime(30);

        review1 = new Review();
        review1.setId(1L);
        review1.setRating(5);
        review1.setComment("Superromig en precies zoet genoeg!");
        review1.setUser(user1);
        review1.setRecipe(recipe1);

        review2 = new Review();
        review2.setId(2L);
        review2.setRating(4);
        review2.setComment("Heel lekker! De bodem mocht iets steviger.");
        review2.setUser(user2);
        review2.setRecipe(recipe1);

        recipe1.setReviews(List.of(review1, review2));

        reviewInputDto = new ReviewInputDto();
        reviewInputDto.rating = 5;
        reviewInputDto.comment = "Geweldige cheesecake!";
    }

    @Test
    @DisplayName("getReviewsByRecipe should return all reviews for existing recipe")
    void getReviewsByRecipeShouldReturnAllReviews() {

        Mockito.when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe1));

        List<ReviewDto> reviews = reviewService.getReviewsByRecipe(1L);

        assertEquals(2, reviews.size());
        assertEquals(5, reviews.get(0).rating);
        assertEquals("Superromig en precies zoet genoeg!", reviews.get(0).comment);
        assertEquals("annie_ananas", reviews.get(0).username);
    }

    @Test
    @DisplayName("getReviewsByRecipe should throw exception when recipe is not found")
    void getReviewsByRecipeShouldThrowRecordNotFoundException() {

        Mockito.when(recipeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> reviewService.getReviewsByRecipe(99L));
    }

    @Test
    @DisplayName("addReview should create review without image")
    void addReviewWithoutImageShouldCreateReview() {

        Review savedReview = new Review();
        savedReview.setId(3L);
        savedReview.setRating(5);
        savedReview.setComment("Geweldige cheesecake!");
        savedReview.setUser(user1);
        savedReview.setRecipe(recipe1);

        Mockito.when(userRepository.findByUsername("annie_ananas")).thenReturn(Optional.of(user1));
        Mockito.when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe1));
        Mockito.when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);

        ReviewDto result = reviewService.addReview("annie_ananas", 1L, reviewInputDto, null);

        assertNotNull(result);
        assertEquals(3L, result.id);
        assertEquals(5, result.rating);
        assertEquals("Geweldige cheesecake!", result.comment);
        assertEquals("annie_ananas", result.username);
        verify(recipeService).updateRecipeRating(recipe1);
    }

    @Test
    @DisplayName("addReview should create review with image")
    void addReviewWithImageShouldCreateReview() throws IOException {

        MultipartFile mockFile = mock(MultipartFile.class);
        Mockito.when(mockFile.isEmpty()).thenReturn(false);
        Mockito.when(mockFile.getOriginalFilename()).thenReturn("prachtige-cheesecake.jpg");
        Mockito.when(mockFile.getInputStream()).thenReturn(new ByteArrayInputStream("test".getBytes()));

        Review savedReview = new Review();
        savedReview.setId(3L);
        savedReview.setRating(5);
        savedReview.setComment("Geweldige cheesecake!");
        savedReview.setImageUrl("/uploads/prachtige-cheesecake.jpg");
        savedReview.setUser(user1);
        savedReview.setRecipe(recipe1);

        Mockito.when(userRepository.findByUsername("annie_ananas")).thenReturn(Optional.of(user1));
        Mockito.when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe1));
        Mockito.when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);

        ReviewDto result = reviewService.addReview("annie_ananas", 1L, reviewInputDto, mockFile);

        assertNotNull(result);
        assertEquals("/uploads/prachtige-cheesecake.jpg", result.imageUrl);
        verify(recipeService).updateRecipeRating(recipe1);
    }

    @Test
    @DisplayName("addReview should throw exception when image upload fails")
    void addReviewShouldThrowRuntimeException() throws IOException {

        MultipartFile mockFile = mock(MultipartFile.class);
        Mockito.when(mockFile.isEmpty()).thenReturn(false);
        Mockito.when(mockFile.getOriginalFilename()).thenReturn("test-image.jpg");
        Mockito.when(mockFile.getInputStream()).thenThrow(new IOException());

        Mockito.when(userRepository.findByUsername("annie_ananas")).thenReturn(Optional.of(user1));
        Mockito.when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe1));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> reviewService.addReview("annie_ananas", 1L, reviewInputDto, mockFile));

        assertEquals("Unable to save image", exception.getMessage());
    }

    @Test
    @DisplayName("addReview should throw exception when user is not found")
    void addReviewShouldThrowRecordNotFoundExceptionUser() {

        Mockito.when(userRepository.findByUsername("unknownuser")).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class,
                () -> reviewService.addReview("unknownuser", 1L, reviewInputDto, null));
    }

    @Test
    @DisplayName("addReview should throw exception when recipe is not found")
    void addReviewShouldThrowRecordNotFoundExceptionRecipe() {

        Mockito.when(userRepository.findByUsername("annie_ananas")).thenReturn(Optional.of(user1));
        Mockito.when(recipeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class,
                () -> reviewService.addReview("annie_ananas", 99L, reviewInputDto, null));
    }

    @Test
    @DisplayName("deleteReview should delete existing review")
    void deleteReviewShouldDeleteReview() {

        Mockito.when(reviewRepository.findById(1L)).thenReturn(Optional.of(review1));

        reviewService.deleteReview(1L, "annie_ananas");

        verify(reviewRepository).delete(review1);
        verify(recipeService).updateRecipeRating(recipe1);
    }

    @Test
    @DisplayName("deleteReview should throw exception when review not found")
    void deleteReviewShouldThrowRecordNotFoundException() {

        Mockito.when(reviewRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class,
                () -> reviewService.deleteReview(99L, "annie_ananas"));
    }

    @Test
    @DisplayName("deleteReview should throw exception when user tries to delete another user's review")
    void deleteReviewShouldThrowBadRequestException() {

        Mockito.when(reviewRepository.findById(1L)).thenReturn(Optional.of(review1));

        assertThrows(BadRequestException.class,
                () -> reviewService.deleteReview(1L, "bobbybanaan"));
    }
}