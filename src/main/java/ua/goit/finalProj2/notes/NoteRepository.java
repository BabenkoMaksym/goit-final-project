package ua.goit.finalProj2.notes;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note, UUID> {

    @Query("SELECT n FROM Note n WHERE n.noteType = 'PUBLIC' ORDER BY n.createdAt DESC")
    List<Note> findPublicNotes(Pageable pageable);

}
