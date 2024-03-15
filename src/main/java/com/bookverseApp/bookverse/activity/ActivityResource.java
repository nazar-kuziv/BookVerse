package com.bookverseApp.bookverse.activity;

import com.bookverseApp.bookverse.exceptions.IncorrectPagesNumberException;
import com.bookverseApp.bookverse.exceptions.SomethingWentWrongException;
import com.bookverseApp.bookverse.exceptions.UserNotFoundException;
import com.bookverseApp.bookverse.jpa.ActivityRepository;
import com.bookverseApp.bookverse.jpa.BookRepository;
import com.bookverseApp.bookverse.jpa.LibraryRepository;
import com.bookverseApp.bookverse.jpa.UserRepository;
import com.bookverseApp.bookverse.library.Library;
import com.bookverseApp.bookverse.user.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
public class ActivityResource {

    ActivityRepository activityRepository;
    UserRepository userRepository;
    LibraryRepository libraryRepository;
    BookRepository bookRepository;

    public ActivityResource(ActivityRepository activityRepository, UserRepository userRepository, LibraryRepository libraryRepository, BookRepository bookRepository) {
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;
        this.libraryRepository = libraryRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/activities")
    public List<Activity> retrieveUserActivitiesByMonth(@RequestParam Long userId,
                                                        @RequestParam @Min(value = 1, message = "The month should fall within the range of 1 to 12.") @Max(value = 12, message = "The month should fall within the range of 1 to 12.") Integer month,
                                                        @RequestParam Integer year) {
        return activityRepository.findUserActivitiesByMonthAndYear(userId, month, year);
    }

    @PostMapping("/readingStop")
    public ResponseEntity<Object> readingStop(@RequestParam Long userId,
                                              @RequestParam Integer finishPage, @RequestParam Long bookId,
                                              @RequestParam @Min(value = 1, message = "Incorrect reading time.") Long minutesOfReading) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Library saveLibrary = libraryRepository.findByUserIdAndBookId(user.getId(), bookId).orElseThrow(() -> new SomethingWentWrongException("The book was not found in the library"));


        if (finishPage > saveLibrary.getBook().getPagesAmount() || finishPage < saveLibrary.getNumberOfPagesRead() || finishPage < 0)
            throw new IncorrectPagesNumberException();

        saveLibrary.setNumberOfPagesRead(finishPage);
        libraryRepository.save(saveLibrary);

        Activity newActivity = new Activity(user, saveLibrary.getBook(), minutesOfReading);
        activityRepository.save(newActivity);

        return ResponseEntity.ok().build();
    }
}
