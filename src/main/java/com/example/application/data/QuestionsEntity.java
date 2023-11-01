package com.example.application.data;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "questions", schema = "test")
public class QuestionsEntity implements Serializable {
    private int idquestions;
    private String question;
    private int typeQe;
    private Collection<AnswerEntity> answersByIdquestions;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idquestions", nullable = false)
    public int getIdquestions() {
        return idquestions;
    }

    public void setIdquestions(int idquestions) {
        this.idquestions = idquestions;
    }

    @Basic
    @Column(name = "question", nullable = true, length = 255)
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Basic
    @Column(name = "Type_qe", nullable = false)
    public int getTypeQe() {
        return typeQe;
    }

    public void setTypeQe(int typeQe) {
        this.typeQe = typeQe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionsEntity that = (QuestionsEntity) o;

        if (idquestions != that.idquestions) return false;
        if (typeQe != that.typeQe) return false;
        if (question != null ? !question.equals(that.question) : that.question != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idquestions;
        result = 31 * result + (question != null ? question.hashCode() : 0);
        result = 31 * result + typeQe;
        return result;
    }

    @OneToMany(mappedBy = "questionsByIdQ")
    public Collection<AnswerEntity> getAnswersByIdquestions() {
        return answersByIdquestions;
    }

    public void setAnswersByIdquestions(Collection<AnswerEntity> answersByIdquestions) {
        this.answersByIdquestions = answersByIdquestions;
    }
}
