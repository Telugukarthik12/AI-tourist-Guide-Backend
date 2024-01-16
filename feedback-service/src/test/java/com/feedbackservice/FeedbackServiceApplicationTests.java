package com.feedbackservice;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.TimeZone;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.feedbackservice.advices.FeedBackNotFound;
import com.feedbackservice.dto.FeedbackDto;
import com.feedbackservice.dto.UserDTO;
import com.feedbackservice.entity.Feedback;
import com.feedbackservice.openfeign.PlaceClient;
import com.feedbackservice.openfeign.UserClient;
import com.feedbackservice.repository.FeedbackRepository;
import com.feedbackservice.serviceimpl.FeedbackServiceImpl;

@ExtendWith(MockitoExtension.class)
public class FeedbackServiceApplicationTests {

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private PlaceClient placeClient;

    @Mock
    private UserClient userClient;

    @InjectMocks
    private FeedbackServiceImpl feedbackService;

    @Test
    public void testGetAllFeedback() {
        when(feedbackRepository.findAll()).thenReturn(java.util.List.of(new Feedback()));

        ResponseEntity<java.util.List<Feedback>> responseEntity = feedbackService.getAllFeedback();

        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert responseEntity.getBody() != null;
        assert !responseEntity.getBody().isEmpty();
    }

    @Test
    public void testUpdateFeedback() throws Throwable {
        FeedbackDto feedbackDto = new FeedbackDto();
        feedbackDto.setFeedbackText("Updated Feedback");

        Feedback existingFeedback = new Feedback();
        existingFeedback.setFeedbackId(1);
        existingFeedback.setFeedbackText("Old Feedback");
        existingFeedback.setDateTime(new Timestamp(System.currentTimeMillis()));

        when(feedbackRepository.findById(1)).thenReturn(Optional.of(existingFeedback));
        when(feedbackRepository.save(any(Feedback.class))).thenReturn(existingFeedback);

        ResponseEntity<String> responseEntity = feedbackService.updateFeedback(1, feedbackDto);

        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert responseEntity.getBody() != null;
        assert responseEntity.getBody().equals("Details update");
    }

    

    
    @Test
    public void testGetPlacefortour() throws Throwable {
        FeedbackDto feedbackDto = new FeedbackDto();
        feedbackDto.setFeedbackText("Test Feedback");
        feedbackDto.setDateTime(new Timestamp(System.currentTimeMillis()));

        when(placeClient.getPlacefortour(any(Integer.class))).thenReturn(1);
        when(userClient.getUserForClient(any(Integer.class))).thenReturn(1);
        when(feedbackRepository.save(any(Feedback.class))).thenReturn(new Feedback());

        ResponseEntity<String> responseEntity = feedbackService.getPlacefortour(1, 1, feedbackDto);

        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert responseEntity.getBody() != null;
        assert responseEntity.getBody().equals("Created");
    }

    @Test
    public void testGetUserDTO() throws Throwable {
        Feedback feedback = new Feedback();
        feedback.setUserId(1);

        when(feedbackRepository.findById(any(Integer.class))).thenReturn(Optional.of(feedback));
        when(userClient.getUserById(any(Integer.class))).thenReturn(ResponseEntity.ok(new UserDTO()));

        ResponseEntity<UserDTO> responseEntity = feedbackService.getUserDTO(1);

        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert responseEntity.getBody() != null;
    }

   
    
}
