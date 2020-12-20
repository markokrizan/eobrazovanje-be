package rs.ac.uns.ftn.education.controller;

import org.springframework.web.bind.annotation.*;

import rs.ac.uns.ftn.education.model.Engagement;
import rs.ac.uns.ftn.education.payload.EngagementRequest;
import rs.ac.uns.ftn.education.service.EngagementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class EngagementController {

  @Autowired
  private EngagementService engagementService;
  
  @GetMapping("/engagements")
  @PreAuthorize("hasRole('ADMIN')")
  public Page<Engagement> getAll(@PageableDefault(size = 10) Pageable pageable) {
    return engagementService.getAll(pageable);
  }

  @GetMapping("/engagements/{engagementId}")
  @PreAuthorize("hasRole('ADMIN')")
  public Engagement getOne(@PathVariable("engagementId") Long engagementId) {
    return engagementService.getOne(engagementId);
  }

  @PostMapping("/engagements")
  @PreAuthorize("hasRole('ADMIN')")
  public Engagement save(@Valid @RequestBody EngagementRequest engagementRequest) {
    return engagementService.save(engagementRequest);
  }

  @DeleteMapping("/engagements/{engagementId}")
  @PreAuthorize("hasRole('ADMIN')")
  public void delete(@PathVariable("engagementId") Long engagementId) {
    engagementService.delete(engagementId);
  }
}
