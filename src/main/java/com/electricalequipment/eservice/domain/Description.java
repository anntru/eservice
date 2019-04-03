package com.electricalequipment.eservice.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Description.
 */
@Entity
@Table(name = "description")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Description implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "parameter", nullable = false)
    private String parameter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("descriptions")
    private Item item;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParameter() {
        return parameter;
    }

    public Description parameter(String parameter) {
        this.parameter = parameter;
        return this;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Item getItem() {
        return item;
    }

    public Description item(Item item) {
        this.item = item;
        return this;
    }

    public void setItem(Item item) {
        this.item = item;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Description description = (Description) o;
        if (description.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), description.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Description{" +
            "id=" + getId() +
            ", parameter='" + getParameter() + "'" +
            "}";
    }
}
