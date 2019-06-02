package io.github.lukamaletin.ftnscholar.model.constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Constants {

    public static final DateFormat DATA_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

    public static final int CONFIRM_PAYMENT_TIMEOUT_MINUTES = 5;

    public static final String ENTER_DETAILS_TASK_KEY = "Task_enter_details";

    public static final String SELECT_JOURNAL_TASK_KEY = "Task_select_journal";

    public static final String RESUBMIT_PAPER_TASK_KEY = "Task_resubmit_paper";

    public static final String REVIEW_PAPER_TASK_KEY = "Task_review_paper";

    public static final String MAKE_PAPER_CORRECTIONS_TASK_KEY = "Task_make_paper_corrections";
}
