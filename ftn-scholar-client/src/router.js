import Vue from 'vue';
import Router from 'vue-router';

import SignInForm from './views/SignInForm.vue';
import SignUpForm from './views/SignUpForm.vue';
import HomePage from './views/HomePage.vue';
import SettingsPage from './views/SettingsPage.vue';
import PaymentSuccess from './views/PaymentSuccess.vue';
import PaymentError from './views/PaymentError.vue';
import TasksPage from './views/TasksPage.vue';

import SelectJournalTask from './tasks/SelectJournalTask.vue';
import SubscribeTask from './tasks/SubscribeTask.vue';
import SubmitPaperTask from './tasks/SubmitPaperTask.vue';
import CheckPaperRelevanceTask from './tasks/CheckPaperRelevanceTask.vue';
import CheckPaperFormattingTask from './tasks/CheckPaperFormattingTask.vue';
import ResubmitPaperTask from './tasks/ResubmitPaperTask.vue';
import SelectReviewersTask from './tasks/SelectReviewersTask.vue';
import ReviewPaperTask from './tasks/ReviewPaperTask.vue';
import SummarizeReviewsTask from './tasks/SummarizeReviewsTask.vue';
import CheckCorrectionsTask from './tasks/CheckCorrectionsTask.vue';

Vue.use(Router)

const getRole = () => {
    const authenticatedUser = localStorage.getItem("authenticatedUser");
    if (authenticatedUser) {
        return JSON.parse(authenticatedUser).user.role;
    }
    return '';
}

export default new Router({
    routes: [
        {
            path: '/signin',
            name: 'signin-form',
            component: SignInForm
        },
        {
            path: '/signup',
            name: 'Task_enter_details',
            component: SignUpForm
        },
        {
            path: '/home',
            name: 'home',
            component: HomePage,
            beforeEnter: (to, from, next) => {
                next(getRole() === 'AUTHOR');
            }
        },
        {
            path: '/settings',
            name: 'settings',
            component: SettingsPage,
            beforeEnter: (to, from, next) => {
                next(getRole() === 'CHIEF_EDITOR');
            }
        },
        {
            path: '/payment-success',
            name: 'payment-success',
            component: PaymentSuccess
        },
        {
            path: '/payment-error',
            name: 'payment-error',
            component: PaymentError
        },
        {
            path: '/tasks',
            name: 'tasks',
            component: TasksPage
        },
        {
            path: '/select-journal/:processId',
            name: 'Task_select_journal',
            component: SelectJournalTask,
            beforeEnter: (to, from, next) => {
                next(getRole() === 'AUTHOR');
            }
        },
        {
            path: '/subscribe/:processId',
            name: 'Task_subscribe',
            component: SubscribeTask,
            beforeEnter: (to, from, next) => {
                next(getRole() === 'AUTHOR');
            }
        },
        {
            path: '/submit-paper/:processId',
            name: 'Task_submit_paper',
            component: SubmitPaperTask,
            beforeEnter: (to, from, next) => {
                next(getRole() === 'AUTHOR');
            }
        },
        {
            path: '/check-paper-relevance/:processId',
            name: 'Task_check_paper_relevance',
            component: CheckPaperRelevanceTask,
            beforeEnter: (to, from, next) => {
                next(getRole() === 'CHIEF_EDITOR');
            }
        },
        {
            path: '/check-paper-formatting/:processId',
            name: 'Task_check_paper_formatting',
            component: CheckPaperFormattingTask,
            beforeEnter: (to, from, next) => {
                next(getRole() === 'CHIEF_EDITOR');
            }
        },
        {
            path: '/resubmit-paper/:processId',
            name: 'Task_resubmit_paper',
            component: ResubmitPaperTask,
            beforeEnter: (to, from, next) => {
                next(getRole() === 'AUTHOR');
            }
        },
        {
            path: '/make-corrections/:processId',
            name: 'Task_make_paper_corrections',
            component: ResubmitPaperTask,
            beforeEnter: (to, from, next) => {
                next(getRole() === 'AUTHOR');
            }
        },
        {
            path: '/select-reviewers/:processId',
            name: 'Task_select_reviewers',
            component: SelectReviewersTask,
            beforeEnter: (to, from, next) => {
                next(getRole() === 'CHIEF_EDITOR' || getRole() === 'FIELD_EDITOR');
            }
        },
        {
            path: '/review-paper/:processId',
            name: 'Task_review_paper',
            component: ReviewPaperTask,
            beforeEnter: (to, from, next) => {
                next(getRole() === 'REVIEWER' || getRole() === 'FIELD_EDITOR');
            }
        },
        {
            path: '/review-summary/:processId',
            name: 'Task_summarize_reviews',
            component: SummarizeReviewsTask,
            beforeEnter: (to, from, next) => {
                next(getRole() === 'CHIEF_EDITOR' || getRole() === 'FIELD_EDITOR');
            }
        },
        {
            path: '/check-corrections/:processId',
            name: 'Task_check_paper_corrections',
            component: CheckCorrectionsTask,
            beforeEnter: (to, from, next) => {
                next(getRole() === 'CHIEF_EDITOR' || getRole() === 'FIELD_EDITOR');
            }
        }
    ]
})
