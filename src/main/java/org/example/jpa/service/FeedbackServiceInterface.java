package org.example.jpa.service;

import org.example.jpa.model.Feedback;

import java.util.List;
import java.util.Optional;

public interface FeedbackServiceInterface {

    public abstract Feedback save(Feedback feedback);
    public abstract List<Feedback> findAll();
    public abstract Optional<Feedback> findById(Long id);
    public abstract Feedback update(Feedback feedback);
    public abstract void deleteById(Long id);
    public abstract long count();
}
