package com.kyubi.digital.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Candidate.
 */
@Entity
@Table(name = "candidate")
public class Candidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "candidate")
    private Set<Figure> figures = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "candidates", allowSetters = true)
    private Election election;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Figure> getFigures() {
        return figures;
    }

    public Candidate figures(Set<Figure> figures) {
        this.figures = figures;
        return this;
    }

    public Candidate addFigures(Figure figure) {
        this.figures.add(figure);
        figure.setCandidate(this);
        return this;
    }

    public Candidate removeFigures(Figure figure) {
        this.figures.remove(figure);
        figure.setCandidate(null);
        return this;
    }

    public void setFigures(Set<Figure> figures) {
        this.figures = figures;
    }

    public Election getElection() {
        return election;
    }

    public Candidate election(Election election) {
        this.election = election;
        return this;
    }

    public void setElection(Election election) {
        this.election = election;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Candidate)) {
            return false;
        }
        return id != null && id.equals(((Candidate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Candidate{" +
            "id=" + getId() +
            "}";
    }
}
