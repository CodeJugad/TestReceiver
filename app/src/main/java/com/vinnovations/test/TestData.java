package com.vinnovations.test;

public class TestData {
    private String answer;
    private String imageUrl;

    TestData(String answer, String imageUrl){
        this.answer = answer;
        this.imageUrl = imageUrl;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
