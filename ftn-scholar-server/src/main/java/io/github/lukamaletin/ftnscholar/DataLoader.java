package io.github.lukamaletin.ftnscholar;

import io.github.lukamaletin.ftnscholar.model.Journal;
import io.github.lukamaletin.ftnscholar.model.Publication;
import io.github.lukamaletin.ftnscholar.model.constants.PaymentOption;
import io.github.lukamaletin.ftnscholar.model.constants.Role;
import io.github.lukamaletin.ftnscholar.model.constants.ScientificField;
import io.github.lukamaletin.ftnscholar.model.user.Member;
import io.github.lukamaletin.ftnscholar.model.user.StaffMember;
import io.github.lukamaletin.ftnscholar.service.JournalService;
import io.github.lukamaletin.ftnscholar.service.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {

    private final UserService userService;

    private final JournalService journalService;

    public DataLoader(UserService userService, JournalService journalService) {
        this.userService = userService;
        this.journalService = journalService;
    }

    @Override
    public void run(ApplicationArguments args) {
        createTestJournal1();
        createTestJournal2();

        createAuthor1();
    }

    private void createTestJournal1() {

        final List<ScientificField> scientificFields = new ArrayList<>();
        scientificFields.add(ScientificField.LIFE_SCIENCE);
        scientificFields.add(ScientificField.MEDICINE);

        StaffMember chiefEditor = new StaffMember();
        chiefEditor.setRole(Role.CHIEF_EDITOR);
        chiefEditor.setUsername("chief1");
        chiefEditor.setPassword("pass123");
        chiefEditor.setFirstName("Chief");
        chiefEditor.setLastName("One");
        chiefEditor.setEmail("chief.one@journal.one");
        chiefEditor.setCity("");
        chiefEditor.setCountry("");
        chiefEditor.setTitle("");
        chiefEditor.setExpertise(scientificFields);
        chiefEditor = (StaffMember) userService.save(chiefEditor);

        StaffMember fieldEditor1 = new StaffMember();
        fieldEditor1.setRole(Role.FIELD_EDITOR);
        fieldEditor1.setUsername("editor11");
        fieldEditor1.setPassword("pass123");
        fieldEditor1.setFirstName("Editor");
        fieldEditor1.setLastName("One");
        fieldEditor1.setEmail("editor.one@journal.one");
        fieldEditor1.setCity("");
        fieldEditor1.setCountry("");
        fieldEditor1.setTitle("");
        fieldEditor1.setExpertise(scientificFields);
        fieldEditor1 = (StaffMember) userService.save(fieldEditor1);

        StaffMember fieldEditor2 = new StaffMember();
        fieldEditor2.setRole(Role.FIELD_EDITOR);
        fieldEditor2.setUsername("editor12");
        fieldEditor2.setPassword("pass123");
        fieldEditor2.setFirstName("Editor");
        fieldEditor2.setLastName("Two");
        fieldEditor2.setEmail("editor.two@journal.one");
        fieldEditor2.setCity("");
        fieldEditor2.setCountry("");
        fieldEditor2.setTitle("");
        fieldEditor2.setExpertise(scientificFields);
        fieldEditor2 = (StaffMember) userService.save(fieldEditor2);

        final List<StaffMember> fieldEditors = new ArrayList<>();
        fieldEditors.add(fieldEditor1);
        fieldEditors.add(fieldEditor2);

        StaffMember reviewer1 = new StaffMember();
        reviewer1.setRole(Role.REVIEWER);
        reviewer1.setUsername("reviewer11");
        reviewer1.setPassword("pass123");
        reviewer1.setFirstName("Reviewer");
        reviewer1.setLastName("One");
        reviewer1.setEmail("reviewer.one@journal.one");
        reviewer1.setCity("");
        reviewer1.setCountry("");
        reviewer1.setTitle("");
        reviewer1.setExpertise(scientificFields);
        reviewer1 = (StaffMember) userService.save(reviewer1);

        StaffMember reviewer2 = new StaffMember();
        reviewer2.setRole(Role.REVIEWER);
        reviewer2.setUsername("reviewer12");
        reviewer2.setPassword("pass123");
        reviewer2.setFirstName("Reviewer");
        reviewer2.setLastName("Two");
        reviewer2.setEmail("reviewer.two@journal.one");
        reviewer2.setCity("");
        reviewer2.setCountry("");
        reviewer2.setTitle("");
        reviewer2.setExpertise(scientificFields);
        reviewer2 = (StaffMember) userService.save(reviewer2);

        StaffMember reviewer3 = new StaffMember();
        reviewer3.setRole(Role.REVIEWER);
        reviewer3.setUsername("reviewer13");
        reviewer3.setPassword("pass123");
        reviewer3.setFirstName("Reviewer");
        reviewer3.setLastName("Three");
        reviewer3.setEmail("reviewer.three@journal.one");
        reviewer3.setCity("");
        reviewer3.setCountry("");
        reviewer3.setTitle("");
        reviewer3.setExpertise(scientificFields);
        reviewer3 = (StaffMember) userService.save(reviewer3);

        StaffMember reviewer4 = new StaffMember();
        reviewer4.setRole(Role.REVIEWER);
        reviewer4.setUsername("reviewer14");
        reviewer4.setPassword("pass123");
        reviewer4.setFirstName("Reviewer");
        reviewer4.setLastName("Four");
        reviewer4.setEmail("reviewer.four@journal.one");
        reviewer4.setCity("");
        reviewer4.setCountry("");
        reviewer4.setTitle("");
        reviewer4.setExpertise(scientificFields);
        reviewer4 = (StaffMember) userService.save(reviewer4);

        final List<StaffMember> reviewers = new ArrayList<>();
        reviewers.add(reviewer1);
        reviewers.add(reviewer2);
        reviewers.add(reviewer3);
        reviewers.add(reviewer4);

        final Publication publication = new Publication();
        publication.setVolume(1);
        publication.setIssue(1);
        publication.setDate(new Date());

        final List<Publication> publications = new ArrayList<>();
        publications.add(publication);

        final Journal journal = new Journal();
        journal.setName("Journal One");
        journal.setIssn("1111-1111");
        journal.setScientificFields(scientificFields);
        journal.setPaymentOption(PaymentOption.OpenAccess);
        journal.setChiefEditor(chiefEditor);
        journal.setFieldEditors(fieldEditors);
        journal.setReviewers(reviewers);
        journal.setPublications(publications);
        journal.setPrice(new BigDecimal(11));
        journalService.save(journal);
    }

    private void createTestJournal2() {

        final List<ScientificField> scientificFields = new ArrayList<>();
        scientificFields.add(ScientificField.ENGINEERING);
        scientificFields.add(ScientificField.MATHEMATICS);

        final List<ScientificField> scientificField1 = new ArrayList<>();
        scientificField1.add(ScientificField.ENGINEERING);

        final List<ScientificField> scientificField2 = new ArrayList<>();
        scientificField2.add(ScientificField.MATHEMATICS);

        StaffMember chiefEditor = new StaffMember();
        chiefEditor.setRole(Role.CHIEF_EDITOR);
        chiefEditor.setUsername("chief2");
        chiefEditor.setPassword("pass123");
        chiefEditor.setFirstName("Chief");
        chiefEditor.setLastName("Two");
        chiefEditor.setEmail("chief.two@journal.two");
        chiefEditor.setCity("");
        chiefEditor.setCountry("");
        chiefEditor.setTitle("");
        chiefEditor.setExpertise(scientificFields);
        chiefEditor = (StaffMember) userService.save(chiefEditor);

        StaffMember fieldEditor1 = new StaffMember();
        fieldEditor1.setRole(Role.FIELD_EDITOR);
        fieldEditor1.setUsername("editor21");
        fieldEditor1.setPassword("pass123");
        fieldEditor1.setFirstName("Editor");
        fieldEditor1.setLastName("One");
        fieldEditor1.setEmail("editor.one@journal.two");
        fieldEditor1.setCity("");
        fieldEditor1.setCountry("");
        fieldEditor1.setTitle("");
        fieldEditor1.setExpertise(scientificField1);
        fieldEditor1 = (StaffMember) userService.save(fieldEditor1);

        StaffMember fieldEditor2 = new StaffMember();
        fieldEditor2.setRole(Role.FIELD_EDITOR);
        fieldEditor2.setUsername("editor22");
        fieldEditor2.setPassword("pass123");
        fieldEditor2.setFirstName("Editor");
        fieldEditor2.setLastName("Two");
        fieldEditor2.setEmail("editor.two@journal.two");
        fieldEditor2.setCity("");
        fieldEditor2.setCountry("");
        fieldEditor2.setTitle("");
        fieldEditor2.setExpertise(scientificField2);
        fieldEditor2 = (StaffMember) userService.save(fieldEditor2);

        final List<StaffMember> fieldEditors = new ArrayList<>();
        fieldEditors.add(fieldEditor1);
        fieldEditors.add(fieldEditor2);

        StaffMember reviewer1 = new StaffMember();
        reviewer1.setRole(Role.REVIEWER);
        reviewer1.setUsername("reviewer21");
        reviewer1.setPassword("pass123");
        reviewer1.setFirstName("Reviewer");
        reviewer1.setLastName("One");
        reviewer1.setEmail("reviewer.one@journal.two");
        reviewer1.setCity("");
        reviewer1.setCountry("");
        reviewer1.setTitle("");
        reviewer1.setExpertise(scientificFields);
        reviewer1 = (StaffMember) userService.save(reviewer1);

        StaffMember reviewer2 = new StaffMember();
        reviewer2.setRole(Role.REVIEWER);
        reviewer2.setUsername("reviewer22");
        reviewer2.setPassword("pass123");
        reviewer2.setFirstName("Reviewer");
        reviewer2.setLastName("Two");
        reviewer2.setEmail("reviewer.two@journal.two");
        reviewer2.setCity("");
        reviewer2.setCountry("");
        reviewer2.setTitle("");
        reviewer2.setExpertise(scientificFields);
        reviewer2 = (StaffMember) userService.save(reviewer2);

        StaffMember reviewer3 = new StaffMember();
        reviewer3.setRole(Role.REVIEWER);
        reviewer3.setUsername("reviewer23");
        reviewer3.setPassword("pass123");
        reviewer3.setFirstName("Reviewer");
        reviewer3.setLastName("Three");
        reviewer3.setEmail("reviewer.three@journal.two");
        reviewer3.setCity("");
        reviewer3.setCountry("");
        reviewer3.setTitle("");
        reviewer3.setExpertise(scientificField1);
        reviewer3 = (StaffMember) userService.save(reviewer3);

        StaffMember reviewer4 = new StaffMember();
        reviewer4.setRole(Role.REVIEWER);
        reviewer4.setUsername("reviewer24");
        reviewer4.setPassword("pass123");
        reviewer4.setFirstName("Reviewer");
        reviewer4.setLastName("Four");
        reviewer4.setEmail("reviewer.four@journal.two");
        reviewer4.setCity("");
        reviewer4.setCountry("");
        reviewer4.setTitle("");
        reviewer4.setExpertise(scientificField2);
        reviewer4 = (StaffMember) userService.save(reviewer4);

        final List<StaffMember> reviewers = new ArrayList<>();
        reviewers.add(reviewer1);
        reviewers.add(reviewer2);
        reviewers.add(reviewer3);
        reviewers.add(reviewer4);

        final Publication publication = new Publication();
        publication.setVolume(1);
        publication.setIssue(1);
        publication.setDate(new Date());

        final List<Publication> publications = new ArrayList<>();
        publications.add(publication);

        final Journal journal = new Journal();
        journal.setName("Journal Two");
        journal.setIssn("2222-2222");
        journal.setScientificFields(scientificFields);
        journal.setPaymentOption(PaymentOption.SubscriptionBased);
        journal.setChiefEditor(chiefEditor);
        journal.setFieldEditors(fieldEditors);
        journal.setReviewers(reviewers);
        journal.setPublications(publications);
        journal.setPrice(new BigDecimal(22));
        journalService.save(journal);
    }

    private void createAuthor1() {
        final Member author = new Member();
        author.setUsername("author1");
        author.setPassword("pass123");
        author.setFirstName("Author");
        author.setLastName("One");
        author.setEmail("author.one@author.one");
        author.setCity("");
        author.setCountry("");
        userService.save(author);
    }
}
