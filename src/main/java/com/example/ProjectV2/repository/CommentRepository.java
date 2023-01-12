package com.example.ProjectV2.repository;

import com.example.ProjectV2.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {


    @Query("select c.score from Comment as c where c.expert.id=:expertId")
    List<Double> findScoreByExpertId(Long expertId);


}