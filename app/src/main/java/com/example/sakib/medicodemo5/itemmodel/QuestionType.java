package com.example.sakib.medicodemo5.itemmodel;

public class QuestionType
{
    private String questionNo;
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String checkedOption;

    public QuestionType(String questionNo, String question, String optionA, String optionB, String optionC, String optionD, String checkedOption) {
        this.questionNo = questionNo;
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.checkedOption = checkedOption;
    }

    public String getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(String questionNo) {
        this.questionNo = questionNo;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getCheckedOption() {
        return checkedOption;
    }

    public void setCheckedOption(String checkedOption) {
        this.checkedOption = checkedOption;
    }
}
