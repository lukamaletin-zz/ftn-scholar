package io.github.lukamaletin.ftnscholar.controller;

import io.github.lukamaletin.ftnscholar.dto.PaperMetadataResponse;
import io.github.lukamaletin.ftnscholar.dto.PaperRequest;
import io.github.lukamaletin.ftnscholar.model.Review;
import io.github.lukamaletin.ftnscholar.model.ScientificPaper;
import io.github.lukamaletin.ftnscholar.model.constants.Role;
import io.github.lukamaletin.ftnscholar.model.user.BaseUser;
import io.github.lukamaletin.ftnscholar.model.user.Member;
import io.github.lukamaletin.ftnscholar.model.user.StaffMember;
import io.github.lukamaletin.ftnscholar.service.PaperService;
import io.github.lukamaletin.ftnscholar.service.StorageService;
import io.github.lukamaletin.ftnscholar.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/paper")
public class PaperController {

    private final PaperService paperService;

    private final UserService userService;

    private final StorageService storageService;

    public PaperController(PaperService paperService, UserService userService, StorageService storageService) {
        this.paperService = paperService;
        this.userService = userService;
        this.storageService = storageService;
    }

    @PreAuthorize("hasAuthority('AUTHOR')")
    @PostMapping("{processId}")
    public ResponseEntity saveMetadata(@PathVariable String processId, @RequestBody PaperRequest paperRequest) {
        final Member member = (Member) userService.getCurrentUser();
        final ScientificPaper paper = paperService.save(paperRequest, member, processId);

        return new ResponseEntity<>(paper.getId(), HttpStatus.CREATED);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("{processId}")
    public ResponseEntity getMetadata(@PathVariable String processId) {
        final ScientificPaper paper = paperService.findByProcessId(processId);

        return new ResponseEntity<>(new PaperMetadataResponse(paper), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('AUTHOR')")
    @PostMapping("{processId}/file")
    public ResponseEntity saveFile(@PathVariable String processId, @RequestParam("file") MultipartFile file) {
        final String filePath = storageService.store(file, processId);
        final ScientificPaper paper = paperService.findByProcessId(processId);
        paper.setPdfPath(filePath);
        paperService.save(paper);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "{processId}/file", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_PDF_VALUE})
    public ResponseEntity getFile(@PathVariable String processId) {
        final byte[] file = storageService.load(processId);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        return new ResponseEntity<>(file, headers, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("{processId}/review")
    public ResponseEntity saveReview(@PathVariable String processId, @RequestBody Review review) {
        final StaffMember reviewer = (StaffMember) userService.getCurrentUser();
        review.setReviewer(reviewer);
        final ScientificPaper paper = paperService.findByProcessId(processId);
        paper.getReviews().add(review);
        paperService.save(paper);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("{processId}/review")
    public ResponseEntity getReviews(@PathVariable String processId) {
        final BaseUser user = userService.getCurrentUser();
        final ScientificPaper paper = paperService.findByProcessId(processId);
        if (user.getRole() == Role.AUTHOR) {
            paper.setReviews(paper.getReviews().stream().map(r -> new Review(r.getContent())).collect(Collectors.toList()));
        }

        return new ResponseEntity<>(paper.getReviews(), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("{processId}/reviewers")
    public ResponseEntity getReviewers(@PathVariable String processId) {
        final List<StaffMember> reviewers = paperService.getReviewers(processId);

        return new ResponseEntity<>(reviewers, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('AUTHOR')")
    @PostMapping("{processId}/reviewReply")
    public ResponseEntity saveReviewReply(@PathVariable String processId, @RequestBody String reviewReply) {
        final ScientificPaper paper = paperService.findByProcessId(processId);
        paper.setReviewReply(reviewReply);
        paperService.save(paper);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
