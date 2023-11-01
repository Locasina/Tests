package com.example.application.data;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "answer", schema = "test")
public class AnswerEntity implements Serializable {
    private int idanswer;
    private String answer;
    private Integer idQ;
    private QuestionsEntity questionsByIdQ;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idanswer", nullable = false)
    public int getIdanswer() {
        return idanswer;
    }

    public void setIdanswer(int idanswer) {
        this.idanswer = idanswer;
    }

    @Basic
    @Column(name = "answer", nullable = true, length = 255)
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Basic
    @Column(name = "idQ", nullable = true,insertable=false, updatable=false)
    public Integer getIdQ() {
        return idQ;
    }

    public void setIdQ(Integer idQ) {
        this.idQ = idQ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnswerEntity that = (AnswerEntity) o;

        if (idanswer != that.idanswer) return false;
        if (answer != null ? !answer.equals(that.answer) : that.answer != null) return false;
        if (idQ != null ? !idQ.equals(that.idQ) : that.idQ != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idanswer;
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        result = 31 * result + (idQ != null ? idQ.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "idQ", referencedColumnName = "idquestions")
    public QuestionsEntity getQuestionsByIdQ() {
        return questionsByIdQ;
    }

    public void setQuestionsByIdQ(QuestionsEntity questionsByIdQ) {
        this.questionsByIdQ = questionsByIdQ;
    }
}
