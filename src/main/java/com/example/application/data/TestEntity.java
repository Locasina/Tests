package com.example.application.data;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "test", schema = "test")
public class TestEntity implements Serializable {
    private int testid;
    private String testName;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "testid", nullable = false)
    public int getTestid() {
        return testid;
    }

    public void setTestid(int testid) {
        this.testid = testid;
    }

    @Basic
    @Column(name = "testName", nullable = false, length = 45)
    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestEntity that = (TestEntity) o;

        if (testid != that.testid) return false;
        if (testName != null ? !testName.equals(that.testName) : that.testName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = testid;
        result = 31 * result + (testName != null ? testName.hashCode() : 0);
        return result;
    }
}
