package rs.ac.uns.ftn.education.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import rs.ac.uns.ftn.education.repository.EngagementRepository;
import rs.ac.uns.ftn.education.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.education.payload.EngagementRequest;
import rs.ac.uns.ftn.education.model.Engagement;

@Service
public class EngagementService {
  
  @Autowired
  private EngagementRepository engagementRepository;

  @Autowired
  private ModelMapper modelMapper;
  
  public Page<Engagement> getAll(Pageable pageable) {
      return engagementRepository.findAll(pageable);
  }

  public Engagement getOne(Long engagementId) {    
      return engagementRepository.findById(engagementId)
        .orElseThrow(() -> new ResourceNotFoundException("Engagement", "id", engagementId));
  }

  public Engagement save(EngagementRequest engagementRequest) {
    Engagement engagement = modelMapper.map(engagementRequest, Engagement.class);

    Engagement savedEngagement = engagementRepository.save(engagement);
    engagementRepository.refresh(savedEngagement);

    return savedEngagement;
  }

  public void delete(Long engagementId) {
    engagementRepository.deleteById(engagementId);
  }
}
