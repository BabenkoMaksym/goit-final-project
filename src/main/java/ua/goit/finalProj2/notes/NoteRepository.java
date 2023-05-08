package ua.goit.finalProj2.notes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note, UUID> {

    @Query(value = "SELECT * FROM final_project.notes\n" +
            "where access_type = 'PUBLIC'\n" +
            "ORDER BY created_at DESC\n" +
            "OFFSET ?1\n" +
            "LIMIT 10;", nativeQuery = true)
    List<Note> feedNotes(Integer skip);
}
