package bai5;

public class JobTitle {
    private final String jobCode;
    private final String jobTitle;
    private final String jobDescription;
    private final String jobSpecification;
    private final String note;

    public JobTitle(String jobCode, String jobTitle, String jobDescription, String jobSpecification, String note) {
        this.jobCode = jobCode;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.jobSpecification = jobSpecification;
        this.note = note;
    }

    public String getJobCode() {
        return jobCode;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public String getJobSpecification() {
        return jobSpecification;
    }

    public String getNote() {
        return note;
    }
}
