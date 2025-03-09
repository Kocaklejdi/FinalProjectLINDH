package com.example.JobSeekerPortal.repository;

import com.example.JobSeekerPortal.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    @Query(value = "select * from users", nativeQuery = true)
    List<User> findAll();

    @Query(value = "select * from users inner join user_roles where users.id = user_roles.user_id and user_roles.role = ?1",nativeQuery = true)
    List<User> findAllByRole(String role, Pageable pageable);

    @Query(value = "SELECT users.* FROM users INNER JOIN job_application INNER JOIN job_listing ON users.id = job_application.user_id AND job_listing.id = ?1", nativeQuery = true)
    List<User> findUsersWhoAppliedToJobListing(Long jobId, Pageable pageable);

    Optional<User> findByVerificationToken(String token);

    void save(User user);

    void deleteById(Long id);
}
