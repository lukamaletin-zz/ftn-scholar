const fieldsArray = [
    { id: 0, value: "GENERAL_SCIENCE", name: "General science" },
    { id: 1, value: "PHYSICAL_SCIENCE", name: "Physical science" },
    { id: 2, value: "LIFE_SCIENCE", name: "Life science" },
    { id: 3, value: "ENGINEERING", name: "Engineering" },
    { id: 4, value: "MATHEMATICS", name: "Mathematics" },
    { id: 5, value: "MEDICINE", name: "Medicine" },
    { id: 6, value: "OTHER", name: "Other" }
];

const gradesArray = [
    { id: 0, value: "ACCEPT", name: "Accept" },
    { id: 1, value: "MINOR_CORRECTIONS", name: "Minor corrections" },
    { id: 2, value: "MAJOR_CORRECTIONS", name: "Major corrections" },
    { id: 3, value: "REJECT", name: "Reject" }
];

const reviewsArray = [
    { id: 0, value: "ACCEPT", name: "Accept" },
    { id: 1, value: "ADDITIONAL_REVIEW", name: "Request additional review" },
    { id: 2, value: "CORRECTIONS", name: "Request corrections" },
    { id: 3, value: "REJECT", name: "Reject" }
];

const Util = {
    scientificFields: fieldsArray,
    getFieldName(value) {
        const filtered = fieldsArray.filter(f => f.value == value);
        return filtered.length > 0 ? filtered[0].name : '';
    },
    reviewGrades: gradesArray,
    getGradeName(value) {
        const filtered = gradesArray.filter(f => f.value == value);
        return filtered.length > 0 ? filtered[0].name : '';
    },
    reviewActions: reviewsArray,
    getActionName(value) {
        const filtered = reviewsArray.filter(f => f.value == value);
        return filtered.length > 0 ? filtered[0].name : '';
    },
    newDeadline() {
        let date = new Date();
        date.setDate(date.getDate() + 2);
        date.setHours(date.getHours() + 1);
        return date.toISOString().substring(0, 16);
    }
}

export default Util;
