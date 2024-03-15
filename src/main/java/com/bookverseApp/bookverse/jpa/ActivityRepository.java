package com.bookverseApp.bookverse.jpa;

import com.bookverseApp.bookverse.activity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    @Query("SELECT a FROM activities a " +
            "WHERE a.user.id = :userId " +
            "AND FUNCTION('MONTH', a.dateOfReading) = :month " +
            "AND FUNCTION('YEAR', a.dateOfReading) = :year")
    List<Activity> findUserActivitiesByMonthAndYear(@Param("userId") Long userId,
                                                    @Param("month") Integer month,
                                                    @Param("year") Integer year);

}
