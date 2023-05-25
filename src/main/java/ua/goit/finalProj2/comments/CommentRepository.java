package ua.goit.finalProj2.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    @Query("SELECT c FROM Comment c WHERE c.note.id = :noteId")
    List<Comment> findByNoteID(@Param("noteId") UUID noteId);

    void deleteById(UUID id);
}